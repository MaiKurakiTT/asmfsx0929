package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by sun on 2016/12/12.
 */

public interface GetShopListContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        void showData(List<ShopVO> shopVOs);
        void showLoadMore(List<ShopVO> shopVOs);
        void showFailedForResult(BaseBean2 baseBean2);
        void showFailedForLoadMoreResult(BaseBean2 baseBean2);
        void showExceptionForLoadMore(Throwable throwable);
    }
    interface IGetShopListBiz extends BaseListener{
        void getShop(int page, int limit, OnRequestListener<List<ShopVO>> requestListener);
    }
}
