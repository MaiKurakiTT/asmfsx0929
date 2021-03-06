package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.PutCommentContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.BaseListener;
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
        view.showLoadingForPutComment();
        putCommentBiz.doPutComment(view.getCommentContent(), view.getCommentFCId(), view.getByUserId(), new BaseListener.OnRequestListener<BaseBean2>() {
            @Override
            public void success(final BaseBean2 baseBean2) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForComment(baseBean2);
                        view.hideLoadingForPutComment();
                    }
                });
            }

            @Override
            public void failedForResult(final BaseBean2 baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForPutCommentResult(baseBean);
                        view.hideLoadingForPutComment();
                    }
                });
            }

            @Override
            public void failedForException(final Throwable t) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showExceptionForPutCommentResult(t);
                        view.hideLoadingForPutComment();
                    }
                });
            }
        });
    }
}
