package com.itheima.news01.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.itheima.news01.R;
import com.itheima.news01.activity.NewsDetailActivity;
import com.itheima.news01.adapter.NewsAdapter;
import com.itheima.news01.base.URLManager;
import com.itheima.news01.bean.NewsEntity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by yls on 2017/6/27.
 */

public class NewsFragment extends BaseFragment {
    private ListView listView;
    private TextView textView;
    private Intent intent;
    //新闻类别ID
    private String channelId;

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsEntity.ResultBean newBean = (NewsEntity.ResultBean) parent.getItemAtPosition(position);
                intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news",newBean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    //请求服务器获取页签详细数据
    private void getDataFromServer() {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html

        String url = URLManager.getUrl(channelId);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.println("----服务器返回的json数据：" + json);

                json = json.replace(channelId, "result");
                Gson gson = new Gson();
                NewsEntity newsDatas = gson.fromJson(json, NewsEntity.class);
                System.out.println("----解析json: " + newsDatas.getResult().size());

                showDatas(newsDatas);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
    }

    private void showDatas(NewsEntity newsDatas) {
        if (newsDatas == null || newsDatas.getResult() == null || newsDatas.getResult().size() == 0) {
            System.out.println("----没有获取到服务器的新闻数据");
            return;
        }

        //实现轮播图
        List<NewsEntity.ResultBean.AdsBean> ads = newsDatas.getResult().get(0).getAds();

        //有轮播图广告
        if (ads != null && ads.size() > 0) {
            View headerView = View.inflate(getActivity(), R.layout.list_header, null);
            SliderLayout sliderLayout = (SliderLayout) headerView.findViewById(R.id.slider_layout);

            for (int i = 0; i < ads.size(); i++) {
                NewsEntity.ResultBean.AdsBean adsBean = ads.get(i);
                TextSliderView sliderView = new TextSliderView(getActivity());
                sliderView.description(adsBean.getTitle()).image(adsBean.getImgsrc());

                //添加子界面
                sliderLayout.addSlider(sliderView);

                //添加点击事件
                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        showToast(slider.getDescription());
                    }
                });
            }
            listView.addHeaderView(headerView);
        }

        //显示新闻列表
        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), newsDatas.getResult());
        listView.setAdapter(newsAdapter);
    }

    @Override
    public void initView() {
        listView = (ListView) mRoot.findViewById(R.id.list_view);
        textView = (TextView) mRoot.findViewById(R.id.tv_01);
        textView.setText(channelId);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


}
