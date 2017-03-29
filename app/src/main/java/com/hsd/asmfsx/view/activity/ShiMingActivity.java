package com.hsd.asmfsx.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShiMingBean;
import com.hsd.asmfsx.contract.GetShiMingStateContract;
import com.hsd.asmfsx.contract.ShiMingWriteContract;
import com.hsd.asmfsx.presenter.GetShiMingStatePresenter;
import com.hsd.asmfsx.presenter.ShiMingWritePresenter;
import com.hsd.asmfsx.utils.PickViewUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2017/3/28.
 * 实名认证
 */

public class ShiMingActivity extends BaseActivity implements ShiMingWriteContract.View, GetShiMingStateContract.View {
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.match_nametext)
    TextView matchNametext;
    @BindView(R.id.nametext)
    EditText nametext;
    @BindView(R.id.match_schooltext)
    TextView matchSchooltext;
    @BindView(R.id.schooltext)
    TextView schooltext;
    @BindView(R.id.schoolparent)
    RelativeLayout schoolparent;
    @BindView(R.id.addimg_first)
    ImageView addimgFirst;
    @BindView(R.id.addimg_recycleview)
    RecyclerView addimgRecycleview;
    @BindView(R.id.write_layout)
    LinearLayout writeLayout;
    @BindView(R.id.result_text)
    TextView resultText;
    @BindView(R.id.write_but)
    Button writeBut;
    @BindView(R.id.success_layout)
    LinearLayout successLayout;
    @BindView(R.id.biaoqing)
    ImageView biaoqing;
    private String[] schoolItems = {"河南师范大学", "新乡医学院", "新乡学院", "河南科技学院"};
    private Long schoolNum = Long.valueOf(0);
    private String schoolString = "";
    private String photoLocal = "";
    private String realName = "";
    private ShiMingWritePresenter shiMingWritePresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shiming_write_layout);
        ButterKnife.bind(this);
        shiMingWritePresenter = new ShiMingWritePresenter(this);
        initView();
        GetShiMingStatePresenter getShiMingStatePresenter = new GetShiMingStatePresenter(this);
        getShiMingStatePresenter.start();
    }

    private void initView() {
        progressDialog = new ProgressDialog(ShiMingActivity.this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCentertext.setText("实名认证");
        toolbarRighttext.setVisibility(View.GONE);
        toolbarRighttext.setText("完成");
        schoolparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(ShiMingActivity.this, schooltext).pickOther(schoolItems);
            }
        });
        addimgFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImg();
            }
        });
        toolbarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });

    }

    private void uploadData() {
        realName = nametext.getText().toString();
        schoolString = schooltext.getText().toString();
        if (TextUtils.isEmpty(realName) || TextUtils.isEmpty(schoolString) || TextUtils.isEmpty(photoLocal)) {
            ShowToast.show(ShiMingActivity.this, "请先完善信息");
        } else {
            switch (schoolString) {
                case "河南师范大学":
                    schoolNum = Long.valueOf(0);
                    break;
                case "新乡医学院":
                    schoolNum = Long.valueOf(1);
                    break;
                case "新乡学院":
                    schoolNum = Long.valueOf(2);
                    break;
                case "河南科技学院":
                    schoolNum = Long.valueOf(3);
                    break;
            }
            shiMingWritePresenter.start();
        }
    }

    private void pickImg() {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(1)
                .singlePhoto(false)
                .hintOfPick("最多只能选择1张图片哦~")
//                .filterMimeTypes(new String[]{"image/jpeg"})
                .build();
        GalleryActivity.openActivity(ShiMingActivity.this, 0, config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode && resultCode == Activity.RESULT_OK) {
            List<String> photos = new ArrayList<>();
            photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            photoLocal = photos.get(0);
            Glide.with(this).load(photoLocal).into(addimgFirst);
        }
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
    public String getRealName() {
        return realName;
    }

    @Override
    public Long getSchoolNum() {
        return schoolNum;
    }

    @Override
    public String getImg() {
        return photoLocal;
    }

    @Override
    public void showDataForWrite(BaseBean2 baseBean2) {
        ShowToast.show(ShiMingActivity.this, "提交审核成功");
        finish();
    }

    @Override
    public void showFailedForResultWrite(BaseBean2 baseBean2) {
        ShowToast.show(ShiMingActivity.this, baseBean2.getMsg() + "");
    }

    @Override
    public void showDataForState(NormalResultBean<ShiMingBean> resultBean) {
        ShiMingBean shiMingBean = resultBean.getJson();
        int state = shiMingBean.getState();
        switch (state) {
            case 0:
                writeLayout.setVisibility(View.VISIBLE);
                toolbarRighttext.setVisibility(View.VISIBLE);
                toolbarRighttext.setText("完成");
                break;
            case 1:
                successLayout.setVisibility(View.VISIBLE);
                writeLayout.setVisibility(View.GONE);
                biaoqing.setImageResource(R.mipmap.ic_mianwubiaoqing);
                resultText.setText("学生认证审核中，请耐心等待~");
                writeBut.setVisibility(View.GONE);
                break;
            case 2:
                successLayout.setVisibility(View.VISIBLE);
                writeLayout.setVisibility(View.GONE);
                biaoqing.setImageResource(R.mipmap.ic_nanguo);
                resultText.setText("很遗憾，你未通过学生认证，未通过原因：" + shiMingBean.getDisc());
                writeBut.setVisibility(View.VISIBLE);
                writeBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        writeLayout.setVisibility(View.VISIBLE);
                        successLayout.setVisibility(View.GONE);
                        toolbarRighttext.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 3:
                successLayout.setVisibility(View.VISIBLE);
                writeLayout.setVisibility(View.GONE);
                biaoqing.setImageResource(R.mipmap.ic_kaixin);
                resultText.setText("恭喜你，" + shiMingBean.getRealName() + "，你已经成功通过学生认证~");
                writeBut.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showFiledForState(BaseBean2 baseBean2) {

    }
}
