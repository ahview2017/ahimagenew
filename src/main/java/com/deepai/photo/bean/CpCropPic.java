package com.deepai.photo.bean;

/**
 * 图片裁剪
 * @author xiayunan
 * @date   2018年4月3日
 *
 */
public class CpCropPic {
	private String x1;//起始x轴坐标
	private String x2;//截止x轴坐标
	private String y1;//起始y轴坐标
	private String y2;//截止y轴坐标
	private String width;//裁剪过的图片宽度
	private String height;//裁剪过的图片高度
	private String oriPicPath;//原始图片尺寸
	private String fileName;//原始图片名称
	private Integer oriPicId;//原始图片ID
	public String getFileName() {
		return fileName;
	}
	public Integer getOriPicId() {
		return oriPicId;
	}
	public void setOriPicId(Integer oriPicId) {
		this.oriPicId = oriPicId;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriPicPath() {
		return oriPicPath;
	}
	public void setOriPicPath(String oriPicPath) {
		this.oriPicPath = oriPicPath;
	}
	public String getX1() {
		return x1;
	}
	public void setX1(String x1) {
		this.x1 = x1;
	}
	public String getX2() {
		return x2;
	}
	public void setX2(String x2) {
		this.x2 = x2;
	}
	public String getY1() {
		return y1;
	}
	public void setY1(String y1) {
		this.y1 = y1;
	}
	public String getY2() {
		return y2;
	}
	public void setY2(String y2) {
		this.y2 = y2;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
	
	
	
	
	
	
}
