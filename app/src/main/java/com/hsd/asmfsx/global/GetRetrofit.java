package com.hsd.asmfsx.global;



import android.content.Context;

import com.hsd.asmfsx.app.MyApplication;
import com.hsd.asmfsx.model.cookie.CookieManger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 2016/10/16.
 */

public class GetRetrofit {
    static Retrofit retrofit;
    static Retrofit retrofit2;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                    .baseUrl(GlobalParameter.iip)
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofit3(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                    .baseUrl(GlobalParameter.iip)
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofit2(){
        if (retrofit2 == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieManger(MyApplication.getAppContext()))
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .build();
            retrofit2 = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                    .baseUrl(GlobalParameter.iip)
                    .build();
        }
        return retrofit2;
    }
}
