package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleBiz implements FriendCircleContract.IFriendCircleBiz{
    @Override
    public void friendCircle(FriendCircleBean friendCircleBean, final OnFriendCircleListener friendCircleListener) {
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        Call<BaseBean> call = service.postFriendCircle(friendCircleBean);
        call.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean body = response.body();
                friendCircleListener.success(body);
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                t.printStackTrace();
                Logger.d("" + t.toString());
                friendCircleListener.failed();
            }
        });
    }
}
