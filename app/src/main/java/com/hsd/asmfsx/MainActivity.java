package com.hsd.asmfsx;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.adapter.HomePagerAdapter;
import com.hsd.asmfsx.adapter.SeeAdapter;
import com.hsd.asmfsx.adapter.SwipeCardViewAdapter;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.contract.GetUserInfoContract;
import com.hsd.asmfsx.db.DBUserBean;
import com.hsd.asmfsx.presenter.FindFriendsPresenter;
import com.hsd.asmfsx.presenter.GetUserInfoPresenter;
import com.hsd.asmfsx.service.MyService;
import com.hsd.asmfsx.utils.SPUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.hsd.asmfsx.view.activity.BigHeadImgActivity;
import com.hsd.asmfsx.view.activity.FriendCircleActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
import com.hsd.asmfsx.view.activity.RegisterActivity;
import com.hsd.asmfsx.view.activity.SetAfterRegisterActivity;
import com.hsd.asmfsx.view.activity.SettingActivity;
import com.hsd.asmfsx.view.activity.ShiMingActivity;
import com.hsd.asmfsx.view.activity.ShopHomeActivity;
import com.hsd.asmfsx.view.activity.UserInfoActivity;
import com.hsd.asmfsx.view.fragment.HBListFragment;
import com.hsd.asmfsx.view.fragment.MessageListFragment;
import com.hsd.asmfsx.widget.swipecardview.SwipeFlingAdapterView;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GetUserInfoContract.View, FindFriendsContract.View {
    public String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.head)
    ImageView headImg;
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
    @BindView(R.id.right_tab)
    TabLayout rightTab;
    @BindView(R.id.right_viewpager)
    ViewPager rightViewpager;
    @BindView(R.id.img_right)
    ImageView imgRight;

    private RecyclerView rightRecycle;
    private RecyclerView seeRecycleView;

    private String[] seeTitles = {"朋友圈", "团购", "注册", "登录", "设置信息"};
    private int[] seeImgs = {R.mipmap.ic_news, R.mipmap.ic_mail, R.mipmap.ic_show,
            R.mipmap.ic_log, R.mipmap.ic_game};
    private UserInformationBean2 mUserInfo;
    private View leftNaviHeadView;
    private ImageView naviHeadImg;
    private TextView naviNickName;
    private GetUserInfoPresenter getUserInfoPresenter;

    private long mExitTime = 0;


    private List<UserInformationBean2> mList = new ArrayList<>();
    private BottomSheetBehavior<View> sheetBehavior;
    private SwipeCardViewAdapter swipeCardViewAdapter;
    private FindFriendsPresenter findFriendsPresenter;
    private ProgressDialog progressDialog;
    private HBListFragment hbListFragment;
    private MessageListFragment messageListFragment;
    private FragmentTransaction beginTransaction;
    private boolean isFindedFriends = false;

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
        findFriendsPresenter = new FindFriendsPresenter(this);

    }

    private void setData2View() {

    }

    private void initData() {
        startService(new Intent(MainActivity.this, MyService.class));
    }


    private void initView() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        initToolbar();
        initListener();
        initBottomSheet();
        initLeftNavigation();
