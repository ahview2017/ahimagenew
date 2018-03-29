package com.deepai.photo.bean;

import java.util.Date;

/**
 * 稿件收藏表
 * @author xiayunan
 * @date   2018年3月28日
 *
 */
public class CpFavoritePicGroups {
	 private Integer id;

     private Date crtime;

     private Integer userId;
     
     private Integer groupId;

     private Integer status;
     
     private String append;

     public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

     public Integer getId() {
		 return id;
	 }

	 public void setId(Integer id) {
		 this.id = id;
	 }

	 public Date getCrtime() {
		 return crtime;
	 }

	 public void setCrtime(Date crtime) {
		 this.crtime = crtime;
	 }

	 

	 public Integer getGroupId() {
		 return groupId;
	 }

	 public void setGroupId(Integer groupId) {
		 this.groupId = groupId;
	 }

	 public Integer getStatus() {
		 return status;
	 }

	 public void setStatus(Integer status) {
		 this.status = status;
	 }

	 public String getAppend() {
		 return append;
	 }

	 public void setAppend(String append) {
		 this.append = append;
	 }

	
     
}
