package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BaseContract;
import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.PictureBean;

import java.util.List;

/**
 * Created by apple on 2016/11/14.
 */

public interface PutFriendCircleContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView {
        String getContent();
        List<String> getImgs();
        void showData(BaseBean2 baseBean, int failedCounts);
        void showFailedForResult(BaseBean2 baseBean);
    }
    interface IPutFriendCircleBiz{
        interface OnPutFriendCircleListener{
            void success(BaseBean2 baseBean, int failedCounts);
            void failedForResult(BaseBean2 baseBean);
            void failedForException(Throwable t);
        }
        void doPut(String content, List<String> imgs, OnPutFriendCircleListener putFriendCircleListener);
    }
}
