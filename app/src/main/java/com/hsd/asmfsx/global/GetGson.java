package com.hsd.asmfsx.global;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by apple on 2016/10/14.
 */

public class GetGson {
    static Gson gson;

    public static Gson getGson(){
        if (gson == null){
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }
        return gson;
    }
}
