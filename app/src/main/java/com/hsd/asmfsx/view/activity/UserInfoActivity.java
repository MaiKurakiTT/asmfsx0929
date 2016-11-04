package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.utils.ShowToast;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2016/11/4.
 */

public class UserInfoActivity extends AppCompatActivity {
    @BindView(R.id.userimg)
    ImageView userImg;
    @BindView(R.id.name)
    TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
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
