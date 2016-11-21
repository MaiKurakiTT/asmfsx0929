package com.hsd.asmfsx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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
    @BindView(R.id.userinfo_toolbar)
    Toolbar userinfoToolbar;
    @BindView(R.id.fab_menu)
    FloatingActionMenu fabMenu;
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
        setSupportActionBar(userinfoToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (type == 0) {
            firstFab.setVisibility(View.GONE);
            secondFab.setVisibility(View.GONE);
        } else {
            thirdFab.setVisibility(View.GONE);
        }
        firstFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
            }
        });
        secondFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
            }
        });
        thirdFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, SetUserInfoActivity.class);
                startActivityForResult(intent, 0);
                fabMenu.close(true);
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
