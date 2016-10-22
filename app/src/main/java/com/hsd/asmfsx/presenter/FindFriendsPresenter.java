package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.FindFriendsBiz;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsPresenter implements FindFriendsContract.Presenter{
    private FindFriendsContract.View view;
    private FindFriendsContract.IFindFriendsBiz findFriendsBiz;

    public FindFriendsPresenter(FindFriendsContract.View view) {
        this.view = view;
        this.findFriendsBiz = new FindFriendsBiz();
    }

    @Override
    public void start() {
        view.showLoading();
        findFriendsBiz.doFindFriends(view.getFindFriendsBean(), new FindFriendsContract.IFindFriendsBiz.OnFindFriendsListener() {
            @Override
            public void success(final FindFriendsBean findFriendsBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showData(findFriendsBean);
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
