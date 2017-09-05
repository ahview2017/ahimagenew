package com.deepai.photo.bean;

import java.util.Date;

public class CpFriendshipLink {

	private Integer id;
	private String title;
	private Date createTime;
	private String createUser;
	private Integer createUserId;
	private Integer orderId;
	private String friendshipLink;
	private String remark;
	private Integer state;
	private Integer langType;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		this.createUser = createUser;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getFriendshipLink() {
		return friendshipLink;
	}

	public void setFriendshipLink(String friendshipLink) {
		this.friendshipLink = friendshipLink;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

}
