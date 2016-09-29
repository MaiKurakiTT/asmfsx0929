package com.hsd.asmfsx.bean;

public class UserBean extends BaseBean {

	private Integer user_ID;
	private String user_phone;
	private String user_password;
	private String user_uuid;
	private UserInformationBean userInformation;
//	private UserOthersDataBean userOthersData;
	private HeartBeatBean heartBeat;
	// 学号
	// 2016年3月21日 19:50:21
	private String student_ID;

	// private List<FriendCircleBean> friendCircles = new
	// ArrayList<FriendCircleBean>();
	public Integer getUser_ID() {
		return user_ID;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public String getUser_password() {
		return user_password;
	}

	public String getUser_uuid() {
		return user_uuid;
	}



	public UserInformationBean getUserInformation() {
		return userInformation;
	}

	public String getStudent_ID() {
		return student_ID;
	}

	public void setUser_ID(Integer user_ID) {
		this.user_ID = user_ID;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}

	public void setUserInformation(UserInformationBean userInformation) {
		this.userInformation = userInformation;
	}

	public void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}

	public HeartBeatBean getHeartBeat() {
		return heartBeat;
	}

	public void setHeartBeat(HeartBeatBean heartBeat) {
		this.heartBeat = heartBeat;
	}

}
