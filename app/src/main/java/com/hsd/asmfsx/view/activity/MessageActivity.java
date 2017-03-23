package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.view.fragment.MessageListFragment;

/**
 * Created by sun on 2017/3/16.
 */

public class MessageActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity_layout);
        MessageListFragment messageListFragment = new MessageListFragment();
//        EaseConversationListFragment easeConversationListFragment = new EaseConversationListFragment();
        /*easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MessageActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }
        });*/
        getSupportFragmentManager().beginTransaction().add(R.id.message_parent, messageListFragment).commit();
    }
}
