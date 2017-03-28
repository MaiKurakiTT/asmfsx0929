package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShiMingBean;
import com.hsd.asmfsx.contract.GetShiMingStateContract;
import com.hsd.asmfsx.global.GetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2017/3/28.
 */

public class GetShiMingStateBiz implements GetShiMingStateContract.IGetShiMingStateBiz{
    @Override
    public void doGetShiMingState(final OnGetShiMingStateListener getShiMingStateListener) {
        GetRetrofit.getRetrofit2()
                .create(RetrofitService.class)
                .postGetShiMingState()
                .enqueue(new Callback<NormalResultBean<ShiMingBean>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<ShiMingBean>> call, Response<NormalResultBean<ShiMingBean>> response) {
                        NormalResultBean<ShiMingBean> body = response.body();
                        if (body != null){
                            if (body.getState() == 0){
                                getShiMingStateListener.success(body);
                            }else {
                                getShiMingStateListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<ShiMingBean>> call, Throwable t) {
                        getShiMingStateListener.failedForException(t);
                    }
                });
    }
}
