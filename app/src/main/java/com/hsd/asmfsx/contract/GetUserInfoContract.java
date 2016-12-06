package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by sun on 2016/12/6.
 */

public interface GetUserInfoContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        int getUserId();
        void showData(UserInformationBean2 userInformationBean);
        void showFailedForResult(BaseBean2 baseBean);
    }
    interface IGetUserInfoBiz extends BaseListener{
        void getUserInfo(int userId, OnRequestListener<UserInformationBean2> requestListener);
    }
}
