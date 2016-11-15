package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;

/**
 * Created by apple on 2016/11/15.
 */

public interface PutGoodContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        FriendCircleBean getFriendCircleBeanForGood();
        void showDataForGood(BaseBean baseBean);
        void showFailedForGood();
        void showLoadingForGood();
        void hideLoadingForGood();
    }
    interface IPutGoodBiz{
        interface OnPutGoodListener{
            void success(BaseBean baseBean);
            void failed();
        }
        void doPutGood(FriendCircleBean friendCircleBean, OnPutGoodListener putGoodListener);
    }
}
