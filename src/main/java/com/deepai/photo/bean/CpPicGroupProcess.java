package com.deepai.photo.bean;

import java.util.Date;

public class CpPicGroupProcess {
    private Integer id;

    private Integer flowType;

    private Integer picgroupId;

    private Integer operateUserId;

    private String operateUsername;

    private Date operateTime;

    private String operateMemo;

    private Integer proofreadId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public Integer getPicgroupId() {
        return picgroupId;
    }

    public void setPicgroupId(Integer picgroupId) {
        this.picgroupId = picgroupId;
    }

    public Integer getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Integer operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername == null ? null : operateUsername.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateMemo() {
        return operateMemo;
    }

    public void setOperateMemo(String operateMemo) {
        this.operateMemo = operateMemo == null ? null : operateMemo.trim();
    }

    public Integer getProofreadId() {
        return proofreadId;
    }

    public void setProofreadId(Integer proofreadId) {
        this.proofreadId = proofreadId;
    }
}