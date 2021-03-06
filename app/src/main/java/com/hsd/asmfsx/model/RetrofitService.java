package com.hsd.asmfsx.model;

import com.hsd.asmfsx.bean.BaseBean;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.CertificationBean;
import com.hsd.asmfsx.bean.CommodityVO;
import com.hsd.asmfsx.bean.FindFriendsBean;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.FriendCircleVO;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.bean.LoginBean;
import com.hsd.asmfsx.bean.LoginBean2;
import com.hsd.asmfsx.bean.NormalResultBean;
import com.hsd.asmfsx.bean.OrderListBean;
import com.hsd.asmfsx.bean.OrderVO;
import com.hsd.asmfsx.bean.RegisterBean;
import com.hsd.asmfsx.bean.ShiMingBean;
import com.hsd.asmfsx.bean.ShopVO;
import com.hsd.asmfsx.bean.UserBean2;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.global.GlobalParameter;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by apple on 2016/10/16.
 */

public interface RetrofitService {

    /**
     * 登录，需要手机号和密码
     * @param phone
     * @param password
     * @return
     */
    @POST(GlobalParameter.project2 + "login.action")
    @FormUrlEncoded
    Call<NormalResultBean<LoginBean2>> postLogin2(@Field("phone") String phone, @Field("password") String password);

    /**
     * 注册，需要手机号、密码、学号
     * @param phone
     * @param password
     * @param studentID
     * @return
     */
    @POST(GlobalParameter.project2 + "register.action")
    @FormUrlEncoded
    Call<BaseBean2> postRegister2(@Field("schoolNumber") String schoolNumber, @Field("studentID") String studentID, @Field("phone") String phone, @Field("password") String password);

    @POST(GlobalParameter.project2 + "user/me.action")
    Call<Object> postGetMe();

    /**
     * 更新或设置用户信息
     * @param userInformationBean
     * @return
     */
    @POST(GlobalParameter.project2 + "user/updateUserInformation.action")
    Call<BaseBean2> postSetUserInfo(@Body UserInformationBean2 userInformationBean);

    /**
     * 获取实名认证状态
     * @return
     */
    @POST(GlobalParameter.project2 + "user/getVerifyStudentState.action")
    Call<NormalResultBean<ShiMingBean>> postGetShiMingState();

    /**
     * 上传实名认证资料
     * @param realName
     * @param schoolNum
     * @param img
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/verifyStudent2.action")
    Call<BaseBean2> postWriteShiMing(@Field("realName") String realName, @Field("schoolNum") Long schoolNum, @Field("img") String img);

    /**
     * 获取用户信息，需要一个userid
     * @param userInformationID
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getUserInformation.action")
    Call<NormalResultBean<UserInformationBean2>> postGetUserInfo(@Field("userID") Long userInformationID);

    /**
     * 图片上传
     * @param img
     * @return
     */
    @Multipart
    @POST("img/" + "upload")
    Call<NormalResultBean<String[]>> uploadImg2(@Part MultipartBody.Part img);

    @Multipart
    @POST("img/" + "upload")
    Call<String> multiUpload(@PartMap Map<String, RequestBody> params);

    /**
     * 发布朋友圈
     * @param content
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/fc/addFriendCircle.action")
    Call<BaseBean2> doPutFC(@Field("content") String content, @Field("pictures") List<String> pictures);

    /**
     * 请求朋友圈数据
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/fc/getFriendCircle.action")
    Call<NormalResultBean<List<FriendCircleVO>>> postGetFC(@Field("page") int page, @Field("limit") int limit);

    /**
     * 给说说评论
     * @param content 评论内容
     * @param friendCircleID 朋友圈ID
     * @param byUserID 被@的人的ID
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/fc/addComment.action")
    Call<BaseBean2> postFCComment(@Field("content") String content, @Field("friendCircleID") Long friendCircleID, @Field("byUserID") Long byUserID);

    /**
     * 给说说点赞
     * @param friendCircleID 朋友圈ID
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/fc/addLike.action")
    Call<BaseBean2> postFCGood(@Field("friendCircleID") Long friendCircleID);

    /**
     * 找朋友
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getRecommendUsers.action")
    Call<NormalResultBean<List<UserInformationBean2>>> postFindFriends(@Field("page") int page, @Field("limit") int limit);

    /**
     * 请求商家列表
     * @param page
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getShops.action")
    Call<NormalResultBean<List<ShopVO>>> postGetShopList(@Field("page") int page, @Field("limit") int limit);

    /**
     * 请求某个商家的商品列表
     * @param shopID
     * @param page
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getCommoditys.action")
    Call<NormalResultBean<List<CommodityVO>>> postGetShopGoodList(@Field("shopID") Long shopID, @Field("page") int page, @Field("limit") int limit);

    /**
     * 请求某个商品的信息
     * @param commodityID
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getCommodity.action")
    Call<NormalResultBean<CommodityVO>> postGetItemGoodInfo(@Field("commodityID") Long commodityID);

    /**
     * 商品下订单
     * @param commodityID
     * @param amount
     * @param detail
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/addOrder.action")
    Call<BaseBean2> postPlaceOrder(@Field("commodityID") Long commodityID, @Field("amount") int amount, @Field("price") double price, @Field("detail") String detail);


    /**
     * 获取订单列表
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getOrders.action")
    Call<NormalResultBean<List<OrderListBean>>> postGetOrderList(@Field("page") int page, @Field("limit") int limit);


    /**
     * 获取每个订单信息
     * @param orderID
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/getOrder.action")
    Call<NormalResultBean<OrderVO>> postGetOrder(@Field("orderID") Long orderID);


    /**
     * 添加心动好友
     * @param heartBeatUserID
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "user/addHeartBeat.action")
    Call<BaseBean2> postAddHB(@Field("heartBeatUserID") Long heartBeatUserID);

    /**
     * 获取心动列表
     * @return
     */
    @POST(GlobalParameter.project2 + "user/getHeartBeats.action")
    Call<NormalResultBean<List<HBListBean>>> postGetHBList();

    /**
     * 获取最新应用版本名
     * @return
     */
    @GET(GlobalParameter.project2 + "version.txt")
    Call<String> getVersionName();

    /**
     * checkSchool
     * @param certificationBiz  需要学校名存入CertificationBean
     * @return  返回CertificationBean
     */
    @FormUrlEncoded
    @POST(GlobalParameter.project2 + "verifyStudent.action")
    Call<BaseBean2> postCertification(@Field("schoolNumber") String schoolNumber, @Field("username") String username, @Field("password") String password);


/**
 * ------------------------------------------------------------------------------------
 */















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
    /*@POST("/Server/MainServer?method=check")
    Call<CertificationBean> postCertification(@Body CertificationBean certificationBean);*/

    /**
     * checkSchool
     * @param certificationBiz  需要学校名存入CertificationBean
     * @return  返回CertificationBean
     */
    @POST("/fsxasm/verifyStudent.action")
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
