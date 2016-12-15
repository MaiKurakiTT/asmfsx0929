package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.asmfsx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/12.
 */

public class PayActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.shopimg)
    ImageView shopimg;
    @BindView(R.id.order_price)
    TextView orderPrice;
    @BindView(R.id.good_name)
    TextView goodName;
    @BindView(R.id.alipayimg)
    ImageView alipayimg;
    @BindView(R.id.pay_but)
    AppCompatButton payBut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_layout);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("支付");
        toolbarRighttext.setText("");
    }
}
