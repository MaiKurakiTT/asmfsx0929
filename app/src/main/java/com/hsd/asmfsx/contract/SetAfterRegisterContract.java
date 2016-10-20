package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.UserBean;

/**
 * Created by apple on 2016/10/20.
 */

public interface SetAfterRegisterContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{

    }
    interface ISetAfterRegisterBiz{
        interface OnSetAfterRegisterListener{
            void success(UserBean userBean);
            void failed();
        }
        void doSetInfo(UserBean userBean, OnSetAfterRegisterListener setAfterRegisterListener);
    }
}
