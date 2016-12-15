package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.OrderListBean;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by sun on 2016/12/15.
 */

public interface GetOrderListContract {
    interface Presenter extends BasePresenter {
        void loadMore();
    }
    interface View extends BaseView {

        void showData(List<OrderListBean> orderListBeen);
        void showLoadMore(List<OrderListBean> orderListBeen);
        void showFailedResult(BaseBean2 baseBean);
        void showFailedForLoadMoreResult(BaseBean2 baseBean2);
        void showExceptionForLoadMore(Throwable t);
    }
    interface IGetOrderListBiz extends BaseListener{
        void getList(int page, int limit, OnRequestListener<List<OrderListBean>> requestListener);
    }
}
