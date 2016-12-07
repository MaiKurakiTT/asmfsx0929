package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by apple on 2016/11/15.
 */

public interface PutGoodContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        Long getPutGoodFCId();
        void showDataForGood(BaseBean2 baseBean);
        void showFailedForPutGoodResult(BaseBean2 baseBean);
        void showExceptionForPutGoodResult(Throwable t);
        void showLoadingForGood();
        void hideLoadingForGood();
    }
    interface IPutGoodBiz extends BaseListener{
        void doPutGood(Long friendCircleId, OnRequestListener<BaseBean2> requestListener);
    }
}
