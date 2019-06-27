package com.lsq.sakura.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.lsq.sakura.R;
import com.lsq.sakura.adapter.EpisodesAdapter;
import com.lsq.sakura.base.BaseActivity;
import com.lsq.sakura.bean.EventCode;
import com.lsq.sakura.bean.EventMessage;
import com.lsq.sakura.bean.VideoInfo;
import com.lsq.sakura.utils.EventBusUtils;
import com.lsq.sakura.utils.VolleyHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends BaseActivity {

    private VideoInfo videoInfo;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private List<String> episodes = new ArrayList<>();
    private List<String> play_urls = new ArrayList<>();
    @BindView(R.id.tv_video_aliasname) TextView tv_video_aliasname;
    @BindView(R.id.tv_video_detail) TextView tv_video_detail;
    @BindView(R.id.tv_video_header) TextView tv_video_header;
    @BindView(R.id.tv_video_index) TextView tv_video_index;
    @BindView(R.id.tv_video_region) TextView tv_video_region;
    @BindView(R.id.tv_video_tag) TextView tv_video_tag;
    @BindView(R.id.tv_video_type) TextView tv_video_type;
    @BindView(R.id.tv_video_years) TextView tv_video_years;
    @BindView(R.id.iv_video_poster1) ImageView iv_video_poster1;
    private RequestQueue mRequestQueue;
    private EpisodesAdapter episodesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoinfo");
        playerView = findViewById(R.id.pv_video_play);
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        getVideoInfo(videoInfo.getVideo_url());

        final RecyclerView recyclerView = findViewById(R.id.rv_video_episodes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        episodesAdapter = new EpisodesAdapter(play_urls, new EpisodesAdapter.OnItemClickCallBack() {
            @Override
            public void playVideo(int position) {
                setPlaySource(play_urls.get(position));
            }
        });
        recyclerView.setAdapter(episodesAdapter);
    }

    public void getVideoInfo(final String videoUrl){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(videoUrl).get();
                    Element alex = doc.getElementsByClass("alex").first();
                    Elements spans = alex.getElementsByTag("span");

                    for(int i = 0;i < spans.size();i++){
                        Elements as = spans.get(i).getElementsByTag("a");
                        String text = "";
                        for(Element a:as){
                            text += a.text() + ",";
                        }
                        switch (i){
                            case 0:
                                videoInfo.setRegion(text);
                                break;
                            case 1:
                                videoInfo.setType(text);
                                break;
                            case 2:
                                videoInfo.setYears(text);
                                break;
                            case 3:
                                videoInfo.setTag(text);
                                break;
                            case 4:
                                videoInfo.setIndex(text);
                                break;
                        }
                    }
                    videoInfo.setAlias_name(alex.getElementsByTag("p").first().text());
                    videoInfo.setUpdate_time(alex.getElementsByTag("p").last().text());
                    videoInfo.setDesc_detail(doc.getElementsByClass("info").text());

                    Element movurl = doc.getElementsByClass("movurl").first();
                    Elements lis = movurl.getElementsByTag("li");
                    for(Element li:lis){
                        episodes.add(getString(R.string.base_url) + li.getElementsByTag("a").first().attr("href"));
                    }
                    videoInfo.setEpisodes(episodes);

                    EventBusUtils.postEvent(new EventMessage(EventCode.EVENT_GET_VIDEODATA_COMPLETED,"completed"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setVideoInfo(){
        tv_video_header.setText(videoInfo.getUpdate_time());
        Glide.with(this).load(videoInfo.getPoster_url()).into(iv_video_poster1);
        tv_video_aliasname.setText(videoInfo.getAlias_name());
        tv_video_index.setText("索引: " + videoInfo.getIndex());
        tv_video_region.setText("地区: " + videoInfo.getRegion());
        tv_video_type.setText("类型: " + videoInfo.getType());
        tv_video_tag.setText("标签: " + videoInfo.getTag());
        tv_video_years.setText("年份: " + videoInfo.getYears());
        tv_video_detail.setText(videoInfo.getDesc_detail());
    }

    public void getPlayUrls(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Element player = doc.getElementsByClass("player").first();
                    Element src = player.getElementsByTag("script").first();
                    videoInfo.setJs_src(getString(R.string.base_url) + src.attr("src"));

                    mRequestQueue = VolleyHelper.getInstance(VideoActivity.this).getRequestQueue();
                    mRequestQueue.add(new StringRequest(videoInfo.getJs_src(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Pattern p = Pattern.compile("http:\\S*?flv");
                            Matcher m = p.matcher(s);
                            while (m.find()){
                                play_urls.add(m.group().split("\\$")[0]);
                            }
                            videoInfo.setPlay_urls(play_urls);
                            EventBusUtils.postEvent(new EventMessage(EventCode.EVENT_GET_PLAYURL_COMPLETED,"completed"));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(VideoActivity.this,getString(R.string.net_error),Toast.LENGTH_SHORT).show();
                        }
                    }));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    public void setPlaySource(String url){
        //创建一个DataSource对象，通过它来下载多媒体数据
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,Util.getUserAgent(this, "sakura"));
        //这是一个代表将要被播放的媒体的MediaSource
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        //使用资源准备播放器
        player.prepare(videoSource);
    }

    @Override
    public void onReceiveEvent(EventMessage eventMessage) {
        super.onReceiveEvent(eventMessage);
        switch (eventMessage.getCode()){
            case EventCode.EVENT_GET_PLAYURL_COMPLETED:
                episodesAdapter.notifyDataSetChanged();
                setPlaySource(videoInfo.getPlay_urls().get(0));
                break;
            case EventCode.EVENT_GET_VIDEODATA_COMPLETED:
                setVideoInfo();
                getPlayUrls(episodes.get(0));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

}
