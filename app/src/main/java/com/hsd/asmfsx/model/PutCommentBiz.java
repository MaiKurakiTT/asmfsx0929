package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.PutCommentContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/11/15.
 */

public class PutCommentBiz implements PutCommentContract.IPutCommentBiz {
    @Override
    public void doPutComment(String content, Long friendCircleID, Long byUserID, final OnRequestListener<BaseBean2> requestListener) {
        GetRetrofit
                .getRetrofit2()
                .create(RetrofitService.class)
                .postFCComment(content, friendCircleID, byUserID)
                .enqueue(new Callback<BaseBean2>() {
                    @Override
                    public void onResponse(Call<BaseBean2> call, Response<BaseBean2> response) {
                        BaseBean2 body = response.body();
                        if (body != null) {
                            if (0 == body.getState()) {
                                requestListener.success(body);
                            } else {
                                requestListener.failedForResult(body);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean2> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("发评论异常" + t.toString());
                        requestListener.failedForException(t);
                    }
                });
    }

}
