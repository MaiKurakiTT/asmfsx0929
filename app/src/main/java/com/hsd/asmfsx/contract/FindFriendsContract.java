package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public interface FindFriendsContract {
    interface Presenter extends BasePresenter{
        void loadMoreData();
    }
    interface  View extends BaseView{
        void showData(List<UserInformationBean2> userInformationBean2s);
        void showMoreData(List<UserInformationBean2> userInformationBean2s);
        void showFailedForResult(BaseBean2 baseBean2);
        void showFailedForMoreResult(BaseBean2 baseBean2);
        void showFailedForMoreException(Throwable t);
    }
    interface IFindFriendsBiz extends BaseListener{
        void doFindFriends(int page, int limit, OnRequestListener<List<UserInformationBean2>> requestListener);
    }
}
