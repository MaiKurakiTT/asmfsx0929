package com.hsd.asmfsx.model;

/**
 * Created by apple on 2016/10/20.
 */

public interface IUploadImgBiz {
    interface OnUploadImgListener{
        void success();
        void failed();
    }
    void doUpload(String fileUri, OnUploadImgListener uploadImgListener);
}
