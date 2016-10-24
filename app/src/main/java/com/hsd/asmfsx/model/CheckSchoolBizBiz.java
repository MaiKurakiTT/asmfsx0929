package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.contract.CheckSchoolContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2016/10/24.
 */

public class CheckSchoolBizBiz implements CheckSchoolContract.ICheckSchoolBiz {
    @Override
    public void doCheckSchool(String schoolName, final OnCheckSchoolListener checkSchoolListener) {
        RetrofitService service = GetRetrofit.getRetrofit().create(RetrofitService.class);
        CertificationBean certificationBean = new CertificationBean();
        certificationBean.setStudent_schoolName(schoolName);
        Call<CertificationBean> call = service.postCheckSchool(certificationBean);
        call.enqueue(new Callback<CertificationBean>() {
            @Override
            public void onResponse(Call<CertificationBean> call, Response<CertificationBean> response) {
                CertificationBean body = response.body();
                checkSchoolListener.success(body);
            }

            @Override
            public void onFailure(Call<CertificationBean> call, Throwable t) {
                t.printStackTrace();
                Logger.d("" + t.toString());
                checkSchoolListener.failed();
            }
        });
    }
}
