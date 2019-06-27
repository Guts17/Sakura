package com.lsq.sakura.bean;

import java.io.Serializable;
import java.util.List;

public class VideoInfo implements Serializable {
    private String poster_url;//缩略图地址
    private String title;//标题
    private String desc;//集数描述
    private String video_url;//播放列表页面地址
    private List<String> play_urls;//视频源地址
    private String desc_detail;//详细描述
    private String alias_name;//别名
    private String region;//地区
    private String type;//类型
    private String years;//年代
    private String tag;//标签
    private String index;//索引
    private int score;//评分
    private String update_time;//更新时间
    private List<String> episodes;//剧集url
    private String js_src;//保存视频源地址的js文件地址

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public List<String> getPlay_urls() {
        return play_urls;
    }

    public void setPlay_urls(List<String> play_urls) {
        this.play_urls = play_urls;
    }

    public String getDesc_detail() {
        return desc_detail;
    }

    public void setDesc_detail(String desc_detail) {
        this.desc_detail = desc_detail;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<String> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<String> episodes) {
        this.episodes = episodes;
    }

    public String getJs_src() {
        return js_src;
    }

    public void setJs_src(String js_src) {
        this.js_src = js_src;
    }
}
