package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.GetUserInfoContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.GetUserInfoBizBiz;

/**
 * Created by sun on 2016/12/6.
 */

public class GetUserInfoPresenter implements BasePresenter{
    private GetUserInfoContract.View view;
    private GetUserInfoContract.IGetUserInfoBiz getUserInfoBiz;

    public GetUserInfoPresenter(GetUserInfoContract.View view) {
        this.view = view;
        this.getUserInfoBiz = new GetUserInfoBizBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        getUserInfoBiz.getUserInfo(view.getUserId(), new BaseListener.OnRequestListener<UserInformationBean2>() {
            @Override
            public void success(final UserInformationBean2 userInformationBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(userInformationBean2);
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
