package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.contract.CheckSchoolContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.CheckSchoolBizBiz;

/**
 * Created by apple on 2016/10/24.
 */

public class CheckSchoolPresenter implements CheckSchoolContract.Presenter{
    private CheckSchoolContract.View view;
    private CheckSchoolContract.ICheckSchoolBiz checkSchoolBiz;

    public CheckSchoolPresenter(CheckSchoolContract.View view) {
        this.view = view;
        this.checkSchoolBiz = new CheckSchoolBizBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        checkSchoolBiz.doCheckSchool(view.getSchoolNameForCheckSchool(), new CheckSchoolContract.ICheckSchoolBiz.OnCheckSchoolListener() {
            @Override
            public void success(final CertificationBean certificationBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForCheckSchool(certificationBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForCheckSchool();
                        view.hideLoading();
                    }
                });
            }
        });
    }
}
