package com.deepai.photo.bean;

import java.util.Date;

public class CpSensitiveWord {
    private Integer id;

    private String wordContent;

    private String memo;

    private Byte wordStatus;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Integer siteId;

    private Integer unmOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWordContent() {
        return wordContent;
    }

    public void setWordContent(String wordContent) {
        this.wordContent = wordContent == null ? null : wordContent.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Byte getWordStatus() {
        return wordStatus;
    }

    public void setWordStatus(Byte wordStatus) {
        this.wordStatus = wordStatus;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getUnmOrder() {
        return unmOrder;
    }

    public void setUnmOrder(Integer unmOrder) {
        this.unmOrder = unmOrder;
    }
}