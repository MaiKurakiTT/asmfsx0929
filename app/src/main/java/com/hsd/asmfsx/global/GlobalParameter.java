package com.hsd.asmfsx.global;

/**
 * Created by apple on 2016/10/14.
 */

public  interface GlobalParameter {

    // 192.168.23.1  210.42.242.16  115.29.41.122
    String ip = "http://www.liuxinkeji.com:8080/";
    String project = "Server/";
    String ip2 = "http://192.168.191.1:8080/";

    String welcomeImageURl = "http://pic.nipic.com/2007-11-09/200711912453162_2.jpg";
    String newsURL = "http://news.sina.com.cn/c/2015-12-08/doc-ifxmhqac0188031.shtml";
    String REGISTER = ip + project + "/MainServer?method=register";
    String LOGIN = ip + project + "MainServer?method=login";
    String FRIEND_CRICLE_SEND = ip + project + "MainServer?method=addFriendCircle";

    String FOUND_FRIENDS = ip + project + "MainServer?method=findFriend";

    String UPLOAD_INFO =ip + project + "MainServer?method=updateUserInformation";

    String UPDATE_IMAGE = ip + project + "UploadServer";

    String RECOMMEND = ip + "HelpBrother/MainServlet?method=getRecommend";

    String SEND_SAID = ip + project + "MainServer?method=addFriendCircle";

    String GET_SAIDS = ip + project + "MainServer?method=getFriendCircle";

    String CHECK_STUDENT = ip + project + "MainServer?method=check";

    String FEED_BACK = ip + project + "MainServer?method=addFeedBack";

    String ADD_LIKE = ip + project + "MainServer?method=addLike";

    String ADD_HeartBeat = ip + project + "MainServer?method=addHeartBeat";
    String GET_HeartBeatList = ip + project + "MainServer?method=getHeartBeat";
    String GET_UserInfo = ip + project + "MainServer?method=getUserInformation";

    String ADD_COMMENT = ip + project + "MainServer?method=addComment";

    String GET_MODLE = ip + project + "MainServer?method=getModel";

    String UPDATA = ip + project + "update";



    String CREATE_TABLE = "create table userinfo(phone varchar(11) primary key,really_name varchar(10),name varchar(20),birthday date,headimage varchar(70),signature varchar(50),school varchar(30),school_department varchar(30),location varchar(30),edustartdate date,eduction varchar(20),school_original varchar(30),affectivestate int(1),timestamp varchar(20),sex varchar(10),uuid varchar(60),myself_infomation varchar(500));";


}
