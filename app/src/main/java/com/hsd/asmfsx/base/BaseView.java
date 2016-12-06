package com.hsd.asmfsx.base;


/**
 * Created by apple on 16/9/29.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showFailedForException(Throwable t);
}
