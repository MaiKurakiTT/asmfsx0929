package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.PlaceOrderContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.PlaceOrderBiz;

/**
 * Created by sun on 2016/12/10.
 */

public class PlaceOrderPresenter implements PlaceOrderContract.Presenter{
    private PlaceOrderContract.View view;
    private PlaceOrderContract.IPlaceOrderBiz placeOrderBiz;

    public PlaceOrderPresenter(PlaceOrderContract.View view) {
        this.view = view;
        placeOrderBiz = new PlaceOrderBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        placeOrderBiz.doPlaceOrder(view.getGoodIds(), view.getAmounts(), view.getDetail(), new BaseListener.OnRequestListener<BaseBean2>() {
            @Override
            public void success(final BaseBean2 baseBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(baseBean2);
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
