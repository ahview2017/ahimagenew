package com.deepai.photo.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CpLanmuGroupPic {
	private Date sgintime;//发布时间
	private String creator;//创建人
	private String title;//稿件标题
	private String allpath;//稿件首图路径
	private String picname;//稿件首图路径
	private int pictureheight;//图片高度
	private int picturewidth; //图片宽度
	private int picid; //图片ID
	private int picturelength;//图片大小
	private double picturePrice;//图片大小
	private Integer groupId;
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public double getPicturePrice() {
		return picturePrice;
	}

	public void setPicturePrice(double picturePrice) {
		this.picturePrice = picturePrice;
	}

	public int getPictureheight() {
		return pictureheight;
	}

	public void setPictureheight(int pictureheight) {
		this.pictureheight = pictureheight;
	}

	public int getPicturewidth() {
		return picturewidth;
	}

	public void setPicturewidth(int picturewidth) {
		this.picturewidth = picturewidth;
	}

	public int getPicid() {
		return picid;
	}

	public void setPicid(int picid) {
		this.picid = picid;
	}

	public int getPicturelength() {
		return picturelength;
	}

	public void setPicturelength(int picturelength) {
		this.picturelength = picturelength;
	}

	public String getSgintime() {
		if (sgintime!=null) {
		String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.sgintime);
		return datetime;
		}
		return "";
	}

	public void setSgintime(Date sgintime) {
		this.sgintime = sgintime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAllpath() {
		return allpath;
	}

	public void setAllpath(String allpath) {
		this.allpath = allpath;
	}

}
