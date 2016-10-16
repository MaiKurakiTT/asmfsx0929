package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.global.GlobalParameter;

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
        }else if (userName == "" && passWord == "" && uuid != "" && uuid != null){
            loginByUuid(uuid, loginListener);
        }else {

        }

    }

    private void loginByUuid(String uuid, OnLoginListener loginListener) {

    }

    private void loginByUserName(String userName, String passWord, final OnLoginListener loginListener) {
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(GlobalParameter.ip)
                                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        LoginBean loginBean = new LoginBean();
        loginBean.setUser_phone(userName);
        loginBean.setUser_password(passWord);
        Call<LoginBean> call = loginService.post(loginBean);
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
