package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/5.
 */

public class UploadImgBiz2 {
    public interface OnUploadListener {
        void success(NormalResultBean resultBean);
        void failed();
    }
    public void uploadImg(File file, final OnUploadListener uploadListener){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part img = MultipartBody.Part.createFormData("img", "img.png", requestBody);
        RetrofitService service = GetRetrofit.getRetrofit2().create(RetrofitService.class);
        service.uploadImg2(img).enqueue(new Callback<NormalResultBean<String[]>>() {
            @Override
            public void onResponse(Call<NormalResultBean<String[]>> call, Response<NormalResultBean<String[]>> response) {
                NormalResultBean<String[]> body = response.body();
                uploadListener.success(body);
            }

            @Override
            public void onFailure(Call<NormalResultBean<String[]>> call, Throwable t) {
                t.printStackTrace();
                Logger.d(t.toString());
                uploadListener.failed();
            }
        });
    }
}
