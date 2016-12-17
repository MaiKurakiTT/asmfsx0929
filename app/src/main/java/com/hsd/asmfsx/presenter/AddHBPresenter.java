package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.AddHBContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.AddHBBiz;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by sun on 2016/12/16.
 */

public class AddHBPresenter implements AddHBContract.Presenter{
    private AddHBContract.View view;
    private AddHBContract.IAddHBBiz addHBBiz;

    public AddHBPresenter(AddHBContract.View view) {
        this.view = view;
        this.addHBBiz = new AddHBBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        addHBBiz.addHB(view.getHBUserID(), new BaseListener.OnRequestListener<BaseBean2>() {
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
