package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.OrderListBean;
import com.hsd.asmfsx.contract.GetOrderListContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/15.
 */

public class GetOrderListBiz implements GetOrderListContract.IGetOrderListBiz{
    @Override
    public void getList(int page, int limit, final OnRequestListener<List<OrderListBean>> requestListener) {
        GetRetrofit.getRetrofit2()
                .create(RetrofitService.class)
                .postGetOrderList(page, limit)
                .enqueue(new Callback<NormalResultBean<List<OrderListBean>>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<List<OrderListBean>>> call, Response<NormalResultBean<List<OrderListBean>>> response) {
                        NormalResultBean<List<OrderListBean>> body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                List<OrderListBean> json = body.getJson();
                                requestListener.success(json);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<List<OrderListBean>>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("获取订单列表异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
