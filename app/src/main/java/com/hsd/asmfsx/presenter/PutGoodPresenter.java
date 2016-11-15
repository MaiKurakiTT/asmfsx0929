package com.hsd.asmfsx.presenter;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.contract.PutGoodContract;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.model.PutGoodBiz;

/**
 * Created by apple on 2016/11/15.
 */

public class PutGoodPresenter implements PutGoodContract.Presenter{
    private PutGoodContract.View view;
    private PutGoodContract.IPutGoodBiz putGoodBiz;

    public PutGoodPresenter(PutGoodContract.View view) {
        this.view = view;
        this.putGoodBiz = new PutGoodBiz();
    }

    @Override
    public void start() {
        view.showLoadingForGood();
        putGoodBiz.doPutGood(view.getFriendCircleBeanForGood(), new PutGoodContract.IPutGoodBiz.OnPutGoodListener() {
            @Override
            public void success(final BaseBean baseBean) {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showDataForGood(baseBean);
                        view.hideLoadingForGood();
                    }
                });
            }

            @Override
            public void failed() {
                GetHandler.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.showFailedForGood();
                        view.hideLoadingForGood();
                    }
                });
            }
        });
    }
}
