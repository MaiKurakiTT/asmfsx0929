package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.FindFriendsBean;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public interface FindFriendsContract {
    interface Presenter extends BasePresenter{

    }
    interface  View extends BaseView{
        FindFriendsBean getFindFriendsBean();
        void showData(FindFriendsBean findFriendsBean);
    }
    interface IFindFriendsBiz{
        interface OnFindFriendsListener{
            void success(FindFriendsBean findFriendsBean);
            void failed();
        }
        void doFindFriends(FindFriendsBean findFriendsBean, OnFindFriendsListener findFriendsListener);
    }
}
