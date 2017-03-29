package com.hsd.asmfsx.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.utils.SPUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2017/3/28.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.tuichu_layout)
    LinearLayout tuichuLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settting_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("设置");
        toolbarRighttext.setVisibility(View.GONE);
        tuichuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils spUtils = SPUtils.getInstance("asmfsx");
                spUtils.clearSP();
                File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","Cookies_Prefs.xml");
                if(file.exists()){
                    file.delete();
                }
                setResult(99);
                finish();
            }
        });
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
