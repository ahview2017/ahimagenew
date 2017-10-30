package com.deepai.photo.bean;

import java.util.Date;

/**
 * 点赞记录表
 * @author xiayunan
 * @date   2017年10月26日
 *
 */
public class CpPicGroupThumbsUp {
	 private Integer id;

     private Date crtime;

     private String ip;
     
     private Integer groupId;

     private Integer status;

     private String append;

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

	 public String getIp() {
		 return ip;
	 }

 	 public void setIp(String ip) {
	     this.ip = ip;
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
