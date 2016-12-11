package com.hsd.asmfsx.alipay;

/**
 * Created by sun on 2016/12/11.
 */

public interface AlipayConfig {
    // 商户PID
    public  String PARTNER = "";
    // 商户收款账号
    public  String SELLER = "";
    // 商户私钥，pkcs8格式
    public  String RSA_PRIVATE = "";
    // 支付宝公钥
    public  String RSA_PUBLIC = "";
}
