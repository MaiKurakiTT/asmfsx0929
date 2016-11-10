package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.FriendCirclePresenter;

import java.util.List;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleActivity extends AppCompatActivity implements FriendCircleContract.View{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendcircle_layout);
        FriendCirclePresenter friendCirclePresenter = new FriendCirclePresenter(this);
        friendCirclePresenter.start();
    }

    @Override
    public FriendCircleBean getFriendCircleBean() {
        FriendCircleBean bean = new FriendCircleBean();
        bean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        bean.setFriendsCircle_pageNow(1);
        bean.setFriendsCircle_pageSize(10);
        return bean;
    }

    @Override
    public void showData(BaseBean baseBean) {
        if (baseBean.getResultCode() == 1){
            String body = baseBean.getBody();
            List<FriendCircleBean> friendCircleList = GetGson.getGson().fromJson(body, new TypeToken<List<FriendCircleBean>>() {
            }.getType());
            String friendsCircle_nickname = friendCircleList.get(0).getFriendsCircle_nickname();
        }
    }

    @Override
    public void showMoreData(BaseBean baseBean) {

    }


    @Override
    public void showFailedForLoadMore() {

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
