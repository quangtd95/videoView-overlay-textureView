package com.androidbegin.videostreamtutorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.io.IOException;

public class VideoViewActivity extends Activity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview, smallView1, smallView2, smallView3;

    // Insert your Video URL
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    AutoFitTextureView myTexture;
    private Camera mCamera;
    private RelativeLayout mRl1;
    private RelativeLayout mRl2;
    private RelativeLayout mRl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.videoview_main);
        // Find your VideoView in your video_main.xml layout
//        videoview = findViewById(R.id.VideoView);
        myTexture = findViewById(R.id.VideoView);
        mRl1 = findViewById(R.id.rl1);
        mRl2 = findViewById(R.id.rl2);
        mRl3 = findViewById(R.id.rl3);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;
        myTexture.setAspectRatio(w, h);
        smallView1 = findViewById(R.id.smallView1);
        smallView2 = findViewById(R.id.smallView2);
        smallView3 = findViewById(R.id.smallView3);
        myTexture.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                mCamera = Camera.open();
                Camera.Size previewSize = mCamera.getParameters().getPreviewSize();

               /* myTexture.setLayoutParams(new RelativeLayout.LayoutParams(
                        previewSize.width, previewSize.height));
*/
                try {
                    mCamera.setPreviewTexture(surfaceTexture);
                } catch (IOException t) {
                }

                mCamera.startPreview();
                myTexture.setAlpha(1.0f);
                myTexture.setRotation(90.0f);
            }

            @Override public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                mCamera.stopPreview();
                mCamera.release();
                return true;
            }

            @Override public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
        // Execute StreamVideo AsyncTask
        // Create a progressbar
        pDialog = new ProgressDialog(VideoViewActivity.this);
        // Set progressbar title
        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoViewActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            String videoPath = "/storage/emulated/0/test.mp4";
            String videoPath1 = "/storage/emulated/0/binladen.mp4";
            String videoPath2 = "/storage/emulated/0/game.mp4";
            String videoPath3 = "/storage/emulated/0/temp0.mp4";
//            videoview.setMediaController(mediacontroller);
            /*videoview.setVideoURI(video);
            smallView1.setVideoURI(video);
            smallView2.setVideoURI(video);
            smallView3.setVideoURI(video);*/
//            videoview.setVideoPath(videoPath);
            smallView1.setVideoPath(videoPath1);
            smallView2.setVideoPath(videoPath2);
            smallView3.setVideoPath(videoPath3);
            smallView1.setZOrderMediaOverlay(true);
            smallView2.setZOrderMediaOverlay(true);
            smallView3.setZOrderMediaOverlay(true);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        mRl1.setOnTouchListener(getDragListener());
        mRl2.setOnTouchListener(getDragListener());
        mRl3.setOnTouchListener(getDragListener());


//        videoview.requestFocus();
      /*  videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {*/
        pDialog.dismiss();
//        videoview.start();
        smallView1.start();
        smallView2.start();
        smallView3.start();

      /*      }
        });*/

    }

    View.OnTouchListener getDragListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    view.setX(motionEvent.getRawX() - view.getWidth() / 2);
                    view.setY(motionEvent.getRawY() - view.getHeight() / 2);
                    return false;
                }
                return true;
            }
        };
    }

}