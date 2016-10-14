package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.SortBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by apple on 16/10/8.
 */

public interface TestGet {
    @GET("/wx/article/category/query")
    Call<SortBean> get(@Query("key") String key);
}
