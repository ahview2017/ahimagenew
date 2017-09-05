package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CpPicGroup {
    private Integer id;

    private String author;

    private Integer authorId;

    private String categoryInfo;

    private Integer coverPictureId;

    private Date createTime;

    private Date applyTime;

    private String creator;

    private Integer creatorId;

    private Byte deleteFlag;

    private String keywords;

    private String memo;

    private Integer pictureCount;

    private Integer pictureDeletedCount;

    private String place;

    private String people;

    private Date fileTime;

    private Byte properties;

    private Integer siteId;

    private Integer groupStatus;

    private String title;

    private Byte type;

    private Integer isLocked;

    private String lockerName;

    private String unlockName;

    private String truckAuthorTrueName;

    private String trucksAuthorTrueName;

    private Byte securityType;

    private String remark;

    private String calGroupPrice;

    private BigDecimal discountPrice;

    private BigDecimal discountRate;

    private String forumsInfo;

    private Date forumsTime;

    private Integer goodsType;

    private Integer priceType;

    private Integer price;

    private BigDecimal reducePrice;

    private Integer emergency;

    private Integer langType;

    private String updateUser;

    private Date updateTime;

    private String fristPfdUser;

    private Integer sourceId;

    private Date sginTime;
    
    private Integer sourceSign;

	private Integer locationType;
	
	private String innerRemark;
	private String operateUserName;
	private Date operateTime;
	
	//视频新增字段 Add by xiayunan 2017-09-03
	private Integer masvideoSign;
	private Integer videoId;
   

	private Integer qbStatus;
	    
    public Integer getQbStatus() {
        return qbStatus;
    }

    public void setQbStatus(Integer qbStatus) {
        this.qbStatus = qbStatus;
    }
	    
	public Integer getMasvideoSign() {
		return masvideoSign;
	}

	public void setMasvideoSign(Integer masvideoSign) {
		this.masvideoSign = masvideoSign;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	private String delRemark;
    private String backRemark;
    private String Datetime;
    private String truename;
    private String path;   
    private List<CpPicture> pics;
    private List<CpCategory> cates;
    private List<CpPicGroupProcess> pros;
    private CpLanmuGroupPic cpLanmuGroupPic;
    
    
    public CpLanmuGroupPic getCpLanmuGroupPic() {
		return cpLanmuGroupPic;
	}

	public void setCpLanmuGroupPic(CpLanmuGroupPic cpLanmuGroupPic) {
		this.cpLanmuGroupPic = cpLanmuGroupPic;
	}

	public String getDelRemark() {
		return delRemark;
	}

	public void setDelRemark(String delRemark) {
		this.delRemark = delRemark;
	}

	public String getTruename() {
		return truename;
	}

	public String getInnerRemark() {
		return innerRemark;
	}

	public void setInnerRemark(String innerRemark) {
		this.innerRemark = innerRemark;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public List<CpPicGroupProcess> getPros() {
		return pros;
	}

	public void setPros(List<CpPicGroupProcess> pros) {
		this.pros = pros;
	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<CpPicture> getPics() {
		return pics;
	}

	public void setPics(List<CpPicture> pics) {
		this.pics = pics;
	}

	public List<CpCategory> getCates() {
		return cates;
	}

	public void setCates(List<CpCategory> cates) {
		this.cates = cates;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(String categoryInfo) {
        this.categoryInfo = categoryInfo == null ? null : categoryInfo.trim();
    }

    public Integer getCoverPictureId() {
        return coverPictureId;
    }

    public void setCoverPictureId(Integer coverPictureId) {
        this.coverPictureId = coverPictureId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(Integer pictureCount) {
        this.pictureCount = pictureCount;
    }

    public Integer getPictureDeletedCount() {
        return pictureDeletedCount;
    }

    public void setPictureDeletedCount(Integer pictureDeletedCount) {
        this.pictureDeletedCount = pictureDeletedCount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people == null ? null : people.trim();
    }

    public Date getFileTime() {
        return fileTime;
    }

    public void setFileTime(Date fileTime) {
        this.fileTime = fileTime;
    }

    public Byte getProperties() {
        return properties;
    }

    public void setProperties(Byte properties) {
        this.properties = properties;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public String getLockerName() {
        return lockerName;
    }

    public void setLockerName(String lockerName) {
        this.lockerName = lockerName == null ? null : lockerName.trim();
    }

    public String getUnlockName() {
        return unlockName;
    }

    public void setUnlockName(String unlockName) {
        this.unlockName = unlockName == null ? null : unlockName.trim();
    }

    public String getTruckAuthorTrueName() {
        return truckAuthorTrueName;
    }

    public void setTruckAuthorTrueName(String truckAuthorTrueName) {
        this.truckAuthorTrueName = truckAuthorTrueName == null ? null : truckAuthorTrueName.trim();
    }

    public String getTrucksAuthorTrueName() {
        return trucksAuthorTrueName;
    }

    public void setTrucksAuthorTrueName(String trucksAuthorTrueName) {
        this.trucksAuthorTrueName = trucksAuthorTrueName == null ? null : trucksAuthorTrueName.trim();
    }

    public Byte getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Byte securityType) {
        this.securityType = securityType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCalGroupPrice() {
        return calGroupPrice;
    }

    public void setCalGroupPrice(String calGroupPrice) {
        this.calGroupPrice = calGroupPrice == null ? null : calGroupPrice.trim();
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public String getForumsInfo() {
        return forumsInfo;
    }

    public void setForumsInfo(String forumsInfo) {
        this.forumsInfo = forumsInfo == null ? null : forumsInfo.trim();
    }

    public Date getForumsTime() {
        return forumsTime;
    }

    public void setForumsTime(Date forumsTime) {
        this.forumsTime = forumsTime;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    public Integer getEmergency() {
        return emergency;
    }

    public void setEmergency(Integer emergency) {
        this.emergency = emergency;
    }

    public Integer getLangType() {
        return langType;
    }

    public void setLangType(Integer langType) {
        this.langType = langType;
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

    public String getFristPfdUser() {
        return fristPfdUser;
    }

    public void setFristPfdUser(String fristPfdUser) {
        this.fristPfdUser = fristPfdUser == null ? null : fristPfdUser.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Date getSginTime() {
        return sginTime;
    }

    public void setSginTime(Date sginTime) {
        this.sginTime = sginTime;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }
    public Integer getSourceSign() {
		return sourceSign;
	}

	public void setSourceSign(Integer sourceSign) {
		this.sourceSign = sourceSign;
	}

	public String getBackRemark() {
		return backRemark;
	}

	public void setBackRemark(String backRemark) {
		this.backRemark = backRemark;
	}
}