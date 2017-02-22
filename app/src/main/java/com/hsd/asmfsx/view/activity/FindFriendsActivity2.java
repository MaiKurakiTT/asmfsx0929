package com.hsd.asmfsx.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.SwipeCardViewAdapter;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.FindFriendsContract;
import com.hsd.asmfsx.presenter.FindFriendsPresenter;
import com.hsd.asmfsx.widget.swipecardview.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class FindFriendsActivity2 extends BaseActivity implements FindFriendsContract.View {

    @BindView(R.id.changebut)
    Button changebut;
    @BindView(R.id.swipe_fling_view)
    SwipeFlingAdapterView swipeFlingView;
    private FindFriendsPresenter findFriendsPresenter;
    private List<UserInformationBean2> mList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findfriends_layout2);
        ButterKnife.bind(this);
        initView();
        findFriendsPresenter = new FindFriendsPresenter(this);
        findFriendsPresenter.start();

        changebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriendsPresenter.loadMoreData();
            }
        });
        for (int i = 0; i<20; i++){
            UserInformationBean2 userInformationBean2 = new UserInformationBean2();
            userInformationBean2.setNickname("i" + i);
            mList.add(userInformationBean2);
        }

        final SwipeCardViewAdapter swipeCardViewAdapter = new SwipeCardViewAdapter(this, mList);
        swipeFlingView.setAdapter(swipeCardViewAdapter);
        swipeFlingView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                swipeCardViewAdapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

            }

            @Override
            public void onRightCardExit(Object dataObject) {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float progress, float scrollXProgress) {

            }
        });

    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);


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
    public void showData(List<UserInformationBean2> userInformationBean2s) {
        mList = userInformationBean2s;

    }

    @Override
    public void showMoreData(List<UserInformationBean2> userInformationBean2s) {
        mList = userInformationBean2s;
    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean2) {
        Snackbar.make(changebut, "加载失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriendsPresenter.start();
            }
        }).show();
    }

    @Override
    public void showFailedForMoreResult(BaseBean2 baseBean2) {
        Snackbar.make(changebut, "加载失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriendsPresenter.loadMoreData();
            }
        }).show();
    }

    @Override
    public void showFailedForMoreException(Throwable t) {

    }
}
