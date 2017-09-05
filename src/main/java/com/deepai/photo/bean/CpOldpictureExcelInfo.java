package com.deepai.photo.bean;

import java.util.Date;

public class CpOldpictureExcelInfo {
    private Integer id;

    private String fileName;

    private Date uploadDate;

    private Integer uploadUserid;

    private String filePath;

    private Integer status;

    private Integer picUploadNum;

    private Integer storageNum;

    private Integer recordTotal;

    private String fileAllpath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getUploadUserid() {
        return uploadUserid;
    }

    public void setUploadUserid(Integer uploadUserid) {
        this.uploadUserid = uploadUserid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPicUploadNum() {
        return picUploadNum;
    }

    public void setPicUploadNum(Integer picUploadNum) {
        this.picUploadNum = picUploadNum;
    }

    public Integer getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(Integer storageNum) {
        this.storageNum = storageNum;
    }

    public Integer getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(Integer recordTotal) {
        this.recordTotal = recordTotal;
    }

    public String getFileAllpath() {
        return fileAllpath;
    }

    public void setFileAllpath(String fileAllpath) {
        this.fileAllpath = fileAllpath == null ? null : fileAllpath.trim();
    }
}