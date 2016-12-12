package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by sun on 2016/12/10.
 */

public interface PlaceOrderContract {
    interface Presenter extends BasePresenter {

    }
    interface View extends BaseView {
        Long getGoodId();
        int getAmount();
        int getPrice();
        String getDetail();
        void showData(BaseBean2 baseBean2);
        void showFailedForResult(BaseBean2 baseBean);
    }
    interface IPlaceOrderBiz extends BaseListener{
        void doPlaceOrder(Long commodityID, int amount, int price, String detail, OnRequestListener<BaseBean2> requestListener);
    }
}
