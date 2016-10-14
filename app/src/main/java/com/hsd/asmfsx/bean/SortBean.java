package com.hsd.asmfsx.bean;

import java.util.ArrayList;

/**
 * Created by apple on 16/10/7.
 */

public class SortBean {
    public String retCode;
    public String msg;
    public ArrayList<SortResultBean> result;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<SortResultBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<SortResultBean> result) {
        this.result = result;
    }
}
