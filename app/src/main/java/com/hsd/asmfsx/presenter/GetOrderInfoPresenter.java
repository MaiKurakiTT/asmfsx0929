package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.OrderVO;
import com.hsd.asmfsx.contract.GetOrderInfoContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.GetOrderInfoBiz;

/**
 * Created by sun on 2016/12/15.
 */

public class GetOrderInfoPresenter implements GetOrderInfoContract.Presenter{
    private GetOrderInfoContract.View view;
    private GetOrderInfoContract.IGetOrderInfoBiz getOrderInfoBiz;

    public GetOrderInfoPresenter(GetOrderInfoContract.View view) {
        this.view = view;
        this.getOrderInfoBiz = new GetOrderInfoBiz();
    }
    @Override
    public void start() {
        view.showLoading();
        getOrderInfoBiz.getOrder(view.getOrderId(), new BaseListener.OnRequestListener<OrderVO>() {
            @Override
            public void success(final OrderVO orderVO) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(orderVO);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForResult(baseBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForException(final Throwable t) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForException(t);
                        view.hideLoading();
                    }
                });
            }
        });
    }
}
