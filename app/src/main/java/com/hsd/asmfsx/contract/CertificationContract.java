package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.CertificationBean;

/**
 * Created by apple on 2016/10/20.
 */

public interface CertificationContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        String getSchoolName();
        String getStuNum();
        String getStuPsw();
        String getCode();
        void showData(CertificationBean certificationBean);
    }
    interface ICertificationBiz{
        interface OnCertificationListener{
            void success(CertificationBean certificationBean);
            void failed();
        }
        void doCertification(String school, String stuNum, String stuPsw, String code,
                             ICertificationBiz.OnCertificationListener certificationListener);
    }
}
