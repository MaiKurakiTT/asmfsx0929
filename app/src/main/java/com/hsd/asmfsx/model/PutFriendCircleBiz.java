package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.PictureBean;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.contract.PutFriendCircleContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/11/14.
 */

public class PutFriendCircleBiz implements PutFriendCircleContract.IPutFriendCircleBiz{
    @Override
    public void doPut(final String content, List<String> imgs, final OnPutFriendCircleListener putFriendCircleListener) {
        if (imgs.size() > 0){
            new UploadMultiImgBiz2().doUpload(imgs, new UploadMultiImgBiz2.OnFinishListener() {
                @Override
                public void finished(List<String> pictures, final int failedCounts) {
                    putFC(content, pictures, failedCounts, putFriendCircleListener);
                }
            });
        }else {
            putFC(content, null, 0, putFriendCircleListener);
        }
    }

    private void putFC(String content, List<String> pictures, final int failedCounts, final OnPutFriendCircleListener putFriendCircleListener) {
        GetRetrofit.getRetrofit2().create(RetrofitService.class)
                .doPutFC(content, pictures)
                .enqueue(new Callback<BaseBean2>() {
                    @Override
                    public void onResponse(Call<BaseBean2> call, Response<BaseBean2> response) {
                        BaseBean2 body = response.body();
                        if (0 == body.getState()){
                            putFriendCircleListener.success(body, failedCounts);
                        }else {
                            putFriendCircleListener.failedForResult(body);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean2> call, Throwable t) {
                        t.printStackTrace();
                        Logger.d("发说说抛异常"+t.toString());
                        putFriendCircleListener.failedForException(t);
                    }
                });
    }


/*RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        Call<BaseBean> call = service.postPutFriendCircle(friendCircleBean);
        call.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean body = response.body();
                putFriendCircleListener.success(body, failedCounts);
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                t.printStackTrace();
                Logger.d("发布说说失败，" + t.toString());
                putFriendCircleListener.failed();
            }
        });*/

}
