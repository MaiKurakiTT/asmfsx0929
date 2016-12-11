package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hsd.asmfsx.adapter.HeartBeatListAdapter;
import com.hsd.asmfsx.adapter.SeeAdapter;
import com.hsd.asmfsx.app.MyApplication;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.LoginBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.chat.ChatActivity;
import com.hsd.asmfsx.chat.RegAndLogin;
import com.hsd.asmfsx.contract.RequestHeartBeatContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.LoginBiz;
import com.hsd.asmfsx.model.RetrofitService;
import com.hsd.asmfsx.model.UploadImgBiz;
import com.hsd.asmfsx.model.UploadImgBiz2;
import com.hsd.asmfsx.presenter.RequestHeartBeatPresenter;
import com.hsd.asmfsx.view.activity.CertificationActivity;
import com.hsd.asmfsx.view.activity.FindFriendsActivity;
import com.hsd.asmfsx.view.activity.FriendCircleActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.bottom_but)
    ImageButton bottomImgBut;
    @BindView(R.id.bottombutton2)
    Button bottombutton2;
    private RequestHeartBeatPresenter presenter;
    private EaseUI easeUI;

    private RecyclerView rightRecycle;
    private RecyclerView seeRecycleView;

    private String[] seeTitles = {"朋友圈", "消息", "show出来", "日志", "休闲", "旅行"};
    private int[] seeImgs = {R.mipmap.ic_news, R.mipmap.ic_mail, R.mipmap.ic_show,
            R.mipmap.ic_log, R.mipmap.ic_game, R.mipmap.ic_traffic};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        easeUIInit();

        presenter = new RequestHeartBeatPresenter(this);
        button = (Button) findViewById(R.id.button);


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
        bottombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testNewApi();
            }
        });
        bottombutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testNew2();
                testNew3();
            }
        });
        findViewById(R.id.bottombutton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testUpDate();
                testNew2();
            }
        });
    }

    private void testUpDate() {
        UserInformationBean2 userInformationBean2 = new UserInformationBean2();
        userInformationBean2.setNickname("yy");
        GetRetrofit.getRetrofit2()
                .create(RetrofitService.class)
                .postSetUserInfo(userInformationBean2)
                .enqueue(new Callback<NormalResultBean<UserBean2>>() {
                    @Override
                    public void onResponse(Call<NormalResultBean<UserBean2>> call, Response<NormalResultBean<UserBean2>> response) {
                        NormalResultBean<UserBean2> body = response.body();
                    }

                    @Override
                    public void onFailure(Call<NormalResultBean<UserBean2>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void testNewApi() {
        new LoginBiz().login("1", "1", new BaseListener.OnRequestListener<UserBean2>() {
            @Override
            public void success(UserBean2 userBean2) {
                Logger.d(userBean2.getPhone());
            }

            @Override
            public void failedForResult(BaseBean2 baseBean) {
                Logger.d(baseBean.getMsg());
            }

            @Override
            public void failedForException(Throwable t) {
                Logger.d("抛异常了");
            }
        });

    }

    private void testNew2() {
    }
    private void testNew3(){
        RetrofitService service = GetRetrofit.getRetrofit2().create(RetrofitService.class);
        Call<Object> call = service.postGetMe();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object body = response.body();
                Logger.d(body);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                Logger.d(t.toString());
            }
        });
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
        View leftHeadView = navigationViewLeft.getHeaderView(0);
        leftHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0);
                drawerView.closeDrawers();
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

                        break;
                    case 1:

                        break;
                    case 2:
                        Intent intent = new Intent(MainActivity.this, ShopHomeActivity.class);
                        startActivity(intent);
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

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
    public void showFailedForException(Throwable t) {

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
}
