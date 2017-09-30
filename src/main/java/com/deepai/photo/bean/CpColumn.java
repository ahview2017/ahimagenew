package com.deepai.photo.bean;

import java.util.List;

public class CpColumn {
	private Integer id;
	private String name;
	private Integer pid;
	private String pname;
	private Integer recommendId;
	private Integer orderId;
	private Integer position;
	private Integer state;
	private Integer signPosition;
	private List<CpPicGroup> cpgList;
	private List<CpColumn> columnList;
	private List<CpColumn> children;
	private Integer langType;
	private Integer type;
	
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLangType() {
		return langType;
	}
	public void setLangType(Integer langType) {
		this.langType = langType;
	}
	public Integer getSignPosition() {
		return signPosition;
	}
	public void setSignPosition(Integer signPosition) {
		this.signPosition = signPosition;
	}
	public List<CpColumn> getChildren() {
		return children;
	}
	public void setChildren(List<CpColumn> children) {
		this.children = children;
	}
	public List<CpColumn> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<CpColumn> columnList) {
		this.columnList = columnList;
	}
	public List<CpPicGroup> getCpgList() {
		return cpgList;
	}
	public void setCpgList(List<CpPicGroup> cpgList) {
		this.cpgList = cpgList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
