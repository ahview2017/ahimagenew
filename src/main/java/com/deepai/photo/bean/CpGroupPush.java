package com.deepai.photo.bean;

import java.util.Date;

public class CpGroupPush {
	
	private Integer id;
	
	private Integer groupId;
	
	private Integer userId;
	
	private Date createTime;
	
	private Integer pushSign;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPushSign() {
		return pushSign;
	}

	public void setPushSign(Integer pushSign) {
		this.pushSign = pushSign;
	}
	
	

}
