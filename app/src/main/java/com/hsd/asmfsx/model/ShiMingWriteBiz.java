package com.hsd.asmfsx.model;

import android.text.TextUtils;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.ShiMingWriteContract;
import com.hsd.asmfsx.global.GetRetrofit;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2017/3/28.
 */

public class ShiMingWriteBiz implements ShiMingWriteContract.IShiMingWriteBiz{
    @Override
    public void doPut(final String realName, final Long schoolNum, final String img, final OnShiMingWriteListener shiMingWriteListener) {
        File file = new File(img);
        new UploadImgBiz2().uploadImg(file, new UploadImgBiz2.OnUploadListener() {
            @Override
            public void success(String[] urls) {
                if (!TextUtils.isEmpty(urls[0])){
                    GetRetrofit.getRetrofit2()
                            .create(RetrofitService.class)
                            .postWriteShiMing(realName, schoolNum, urls[0])
                            .enqueue(new Callback<BaseBean2>() {
                                @Override
                                public void onResponse(Call<BaseBean2> call, Response<BaseBean2> response) {
                                    BaseBean2 body = response.body();
                                    if (body != null){
                                        int state = body.getState();
                                        if (0 == state){
                                            shiMingWriteListener.success(body);
                                        }else {
                                            shiMingWriteListener.failedForResult(body);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseBean2> call, Throwable t) {
                                    shiMingWriteListener.failedForException(t);
                                }
                            });
                }
            }

            @Override
            public void failedForResult(BaseBean2 baseBean) {

            }

            @Override
            public void failedForException(Throwable t) {

            }
        });

    }
}
