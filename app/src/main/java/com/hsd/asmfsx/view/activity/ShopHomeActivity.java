package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.ShopHomeListAdapter;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.contract.GetShopListContract;
import com.hsd.asmfsx.presenter.GetShopListPresenter;
import com.hsd.asmfsx.utils.ShowToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class ShopHomeActivity extends AppCompatActivity implements GetShopListContract.View {
    @BindView(R.id.first_layout)
    LinearLayout firstLayout;
    @BindView(R.id.second_layout)
    LinearLayout secondLayout;
    @BindView(R.id.third_layout)
    LinearLayout thirdLayout;
    @BindView(R.id.fourth_layout)
    LinearLayout fourthLayout;
    @BindView(R.id.shop_list_recycle)
    RecyclerView shopListRecycle;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    private ShopHomeListAdapter shopHomeListAdapter;
    private GetShopListPresenter getShopListPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_home_layout);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        getShopListPresenter = new GetShopListPresenter(this);
        getShopListPresenter.start();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCentertext.setText("附近商家");
        toolbarRighttext.setText("");
        shopListRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(List<ShopVO> shopVOs) {
        shopHomeListAdapter = new ShopHomeListAdapter(this, shopVOs);
        shopListRecycle.setAdapter(shopHomeListAdapter);
        shopHomeListAdapter.setOnItemClickListener(new ShopHomeListAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position, ShopVO shopVO) {
                Intent intent = new Intent(ShopHomeActivity.this, ItemShopActivity.class);
                intent.putExtra("shopVO", shopVO);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoadMore(List<ShopVO> shopVOs) {

    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean2) {
        ShowToast.show(ShopHomeActivity.this, baseBean2.getMsg());
    }

    @Override
    public void showFailedForLoadMoreResult(BaseBean2 baseBean2) {

    }

    @Override
    public void showExceptionForLoadMore(Throwable throwable) {

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
        ShowToast.show(ShopHomeActivity.this, t.toString());
    }
}
