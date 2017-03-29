package com.hsd.asmfsx.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hsd.asmfsx.MainActivity;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.HBListQuickAdapter;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.contract.HBListContract;
import com.hsd.asmfsx.db.DBUserBean;
import com.hsd.asmfsx.presenter.HBListPresenter;
import com.hsd.asmfsx.view.activity.UserInfoActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2017/3/22.
 */

public class HBListFragment extends Fragment implements HBListContract.View {
    @BindView(R.id.right_navi_recycle)
    RecyclerView rightNaviRecycle;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private HBListPresenter hbListPresenter;
    private MainActivity mActivity;
    private HBListQuickAdapter hbListQuickAdapter;

    private boolean isCreateView = false;
    private boolean isLoadData = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_navigation_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        isCreateView = true;
        hbListPresenter = new HBListPresenter(this);
        hbListPresenter.start();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView){
            lazyLoad();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()){
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (!isLoadData){

            isLoadData = true;
        }
    }

    private void initView() {
        rightNaviRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        initRefresh();
        mActivity = (MainActivity) getActivity();
    }

    private void initRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hbListPresenter.start();
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showFailedForException(Throwable t) {

    }

    @Override
    public void showDataForHBList(final List<HBListBean> hbList) {
        if (hbList.size() > 0) {
            hbListQuickAdapter = new HBListQuickAdapter(hbList);
            hbListQuickAdapter.setContext(mActivity);
            rightNaviRecycle.setAdapter(hbListQuickAdapter);
            View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null, false);
            hbListQuickAdapter.setEmptyView(emptyView);
            hbListQuickAdapter.openLoadAnimation();
            hbListQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    HBListBean data = hbList.get(position);
                    if (data != null) {
                        Long id = data.getId();
                        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("userID", id);
                        startActivity(intent);
                        MainActivity activity = (MainActivity) getActivity();
                        activity.closeDrawer();
                    }
                }
            });

            for (HBListBean hbListBean : hbList) {
                Long id = hbListBean.getId();
                List<DBUserBean> dbUserBeanList = DataSupport.where("userId = ?", "" + id).find(DBUserBean.class);
                if (dbUserBeanList.size() > 0) {
                    DataSupport.delete(DBUserBean.class, dbUserBeanList.get(0).getId());
                }
                DBUserBean dbUserBean = new DBUserBean();
                dbUserBean.setUserId(id);
                dbUserBean.setNickname(hbListBean.getNickname());
                dbUserBean.setIcon(hbListBean.getIcon());
                dbUserBean.save();
            }
        }
    }

    @Override
    public void showFailedForResultHBList(BaseBean2 baseBean2) {

    }
}
