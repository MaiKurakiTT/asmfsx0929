package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.AddHBContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/16.
 */

public class AddHBBiz implements AddHBContract.IAddHBBiz{
    @Override
    public void addHB(Long heartBeatUserID, final OnRequestListener<BaseBean2> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postAddHB(heartBeatUserID)
                .enqueue(new Callback<BaseBean2>() {
                    @Override
                    public void onResponse(Call<BaseBean2> call, Response<BaseBean2> response) {
                        BaseBean2 body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                requestListener.success(body);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean2> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("添加心动好友异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
