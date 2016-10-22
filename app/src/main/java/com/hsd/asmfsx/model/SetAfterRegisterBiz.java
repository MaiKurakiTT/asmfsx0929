package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 紫荆 on 2016/10/22.
 * 注册后的设置用户信息业务
 */

public class SetAfterRegisterBiz implements SetAfterRegisterContract.ISetAfterRegisterBiz{
    @Override
    public void doSetInfo(UserInformationBean userInformationBean, final OnSetAfterRegisterListener setAfterRegisterListener) {
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        Call<UserInformationBean> call = service.postSetUserInfo(userInformationBean);
        call.enqueue(new Callback<UserInformationBean>() {
            @Override
            public void onResponse(Call<UserInformationBean> call, Response<UserInformationBean> response) {
                UserInformationBean body = response.body();
                setAfterRegisterListener.success(body);
            }

            @Override
            public void onFailure(Call<UserInformationBean> call, Throwable t) {
                t.printStackTrace();
                setAfterRegisterListener.failed();
                Logger.d("" + t.toString());
            }
        });
    }
}
