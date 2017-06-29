package com.itheima.news01.activity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itheima.news01.R;

/**
 * Created by yls on 2017/6/27.
 */
public class GuideActivity extends BaseActivity {
    private ImageView iv01;
    private Button btnGo;
    private MediaPlayer mMediaPlayer;

    private int count = 0;
    private int[] imagesArray = new int[]{
            R.drawable.ad_new_version1_img1,
            R.drawable.ad_new_version1_img2,
            R.drawable.ad_new_version1_img3,
            R.drawable.ad_new_version1_img4,
            R.drawable.ad_new_version1_img5,
            R.drawable.ad_new_version1_img6,
            R.drawable.ad_new_version1_img7,
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    startAnimation();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide;
    }

    @Override
    public void initData() {
        startAnimation();   //开始显示动画
    }

    private void startAnimation() {
        count++;
        count = count % imagesArray.length;  //取余数
        iv01.setBackgroundResource(imagesArray[count]);

        iv01.setScaleX(1f);
        iv01.setScaleY(1f);

        iv01.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(3500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mHandler.sendEmptyMessageDelayed(0, 1000);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    @Override
    public void initListener() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMainActivity();
            }
        });

    }

    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        iv01 = (ImageView) findViewById(R.id.iv_01);
        btnGo = (Button) findViewById(R.id.btn_go);
    }

    private void playBackgroundMusic() {
        try {
            AssetFileDescriptor fileDescriptor = getAssets().openFd("new_version.mp3");
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    0L, fileDescriptor.getLength());
            mMediaPlayer.setLooping(true);        // 循环播放
            mMediaPlayer.setVolume(1.0f, 1.0f);   // 左声道音量 右声道音量
            mMediaPlayer.prepare();               // 缓冲文件
            mMediaPlayer.start();                 // 开始播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        playBackgroundMusic();     //开始播放
    }

    @Override
    protected void onStop() {
        super.onStop();

        //释放MediaPlayer资源
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
