package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CpShopCar {
    private Integer id;

    private Integer picId;

    private Integer picType;

    private String picName;

    private String picTitle;

    private String createUsername;

    private Integer createUserid;

    private Date createTime;

    private Integer groupId;

    private BigDecimal price;

    private Integer width;

    private String height;

    private String orderId;

    private String allPath;

    private String publicTime;

    private Integer pictureLengh;

    private String pictureCreter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle == null ? null : picTitle.trim();
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getAllPath() {
        return allPath;
    }

    public void setAllPath(String allPath) {
        this.allPath = allPath == null ? null : allPath.trim();
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime == null ? null : publicTime.trim();
    }

    public Integer getPictureLengh() {
        return pictureLengh;
    }

    public void setPictureLengh(Integer pictureLengh) {
        this.pictureLengh = pictureLengh;
    }

    public String getPictureCreter() {
        return pictureCreter;
    }

    public void setPictureCreter(String pictureCreter) {
        this.pictureCreter = pictureCreter == null ? null : pictureCreter.trim();
    }
}