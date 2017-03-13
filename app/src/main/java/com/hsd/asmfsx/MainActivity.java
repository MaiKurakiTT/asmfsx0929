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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hsd.asmfsx.adapter.HBListAdapter;
import com.hsd.asmfsx.adapter.HBListQuickAdapter;
import com.hsd.asmfsx.adapter.SeeAdapter;
import com.hsd.asmfsx.adapter.SwipeCardViewAdapter;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.chat.ChatActivity;
import com.hsd.asmfsx.chat.RegAndLogin;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.contract.GetUserInfoContract;
import com.hsd.asmfsx.contract.HBListContract;
import com.hsd.asmfsx.model.BaseListener;
import com.hsd.asmfsx.model.FindFriendsBiz;
import com.hsd.asmfsx.presenter.FindFriendsPresenter;
import com.hsd.asmfsx.presenter.GetUserInfoPresenter;
import com.hsd.asmfsx.presenter.HBListPresenter;
import com.hsd.asmfsx.service.MyService;
import com.hsd.asmfsx.utils.ShowToast;
import com.hsd.asmfsx.view.activity.BigHeadImgActivity;
import com.hsd.asmfsx.view.activity.CertificationActivity;
import com.hsd.asmfsx.view.activity.FindFriendsActivity2;
import com.hsd.asmfsx.view.activity.FriendCircleActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
import com.hsd.asmfsx.view.activity.OrderListActivity;
import com.hsd.asmfsx.view.activity.RegisterActivity;
import com.hsd.asmfsx.view.activity.SetAfterRegisterActivity;
import com.hsd.asmfsx.view.activity.ShopHomeActivity;
import com.hsd.asmfsx.view.activity.UserInfoActivity;
import com.hsd.asmfsx.widget.swipecardview.SwipeFlingAdapterView;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GetUserInfoContract.View , HBListContract.View, FindFriendsContract.View{
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
    @BindView(R.id.swipe_fling_view)
    SwipeFlingAdapterView swipeFlingView;

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


    private List<UserInformationBean2> mList = new ArrayList<>();
    private BottomSheetBehavior<View> sheetBehavior;
    private SwipeCardViewAdapter swipeCardViewAdapter;
    private HBListPresenter hbListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
        requestData();
        setData2View();
    }

    private void requestData() {
        getUserInfoPresenter = new GetUserInfoPresenter(this);
        getUserInfoPresenter.start();
        hbListPresenter = new HBListPresenter(this);
//        hbListPresenter.start();
        FindFriendsPresenter findFriendsPresenter = new FindFriendsPresenter(this);
        findFriendsPresenter.start();
    }

    private void setData2View() {

    }

    private void initData() {
        startService(new Intent(MainActivity.this, MyService.class));
    }


    private void initView() {
        initToolbar();
        initListener();
        initBottomSheet();
        initLeftNavigation();
        initRightNavigation();
        initSwipCardView();
    }

    private void initSwipCardView() {
        /*for (int i = 0; i<20; i++){
            UserInformationBean2 userInformationBean2 = new UserInformationBean2();
            userInformationBean2.setNickname("i" + i);
            mList.add(userInformationBean2);
        }*/


        swipeFlingView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(MotionEvent event, View v, Object dataObject) {
                UserInformationBean2 data = (UserInformationBean2) dataObject;
                if (data != null) {
                    ShowToast.show(MainActivity.this, "点击了" + data.getNickname());
                    Long id = data.getId();
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("userID", id);
                    intent.putExtra("userInformationBean", data);
                    startActivity(intent);
                }
            }
        });
        swipeFlingView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                swipeCardViewAdapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

            }

            @Override
            public void onRightCardExit(Object dataObject) {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                if (itemsInAdapter == 5){

                }
            }

            @Override
            public void onScroll(float progress, float scrollXProgress) {
                //设置底部关闭
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void initRightNavigation() {
        View headerView = navigationViewRight.getHeaderView(0);
        TextView centerTitle = (TextView) headerView.findViewById(R.id.toolbar_centertext);
        TextView rightText = (TextView) headerView.findViewById(R.id.toolbar_righttext);
        centerTitle.setText("心动好友");
        rightText.setVisibility(View.GONE);
        rightRecycle = (RecyclerView) headerView.findViewById(R.id.right_navi_recycle);
        rightRecycle.setLayoutManager(new LinearLayoutManager(this));
        drawerView.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                hbListPresenter.start();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initLeftNavigation() {
        leftNaviHeadView = navigationViewLeft.getHeaderView(0);
        navigationViewLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navi_menu_1:
                        startActivity(new Intent(MainActivity.this, OrderListActivity.class));
                        drawerView.closeDrawers();
                        break;
                }
                return true;
            }
        });
        naviHeadImg = (CircleImageView) leftNaviHeadView.findViewById(R.id.navi_head);
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
        /**
         * 点击左侧滑栏的头像
         */
        naviHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BigHeadImgActivity.class);
                intent.putExtra("img", mUserInfo.getIcon());
                startActivityForResult(intent, 1);
