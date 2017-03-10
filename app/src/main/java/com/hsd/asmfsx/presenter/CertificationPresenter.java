package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.contract.CertificationContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.CertificationBiz;

/**
 * Created by apple on 2016/10/20.
 */

public class CertificationPresenter implements CertificationContract.Presenter{
    private CertificationContract.View view;
    private CertificationContract.ICertificationBiz certificationBiz;
    public CertificationPresenter(CertificationContract.View view) {
        this.view = view;
        this.certificationBiz = new CertificationBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        certificationBiz.doCertification(view.getSchoolName(), view.getStuNum(), view.getStuPsw(), new CertificationContract.ICertificationBiz.OnCertificationListener() {
            @Override
            public void success(final BaseBean2 baseBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(baseBean2);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failed(final BaseBean2 baseBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailed(baseBean2);
                        view.hideLoading();
                    }
                });
            }
        });

        /*certificationBiz.doCertification(view.getSchoolName(), view.getStuNum(), view.getStuPsw(), view.getCode(), new CertificationContract.ICertificationBiz.OnCertificationListener() {
            @Override
            public void success(final CertificationBean certificationBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(certificationBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
//                        view.showFailedForException();
                        view.hideLoading();
                    }
                });
            }
        });*/
    }
}
