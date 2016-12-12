package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.contract.GetItemGoodInfoContract;
import com.hsd.asmfsx.contract.GetShopGoodListContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/12.
 */

public class GetItemGoodInfoBiz implements GetItemGoodInfoContract.IGetItemGoodInfoBiz{
    @Override
    public void getGoodInfo(Long commodityID, final OnRequestListener<CommodityVO> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postGetItemGoodInfo(commodityID)
                .enqueue(new Callback<NormalResultBean<CommodityVO>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<CommodityVO>> call, Response<NormalResultBean<CommodityVO>> response) {
                        NormalResultBean<CommodityVO> body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                CommodityVO json = body.getJson();
                                requestListener.success(json);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<CommodityVO>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("请求商品抛异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }
}
