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

public class UploadImgByRetrofitBiz implements IUploadImgBiz{
    @Override
    public void doUpload(String fileUri, OnUploadImgListener uploadImgListener) {
        File file = new File(Environment.getExternalStorageDirectory(), "icon.jpg");
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder().addPart(body);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), file);
        MultipartBody.Part part = MultipartBody.Part.create(body);
//        MultipartBody.Part img = MultipartBody.Part.createFormData("img", "icon.jpg", requestBody);
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        Call<BaseBean> call = service.uploadImg(part);
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
