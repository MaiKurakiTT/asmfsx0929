package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean2;

/**
 * Created by sun on 2016/12/6.
 */

public interface BaseListener {
    interface OnRequestListener<T>{
        void success(T t);
        void failedForResult(BaseBean2 baseBean);
        void failedForException(Throwable t);
    }
}
