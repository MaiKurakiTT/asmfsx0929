package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.SortBean;
import com.hsd.asmfsx.bean.UserInformationBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by apple on 16/10/7.
 * http://apicloud.mob.com/wx/article/category/query?key=15375df9cf9c3
 */

public interface HBService {
    @POST("/Server/MainServer?method=getHeartBeat2")
    Call<BaseBean> post(@Body BaseBean baseBean);
}
