package com.deepai.photo.bean;

import java.util.Date;

public class CpWaterMarkPicture {
    private Integer id;

    private Date createTime;

    private String createUser;

    private String description;

    private String filename;

    private Integer isDefaultWmpic;

    private Integer siteId;

    private Integer sortId;

    private String title;

    private String updateUser;

    private Date updateTime;

    private Byte deleteFlag;
    
    private String wmPath;

    public String getWmPath() {
		return wmPath;
	}

	public void setWmPath(String wmPath) {
		this.wmPath = wmPath;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Integer getIsDefaultWmpic() {
        return isDefaultWmpic;
    }

    public void setIsDefaultWmpic(Integer isDefaultWmpic) {
        this.isDefaultWmpic = isDefaultWmpic;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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
}