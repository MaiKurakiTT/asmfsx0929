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

public interface PutCommentContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        String getCommentContent();
        Long getCommentFCId();
        Long getByUserId();
        void showDataForComment(BaseBean2 baseBean);
        void showFailedForPutCommentResult(BaseBean2 baseBean);
        void showExceptionForPutCommentResult(Throwable t);
        void showLoadingForPutComment();
        void hideLoadingForPutComment();
    }
    interface IPutCommentBiz extends BaseListener{
        void doPutComment(String content, Long friendCircleID, Long byUserID, OnRequestListener<BaseBean2> requestListener);
    }
}
