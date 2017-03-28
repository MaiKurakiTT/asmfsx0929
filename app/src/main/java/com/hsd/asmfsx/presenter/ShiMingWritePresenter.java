package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.ShiMingWriteContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.ShiMingWriteBiz;

/**
 * Created by sun on 2017/3/28.
 */

public class ShiMingWritePresenter implements ShiMingWriteContract.Presenter{
    private ShiMingWriteContract.IShiMingWriteBiz shiMingWriteBiz;
    private ShiMingWriteContract.View view;

    public ShiMingWritePresenter(ShiMingWriteContract.View view) {
        this.view = view;
        this.shiMingWriteBiz = new ShiMingWriteBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        shiMingWriteBiz.doPut(view.getRealName(), view.getSchoolNum(), view.getImg(), new ShiMingWriteContract.IShiMingWriteBiz.OnShiMingWriteListener() {
            @Override
            public void success(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForWrite(baseBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForResultWrite(baseBean);
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
