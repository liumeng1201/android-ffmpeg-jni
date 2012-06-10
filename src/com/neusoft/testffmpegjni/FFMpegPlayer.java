package com.neusoft.testffmpegjni;


import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class FFMpegPlayer
{
    private final static String         TAG = "MediaPlayer";
//    private static int sdk=android.os.Build.VERSION.SDK_INT;

    private Surface                     mSurface; // accessed by native methods
    private SurfaceHolder               mSurfaceHolder;
    private String mPath = "";
    private static int sdk=android.os.Build.VERSION.SDK_INT;

    
    public static final String[] EXTENSIONS = new String[] {
        ".mp4", //ok
        ".flv", //ok
        ".wmv", //ok
        ".mov", //ok
        ".mkv", //ok
        ".m4a", 
        ".3gpp", 
        ".3gpp2", 
        ".3g2", 
        ".3gp", //ma sai ke
        ".avi", //ma sai ke
        ".mpg", //ma sai ke
        ".vob",   //ma sai ke
        ".rmvb"  //bu tong bu
    };
//    private static void loadlibs()
    static{
    	
    	String sdkstr = Integer.toString(sdk);
    	Log.d(TAG, "load libs......111");
        System.loadLibrary("dl");
        System.loadLibrary("ffmpeg");
       if(sdk == 8)
        {
    	   System.loadLibrary("player-8");
    	   Log.d(TAG, "load libs......8");
        }
        
        Log.d(TAG, sdkstr);
        if(sdk >= 9)
        {
        	Log.d(TAG, "load libs......9");
        	System.loadLibrary("player-9");
        }
   }

    public FFMpegPlayer() {
        /* Native setup requires a weak reference to our object.
         * It's easier to create it here than in C++.
         */
//    	loadlibs();
        new WeakReference<FFMpegPlayer>(this);
    }
    
    
    /**
     * Sets the SurfaceHolder to use for displaying the video portion of the media.
     * This call is optional. Not calling it when playing back a video will
     * result in only the audio track being played.
     *
     * @param sh the SurfaceHolder to use for video display
     * @throws IOException 
     */
    public void setDisplay(SurfaceHolder sh) throws IOException {
        mSurfaceHolder = sh;
        if (sh != null) {
            mSurface = sh.getSurface();
        } else {
            mSurface = null;
        }
        attach(mSurface);
    }
    public void setDataSource(String path) throws IOException {
        mPath = path;
    }
    
    /**
     * Starts or resumes playback. If playback had previously been paused,
     * playback will continue from where it was paused. If playback had
     * been stopped, or never started before, playback will start at the
     * beginning.
     *
     * @throws IllegalStateException if it is called in an invalid state
     */
    public void start() throws IllegalStateException {
        //stayAwake(true);
        if (TextUtils.isEmpty(mPath)) {
            return;
        }
        open(mPath);
        play(0,0,0,1);
    }

    /**
     * Pauses playback. Call start() to resume.
     *
     * @throws IllegalStateException if the internal player engine has not been
     * initialized.
     */
//    public void pause() throws IllegalStateException {
//        //stayAwake(false);
//        nativePause();
//    }
    
    /**
     * Returns the width of the video.
     *
     * @return the width of the video, or 0 if there is no video,
     * no display surface was set, or the width has not been determined
     * yet. The OnVideoSizeChangedListener can be registered via
     * {@link #setOnVideoSizeChangedListener(OnVideoSizeChangedListener)}
     * to provide a notification when the width is available.
     */
//    public int getVideoWidth() {
//        return nativeGetVideoWidth();
//    }

    
    /**
     * Returns the height of the video.
     *
     * @return the height of the video, or 0 if there is no video,
     * no display surface was set, or the height has not been determined
     * yet. The OnVideoSizeChangedListener can be registered via
     * {@link #setOnVideoSizeChangedListener(OnVideoSizeChangedListener)}
     * to provide a notification when the height is available.
     */
//    public int getVideoHeight() {
//        return nativeGetVideoHeight();
//    }

    /**
     * Checks whether the MediaPlayer is playing.
     *
     * @return true if currently playing, false otherwise
     */
//    public boolean isPlaying() {
//        return nativeIsPlaying();
//    }

    /**
     * Seeks to specified time position.
     *
     * @param msec the offset in milliseconds from the start to seek to
     * @throws IllegalStateException if the internal player engine has not been
     * initialized
     */
    public void seekTo(int msec) {
        seek(msec);
    }

    /**
     * Gets the current playback position.
     *
     * @return the current position in milliseconds
     */
	public int getCurrentPosition() {
		int current = getCurrentTime();
		Log.d("DeskApp", "getCurrentTime: " + current);
		return current;
	}

    /**
     * Gets the duration of the file.
     *
     * @return the duration in milliseconds
     */
//    public int getDuration() {
//        return nativeGetDuration();
//    }
    
//    public native void _release();
//    
//    public native void _reset();
    
//    public void resume() {
//        resume();
//    }

//    @Override
//    protected void finalize() {
//    }

    public void release() {
        close();
        detach();
    }
    
//    public void getSubtitleStreamCount() {
//        nativeGetSubtitleStreamCount();
//    }
//    public void setVideoMode(int mode) {
//        nativeSetVideoMode(mode);
//    }
//    public void getAudioStreamCount() {
//        getAudioStreamCount();
//    }
//
//    public int getVideoStreamCount() {
//        getVideoStreamCount();
//    }
    
    public native int open(String strPath);
    public native void close();
    public native int play(double start, int audio, int video, int subtitle);
    public native void pause();
    public native void resume();
    public native int seek(double seekTo);
    public native int getDuration();
    public native int getCurrentTime();
    public native int setVideoMode(int mode);
    public native int getVideoWidth();
    public native int getVideoHeight();
    public native int getAudioStreamCount();
    public native int getVideoStreamCount();
    public native int getSubtitleStreamCount();
    public native int isPlaying();
    public native int attach(android.view.Surface s);
    public native void detach();
    public native int getAudioTimeBase();
}