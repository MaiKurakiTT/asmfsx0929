package com.hsd.asmfsx.bean;

/**
 * Created by sun on 2016/12/14.
 */

public class OrderListBean {
    /**
     * 订单号
     */
    private String orderNumber;
    private String commodityName;
    private Long commodityID;
    private Integer amount;
    private Double price;
    private String picture;
    private Long id;

    /**
     * 约定： state： 0：未付款 1：已付款 -1：冻结  2 已取消
     */
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
