package com.hsd.asmfsx.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendCircleVO {

	private Long id;
	/**
	 * @Fields content : 内容
	 */
	private String content;

	/**
	 * @Fields picutres : 图片
	 */
	private List<String> picutres = new ArrayList<String>();

	private List<CommentVO> commentVOs = new ArrayList<CommentVO>();
	
	private List<UserVO> likeUserVOs = new ArrayList<UserVO>();
	
	private UserVO userVO;

	private Date createTime;

	public List<CommentVO> getCommentVOs() {
		return commentVOs;
	}

	public void setCommentVOs(List<CommentVO> commentVOs) {
		this.commentVOs = commentVOs;
	}

	public List<UserVO> getLikeUserVOs() {
		return likeUserVOs;
	}

	public void setLikeUserVOs(List<UserVO> likeUserVOs) {
		this.likeUserVOs = likeUserVOs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getPicutres() {
		return picutres;
	}

	public void setPicutres(List<String> picutres) {
		this.picutres = picutres;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
