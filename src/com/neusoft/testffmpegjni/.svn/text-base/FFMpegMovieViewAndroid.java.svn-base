package com.neusoft.testffmpegjni;

import java.io.IOException;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.neusoft.testffmpegjni.FFMpegMediaController.FFMpegMediaPlayerControl;

public class FFMpegMovieViewAndroid extends SurfaceView {
	private static final String 	TAG = "FFMpegMovieViewAndroid"; 
	
	private Context					mContext;
	private FFMpegPlayer			mPlayer;
	private SurfaceHolder			mSurfaceHolder;
	private FFMpegMediaController			mMediaController;
	
	public FFMpegMovieViewAndroid(Context context) {
        super(context);
        initVideoView(context);
    }
    
    public FFMpegMovieViewAndroid(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initVideoView(context);
    }
    
    public FFMpegMovieViewAndroid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVideoView(context);
    }
    
    private void initVideoView(Context context) {
    	mContext = context;
    	mPlayer = new FFMpegPlayer();
    	getHolder().addCallback(mSHCallback);
        attachMediaController();
    }
    
    private void attachMediaController() {
    	mMediaController = new FFMpegMediaController(mContext);
        View anchorView = this.getParent() instanceof View ?
                    (View)this.getParent() : this;
        mMediaController.setMediaPlayer(mMediaPlayerControl);
//        mMediaController.setAnchorView(anchorView);
        mMediaController.setEnabled(true);
    }
    
    public void setVideoPath(String filePath) throws IllegalArgumentException, IllegalStateException, IOException {
		mPlayer.setDataSource(filePath);
	}
    
    /**
     * initzialize player
     */
    private void openVideo() {
    	try {
            Log.e(TAG, "openVideo start");
//            mSurfaceHolder.setFixedSize(800, 480);
    		mPlayer.setDisplay(mSurfaceHolder);
            Log.e(TAG, "openVideo end");
//    		mPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
//			mPlayer.prepare();
		} catch (IllegalStateException e) {
			Log.e(TAG, "Couldn't prepare player: " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "Couldn't prepare player: " + e.getMessage());
		}
    }
    
    private void startVideo() {
        Log.e(TAG, "startVideo start");
    	attachMediaController();
//    	mPlayer.close();
    	mPlayer.start();
        Log.e(TAG, "startVideo end");
    }
    
    private void release() {
    	Log.d(TAG, "releasing player");
    	
    	mPlayer.release();
		
		Log.d(TAG, "released");
    }
    
    public boolean onTouchEvent(android.view.MotionEvent event) {
    	if(mMediaController != null && !mMediaController.isShowing()) {
			mMediaController.show(3000);
		}
    	if (mMediaController.isShowing()) {
    		mMediaController.onTouchEvent(event);
    	}
		return true;
    }
    
    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Log.e(TAG, "surfaceChanged : w = " + w + "  h = " + h + " format = " + format);
            mSurfaceHolder = holder;
            openVideo();
            startVideo();
            Log.e(TAG, "surfaceChanged end");
        }

        public void surfaceCreated(SurfaceHolder holder) {
            Log.e(TAG, "surfaceCreated");
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.e(TAG, "surfaceDestroyed");
			release();
			if(mMediaController != null && mMediaController.isShowing()) {
				mMediaController.hide();
			}
			// after we return from this we can't use the surface any more
            mSurfaceHolder = null;
        }
    };
    FFMpegMediaPlayerControl mMediaPlayerControl = new FFMpegMediaPlayerControl() {
		
		public void start() {
			mPlayer.resume();
		}
		
		public void seekTo(int pos) {
			Log.d(TAG, "seekTo: " + pos);
			mPlayer.seekTo(pos);
		}
		
		public void pause() {
			mPlayer.pause();
		}
		
		public boolean isPlaying() {
			return mPlayer.isPlaying() == 0;
		}
		
		public int getDuration() {
			return mPlayer.getDuration();
		}
		
		public int getCurrentPosition() {
			return mPlayer.getCurrentPosition();
		}
		
		public int getBufferPercentage() {
			return 0;
		}

        public boolean canPause() {
            return true;
        }

        public boolean canSeekBackward() {
            return true;
        }

        public boolean canSeekForward() {
            return true;
        }

		@Override
		public int getAudioTimeBase() {
			int time = mPlayer.getAudioTimeBase();
			return time;
		}
	};
}
