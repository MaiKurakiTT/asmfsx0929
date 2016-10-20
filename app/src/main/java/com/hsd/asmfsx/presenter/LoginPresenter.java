package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.LoginBiz;

/**
 * Created by apple on 2016/10/16.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View view;
    private LoginBiz loginBiz;
    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.loginBiz = new LoginBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        loginBiz.login(view.getUserName(), view.getPassWord(), "",  new LoginContract.ILoginBiz.OnLoginListener() {
            @Override
            public void success(final LoginBean loginBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.clearData();
                        view.showData(loginBean);
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.clearData();
                        view.showFailed();
                    }
                });
            }
        });
    }
}
