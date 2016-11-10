package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;

/**
 * Created by apple on 2016/11/10.
 */

public interface FriendCircleContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        FriendCircleBean getFriendCircleBean();
        void showData(BaseBean baseBean);
        void showMoreData(BaseBean baseBean);
        void showFailedForLoadMore();
    }
    interface IFriendCircleBiz{
        interface OnFriendCircleListener{
            void success(BaseBean baseBean);
            void failed();
        }
        void friendCircle(FriendCircleBean friendCircleBean, OnFriendCircleListener friendCircleListener);
    }
}
