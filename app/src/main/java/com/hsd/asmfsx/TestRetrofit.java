package com.hsd.asmfsx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.SortBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.global.GlobalParameter;
import com.hsd.asmfsx.model.IRequestHeartBeatBiz;
import com.hsd.asmfsx.model.RequestHeartBeatBiz;
import com.hsd.asmfsx.model.RetrofitService;
import com.hsd.asmfsx.model.TestGet;
import com.hsd.asmfsx.global.GetGson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 16/10/7.
 * http://www.liuxinkeji.com:8080/Server/MainServer?method=getHeartBeat2
 */

/**
 * {"body":"{\"2\":[{\"user\":{\"heartBeat\":{\"byHeartBeatCount\":10,\"byHeartBeatUserIDSet\":[64,158,189,95,10,147,41,182,29,166],\"byHeartBeatUserIDString\":\"64&158&189&95&10&147&41&182&29&166\",\"eachOtherHeartBeatCount\":1,\"eachOtherHeartBeatCountString\":\"29\",\"eachOtherHeartBeatSet\":[29],\"heartBeatCount\":3,\"heartBeatUserIDSet\":[2,8,29],\"heartBeatUserIDString\":\"2&8&29\",\"heartBeat_ID\":18,\"user\":{\"$ref\":\"..\"}},\"student_ID\":\"1428524006\",\"userInformation\":{\"$ref\":\"..\"},\"user_ID\":18,\"user_password\":\"19950823\",\"user_phone\":\"15225485020\",\"user_uuid\":\"2e5fc842-b50e-4f87-ae61-c326f738bc14\"},\"userInformation_ID\":18,\"user_age_IOS\":21,\"user_birthday\":809107200000,\"user_department\":\"\",\"user_height\":160,\"user_icon\":\"http://www.liuxinkeji.com:8080/Server/UploadFile/Image/0/0/2/98d9a8cb-4e04-4136-8b03-fa470eb9cc581.jpg\",\"user_locality\":\"未设定 | 未设定 | 未设定\",\"user_nickname\":\"加油小菇凉\",\"user_school\":\"河南师范大学\",\"user_sex\":\"女\",\"user_sign\":\"\",\"user_star\":\"处女座\",\"user_state\":\"单身\"}],\"1\":[{\"user\":{\"heartBeat\":{\"byHeartBeatCount\":6,\"byHeartBeatUserIDSet\":[102,204,49,9,182,29],\"byHeartBeatUserIDString\":\"102&204&49&9&182&29\",\"eachOtherHeartBeatCount\":0,\"eachOtherHeartBeatSet\":[],\"heartBeatUserIDSet\":[],\"heartBeat_ID\":3,\"user\":{\"$ref\":\"..\"}},\"student_ID\":\"123\",\"userInformation\":{\"$ref\":\"..\"},\"user_ID\":3,\"user_password\":\"xxxxxxxx\",\"user_phone\":\"2222222\",\"user_uuid\":\"0f5b66d7-e6b9-450a-8d1f-c6e30f9dd356\"},\"userInformation_ID\":3,\"user_birthday\":1462526958000,\"user_height\":175,\"user_icon\":\"http://www.liuxinkeji.com:8080/Server/UploadFile/Image/0/0/2/t2.jpg\",\"user_locality\":\"河南 新乡\",\"user_nickname\":\"ENYi\",\"user_phone\":\"123\",\"user_school\":\"河南师范大学\",\"user_sex\":\"女\",\"user_state\":\"单身\"},{\"$ref\":\"$.2[0]\"},{\"user\":{\"heartBeat\":{\"byHeartBeatCount\":9,\"byHeartBeatUserIDSet\":[275,50,49,10,56,41,182,29,166],\"byHeartBeatUserIDString\":\"275&50&49&10&56&41&182&29&166\",\"eachOtherHeartBeatCount\":2,\"eachOtherHeartBeatCountString\":\"10&41\",\"eachOtherHeartBeatSet\":[10,41],\"heartBeatCount\":2,\"heartBeatUserIDSet\":[10,41],\"heartBeatUserIDString\":\"10&41\",\"heartBeat_ID\":24,\"user\":{\"$ref\":\"..\"}},\"student_ID\":\"1528424018\",\"userInformation\":{\"$ref\":\"..\"},\"user_ID\":24,\"user_password\":\"123\",\"user_phone\":\"13569427571\",\"user_uuid\":\"938e0f88-47ce-493d-8704-23a6a2943370\"},\"userInformation_ID\":24,\"user_age_IOS\":20,\"user_birthday\":830534400000,\"user_height\":161,\"user_icon\":\"http://www.liuxinkeji.com:8080/Server/UploadFile/Image/4/9/8/16b9991c-02cb-4010-8dbf-1558d293819c1.jpg\",\"user_locality\":\"河南 | 新乡 | 红旗区\",\"user_nickname\":\"(° ﾛ °)✧˖°宝宝方了\",\"user_phone\":\"13569427571\",\"user_school\":\"河南师范大学\",\"user_sex\":\"女\",\"user_star\":\"金牛座\",\"user_state\":\"热恋ing\"},{\"user\":{\"heartBeat\":{\"byHeartBeatCount\":7,\"byHeartBeatUserIDSet\":[204,50,56,41,270,29,166],\"byHeartBeatUserIDString\":\"204&50&56&41&270&29&166\",\"eachOtherHeartBeatCount\":0,\"eachOtherHeartBeatSet\":[],\"heartBeatUserIDSet\":[],\"heartBeat_ID\":26,\"user\":{\"$ref\":\"..\"}},\"student_ID\":\"1428524050\",\"userInformation\":{\"$ref\":\"..\"},\"user_ID\":26,\"user_password\":\"1234567890.me\",\"user_phone\":\"15670560668\",\"user_uuid\":\"5f905fd5-3eca-4b17-8f68-3284f4389200\"},\"userInformation_ID\":26,\"user_age_IOS\":22,\"user_birthday\":771696000000,\"user_height\":160,\"user_icon\":\"http://www.liuxinkeji.com:8080/Server/UploadFile/Image/0/5/0/5e017fd2-35eb-4a02-8d50-d9f2b5b024fd1.jpg\",\"user_locality\":\"河南 新乡\",\"user_nickname\":\"啦啦啦\",\"user_phone\":\"15670560668\",\"user_school\":\"河南师范大学\",\"user_sex\":\"女\",\"user_star\":\"双子座\",\"user_state\":\"热恋ing\"},{\"user\":{\"heartBeat\":{\"byHeartBeatCount\":18,\"byHeartBeatUserIDSet\":[102,236,143,202,163,10,193,192,50,49,115,57,95,270,182,29,243,183],\"byHeartBeatUserIDString\":\"102&236&143&202&163&10&193&192&50&49&115&57&95&270&182&29&243&183\",\"eachOtherHeartBeatCount\":4,\"eachOtherHeartBeatCountString\":\"50&10&57&193\",\"eachOtherHeartBeatSet\":[50,10,57,193],\"heartBeatCount\":20,\"heartBeatUserIDSet\":[2,5,6,7,8,10,199,167,193,194,164,186,50,48,52,179,57,176,56,180],\"heartBeatUserIDString\":\"2&5&6&7&8&10&199&167&193&194&164&186&50&48&52&179&57&176&56&180\",\"heartBeat_ID\":11,\"user\":{\"$ref\":\"..\"}},\"student_ID\":\"1508283023\",\"userInformation\":{\"$ref\":\"..\"},\"user_ID\":11,\"user_password\":\"123456\",\"user_phone\":\"13283723713\",\"user_uuid\":\"30862bd9-0595-4b0e-a17e-f041615bafc7\"},\"userInformation_ID\":11,\"user_age_IOS\":26,\"user_birthday\":633456000000,\"user_department\":\"请选择你的学院\",\"user_height\":170,\"user_icon\":\"http://www.liuxinkeji.com:8080/Server/UploadFile/Image/6/4/2/9460e220-d7a8-4d5c-b328-803528e763c21.jpg\",\"user_locality\":\"河南安阳滑县\",\"user_nickname\":\"那e瞬间\",\"user_school\":\"河南师范大学\",\"user_sex\":\"女\",\"user_sign\":\"这个人很懒什么都没留下...\",\"user_star\":\"水瓶座\",\"user_state\":\"单身\"},{\"user\":{\"heartBeat\":{\"byHeartBeatCount\":10,\"byHeartBeatUserIDSet\":[50,49,175,52,43,10,41,270,29,166],\"byHeartBeatUserIDString\":\"50&49&175&52&43&10&41&270&29&166\",\"eachOtherHeartBeatCount\":0,\"eachOtherHeartBeatSet\":[],\"heartBeatCount\":0,\"heartBeatUserIDSet\":[],\"heartBeat_ID\":30,\"user\":{\"$ref\":\"..\"}},\"student_ID\":\"1428624067\",\"userInformation\":{\"$ref\":\"..\"},\"user_ID\":30,\"user_password\":\"18790539383\",\"user_phone\":\"18790539383\",\"user_uuid\":\"1d911250-d468-4848-bae3-3b0a06978679\"},\"userInformation_ID\":30,\"user_age_IOS\":20,\"user_birthday\":850924800000,\"user_department\":\"\",\"user_height\":162,\"user_icon\":\"http://www.liuxinkeji.com:8080/Server/UploadFile/Image/DefaultHeadImage.jpg\",\"user_locality\":\"河南 新乡\",\"user_nickname\":\"小师妹\",\"user_school\":\"河南师范大学\",\"user_sex\":\"女\",\"user_sign\":\"\",\"user_star\":\"射手座\",\"user_state\":\"单身\"}]}","byHeartBeatCount":1,"byHeartBeatUserIDSet":[18],"byHeartBeatUserIDString":"18","describe":"获取成功","eachOtherHeartBeatCount":1,"eachOtherHeartBeatCountString":"18","eachOtherHeartBeatSet":[18],"heartBeatCount":6,"heartBeatUserIDSet":[3,18,24,26,11,30],"heartBeatUserIDString":"3&18&24&26&11&30","heartBeat_ID":29,"resultCode":1}
 */
