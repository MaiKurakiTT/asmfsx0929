package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;

import java.io.File;

/**
 * Created by apple on 2016/10/20.
 */

public interface SetAfterRegisterContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        UserInformationBean getUserInformationBean();
        File getImgFile();
        void showData(UserInformationBean userInformationBean);
    }
    interface ISetAfterRegisterBiz{
        interface OnSetAfterRegisterListener{
            void success(UserInformationBean userInformationBean);
            void failed();
        }
        void doSetInfo(File imgFile, UserInformationBean userInformationBean, OnSetAfterRegisterListener setAfterRegisterListener);
    }
}
