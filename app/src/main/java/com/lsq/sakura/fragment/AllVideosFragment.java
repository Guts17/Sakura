package com.lsq.sakura.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsq.sakura.R;
import com.lsq.sakura.adapter.VideosAdapter;
import com.lsq.sakura.base.BaseFragment;
import com.lsq.sakura.bean.EventCode;
import com.lsq.sakura.bean.EventMessage;
import com.lsq.sakura.bean.VideoInfo;
import com.lsq.sakura.utils.EventBusUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AllVideosFragment extends BaseFragment {

    private String mSearchUrl;
    private List<VideoInfo> mVideoList = new ArrayList<>();
    private VideosAdapter videosAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static AllVideosFragment newInstance(String searchUrl){
        AllVideosFragment fragment = new AllVideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SearchUrl",searchUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
//            mSearchUrl = getArguments().getString("SearchUrl","");
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allvideos, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_videos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        videosAdapter = new VideosAdapter(mVideoList);
        recyclerView.setAdapter(videosAdapter);

        swipeRefreshLayout = view.findViewById(R.id.sr_fresh_allvideos);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshVideos();
            }
        });
        refreshVideos();

        return view;

    }

    public void refreshVideos() {
        mVideoList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(getString(R.string.base_url)).get();
                    Elements elements = doc.getElementsByClass("imgs");
                    for (Element element : elements) {
                        Elements liElements = element.getElementsByTag("li");
                        for (Element li : liElements) {
                            Element a = li.select("a").first();
                            Element img = li.select("img").first();
                            Element p = li.select("p").last();
                            VideoInfo videoInfo = new VideoInfo();
                            videoInfo.setTitle(img.attr("alt"));
                            videoInfo.setPoster_url(img.attr("src"));
                            videoInfo.setVideo_url(getString(R.string.base_url) + a.attr("href"));
                            videoInfo.setDesc(p.text());
                            mVideoList.add(videoInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EventBusUtils.postEvent(new EventMessage(EventCode.EVENT_ALLVIDEOS_GETVIDEOS_COMPLETED,"completed"));
            }
        }).start();

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage eventMessage) {
        super.onReceiveEvent(eventMessage);
        switch (eventMessage.getCode()){
            case EventCode.EVENT_ALLVIDEOS_GETVIDEOS_COMPLETED:
                break;
        }
    }
}
