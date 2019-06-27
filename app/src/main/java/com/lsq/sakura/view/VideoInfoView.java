package com.lsq.sakura.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lsq.sakura.bean.VideoInfo;

public class VideoInfoView extends LinearLayout {
    public VideoInfoView(Context context) {
        super(context);
    }

    public VideoInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setVideoInfo(VideoInfo videoInfo){

    }
}
