package com.lsq.sakura.utils;


import com.lsq.sakura.bean.EventMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018-12-01.
 */

public class EventBusUtils {

    /**
     * 注册EventBus
     * @param subscriber
     */
    public static void registerEventBus(Object subscriber){
        EventBus eventBus = EventBus.getDefault();
        if(!eventBus.isRegistered(subscriber)){
            eventBus.register(subscriber);
        }
    }

    /**
     * 注销EventBus
     * @param subscriber
     */
    public static void unregisterEventBus(Object subscriber){
        EventBus eventBus = EventBus.getDefault();
        if(eventBus.isRegistered(subscriber)){
            eventBus.unregister(subscriber);
        }
    }

    /**
     * 发送事件
     * @param eventMessage
     */
    public static void postEvent(EventMessage eventMessage){
        EventBus.getDefault().post(eventMessage);
    }

    public static void postStickyEvent(EventMessage eventMessage){
        EventBus.getDefault().postSticky(eventMessage);
    }
}
