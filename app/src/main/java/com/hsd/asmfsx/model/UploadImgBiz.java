package com.hsd.asmfsx.model;

import android.os.Environment;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.global.GlobalParameter;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 紫荆 on 2016/10/21.
 */

public class UploadImgBiz {
    private String ip = GlobalParameter.ip + "Server/UploadServer";
    public static UploadImgBiz uploadImgBiz;
    public static UploadImgBiz getInstance(){
        if (uploadImgBiz == null){
            uploadImgBiz = new UploadImgBiz();
        }
        return uploadImgBiz;
    }
    public interface OnUploadListener{
        void success(BaseBean baseBean);
        void failed();
    }
    public void uploadImg(String fileUri, UploadImgBiz.OnUploadListener uploadListener)
            throws IOException {
        File uploadFile = new File(Environment.getExternalStorageDirectory(), fileUri);
        URL url = new URL(ip);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        InputStream in = new FileInputStream(uploadFile);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1)
            out.write(buf, 0, len);
        in.close();
        out.close();
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = bufferedReader.readLine();
            BaseBean baseBean = GetGson.getGson().fromJson(s, BaseBean.class);
            uploadListener.success(baseBean);
        }else {
            Logger.d("上传时ResponseCode不为200");
            uploadListener.failed();
        }
    }
}
