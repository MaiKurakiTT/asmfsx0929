package com.hsd.asmfsx.global;

import android.os.Handler;

/**
 * Created by apple on 2016/10/16.
 */

public class GetHandler {
    static Handler mHandler;
    public static Handler getHandler(){
        if (mHandler == null){
            mHandler = new Handler();
        }
        return mHandler;
    }
}
