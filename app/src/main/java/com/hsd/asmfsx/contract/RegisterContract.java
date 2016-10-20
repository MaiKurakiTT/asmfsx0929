package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.RegisterBean;

/**
 * Created by apple on 2016/10/20.
 */

public interface RegisterContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        String getPhone();
        String getPassword();
        String getStuId();
        void showData(RegisterBean registerBean);
    }
    interface IRegisterBiz{
        interface OnRegisterListener{
            void success(RegisterBean registerBean);
            void failed();
        }
        void doRegister(String stuId, String username, String password, IRegisterBiz.OnRegisterListener registerListener);
    }
}
