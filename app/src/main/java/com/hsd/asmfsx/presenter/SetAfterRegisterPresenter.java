package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
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
    public void start(){
        view.showLoading();
        setAfterRegisterBiz.doSetInfo(view.getImgFile(), view.getUserInformationBean(), new BaseListener.OnRequestListener<NormalResultBean<UserBean2>>() {
            @Override
            public void success(final NormalResultBean<UserBean2> userBean2NormalResultBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(userBean2NormalResultBean);
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
