package com.deepai.photo.bean;

import java.util.Date;

public class CpOldpictureExcelList {
    private Integer id;

    private String title;

    private String oldDate;

    private String years;

    private String category;

    private String figure;

    private String place;

    private String author;

    private String editor;

    private String number;

    private String picFileName;

    private String keywords;

    private String handin;

    private Date edittime;

    private Integer excelid;

    private String fileAllpath;

    private Integer picGroupId;

    private String memo;

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
        this.title = title == null ? null : title.trim();
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate == null ? null : oldDate.trim();
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years == null ? null : years.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure == null ? null : figure.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName == null ? null : picFileName.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getHandin() {
        return handin;
    }

    public void setHandin(String handin) {
        this.handin = handin == null ? null : handin.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Integer getExcelid() {
        return excelid;
    }

    public void setExcelid(Integer excelid) {
        this.excelid = excelid;
    }

    public String getFileAllpath() {
        return fileAllpath;
    }

    public void setFileAllpath(String fileAllpath) {
        this.fileAllpath = fileAllpath == null ? null : fileAllpath.trim();
    }

    public Integer getPicGroupId() {
        return picGroupId;
    }

    public void setPicGroupId(Integer picGroupId) {
        this.picGroupId = picGroupId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	@Override
	public String toString() {
		return "CpOldpictureExcelList [id=" + id + ", title=" + title
				+ ", oldDate=" + oldDate + ", years=" + years + ", category="
				+ category + ", figure=" + figure + ", place=" + place
				+ ", author=" + author + ", editor=" + editor + ", number="
				+ number + ", picFileName=" + picFileName + ", keywords="
				+ keywords + ", handin=" + handin + ", edittime=" + edittime
				+ ", excelid=" + excelid + ", fileAllpath=" + fileAllpath
				+ ", picGroupId=" + picGroupId + ", memo=" + memo + "]";
	}
    
}