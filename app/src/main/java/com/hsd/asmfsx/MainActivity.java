package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hsd.asmfsx.adapter.HeartBeatListAdapter;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.chat.ChatActivity;
import com.hsd.asmfsx.chat.RegAndLogin;
import com.hsd.asmfsx.contract.RequestHeartBeatContract;
import com.hsd.asmfsx.model.UploadImgBiz;
import com.hsd.asmfsx.presenter.RequestHeartBeatPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.hsd.asmfsx.view.activity.CertificationActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
import com.hsd.asmfsx.view.activity.RegisterActivity;
import com.hsd.asmfsx.view.activity.SetAfterRegisterActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class MainActivity extends AppCompatActivity implements RequestHeartBeatContract.View, EMMessageListener {
    public String TAG = "MainActivity";
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.log)
    Button log;
    @BindView(R.id.cer)
    Button cer;
    @BindView(R.id.set)
    Button set;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.tab_parent)
    FrameLayout tabParent;
    private RequestHeartBeatPresenter presenter;
    private EaseUI easeUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        EMClient.getInstance().chatManager().addMessageListener(this);
        easeUI = EaseUI.getInstance();
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, MainActivity.this);
                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if(user != null){
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                }else{
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return message.getFrom() + "发来消息";
            }

            @Override
            public String getTitle(EMMessage message) {
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                return 0;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("name", message.getFrom());
                return intent;
            }
        });
        presenter = new RequestHeartBeatPresenter(this);
        button = (Button) findViewById(R.id.button);
        textview = (TextView) findViewById(R.id.textview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        recycle = (RecyclerView) findViewById(R.id.recycle);

        recycle.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
//                startActivity(new Intent(MainActivity.this, SetAfterRegisterActivity.class));
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
//                startActivity(new Intent(MainActivity.this, CertificationActivity.class));
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                startActivity(new Intent(MainActivity.this, TestRetrofit.class));
//                presenter.getData();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        cer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CertificationActivity.class));
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SetAfterRegisterActivity.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegAndLogin.class));
            }
        });
    }

    private void testup() throws IOException {
        UploadImgBiz.getInstance().uploadImg("androidschoolbus.jpg", new UploadImgBiz.OnUploadListener() {
            @Override
            public void success(BaseBean baseBean) {
                if (baseBean.getResultCode() == 1) {
                    Logger.d("上传成功，" + baseBean.getBody());
                } else {
                    Logger.d("" + baseBean.getDescribe());
                }
            }

            @Override
            public void failed() {

            }
        });
    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public String getUuid() {
        return "84f4b998-17df-4997-8fc2-828f89aec37d";
    }

    @Override
    public void showData(List<UserInformationBean> userInformation) {
        HeartBeatListAdapter adapter = new HeartBeatListAdapter(this, userInformation);
        recycle.setAdapter(adapter);
    }

    @Override
    public void showFailed() {
        Toast.makeText(this, "failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (EMMessage message : list) {
            EMLog.d("Helper", "onMessageReceived id : " + message.getMsgId());
            Logger.d("消息来了: " + message.getBody().toString());
            //应用在后台，不需要刷新UI,通知栏提示新消息
            if (!easeUI.hasForegroundActivies()) {
                easeUI.getNotifier().onNewMsg(message);
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
