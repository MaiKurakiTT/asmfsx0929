package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.OrderListBean;
import com.hsd.asmfsx.bean.OrderVO;
import com.hsd.asmfsx.contract.GetOrderInfoContract;
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

public class GetOrderInfoBiz implements GetOrderInfoContract.IGetOrderInfoBiz{
    @Override
    public void getOrder(Long orderId, final OnRequestListener<OrderVO> requestListener) {
        GetRetrofit.getRetrofit2()
                .create(RetrofitService.class)
                .postGetOrder(orderId)
                .enqueue(new Callback<NormalResultBean<OrderVO>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<OrderVO>> call, Response<NormalResultBean<OrderVO>> response) {
                        NormalResultBean<OrderVO> body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                OrderVO json = body.getJson();
                                requestListener.success(json);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<OrderVO>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("获取订单信息异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
