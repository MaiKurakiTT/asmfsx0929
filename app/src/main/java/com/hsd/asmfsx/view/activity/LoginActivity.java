package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.db.DbBean;
import com.hsd.asmfsx.db.DbBeanHelper;
import com.hsd.asmfsx.db.DbUtil;
import com.hsd.asmfsx.presenter.LoginPresenter;
import com.hsd.asmfsx.utils.NetworkUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    @BindView(R.id.usernametext)
    TextInputEditText usernametext;
    @BindView(R.id.usernameinput)
    TextInputLayout usernameinput;
    @BindView(R.id.passwordtext)
    TextInputEditText passwordtext;
    @BindView(R.id.passwordinput)
    TextInputLayout passwordinput;
    @BindView(R.id.login_but)
    AppCompatButton loginBut;
    @BindView(R.id.register_link)
    TextView registerLink;
    private String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;
    private DbBeanHelper driverHelper;
    private String username;
    private String password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在登录...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void initData() {
        loginPresenter = new LoginPresenter(this);
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        if (NetworkUtils.isNetworkAvailable(LoginActivity.this)){
            username = usernameinput.getEditText().getText().toString();
            password = passwordinput.getEditText().getText().toString();
            if (TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
                ShowToast.show(LoginActivity.this, "手机号或密码为空");
            }else {
                loginPresenter.start();
            }
        }else {
            ShowToast.show(LoginActivity.this, "网络好像出问题了，请检查你的网络状况~");
        }
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassWord() {
        return password;
    }

    @Override
    public void showData(LoginBean loginBean) {
        if (loginBean.getResultCode() == 1){
            Log.d(TAG, loginBean.getUser_uuid());
            driverHelper = DbUtil.getDriverHelper();
            int size = driverHelper.queryAll().size();
            if (size == 0) {
                Logger.d("数据库为空");
                DbBean tempBean = new DbBean();
                DbBean dbBean = insertInfo(tempBean, loginBean);
                driverHelper.save(dbBean);
            } else {
                Logger.d("数据库不为空");
                DbBean tempBean = driverHelper.queryAll().get(0);
                DbBean dbBean = insertInfo(tempBean, loginBean);
                dbBean.setDbId(Long.valueOf(1));
                dbBean.setUser_nickname("update");
                driverHelper.update(dbBean);
            }
        }else {
            ShowToast.show(LoginActivity.this, "" + loginBean.getDescribe());
        }

    }

    public DbBean insertInfo(DbBean dbBean, LoginBean loginBean) {
        dbBean.setUser_ID(loginBean.getUser_ID());
        dbBean.setUser_phone(loginBean.getUser_phone());
        dbBean.setUser_uuid(loginBean.getUser_uuid());
        dbBean.setStudent_ID(loginBean.getStudent_ID());
        dbBean.setUser_icon(loginBean.getUserInformation().getUser_icon());
        dbBean.setUser_nickname(loginBean.getUserInformation().getUser_nickname());
        dbBean.setUser_sex(loginBean.getUserInformation().getUser_sex());
        dbBean.setUser_birthday(loginBean.getUserInformation().getUser_birthday());
        dbBean.setUser_star(loginBean.getUserInformation().getUser_star());
        dbBean.setUser_height(loginBean.getUserInformation().getUser_height());
        dbBean.setUser_sign(loginBean.getUserInformation().getUser_sign());
        dbBean.setUser_education(loginBean.getUserInformation().getUser_education());
        dbBean.setUser_department(loginBean.getUserInformation().getUser_department());
        dbBean.setUser_locality(loginBean.getUserInformation().getUser_locality());
        dbBean.setUser_school(loginBean.getUserInformation().getUser_school());
        dbBean.setUser_state(loginBean.getUserInformation().getUser_state());
        dbBean.setUser_registerDate(loginBean.getUserInformation().getUser_registerDate());
        return dbBean;
    }

    @Override
    public void clearData() {

    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void showFailed() {
        Logger.d("failed");
        Snackbar.make(loginBut, "登录失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }
}
