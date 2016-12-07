package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.PutGoodContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.PutFCGoodBiz;

/**
 * Created by sun on 2016/12/7.
 */

public class PutFCGoodPresenter implements PutGoodContract.Presenter{
    private PutGoodContract.View view;
    private PutGoodContract.IPutGoodBiz putGoodBiz;

    public PutFCGoodPresenter(PutGoodContract.View view) {
        this.view = view;
        this.putGoodBiz = new PutFCGoodBiz();
    }

    @Override
    public void start() {
        view.showLoadingForGood();
        putGoodBiz.doPutGood(view.getPutGoodFCId(), new BaseListener.OnRequestListener<BaseBean2>() {
            @Override
            public void success(final BaseBean2 baseBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForGood(baseBean2);
                        view.hideLoadingForGood();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForPutGoodResult(baseBean);
                        view.hideLoadingForGood();
                    }
                });
            }

            @Override
            public void failedForException(final Throwable t) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForException(t);
                        view.hideLoadingForGood();
                    }
                });
            }
        });
    }
}
