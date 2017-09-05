package com.deepai.photo.bean;

import java.util.Date;

public class CpPicturePrice {
    private Integer id;

    private Integer pictureType;

    private String pictureLittleType;

    private Double setPrice;

    private Double payPrice;

    private Date updateTime;

    private String editName;

    private String container;
    private String Datetime;
    private String allpath;

    public String getAllpath() {
		return allpath;
	}

	public void setAllpath(String allpath) {
		this.allpath = allpath;
	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPictureType() {
        return pictureType;
    }

    public void setPictureType(Integer pictureType) {
        this.pictureType = pictureType;
    }

    public String getPictureLittleType() {
        return pictureLittleType;
    }

    public void setPictureLittleType(String pictureLittleType) {
        this.pictureLittleType = pictureLittleType == null ? null : pictureLittleType.trim();
    }

    public Double getSetPrice() {
        return setPrice;
    }

    public void setSetPrice(Double setPrice) {
        this.setPrice = setPrice;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName == null ? null : editName.trim();
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container == null ? null : container.trim();
    }
}