package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.LoginBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.utils.ShowToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginBiz implements LoginContract.ILoginBiz{
    @Override
    public void login(String userName, String passWord, final OnRequestListener<NormalResultBean<LoginBean2>> requestListener) {
        GetRetrofit.
                getRetrofit2().
                create(RetrofitService.class).
                postLogin2(userName, passWord)
                .enqueue(new Callback<NormalResultBean<LoginBean2>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<LoginBean2>> call, Response<NormalResultBean<LoginBean2>> response) {
                        NormalResultBean<LoginBean2> body = response.body();
                        if (body != null){
                            if (0 == body.getState()){
                                final LoginBean2 json = body.getJson();
                                EMClient.getInstance().login(json.getUserId() + "", "123", new EMCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        Logger.d(json.getUserId() + "聊天登录成功");
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        Logger.d(json.getUserId() + "聊天登录失败");
                                    }

                                    @Override
                                    public void onProgress(int i, String s) {

                                    }
                                });
                                requestListener.success(body);
                            }else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<LoginBean2>> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d(t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }


    /*@Override
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
*/

}
/**
 *817574400000
 * java.text.ParseException: Failed to parse date ["817574400000']: Invalid time zone indicator '0' (at offset 0)
 */
