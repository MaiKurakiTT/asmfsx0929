package com.hsd.asmfsx.bean;

import java.util.Date;

public class CommentVO {
	
	private Long id;

	private String content;
	/**
	 * @Fields userVO : 当前发评论的人
	 */
	private UserVO userVO;
	/**
	 * @Fields byUserID : 被@的人
	 */
	private UserVO byUserVO;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public UserVO getByUserVO() {
		return byUserVO;
	}

	public void setByUserVO(UserVO byUserVO) {
		this.byUserVO = byUserVO;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
