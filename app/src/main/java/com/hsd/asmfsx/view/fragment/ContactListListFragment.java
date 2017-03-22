package com.hsd.asmfsx.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.hsd.asmfsx.chat.ChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.orhanobut.logger.Logger;

/**
 * Created by sun on 2017/3/16.
 */

public class ContactListListFragment extends EaseConversationListFragment {
    @Override
    protected void setUpView() {
        super.setUpView();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EMConversation conversation = conversationListView.getItem(i);
                Logger.d("ContactListListFragment", conversation + "");
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("name", conversation.getUserName()));
            }
        });
    }
}
