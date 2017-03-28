package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShiMingBean;

/**
 * Created by sun on 2017/3/28.
 * 获取实名认证的状态
 */

public interface GetShiMingStateContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        void showDataForState(NormalResultBean<ShiMingBean> resultBean);
        void showFiledForState(BaseBean2 baseBean2);
    }
    interface IGetShiMingStateBiz{
        interface OnGetShiMingStateListener{
            void success(NormalResultBean<ShiMingBean> resultBean);
            void failedForResult(BaseBean2 baseBean);
            void failedForException(Throwable t);
        }
        void doGetShiMingState(OnGetShiMingStateListener getShiMingStateListener);
    }
}
