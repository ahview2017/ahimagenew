package com.deepai.photo.bean;

public class CpPicAllpath {
    private Integer id;

    private Integer picType;

    private Integer tragetId;

    private String allPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public Integer getTragetId() {
        return tragetId;
    }

    public void setTragetId(Integer tragetId) {
        this.tragetId = tragetId;
    }

    public String getAllPath() {
        return allPath;
    }

    public void setAllPath(String allPath) {
        this.allPath = allPath == null ? null : allPath.trim();
    }
}