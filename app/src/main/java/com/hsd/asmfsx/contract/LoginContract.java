package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.LoginBean;

/**
 * Created by apple on 2016/10/16.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter{

    }
    interface IView extends BaseView{
        String getUserName();
        String getPassWord();
        void showData(LoginBean loginBean);
        void clearData();
    }
}
