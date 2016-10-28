package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
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
    @Override
    public void doSetInfo(File imgFile, final UserInformationBean userInformationBean, final OnSetAfterRegisterListener setAfterRegisterListener) {
        UploadImgBiz uploadImgBiz = new UploadImgBiz();
            uploadImgBiz.uploadImg(imgFile, new UploadImgBiz.OnUploadListener() {
                @Override
                public void success(BaseBean baseBean) {
                    Logger.d("头像上传成功，URL = "+baseBean.getBody());
                    String headUrl = baseBean.getBody();
                    userInformationBean.setUser_icon(headUrl);
                    uploadUserInfo(userInformationBean, setAfterRegisterListener);
                }

                @Override
                public void failed() {
                    Logger.d("头像上传失败，只传其他资料");
                    uploadUserInfo(userInformationBean, setAfterRegisterListener);
                }
            });
    }

    private void uploadUserInfo(UserInformationBean userInformationBean, final OnSetAfterRegisterListener setAfterRegisterListener) {
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
