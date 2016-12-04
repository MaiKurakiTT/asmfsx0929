package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.bean.LoginBean2;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @POST("/F/login.action")
    @FormUrlEncoded
    Call<Object> postLogin2(@Field("phone") String phone, @Field("password") String password);

    @POST("/F/user/me.action")
    Call<Object> postGetMe();

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
     * checkSchool
     * @param certificationBiz  需要学校名存入CertificationBean
     * @return  返回CertificationBean
     */
    @POST("/Server/MainServer?method=getModel")
    Call<CertificationBean> postCheckSchool(@Body CertificationBean certificationBean);

    /**
     * 注册
     * @param registerBean  需要学号、手机号、密码存入RegisterBean
     * @return  返回RegisterBean
     */
    @POST("/Server/MainServer?method=register")
    Call<RegisterBean> postRegister(@Body RegisterBean registerBean);

    /**
     * 上传图片，暂时不用
     * @param img
     * @return
     */
    @Multipart
    @POST("/Server/UploadServer")
    Call<BaseBean> uploadImg(@Part MultipartBody.Part img);

    /**
     * 在注册后设置用户信息的请求
     * @param userInformationBean
     * @return
     */
    @POST("/Server/MainServer?method=updateUserInformation")
    Call<UserInformationBean> postSetUserInfo(@Body UserInformationBean userInformationBean);

    /**
     * 找朋友
     * @param findFriendsBean
     * @return
     */
    @POST("/Server/MainServer?method=findFriend")
    Call<FindFriendsBean> postFindFriends(@Body FindFriendsBean findFriendsBean);

    /**
     * 请求朋友圈数据
     * @param friendCircleBean
     * @return
     */
    @POST("/Server/MainServer?method=getFriendCircle")
    Call<BaseBean> postFriendCircle(@Body FriendCircleBean friendCircleBean);

    /**
     * 发表朋友圈
     * @param friendCircleBean
     * @return
     */
    @POST("/Server/MainServer?method=addFriendCircle")
    Call<BaseBean> postPutFriendCircle(@Body FriendCircleBean friendCircleBean);
    /**
     * 发表朋友圈的评论
     * @param friendCircleBean
     * @return
     */
    @POST("/Server/MainServer?method=addComment")
    Call<BaseBean> postPutFriendCircleComment(@Body FriendCircleBean friendCircleBean);
    /**
     * 朋友圈点赞
     * @param friendCircleBean
     * @return
     */
    @POST("/Server/MainServer?method=addLike")
    Call<BaseBean> postPutFriendCircleGood(@Body FriendCircleBean friendCircleBean);
}
