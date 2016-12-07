package com.hsd.asmfsx.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hsd.asmfsx.app.MyApplication;

/**
 * Created by sun on 2016/12/7.
 */

public class SPUtils {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor edit;
    private static SPUtils spUtils;
    private static String name;

    public static SPUtils getInstance(String name){
        if (spUtils == null)
            spUtils = new SPUtils();
        if (sp == null){
            sp = MyApplication.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        edit = sp.edit();
        return spUtils;
    }

    public void getSP(){
        if (sp == null){
            sp = MyApplication.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        edit = sp.edit();
    }
    public void putString(String key, String value){
        edit.putString(key, value);
        edit.commit();
    }
    public void putInt(String key, int value){
        edit.putInt(key, value);
        edit.commit();
    }
    public void putBoolean(String key, Boolean value){
        edit.putBoolean(key, value);
        edit.commit();
    }
    public String getString(String key){
        String string = sp.getString(key, "");
        return string;
    }
    public int getInt(String key){
        int anInt = sp.getInt(key, 0);
        return anInt;
    }
    public Boolean getBoolean(String key){
        boolean b = sp.getBoolean(key, false);
        return b;
    }
}
