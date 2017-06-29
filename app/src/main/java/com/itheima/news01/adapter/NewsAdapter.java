package com.itheima.news01.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.news01.R;
import com.itheima.news01.bean.NewsEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yls on 2017/6/28.
 */

public class NewsAdapter extends BaseAdapter{
    private ImageView ivIcon;
    private Context context;
    private TextView tvTitle;
    private TextView tvSource;
    private TextView tvComment;
    private ImageView iv01;
    private ImageView iv02;
    private ImageView iv03;

    private List<NewsEntity.ResultBean> listDatas;

    public NewsAdapter(Context context, List<NewsEntity.ResultBean> listDatas){
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return (listDatas == null) ? 0:listDatas.size();
    }

    @Override
    public NewsEntity.ResultBean getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //列表项数据
        NewsEntity.ResultBean info = (NewsEntity.ResultBean)getItem(position);
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_TYPE_WITH_1_IMAGE){
            if (convertView == null){
                convertView = View.inflate(context, R.layout.item_news_1, null);
            }

            //查找列表项中的子控件
            ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            tvComment = (TextView) convertView.findViewById(R.id.tv_comment);

            //显示列表中的子控件
            tvTitle.setText(info.getTitle());
            tvSource.setText(info.getSource());
            tvComment.setText(info.getReplyCount() + "跟帖");
            Picasso.with(context).load(info.getImgsrc()).into(ivIcon);
        }else if(itemViewType == ITEM_TYPE_WITH_3_IMAGE){
            if (convertView == null){
                convertView = View.inflate(context, R.layout.item_news_2,null);
            }
            //查找列表中的子控件
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
            iv01 = (ImageView) convertView.findViewById(R.id.iv_01);
            iv02 = (ImageView) convertView.findViewById(R.id.iv_02);
            iv03 = (ImageView) convertView.findViewById(R.id.iv_03);

            tvTitle.setText(info.getTitle());
            tvComment.setText(info.getReplyCount() + "跟帖");
            try{
                Picasso.with(context).load(info.getImgsrc()).into(iv01);
                Picasso.with(context).load(info.getImgextra().get(0).getImgsrc()).into(iv02);
                Picasso.with(context).load(info.getImgextra().get(1).getImgsrc()).into(iv03);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return convertView;
    }
    private static final int ITEM_TYPE_WITH_1_IMAGE = 0;
    private static final int ITEM_TYPE_WITH_3_IMAGE = 1;

    @Override
    public int getItemViewType(int position) {
        NewsEntity.ResultBean item = getItem(position);
        if (item.getImgextra() == null || item.getImgextra().size() == 0){
            return ITEM_TYPE_WITH_1_IMAGE;
        }else {
            return ITEM_TYPE_WITH_3_IMAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
