package com.hsd.asmfsx.global;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 2016/10/16.
 */

public class GetRetrofit {
    static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                    .baseUrl(GlobalParameter.ip)
                    .build();
        }
        return retrofit;
    }
}
