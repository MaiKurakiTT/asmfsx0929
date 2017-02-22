package com.hsd.asmfsx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.Date2Star;
import com.hsd.asmfsx.utils.DateFormatUtils;
import com.hsd.asmfsx.utils.PickViewUtils;
import com.hsd.asmfsx.utils.ShowToast;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/11/21.
 */

public class SetUserInfoActivity extends BaseActivity implements SetAfterRegisterContract.View{
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.nametext)
    EditText nametext;
    @BindView(R.id.nameparent)
    RelativeLayout nameparent;
    @BindView(R.id.match_birthdaytext)
    TextView matchBirthdaytext;
    @BindView(R.id.birthdaytext)
    TextView birthdaytext;
    @BindView(R.id.birthdayparent)
    RelativeLayout birthdayparent;
    @BindView(R.id.match_startext)
    TextView matchStartext;
    @BindView(R.id.startext)
    TextView startext;
    @BindView(R.id.starparent)
    RelativeLayout starparent;
    @BindView(R.id.match_hometext)
    TextView matchHometext;
    @BindView(R.id.hometext)
    TextView hometext;
    @BindView(R.id.homeparent)
    RelativeLayout homeparent;
    @BindView(R.id.match_schooltext)
    TextView matchSchooltext;
    @BindView(R.id.schooltext)
    TextView schooltext;
    @BindView(R.id.schoolparent)
    RelativeLayout schoolparent;
    @BindView(R.id.match_statustext)
    TextView matchStatustext;
    @BindView(R.id.statustext)
    TextView statustext;
    @BindView(R.id.statusparent)
    RelativeLayout statusparent;
    @BindView(R.id.match_signtext)
    TextView matchSigntext;
    @BindView(R.id.signtext)
    TextView signtext;
    @BindView(R.id.signparent)
    RelativeLayout signparent;
    @BindView(R.id.okbut)
    AppCompatButton okbut;

    private String[] schoolItems = {"河南师范大学"};
    private String[] statusItems = {"单身", "失恋了", "恋爱ing"};
    private String nickName;
    private String birthdayString;
    private Long birthday;
    private String star;
    private String home;
    private String schoolString;
    private String statusString;
    private Integer status;
    private Integer schoolInt = new Integer(0);
    private String sign;
    private UserInformationBean2 userInformationBean;
    private UserInformationBean2 userInformationBean2;
    private SetAfterRegisterPresenter setAfterRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setuserinfo_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View();
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("设置信息");
        toolbarRighttext.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        okbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (TextUtils.isEmpty(nickName) || TextUtils.isEmpty(birthdayString) || TextUtils.isEmpty(star)
                        || TextUtils.isEmpty(home) || TextUtils.isEmpty(schoolString) || TextUtils.isEmpty(statusString)) {
                    ShowToast.show(SetUserInfoActivity.this, "请先完善信息");
                } else {
                    userInformationBean2 = new UserInformationBean2();
                    userInformationBean2.setNickname(nickName);
                    userInformationBean2.setBirthday(birthday);
                    userInformationBean2.setStar(star);
                    userInformationBean2.setLocality(home);
                    userInformationBean2.setSchool(schoolInt);
                    userInformationBean2.setState(status);
                    userInformationBean2.setSign(sign);
                    setAfterRegisterPresenter.start();
                }
            }
        });
    }

    private void initData() {
        userInformationBean = (UserInformationBean2) getIntent().getSerializableExtra("userInformationBean");
        setAfterRegisterPresenter = new SetAfterRegisterPresenter(this);
    }

    private void getData() {
        nickName = nametext.getText().toString();
        birthdayString = birthdaytext.getText().toString();
        if (!TextUtils.isEmpty(birthdaytext.getText().toString())) {
            birthday = DateFormatUtils.formatString2Date(birthdayString).getTime();
        }
        star = startext.getText().toString();
        home = hometext.getText().toString();
        schoolString = schooltext.getText().toString();
        if (schoolString.equals("河南师范大学")) {
            schoolInt = 0;
        }
        statusString = statustext.getText().toString();
        if (statusString.equals("单身")){
            status = 0;
        }else if (statusString.equals("失恋了")){
            status = 1;
        }else if (statusString.equals("恋爱ing")){
            status = 2;
        }
        sign = signtext.getText().toString();
    }

    private void setData2View() {
        if (userInformationBean != null) {
            nametext.setText("" + userInformationBean.getNickname());
            if (userInformationBean.getBirthday() != null) {
                birthdaytext.setText("" + DateFormatUtils.formatDate2StringSimple(new Date(userInformationBean.getBirthday())));
            }
            startext.setText("" + userInformationBean.getStar());
            hometext.setText("" + userInformationBean.getLocality());
            schoolInt = userInformationBean.getSchool();
            if (schoolInt == 0)
                schooltext.setText("河南师范大学");
            status = userInformationBean.getState();
            switch (status) {
                case 0:
                    statustext.setText("单身");
                    break;
                case 1:
                    statustext.setText("失恋了");
                    break;
                case 2:
                    statustext.setText("恋爱ing");
                    break;
            }
            if (TextUtils.isEmpty(userInformationBean.getSign())){
                signtext.setText("");
            }else {
                signtext.setText(userInformationBean.getSign());
            }
        }
        birthdayparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, birthdaytext).pickDate();
            }
        });
        starparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = birthdaytext.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    Date date = DateFormatUtils.formatString2Date(s);
                    if (date != null) {
                        startext.setText(Date2Star.date2Constellation(date) + "");
                    }
                } else {
                    ShowToast.show(SetUserInfoActivity.this, "要先设置生日~");
                }
            }
        });
        homeparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, hometext).pickProvince();
            }
        });
        schoolparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, schooltext).pickOther(schoolItems);
            }
        });
        statusparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, statustext).pickOther(statusItems);
            }
        });
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
    public UserInformationBean2 getUserInformationBean() {
        return userInformationBean2;
    }

    @Override
    public File getImgFile() {
        return null;
    }

    @Override
    public void showData(BaseBean2 baseBean) {
        if (baseBean != null){
            ShowToast.show(SetUserInfoActivity.this, "设置成功");
            Intent intent = getIntent();
            intent.putExtra("userInformationBean", userInformationBean2);
            setResult(0, intent);
            finish();
        }
    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean) {

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
}
