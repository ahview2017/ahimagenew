package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpCategory {
    private Integer id;

    private String categoryName;

    private String categoryNumber;

    private Integer categoryOrder;

    private Date createTime;

    private String createUser;

    private Integer categoryGrade;

    private String memo;

    private Integer parentId;

    private Integer siteId;

    private String image;

    private Byte showType;

    private String updateUser;

    private Date updateTime;

    private Byte deleteFlag;
    
    private Integer langType;
    
    private Integer allowSubmissions;//允许投稿标识 ,专题专用  add by xiayunan@20180130
    
    public Integer getAllowSubmissions() {
		return allowSubmissions;
	}

	public void setAllowSubmissions(Integer allowSubmissions) {
		this.allowSubmissions = allowSubmissions;
	}

	private List<CpCategory> categories = new ArrayList<CpCategory>();
    

    
    public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	public List<CpCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<CpCategory> categories) {
		this.categories = categories;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber == null ? null : categoryNumber.trim();
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
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

    public Integer getCategoryGrade() {
        return categoryGrade;
    }

    public void setCategoryGrade(Integer categoryGrade) {
        this.categoryGrade = categoryGrade;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Byte getShowType() {
        return showType;
    }

    public void setShowType(Byte showType) {
        this.showType = showType;
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