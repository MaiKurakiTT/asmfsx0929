package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.contract.GetItemGoodInfoContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.GetItemGoodInfoBiz;

/**
 * Created by sun on 2016/12/12.
 */

public class GetItemGoodInfoPresenter implements GetItemGoodInfoContract.Presenter{
    private GetItemGoodInfoContract.View view;
    private GetItemGoodInfoContract.IGetItemGoodInfoBiz getItemGoodInfoBiz;

    public GetItemGoodInfoPresenter(GetItemGoodInfoContract.View view) {
        this.view = view;
        this.getItemGoodInfoBiz = new GetItemGoodInfoBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        getItemGoodInfoBiz.getGoodInfo(view.getGoodId(), new BaseListener.OnRequestListener<CommodityVO>() {
            @Override
            public void success(final CommodityVO commodityVO) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(commodityVO);
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
