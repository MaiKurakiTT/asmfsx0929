package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.PictureBean;
import com.hsd.asmfsx.contract.PutFriendCircleContract;

import java.util.List;

/**
 * Created by apple on 2016/11/14.
 */

public class PutFriendCircleBiz implements PutFriendCircleContract.IPutFriendCircleBiz{
    @Override
    public void doPut(List<PictureBean> pictures, FriendCircleBean friendCircleBean, OnPutFriendCircleListener putFriendCircleListener) {

    }
}
