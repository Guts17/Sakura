package com.lsq.sakura.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    static SharedPreferences sp;

    /**
     * @param context  上下文
     * @param key      关键字
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void setInt(Context context, String key, int value) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
}
