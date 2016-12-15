package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.bean.UserVO;
import com.hsd.asmfsx.contract.GetItemGoodInfoContract;
import com.hsd.asmfsx.presenter.GetItemGoodInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/12.
 */

public class ItemGoodInfoActivity extends AppCompatActivity implements GetItemGoodInfoContract.View {
    @BindView(R.id.good_photo)
    ImageView goodPhoto;
    @BindView(R.id.itemshop_toolbar)
    Toolbar itemshopToolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.good_name)
    TextView goodName;
    @BindView(R.id.now_price)
    TextView nowPrice;
    @BindView(R.id.normal_price)
    TextView normalPrice;
    @BindView(R.id.buy_but)
    AppCompatButton buyBut;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_position_img)
    ImageView shopPositionImg;
    @BindView(R.id.shop_position_text)
    TextView shopPositionText;
    @BindView(R.id.shop_callphone)
    ImageView shopCallphone;
    @BindView(R.id.nest)
    NestedScrollView nest;
    @BindView(R.id.use_rule)
    TextView useRule;

    private Long goodId = new Long(0);
    private GetItemGoodInfoPresenter getItemGoodInfoPresenter;
    private ProgressDialog progressDialog;
    private double price;
    private String goodNameText;
    private ShopVO shopVO;
    private String shopPhone;
    private CommodityVO mCommodity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_good_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View();
        getItemGoodInfoPresenter = new GetItemGoodInfoPresenter(this);
        getItemGoodInfoPresenter.start();
    }

    private void setData2View() {

        if (shopVO != null) {
            shopName.setText("" + shopVO.getName());
            shopPhone = "" + shopVO.getUserVO().getPhone();
            shopPositionText.setText("" + shopVO.getAddress());
            //打电话
            shopCallphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserVO userVO = shopVO.getUserVO();
                    if (userVO != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userVO.getPhone()));
                        startActivity(intent);
                    }
                }
            });
        } else {

        }
    }

    private void initData() {
        goodId = getIntent().getLongExtra("goodId", new Long(0));
        shopVO = (ShopVO) getIntent().getSerializableExtra("shopVO");
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(itemshopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        buyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemGoodInfoActivity.this, PlaceOrderActivity.class);
                intent.putExtra("goodId", goodId);
                intent.putExtra("goodName", goodNameText);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });
    }

    @Override
    public Long getGoodId() {
        return goodId;
    }

    @Override
    public void showData(CommodityVO commodityVO) {
        if (commodityVO != null){
            mCommodity = commodityVO;
            if (commodityVO.getPictures() != null)
                Glide.with(this).load(mCommodity.getPictures().get(0)).placeholder(R.mipmap.ic_loadingimg).into(goodPhoto);
            price = commodityVO.getPrice();
            goodNameText = commodityVO.getName();
            goodName.setText("" + commodityVO.getName());
            nowPrice.setText("￥" + price);
            normalPrice.setText("门市价：￥" + commodityVO.getPrice());
            useRule.setText("· " + commodityVO.getRule());
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
}
