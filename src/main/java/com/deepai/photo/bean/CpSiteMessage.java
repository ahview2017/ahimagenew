package com.deepai.photo.bean;

import java.util.Date;

public class CpSiteMessage {
    private Integer id;

    private String smContent;

    private String smLink;
    private String Datetime;

    private Integer siteId;

    private Date createTime;

    public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmContent() {
        return smContent;
    }

    public void setSmContent(String smContent) {
        this.smContent = smContent == null ? null : smContent.trim();
    }

    public String getSmLink() {
        return smLink;
    }

    public void setSmLink(String smLink) {
        this.smLink = smLink == null ? null : smLink.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}