package com.hsd.asmfsx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.RequestHeartBeatContract;
import com.hsd.asmfsx.presenter.RequestHeartBeatPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RequestHeartBeatContract.View{
    public String TAG = "MainActivity";
    private Button button;
    private TextView textView;
    private ProgressBar progressBar;
    private RequestHeartBeatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new RequestHeartBeatPresenter(this);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData();
            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showData(List<UserInformationBean> userInformation) {
        textView.setText(userInformation.get(0).getUser_icon());
    }

    @Override
    public void showFailed() {
        textView.setText("failed");
    }
}
