package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.LoginBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by apple on 2016/10/16.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        String getUserName();
        String getPassWord();
        void showData(NormalResultBean<LoginBean2> normalResultBean);
        void showFailedForResult(BaseBean2 baseBean);
        void clearData();
    }
    interface ILoginBiz extends BaseListener {
        void login(String userName, String passWord, OnRequestListener<NormalResultBean<LoginBean2>> requestListener);
    }
}
