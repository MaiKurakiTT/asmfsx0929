package com.hsd.asmfsx.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.AddImgAdapter;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.contract.PutFriendCircleContract;
import com.hsd.asmfsx.presenter.PutFriendCirclePresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2016/11/11.
 * 发布朋友圈
 */

public class PutFriendCircleActivity extends BaseActivity implements PutFriendCircleContract.View {
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.addimg_recycleview)
    RecyclerView addimgRecycle;
    @BindView(R.id.addimg_first)
    ImageView addimgFirst;
    private AddImgAdapter addImgAdapter;
    private List<String> photos = new ArrayList<>();
    private PutFriendCirclePresenter putFriendCirclePresenter;
    private ProgressDialog progressDialog;
    private String contentText;

    private int PUTFRIENDCIRCLE_RESULT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putfriendcircle_layout);
        ButterKnife.bind(this);
        addimgRecycle.setLayoutManager(new GridLayoutManager(this, 4));
        initData();
        initView();

    }

    private void initData() {
        putFriendCirclePresenter = new PutFriendCirclePresenter(this);
    }

    private void initView() {
        initToolbar();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        head.setVisibility(View.GONE);
        toolbarCentertext.setText("发布");
        toolbarRighttext.setText("完成");
        toolbarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentText = contentEt.getText().toString();
                if (TextUtils.isEmpty(contentText) & photos.size() == 0) {
                    ShowToast.show(PutFriendCircleActivity.this, "说点什么再提交吧~");
                } else {
                    putFriendCirclePresenter.start();
                }
            }
        });
        addimgFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImg();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode && resultCode == Activity.RESULT_OK) {
            photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            addimgFirst.setVisibility(View.GONE);
            addImgAdapter = new AddImgAdapter(PutFriendCircleActivity.this, photos);
            addimgRecycle.setAdapter(addImgAdapter);
            addImgAdapter.setOnItemClickListener(new AddImgAdapter.OnItemClickListener() {
                @Override
                public void onclick(View view, int position) {
                    pickImg();
                }
            });
        }
    }

    private void pickImg() {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(9)
                .singlePhoto(false)
                .hintOfPick("最多只能选择9张图片哦~")
//                .filterMimeTypes(new String[]{"image/jpeg"})
                .build();
        GalleryActivity.openActivity(PutFriendCircleActivity.this, 0, config);
    }


    @Override
    public String getContent() {
        return contentEt.getText().toString();
    }

    @Override
    public List<String> getImgs() {
        return photos;
    }

    @Override
    public void showData(BaseBean2 baseBean, int failedCounts) {
        setResult(PUTFRIENDCIRCLE_RESULT);
        ShowToast.show(PutFriendCircleActivity.this, "发布成功");
        finish();
    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean) {

    }

    @Override
    public void showFailedForException(Throwable t) {

    }

    /*@Override
    public void showDataForUserInfo(BaseBean baseBean, int failedCounts) {
        if (baseBean.getResultCode() == 1) {
            if (failedCounts > 0) {
                Snackbar.make(toolbar, "发布成功, 但是有 " + failedCounts + "张图片上传失败", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(toolbar, "发布成功", Snackbar.LENGTH_LONG).show();
                setResult(PUTFRIENDCIRCLE_RESULT);
                finish();
            }
        }
    }*/

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }



    /*@Override
    public void showFailed() {
        Snackbar.make(toolbar, "发布失败", Snackbar.LENGTH_LONG).show();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
