package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.contract.RegisterContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.RegisterBiz;

/**
 * Created by apple on 2016/10/20.
 */

public class RegisterPresenter implements RegisterContract.Presenter{
    private RegisterContract.View view;
    private RegisterContract.IRegisterBiz registerBiz;
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.registerBiz = new RegisterBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        registerBiz.doRegister(view.getStuId(), view.getPhone(), view.getPassword(), new RegisterContract.IRegisterBiz.OnRegisterListener() {
            @Override
            public void success(final RegisterBean registerBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(registerBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailed();
                        view.hideLoading();
                    }
                });
            }
        });
    }
}
