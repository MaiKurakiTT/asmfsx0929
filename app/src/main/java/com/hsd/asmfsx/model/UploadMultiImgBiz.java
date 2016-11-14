package com.hsd.asmfsx.model;

import android.os.Handler;
import android.os.Message;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.PictureBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2016/11/14.
 */

public class UploadMultiImgBiz {
    private int okImgCounts = 0;
    private List<PictureBean> pictures = new ArrayList<>();

    public interface OnFinishListener{
        void finished(List<PictureBean> pictures);
    }

    public void doUpload(final List<String> imgs, final OnFinishListener finishListener) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0){
                    finishListener.finished(pictures);
                }
                super.handleMessage(msg);
            }
        };
        for (int i = 0; i < imgs.size(); i++) {
            final int pos = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(imgs.get(pos));
                    UploadImgBiz uploadImgBiz = new UploadImgBiz();
                    uploadImgBiz.uploadImg(file, new UploadImgBiz.OnUploadListener() {
                        @Override
                        public void success(BaseBean baseBean) {
                            if (baseBean.getResultCode() == 1) {
                                PictureBean pictureBean = new PictureBean();
                                pictureBean.setPicture_URL(baseBean.getBody());
                                pictures.add(pictureBean);
                            }
                            okImgCounts++;
                            if (okImgCounts == imgs.size()){
                                Message msg = new Message();
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                            }
                        }

                        @Override
                        public void failed() {
                            okImgCounts++;
                            if (okImgCounts == imgs.size()){
                                Message msg = new Message();
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                            }
                        }
                    });
                }
            }).start();
        }
    }
}
