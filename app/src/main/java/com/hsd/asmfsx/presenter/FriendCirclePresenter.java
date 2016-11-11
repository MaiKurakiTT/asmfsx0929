package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.FriendCircleBiz;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCirclePresenter implements FriendCircleContract.Presenter {
    private FriendCircleContract.View view;
    private FriendCircleContract.IFriendCircleBiz friendCircleBiz;
    private int page = 1;
    private int pageSize = 20;

    public FriendCirclePresenter(FriendCircleContract.View view) {
        this.view = view;
        this.friendCircleBiz = new FriendCircleBiz();
    }

    @Override
    public void start() {
        FriendCircleBean friendCircleBean = new FriendCircleBean();
        friendCircleBean.setUUID(view.getUUID());
        friendCircleBean.setFriendsCircle_pageNow(1);
        friendCircleBean.setFriendsCircle_pageSize(pageSize);
        requestData(friendCircleBean, 0);
    }

    @Override
    public void loadMore() {
        FriendCircleBean friendCircleBean = new FriendCircleBean();
        friendCircleBean.setUUID(view.getUUID());
        friendCircleBean.setFriendsCircle_pageNow(page);
        friendCircleBean.setFriendsCircle_pageSize(pageSize);
        requestData(friendCircleBean, 1);
    }

    /**
     * 请求数据
     * @param friendCircleBean
     * @param type 0为正常刷新数据，1为加载更多数据
     */
    private void requestData(FriendCircleBean friendCircleBean, final int type) {
        view.showLoading();
        friendCircleBiz.friendCircle(friendCircleBean, new FriendCircleContract.IFriendCircleBiz.OnFriendCircleListener() {
            @Override
            public void success(final BaseBean baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0) {
                            view.showData(baseBean);
                        }else {
                            view.showMoreData(baseBean);
                        }
                        page++;
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0) {
                            view.showFailed();
                        }else {
                            view.showFailedForLoadMore();
                        }
                        view.hideLoading();
                    }
                });
            }
        });
    }
}
