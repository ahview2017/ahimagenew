package com.deepai.photo.bean;

import java.util.Date;

public class CpLogType {
    private Integer id;

    private String logCode;

    private Date createTime;

    private String createUser;

    private String logtypeName;

    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode == null ? null : logCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getLogtypeName() {
        return logtypeName;
    }

    public void setLogtypeName(String logtypeName) {
        this.logtypeName = logtypeName == null ? null : logtypeName.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}