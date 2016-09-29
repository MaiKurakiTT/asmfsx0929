package com.hsd.asmfsx.bean;

import java.util.Set;

/**
 * 
 * @author ZEAL
 *
 *		2016年4月22日 19:12:29			增加三列，set集合转换为字符串
 */
public class HeartBeatBean extends BaseBean {


	//心动表主键
	private Integer heartBeat_ID;
	//被心动人的ID
	private Integer byHeartBeatID;
	//心动用户集合
	private Set<Integer> heartBeatUserIDSet;
	//心动用户字符串集合
	private String heartBeatUserIDString;
	//心动数量
	private Integer heartBeatCount;
	//被心动用户集合
	private Set<Integer> byHeartBeatUserIDSet;
	//被心动用户字符串集合
	private String byHeartBeatUserIDString;
	//被心动数量
	private Integer byHeartBeatCount;
	//互相心动
	private Set<Integer> eachOtherHeartBeatSet;
	//互相心动用户字符串
	private String eachOtherHeartBeatCountString;
	//互相心动的数量
	private Integer eachOtherHeartBeatCount;
	
	//用户
	private UserBean user;

	public Integer getHeartBeat_ID() {
		return heartBeat_ID;
	}

	public Integer getByHeartBeatID() {
		return byHeartBeatID;
	}

	public Set<Integer> getHeartBeatUserIDSet() {
		return heartBeatUserIDSet;
	}

	public String getHeartBeatUserIDString() {
		return heartBeatUserIDString;
	}

	public Integer getHeartBeatCount() {
		return heartBeatCount;
	}

	public Set<Integer> getByHeartBeatUserIDSet() {
		return byHeartBeatUserIDSet;
	}

	public String getByHeartBeatUserIDString() {
		return byHeartBeatUserIDString;
	}

	public Integer getByHeartBeatCount() {
		return byHeartBeatCount;
	}

	public Set<Integer> getEachOtherHeartBeatSet() {
		return eachOtherHeartBeatSet;
	}

	public String getEachOtherHeartBeatCountString() {
		return eachOtherHeartBeatCountString;
	}

	public Integer getEachOtherHeartBeatCount() {
		return eachOtherHeartBeatCount;
	}

	public UserBean getUser() {
		return user;
	}

	public void setHeartBeat_ID(Integer heartBeat_ID) {
		this.heartBeat_ID = heartBeat_ID;
	}

	public void setByHeartBeatID(Integer byHeartBeatID) {
		this.byHeartBeatID = byHeartBeatID;
	}

	public void setHeartBeatUserIDSet(Set<Integer> heartBeatUserIDSet) {
		this.heartBeatUserIDSet = heartBeatUserIDSet;
	}

	public void setHeartBeatUserIDString(String heartBeatUserIDString) {
		this.heartBeatUserIDString = heartBeatUserIDString;
	}

	public void setHeartBeatCount(Integer heartBeatCount) {
		this.heartBeatCount = heartBeatCount;
	}

	public void setByHeartBeatUserIDSet(Set<Integer> byHeartBeatUserIDSet) {
		this.byHeartBeatUserIDSet = byHeartBeatUserIDSet;
	}

	public void setByHeartBeatUserIDString(String byHeartBeatUserIDString) {
		this.byHeartBeatUserIDString = byHeartBeatUserIDString;
	}

	public void setByHeartBeatCount(Integer byHeartBeatCount) {
		this.byHeartBeatCount = byHeartBeatCount;
	}

	public void setEachOtherHeartBeatSet(Set<Integer> eachOtherHeartBeatSet) {
		this.eachOtherHeartBeatSet = eachOtherHeartBeatSet;
	}

	public void setEachOtherHeartBeatCountString(
			String eachOtherHeartBeatCountString) {
		this.eachOtherHeartBeatCountString = eachOtherHeartBeatCountString;
	}

	public void setEachOtherHeartBeatCount(Integer eachOtherHeartBeatCount) {
		this.eachOtherHeartBeatCount = eachOtherHeartBeatCount;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	

	
}
