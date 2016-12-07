package com.hsd.asmfsx.model;

import com.hsd.asmfsx.global.GetRetrofit;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2016/12/7.
 */

public class UploadMultiImgBiz3 {
    public void doUpload(List<String> imgs){
        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < imgs.size(); i++){
            File file = new File(imgs.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            paramsMap.put("img\";filename=\"img.png", requestBody);
        }
        GetRetrofit.getRetrofit().create(RetrofitService.class)
                .multiUpload(paramsMap)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }
}
