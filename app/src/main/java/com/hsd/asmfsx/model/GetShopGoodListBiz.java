package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.contract.GetShopGoodListContract;
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

public class GetShopGoodListBiz implements GetShopGoodListContract.IGetShopGoodListBiz{
    @Override
    public void getGoodList(Long shopID, int page, int limit, final OnRequestListener<List<CommodityVO>> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postGetShopGoodList(shopID, page, limit)
                .enqueue(new Callback<NormalResultBean<List<CommodityVO>>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<List<CommodityVO>>> call, Response<NormalResultBean<List<CommodityVO>>> response) {
                        NormalResultBean<List<CommodityVO>> body = response.body();
                        if (body != null){
                            if (body.getState() == 0){
                                List<CommodityVO> json = body.getJson();
                                requestListener.success(json);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<List<CommodityVO>>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("请求商家商品列表异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
