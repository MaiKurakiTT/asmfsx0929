package com.hsd.asmfsx.bean;

import java.util.Date;

public class OrderVO {

	
	private Long id;

	/**
	 * 订单号
	 */
	private String orderNumber;
	
	private Long commodityID;
	
	private String commodityName;
	
	private Double commodityRetailPrice;
	
	private Double commodityprice;
	
	private String commodityDescription;
	
	private Long shopID;
	
	private String shopName;
	
	private String shopAddress;
	
	private Double shopX;
	
	private Double shopY;
	
	private Integer amount;
	
	private Double price;
	
	/**
	 * 约定： state： 0：未付款 1：已付款 -1：冻结  2 已取消
	 */
	private Integer state;

	private String address;

	private Date createDate;

	private String detail;
	
	private Integer grade;
	
	private String orderEvaluate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Long commodityID) {
		this.commodityID = commodityID;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Double getCommodityRetailPrice() {
		return commodityRetailPrice;
	}

	public void setCommodityRetailPrice(Double commodityRetailPrice) {
		this.commodityRetailPrice = commodityRetailPrice;
	}

	public Double getCommodityprice() {
		return commodityprice;
	}

	public void setCommodityprice(Double commodityprice) {
		this.commodityprice = commodityprice;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public Long getShopID() {
		return shopID;
	}

	public void setShopID(Long shopID) {
		this.shopID = shopID;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Double getShopX() {
		return shopX;
	}

	public void setShopX(Double shopX) {
		this.shopX = shopX;
	}

	public Double getShopY() {
		return shopY;
	}

	public void setShopY(Double shopY) {
		this.shopY = shopY;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getOrderEvaluate() {
		return orderEvaluate;
	}

	public void setOrderEvaluate(String orderEvaluate) {
		this.orderEvaluate = orderEvaluate;
	}

	
}
