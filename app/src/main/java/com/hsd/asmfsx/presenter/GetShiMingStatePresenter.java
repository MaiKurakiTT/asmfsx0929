package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShiMingBean;
import com.hsd.asmfsx.contract.GetShiMingStateContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.GetShiMingStateBiz;

/**
 * Created by sun on 2017/3/28.
 */

public class GetShiMingStatePresenter implements GetShiMingStateContract.Presenter{
    private GetShiMingStateContract.IGetShiMingStateBiz getShiMingStateBiz;
    private GetShiMingStateContract.View view;

    public GetShiMingStatePresenter(GetShiMingStateContract.View view) {
        this.view = view;
        getShiMingStateBiz = new GetShiMingStateBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        getShiMingStateBiz.doGetShiMingState(new GetShiMingStateContract.IGetShiMingStateBiz.OnGetShiMingStateListener() {
            @Override
            public void success(final NormalResultBean<ShiMingBean> resultBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForState(resultBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFiledForState(baseBean);
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