//        initRightNavigation();
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
//                    ShowToast.show(MainActivity.this, "点击了" + data.getNickname());
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
                Logger.d("onAdapterAboutToEmpty", "" + itemsInAdapter);
                if (itemsInAdapter == 3) {
                    findFriendsPresenter.loadMoreData();
                }
            }

            @Override
            public void onScroll(float progress, float scrollXProgress) {
                //设置底部关闭
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void initRightNavigation() {
        hbListFragment = new HBListFragment();
        messageListFragment = new MessageListFragment();
        FragmentManager fm = getSupportFragmentManager();
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(fm);
        homePagerAdapter.addFragment(hbListFragment, "好友");
        homePagerAdapter.addFragment(messageListFragment, "消息");
        rightViewpager.setAdapter(homePagerAdapter);
        rightTab.setupWithViewPager(rightViewpager);
    }

    private void initLeftNavigation() {
        leftNaviHeadView = navigationViewLeft.getHeaderView(0);
        navigationViewLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navi_menu_1:
//                        startActivity(new Intent(MainActivity.this, MessageActivity.class));
                        break;
                    case R.id.navi_menu_2:
                        startActivity(new Intent(MainActivity.this, ShiMingActivity.class));
                        break;
                    case R.id.navi_menu_3:
                        ShowToast.show(MainActivity.this, "攻城狮正在拼命开发中...");
                        break;
                    case R.id.sub_1:
                        startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), 99);
                        break;
                }
                drawerView.closeDrawers();
                return true;
            }
        });
        naviHeadImg = (ImageView) leftNaviHeadView.findViewById(R.id.navi_head);
        naviNickName = (TextView) leftNaviHeadView.findViewById(R.id.navi_name);
        leftNaviHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                if (mUserInfo != null) {
                    intent.putExtra("type", 0);
                    intent.putExtra("userInformationBean", mUserInfo);
                    startActivityForResult(intent, 0);
                    drawerView.closeDrawers();
                } else {
                    ShowToast.show(MainActivity.this, "未知的错误");
                }
            }
        });
        /**
         * 点击左侧滑栏的头像
         */
        naviHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BigHeadImgActivity.class);
                if (mUserInfo != null) {
                    intent.putExtra("img", mUserInfo.getIcon() + "");
                    startActivityForResult(intent, 1);
                } else {
                    ShowToast.show(MainActivity.this, "未知的错误");
                }
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
        //底部bottomsheet点击事件
        seeAdapter.setOnItemClickListener(new SeeAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position) {
                switch (position) {
                    case 0:
                        //开启朋友圈
                        startActivity(new Intent(MainActivity.this, FriendCircleActivity.class));
                        break;
                    case 1:
                        //开启团购
                        startActivity(new Intent(MainActivity.this, ShopHomeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, SetAfterRegisterActivity.class));
                        break;
                }
                //设置底部关闭
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
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
        imgRight.setOnClickListener(new View.OnClickListener() {
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
            getUserInfoPresenter.start();
        } else if (requestCode == 1) {
            getUserInfoPresenter.start();
        }
        if (resultCode == 99) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void closeDrawer() {
        drawerView.closeDrawers();
    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().logout(true);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                // 如果两次按键时间间隔大于2000毫秒，则不退出
                ShowToast.show(MainActivity.this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                // 否则退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public void showDataForUserInfo(UserInformationBean2 userInformationBean) {
        if (userInformationBean != null) {
            mUserInfo = userInformationBean;
            if (mUserInfo.getSex() == null) {
                startActivity(new Intent(MainActivity.this, SetAfterRegisterActivity.class));
                finish();
            } else {
                if (!isFindedFriends) {
                    //加载完个人信息后再请求推荐好友
                    findFriendsPresenter.start();
                    //请求过一次找朋友了就不再重新请求了
                    isFindedFriends = true;
                }
            }
//            Glide.with(this).load(userInformationBean.getIcon()).into(headImg);
            Glide.with(this).load(userInformationBean.getIcon()).into(naviHeadImg);
            naviNickName.setText("" + userInformationBean.getNickname());
            if (userInformationBean.getSex() != null) {
                if (userInformationBean.getSex() == 0) {
                    toolbarCentertext.setText("爱师妹");
                } else {
                    toolbarCentertext.setText("防师兄");
                }
            }
            SPUtils spUtils = SPUtils.getInstance("asmfsx");
            spUtils.putString("myIcon", userInformationBean.getIcon() + "");
            spUtils.putString("myNick", userInformationBean.getNickname() + "");
            spUtils.putString("myId", userInformationBean.getId() + "");
            spUtils.putBoolean("isStudentVerify", userInformationBean.isStudentVerify());
            //保存数据库
//            DataSupport.deleteAll(DBUserBean.class);
            DBUserBean dbUserBean = new DBUserBean();
            setDBUserBean(dbUserBean, userInformationBean);
            List<DBUserBean> dbUserBeenList = DataSupport.where("userId = ?", userInformationBean.getId() + "").find(DBUserBean.class);
            if (dbUserBeenList.size() > 0) {
                DataSupport.delete(DBUserBean.class, dbUserBeenList.get(0).getId());
            }
            dbUserBean.save();
        }
    }

    private ContentValues setContentValues(UserInformationBean2 userInformationBean) {
        ContentValues values = new ContentValues();
        values.put("", "");
        return values;
    }

    private void setDBUserBean(DBUserBean dbUserBean, UserInformationBean2 userInformationBean) {
        dbUserBean.setUserId(userInformationBean.getId());
        dbUserBean.setPhone(userInformationBean.getPhone());
        dbUserBean.setIcon(userInformationBean.getIcon());
        dbUserBean.setNickname(userInformationBean.getNickname());
        dbUserBean.setSex(userInformationBean.getSex());
        dbUserBean.setBirthday(userInformationBean.getBirthday());
        dbUserBean.setStar(userInformationBean.getStar());
        dbUserBean.setHeight(userInformationBean.getHeight());
        dbUserBean.setSign(userInformationBean.getSign());
        dbUserBean.setYear(userInformationBean.getYear());
        dbUserBean.setLocality(userInformationBean.getLocality());
        dbUserBean.setAdore(userInformationBean.getAdore());
        dbUserBean.setState(userInformationBean.getState());
        dbUserBean.setRegisterDate(userInformationBean.getRegisterDate());

    }


    @Override
    public void showFailedForUserInfoResult(BaseBean2 baseBean) {

    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showFailedForException(Throwable t) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showData(List<UserInformationBean2> userInformationBean2s) {
        if (userInformationBean2s.size() > 0) {
            mList.addAll(userInformationBean2s);

            //初始化swipecardview
            swipeCardViewAdapter = new SwipeCardViewAdapter(this, mList);
            swipeFlingView.setAdapter(swipeCardViewAdapter);
            swipeCardViewAdapter.notifyDataSetChanged();
            for (UserInformationBean2 userInformationBean2 : userInformationBean2s) {
                List<DBUserBean> dbUserBeenList = DataSupport.where("userId = ?", userInformationBean2.getId() + "").find(DBUserBean.class);
                if (dbUserBeenList.size() > 0) {
                    DataSupport.delete(DBUserBean.class, dbUserBeenList.get(0).getId());
                }
                DBUserBean dbUserBean = new DBUserBean();
                setDBUserBean(dbUserBean, userInformationBean2);
                dbUserBean.save();

            }
            //初始化右边侧滑栏要放在请求完推送好友之后，不然读取不到数据库资料，导致头像昵称不显示
            initRightNavigation();
        }
    }

    @Override
    public void showMoreData(List<UserInformationBean2> userInformationBean2s) {
        if (userInformationBean2s.size() > 0) {
            mList.addAll(userInformationBean2s);
            swipeCardViewAdapter.notifyDataSetChanged();
            for (UserInformationBean2 userInformationBean2 : userInformationBean2s) {
                DBUserBean dbUserBean = new DBUserBean();
                setDBUserBean(dbUserBean, userInformationBean2);
                dbUserBean.save();
            }
        } else {
            ShowToast.show(MainActivity.this, "没有更多数据了");
        }
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
