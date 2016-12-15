package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.OrderListBean;
import com.hsd.asmfsx.contract.GetItemGoodInfoContract;
import com.hsd.asmfsx.contract.GetOrderListContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.GetItemGoodInfoBiz;
import com.hsd.asmfsx.model.GetOrderListBiz;

import java.util.List;

/**
 * Created by sun on 2016/12/15.
 */

public class GetOrderListPresenter implements GetOrderListContract.Presenter{
    private GetOrderListContract.View view;
    private GetOrderListContract.IGetOrderListBiz getItemGoodInfoBiz;
    private int page = 2;
    private int limit = 10;

    public GetOrderListPresenter(GetOrderListContract.View view) {
        this.view = view;
        this.getItemGoodInfoBiz = new GetOrderListBiz();
    }
    @Override
    public void start() {
        view.showLoading();
        getList(0);

    }

    @Override
    public void loadMore() {
        getList(1);
    }

    private void getList(int type) {
        if (type == 0){
            page = 1;
        }
        doRequest(type);
    }

    private void doRequest(final int type) {
        getItemGoodInfoBiz.getList(page, limit, new BaseListener.OnRequestListener<List<OrderListBean>>() {
            @Override
            public void success(final List<OrderListBean> orderListBeen) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0){
                            view.showData(orderListBeen);
                        }else {
                            view.showLoadMore(orderListBeen);
                        }
                        page ++;
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0){
                            view.showFailedResult(baseBean);
                        }else {
                            view.showFailedForLoadMoreResult(baseBean);
                        }
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForException(final Throwable t) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0){
                            view.showFailedForException(t);
                        }else {
                            view.showExceptionForLoadMore(t);
                        }
                        view.hideLoading();
                    }
                });
            }
        });
    }


}
