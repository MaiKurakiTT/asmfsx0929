package com.hsd.asmfsx;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.util.EMLog;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by apple on 2016/11/5.
 */

public class Helper {
    private EaseUI easeUI;
    protected EMMessageListener messageListener = null;
    static Helper helper;
    public static Helper getInstance(){
        if (helper == null){
            helper = new Helper();
        }
        return helper;
    }

    public void init(){
        easeUI = EaseUI.getInstance();
        registerEventListener();
    }

    private void registerEventListener() {
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                for (EMMessage message : list) {
                    EMLog.d("Helper", "onMessageReceived id : " + message.getMsgId());
                    Logger.d("消息来了: " + message.getBody().toString());
                    //应用在后台，不需要刷新UI,通知栏提示新消息
                    if(!easeUI.hasForegroundActivies()){
                        getNotifier().onNewMsg(message);
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    public EaseNotifier getNotifier(){
        return easeUI.getNotifier();
    }
}
