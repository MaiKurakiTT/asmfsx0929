package com.hsd.asmfsx.model;

import android.os.Handler;
import android.os.Message;

import com.hsd.asmfsx.bean.BaseBean2;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sun on 2016/12/7.
 */

public class UploadMultiImgBiz2 {
    private int okImgCounts = 0;
    private int failedCounts = 0;
    private List pictures = new ArrayList<>();

    public interface OnFinishListener {
        /**
         * 所有图片上传完毕后的回调
         * @param pictures 上传成功的图片URL
         * @param failedCounts 上传失败的图片个数
         */
        public void finished(List<String> pictures, int failedCounts);
    }
    private synchronized int addCounts(){
        okImgCounts = okImgCounts + 1;
        return okImgCounts;
    }
    public void doUpload(final List<String> imgs, final UploadMultiImgBiz2.OnFinishListener finishListener) {
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
                        new UploadImgBiz2().uploadImg(file, new UploadImgBiz2.OnUploadListener() {
                            @Override
                            public void success(String[] urls) {
                                pictures.add(urls[0]);
                                int counts = addCounts();
                                if (counts == imgs.size()) {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    mHandler.sendMessage(msg);
                                }
                            }

                            @Override
                            public void failedForResult(BaseBean2 baseBean) {
                                failedCounts = failedCounts + 1;
                                int counts = addCounts();
                                if (counts == imgs.size()) {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    mHandler.sendMessage(msg);
                                }
                            }

                            @Override
                            public void failedForException(Throwable t) {
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
        }
    }
}
