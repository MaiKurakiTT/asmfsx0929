package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.model.BaseListener;

import java.util.List;

/**
 * Created by sun on 2016/12/12.
 */

public interface GetShopGoodListContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        Long getShopId();
        void showData(List<CommodityVO> commodityVOs);
        void showLoadMore(List<CommodityVO> commodityVOs);
        void showFailedForResult(BaseBean2 baseBean2);
        void showFailedForLoadMoreResult(BaseBean2 baseBean2);
        void showExceptionForLoadMore(Throwable throwable);
    }
    interface IGetShopGoodListBiz extends BaseListener{
        void getGoodList(Long shopID, int page, int limit, OnRequestListener<List<CommodityVO>> requestListener);
    }
}
