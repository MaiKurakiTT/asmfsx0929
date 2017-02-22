package com.hsd.asmfsx.view.activity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.chat.ChatActivity;
import com.hsd.asmfsx.contract.AddHBContract;
import com.hsd.asmfsx.contract.GetUserInfoContract;
import com.hsd.asmfsx.presenter.AddHBPresenter;
import com.hsd.asmfsx.presenter.GetUserInfoPresenter;
import com.hsd.asmfsx.utils.DateFormatUtils;
import com.hsd.asmfsx.utils.GetAgeFromDate;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2016/11/4.
 * 个人信息界面
 */

public class UserInfoActivity extends BaseActivity implements GetUserInfoContract.View, AddHBContract.View{
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
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.age)
    TextView ageTV;
    @BindView(R.id.star)
    TextView starTV;
    @BindView(R.id.school)
    TextView schoolTV;
    @BindView(R.id.match_info)
    TextView matchInfo;
    @BindView(R.id.match_status)
    TextView matchStatus;
    @BindView(R.id.status)
    TextView statusTV;
    @BindView(R.id.match_home)
    TextView matchHome;
    @BindView(R.id.home)
    TextView homeTV;
    @BindView(R.id.match_sign)
    TextView matchSign;
    @BindView(R.id.sign)
    TextView signTV;
    @BindView(R.id.nest)
    NestedScrollView nest;
    /**
     * 判断是从哪开启此activity的，0是个人中心，1是他人查看的个人信息
     */
    private int type;
    private UserInformationBean2 userInformationBean;
    private int ageInt;
    private Long userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View(userInformationBean);
    }

    private void setData2View(UserInformationBean2 userInformationBean) {
        if (userInformationBean == null) {

        } else {
            String nickname = userInformationBean.getNickname();
            String icon = userInformationBean.getIcon();
            name.setText(nickname);
            Glide.with(this).load(icon).into(userImg);
            Long birthdayLong = userInformationBean.getBirthday();
            if (birthdayLong != null) {
                ageInt = GetAgeFromDate.getAge(DateFormatUtils.formatLong2Date(birthdayLong));
            }
            ageTV.setText(ageInt + "岁");
            starTV.setText(userInformationBean.getStar() + "");
            Integer schoolInt = userInformationBean.getSchool();
            if (schoolInt != null) {
                switch (schoolInt) {
                    case 0:
                        schoolTV.setText("河南师范大学");
                        break;
                }
            }
            Integer stateInt = userInformationBean.getState();
            if (stateInt != null) {
                switch (stateInt) {
                    case 0:
                        statusTV.setText("单身");
                        break;
                    case 1:
                        statusTV.setText("失恋了");
                        break;
                    case 2:
                        statusTV.setText("恋爱ing");
                        break;
                }
            }
            homeTV.setText("" + userInformationBean.getLocality());
            if (TextUtils.isEmpty(userInformationBean.getSign())) {
                signTV.setText("这家伙很懒，什么都不填。");
            } else {
                signTV.setText("" + userInformationBean.getSign());
            }
        }
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
                //心动TA
                if (userID != null){
                    AddHBPresenter addHBPresenter = new AddHBPresenter(UserInfoActivity.this);
//                    addHBPresenter.start();
                }
                fabMenu.close(true);
            }
        });
        secondFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跟TA聊天
                if (userID != null){
                    Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
                    intent.putExtra("name", userID + "");
                    startActivity(intent);
                }
                fabMenu.close(true);
            }
        });
        thirdFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, SetUserInfoActivity.class);
                intent.putExtra("userInformationBean", userInformationBean);
                startActivityForResult(intent, 0);
                fabMenu.close(true);
            }
        });
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 1);
        if (type == 0) {
            userInformationBean = (UserInformationBean2) getIntent().getSerializableExtra("userInformationBean");
        }else {
            //从其他Activity传过来某人的userID，然后调用GetUserInfo查询并显示其信息
            userID = getIntent().getLongExtra("userID", 0);
            GetUserInfoPresenter getUserInfoPresenter = new GetUserInfoPresenter(this);
//            getUserInfoPresenter.start();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //从SetUserInfoActivity回传过来，更新界面信息
        if (requestCode == 0 && data != null) {
            UserInformationBean2 userInformationBean = (UserInformationBean2) data.getSerializableExtra("userInformationBean");
            if (userInformationBean != null) {
                setData2View(userInformationBean);
                Intent intent = getIntent();
                intent.putExtra("userInformationBean", userInformationBean);
                setResult(0, intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public Long getUserId() {
        return userID;
    }

    @Override
    public void showDataForUserInfo(UserInformationBean2 userInformationBean) {
        setData2View(userInformationBean);
    }

    @Override
    public void showFailedForUserInfoResult(BaseBean2 baseBean) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailedForException(Throwable t) {

    }

    @Override
    public Long getHBUserID() {
        return userID;
    }

    @Override
    public void showData(BaseBean2 baseBean2) {
        Snackbar.make(fabMenu, "已添加心动", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean2) {
        Snackbar.make(fabMenu, "添加心动失败, " + baseBean2.getMsg(), Snackbar.LENGTH_LONG).show();
    }
}
