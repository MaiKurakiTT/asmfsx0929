package com.hsd.asmfsx.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.orhanobut.logger.Logger;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import cn.smssdk.SMSSDK;


/**
 * Created by apple on 16/10/6.
 */

public class MyApplication extends LitePalApplication {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.init("MyLog");
        //初始化数据库
        SQLiteDatabase db = Connector.getDatabase();
        EaseUI.getInstance().init(this, new EMOptions());
        EMClient.getInstance().setDebugMode(true);

        SMSSDK.initSDK(this, "19537e46b6692", "e1afbf73ef9df65ce566b1a0d1cb785a");


    }
    public static Context getAppContext(){
        return context;
    }



}
