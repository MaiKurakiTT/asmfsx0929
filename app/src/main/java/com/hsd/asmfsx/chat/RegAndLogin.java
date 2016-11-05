package com.hsd.asmfsx.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.global.GetHandler;
import com.hsd.asmfsx.utils.ShowToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/11/5.
 */

public class RegAndLogin extends AppCompatActivity {
    @BindView(R.id.reg_username)
    EditText regUsername;
    @BindView(R.id.reg_password)
    EditText regPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerandlogin_layout);
        ButterKnife.bind(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = regUsername.getText().toString().trim();
                password = regPassword.getText().toString().trim();
                doRegister();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = regUsername.getText().toString().trim();
                password = regPassword.getText().toString().trim();
                doLogin();
            }
        });
    }
    private void doRegister(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EMClient.getInstance().createAccount(username, password);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowToast.show(RegAndLogin.this, "注册成功");
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowToast.show(RegAndLogin.this,
                                    "注册失败，错误码：" + e.getErrorCode() + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }
    private void doLogin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(username, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowToast.show(RegAndLogin.this, "登录成功");
                                startActivity(new Intent(RegAndLogin.this, ChatWithActivity.class));
                            }
                        });
                    }

                    @Override
                    public void onError(final int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowToast.show(RegAndLogin.this, "登录失败" + i + s);
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        }).start();
    }
}
