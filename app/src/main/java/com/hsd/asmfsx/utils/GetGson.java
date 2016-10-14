package com.hsd.asmfsx.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by apple on 2016/10/14.
 */

public class GetGson {
    public Gson tempGson;

    public static Gson getGson(){
        Gson gson = new GetGson().get();
        if (gson == null){
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }
        return gson;
    }

    public Gson get(){
        return tempGson;
    }
}
