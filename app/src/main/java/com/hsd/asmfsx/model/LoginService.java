package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by apple on 2016/10/16.
 */

public interface LoginService {
    @POST("/Server/MainServer?method=login")
    Call<LoginBean> post(@Body LoginBean loginBean);
}
