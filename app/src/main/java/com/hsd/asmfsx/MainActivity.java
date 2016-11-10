package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.hsd.asmfsx.view.activity.CertificationActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
import com.hsd.asmfsx.view.activity.RegisterActivity;
import com.hsd.asmfsx.view.activity.SetAfterRegisterActivity;
import com.hsd.asmfsx.view.fragment.FriendsFragment;
import com.hsd.asmfsx.view.fragment.HomeFragment;
import com.hsd.asmfsx.view.fragment.SeeFragment;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class MainActivity extends AppCompatActivity implements RequestHeartBeatContract.View, EMMessageListener {
    public String TAG = "MainActivity";
    @BindView(R.id.button)
    Button button;
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
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.firstrb)
    RadioButton firstrb;
    @BindView(R.id.secondrb)
    RadioButton secondrb;
    @BindView(R.id.thirdrb)
    RadioButton thirdrb;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.fragment_parent)
    FrameLayout fragmentParent;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_view)
    DrawerLayout drawerView;
    private RequestHeartBeatPresenter presenter;
    private EaseUI easeUI;

    private String[] tabTitles = {"首页", "发现", "好友"};
    private Class[] classTabs = {HomeFragment.class, SeeFragment.class, FriendsFragment.class};
    private int position = 0;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        easeUIInit();

        presenter = new RequestHeartBeatPresenter(this);
        button = (Button) findViewById(R.id.button);
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

    private void initView() {
        initToolbar();
        initFragment();
        initListener();

    }

    private void initListener() {
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerView.openDrawer(GravityCompat.START);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (!radioButton.isChecked()) {
                    return;
                }
                switch (checkedId) {
                    case R.id.firstrb:
                        position = 0;
                        toolbarRighttext.setVisibility(View.VISIBLE);
                        toolbarCentertext.setText("首页");
                        toolbarRighttext.setText("更多");
                        break;
                    case R.id.secondrb:
                        position = 1;
                        toolbarRighttext.setVisibility(View.GONE);
                        toolbarCentertext.setText("发现");
                        break;
                    case R.id.thirdrb:
                        position = 2;
                        toolbarRighttext.setVisibility(View.GONE);
                        toolbarCentertext.setText("好友");
                        break;
                }
                showFragment(fragmentList.get(position));
                Logger.d("显示" + position);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("首页");
        toolbarRighttext.setText("更多");
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SeeFragment());
        fragmentList.add(new FriendsFragment());
        mFragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentList.size(); i++) {
            mFragmentManager.beginTransaction().add(R.id.fragment_parent, fragmentList.get(i)).commit();
        }
        firstrb.setChecked(true);
        showFragment(fragmentList.get(0));
    }

    /**
     * 显示某个fragment
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentTransaction.hide(fragmentList.get(i));
        }
    }


    /**
     * 进行EaseUI和环信的一些设置
     */
    private void easeUIInit() {
        EMClient.getInstance().chatManager().addMessageListener(this);
        easeUI = EaseUI.getInstance();
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, MainActivity.this);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
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

    }

    @Override
    public void hideLoading() {

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

    /**
     * 实现EMMessageListener接口，保证全局都能收到消息
     *
     * @param list
     */
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
