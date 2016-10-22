package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsBiz implements FindFriendsContract.IFindFriendsBiz{
    @Override
    public void doFindFriends(FindFriendsBean findFriendsBean, final OnFindFriendsListener findFriendsListener) {
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        Call<FindFriendsBean> call = service.postFindFriends(findFriendsBean);
        call.enqueue(new Callback<FindFriendsBean>() {
            @Override
            public void onResponse(Call<FindFriendsBean> call, Response<FindFriendsBean> response) {
                FindFriendsBean body = response.body();
                findFriendsListener.success(body);
            }

            @Override
            public void onFailure(Call<FindFriendsBean> call, Throwable t) {
                t.printStackTrace();
                Logger.d("" + t.toString());
                findFriendsListener.failed();
            }
        });
    }
}
