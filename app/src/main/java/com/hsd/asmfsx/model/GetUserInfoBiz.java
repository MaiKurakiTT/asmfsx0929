package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.GetUserInfoContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/6.
 */

public class GetUserInfoBiz implements GetUserInfoContract.IGetUserInfoBiz {
    @Override
    public void getUserInfo(Long userId, final OnRequestListener<UserInformationBean2> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postGetUserInfo(userId)
                .enqueue(new Callback<NormalResultBean<UserInformationBean2>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<UserInformationBean2>> call, Response<NormalResultBean<UserInformationBean2>> response) {
                        NormalResultBean<UserInformationBean2> body = response.body();
                        if (body != null) {
                            if (0 == body.getState()) {
                                requestListener.success(body.getJson());
                            } else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<UserInformationBean2>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
