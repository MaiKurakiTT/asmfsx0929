package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hsd.asmfsx.adapter.HeartBeatListAdapter;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.RequestHeartBeatContract;
import com.hsd.asmfsx.global.GlobalParameter;
import com.hsd.asmfsx.model.IUploadImgBiz;
import com.hsd.asmfsx.model.TestUpload;
import com.hsd.asmfsx.model.UploadImgBiz;
import com.hsd.asmfsx.presenter.RequestHeartBeatPresenter;
import com.hsd.asmfsx.view.activity.CertificationActivity;
import com.hsd.asmfsx.view.activity.LoginActivity;
import com.hsd.asmfsx.view.activity.RegisterActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RequestHeartBeatContract.View {
    public String TAG = "MainActivity";
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private RequestHeartBeatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        ButterKnife.bind(this);
        presenter = new RequestHeartBeatPresenter(this);
        button = (Button) findViewById(R.id.button);
        textview = (TextView) findViewById(R.id.textview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        recycle = (RecyclerView) findViewById(R.id.recycle);

        recycle.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadImgBiz().doUpload("", new IUploadImgBiz.OnUploadImgListener() {
                    @Override
                    public void success() {

                    }

                    @Override
                    public void failed() {

                    }
                });
//                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
//                startActivity(new Intent(MainActivity.this, CertificationActivity.class));
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                startActivity(new Intent(MainActivity.this, TestRetrofit.class));
//                startActivity(new Intent(MainActivity.this, RegisterAndLoginActivity.class));
//                presenter.getData();
            }
        });
    }

    private void testup() throws IOException {
        TestUpload testUpload = new TestUpload();
        File file = new File(Environment.getExternalStorageDirectory(), "icon.jpg");
        testUpload.uploadForm(file, GlobalParameter.ip + "Server/UploadServer");
    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public String getUuid() {
        return "84f4b998-17df-4997-8fc2-828f89aec37d";
    }

    @Override
    public void showData(List<UserInformationBean> userInformation) {
        HeartBeatListAdapter adapter = new HeartBeatListAdapter(this, userInformation);
        recycle.setAdapter(adapter);
    }

    @Override
    public void showFailed() {
        Toast.makeText(this, "failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.d(TAG, "成功退出登录");

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                Log.d(TAG, "失败退出登录");
            }
        });
    }
}
