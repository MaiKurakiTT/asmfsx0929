package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.contract.CertificationContract;
import com.hsd.asmfsx.contract.CheckSchoolContract;
import com.hsd.asmfsx.presenter.CertificationPresenter;
import com.hsd.asmfsx.presenter.CheckSchoolPresenter;
import com.hsd.asmfsx.utils.NetworkUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/10/20.
 */

public class CertificationActivity extends AppCompatActivity implements CertificationContract.View, CheckSchoolContract.View {
    @BindView(R.id.school_spinner)
    MaterialSpinner schoolSpinner;
    @BindView(R.id.stuidinput)
    TextInputLayout stuidinput;
    @BindView(R.id.stupswinput)
    TextInputLayout stupswinput;
    @BindView(R.id.vercodeinput)
    TextInputLayout vercodeinput;
    @BindView(R.id.vercodeimg)
    ImageView vercodeimg;
    @BindView(R.id.vercodeview)
    LinearLayout vercodeview;
    @BindView(R.id.check_but)
    AppCompatButton checkBut;
    private CertificationPresenter certificationPresenter;
    String[] ITEMS = {"河南师范大学", "新乡医学院", "新乡学院", "河南科技学院"};
    private int SCHOOL = 0;
    private CheckSchoolPresenter checkSchoolPresente;
    private ProgressDialog progressDialog;
    //该学校是否需要验证码,true为需要
    private Boolean HASCODE = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_layout);
        ButterKnife.bind(this);
        initView();
        certificationPresenter = new CertificationPresenter(this);
        checkSchoolPresente = new CheckSchoolPresenter(this);
        checkSchoolPresente.start();

    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        schoolSpinner.setItems("河南师范大学", "新乡医学院", "新乡学院", "河南科技学院");
        schoolSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        SCHOOL = 0;
                        checkSchoolPresente.start();
                        break;
                    case 1:
                        SCHOOL = 1;
                        checkSchoolPresente.start();
                        break;
                    case 2:
                        SCHOOL = 2;
                        checkSchoolPresente.start();
                        break;
                    case 3:
                        SCHOOL = 3;
                        checkSchoolPresente.start();
                        break;

                }
            }
        });
        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCertification();
            }
        });
    }

    private void doCertification() {
        if (NetworkUtils.isNetworkAvailable(this)){
            String schoolName = schoolSpinner.getText().toString();
            String stuId = stuidinput.getEditText().getText().toString();
            String stuPsw = stupswinput.getEditText().getText().toString();
            String verCode = vercodeinput.getEditText().getText().toString();
            if (HASCODE == true){
                if (TextUtils.isEmpty(stuId) || TextUtils.isEmpty(stuPsw) || TextUtils.isEmpty(verCode)){
                    ShowToast.show(CertificationActivity.this, "信息填写有误！");
                }else {
                    certificationPresenter.start();
                }
            }else {
                if (TextUtils.isEmpty(stuId) || TextUtils.isEmpty(stuPsw)){
                    ShowToast.show(CertificationActivity.this, "信息填写有误！");
                }else {
                    certificationPresenter.start();
                }
            }
        }else {
            ShowToast.show(CertificationActivity.this, "网络好像出问题了，请检查你的网络状况~");
        }

    }

    @Override
    public String getSchoolName() {
        return schoolSpinner.getText().toString();
    }

    @Override
    public String getStuNum() {
        return stuidinput.getEditText().getText().toString();
    }

    @Override
    public String getStuPsw() {
        return stupswinput.getEditText().getText().toString();
    }

    @Override
    public String getCode() {
        return vercodeinput.getEditText().getText().toString();
    }

    @Override
    public void showData(CertificationBean certificationBean) {
        if (certificationBean.getResultCode() == 1) {
            Logger.d("实名认证成功");
            ShowToast.show(CertificationActivity.this, "" + certificationBean.getDescribe());
            /**
             * 认证成功，跳转注册
             */
        } else {
            ShowToast.show(CertificationActivity.this, "" + certificationBean.getDescribe());
        }
    }

    @Override
    public void showDataForCheckSchool(CertificationBean certificationBean) {
        String verificationCode = certificationBean.getVerificationCode();
        if (TextUtils.isEmpty(verificationCode)) {
            //验证码为空，表示该学校不需要验证码
            Logger.d("验证码为空");
            vercodeview.setVisibility(View.GONE);
            HASCODE = false;
        } else {
            //验证码不为空，表示该学校需要验证码,验证码的图片地址就是验证码的值
            Logger.d("验证码不为空");
            vercodeview.setVisibility(View.VISIBLE);
            HASCODE = true;
            Glide.with(this).load(verificationCode).into(vercodeimg);
        }
    }

    @Override
    public String getSchoolNameForCheckSchool() {
        Logger.d(schoolSpinner.getText().toString());
        return schoolSpinner.getText().toString();
    }

    @Override
    public void showFailedForCheckSchool() {
        Snackbar.make(schoolSpinner, "请求验证码失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSchoolPresente.start();
            }
        }).show();
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
        Logger.d("实名认证失败");
        Snackbar.make(schoolSpinner, "实名认证失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCertification();
            }
        }).show();
    }
}
