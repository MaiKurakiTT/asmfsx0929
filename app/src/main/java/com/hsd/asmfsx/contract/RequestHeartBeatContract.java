package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.UserInformationBean;

import java.util.List;

/**
 * Created by apple on 16/9/29.
 */

public interface RequestHeartBeatContract {
    interface Presenter extends BasePresenter{
        void getData();
    }
    interface View extends BaseView{
        String getUuid();
        void showData(List<UserInformationBean> userInformation);
    }
    interface IRequestHeartBeatBiz {
        interface OnRequestListener{
            void success(List<UserInformationBean> userInformation);
            void failed();
        }
        void requestData(String uuid, IRequestHeartBeatBiz.OnRequestListener requestListener);
    }
}
