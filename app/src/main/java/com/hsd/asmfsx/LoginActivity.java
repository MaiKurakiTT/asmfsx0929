package com.hsd.asmfsx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.presenter.LoginPresenter;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.IView{
    private String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.start();

    }

    @Override
    public String getUserName() {
        return "18738577578";
    }

    @Override
    public String getPassWord() {
        return "123";
    }

    @Override
    public void showData(LoginBean loginBean) {
        Log.d(TAG, loginBean.getUser_uuid());
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
    public void showFailed() {
        Log.d(TAG, "failed");
    }
}
