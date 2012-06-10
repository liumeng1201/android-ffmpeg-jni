package com.neusoft.testffmpegjni;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FFMpegMediaController extends ViewGroup {
	
	private static final String TAG = "FFMpegMediaController";

	private static final int DEFAULT_TIME_OUT = 3000;

	/**
	 * When receive this message, begin to hide the scroll(start animation).
	 */
	private static final int MSG_HIDE_VIEW = 1001;
	
	/**
     * The time of fade out.
     */
    private static final int TIME_FADE_OUT = 300;

	/**
	 * The end time.
	 */
	private TextView mEndTime;
	
	/**
	 * The current time.
	 */
	private TextView mCurrentTime;

	/**
	 * The play pause button.
	 */
	private Button mPauseButton = null;

	/**
	 * The fast forward button.
	 */
	private Button mFfwdButton = null;

	/**
	 * The rewind button.
	 */
	private Button mRewButton = null;
	
	/**
	 * The fast forward button.
	 */
	private Button mSizeButton = null;
	
	/**
	 * The progress bar view.
	 */
	private ProgressView mProgressBar = null;
	
	/**
	 * 
	 */
	private FFMpegMediaPlayerControl mPlayer = null;
	
	/**
	 * The view is showing flag.
	 */
	private boolean mShowing;
	
	/**
	 * The media is playing flag.
	 */
	private boolean mPlaying;

	private int mWidth;

	private int mHeight;

	private int mButtonWidth;

	private int mButtonHeight;

	private int mProgressWidth;

	private int mProgressHeight;

	private int mTextViewWidth;

	private int mTextViewHeight;
	
	/**
     * Scroll Handler.
     */
    private ControllerHandler mHandler;
    
    int count = 0;
    
    /**
     * Scroll'a handler.
     * <br>
     * Handler implement the TIMER worker.
     * After some NO ACTION time, then hide the scroll bar.
     */
    private class ControllerHandler extends Handler {

        /**
         * Handle message.
         * @param msg message
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_HIDE_VIEW:
                wantToHide(TIME_FADE_OUT);
                break;
            }
        }
    }
	
	public FFMpegMediaController(Context context) {
		this(context, null, 0);
	}

	public FFMpegMediaController(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FFMpegMediaController(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		setBackgroundResource(R.drawable.videoplayer_control_bg_9);
		
		mRewButton = new Button(context);
		mRewButton.setBackgroundResource(R.drawable.video_player_control_rew);
		addView(mRewButton);
		mRewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rewindSeekTo();
			}
			
		});
		
		mFfwdButton = new Button(context);
		mFfwdButton.setBackgroundResource(R.drawable.video_player_control_ff);
		addView(mFfwdButton);
		mFfwdButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fastForwardSeeTo();
			}
			
		});
		
		mPauseButton = new Button(context);
		mPauseButton.setBackgroundResource(R.drawable.video_player_control_play);
		addView(mPauseButton);
		mPauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playStateChange();
			}
			
		});
		
		mSizeButton = new Button(context);
		mSizeButton.setBackgroundResource(R.drawable.video_player_ratio);
		addView(mSizeButton);
		
		mProgressBar = new ProgressView(context);
		addView(mProgressBar);
		
		mEndTime = new TextView(context);
		mEndTime.setText("2:20:18");
		mEndTime.setTextSize(16);
		addView(mEndTime);
		mCurrentTime = new TextView(context);
		mCurrentTime.setText("1:10:00");
		mCurrentTime.setTextSize(16);
		addView(mCurrentTime);
		mHandler = new ControllerHandler();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// bookshelf width and bookshelf height
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		mButtonWidth = 152;
		mButtonHeight = 88;
		mTextViewWidth = 80;
		mTextViewHeight = 30;
		mProgressWidth = mWidth - mTextViewWidth * 2;
		mProgressHeight = 60;
		mRewButton.measure(mButtonWidth + View.MeasureSpec.EXACTLY,
				mButtonHeight + View.MeasureSpec.EXACTLY);
		mFfwdButton.measure(mButtonWidth + View.MeasureSpec.EXACTLY,
				mButtonHeight + View.MeasureSpec.EXACTLY);
		mPauseButton.measure(mButtonWidth + View.MeasureSpec.EXACTLY,
				mButtonHeight + View.MeasureSpec.EXACTLY);
		mSizeButton.measure(mButtonWidth + View.MeasureSpec.EXACTLY,
				mButtonHeight + View.MeasureSpec.EXACTLY);
		mEndTime.measure(mTextViewWidth + View.MeasureSpec.EXACTLY,
				mTextViewHeight + View.MeasureSpec.EXACTLY);
		mCurrentTime.measure(mTextViewWidth + View.MeasureSpec.EXACTLY,
				mTextViewHeight + View.MeasureSpec.EXACTLY);
		mProgressBar.measure(mProgressWidth + View.MeasureSpec.EXACTLY,
				mProgressHeight + View.MeasureSpec.EXACTLY);
	}


	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int botton) {
		int l = 0;
		int t = 15;
		int r = l + mTextViewWidth;
		int b = t + mTextViewHeight;
		mCurrentTime.layout(l, t, r, b);
		
		l = mTextViewWidth;
		t = 0;
		r = l + mProgressWidth;
		b = t + mProgressHeight;
		mProgressBar.layout(l, t, r, b);
		
		l = r;
		t = 15;
		r = l + mTextViewWidth;
		b = t + mTextViewHeight;
		mEndTime.layout(l, t, r, b);
		
		l = 0;
		t = 60;
		r = l + mButtonWidth;
		b = t + mButtonHeight;
		mSizeButton.layout(l, t, r, b);
		l = r;
		r += mButtonWidth;
		mRewButton.layout(l, t, r, b);
		l = r;
		r += mButtonWidth;
		mPauseButton.layout(l, t, r, b);
		l = r;
		r += mButtonWidth;
		mFfwdButton.layout(l, t, r, b);
	}
	
	public void setMediaPlayer(FFMpegMediaPlayerControl player) {
		mPlayer = player;
		updatePausePlay();
	}
	
	private void updatePausePlay() {
		if (mPauseButton == null)
			return;

		if (mPlayer.isPlaying()) {
			mPauseButton
					.setBackgroundResource(R.drawable.video_player_control_pause);
			mPlaying = true;
		} else {
			mPauseButton
					.setBackgroundResource(R.drawable.video_player_control_play);
			mPlaying = false;
		}
	}
	
	public void show() {
		show(DEFAULT_TIME_OUT);
	}
	
	/**
     * Show the controller on screen. It will go away
     * automatically after 'timeout' milliseconds of inactivity.
     * @param timeout The timeout in milliseconds. Use 0 to show
     * the controller until hide() is called.
     */
    public void show(int timeout) {
    	initTime();
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.FILL_PARENT, 150, 0, 0,
				WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
				WindowManager.LayoutParams.FLAG_DITHER
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				PixelFormat.TRANSPARENT);
		lp.gravity = Gravity.BOTTOM;
		try {
			((Activity) getContext()).getWindowManager().addView(this, lp);
			mHandler.sendEmptyMessageDelayed(MSG_HIDE_VIEW, timeout);
			mShowing = true;
		} catch (Exception e) {
			mShowing = false;
			Log.e(TAG, "failed to add the book detail view", e);
		}
    }
    
    private void initTime() {
    	if (mPlayer != null) {
    		int duration = mPlayer.getDuration();
    		mEndTime.setText(Util.getTimeByInt(duration));
    		int current = mPlayer.getCurrentPosition();
    		mCurrentTime.setText(Util.getTimeByInt(current));
    	}
	}

	public void wantToHide(int timeFadeOut) {
    	hide();
	}
    
    public void hide() {
		((Activity) this.getContext()).getWindowManager().removeView(this);
		mShowing = false;
	}
    
	protected void playStateChange() {
		resetHideMsg();
		if (mPlayer == null) {
			return;
		}
		if (mPlayer.isPlaying() && mPlayer.canPause()) {
			mPlayer.pause();
		} else if (!mPlayer.isPlaying()) {
			mPlayer.start();
		}
		updatePausePlay();
	}

	protected void fastForwardSeeTo() {
		resetHideMsg();
		if (mPlayer != null) {
			int current = mPlayer.getCurrentPosition();
			int duration = mPlayer.getDuration();
			count = current + 20;
			if (count > duration) {
				count = duration;
			}
			mPlayer.seekTo(count);
		}
	}

	protected void rewindSeekTo() {
		resetHideMsg();
		if (mPlayer != null) {
			int current = mPlayer.getCurrentPosition();
			count = current - 20;
			if (count < 0) {
				count = 0;
			}
			mPlayer.seekTo(count);
		}
	}
    
    /**
     * Clear all message from handler.
     */
    protected void clearHideMsg() {
        mHandler.removeMessages(MSG_HIDE_VIEW);
    }
    
    /**
     * Clear all HIDE message and insert one message.
     */
    protected void resetHideMsg() {
        clearHideMsg();
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_VIEW, DEFAULT_TIME_OUT);
    }
	
	private class ProgressView extends ViewGroup {
		
		private ImageView mProgressBefore = null;
		
		private ImageView mProgressAfter = null;
		
		private ImageView mProgressPoint = null;

		private int mProgressBarWidth;

		private int mProgressBarHeight;

		private int mPointWidth;

		private int mPointHeight;

		public ProgressView(Context context) {
			super(context);
			mProgressBefore = new ImageButton(context);
			mProgressBefore.setBackgroundResource(R.drawable.loadingbefore);
			addView(mProgressBefore);
			
			mProgressAfter = new ImageButton(context);
			mProgressAfter.setBackgroundResource(R.drawable.loadingafter);
			addView(mProgressAfter);
			
			mProgressPoint = new ImageButton(context);
			mProgressPoint.setBackgroundResource(R.drawable.videoplay_progress_thumb_normal);
			addView(mProgressPoint);
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			mProgressBarWidth = getMeasuredWidth();
			mProgressBarHeight = 10;
			mPointWidth = 58;
			mPointHeight = 58;
			
			mProgressBefore.measure(mProgressBarWidth + View.MeasureSpec.EXACTLY,
					mProgressBarHeight + View.MeasureSpec.EXACTLY);
			mProgressAfter.measure(mProgressBarWidth + View.MeasureSpec.EXACTLY,
					mProgressBarHeight + View.MeasureSpec.EXACTLY);
			mProgressPoint.measure(mPointWidth + View.MeasureSpec.EXACTLY,
					mPointHeight + View.MeasureSpec.EXACTLY);
		}

		@Override
		protected void onLayout(boolean changed, int left, int top, int right, int botton) {
			int l = 0;
			int t = 25;
			int r = l + mProgressBarWidth;
			int b = t + mProgressBarHeight;
			mProgressBefore.layout(l, t, r, b);
			mProgressAfter.layout(l, t, r- 200, b);
			mProgressPoint.layout(r - 238, 1, r - 180, mPointHeight + 1);
		}
	}
	
	public interface FFMpegMediaPlayerControl {
        void    start();
        void    pause();
        int     getDuration();
        int     getCurrentPosition();
        void    seekTo(int pos);
        boolean isPlaying();
        int     getBufferPercentage();
        boolean canPause();
        boolean canSeekBackward();
        boolean canSeekForward();
        int     getAudioTimeBase();
    }

	public boolean isShowing() {
		return mShowing;
	}
}
