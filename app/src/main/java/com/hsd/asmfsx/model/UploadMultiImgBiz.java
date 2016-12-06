package com.hsd.asmfsx.model;

import android.os.Handler;
import android.os.Message;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.PictureBean;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by apple on 2016/11/14.
 * 多图片上传
 */

public class UploadMultiImgBiz {
    private int okImgCounts = 0;
    private int failedCounts = 0;
    private List<PictureBean> pictures = new ArrayList<>();

    public interface OnFinishListener {
        /**
         * 所有图片上传完毕后的回调
         * @param pictures 上传成功的图片URL
         * @param failedCounts 上传失败的图片个数
         */
        void finished(List<PictureBean> pictures, int failedCounts);
    }
    private synchronized int addCounts(){
        okImgCounts = okImgCounts + 1;
        return okImgCounts;
    }

    public void doUpload(final List<String> imgs, final OnFinishListener finishListener) {
        if (imgs.size() == 0){
            finishListener.finished(null, 0);
            return;
        }else {
            final Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        finishListener.finished(pictures, failedCounts);
                    }
                    super.handleMessage(msg);
                }
            };
            //使用线程池，固定只开三个线程
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
            for (int i = 0; i < imgs.size(); i++) {
                final int pos = i;
                fixedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        String threadName = Thread.currentThread().getName();
                        Logger.d("线程名：" + threadName + "，上传第 " + pos + "张图片");
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
                                int counts = addCounts();
                                if (counts == imgs.size()) {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    mHandler.sendMessage(msg);
                                }
                            }

                            @Override
                            public void failed() {
                                failedCounts = failedCounts + 1;
                                int counts = addCounts();
                                if (counts == imgs.size()) {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    mHandler.sendMessage(msg);
                                }
                            }
                        });
                    }
                });
        }

            /*new Thread(new Runnable() {
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
                            okImgCounts = okImgCounts + 1;
                            if (okImgCounts == imgs.size()){
                                Message msg = new Message();
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                            }
                        }

                        @Override
                        public void failedForException() {
                            okImgCounts = okImgCounts + 1;
                            if (okImgCounts == imgs.size()){
                                Message msg = new Message();
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                            }
                        }
                    });
                }
            }).start();*/
        }
    }
}
