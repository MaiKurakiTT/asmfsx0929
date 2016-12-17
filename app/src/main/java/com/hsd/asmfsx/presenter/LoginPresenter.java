package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.bean.LoginBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.contract.LoginContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
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
        loginBiz.login(view.getUserName(), view.getPassWord(), new BaseListener.OnRequestListener<NormalResultBean<LoginBean2>>() {
            @Override
            public void success(final NormalResultBean<LoginBean2> normalResultBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
//                        view.clearData();
                        view.showData(normalResultBean);
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
//                        view.clearData();
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
                        view.clearData();
                        view.showFailedForException(t);
                        view.hideLoading();
                    }
                });
            }
        });
        /*loginBiz.login(view.getUserName(), view.getPassWord(), new BaseListener.OnRequestListener<BaseBean2>() {
            @Override
            public void success(final BaseBean2 baseBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
//                        view.clearData();
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
//                        view.clearData();
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
                        view.clearData();
                        view.showFailedForException(t);
                        view.hideLoading();
                    }
                });
            }
        });
*/
    }
}
