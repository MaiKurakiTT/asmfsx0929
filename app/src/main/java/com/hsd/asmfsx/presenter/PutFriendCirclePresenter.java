package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.PutFriendCircleContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.PutFriendCircleBiz;

/**
 * Created by apple on 2016/11/15.
 */

public class PutFriendCirclePresenter implements PutFriendCircleContract.Presenter{
    private PutFriendCircleContract.View view;
    private PutFriendCircleContract.IPutFriendCircleBiz putFriendCircleBiz;

    public PutFriendCirclePresenter(PutFriendCircleContract.View view) {
        this.view = view;
        this.putFriendCircleBiz = new PutFriendCircleBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        putFriendCircleBiz.doPut(view.getContent(), view.getImgs(), new PutFriendCircleContract.IPutFriendCircleBiz.OnPutFriendCircleListener() {
            @Override
            public void success(final BaseBean2 baseBean, final int failedCounts) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(baseBean, failedCounts);
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