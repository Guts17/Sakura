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
import com.lsq.sakura.base.FragmentCallBack;
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
    private AllVideosCallBack mCallBack;

    public static AllVideosFragment newInstance(AllVideosCallBack callBack) {
        AllVideosFragment fragment = new AllVideosFragment();
        Bundle bundle = new Bundle();
//        bundle.putParcelable("CallBack",callBack);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allvideos, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_allvideos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        videosAdapter = new VideosAdapter(mVideoList);
        recyclerView.setAdapter(videosAdapter);

        swipeRefreshLayout = view.findViewById(R.id.sr_fresh_allvideos);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshVideos(getString(R.string.base_url));
            }
        });
        if (getArguments() != null) {
            mSearchUrl = getArguments().getString("SearchUrl", "");
            refreshVideos(mSearchUrl);
        }else {
            refreshVideos(getString(R.string.base_url));
        }

        return view;

    }

    public void refreshVideos(final String url) {
        mVideoList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Element element = doc.getElementsByClass("pics").first();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EventBusUtils.postEvent(new EventMessage(EventCode.EVENT_ALLVIDEOS_GETVIDEOS_COMPLETED, "completed"));
            }
        }).start();

    }


    public interface AllVideosCallBack extends FragmentCallBack {
        void getSearchVideos(String searchUrl);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage eventMessage) {
        super.onReceiveEvent(eventMessage);
        switch (eventMessage.getCode()) {
            case EventCode.EVENT_ALLVIDEOS_GETVIDEOS_COMPLETED:
                videosAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    public void setmSearchUrl(String mSearchUrl) {
        this.mSearchUrl = mSearchUrl;
    }
}
