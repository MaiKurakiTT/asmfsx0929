package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by apple on 2016/10/16.
 */

public interface RetrofitService {
    /**
     * 登录请求
     * @param loginBean 需要传一个LoginBean
     * @return  返回一个LoginBean
     */
    @POST("/Server/MainServer?method=login")
    Call<LoginBean> postLogin(@Body LoginBean loginBean);

    /**
     * 请求心动列表
     * @param baseBean  需要uuid，放BaseBean里传给服务器
     * @return  返回BaseBean
     */
    @POST("/Server/MainServer?method=getHeartBeat2")
    Call<BaseBean> postHB(@Body BaseBean baseBean);

    /**
     * 实名认证
     * @param certificationBiz  需要教务系统学号、密码、学校名
     * @return  返回CertificationBean
     */
    @POST("/Server/MainServer?method=check")
    Call<CertificationBean> postCertification(@Body CertificationBean certificationBean);
}
