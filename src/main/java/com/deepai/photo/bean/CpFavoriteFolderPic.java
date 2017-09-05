package com.deepai.photo.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CpFavoriteFolderPic {
    private Integer id;

    private Integer pictureId;

    private String pictureFilename;

    private Integer folderId;

    private Integer pictureLength;

    private Integer pictureWidth;

    private Integer pictureHeight;

    private Integer groupId;

    private String groupTitle;

    private Date publicTime;

    private String creater;

    private String allPath;

    private Integer userId;

	private Integer priceType;
    
    private Double picturePrice;
    private String datetime;
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Double getPicturePrice() {
		return picturePrice;
	}

	public void setPicturePrice(Double picturePrice) {
		this.picturePrice = picturePrice;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureFilename() {
        return pictureFilename;
    }

    public void setPictureFilename(String pictureFilename) {
        this.pictureFilename = pictureFilename == null ? null : pictureFilename.trim();
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    
    public Integer getPricetype() {
		return priceType;
	}

	public void setPricetype(Integer priceType) {
		this.priceType = priceType;
	}

    public Integer getPictureLength() {
        return pictureLength;
    }

    public void setPictureLength(Integer pictureLength) {
        this.pictureLength = pictureLength;
    }

    public Integer getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(Integer pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    public Integer getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(Integer pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle == null ? null : groupTitle.trim();
    }

    public String getPublicTime() {
    	if (this.publicTime!=null) {
    		Date publicTime2 = this.publicTime;
    		String datetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(publicTime2);
    		return datetime;
		}
    	return "";
    }

    public void setPublicTime(Date publicTime) {
    	this.publicTime = publicTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getAllPath() {
        return allPath;
    }

    public void setAllPath(String allPath) {
        this.allPath = allPath == null ? null : allPath.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}