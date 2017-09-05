package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CpOrderDetail {
    private Integer id;

    private Integer orderId;

    private String orderNo;

    private String filename;

    private Integer picId;

    private Integer groupId;

    private BigDecimal price;

    private BigDecimal dealPrice;

    private Integer userId;

    private String userName;

    private Date createtime;

    private Integer photoUserId;

    private String photoUserName;

    private Integer photoUserDivide;

    private BigDecimal dividePrice;

    private Integer divideStatus;

    private Integer divideUserId;

    private String divideUserName;

    private Date divideTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getPhotoUserId() {
        return photoUserId;
    }

    public void setPhotoUserId(Integer photoUserId) {
        this.photoUserId = photoUserId;
    }

    public String getPhotoUserName() {
        return photoUserName;
    }

    public void setPhotoUserName(String photoUserName) {
        this.photoUserName = photoUserName == null ? null : photoUserName.trim();
    }

    public Integer getPhotoUserDivide() {
        return photoUserDivide;
    }

    public void setPhotoUserDivide(Integer photoUserDivide) {
        this.photoUserDivide = photoUserDivide;
    }

    public BigDecimal getDividePrice() {
        return dividePrice;
    }

    public void setDividePrice(BigDecimal dividePrice) {
        this.dividePrice = dividePrice;
    }

    public Integer getDivideStatus() {
        return divideStatus;
    }

    public void setDivideStatus(Integer divideStatus) {
        this.divideStatus = divideStatus;
    }

    public Integer getDivideUserId() {
        return divideUserId;
    }

    public void setDivideUserId(Integer divideUserId) {
        this.divideUserId = divideUserId;
    }

    public String getDivideUserName() {
        return divideUserName;
    }

    public void setDivideUserName(String divideUserName) {
        this.divideUserName = divideUserName == null ? null : divideUserName.trim();
    }

    public Date getDivideTime() {
        return divideTime;
    }

    public void setDivideTime(Date divideTime) {
        this.divideTime = divideTime;
    }
}