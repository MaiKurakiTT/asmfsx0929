package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.bean.UserBean;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
     * @param certificationBiz  需要教务系统学号、密码、学校名存入返回CertificationBean
     * @return  返回CertificationBean
     */
    @POST("/Server/MainServer?method=check")
    Call<CertificationBean> postCertification(@Body CertificationBean certificationBean);

    /**
     * 注册
     * @param registerBean  需要学号、手机号、密码存入RegisterBean
     * @return  返回RegisterBean
     */
    @POST("/Server/MainServer?method=register")
    Call<RegisterBean> postRegister(@Body RegisterBean registerBean);

    @Multipart
    @POST("/Server/UploadServer")
    Call<BaseBean> uploadImg(@Part MultipartBody.Part img);
}
