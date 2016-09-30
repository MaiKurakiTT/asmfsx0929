package com.hsd.asmfsx.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by apple on 16/9/29.
 */

public class RequestHeartBeatBiz implements IRequestHeartBeatBiz {
    public String TAG = "RequestHeartBeatBiz";
    private List<UserInformationBean> userInformation;
    @Override
    public void requestData(String uuid, final IRequestHeartBeatBiz.OnRequestListener requestListener){
        BaseBean baseBean = new BaseBean();
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
                });
    }
}
