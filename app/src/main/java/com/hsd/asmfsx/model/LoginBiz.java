package com.hsd.asmfsx.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.global.GlobalParameter;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginBiz implements ILoginBiz{
    private String userName = "";
    private String passWord = "";
    private String uuid = "";

    @Override
    public void login(String userName, String passWord, String uuid, OnLoginListener loginListener) {
        this.userName = userName;
        this.passWord = passWord;
        this.uuid = uuid;
        if (userName != "" && userName != null && passWord != ""){
            loginByUserName(userName, passWord, loginListener);
        }else if (userName == "" || userName == null && uuid != "" && uuid != null){
            loginByUuid(uuid, loginListener);
        }else {

        }

    }

    private void loginByUuid(String uuid, final OnLoginListener loginListener) {
        Retrofit retrofit = GetRetrofit.getRetrofit();
        RetrofitService service = retrofit.create(RetrofitService.class);
        LoginBean loginBean = new LoginBean();
        loginBean.setUUID(uuid);
        Call<LoginBean> call = service.postLogin(loginBean);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean body = response.body();
                loginListener.success(body);
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                loginListener.failed();
            }
        });
    }

    private void loginByUserName(String userName, String passWord, final OnLoginListener loginListener) {
        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                                .baseUrl(GlobalParameter.ip)
                                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        LoginBean loginBean = new LoginBean();
        loginBean.setUser_phone(userName);
        loginBean.setUser_password(passWord);
        Call<LoginBean> call = service.postLogin(loginBean);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean body = response.body();
                loginListener.success(body);
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                loginListener.failed();
            }
        });
    }
}
/**
 *817574400000
 * java.text.ParseException: Failed to parse date ["817574400000']: Invalid time zone indicator '0' (at offset 0)
 */
