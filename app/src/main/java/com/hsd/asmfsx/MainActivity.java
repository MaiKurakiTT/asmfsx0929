package com.hsd.asmfsx;

import android.content.Intent;
import android.os.Bundle;
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
import com.hsd.asmfsx.chat.RegisterAndLoginActivity;
import com.hsd.asmfsx.contract.RequestHeartBeatContract;
import com.hsd.asmfsx.presenter.RequestHeartBeatPresenter;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                startActivity(new Intent(MainActivity.this, TestRetrofit.class));
//                startActivity(new Intent(MainActivity.this, RegisterAndLoginActivity.class));
//                presenter.getData();
            }
        });
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
