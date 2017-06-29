package com.itheima.news01.activity;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.itheima.news01.R;
import com.itheima.news01.bean.NewsEntity;

/**
 * Created by yls on 2017/6/28.
 */

public class NewsDetailActivity extends BaseActivity{
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initData() {
        NewsEntity.ResultBean newBean = (NewsEntity.ResultBean) getIntent().getSerializableExtra("news");
       webView.loadUrl(newBean.getUrl());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(newBean.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        progressBar = (ProgressBar) findViewById(R.id.pb_01);
        initWebView();

    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.web_view);

        //当点击WebView显示的网页的链接时，禁止使用其他浏览器打开
        webView.setWebViewClient(new WebViewClient());

        //设置webview支持的javasrcipt脚本
        webView.getSettings().setJavaScriptEnabled(true);

        //显示加载网页的速度
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                    System.out.println("------percent: " + newProgress);
                }
            }
        });
    }
}
