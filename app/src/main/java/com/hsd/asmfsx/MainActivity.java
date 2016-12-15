package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.adapter.SeeAdapter;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.chat.ChatActivity;
import com.hsd.asmfsx.chat.RegAndLogin;
import com.hsd.asmfsx.contract.GetUserInfoContract;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.model.RetrofitService;
import com.hsd.asmfsx.model.UploadImgBiz;
import com.hsd.asmfsx.presenter.GetUserInfoPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.hsd.asmfsx.view.activity.CertificationActivity;
import com.hsd.asmfsx.view.activity.FindFriendsActivity;
import com.hsd.asmfsx.view.activity.FriendCircleActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
import com.hsd.asmfsx.view.activity.OrderListActivity;
import com.hsd.asmfsx.view.activity.RegisterActivity;
import com.hsd.asmfsx.view.activity.SetAfterRegisterActivity;
import com.hsd.asmfsx.view.activity.ShopHomeActivity;
import com.hsd.asmfsx.view.activity.UserInfoActivity;
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
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class MainActivity extends AppCompatActivity implements EMMessageListener, GetUserInfoContract.View{
    public String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.head)
    CircleImageView headImg;
    @BindView(R.id.navigation_view_left)
    NavigationView navigationViewLeft;
    @BindView(R.id.drawer_view)
    DrawerLayout drawerView;
    @BindView(R.id.navigation_view_right)
    NavigationView navigationViewRight;
    @BindView(R.id.bottom_but)
    ImageButton bottomImgBut;
    private EaseUI easeUI;

    private RecyclerView rightRecycle;
    private RecyclerView seeRecycleView;

    private String[] seeTitles = {"朋友圈", "消息", "团购", "注册", "登录", "朋友圈", "实名认证", "设置信息", "聊天", "好友", "找朋友"};
    private int[] seeImgs = {R.mipmap.ic_news, R.mipmap.ic_mail, R.mipmap.ic_show,
            R.mipmap.ic_log, R.mipmap.ic_game, R.mipmap.ic_traffic, R.mipmap.ic_traffic, R.mipmap.ic_traffic
            , R.mipmap.ic_traffic, R.mipmap.ic_traffic, R.mipmap.ic_traffic};
    private UserInformationBean2 mUserInfo;
    private View leftNaviHeadView;
    private CircleImageView naviHeadImg;
    private TextView naviNickName;
    private GetUserInfoPresenter getUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initData();
        initView();
        easeUIInit();
        requestData();
        setData2View();
    }

    private void requestData() {
        getUserInfoPresenter = new GetUserInfoPresenter(this);
        getUserInfoPresenter.start();
    }

    private void setData2View() {

    }

    private void initData() {

    }


    private void initView() {
        initToolbar();
        initListener();
        initBottomSheet();
        initLeftNavigation();
        initRightNavigation();

    }

    private void initRightNavigation() {
        View headerView = navigationViewRight.getHeaderView(0);
        rightRecycle = (RecyclerView) headerView.findViewById(R.id.right_navi_recycle);
        rightRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initLeftNavigation() {
        leftNaviHeadView = navigationViewLeft.getHeaderView(0);
        navigationViewLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_menu_1:
                        startActivity(new Intent(MainActivity.this, OrderListActivity.class));
                        drawerView.closeDrawers();
                        break;
                }
                return true;
            }
        });
        naviHeadImg = (CircleImageView)leftNaviHeadView.findViewById(R.id.navi_head);
        naviNickName = (TextView) leftNaviHeadView.findViewById(R.id.navi_name);
        leftNaviHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("userInformationBean", mUserInfo);
                startActivityForResult(intent, 0);
                drawerView.closeDrawers();
            }
        });
        naviHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowToast.show(MainActivity.this, "头像");
            }
        });
    }

    private void initBottomSheet() {
        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        seeRecycleView = (RecyclerView) bottomSheet.findViewById(R.id.see_recycle);
        final BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottomImgBut.setImageResource(R.mipmap.ic_up);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        bottomImgBut.setImageResource(R.mipmap.ic_down);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        SeeAdapter seeAdapter = new SeeAdapter(this, seeTitles, seeImgs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        seeRecycleView.setLayoutManager(linearLayoutManager);
        seeRecycleView.setAdapter(seeAdapter);
        seeAdapter.setOnItemClickListener(new SeeAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, FriendCircleActivity.class));
                        break;
                    case 1:

                        break;
                    case 2:
                        Intent intent = new Intent(MainActivity.this, ShopHomeActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, FriendCircleActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, CertificationActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, SetAfterRegisterActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, RegAndLogin.class));
                        break;
                    case 9:
//                        presenter.getData();
                        break;
                    case 10:
                        startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
                        break;
                }
            }
        });
        bottomImgBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(sheetBehavior);
            }
        });
    }

    private void showBottomSheet(BottomSheetBehavior<View> sheetBehavior) {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void initListener() {
        headImg.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //从UserInfoActivity回传过来，更新界面信息
        if (resultCode == 0){
            UserInformationBean2 userInformationBean = (UserInformationBean2) data.getSerializableExtra("userInformationBean");
            if (userInformationBean != null){
                naviNickName.setText("" + userInformationBean.getNickname());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        final EaseUser easeUser = new EaseUser("环信");
        easeUser.setInitialLetter("haha");
        easeUser.setAvatar("http://pics.sc.chinaz.com/files/pic/pic9/201508/apic14052.jpg");
//        UserInfoProvider userInfoProvider = new UserInfoProvider("测试", "https://www.google.com/logos/doodles/2016/united-states-elections-2016-reminder-day-1-5669879209263104-hp.jpg");
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                //通过传来的用户的username在APP服务器查询该用户的信息，通过内容提供着EaseUser传给EaseUI
                return easeUser;
            }
        });
    }


    @Override
    protected void onDestroy() {
        EMClient.getInstance().logout(true);
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

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public void showDataForUserInfo(UserInformationBean2 userInformationBean) {
        mUserInfo = userInformationBean;
        Glide.with(this).load(userInformationBean.getIcon()).into(headImg);
        Glide.with(this).load(userInformationBean.getIcon()).into(naviHeadImg);
        naviNickName.setText("" + userInformationBean.getNickname());
    }

    @Override
    public void showFailedForUserInfoResult(BaseBean2 baseBean) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailedForException(Throwable t) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
