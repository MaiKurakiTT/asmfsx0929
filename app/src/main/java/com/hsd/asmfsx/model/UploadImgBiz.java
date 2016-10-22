package com.hsd.asmfsx.model;

import android.os.Environment;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.global.GetRetrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/10/20.
 */

public class UploadImgBiz implements IUploadImgBiz{
    @Override
    public void doUpload(String fileUri, OnUploadImgListener uploadImgListener) {
        File file = new File(Environment.getExternalStorageDirectory(), "img.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), file);
        MultipartBody.Part img = MultipartBody.Part.createFormData("img", "img.jpg", requestBody);
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        Call<BaseBean> call = service.uploadImg(img);
        call.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean body = response.body();
                String describe = body.getDescribe();
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
