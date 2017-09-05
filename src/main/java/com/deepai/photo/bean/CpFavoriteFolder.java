package com.deepai.photo.bean;

import java.util.Date;
import java.util.List;

public class CpFavoriteFolder {
    private Integer id;

    private String folderName;

    private String memo;

    private Date createTime;
    private Integer uid;
    private Integer createId;
    private List<CpFavoriteFolderPic> cpFavoriteFolderPics;
    
    
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public List<CpFavoriteFolderPic> getCpFavoriteFolderPics() {
		return cpFavoriteFolderPics;
	}
	public void setCpFavoriteFolderPics(List<CpFavoriteFolderPic> cpFavoriteFolderPics) {
		this.cpFavoriteFolderPics = cpFavoriteFolderPics;
	}

}