package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by sun on 2016/12/16.
 */

public interface HBListContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        void showDataForHBList(List<HBListBean> hbList);
        void showFailedForResultHBList(BaseBean2 baseBean2);
    }
    interface IHBListBiz extends BaseListener{
        void getHBList(OnRequestListener<List<HBListBean>> requestListener);
    }
}
