package com.hsd.asmfsx.bean;

import org.greenrobot.greendao.annotation.Entity;
public class BaseBean {

	public Integer resultCode; // 结果码

	public String describe; // 描述

	public String body; // 主体(可放置数据)

	public String timeStamp; // 时间戳

	public String UUID;

	// -------------------------------------
	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

}
