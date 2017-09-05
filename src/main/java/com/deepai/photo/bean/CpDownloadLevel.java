package com.deepai.photo.bean;

import java.util.Date;

public class CpDownloadLevel {
    private Integer id;

    private String levelName;

    private Integer limitPxH;

    private Integer limitPxW;

    private Byte isWatermark;

    private Integer watermarkLocation;

    private Integer siteId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Byte deleteFlag;

    private Integer watermarkPicId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    public Integer getLimitPxH() {
        return limitPxH;
    }

    public void setLimitPxH(Integer limitPxH) {
        this.limitPxH = limitPxH;
    }

    public Integer getLimitPxW() {
        return limitPxW;
    }

    public void setLimitPxW(Integer limitPxW) {
        this.limitPxW = limitPxW;
    }

    public Byte getIsWatermark() {
        return isWatermark;
    }

    public void setIsWatermark(Byte isWatermark) {
        this.isWatermark = isWatermark;
    }

    public Integer getWatermarkLocation() {
        return watermarkLocation;
    }

    public void setWatermarkLocation(Integer watermarkLocation) {
        this.watermarkLocation = watermarkLocation;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getWatermarkPicId() {
        return watermarkPicId;
    }

    public void setWatermarkPicId(Integer watermarkPicId) {
        this.watermarkPicId = watermarkPicId;
    }
}