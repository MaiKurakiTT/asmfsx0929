package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;

/**
 * Created by sun on 2017/3/28.
 * 填写实名认证信息
 */

public interface ShiMingWriteContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        String getRealName();
        Long getSchoolNum();
        String getImg();
        void showDataForWrite(BaseBean2 baseBean2);
        void showFailedForResultWrite(BaseBean2 baseBean2);
    }
    interface IShiMingWriteBiz{
        interface OnShiMingWriteListener{
            void success(BaseBean2 baseBean);
            void failedForResult(BaseBean2 baseBean);
            void failedForException(Throwable t);
        }
        void doPut(String realName, Long schoolNum, String img, OnShiMingWriteListener shiMingWriteListener);
    }
}
