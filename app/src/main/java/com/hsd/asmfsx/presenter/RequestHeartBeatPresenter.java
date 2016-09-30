package com.hsd.asmfsx.presenter;

import android.os.Handler;
import android.util.Log;

import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.RequestHeartBeatContract;
import com.hsd.asmfsx.model.IRequestHeartBeatBiz;
import com.hsd.asmfsx.model.RequestHeartBeatBiz;

import java.util.List;


/**
 * Created by apple on 16/9/29.
 */

public class RequestHeartBeatPresenter implements RequestHeartBeatContract.Presenter{
    public String TAG = "RequestHeartBeatPresenter";
    private IRequestHeartBeatBiz requestBiz;
    private RequestHeartBeatContract.View view;
    private Handler mHandler;
    public RequestHeartBeatPresenter(RequestHeartBeatContract.View view) {
        this.view = view;
        this.requestBiz = new RequestHeartBeatBiz();
        mHandler = new Handler();
    }

    @Override
    public void getData() {
        view.showLoading();
        requestBiz.requestData("", new IRequestHeartBeatBiz.OnRequestListener() {
            @Override
            public void success(final List<UserInformationBean> userInformation) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (userInformation != null) {
                            view.showData(userInformation);
                            view.hideLoading();
                        }else{
                            Log.d(TAG, "userInformation空了");
                        }
                    }
                });
            }

            @Override
            public void failed() {
                mHandler.post(new Runnable() {
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
