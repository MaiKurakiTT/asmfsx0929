package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;

/**
 * Created by apple on 2016/10/20.
 */

public interface SetAfterRegisterContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        UserInformationBean getUserInformationBean();
        void showData(UserInformationBean userInformationBean);
    }
    interface ISetAfterRegisterBiz{
        interface OnSetAfterRegisterListener{
            void success(UserInformationBean userInformationBean);
            void failed();
        }
        void doSetInfo(UserInformationBean userInformationBean, OnSetAfterRegisterListener setAfterRegisterListener);
    }
}
