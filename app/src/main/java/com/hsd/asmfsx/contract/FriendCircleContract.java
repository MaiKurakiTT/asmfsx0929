package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.FriendCircleVO;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by apple on 2016/11/10.
 */

public interface FriendCircleContract {
    interface Presenter extends BasePresenter{
        void loadMore();
    }
    interface View extends BaseView{
        void showData(List<FriendCircleVO> friendCircleVOs);
        void showMoreData(List<FriendCircleVO> friendCircleVOs);
        void showFailedForResult(BaseBean2 baseBean2);
        void showFailedForLoadMoreResult(BaseBean2 baseBean2);
        void showFailedForLoadMoreException(Throwable t);
    }
    interface IFriendCircleBiz extends BaseListener{
        void friendCircle(int page, int limit, OnRequestListener<List<FriendCircleVO>> requestListener);
    }
}
