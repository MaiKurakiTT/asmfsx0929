package com.hsd.asmfsx.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsd.asmfsx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 16/10/6.
 */

public class ChatWithActivity extends AppCompatActivity {
    @BindView(R.id.withname)
    EditText withname;
    @BindView(R.id.startchat)
    Button startchat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_with);
        ButterKnife.bind(this);
        startchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String withName = withname.getText().toString();
                Intent intent = new Intent(ChatWithActivity.this, ChatActivity.class);
                intent.putExtra("withName", withName);
                startActivity(intent);
            }
        });
    }
}
