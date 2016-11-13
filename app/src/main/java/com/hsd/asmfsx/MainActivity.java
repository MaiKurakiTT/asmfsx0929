package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.hsd.asmfsx.view.activity.FindFriendsActivity;
import com.hsd.asmfsx.view.activity.FriendCircleActivity;
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
    @BindView(R.id.log)
    Button log;
    @BindView(R.id.cer)
    Button cer;
    @BindView(R.id.set)
    Button set;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.navigation_view_left)
    NavigationView navigationViewLeft;
    @BindView(R.id.drawer_view)
    DrawerLayout drawerView;
    @BindView(R.id.bottombutton)
    Button bottombutton;
    @BindView(R.id.navigation_view_right)
    NavigationView navigationViewRight;
    @BindView(R.id.friendsbut)
    Button friendsbut;
    @BindView(R.id.findfriendbut)
    Button findfriendbut;
    @BindView(R.id.friendcirclebut)
    Button friendcirclebut;
    private RequestHeartBeatPresenter presenter;
    private EaseUI easeUI;

    private String[] tabTitles = {"首页", "发现", "好友"};
    private Class[] classTabs = {HomeFragment.class, SeeFragment.class, FriendsFragment.class};
    private int position = 0;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private RecyclerView rightRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        easeUIInit();

        presenter = new RequestHeartBeatPresenter(this);
        button = (Button) findViewById(R.id.button);

        View headerView = navigationViewRight.getHeaderView(0);
        rightRecycle = (RecyclerView) headerView.findViewById(R.id.right_navi_recycle);
        rightRecycle.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
//                startActivity(new Intent(MainActivity.this, SetAfterRegisterActivity.class));
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
//                startActivity(new Intent(MainActivity.this, FriendCircleActivity.class));
//                startActivity(new Intent(MainActivity.this, CertificationActivity.class));
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                startActivity(new Intent(MainActivity.this, TestRetrofit.class));

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
        friendsbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getData();
            }
        });
        findfriendbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
            }
        });
        friendcirclebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FriendCircleActivity.class));
            }
        });
    }

    private void initView() {
        initToolbar();
        initListener();
        initBottomSheet();

    }

    private void initBottomSheet() {
        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        final BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(sheetBehavior);
            }
        });
    }

    private void showBottomSheet(BottomSheetBehavior<View> sheetBehavior) {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    private void initListener() {
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerView.openDrawer(GravityCompat.START);
            }
        });
        toolbarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerView.openDrawer(GravityCompat.END);
            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("首页");
        toolbarRighttext.setText("好友");
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
        rightRecycle.setAdapter(adapter);
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
