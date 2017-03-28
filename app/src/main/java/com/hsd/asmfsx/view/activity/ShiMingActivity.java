package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2017/3/28.
 * 实名认证
 */

public class ShiMingActivity extends BaseActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_name_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }
}
