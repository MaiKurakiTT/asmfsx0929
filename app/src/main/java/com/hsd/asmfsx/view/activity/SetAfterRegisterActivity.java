package com.hsd.asmfsx.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.ShowToast;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

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
//        setAfterRegisterPresenter.start();
        String[] s = {"1", "2", "1", "2", "1", "2", "1", "2", "1", "2"};
        View inflate = View.inflate(this, R.layout.numberpickerview, null);
        NumberPickerView numberPickerView = (NumberPickerView) inflate.findViewById(R.id.numberpicker);
//        numberPickerView.refreshByNewDisplayedValues(s);
        numberPickerView.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {

            }
        });
        numberPickerView.setOnScrollListener(new NumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPickerView view, int scrollState) {

            }
        });
        numberPickerView.setDisplayedValues(s);
        numberPickerView.setMaxValue(1);
        numberPickerView.setMinValue(0);
        numberPickerView.setValue(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(inflate);
        builder.setTitle("设置身高");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
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
