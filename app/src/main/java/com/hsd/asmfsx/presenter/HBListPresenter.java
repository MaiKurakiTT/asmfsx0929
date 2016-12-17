package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.contract.HBListContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.HBListBiz;

import java.util.List;

/**
 * Created by sun on 2016/12/16.
 */

public class HBListPresenter implements HBListContract.Presenter{
    private HBListContract.View view;
    private HBListContract.IHBListBiz hbListBiz;

    public HBListPresenter(HBListContract.View view) {
        this.view = view;
        this.hbListBiz = new HBListBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        hbListBiz.getHBList(new BaseListener.OnRequestListener<List<HBListBean>>() {
            @Override
            public void success(final List<HBListBean> hbListBeen) {
                GetHandler
                        .getHandler()
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                view.showDataForHBList(hbListBeen);
                                view.hideLoading();
                            }
                        });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler
                        .getHandler()
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                view.showFailedForResultHBList(baseBean);
                                view.hideLoading();
                            }
                        });
            }

            @Override
            public void failedForException(final Throwable t) {
                GetHandler
                        .getHandler()
                        .post(new Runnable() {
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
