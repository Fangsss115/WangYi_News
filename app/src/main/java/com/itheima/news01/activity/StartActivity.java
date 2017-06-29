package com.itheima.news01.activity;

import android.content.Intent;
import android.os.SystemClock;

import com.itheima.news01.R;

/**
 * Created by yls on 2017/6/27.
 */

public class StartActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_start;
    }

    @Override
    public void initData() {
        new Thread() {
            public void run() {
                SystemClock.sleep(1500);
                enterGuideActivity();
            }
        }.start();
    }

    private void enterGuideActivity() {
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {

    }
}
