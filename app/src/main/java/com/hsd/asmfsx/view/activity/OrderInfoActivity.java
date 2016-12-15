package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.OrderVO;
import com.hsd.asmfsx.contract.GetOrderInfoContract;
import com.hsd.asmfsx.presenter.GetOrderInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/15.
 */

public class OrderInfoActivity extends BaseActivity implements GetOrderInfoContract.View{
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.good_img)
    ImageView goodImg;
    @BindView(R.id.good_name_tv)
    TextView goodNameTv;
    @BindView(R.id.now_price_tv)
    TextView nowPriceTv;
    @BindView(R.id.normal_price_tv)
    TextView normalPriceTv;
    @BindView(R.id.pay_but)
    AppCompatButton payBut;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_position_img)
    ImageView shopPositionImg;
    @BindView(R.id.shop_position_text)
    TextView shopPositionText;
    @BindView(R.id.shop_callphone)
    ImageView shopCallphone;
    @BindView(R.id.match_order_num)
    TextView matchOrderNum;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.match_order_time)
    TextView matchOrderTime;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.match_order_amount)
    TextView matchOrderAmount;
    @BindView(R.id.order_amount)
    TextView orderAmount;
    @BindView(R.id.match_order_price)
    TextView matchOrderPrice;
    @BindView(R.id.order_price)
    TextView orderPrice;
    private Long orderId;
    private ProgressDialog progressDialog;
    private GetOrderInfoPresenter getOrderInfoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderinfo_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        getOrderInfoPresenter = new GetOrderInfoPresenter(this);
        getOrderInfoPresenter.start();
    }

    private void initData() {
        orderId = getIntent().getLongExtra("orderId", new Long(0));
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCentertext.setText("订单详情");
        toolbarRighttext.setText("");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public void showData(final OrderVO orderVO) {
        if (orderVO != null){
//            Glide.with(this).load(orderVO.get()).into(goodImg);
            goodNameTv.setText("" + orderVO.getCommodityName());
            nowPriceTv.setText("￥" + orderVO.getCommodityprice());
            normalPriceTv.setText("门市价：￥" + orderVO.getCommodityRetailPrice());
            shopName.setText("" + orderVO.getShopName());
            shopPositionText.setText("" + orderVO.getShopAddress());
            orderNum.setText("" + orderVO.getOrderNumber());
            orderAmount.setText("" + orderVO.getAmount());
            orderPrice.setText("" + orderVO.getPrice());
            shopCallphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
                }
            });
        }
    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean2) {

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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
