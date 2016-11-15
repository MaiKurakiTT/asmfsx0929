package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.PictureBean;
import com.hsd.asmfsx.contract.PutFriendCircleContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/11/14.
 */

public class PutFriendCircleBiz implements PutFriendCircleContract.IPutFriendCircleBiz{
    @Override
    public void doPut(List<String> imgs, final FriendCircleBean friendCircleBean, final OnPutFriendCircleListener putFriendCircleListener) {
        if (imgs.size() > 0){
            UploadMultiImgBiz uploadMultiImgBiz = new UploadMultiImgBiz();
            uploadMultiImgBiz.doUpload(imgs, new UploadMultiImgBiz.OnFinishListener() {
                @Override
                public void finished(List<PictureBean> pictures, final int failedCounts) {
                    put(friendCircleBean, pictures, failedCounts, putFriendCircleListener);
                }
            });
        }else {
            put(friendCircleBean, null, 0, putFriendCircleListener);
        }

    }

    private void put(FriendCircleBean friendCircleBean, List<PictureBean> pictures, final int failedCounts, final OnPutFriendCircleListener putFriendCircleListener) {
        friendCircleBean.setPictures(pictures);
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
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
        });
    }
}
