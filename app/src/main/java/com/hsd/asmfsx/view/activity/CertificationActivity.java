package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.contract.CertificationContract;
import com.hsd.asmfsx.presenter.CertificationPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;

/**
 * Created by apple on 2016/10/20.
 */

public class CertificationActivity extends AppCompatActivity implements CertificationContract.View{
    private CertificationPresenter certificationPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_layout);
        certificationPresenter = new CertificationPresenter(this);
        certificationPresenter.start();
    }

    @Override
    public String getSchoolName() {
        return "河南师范大学";
    }

    @Override
    public String getStuNum() {
        return "1428524062";
    }

    @Override
    public String getStuPsw() {
        return "sza11111";
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public void showData(CertificationBean certificationBean) {
        if (certificationBean.getResultCode() == 1){
            Logger.d("实名认证成功");
            ShowToast.show(CertificationActivity.this, "" + certificationBean.getDescribe());
            /**
             * 认证成功，跳转注册
             */
        }else {
            ShowToast.show(CertificationActivity.this, "" + certificationBean.getDescribe());
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
        Logger.d("实名认证失败");
    }
}
