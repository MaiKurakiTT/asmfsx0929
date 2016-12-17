package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.contract.HBListContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/16.
 */

public class HBListBiz implements HBListContract.IHBListBiz{
    @Override
    public void getHBList(final OnRequestListener<List<HBListBean>> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postGetHBList()
                .enqueue(new Callback<NormalResultBean<List<HBListBean>>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<List<HBListBean>>> call, Response<NormalResultBean<List<HBListBean>>> response) {
                        NormalResultBean<List<HBListBean>> body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                List<HBListBean> json = body.getJson();
                                requestListener.success(json);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<List<HBListBean>>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("心动列表异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
