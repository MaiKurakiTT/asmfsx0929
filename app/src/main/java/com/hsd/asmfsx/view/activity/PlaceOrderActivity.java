package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.contract.PlaceOrderContract;
import com.hsd.asmfsx.presenter.PlaceOrderPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/12.
 */

public class PlaceOrderActivity extends AppCompatActivity implements PlaceOrderContract.View{

    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.good_name)
    TextView goodName;
    @BindView(R.id.good_price)
    TextView goodPrice;
    @BindView(R.id.good_amount)
    EditText goodAmount;
    @BindView(R.id.order_price)
    TextView orderPrice;
    @BindView(R.id.do_place_but)
    AppCompatButton doPlaceBut;
    private Long goodId;
    private double price;
    private double totalPrice = 0;
    private String goodNameText;
    private int goodAmountInt;
    private PlaceOrderPresenter placeOrderPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeorder_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View();


    }

    private void setData2View() {
        goodName.setText("" + goodNameText);
        goodPrice.setText("" + price);
        orderPrice.setText("" + price);
        goodAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(PlaceOrderActivity.this.goodAmount.getText().toString())){
                    goodAmountInt = Integer.parseInt(PlaceOrderActivity.this.goodAmount.getText().toString() + "");
                    orderPrice.setText("￥" + (price * goodAmountInt));
                    totalPrice = goodAmountInt * price;
                }
            }
        });
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCentertext.setText("订单信息");
        toolbarRighttext.setText("");
        doPlaceBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalPrice != 0){
                    placeOrderPresenter.start();
                    Intent intent = new Intent(PlaceOrderActivity.this, PayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        goodId = getIntent().getLongExtra("goodId", new Long(0));
        price = getIntent().getDoubleExtra("price", 0);
        goodNameText = getIntent().getStringExtra("goodName");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在加载");
        progressDialog.setCanceledOnTouchOutside(false);
        placeOrderPresenter = new PlaceOrderPresenter(this);
    }

    @Override
    public Long getGoodId() {
        return goodId;
    }

    @Override
    public int getAmount() {
        return goodAmountInt;
    }

    @Override
    public double getPrice() {
        return totalPrice;
    }

    @Override
    public String getDetail() {
        return null;
    }

    @Override
    public void showData(BaseBean2 baseBean2) {

    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean) {

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
