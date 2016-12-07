package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.FriendCircleVO;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.FriendCircleBiz;

import java.util.List;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCirclePresenter implements FriendCircleContract.Presenter {
    private FriendCircleContract.View view;
    private FriendCircleContract.IFriendCircleBiz friendCircleBiz;
    private int page = 2; //页码，默认1
    private int limit = 10; //每页条数，默认10

    public FriendCirclePresenter(FriendCircleContract.View view) {
        this.view = view;
        this.friendCircleBiz = new FriendCircleBiz();
    }

    @Override
    public void start() {
        requestData(0);
    }

    @Override
    public void loadMore() {
        requestData(1);
    }

    /**
     * 请求数据
     * @param type 0为正常刷新数据，1为加载更多数据
     */
    private void requestData(final int type) {
        if (type == 0){
            view.showLoading();
            page = 1;
        }
        friendCircleBiz.friendCircle(page, limit, new BaseListener.OnRequestListener<List<FriendCircleVO>>() {
            @Override
            public void success(final List<FriendCircleVO> friendCircleVOs) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 0) {
                            view.showData(friendCircleVOs);
                        }else {
                            view.showMoreData(friendCircleVOs);
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
                            view.showFailedForLoadMoreResult(baseBean);
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
                            view.showFailedForLoadMoreException(t);
                        }
                        view.hideLoading();
                    }
                });
            }
        });
    }
}
