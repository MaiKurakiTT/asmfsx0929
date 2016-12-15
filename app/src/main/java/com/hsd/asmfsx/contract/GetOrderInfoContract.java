package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.OrderVO;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by sun on 2016/12/15.
 */

public interface GetOrderInfoContract {
    interface Presenter extends BasePresenter {
    }
    interface View extends BaseView {
        Long getOrderId();
        void showData(OrderVO orderVO);
        void showFailedForResult(BaseBean2 baseBean2);
    }
    interface IGetOrderInfoBiz extends BaseListener{
        void getOrder(Long orderId, OnRequestListener<OrderVO> requestListener);
    }
}
