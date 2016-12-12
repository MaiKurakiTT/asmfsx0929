package com.hsd.asmfsx.base;


import com.hsd.asmfsx.bean.BaseBean2;

/**
 * Created by apple on 16/9/29.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showFailedForException(Throwable t);
}
