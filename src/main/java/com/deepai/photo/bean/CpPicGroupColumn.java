package com.deepai.photo.bean;

public class CpPicGroupColumn {
	
	private Integer id;
	
	private Integer groupId;
	
	private Integer columnId;

	private Integer sginShow;
	
	private Integer status;
	
	private CpPicGroup cpPicGroup;

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

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public Integer getSginShow() {
		return sginShow;
	}

	public void setSginShow(Integer sginShow) {
		this.sginShow = sginShow;
	}

	public CpPicGroup getCpPicGroup() {
		return cpPicGroup;
	}

	public void setCpPicGroup(CpPicGroup cpPicGroup) {
		this.cpPicGroup = cpPicGroup;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
