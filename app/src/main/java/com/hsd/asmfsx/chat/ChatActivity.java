package com.hsd.asmfsx.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hsd.asmfsx.R;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * Created by apple on 2016/11/5.
 */

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        String name = getIntent().getStringExtra("name");
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.hideTitleBar();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, name);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_parent, chatFragment).commit();

    }
}
