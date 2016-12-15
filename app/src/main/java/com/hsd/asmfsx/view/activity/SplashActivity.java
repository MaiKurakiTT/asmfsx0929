package com.hsd.asmfsx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.hsd.asmfsx.MainActivity;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.presenter.LoginPresenter;
import com.hsd.asmfsx.utils.SPUtils;
import com.hsd.asmfsx.utils.ShowToast;

/**
 * Created by sun on 2016/12/14.
 */

public class SplashActivity extends AppCompatActivity implements LoginContract.View{

    private SPUtils spUtils;
    private String phone;
    private String psw;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initLogin();
    }

    private void initLogin() {
        loginPresenter = new LoginPresenter(this);
        spUtils = SPUtils.getInstance("asmfsx");
        phone = spUtils.getString("phone");
        psw = spUtils.getString("psw");
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(psw)){
            jump2Login();
        }else {
            loginPresenter.start();
        }
    }

    private void jump2Login(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void jump2Main(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getUserName() {
        return phone;
    }

    @Override
    public String getPassWord() {
        return psw;
    }

    @Override
    public void showData(BaseBean2 baseBean) {
        ShowToast.show(SplashActivity.this, "欢迎回来");
        jump2Main();
    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean) {
        jump2Login();
    }

    @Override
    public void clearData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailedForException(Throwable t) {
        jump2Login();
    }
}
