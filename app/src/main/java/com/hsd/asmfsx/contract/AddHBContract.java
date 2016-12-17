package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by sun on 2016/12/16.
 */

public interface AddHBContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        Long getHBUserID();
        void showData(BaseBean2 baseBean2);
        void showFailedForResult(BaseBean2 baseBean2);
    }
    interface IAddHBBiz extends BaseListener{
        void addHB(Long heartBeatUserID, OnRequestListener<BaseBean2> requestListener);
    }
}
