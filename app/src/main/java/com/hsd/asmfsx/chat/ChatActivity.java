package com.hsd.asmfsx.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 16/10/6.
 */

public class ChatActivity extends AppCompatActivity implements EMMessageListener{
    private String TAG = "ChatActivity";
    @BindView(R.id.msg_text)
    TextView msgText;
    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.send)
    Button send;
    private EMMessageListener mMessageListener;
    private String toChatUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        ButterKnife.bind(this);
        mMessageListener = this;
        Intent intent = getIntent();
        toChatUsername = intent.getStringExtra("withName");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = edittext.getText().toString();
                msgText.setText(msgText.getText() + "\n 发送" + content);
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
//                //如果是群聊，设置chattype，默认是单聊
//                if (chatType == CHATTYPE_GROUP)
//                    message.setChatType(EMMessage.ChatType.GroupChat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
//                        msgText.setText(msgText.getText() + "\n 发送" + content);
                        Log.d(TAG, "发送成功" + content);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "发送失败");
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 添加消息监听
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 移除消息监听
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){

                msgText.setText(msgText.getText() + "\n 接受" + msg.obj);
            }
        }
    };
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for(final EMMessage message : list){
            if (message.getFrom().equals(toChatUsername)){
                        EMTextMessageBody messageBody = (EMTextMessageBody) message.getBody();
                        Message msg = new Message();
                        msg.what = 0;
                msg.obj = messageBody.getMessage();
                mHandler.sendMessage(msg);
                        Log.d(TAG, "接收成功:" + messageBody.getMessage());

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
}
