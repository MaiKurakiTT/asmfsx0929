package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.CertificationBean;

/**
 * Created by apple on 2016/10/24.
 */

public interface CheckSchoolContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        void showDataForCheckSchool(CertificationBean certificationBean);
        String getSchoolNameForCheckSchool();
        void showFailedForCheckSchool();
    }
    interface ICheckSchoolBiz {
        interface OnCheckSchoolListener{
            void success(CertificationBean certificationBean);
            void failed();
        }
        void doCheckSchool(String schoolName, OnCheckSchoolListener checkSchoolListener);
    }
}
