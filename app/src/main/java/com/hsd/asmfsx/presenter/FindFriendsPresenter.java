package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.FindFriendsBiz;

import java.util.List;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsPresenter implements FindFriendsContract.Presenter {
    private FindFriendsContract.View view;
    private FindFriendsContract.IFindFriendsBiz findFriendsBiz;
    private int page = 2;
    private int limit = 10;

    public FindFriendsPresenter(FindFriendsContract.View view) {
        this.view = view;
        this.findFriendsBiz = new FindFriendsBiz();
    }

    @Override
    public void start() {
        requestData(0);
    }

    @Override
    public void loadMoreData() {
        requestData(1);
    }

    private void requestData(final int type) {
        view.showLoading();
        if (type == 0){
            page = 1;
        }
        findFriendsBiz.doFindFriends(page, limit, new BaseListener.OnRequestListener<List<UserInformationBean2>>() {
            @Override
            public void success(final List<UserInformationBean2> userInformationBean2s) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0){
                            view.showData(userInformationBean2s);
                        }else {
                            view.showMoreData(userInformationBean2s);
                        }
                        page++;
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0) {
                            view.showFailedForResult(baseBean);
                        }else {
                            view.showFailedForMoreResult(baseBean);
                        }
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failedForException(final Throwable t) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0) {
                            view.showFailedForException(t);
                        }else {
                            view.showFailedForMoreException(t);
                        }
                        view.hideLoading();
                    }
                });
            }
        });
    }
}
