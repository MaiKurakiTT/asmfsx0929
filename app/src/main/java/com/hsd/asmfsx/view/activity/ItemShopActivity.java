package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.bean.UserVO;
import com.hsd.asmfsx.contract.GetShopGoodListContract;
import com.hsd.asmfsx.presenter.GetShopGoodListPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.hsd.asmfsx.widget.FullyLinearLayoutManager;
import com.hsd.asmfsx.widget.MyNestedScrollView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class ItemShopActivity extends AppCompatActivity implements GetShopGoodListContract.View{
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

    public Long shopID = new Long(0);
    private GetShopGoodListPresenter getShopGoodListPresenter;
    private GoodsListAdapter goodsListAdapter;
    private ShopVO shopVO;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemshop_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View();
    }

    private void setData2View() {
        //设置商家信息显示
        if (shopVO != null){
            shopName.setText("" + shopVO.getName());
            shopPositionText.setText("" + shopVO.getAddress());
        }else {
            shopName.setText("加载失败");
        }
    }

    private void initData() {
        shopVO = (ShopVO) getIntent().getSerializableExtra("shopVO");

        getShopGoodListPresenter = new GetShopGoodListPresenter(this);
        getShopGoodListPresenter.start();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(itemshopToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goodList.setLayoutManager(new FullyLinearLayoutManager(this));
        goodList.setNestedScrollingEnabled(false);

        shopCallphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserVO userVO = shopVO.getUserVO();
                if (userVO != null){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userVO.getPhone()));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public Long getShopId() {
        return shopVO.getId();
    }

    @Override
    public void showData(List<CommodityVO> commodityVOs) {

        goodsListAdapter = new GoodsListAdapter(this, commodityVOs);
        goodList.setAdapter(goodsListAdapter);
        goodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position, Long goodId) {
                Intent intent = new Intent(ItemShopActivity.this, ItemGoodInfoActivity.class);
                intent.putExtra("goodId", goodId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoadMore(List<CommodityVO> commodityVOs) {

    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean2) {
        ShowToast.show(ItemShopActivity.this, baseBean2.getMsg());
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
        ShowToast.show(ItemShopActivity.this, t.toString());
    }
}
