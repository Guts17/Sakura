package com.lsq.sakura.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lsq.sakura.bean.EventMessage;
import com.lsq.sakura.utils.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isRegisterEventBus()){
            EventBusUtils.registerEventBus(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRegisterEventBus()){
            EventBusUtils.unregisterEventBus(this);
        }
    }

    /**
     * 是否需要注册EventBus
     * @return false-不需要    true-需要
     */
    protected boolean isRegisterEventBus(){
        return false;
    }

    /**
     * 接收到分发事件
     * @param eventMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage eventMessage){
    }

    /**
     * 接收到粘性分发事件
     * @param eventMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onReceiveStickyEvent(EventMessage eventMessage){
    }

}
