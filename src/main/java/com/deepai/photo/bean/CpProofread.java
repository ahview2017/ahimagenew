package com.deepai.photo.bean;

import java.util.Date;
import java.util.List;

public class CpProofread {
    private Integer id;

    private Date beginTime;

    private Date endTime;

    private Byte isenable;

    private Integer siteId;

    //添加langType字段，以及相关get\set方法
    private Integer langType;
    
    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Byte deleteFlag;
    
    private List<CpDutyUser> dutys;

    
    
    public List<CpDutyUser> getDutys() {
		return dutys;
	}

	public void setDutys(List<CpDutyUser> dutys) {
		this.dutys = dutys;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getIsenable() {
        return isenable;
    }

    public void setIsenable(Byte isenable) {
        this.isenable = isenable;
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

	public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}
}