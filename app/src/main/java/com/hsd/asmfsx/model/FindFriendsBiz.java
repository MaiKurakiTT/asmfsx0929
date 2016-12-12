package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsBiz implements FindFriendsContract.IFindFriendsBiz{
    @Override
    public void doFindFriends(int page, int limit, final OnRequestListener<List<UserInformationBean2>> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postFindFriends(page, limit)
                .enqueue(new Callback<NormalResultBean<List<UserInformationBean2>>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<List<UserInformationBean2>>> call, Response<NormalResultBean<List<UserInformationBean2>>> response) {
                        NormalResultBean<List<UserInformationBean2>> body = response.body();
                        if (body != null) {
                            if (0 == body.getState()) {
                                requestListener.success(body.getJson());
                            } else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<List<UserInformationBean2>>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d(t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
