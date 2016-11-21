package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.contract.RegisterContract;
import com.hsd.asmfsx.presenter.RegisterPresenter;
import com.hsd.asmfsx.utils.NetworkUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/10/20.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.register_but)
    Button registerBut;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.code_edit)
    EditText codeEdit;
    @BindView(R.id.code_but)
    Button codeBut;
    @BindView(R.id.psw_edit)
    EditText pswEdit;
    private RegisterPresenter registerPresenter;
    private String stuId;
    private String schoolName;
    private ProgressDialog progressDialog;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        initData();
        initView();
//        registerPresenter.start();

    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    private void doRegister() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            username = phoneEdit.getText().toString();
            password = pswEdit.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                ShowToast.show(RegisterActivity.this, "信息填写有误！");
            } else {
                registerPresenter.start();
            }
        } else {
            ShowToast.show(RegisterActivity.this, "网络好像出问题了，请检查你的网络状况~");
        }
    }

    private void initData() {
        Intent intent = getIntent();
        stuId = intent.getStringExtra("stuId");
        schoolName = intent.getStringExtra("schoolName");
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public String getPhone() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getStuId() {
        return stuId;
    }

    @Override
    public void showData(RegisterBean registerBean) {
        if (registerBean.getResultCode() == 1) {
            Logger.d("返回码为" + registerBean.getResultCode() + registerBean.getDescribe());
            ShowToast.show(RegisterActivity.this, "" + registerBean.getDescribe());
        } else {
            Logger.d("返回码为" + registerBean.getResultCode() + registerBean.getDescribe());
            ShowToast.show(RegisterActivity.this, "" + registerBean.getDescribe());
        }
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showFailed() {
        Snackbar.make(registerBut, "注册失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }
}
