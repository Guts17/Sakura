package com.lsq.sakura.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsq.sakura.R;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private List<String> mEpisodes;
    private Context mContext;
    private OnItemClickCallBack mCallBack;

    public EpisodesAdapter(List<String> episodes,OnItemClickCallBack callBack){
        mEpisodes = episodes;
        mCallBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null){
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_episode,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_episode.setText("第" + i + "集");
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.playVideo(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEpisodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_episode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            tv_episode = itemView.findViewById(R.id.tv_episode);
        }
    }

    public interface OnItemClickCallBack{
        public void playVideo(int position);
    }

}
