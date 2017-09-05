package com.deepai.photo.bean;

import java.util.Date;

public class CpLog {
    private Integer id;

    private Integer logtypeId;

    private Integer siteId;

    private String opeIp;

    private Integer opeUserId;

    private String opeUser;

    private Date opeTime;

    private String opeContent;

    private String opeParam;

    private String opeIds;

    private String opeResult;

    private Byte opeType;

    private String opeMemo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogtypeId() {
        return logtypeId;
    }

    public void setLogtypeId(Integer logtypeId) {
        this.logtypeId = logtypeId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getOpeIp() {
        return opeIp;
    }

    public void setOpeIp(String opeIp) {
        this.opeIp = opeIp == null ? null : opeIp.trim();
    }

    public Integer getOpeUserId() {
        return opeUserId;
    }

    public void setOpeUserId(Integer opeUserId) {
        this.opeUserId = opeUserId;
    }

    public String getOpeUser() {
        return opeUser;
    }

    public void setOpeUser(String opeUser) {
        this.opeUser = opeUser == null ? null : opeUser.trim();
    }

    public Date getOpeTime() {
        return opeTime;
    }

    public void setOpeTime(Date opeTime) {
        this.opeTime = opeTime;
    }

    public String getOpeContent() {
        return opeContent;
    }

    public void setOpeContent(String opeContent) {
        this.opeContent = opeContent == null ? null : opeContent.trim();
    }

    public String getOpeParam() {
        return opeParam;
    }

    public void setOpeParam(String opeParam) {
        this.opeParam = opeParam == null ? null : opeParam.trim();
    }

    public String getOpeIds() {
        return opeIds;
    }

    public void setOpeIds(String opeIds) {
        this.opeIds = opeIds == null ? null : opeIds.trim();
    }

    public String getOpeResult() {
        return opeResult;
    }

    public void setOpeResult(String opeResult) {
        this.opeResult = opeResult == null ? null : opeResult.trim();
    }

    public Byte getOpeType() {
        return opeType;
    }

    public void setOpeType(Byte opeType) {
        this.opeType = opeType;
    }

    public String getOpeMemo() {
        return opeMemo;
    }

    public void setOpeMemo(String opeMemo) {
        this.opeMemo = opeMemo == null ? null : opeMemo.trim();
    }
}