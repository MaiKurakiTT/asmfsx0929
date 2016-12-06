package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.model.BaseListener;

import java.io.File;

/**
 * Created by apple on 2016/10/20.
 */

public interface SetAfterRegisterContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        UserInformationBean2 getUserInformationBean();
        File getImgFile();
        void showData(BaseBean2 baseBean);
        void showFailedForResult(BaseBean2 baseBean);
    }
    interface ISetAfterRegisterBiz extends BaseListener{
        void doSetInfo(File imgFile, UserInformationBean2 userInformationBean, OnRequestListener<NormalResultBean<UserBean2>> requestListener);
    }
}
