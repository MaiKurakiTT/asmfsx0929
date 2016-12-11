package com.hsd.asmfsx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.ShopHomeListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class ShopHomeActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_home_layout);
        ButterKnife.bind(this);
        shopListRecycle.setLayoutManager(new LinearLayoutManager(this));
        ShopHomeListAdapter shopHomeListAdapter = new ShopHomeListAdapter(this);
        shopListRecycle.setAdapter(shopHomeListAdapter);
        shopHomeListAdapter.setOnItemClickListener(new ShopHomeListAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position, Long shopId) {
                Intent intent = new Intent(ShopHomeActivity.this, ItemShopActivity.class);
                startActivity(intent);
            }
        });
    }
}
