package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.cardviewpager.CardPagerAdapter;
import com.hsd.asmfsx.cardviewpager.ShadowTransformer;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.FindFriendsPresenter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsActivity extends AppCompatActivity implements FindFriendsContract.View {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.changebut)
    Button changebut;
    private FindFriendsPresenter findFriendsPresenter;
    private List<UserInformationBean> mList;
    private CardPagerAdapter cardPagerAdapter;
    private ShadowTransformer shadowTransformer;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findfriends_layout);
        ButterKnife.bind(this);
        initView();
        findFriendsPresenter = new FindFriendsPresenter(this);
        findFriendsPresenter.start();

        changebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriendsPresenter.loadMoreData();
            }
        });

    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public FindFriendsBean getFindFriendsBean() {
        FindFriendsBean findFriendsBean = new FindFriendsBean();
        findFriendsBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        findFriendsBean.setSex("男");
        findFriendsBean.setFindFriend_pageNow(Integer.valueOf(1));
        findFriendsBean.setFindFriend_pageSize(Integer.valueOf(10));
        return findFriendsBean;
    }

    @Override
    public void showData(FindFriendsBean findFriendsBean) {
        String body = findFriendsBean.getBody();
        List<UserInformationBean> list = GetGson.getGson().fromJson(body, new TypeToken<List<UserInformationBean>>() {
        }.getType());
        Logger.d("" + list);

        mList = list;

        cardPagerAdapter = new CardPagerAdapter(this, mList);
        shadowTransformer = new ShadowTransformer(viewpager, cardPagerAdapter);

        viewpager.setAdapter(cardPagerAdapter);
        viewpager.setPageTransformer(false, shadowTransformer);
        viewpager.setOffscreenPageLimit(3);
    }

    @Override
    public void showMoreData(FindFriendsBean findFriendsBean) {
        String body = findFriendsBean.getBody();
        List<UserInformationBean> list = GetGson.getGson().fromJson(body, new TypeToken<List<UserInformationBean>>() {
        }.getType());
        mList = list;
        viewpager.removeAllViews();
        cardPagerAdapter = new CardPagerAdapter(FindFriendsActivity.this, mList);
        shadowTransformer = new ShadowTransformer(viewpager, cardPagerAdapter);
        viewpager.setAdapter(cardPagerAdapter);
        viewpager.setPageTransformer(false, shadowTransformer);
        viewpager.setOffscreenPageLimit(3);
    }

    @Override
    public void showFailedForLoadMore() {

    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showFailed() {

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
/**
 * {
 * "user": {
 * "heartBeat": {
 * "byHeartBeatCount": 2,
 * "byHeartBeatUserIDSet": [
 * 18,
 * 11
 * ],
 * "byHeartBeatUserIDString": "18&11",
 * "eachOtherHeartBeatCount": 0,
 * "eachOtherHeartBeatSet": [],
 * "heartBeatCount": 4,
 * "heartBeatUserIDSet": [
 * 153,
 * 174,
 * 151,
 * 60
 * ],
 * "heartBeatUserIDString": "153&174&151&60",
 * "heartBeat_ID": 2,
 * "user": {
 * "$ref": ".."
 * }
 * },
 * "userInformation": {
 * "$ref": ".."
 * },
 * "user_ID": 2,
 * "user_password": "123456",
 * "user_phone": "13721620125",
 * "user_uuid": "e91ef90a-7e24-4019-8b96-5fb9f0414929"
 * },
 * "userInformation_ID": 2,
 * "user_age_IOS": 20,
 * "user_birthday": 846604800000,
 * "user_height": 160,
 * "user_icon": "http://www.liuxinkeji.com:8080/Server/UploadFile/Image/1/8/5/2f79e748-9990-4a2d-89a2-905f097762981.jpg",
 * "user_locality": "河南 新乡",
 * "user_nickname": "简单爱",
 * "user_school": "河南师范大学",
 * "user_sex": "男",
 * "user_state": "单身"
 * }
 */
