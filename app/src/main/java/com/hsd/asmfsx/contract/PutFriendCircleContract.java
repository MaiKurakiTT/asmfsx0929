package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BaseContract;
import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
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
        FriendCircleBean getFriendCircleBean();
        void showData(BaseBean baseBean);
    }
    interface IPutFriendCircleBiz{
        interface OnPutFriendCircleListener{
            void success(BaseBean baseBean);
            void failed();
        }
        void doPut(List<PictureBean> pictures, FriendCircleBean friendCircleBean, OnPutFriendCircleListener putFriendCircleListener);
    }
}
