package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;

/**
 * Created by apple on 2016/10/20.
 */

public interface RegisterContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        void showData();
    }
    interface IRegisterBiz{
        interface OnRegisterListener{
            void success();
            void failed();
        }
        void doRegister(String username, String password, IRegisterBiz.OnRegisterListener registerListener);
    }
}
