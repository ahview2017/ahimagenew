package com.deepai.photo.bean;

public class CpFlActInfo {
    private Integer id;

    private String actName;

    private String actLink;

    private Integer actStatus;

    private Integer actSort;

    private String actMemo;

    private Integer oactId;

    private Byte langType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    public String getActLink() {
        return actLink;
    }

    public void setActLink(String actLink) {
        this.actLink = actLink == null ? null : actLink.trim();
    }

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

    public Integer getActSort() {
        return actSort;
    }

    public void setActSort(Integer actSort) {
        this.actSort = actSort;
    }

    public String getActMemo() {
        return actMemo;
    }

    public void setActMemo(String actMemo) {
        this.actMemo = actMemo == null ? null : actMemo.trim();
    }

    public Integer getOactId() {
        return oactId;
    }

    public void setOactId(Integer oactId) {
        this.oactId = oactId;
    }

    public Byte getLangType() {
        return langType;
    }

    public void setLangType(Byte langType) {
        this.langType = langType;
    }
}