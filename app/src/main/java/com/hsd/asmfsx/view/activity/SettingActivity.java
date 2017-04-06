package com.hsd.asmfsx.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.global.GetRetrofit;
import com.hsd.asmfsx.model.RetrofitService;
import com.hsd.asmfsx.model.cookie.CookieManger;
import com.hsd.asmfsx.model.cookie.PersistentCookieStore;
import com.hsd.asmfsx.utils.SPUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.guanyu_layout)
    LinearLayout guanyuLayout;
    @BindView(R.id.gengxin_layout)
    LinearLayout gengxinLayout;

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
                File file = new File("/data/data/" + getPackageName().toString() + "/shared_prefs", "Cookies_Prefs.xml");
                if (file.exists()) {
                    file.delete();
                }
                setResult(99);
                finish();
            }
        });
        guanyuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, AboutMeActivity.class));
            }
        });
        gengxinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            final String versionName = packageInfo.versionName;
                            URL url = new URL("http://www.liuxinkeji.com:8080/fsxasm/version.txt");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(10000);
                            conn.setReadTimeout(10000);
                            conn.setDoInput(true);
                            conn.connect();
                            if (conn.getResponseCode() == 200){
                                InputStream is = conn.getInputStream();
                                if (is != null){
                                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                                    String s = in.readLine();
                                    if (TextUtils.equals(s, versionName)){
                                        ShowToast.show(SettingActivity.this, "当前是最新版本");
                                    }else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(SettingActivity.this)
                                                        .setMessage("请到 爱师妹防师兄 微信公众平台下载最新版本")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                            }
                                                        }).create()
                                                        .show();
                                            }
                                        });
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

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
