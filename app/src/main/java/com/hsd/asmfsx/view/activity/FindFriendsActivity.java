package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.FindFriendsPresenter;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsActivity extends AppCompatActivity implements FindFriendsContract.View{

    private FindFriendsPresenter findFriendsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findfriends_layout);
        findFriendsPresenter = new FindFriendsPresenter(this);
        findFriendsPresenter.start();
    }

    @Override
    public FindFriendsBean getFindFriendsBean() {
        FindFriendsBean findFriendsBean = new FindFriendsBean();
        findFriendsBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        findFriendsBean.setSex("男");
        findFriendsBean.setFindFriend_pageNow(Integer.valueOf(1));
        findFriendsBean.setFindFriend_pageSize(Integer.valueOf(20));
        return findFriendsBean;
    }

    @Override
    public void showData(FindFriendsBean findFriendsBean) {
        String body = findFriendsBean.getBody();
        List<UserInformationBean> list = GetGson.getGson().fromJson(body, new TypeToken<List<UserInformationBean>>() {
        }.getType());
        Logger.d("" + list);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailed() {

    }
}
/**
 *  {
 "user": {
 "heartBeat": {
 "byHeartBeatCount": 2,
 "byHeartBeatUserIDSet": [
 18,
 11
 ],
 "byHeartBeatUserIDString": "18&11",
 "eachOtherHeartBeatCount": 0,
 "eachOtherHeartBeatSet": [],
 "heartBeatCount": 4,
 "heartBeatUserIDSet": [
 153,
 174,
 151,
 60
 ],
 "heartBeatUserIDString": "153&174&151&60",
 "heartBeat_ID": 2,
 "user": {
 "$ref": ".."
 }
 },
 "userInformation": {
 "$ref": ".."
 },
 "user_ID": 2,
 "user_password": "123456",
 "user_phone": "13721620125",
 "user_uuid": "e91ef90a-7e24-4019-8b96-5fb9f0414929"
 },
 "userInformation_ID": 2,
 "user_age_IOS": 20,
 "user_birthday": 846604800000,
 "user_height": 160,
 "user_icon": "http://www.liuxinkeji.com:8080/Server/UploadFile/Image/1/8/5/2f79e748-9990-4a2d-89a2-905f097762981.jpg",
 "user_locality": "河南 新乡",
 "user_nickname": "简单爱",
 "user_school": "河南师范大学",
 "user_sex": "男",
 "user_state": "单身"
 }
 */
