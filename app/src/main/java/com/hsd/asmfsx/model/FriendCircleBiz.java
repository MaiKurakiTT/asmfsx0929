package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.FriendCircleVO;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleBiz implements FriendCircleContract.IFriendCircleBiz{
    @Override
    public void friendCircle(int page, int limit, final OnRequestListener<List<FriendCircleVO>> requestListener) {
        GetRetrofit.getRetrofit2()
                .create(RetrofitService.class)
                .postGetFC(page, limit)
                .enqueue(new Callback<NormalResultBean<List<FriendCircleVO>>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<List<FriendCircleVO>>> call, Response<NormalResultBean<List<FriendCircleVO>>> response) {
                        NormalResultBean<List<FriendCircleVO>> body = response.body();
                        if (body != null) {
                            if (0 == body.getState()) {
                                requestListener.success(body.getJson());
                            } else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<List<FriendCircleVO>>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("请求朋友圈异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
