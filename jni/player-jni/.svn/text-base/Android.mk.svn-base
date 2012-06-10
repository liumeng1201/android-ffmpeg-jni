LOCAL_PATH:= $(call my-dir)

##first lib
include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_MODULE := player-8

AOSP := /home/xiaoyu-wang/work/src/android-2.2_r1.1

LOCAL_C_INCLUDES += \
    $(AOSP)/hardware/libhardware/include \
    $(AOSP)/system/core/include \
	$(AOSP)/external/skia/src/core \
	$(AOSP)/external/skia/include/core \
    $(AOSP)/frameworks/base/include \
    $(LOCAL_PATH)/../libffmpeg

LOCAL_CFLAGS += -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -DCLASS=com_neusoft_testffmpegjni_FFMpegPlayer -DPLATFORM=8

LOCAL_SRC_FILES := \
    utility.c \
    player.c \
    input.c \
    output.c \
    decode.c \
    queue.c \
    ao.c \
    vo.c \
    ao_android.c \
    ao_android_wrapper.cpp \
    vo_android.c \
    vo_android_wrapper.cpp \
    jni.c

LOCAL_LDFLAGS += -L$(LOCAL_PATH)/aosp-f-8
LOCAL_LDLIBS += -llog -lutils -lsurfaceflinger_client -lmedia 
LOCAL_STATIC_LIBRARIES += libswscale 
LOCAL_SHARED_LIBRARIES += ffmpeg skia

include $(BUILD_SHARED_LIBRARY)
#include /home/chenhb/ffmpeg/TestFFmpegJNI/jni/ffmpeg/libswscale/Android.mk
#include /home/chenhb/ffmpeg/TestFFmpegJNI/jni/ffmpeg/libavutil/Android.mk



##second lib
include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_MODULE := player-9

AOSP := /home/xiaoyu-wang/work/src/android2.3

LOCAL_C_INCLUDES += \
    $(AOSP)/hardware/libhardware/include \
    $(AOSP)/system/core/include \
	$(AOSP)/external/skia/src/core \
	$(AOSP)/external/skia/include/core \
    $(AOSP)/frameworks/base/include \
    $(AOSP)/frameworks/base/native/include \
    $(LOCAL_PATH)/../libffmpeg

LOCAL_CFLAGS += -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -DCLASS=com_neusoft_testffmpegjni_FFMpegPlayer -DPLATFORM=9

LOCAL_SRC_FILES := \
    utility.c \
    player.c \
    input.c \
    output.c \
    decode.c \
    queue.c \
    ao.c \
    vo.c \
    ao_android.c \
    ao_android_wrapper.cpp \
    vo_android.c \
    vo_android_wrapper.cpp \
    jni.c

LOCAL_LDFLAGS += -L$(LOCAL_PATH)/aosp-f-9
LOCAL_LDLIBS += -llog -lutils -lsurfaceflinger_client -lmedia 
LOCAL_STATIC_LIBRARIES += libswscale 
LOCAL_SHARED_LIBRARIES += ffmpeg skia

include $(BUILD_SHARED_LIBRARY)