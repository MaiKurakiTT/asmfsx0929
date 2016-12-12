package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginBiz implements LoginContract.ILoginBiz{


    @Override
    public void login(String userName, String passWord, final OnRequestListener<BaseBean2> requestListener) {
        GetRetrofit.
                getRetrofit2().
                create(RetrofitService.class).
                postLogin2(userName, passWord).
                enqueue(new Callback<BaseBean2>() {
                    @Override
                    public void onResponse(Call<BaseBean2> call, Response<BaseBean2> response) {
                        BaseBean2 body = response.body();
                        if (body != null){
                            if (body.getState() == 0){
                                requestListener.success(body);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean2> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d(t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }


}
/**
 *817574400000
 * java.text.ParseException: Failed to parse date ["817574400000']: Invalid time zone indicator '0' (at offset 0)
 */
