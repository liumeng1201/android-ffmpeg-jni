LOCAL_PATH:= $(TOP_LOCAL_PATH)
include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := TestFFmpegJNI

LOCAL_JNI_SHARED_LIBRARIES := libplayer-8 libplayer-9 libffmpeg log

include $(BUILD_PACKAGE)

PRODUCT_COPY_FILES := \
    $(LOCAL_PATH)/obj/local/armeabi/libffmpeg.so:$(LOCAL_PATH)/libs/armeabi/libffmpeg.so
# ============================================================

# Also build all of the sub-targets under this one: the shared library.
include $(call all-makefiles-under,$(LOCAL_PATH))
