package com.hsd.asmfsx.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.chat.ChatActivity;
import com.hsd.asmfsx.db.DBUserBean;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

/**
 * Created by sun on 2016/12/15.
 */

public class MyService extends Service implements EMMessageListener {

    private EaseUI easeUI;

    @Override
    public void onCreate() {
        super.onCreate();
        easeUIInit();
    }

    /**
     * 进行EaseUI和环信的一些设置
     */
    private void easeUIInit() {
        EMClient.getInstance().chatManager().addMessageListener(this);
        easeUI = EaseUI.getInstance();
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, MyService.this);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                String sendNick = "";
                try {
                    sendNick = message.getStringAttribute("sendNick");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return sendNick + ": " + ticker;
                } else {
                    return sendNick + ": " + ticker;
                }

            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                String sendNick = "";
                try {
                    sendNick = message.getStringAttribute("sendNick");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                return sendNick + "发来消息";
            }

            @Override
            public String getTitle(EMMessage message) {
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                return R.mipmap.icon_icon;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                Intent intent = new Intent(MyService.this, ChatActivity.class);
                intent.putExtra("name", message.getFrom());
                return intent;
            }
        });
//        final EaseUser easeUser = new EaseUser("环信ceshi");
//        easeUser.setInitialLetter("haha");
//        easeUser.setAvatar("http://pics.sc.chinaz.com/files/pic/pic9/201508/apic14052.jpg");
//        UserInfoProvider userInfoProvider = new UserInfoProvider("测试", "https://www.google.com/logos/doodles/2016/united-states-elections-2016-reminder-day-1-5669879209263104-hp.jpg");
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                //通过传来的用户的username在APP服务器查询该用户的信息，通过内容提供着EaseUser传给EaseUI
                EaseUser easeUser = new EaseUser(username);
                List<DBUserBean> dbUserBeanList = DataSupport.where("userId = ?", username).find(DBUserBean.class);
                if (dbUserBeanList.size() > 0){
                    easeUser.setAvatar(dbUserBeanList.get(0).getIcon());
                    easeUser.setNick(dbUserBeanList.get(0).getNickname());
                }
                return easeUser;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * 实现EMMessageListener接口，保证全局都能收到消息
     *
     * @param list
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (EMMessage message : list) {
            EMLog.d("Helper", "onMessageReceived id : " + message.getMsgId());
            Logger.d("消息来了: " + message.getBody().toString());
            //应用在后台，不需要刷新UI,通知栏提示新消息
            if (!easeUI.hasForegroundActivies()) {
                easeUI.getNotifier().onNewMsg(message);
            }
            String sendNick = "";
            String sendIcon = "";
            try {
                sendNick =   message.getStringAttribute("sendNick");
                sendIcon = message.getStringAttribute("sendIcon");
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            List<DBUserBean> dbUserBeanList = DataSupport.where("userId = ?", message.getFrom()).find(DBUserBean.class);
            if (dbUserBeanList.size() > 0){
                DataSupport.delete(DBUserBean.class, dbUserBeanList.get(0).getId());
            }
            DBUserBean dbUserBean = new DBUserBean();
            dbUserBean.setUserId(Long.valueOf(message.getFrom()));
            dbUserBean.setNickname(sendNick);
            dbUserBean.setIcon(sendIcon);
            dbUserBean.save();
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

    class MyBinder extends Binder{

    }
}
