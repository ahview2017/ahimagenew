package com.deepai.photo.bean;

import java.util.Date;

public class CpPhoneMsg {
    private Integer id;

    private String content;

    private String sender;

    private Date createTime;

    private Integer status;

    private String phoneReciver;

    private String phoneReciverRole;
    private String Datetime;

    public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	private Integer siteId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhoneReciver() {
        return phoneReciver;
    }

    public void setPhoneReciver(String phoneReciver) {
        this.phoneReciver = phoneReciver == null ? null : phoneReciver.trim();
    }

    public String getPhoneReciverRole() {
        return phoneReciverRole;
    }

    public void setPhoneReciverRole(String phoneReciverRole) {
        this.phoneReciverRole = phoneReciverRole == null ? null : phoneReciverRole.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}