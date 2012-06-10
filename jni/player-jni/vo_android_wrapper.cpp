
#include <pthread.h>
#include <jni.h>
#if PLATFORM < 8
#include <ui/Surface.h>
#elif PLATFORM >= 8
#include <surfaceflinger/Surface.h>
#else
#error "?"
#endif
#include "utility.h"
#include <utils/Log.h>
#include <SkBitmap.h>
#include <SkCanvas.h>

#define ANDROID_SURFACE_RESULT_SUCCESS						 0
#define ANDROID_SURFACE_RESULT_NOT_VALID					-1
#define ANDROID_SURFACE_RESULT_COULDNT_LOCK					-2
#define ANDROID_SURFACE_RESULT_COULDNT_UNLOCK_AND_POST		-3
#define ANDROID_SURFACE_RESULT_COULDNT_INIT_BITMAP_SURFACE	-4
#define ANDROID_SURFACE_RESULT_COULDNT_INIT_BITMAP_CLIENT	-5
#define ANDROID_SURFACE_RESULT_JNI_EXCEPTION				-6
#define TAG "chenhb"

using namespace android;

//static pthread_mutex_t mutex;
static int locked = 0;
static Surface* surface = 0;
static char dummy[sizeof(Surface::SurfaceInfo)];
static Surface::SurfaceInfo* info = (Surface::SurfaceInfo*) dummy;

static SkBitmap		sBitmapClient;
static SkBitmap		sBitmapSurface;

extern "C" {

void createSurfaceLock() {
    //pthread_mutex_init(&mutex, 0);
    locked = 0;
}

void destroySurfaceLock() {
    //pthread_mutex_lock(&mutex);
    if (surface) {
            surface->unlockAndPost();
            locked = 0;
        }
    surface = 0;
    locked = 0;
    //pthread_mutex_unlock(&mutex);
    //pthread_mutex_destroy(&mutex);
}

void lockSurface() {
    //pthread_mutex_lock(&mutex);
    if (surface) {
        surface->lock(info);
        locked = -1;
    }
}

void unlockSurface() {
    if (surface) {
        surface->unlockAndPost();
        locked = 0;
    }
    //pthread_mutex_unlock(&mutex);
}

void getSurfaceInfo(int* w , int* h, int* s, void** p) {
    if (!locked) {
        return;
    }
    if (w) {
        *w = info->w;
    }
    if (h) {
        *h = info->h;
    }
#if PLATFORM < 8
    if (s) {
        *s = info->s;//bpr;
    }
    if (p) {
        *p = (reinterpret_cast<int>(info->bits) < 0x0200) ? info->bits : info->bits;//base
    }
#elif PLATFORM >= 8
    if (s) {
        *s = info->s;
    }
    if (p) {
        *p = info->bits;
    }
#else
#error ?
#endif
}

jint attach(JNIEnv* env, jobject thiz, jobject surf) {
    jclass cls = env->GetObjectClass(surf);
#if PLATFORM == 8
    	jfieldID fid = env->GetFieldID(cls, "mSurface", "I");
#elif PLATFORM > 8
    	jfieldID fid = env->GetFieldID(cls, "mNativeSurface", "I");
#endif
    Surface* ptr = (Surface*)env->GetIntField(surf, fid);
    if (ptr) {
        //pthread_mutex_lock(&mutex);
        surface = ptr;
        locked = 0;
        //pthread_mutex_unlock(&mutex);
        return 0;
    }
	__android_log_print(ANDROID_LOG_INFO, TAG,
			"attach Surface error..");
    return -1;
}

void detach(JNIEnv* env, jobject thiz) {
    if (surface) {
        //pthread_mutex_lock(&mutex);
        surface = 0;
        locked = 0;
        //pthread_mutex_unlock(&mutex);
    }
}


static int initBitmap(SkBitmap *bitmap, int format, int width, int height, bool allocPixels) {
	switch (format) {
        case PIXEL_FORMAT_RGBA_8888:
            bitmap->setConfig(SkBitmap::kARGB_8888_Config, width, height);
            break;

        case PIXEL_FORMAT_RGBA_4444:
            bitmap->setConfig(SkBitmap::kARGB_4444_Config, width, height);
            break;

        case PIXEL_FORMAT_RGB_565:
            bitmap->setConfig(SkBitmap::kRGB_565_Config, width, height);
            break;

        case PIXEL_FORMAT_A_8:
            bitmap->setConfig(SkBitmap::kA8_Config, width, height);
            break;

        default:
            bitmap->setConfig(SkBitmap::kNo_Config, width, height);
            break;
    }

	if(allocPixels) {
		bitmap->setIsOpaque(true);
		//-- alloc array of pixels
		if(!bitmap->allocPixels()) {
			return -1;
		}
	}
	return 0;
}


static int prepareSurfaceBitmap(Surface::SurfaceInfo* info) {
	if(initBitmap(&sBitmapSurface, info->format, info->w, info->h, false) < 0) {
		__android_log_print(ANDROID_LOG_INFO, TAG,
				"prepareSurfaceBitmap error..");
		return -1;
	}
	sBitmapSurface.setPixels(info->bits);
	return 0;
}

static void doUpdateSurface() {
	SkCanvas	canvas(sBitmapSurface);
	SkRect		surface_sBitmapClient;
	SkRect		surface_sBitmapSurface;
	SkMatrix	matrix;

	surface_sBitmapSurface.set(0, 0, sBitmapSurface.width(), sBitmapSurface.height());
	surface_sBitmapClient.set(0, 0, sBitmapClient.width(), sBitmapClient.height());
	matrix.setRectToRect(surface_sBitmapClient, surface_sBitmapSurface, SkMatrix::kFill_ScaleToFit);

	canvas.drawBitmapMatrix(sBitmapClient, matrix);
}

int AndroidSurface_getPixels(int width, int height, void** pixels) {
	__android_log_print(ANDROID_LOG_INFO, TAG, "getting surface's pixels %ix%i", width, height);
	if(surface == NULL) {
		return ANDROID_SURFACE_RESULT_JNI_EXCEPTION;
	}
	if(initBitmap(&sBitmapClient, PIXEL_FORMAT_RGB_565, width, height, true) < 0) {
		return ANDROID_SURFACE_RESULT_COULDNT_INIT_BITMAP_CLIENT;
	}
	*pixels = sBitmapClient.getPixels();
	__android_log_print(ANDROID_LOG_INFO, TAG, "getted");
	return ANDROID_SURFACE_RESULT_SUCCESS;
}
int AndroidSurface_updateSurface(uint8_t *pixel) {
	sBitmapClient.setPixels(pixel);
	static Surface::SurfaceInfo surfaceInfo;
	if(surface == NULL) {
		return ANDROID_SURFACE_RESULT_JNI_EXCEPTION;
	}

	if (!surface->isValid()) {
		return ANDROID_SURFACE_RESULT_NOT_VALID;
	}

	if (surface->lock(&surfaceInfo) < 0) {
		return ANDROID_SURFACE_RESULT_COULDNT_LOCK;
	}

	if(prepareSurfaceBitmap(&surfaceInfo) < 0) {
		return ANDROID_SURFACE_RESULT_COULDNT_INIT_BITMAP_SURFACE;
	}

	doUpdateSurface();

	if (surface->unlockAndPost() < 0) {
		return ANDROID_SURFACE_RESULT_COULDNT_UNLOCK_AND_POST;
	}
	return ANDROID_SURFACE_RESULT_SUCCESS;
}

}

