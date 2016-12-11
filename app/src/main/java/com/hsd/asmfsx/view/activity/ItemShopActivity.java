package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.GoodsListAdapter;
import com.hsd.asmfsx.widget.FullyLinearLayoutManager;
import com.hsd.asmfsx.widget.MyNestedScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class ItemShopActivity extends AppCompatActivity {
    @BindView(R.id.shop_photo)
    ImageView shopPhoto;
    @BindView(R.id.itemshop_toolbar)
    Toolbar itemshopToolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_position_img)
    ImageView shopPositionImg;
    @BindView(R.id.shop_position_text)
    TextView shopPositionText;
    @BindView(R.id.shop_callphone)
    ImageView shopCallphone;
    @BindView(R.id.good_list)
    RecyclerView goodList;
    @BindView(R.id.nest)
    MyNestedScrollView nest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemshop_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(itemshopToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goodList.setLayoutManager(new FullyLinearLayoutManager(this));
        goodList.setNestedScrollingEnabled(false);
        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(this);
        goodList.setAdapter(goodsListAdapter);
        nest.post(new Runnable() {
            @Override
            public void run() {
                nest.scrollTo(0, 0);
            }
        });
        goodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position, Long shopId) {
            }
        });

    }
}
