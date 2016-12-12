package com.hsd.asmfsx.bean;

import java.util.List;

public class CommodityVO {

private Long id;
	
	private String name;
		
	/**
	 * 门市价格
	 */
	private Double retailPrice;
	
	private Double price;
	/**
	 * 库存
	 */
	private Long inventory;
	
	private String description;
	
	//使用规则
	private String rule;
	
	private List<CommentVO> commentVOs;
	/**
	 * 单项一对多
	 */
	private List<String> pictures;
	/**
	 * 商铺
	 * 双向多对一
	 */
	private ShopVO shopVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public ShopVO getShopVO() {
		return shopVO;
	}

	public void setShopVO(ShopVO shopVO) {
		this.shopVO = shopVO;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public List<CommentVO> getCommentVOs() {
		return commentVOs;
	}

	public void setCommentVOs(List<CommentVO> commentVOs) {
		this.commentVOs = commentVOs;
	}
	
	
}
