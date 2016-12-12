package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.contract.GetShopListContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.GetShopListBiz;

import java.util.List;

/**
 * Created by sun on 2016/12/12.
 */

public class GetShopListPresenter implements GetShopListContract.Presenter{
    private GetShopListContract.View view;
    private GetShopListContract.IGetShopListBiz getShopListBiz;

    private int page = 1;
    private int limit = 10;

    public GetShopListPresenter(GetShopListContract.View view) {
        this.view = view;
        this.getShopListBiz = new GetShopListBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        getShopListBiz.getShop(page, limit, new BaseListener.OnRequestListener<List<ShopVO>>() {
            @Override
            public void success(final List<ShopVO> shopVOs) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(shopVOs);
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
