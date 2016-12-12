package com.hsd.asmfsx.bean;


import java.io.Serializable;

public class ShopVO implements Serializable{

	private Long id;
	
	private String name;
	
	private String description;
	
	private UserVO userVO;
	
	private String picture;
	
	private int commodityAmount;
	
	private double lowestPrice;
	
	private int sales;
	
	private Double x;
	
	private Double y;
	
	private String address;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public int getCommodityAmount() {
		return commodityAmount;
	}

	public void setCommodityAmount(int commodityAmount) {
		this.commodityAmount = commodityAmount;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
