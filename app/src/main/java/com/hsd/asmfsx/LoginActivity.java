package com.hsd.asmfsx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.db.DbBean;
import com.hsd.asmfsx.db.DbBeanHelper;
import com.hsd.asmfsx.db.DbUtil;
import com.hsd.asmfsx.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.IView{
    private String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;
    private DbBeanHelper driverHelper;

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
        driverHelper = DbUtil.getDriverHelper();
        int size = driverHelper.queryAll().size();
        if (size == 0){
            Log.d(TAG, "数据库为空");
            DbBean tempBean = new DbBean();
            DbBean dbBean = insertInfo(tempBean, loginBean);
            driverHelper.save(dbBean);
        }else {
            Log.d(TAG, "数据库不为空");
            DbBean tempBean = driverHelper.queryAll().get(0);
            DbBean dbBean = insertInfo(tempBean, loginBean);
            dbBean.setDbId(Long.valueOf(1));
            dbBean.setUser_nickname("update");
            driverHelper.update(dbBean);
        }
    }
    public DbBean insertInfo(DbBean dbBean, LoginBean loginBean){
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailed() {
        Log.d(TAG, "failed");
    }
}
