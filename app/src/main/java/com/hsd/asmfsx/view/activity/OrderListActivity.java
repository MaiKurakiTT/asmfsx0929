package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.OrderListAdapter;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.OrderListBean;
import com.hsd.asmfsx.contract.GetOrderListContract;
import com.hsd.asmfsx.presenter.GetOrderListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/15.
 */

public class OrderListActivity extends BaseActivity implements GetOrderListContract.View {
    @BindView(R.id.order_list)
    RecyclerView orderList;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    private ProgressDialog progressDialog;
    private OrderListAdapter orderListAdapter;
    private GetOrderListPresenter getOrderListPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist_layout);
        ButterKnife.bind(this);
        initView();
        getOrderListPresenter = new GetOrderListPresenter(this);
        getOrderListPresenter.start();
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCentertext.setText("我的订单");
        toolbarRighttext.setText("");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(List<OrderListBean> orderListBeen) {
        if (orderListBeen != null) {
            orderListAdapter = new OrderListAdapter(this, orderListBeen);
            orderList.setAdapter(orderListAdapter);
            orderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, Long id) {
                    Intent intent = new Intent(OrderListActivity.this, OrderInfoActivity.class);
                    intent.putExtra("orderId", id);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void showLoadMore(List<OrderListBean> orderListBeen) {

    }

    @Override
    public void showFailedResult(BaseBean2 baseBean) {

    }

    @Override
    public void showFailedForLoadMoreResult(BaseBean2 baseBean2) {

    }

    @Override
    public void showExceptionForLoadMore(Throwable t) {

    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void showFailedForException(Throwable t) {

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
}
