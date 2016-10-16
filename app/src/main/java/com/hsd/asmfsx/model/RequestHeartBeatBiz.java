package com.hsd.asmfsx.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.global.GlobalParameter;
import com.hsd.asmfsx.global.GetGson;

import java.util.List;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 16/9/29.
 *
 private String url = "http://www.liuxinkeji.com:8080/Server/MainServer?method=getHeartBeat2";
 private String uuid = "84f4b998-17df-4997-8fc2-828f89aec37d";
 */

public class RequestHeartBeatBiz implements IRequestHeartBeatBiz {
    public String TAG = "RequestHeartBeatBiz";
    @Override
    public void requestData(String uuid, final IRequestHeartBeatBiz.OnRequestListener requestListener){
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(GlobalParameter.ip)
                                        .addConverterFactory(GsonConverterFactory.create(GetGson.getGson()))
                                        .build();
        HBService hbService = retrofit.create(HBService.class);
        BaseBean baseBean = new BaseBean();
        baseBean.setUUID(uuid);
        retrofit2.Call<BaseBean> call = hbService.post(baseBean);
        call.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(retrofit2.Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean body = response.body();
                if (body.getResultCode() == 1) {
                    String s = body.getBody();
                    Log.d(TAG, "body = " + body.getBody());
                    /**
                     * 服务器返回的数据是一个Map<String, List<UserInformationBean>>
                     * 其中String是类型，1 为心动的人， 2 为被谁心动
                     * 这里需要使用fastJson来解析，使用Gson的话有时间格式化的问题
                     */
                    Map<String, List<UserInformationBean>> stringListMap = JSONObject
                            .parseObject(s, new TypeReference<Map<String, List<UserInformationBean>>>() {
                            });
                    //Map中  1为心动的人，2为被谁心动
                    List<UserInformationBean> userInformationBeen = stringListMap.get("1");
                    if (userInformationBeen != null) {
                        requestListener.success(userInformationBeen);
                    }else {
                        Log.d(TAG, "List<UserInformationBean>为空");
                        requestListener.failed();
                    }
                }else {
                    Log.d(TAG, "请求服务器出错， 返回码为 " + body.getResultCode());
                    requestListener.failed();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseBean> call, Throwable t) {
                t.printStackTrace();
                requestListener.failed();
            }
        });

    }



    /*BaseBean baseBean = new BaseBean();
        baseBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        final Gson gson = new Gson();
        String s = gson.toJson(baseBean);
        OkHttpUtils
                .postString()
                .url("http://www.liuxinkeji.com:8080/Server/MainServer?method=getHeartBeat2")
                .content(s)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        requestListener.failed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!response.equals("") && response != null) {
                            Log.d(TAG, "response == " + response);
                            BaseBean fromJson = gson.fromJson(response, BaseBean.class);
                            String body = fromJson.getBody();
                            Log.d(TAG, "body == " + body);
                            Map<String, List<UserInformationBean>> stringListMap = JSONObject.parseObject(body, new TypeReference<Map<String, List<UserInformationBean>>>() {
                            });
                            userInformation = stringListMap.get("1");
                        }
                        requestListener.success(userInformation);
                    }
                });*/
}