//                ShowToast.show(MainActivity.this, "头像");
            }
        });
    }

    private void initBottomSheet() {
        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        seeRecycleView = (RecyclerView) bottomSheet.findViewById(R.id.see_recycle);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
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
                        startActivity(new Intent(MainActivity.this, FindFriendsActivity2.class));
                        break;
                }
                //设置底部关闭
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
        if (resultCode == 0 && data != null) {
            UserInformationBean2 userInformationBean = (UserInformationBean2) data.getSerializableExtra("userInformationBean");
            if (userInformationBean != null) {
                naviNickName.setText("" + userInformationBean.getNickname());
            }
        }else if (requestCode == 1){
            getUserInfoPresenter.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
//        EMClient.getInstance().logout(true);
        super.onDestroy();
    }


    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public void showDataForUserInfo(UserInformationBean2 userInformationBean) {
        if (userInformationBean != null) {
            mUserInfo = userInformationBean;
            Glide.with(this).load(userInformationBean.getIcon()).into(headImg);
            Glide.with(this).load(userInformationBean.getIcon()).into(naviHeadImg);
            naviNickName.setText("" + userInformationBean.getNickname());
            if (userInformationBean.getSex() != null) {
                if (userInformationBean.getSex() == 0) {
                    toolbarCentertext.setText("爱师妹");
                } else {
                    toolbarCentertext.setText("防师兄");
                }
            }
        }
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

    @Override
    public void showDataForHBList(final List<HBListBean> hbList) {
        if (hbList.size() > 0){
            HBListQuickAdapter hbListQuickAdapter = new HBListQuickAdapter(hbList);
            hbListQuickAdapter.setContext(MainActivity.this);
            rightRecycle.setAdapter(hbListQuickAdapter);
            rightRecycle.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    drawerView.closeDrawers();
                    HBListBean data = hbList.get(position);
                    if (data != null) {
                        ShowToast.show(MainActivity.this, "点击了" + data.getNickname());
                        Long id = data.getId();
                        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("userID", id);
                        startActivity(intent);
                    }
                }
            });
            View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null, false);
            hbListQuickAdapter.setEmptyView(emptyView);
            /*HBListAdapter hbListAdapter = new HBListAdapter(this, hbList);
            rightRecycle.setAdapter(hbListAdapter);
            //聊天
            hbListAdapter.setOnItemClickListener(new HBListAdapter.OnItemClickListener() {
                @Override
                public void click(View view, int position, Long userId) {
                    if (userId != null) {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("name", userId + "");
                        startActivity(intent);
                    }
                }
            });*/
        }
    }

    @Override
    public void showFailedForResultHBList(BaseBean2 baseBean2) {

    }

    @Override
    public void showData(List<UserInformationBean2> userInformationBean2s) {
        if (userInformationBean2s.size() > 0){
            mList.addAll(userInformationBean2s);
            //初始化swipecardview
            swipeCardViewAdapter = new SwipeCardViewAdapter(this, mList);
            swipeFlingView.setAdapter(swipeCardViewAdapter);
            swipeCardViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMoreData(List<UserInformationBean2> userInformationBean2s) {

    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean2) {

    }

    @Override
    public void showFailedForMoreResult(BaseBean2 baseBean2) {

    }

    @Override
    public void showFailedForMoreException(Throwable t) {

    }
}
