package com.lsq.sakura.bean;

/**
 * Created by Administrator on 2018-12-01.
 */

/**
 * EventBus事件类型
 */
public class EventCode {

    /**
     * 验证用户
     */
    public static final int EVENT_VALIDATE_USER = 1000;
    /**
     * 获取app更新配置文件完成事件
     */
    public static final int EVENT_REQUEST_APPCONF_COMPLETED = 1001;
    /**
     * 获取app更新文件完成事件
     */
    public static final int EVENT_FILE_DOWNLOAD_COMPLETED = 1002;
    /**
     * 获取app更新文件开始事件
     */
    public static final int EVENT_FILE_DOWNLOAD_STARTED = 1003;
    /**
     * app更新完成事件
     */
    public static final int EVENT_FILE_UPDATE_COMPLETED = 1004;
    /**
     * 首页获取视频列表完成事件
     */
    public static final int EVENT_HOME_GETVIDEOS_COMPLETED = 1005;
    /**
     * 获取到视频的播放源地址
     */
    public static final int EVENT_GET_PLAYURL_COMPLETED = 1006;
    /**
     * 获取视频信息完成
     */
    public static final int EVENT_GET_VIDEODATA_COMPLETED = 1007;
    /**
     * 所有视频获取视频列表完成事件
     */
    public static final int EVENT_ALLVIDEOS_GETVIDEOS_COMPLETED = 1008;

}
