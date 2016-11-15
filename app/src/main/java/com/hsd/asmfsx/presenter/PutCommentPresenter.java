package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.contract.PutCommentContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.PutCommentBiz;

/**
 * Created by apple on 2016/11/15.
 */

public class PutCommentPresenter implements PutCommentContract.Presenter{
    private PutCommentContract.View view;
    private PutCommentContract.IPutCommentBiz putCommentBiz;

    public PutCommentPresenter(PutCommentContract.View view) {
        this.view = view;
        this.putCommentBiz = new PutCommentBiz();
    }

    @Override
    public void start() {
        view.showLoadingForComment();
        putCommentBiz.doPutComment(view.getFriendCircleBeanForComment(), new PutCommentContract.IPutCommentBiz.OnPutCommentListener() {
            @Override
            public void success(final BaseBean baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForComment(baseBean);
                        view.hideLoadingForComment();
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForComment();
                        view.hideLoadingForComment();
                    }
                });
            }
        });
    }
}
