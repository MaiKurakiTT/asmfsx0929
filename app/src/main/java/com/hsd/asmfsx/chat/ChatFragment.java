package com.hsd.asmfsx.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.utils.SPUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.hsd.asmfsx.view.activity.UserInfoActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

/**
 * Created by apple on 2016/11/21.
 * 继承自EaseChatFragment，实现一些聊天界面的方法
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper{

    private String myIcon;
    private String myNick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SPUtils spUtils = SPUtils.getInstance("asmfsx");
        myIcon = spUtils.getString("myIcon");
        myNick = spUtils.getString("myNick");
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        super.setUpView();
        //在super.setUpView();之后设置界面内容才有效
        titleBar.setBackgroundColor(getResources().getColor(R.color.primary));
//        titleBar.setRightImageResource(R.mipmap.ic_user);
    }
    /**
     * 设置消息扩展属性
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        message.setAttribute("sendIcon", myIcon);
        message.setAttribute("sendNick", myNick);
    }
    /**
     * 进入会话详情
     */
    @Override
    public void onEnterToChatDetails() {

    }
    /**
     * 用户头像点击事件
     * @param username
     */
    @Override
    public void onAvatarClick(String username) {
        SPUtils spUtils = SPUtils.getInstance("asmfsx");
        String myId = spUtils.getString("myId");
        if (!myId.equals(username)){
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("userID", Long.valueOf(username));
            startActivity(intent);
        }
    }

    @Override
    public void onAvatarLongClick(String username) {

    }
    /**
     * 消息气泡框点击事件
     */
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }
    /**
     * 消息气泡框长按事件
     */
    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }
    /**
     * 扩展输入栏item点击事件,如果要覆盖EaseChatFragment已有的点击事件，return true
     * @param view
     * @param itemId
     * @return
     */
    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }
    /**
     * 设置自定义chatrow提供者
     * @return
     */
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}
