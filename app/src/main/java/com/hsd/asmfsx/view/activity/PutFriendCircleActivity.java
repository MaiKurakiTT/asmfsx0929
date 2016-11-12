package com.hsd.asmfsx.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.AddImgAdapter;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2016/11/11.
 */

public class PutFriendCircleActivity extends AppCompatActivity {
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
    private List<String> photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putfriendcircle_layout);
        ButterKnife.bind(this);
        addimgRecycle.setLayoutManager(new GridLayoutManager(this, 4));
        initView();

    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        head.setVisibility(View.GONE);
        toolbarCentertext.setText("发布");
        toolbarRighttext.setText("图片");
        toolbarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImg();
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
        if (0 == requestCode && resultCode == Activity.RESULT_OK){
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
}
