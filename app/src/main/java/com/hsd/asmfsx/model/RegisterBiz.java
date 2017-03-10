package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.contract.RegisterContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/10/20.
 */

public class RegisterBiz implements RegisterContract.IRegisterBiz{
    @Override
    public void doRegister(String schoolNumber, String stuId, String username, String password, final OnRequestListener<BaseBean2> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postRegister2(schoolNumber, stuId, username, password)
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
                        t.printStackTrace();
                        Logger.d("" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }

    /*@Override
    public void doRegister(String stuId, String username, String password, final OnRegisterListener registerListener) {
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        RegisterBean registerBean = new RegisterBean();
        registerBean.setStudent_ID(stuId);
        registerBean.setUser_phone(username);
        registerBean.setUser_password(password);
        Call<RegisterBean> call = service.postRegister(registerBean);
        call.enqueue(new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                RegisterBean bean = response.body();
                if (bean != null){
                    registerListener.success(bean);
                }else {
                    Logger.d("RegisterBean空了");
                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                Logger.d("请求注册抛异常：" + t.toString());
                t.printStackTrace();
                registerListener.failed();
            }
        });
    }*/


}
