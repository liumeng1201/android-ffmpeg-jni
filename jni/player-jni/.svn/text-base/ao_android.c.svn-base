
#include <pthread.h>
#include <libavcodec/avcodec.h>
#include "ao.h"
#include "queue.h"

#include <android/log.h>
#define LOG_TAG "CHENHB_DECODE"
#define LOGI(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

extern int audio_track_create(int rate, int format, int channel);
extern void audio_track_play(const void* buffer, int length);
extern void audio_track_destroy();

static int ao_init_android() {
    return 0;
}

static int ao_play_android(Samples* sam, void* extra) {
    int err;
    //LOGI("ao_play_android -- in");
    err = audio_track_create(sam->rate, sam->format, sam->channel);
    //LOGI("ao_play_android -- 1");
    if (!err && sam->samples && sam->size) {
        //LOGI("ao_play_android -- 2");
        audio_track_play(sam->samples, sam->size);
        //LOGI("ao_play_android -- 3");
    }
    //LOGI("ao_play_android -- out");
    return err;
}

static void ao_free_android() {
    audio_track_destroy();
}

ao_t ao_android = {
    .name = "android",
    .init = ao_init_android,
    .free = ao_free_android,
    .play = ao_play_android,
};

