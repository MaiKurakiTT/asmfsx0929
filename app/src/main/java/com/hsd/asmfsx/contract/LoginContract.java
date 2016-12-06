package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
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
        void showData(UserBean2 userBean);
        void showFailedForResult(BaseBean2 baseBean);
        void clearData();
    }
    interface ILoginBiz extends BaseListener {
        /*interface OnLoginListener{
            void success(UserBean2 userBean);
            void failedForResult(BaseBean2 baseBean);
            void failedForException();
        }*/
        /*void login(String userName, String passWord,
                   ILoginBiz.OnLoginListener loginListener);*/
        void login(String userName, String passWord, OnRequestListener<UserBean2> requestListener);
    }
}
