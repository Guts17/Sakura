package com.lsq.sakura.bean;

/**
 * Created by Administrator on 2018-12-01.
 */

/**
 * EventBus消息对象
 * @param <T> T-消息中数据类型
 */
public class EventMessage<T> {
    private int code;
    private T data;

    public EventMessage(int code){
        this.code = code;
    }

    public EventMessage(int code,T data){
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
