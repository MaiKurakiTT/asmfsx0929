package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
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
//        String getCode();
        void showData(BaseBean2 baseBean2);
        void showFailed(BaseBean2 baseBean2);
    }
    interface ICertificationBiz{
        interface OnCertificationListener{
            void success(BaseBean2 baseBean2);
            void failed(BaseBean2 baseBean2);
        }
        void doCertification(String school, String stuNum, String stuPsw,
                             ICertificationBiz.OnCertificationListener certificationListener);
    }
}
