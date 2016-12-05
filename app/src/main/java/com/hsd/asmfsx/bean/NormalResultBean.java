package com.hsd.asmfsx.bean;

/**
 * Created by sun on 2016/12/5.
 * 服务器返回的一般结果
 */

public class NormalResultBean<T> extends BaseBean2{
    private T json;

    public T getJson() {
        return json;
    }

    public void setJson(T json) {
        this.json = json;
    }
}
