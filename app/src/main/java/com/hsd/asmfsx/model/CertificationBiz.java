package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.contract.CertificationContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by apple on 2016/10/20.
 * 实名认证业务逻辑
 */

public class CertificationBiz implements CertificationContract.ICertificationBiz{
    @Override
    public void doCertification(String school, String stuNum, String stuPsw, String code, final OnCertificationListener certificationListener) {
        Retrofit retrofit = GetRetrofit.getRetrofit();
        RetrofitService service = retrofit.create(RetrofitService.class);
        CertificationBean certificationBean = new CertificationBean();
        certificationBean.setStudent_schoolName(school);
        certificationBean.setStudent_number(stuNum);
        certificationBean.setStudent_password(stuPsw);
        certificationBean.setVerificationCode(code);
        Call<CertificationBean> call = service.postCertification(certificationBean);
        call.enqueue(new Callback<CertificationBean>() {
            @Override
            public void onResponse(Call<CertificationBean> call, Response<CertificationBean> response) {
                CertificationBean bean = response.body();
                if (bean != null){
                    certificationListener.success(bean);
                }else {
                    Logger.d("CertificationBean空了");
                    certificationListener.failed();
                }
            }

            @Override
            public void onFailure(Call<CertificationBean> call, Throwable t) {
                Logger.d("实名认证请求抛异常了：" + t.toString());
                t.printStackTrace();
                certificationListener.failed();
            }
        });
    }
}
