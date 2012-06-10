package com.neusoft.testffmpegjni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View.OnClickListener;

public class TestFFmpegJNI extends Activity {

    private static final String TAG = "TestFFmpegJNI";

    // private static final String LICENSE =
    // "This software uses libraries from the FFmpeg project under the LGPLv2.1";

    private FFMpegMovieViewAndroid mMovieView;

    // private WakeLock mWakeLock;
    private SurfaceHolder           mSurfaceHolder;
    private FFMpegPlayer mFFMpegPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        Intent i = getIntent();
        String filePath = i.getStringExtra(getResources().getString(R.string.input_file));
//        String filePath = "/sdcard/123.mp4";
//      String filePath = "/sdcard/MTV/liangjinru.rmvb";  //bu tong bu
//      filePath = "/sdcard/MTV/liangjinru.avi";  //ma sai ke
//      filePath = "/sdcard/MTV/liangjinru.mkv";  //ok
//      filePath = "/sdcard/MTV/liangjinru.mpg";  //ma sai ke
//      filePath = "/sdcard/MTV/liangjinru.vob";  //ma sai ke
//      filePath = "/sdcard/MTV/liangjinru.mov";  //ok
//      filePath = "/sdcard/MTV/liangjinru.wmv";  //ok
//      filePath = "/sdcard/MTV/liangjinru_720P.mp4";   //ok
//      filePath = "/sdcard/MTV/1280P.mkv";    //bu tong bu
//      filePath = "http://10.10.80.252:8080/airchina/VID_20110421_130617.3gp";
//      filePath = "http://10.10.80.252:8080/airchina/liangjinru.mkv";
        if (filePath == null) {
            Log.d(TAG, "Not specified video file");
            finish();
        } else {
            // PowerManager pm = (PowerManager)
            // getSystemService(Context.POWER_SERVICE);
            // mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
            // TAG);

            try {
                mMovieView = new FFMpegMovieViewAndroid(this);
                mMovieView.setVideoPath(filePath);
                setContentView(mMovieView);
            } catch (Exception e) {
                Log.e(TAG, "Error when inicializing ffmpeg: " + e.getMessage(), e);
                finish();
            }
        }
//        this.setContentView(R.layout.main);
//        
//        Button button = (Button) this.findViewById(R.id.play);
//        button.setOnClickListener(this);
//    }
//    @Override
//    public void onClick(View paramView) {
//        // TODO Auto-generated method stub
//
//        SurfaceView sv = (SurfaceView) this.findViewById(R.id.surface);
//        mSurfaceHolder = sv.getHolder();
//        mSurfaceHolder.setFixedSize(480, 724);
//        if (mFFMpegPlayer != null) {
//            mFFMpegPlayer.release();
//            mFFMpegPlayer = null;
//        }
//        mFFMpegPlayer = new FFMpegPlayer(); 
//        try {
//            mFFMpegPlayer.setDisplay(mSurfaceHolder);
//            mFFMpegPlayer.setVideoMode(0);
//            mFFMpegPlayer.setDataSource("/sdcard/123.mp4");
//            mFFMpegPlayer.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}