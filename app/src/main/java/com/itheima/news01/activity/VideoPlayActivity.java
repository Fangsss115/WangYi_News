package com.itheima.news01.activity;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.itheima.news01.R;

/**
 * Created by yls on 2017/6/29.
 */
public class VideoPlayActivity extends BaseActivity{
    private VideoView mVideoView;
    private ProgressBar mProgressBar;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_play;
    }



    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_01);

    }

    @Override
    public void initData() {
        String videoUrl = getIntent().getStringExtra("video_url");

        // 设置视频播放路径
        mVideoView.setVideoPath(videoUrl);

        // 设置监听器，监听缓冲完成
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override   // 缓冲完成后回调
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();  // 缓冲完成后，开始播放视频
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
