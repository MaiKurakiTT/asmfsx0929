package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by apple on 2016/10/16.
 */

public interface RetrofitService {

    @POST("/Server/MainServer?method=login")
    Call<LoginBean> postLogin(@Body LoginBean loginBean);

    @POST("/Server/MainServer?method=getHeartBeat2")
    Call<BaseBean> postHB(@Body BaseBean baseBean);


}
