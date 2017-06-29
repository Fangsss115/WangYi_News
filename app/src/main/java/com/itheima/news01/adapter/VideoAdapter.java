package com.itheima.news01.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.news01.R;
import com.itheima.news01.bean.VideoEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by yls on 2017/6/29.
 */

public class VideoAdapter extends RecyclerView.Adapter {
    private Context context;

    /** 列表数据集合 */
    private List<VideoEntity.ResultBean> listDatas;

    public VideoAdapter(Context context, List<VideoEntity.ResultBean> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建列表项
        View item = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        //创建ViewHolder并返回
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        final VideoEntity.ResultBean video = listDatas.get(position);

        // 预加载缩略图
        Picasso.with(context).load(video.getCover()).into(holder.ivVideoImage);

        // 显示标题
        holder.tvVideoTitle.setText(listDatas.get(position).getTitle());
        // 显示视频播放时长
        String duration = video.getLength() / 60 + "分" + video.getLength() % 60 + "秒";
        holder.tvVideoDuration.setText(duration);
        // 显示播放次数
        holder.tvPlayCount.setText(video.getPlayCount() + "");

        // 点击列表项时，跳转进入视频播放详情界面
/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivVideoImage;
        private TextView tvVideoTitle;
        private TextView tvVideoDuration;
        private TextView tvPlayCount;
        private JCVideoPlayerStandard videoPlayer;


        public MyViewHolder(View itemView) {
            super(itemView);

            ivVideoImage = (ImageView) itemView.findViewById(R.id.iv_video_image);
            tvVideoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            tvVideoDuration = (TextView) itemView.findViewById(R.id.tv_video_duration);
            tvPlayCount = (TextView) itemView.findViewById(R.id.tv_play_count);
            videoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer_standard);
        }
    }
}