public class TestRetrofit extends AppCompatActivity {
    private String TAG = "TestRetrofit";
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_retrofit);
        gson = GetGson.getGson();
        re();

//        request();
//        getTest();
    }

    private void re() {
        RequestHeartBeatBiz requestHeartBeatBiz = new RequestHeartBeatBiz();
        requestHeartBeatBiz.requestData("84f4b998-17df-4997-8fc2-828f89aec37d", new IRequestHeartBeatBiz.OnRequestListener() {
            @Override
            public void success(List<UserInformationBean> userInformation) {

            }

            @Override
            public void failed() {

            }
        });
    }

    private void getTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://apicloud.mob.com")
                .build();
        TestGet testGet = retrofit.create(TestGet.class);
        Call<SortBean> call = testGet.get("15375df9cf9c3");
        call.enqueue(new Callback<SortBean>() {
            @Override
            public void onResponse(Call<SortBean> call, Response<SortBean> response) {
                    if (response.isSuccessful()){
                        SortBean body = response.body();
                        Log.d(TAG, "=====" + body.getResult());
                    }
            }

            @Override
            public void onFailure(Call<SortBean> call, Throwable t) {

            }
        })
        ;
    }

    private void request() {
        Retrofit retrofit = GetRetrofit.getRetrofit();
        RetrofitService service = retrofit.create(RetrofitService.class);
        BaseBean baseBean = new BaseBean();
        baseBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        Call<BaseBean> call = service.postHB(baseBean);
        call.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (response.isSuccessful()){
                    BaseBean body = response.body();
                    Log.d(TAG, "====" + body.getBody());
//                    BaseBean fromJson = gson.fromJson(response.body(), BaseBean.class);
                    String s = body.getBody();
                    Log.d(TAG, "body == " + s);
                    Gson gson1  =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                    Type typenew  = new TypeToken<Map<String, List<UserInformationBean>>>(){}.getType();
//                    Map<String, List<UserInformationBean>> stringListMap = gson.fromJson(s, typenew);
//                    Map<String, List<UserInformationBean>> stringListMap = JSONObject
//                            .parseObject(s, new TypeReference<Map<String, List<UserInformationBean>>>() {});
//                    Log.d(TAG, "ssss:" + stringListMap.get("1").get(0).getUser_icon());
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                    t.printStackTrace();
            }
        });

    }

}
