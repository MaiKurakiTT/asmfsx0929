package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.contract.GetShopGoodListContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.GetShopGoodListBiz;

import java.util.List;

/**
 * Created by sun on 2016/12/12.
 */

public class GetShopGoodListPresenter implements GetShopGoodListContract.Presenter{
    private GetShopGoodListContract.View view;
    private GetShopGoodListContract.IGetShopGoodListBiz getShopGoodListBiz;

    private int page = 1;
    private int limit = 10;

    public GetShopGoodListPresenter(GetShopGoodListContract.View view) {
        this.view = view;
        this.getShopGoodListBiz = new GetShopGoodListBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        getShopGoodListBiz.getGoodList(view.getShopId(), page, limit, new BaseListener.OnRequestListener<List<CommodityVO>>() {
            @Override
            public void success(final List<CommodityVO> commodityVOs) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(commodityVOs);
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
