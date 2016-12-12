package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.contract.GetItemGoodInfoContract;
import com.hsd.asmfsx.presenter.GetItemGoodInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/12.
 */

public class ItemGoodInfoActivity extends AppCompatActivity implements GetItemGoodInfoContract.View{
    @BindView(R.id.shop_photo)
    ImageView shopPhoto;
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

    private Long goodId = new Long(0);
    private GetItemGoodInfoPresenter getItemGoodInfoPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_good_layout);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        goodId = getIntent().getLongExtra("goodId", new Long(0));
        getItemGoodInfoPresenter = new GetItemGoodInfoPresenter(this);
        getItemGoodInfoPresenter.start();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(itemshopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    public Long getGoodId() {
        return goodId;
    }

    @Override
    public void showData(CommodityVO commodityVO) {
        goodName.setText(commodityVO.getName());
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
