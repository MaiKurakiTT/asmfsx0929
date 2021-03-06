package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 紫荆 on 2016/10/22.
 * 注册后的设置用户信息业务
 */

public class SetAfterRegisterBiz implements SetAfterRegisterContract.ISetAfterRegisterBiz{

    private void uploadUserInfo(UserInformationBean2 userInformationBean, final OnRequestListener<BaseBean2> requestListener) {
        String s = GetGson.getGson().toJson(userInformationBean, UserInformationBean2.class);
        Logger.d(s);
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postSetUserInfo(userInformationBean)
                .enqueue(new Callback<BaseBean2>() {
                    @Override
                    public void onResponse(Call<BaseBean2> call, Response<BaseBean2> response) {
                        BaseBean2 body = response.body();
                        if (body != null) {
                            if (body.getState() == 0) {
                                requestListener.success(body);
                            } else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean2> call, Throwable t) {
                        requestListener.failedForException(t);
                    }
                });
    }

    @Override
    public void doSetInfo(File imgFile, final UserInformationBean2 userInformationBean, final OnRequestListener<BaseBean2> requestListener) {
        if (imgFile == null){
            uploadUserInfo(userInformationBean, requestListener);
        }else {
            UploadImgBiz2 uploadImgBiz = new UploadImgBiz2();
            uploadImgBiz.uploadImg(imgFile, new UploadImgBiz2.OnUploadListener() {
                @Override
                public void success(String[] urls) {
                    userInformationBean.setIcon(urls[0]);
                    uploadUserInfo(userInformationBean, requestListener);
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
}
