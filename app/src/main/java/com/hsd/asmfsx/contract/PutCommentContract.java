package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;

/**
 * Created by apple on 2016/11/15.
 */

public interface PutCommentContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        FriendCircleBean getFriendCircleBeanForComment();
        void showDataForComment(BaseBean baseBean);
        void showFailedForComment();
        void showLoadingForComment();
        void hideLoadingForComment();
    }
    interface IPutCommentBiz{
        interface OnPutCommentListener{
            void success(BaseBean baseBean);
            void failed();
        }
        void doPutComment(FriendCircleBean friendCircleBean, OnPutCommentListener putCommentListener);
    }
}
