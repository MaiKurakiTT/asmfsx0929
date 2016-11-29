package com.hsd.asmfsx.global;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 2016/10/16.
 */

public class GetRetrofit {
    static Retrofit retrofit;
    static Retrofit retrofit2;
    static ClearableCookieJar cookieJar;
    static OkHttpClient okHttpClient;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                    .baseUrl(GlobalParameter.ip)
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofit2(Context context){
        ClearableCookieJar cookieJar = getCookieJar(context);
        OkHttpClient okHttpClient = getOkHttpClient(cookieJar);
        if (retrofit2 == null){
            retrofit2 = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                    .baseUrl(GlobalParameter.iip)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit2;
    }
    static ClearableCookieJar getCookieJar(Context context){
        if (cookieJar == null){
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        }
        return cookieJar;
    }
    static OkHttpClient getOkHttpClient(ClearableCookieJar cookieJar){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();
        }
        return okHttpClient;
    }
}
