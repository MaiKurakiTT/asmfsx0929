package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.contract.GetShopListContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/12.
 */

public class GetShopListBiz implements GetShopListContract.IGetShopListBiz{
    @Override
    public void getShop(int page, int limit, final OnRequestListener<List<ShopVO>> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postGetShopList(page, limit)
                .enqueue(new Callback<NormalResultBean<List<ShopVO>>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<List<ShopVO>>> call, Response<NormalResultBean<List<ShopVO>>> response) {
                        NormalResultBean<List<ShopVO>> body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                List<ShopVO> json = body.getJson();
                                requestListener.success(json);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<List<ShopVO>>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("请求商家列表异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
