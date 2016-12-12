package com.hsd.asmfsx.contract;

import com.hsd.asmfsx.base.BasePresenter;
import com.hsd.asmfsx.base.BaseView;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.model.BaseListener;

/**
 * Created by sun on 2016/12/12.
 */

public interface GetItemGoodInfoContract {
    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView{
        Long getGoodId();
        void showData(CommodityVO commodityVO);
        void showFailedForResult(BaseBean2 baseBean2);
    }
    interface IGetItemGoodInfoBiz extends BaseListener{
        void getGoodInfo(Long commodityID, OnRequestListener<CommodityVO> requestListener);
    }
}
