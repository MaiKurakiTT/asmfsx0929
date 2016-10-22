package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.SetAfterRegisterBiz;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class SetAfterRegisterPresenter implements SetAfterRegisterContract.Presenter{
    private SetAfterRegisterContract.View view;
    private SetAfterRegisterContract.ISetAfterRegisterBiz setAfterRegisterBiz;

    public SetAfterRegisterPresenter(SetAfterRegisterContract.View view) {
        this.view = view;
        this.setAfterRegisterBiz = new SetAfterRegisterBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        setAfterRegisterBiz.doSetInfo(view.getUserInformationBean(), new SetAfterRegisterContract.ISetAfterRegisterBiz.OnSetAfterRegisterListener() {
            @Override
            public void success(final UserInformationBean userInformationBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(userInformationBean);
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
