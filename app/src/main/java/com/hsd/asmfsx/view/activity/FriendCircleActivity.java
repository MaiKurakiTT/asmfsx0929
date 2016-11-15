package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.FriendCircleAdapter;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.FriendCircleContract;
import com.hsd.asmfsx.contract.PutCommentContract;
import com.hsd.asmfsx.contract.PutGoodContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.FriendCirclePresenter;
import com.hsd.asmfsx.presenter.PutCommentPresenter;
import com.hsd.asmfsx.presenter.PutGoodPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleActivity extends AppCompatActivity implements FriendCircleContract.View, PutGoodContract.View, PutCommentContract.View{
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private FriendCirclePresenter friendCirclePresenter;
    private List<FriendCircleBean> friendCircleList;
    private FriendCircleAdapter friendCircleAdapter;

    private int PUTFRIENDCIRCLE_REQUEST = 0;
    private int PUTFRIENDCIRCLE_RESULT = 1;
    private int goodPosition = 0;
    private int commentPosition = 0;
    private PutGoodPresenter putGoodPresenter;
    private ProgressDialog progressDialog;
    private BottomSheetDialog bottomDialog;
    private EditText commentText;
    private Button commentOk;
    private String commentContent;
    private PutCommentPresenter putCommentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendcircle_layout);
        ButterKnife.bind(this);
        friendCirclePresenter = new FriendCirclePresenter(this);
        friendCirclePresenter.start();
        initData();
        initView();
    }

    private void initData() {
        putGoodPresenter = new PutGoodPresenter(this);
        putCommentPresenter = new PutCommentPresenter(this);
    }

    private void initView() {
        initToolbar();
        initRefresh();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.bottomdialog_layout, null);
        commentText = (EditText) view.findViewById(R.id.comment_text);
        commentOk = (Button) view.findViewById(R.id.comment_but);
        bottomDialog = new BottomSheetDialog(this);
        bottomDialog.setContentView(view);
        /**
         * 发送评论
         */
        commentOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentContent = commentText.getText().toString();
                if (TextUtils.isEmpty(commentContent)){
                    ShowToast.show(FriendCircleActivity.this, "写点什么再发送吧~");
                }else {
                    putCommentPresenter.start();
                    commentText.setText("");
                    bottomDialog.dismiss();
                }
            }
        });
    }

    private void initRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                friendCirclePresenter.start();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        head.setVisibility(View.GONE);
        toolbarCentertext.setText("微圈");
        toolbarRighttext.setText("发布");
        toolbarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(FriendCircleActivity.this, PutFriendCircleActivity.class), PUTFRIENDCIRCLE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PUTFRIENDCIRCLE_REQUEST && resultCode == PUTFRIENDCIRCLE_RESULT){
            //发布完说说后来到这里
            friendCirclePresenter.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 给recycleView设置上拉加载
     * @param adapter
     */
    private void initRecycleLoadMore(final FriendCircleAdapter adapter) {
        recycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int lastCompletelyVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
//                Log.d(TAG, "totalItemCount = " + totalItemCount + ", lastVisibleItem = "
//                        + lastVisibleItem + ", lastCompletelyVisibleItem = " + lastCompletelyVisibleItem);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if (adapter.getStatus() == 0) {
                        //判断当前是否正在刷新，为0代表没有在刷新
                        adapter.setStatus(1);
                        friendCirclePresenter.loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        /**
         * 给点赞按钮加点击事件
         */
        adapter.setOnGoodClickListener(new FriendCircleAdapter.OnGoodClickListener() {
            @Override
            public void click(View view, int position) {
                ShowToast.show(FriendCircleActivity.this, "点击了" + position);
                goodPosition = position;
                putGoodPresenter.start();
            }
        });
        /**
         * 给评论按钮添加点击事件
         */
        adapter.setOnCommentClickListener(new FriendCircleAdapter.OnCommentClickListener() {
            @Override
            public void click(View view, int position) {
                commentPosition = position;
                bottomDialog.show();
            }
        });

    }

    @Override
    public String getUUID() {
        return "84f4b998-17df-4997-8fc2-828f89aec37d";
    }

    @Override
    public void showData(BaseBean baseBean) {
        if (baseBean.getResultCode() == 1) {
            String body = baseBean.getBody();
            friendCircleList = GetGson.getGson().fromJson(body, new TypeToken<List<FriendCircleBean>>() {
            }.getType());
            Logger.d("请求到" + friendCircleList.size() + "条数据");
            if (friendCircleList.size() > 0) {
                recycleView.setLayoutManager(new LinearLayoutManager(this));
                friendCircleAdapter = new FriendCircleAdapter(this, friendCircleList);
                recycleView.setAdapter(friendCircleAdapter);
                initRecycleLoadMore(friendCircleAdapter);
            }
        }
    }

    @Override
    public void showMoreData(BaseBean baseBean) {
        if (baseBean.getResultCode() == 1) {
            String body = baseBean.getBody();
            List<FriendCircleBean> moreDataList = GetGson.getGson().fromJson(body, new TypeToken<List<FriendCircleBean>>() {
            }.getType());
            Logger.d("请求到" + moreDataList.size() + "条数据");
            if (moreDataList.size() > 0) {
                friendCircleList.addAll(moreDataList);
                if (friendCircleAdapter != null) {
                    friendCircleAdapter.setStatus(0);
                    friendCircleAdapter.notifyDataSetChanged();
                } else {
                    friendCircleAdapter = new FriendCircleAdapter(this, friendCircleList);
                    recycleView.setAdapter(friendCircleAdapter);
                }
            }

        }
    }


    @Override
    public void showFailedForLoadMore() {
        Snackbar.make(toolbar, "加载失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendCirclePresenter.loadMore();
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showFailed() {
        Snackbar.make(toolbar, "加载失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendCirclePresenter.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public FriendCircleBean getFriendCircleBeanForGood() {
        FriendCircleBean friendCircleBean = new FriendCircleBean();
        friendCircleBean.setUUID(getUUID());
        friendCircleBean.setFriendsCircle_ID(friendCircleList.get(goodPosition).getFriendsCircle_ID());
        return friendCircleBean;
    }

    @Override
    public void showDataForGood(BaseBean baseBean) {
        if (baseBean.getResultCode() == 1){

        }
    }

    @Override
    public void showFailedForGood() {
    }

    @Override
    public void showLoadingForGood() {
        progressDialog.show();
    }

    @Override
    public void hideLoadingForGood() {
        progressDialog.dismiss();
    }

    @Override
    public FriendCircleBean getFriendCircleBeanForComment() {
        FriendCircleBean friendCircleBean = new FriendCircleBean();
        friendCircleBean.setUUID(getUUID());
        friendCircleBean.setFriendsCircle_ID(friendCircleList.get(commentPosition).getFriendsCircle_ID());
        friendCircleBean.setBody(commentContent);
        return friendCircleBean;
    }

    @Override
    public void showDataForComment(BaseBean baseBean) {
        if (baseBean.getResultCode() == 1){
            friendCirclePresenter.start();
        }
    }

    @Override
    public void showFailedForComment() {
        Snackbar.make(toolbar, "发表失败", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingForComment() {
        progressDialog.show();
    }

    @Override
    public void hideLoadingForComment() {
        progressDialog.dismiss();
    }
}