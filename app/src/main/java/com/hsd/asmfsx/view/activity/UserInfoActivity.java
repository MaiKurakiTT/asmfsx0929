package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.utils.ShowToast;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2016/11/4.
 * 个人信息界面
 */

public class UserInfoActivity extends AppCompatActivity {
    @BindView(R.id.userimg)
    ImageView userImg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.first_fab)
    FloatingActionButton firstFab;
    @BindView(R.id.second_fab)
    FloatingActionButton secondFab;
    @BindView(R.id.third_fab)
    FloatingActionButton thirdFab;
    /**
     * 判断是从哪开启此activity的，0是个人中心，1是他人查看的个人信息
     */
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        if (type == 0) {
            firstFab.setVisibility(View.GONE);
            secondFab.setVisibility(View.GONE);
        } else {
            thirdFab.setVisibility(View.GONE);
        }
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 1);

        UserInformationBean userInformationBean = (UserInformationBean) getIntent().getSerializableExtra("userInformationBean");
        if (userInformationBean == null) {

        } else {
            String user_nickname = userInformationBean.getUser_nickname();
            String user_icon = userInformationBean.getUser_icon();
            name.setText(user_nickname);
            Glide.with(this).load(user_icon).into(userImg);
            ShowToast.show(UserInfoActivity.this, user_nickname);
        }
    }
}
