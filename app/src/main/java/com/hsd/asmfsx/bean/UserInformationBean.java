package com.hsd.asmfsx.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
public class UserInformationBean extends BaseBean {

	// 属性
	private Integer userInformation_ID;
	private String user_phone;
	private String user_icon;
	private String user_nickname;
	private String user_sex;
	private Date user_birthday;
	private String user_star;//
	private Integer user_height;
	private String user_sign;//
	private String user_education;//
	private String user_department;//
	private String user_year;//
	private String user_locality;
	private String user_graduate;//
	private String user_idol;//
	private String user_book;//
	private String user_like;//
	private String user_school;
	private String user_state;
	private String user_registerDate;

 	private UserBean user;

	// ios使用的年龄
	private Integer user_age_IOS;

	// private String user_ID;//服务器外键关联使用
	// private UserBean user;
	//
	//

	// ------------------------------------------------

	
	
	
	public Integer getUserInformation_ID() {
		return userInformation_ID;
	}

	
	public String getUser_registerDate() {
		return user_registerDate;
	}


	public void setUser_registerDate(String user_registerDate) {
		this.user_registerDate = user_registerDate;
	}


	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public void setUserInformation_ID(Integer userInformation_ID) {
		this.userInformation_ID = userInformation_ID;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_icon() {
		return user_icon;
	}

	public void setUser_icon(String user_icon) {
		this.user_icon = user_icon;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public Date getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(Date user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_star() {
		return user_star;
	}

	public void setUser_star(String user_star) {
		this.user_star = user_star;
	}

	public Integer getUser_height() {
		return user_height;
	}

	public void setUser_height(Integer user_height) {
		this.user_height = user_height;
	}

	public String getUser_sign() {
		return user_sign;
	}

	public void setUser_sign(String user_sign) {
		this.user_sign = user_sign;
	}

	public String getUser_education() {
		return user_education;
	}

	public void setUser_education(String user_education) {
		this.user_education = user_education;
	}

	public String getUser_department() {
		return user_department;
	}

	public void setUser_department(String user_department) {
		this.user_department = user_department;
	}

	public String getUser_year() {
		return user_year;
	}

	public void setUser_year(String user_year) {
		this.user_year = user_year;
	}

	public String getUser_locality() {
		return user_locality;
	}

	public void setUser_locality(String user_locality) {
		this.user_locality = user_locality;
	}

	public String getUser_graduate() {
		return user_graduate;
	}

	public void setUser_graduate(String user_graduate) {
		this.user_graduate = user_graduate;
	}

	public String getUser_idol() {
		return user_idol;
	}

	public void setUser_idol(String user_idol) {
		this.user_idol = user_idol;
	}

	public String getUser_book() {
		return user_book;
	}

	public void setUser_book(String user_book) {
		this.user_book = user_book;
	}

	public String getUser_like() {
		return user_like;
	}

	public void setUser_like(String user_like) {
		this.user_like = user_like;
	}

	public String getUser_school() {
		return user_school;
	}

	public void setUser_school(String user_school) {
		this.user_school = user_school;
	}

	public String getUser_state() {
		return user_state;
	}

	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}

	// public UserBean getUser() {
	// return user;
	// }
	//
	// public void setUser(UserBean user) {
	// this.user = user;
	// }

	public Integer getUser_age_IOS() {
		return user_age_IOS;
	}

	public void setUser_age_IOS(Integer user_age_IOS) {
		this.user_age_IOS = user_age_IOS;
	}

}
