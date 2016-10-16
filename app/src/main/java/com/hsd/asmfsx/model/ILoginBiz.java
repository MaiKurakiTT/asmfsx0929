package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.LoginBean;

/**
 * Created by apple on 2016/10/16.
 */

public interface ILoginBiz {
    interface OnLoginListener{
        void success(LoginBean loginBean);
        void failed();
    }
    void login(String userName, String passWord, String uuid, OnLoginListener loginListener);
}
