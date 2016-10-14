package com.hsd.asmfsx.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsd.asmfsx.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 16/10/6.
 */

public class RegisterAndLoginActivity extends AppCompatActivity {
    public String TAG = "RegisterAndLogin";
    @BindView(R.id.reg_username)
    EditText regUsername;
    @BindView(R.id.reg_password)
    EditText regPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    private String password;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);
        ButterKnife.bind(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = regUsername.getText().toString();
                password = regPassword.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //注册失败会抛出HyphenateException
                        try {
                            EMClient.getInstance().createAccount(username, password);//同步方法
                            Log.d(TAG, "注册成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            Log.d(TAG, "注册失败: " + e.getErrorCode() + ", " + e.getDescription());
                        }
                    }
                }).start();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = regUsername.getText().toString();
                password = regPassword.getText().toString();
                EMClient.getInstance().login(username,password,new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d(TAG, "登录聊天服务器成功！");
                        startActivity(new Intent(RegisterAndLoginActivity.this, ChatWithActivity.class));
                        finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d(TAG, "登录聊天服务器失败！" + code + ", " + message);
                    }
                });

            }
        });
    }
}
