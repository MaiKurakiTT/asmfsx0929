package com.hsd.asmfsx.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hsd.asmfsx.R;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * Created by apple on 2016/11/5.
 */

public class ChatActivity extends AppCompatActivity {

    private String toName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        toName = getIntent().getStringExtra("name");
        EaseUI easeUI = EaseUI.getInstance();
        final EaseUser easeUser = new EaseUser("环信ID: " + toName);
        easeUser.setAvatar("http://pics.sc.chinaz.com/files/pic/pic9/201508/apic14052.jpg");
//        UserInfoProvider userInfoProvider = new UserInfoProvider("测试", "https://www.google.com/logos/doodles/2016/united-states-elections-2016-reminder-day-1-5669879209263104-hp.jpg");
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return easeUser;
            }
        });
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.hideTitleBar();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, toName);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_parent, chatFragment).commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        /*String username = intent.getStringExtra("name");
        if (toName.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }*/
        super.onNewIntent(intent);
    }
}
