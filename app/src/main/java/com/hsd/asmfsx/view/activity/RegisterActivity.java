package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.contract.RegisterContract;
import com.hsd.asmfsx.presenter.RegisterPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;

/**
 * Created by apple on 2016/10/20.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        registerPresenter = new RegisterPresenter(this);
        registerPresenter.start();
    }

    @Override
    public String getPhone() {
        return "18738577578";
    }

    @Override
    public String getPassword() {
        return "123";
    }

    @Override
    public String getStuId() {
        return "1428524062";
    }

    @Override
    public void showData(RegisterBean registerBean) {
        if (registerBean.getResultCode() == 1){
            Logger.d("返回码为" + registerBean.getResultCode() + registerBean.getDescribe());

        }else {
            Logger.d("返回码为" + registerBean.getResultCode() + registerBean.getDescribe());
            ShowToast.show(RegisterActivity.this, "" + registerBean.getDescribe());
        }
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
