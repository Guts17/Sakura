package com.lsq.sakura.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lsq.sakura.R;
import com.lsq.sakura.activity.VideoActivity;
import com.lsq.sakura.bean.VideoInfo;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private List<VideoInfo> mVideoList;
    private Context mContext;

    public VideosAdapter(List<VideoInfo> videoList){
        this.mVideoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        if(mContext == null){
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoInfo videoInfo = mVideoList.get(viewHolder.getAdapterPosition());
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("videoinfo",videoInfo);
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        VideoInfo videoInfo = mVideoList.get(i);
        viewHolder.tv_video_title.setText(videoInfo.getTitle());
        viewHolder.tv_video_desc.setText(videoInfo.getDesc());
        Glide.with(mContext)
                .load(videoInfo.getPoster_url())
                .into(viewHolder.iv_video_poster);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iv_video_poster;
        TextView tv_video_title;
        TextView tv_video_desc;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            iv_video_poster = itemView.findViewById(R.id.iv_video_poster);
            tv_video_title = itemView.findViewById(R.id.tv_video_title);
            tv_video_desc = itemView.findViewById(R.id.tv_video_desc);
        }
    }
}
