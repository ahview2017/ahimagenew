package com.deepai.photo.common.pojo;

import java.util.Date;

/**
 * trs全文检索返回的结果集
*/
public class TRSResult {
	/**
	 * id主键
	 *   */
	private String id;
	/**
	 * 标题
	 *   */
	private String title;
	/**
	 * 正文 trs中定义的正文合并了一些字段
	 *   */
	private String zw;
	/**
	 * 日期
	 *   */
	private String date;
	/**
	 * 组图说明
	 *   */
	private String memo;
	/**
	 * 关键字
	 *   */
	private String keyWords;
	/**
	 * 创建人姓名
	 *   */
	private String authorName;
	/**
	 * 检索用图片关键字合集
	 *   */
	private String pickw;
	/**
	 * 检索用图片说明合集
	 *   */
	private String picmemo;
	/**
	 * 封面图片名称合集
	 *   */
	private String coverPic;
	
	/**
	 * 组图中图片的数量
	 *   */
	private String picCount;
	/**
	 * 图片名称
	 *  */
	private String fileName;
	/**
	 * 图片分类
	 *   */
	private Integer category_id;
	/**
	 * 作者地址
	 *   */
	private String place;
	/**
	 * 人物
	 *   */
	private String people;
	/**
	 * 组图标题
	 *   */
	private String gtitle;
	private String gKEYWORDS;
	private Integer langType;
	private String  staTime;
	private String  endTime;
	private String  properties;
	private String  groupid;
	
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getStaTime() {
		return staTime;
	}
	public void setStaTime(String staTime) {
		this.staTime = staTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getgKEYWORDS() {
		return gKEYWORDS;
	}
	public void setgKEYWORDS(String gKEYWORDS) {
		this.gKEYWORDS = gKEYWORDS;
	}
	public Integer getLangType() {
		return langType;
	}
	public void setLangType(Integer langType) {
		this.langType = langType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getGtitle() {
		return gtitle;
	}
	public void setGtitle(String gtitle) {
		this.gtitle = gtitle;
	}
	public String getPicCount() {
		return picCount;
	}
	public void setPicCount(String picCount) {
		this.picCount = picCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getPickw() {
		return pickw;
	}
	public void setPickw(String pickw) {
		this.pickw = pickw;
	}
	public String getPicmemo() {
		return picmemo;
	}
	public void setPicmemo(String picmemo) {
		this.picmemo = picmemo;
	}
	public String getCoverPic() {
		return coverPic;
	}
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
}
