package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CpOrderForm {
    private Integer id;

    private String orderNo;

    private Integer userId;

    private String userName;

    private Integer userType;

    private Date createTime;

    private Integer orderStatus;

    private Date lastSubmitTime;

    private BigDecimal totalPrice;

    private Date payTime;

    private Integer picCount;

    private Integer payType;
    
    public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	private Integer langType;//中英文版标志
    // 
    private String Datetime;
    public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
	public String getLasttime() {
		return Lasttime;
	}

	public void setLasttime(String lasttime) {
		Lasttime = lasttime;
	}

	private String Lasttime;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getLastSubmitTime() {
        return lastSubmitTime;
    }

    public void setLastSubmitTime(Date lastSubmitTime) {
        this.lastSubmitTime = lastSubmitTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}