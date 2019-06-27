package com.lsq.sakura.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHelper {
    private RequestQueue mRequestQueue;
    private Context mContext;
    private static VolleyHelper mInstance;

    private VolleyHelper(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized VolleyHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleyHelper(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req){
        mRequestQueue.add(req);
    }
}
