package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.model.BaseListener;

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
        void showData(BaseBean2 baseBean);
        void showFailedForResult(BaseBean2 baseBean);
    }
    interface IRegisterBiz extends BaseListener{
        void doRegister(String stuId, String username, String password, OnRequestListener<BaseBean2> requestListener);
    }
}
