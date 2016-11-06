package com.hsd.asmfsx.app;

import android.app.Application;

import com.hsd.asmfsx.Helper;
import com.hsd.asmfsx.db.DbCore;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * Created by apple on 16/10/6.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("MyLog");
        //初始化数据库
        DbCore.init(this);
        DbCore.enableQueryBuilderLog(); //开启调试 log
        EaseUI.getInstance().init(this, new EMOptions());
//        Helper.getInstance().init();
        EMClient.getInstance().setDebugMode(true);
    }


}
