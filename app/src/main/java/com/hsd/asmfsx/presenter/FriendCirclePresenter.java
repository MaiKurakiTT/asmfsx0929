package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.FriendCircleBiz;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCirclePresenter implements FriendCircleContract.Presenter{
    private FriendCircleContract.View view;
    private FriendCircleContract.IFriendCircleBiz friendCircleBiz;
    public FriendCirclePresenter(FriendCircleContract.View view) {
        this.view = view;
        this.friendCircleBiz = new FriendCircleBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        friendCircleBiz.friendCircle(view.getFriendCircleBean(), new FriendCircleContract.IFriendCircleBiz.OnFriendCircleListener() {
            @Override
            public void success(final BaseBean baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(baseBean);
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
