package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class SetAfterRegisterActivity extends AppCompatActivity implements SetAfterRegisterContract.View{

    private SetAfterRegisterPresenter setAfterRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setafterregister_layout);
        setAfterRegisterPresenter = new SetAfterRegisterPresenter(this);
        setAfterRegisterPresenter.start();
    }

    @Override
    public UserInformationBean getUserInformationBean() {
        UserInformationBean userInformationBean = new UserInformationBean();
        UserBean userBean = new UserBean();
        userBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        userInformationBean.setUser(userBean);
        userInformationBean.setUser_nickname("2016年10月22日");
        return userInformationBean;
    }

    @Override
    public void showData(UserInformationBean userInformationBean) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailed() {

    }
}
