package com.deepai.photo.bean;

import java.util.Date;

public class CpPictureDownloadrecord {
	private Integer id;

	private Date downloadTime;

	private Integer pictureGroupId;

	private Integer pictureId;

	private String pictureSize;

	private Integer pictureType;

	private Integer siteId;

	private Integer userId;

	private Byte watermarker;

	private String userName;

	private String pictureTitle;

	private String pictureFileName;

	private String pictureFilePath;

	private Date pictureSignTime;

	private String userIP;
	
	private Integer langType;

	private double picturePrice;

	private String pictureGroup;

	private Integer subscriberType;

	private String pictureAuthor;

	private String editUser;

	private Integer isInteriorDownLoad;

	private double incomeBefore;

	private Integer authorId;
	
	private String authorLoginName;
	
	private String downStartTime;

	private String downEndTime;

	private String signStartTime;

	private String signEndTime;

	private String orderByCase;

	private Integer selectType;

	private Integer page;

	private Integer rows;
	
	private double downLoadPrice;

	
	public double getDownLoadPrice() {
		return downLoadPrice;
	}

	public void setDownLoadPrice(double downLoadPrice) {
		this.downLoadPrice = downLoadPrice;
	}

	public Integer getId() {
		return id;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorLoginName() {
		return authorLoginName;
	}

	public void setAuthorLoginName(String authorLoginName) {
		this.authorLoginName = authorLoginName;
	}

	public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	
	public String getOrderByCase() {
		return orderByCase;
	}

	public void setOrderByCase(String orderByCase) {
		this.orderByCase = orderByCase;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	

	public String getEditUser() {
		return editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}
	

	public String getDownStartTime() {
		return downStartTime;
	}

	public void setDownStartTime(String downStartTime) {
		this.downStartTime = downStartTime;
	}

	public String getDownEndTime() {
		return downEndTime;
	}

	public void setDownEndTime(String downEndTime) {
		this.downEndTime = downEndTime;
	}

	public String getSignStartTime() {
		return signStartTime;
	}

	public void setSignStartTime(String signStartTime) {
		this.signStartTime = signStartTime;
	}

	public String getSignEndTime() {
		return signEndTime;
	}

	public void setSignEndTime(String signEndTime) {
		this.signEndTime = signEndTime;
	}

	public String getPictureGroup() {
		return pictureGroup;
	}

	public void setPictureGroup(String pictureGroup) {
		this.pictureGroup = pictureGroup;
	}

	public Integer getIsInteriorDownLoad() {
		return isInteriorDownLoad;
	}

	public void setIsInteriorDownLoad(Integer isInteriorDownLoad) {
		this.isInteriorDownLoad = isInteriorDownLoad;
	}

	public String getPictureTitle() {
		return pictureTitle;
	}

	public void setPictureTitle(String pictureTitle) {
		this.pictureTitle = pictureTitle;
	}

	public String getPictureFileName() {
		return pictureFileName;
	}

	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}

	public String getPictureFilePath() {
		return pictureFilePath;
	}

	public void setPictureFilePath(String pictureFilePath) {
		this.pictureFilePath = pictureFilePath;
	}

	public Date getPictureSignTime() {
		return pictureSignTime;
	}

	public void setPictureSignTime(Date pictureSignTime) {
		this.pictureSignTime = pictureSignTime;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public double getPicturePrice() {
		return picturePrice;
	}

	public void setPicturePrice(double picturePrice) {
		this.picturePrice = picturePrice;
	}

	public Integer getSubscriberType() {
		return subscriberType;
	}

	public void setSubscriberType(Integer subscriberType) {
		this.subscriberType = subscriberType;
	}

	public String getPictureAuthor() {
		return pictureAuthor;
	}

	public void setPictureAuthor(String pictureAuthor) {
		this.pictureAuthor = pictureAuthor;
	}

	public double getIncomeBefore() {
		return incomeBefore;
	}

	public void setIncomeBefore(double incomeBefore) {
		this.incomeBefore = incomeBefore;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	public Integer getPictureGroupId() {
		return pictureGroupId;
	}

	public void setPictureGroupId(Integer pictureGroupId) {
		this.pictureGroupId = pictureGroupId;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getPictureSize() {
		return pictureSize;
	}

	public void setPictureSize(String pictureSize) {
		this.pictureSize = pictureSize == null ? null : pictureSize.trim();
	}

	public Integer getPictureType() {
		return pictureType;
	}

	public void setPictureType(Integer pictureType) {
		this.pictureType = pictureType;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Byte getWatermarker() {
		return watermarker;
	}

	public void setWatermarker(Byte watermarker) {
		this.watermarker = watermarker;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}