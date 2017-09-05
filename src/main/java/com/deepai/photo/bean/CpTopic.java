package com.deepai.photo.bean;

import java.util.Date;
import java.util.List;

public class CpTopic {
    private Integer id;

    private String name;

    private Integer type;

    private String emage;

    private Integer qianfa;

    private Integer display;

    private String remarks;

    private Date createTime;

    private String url;
    private String Datetime;
    private String clentIp;
    private Integer langType;
    private Integer siteId;
    private Integer pid;
    private String createUser;
    private Integer createUserId;
    private List<CpLanmu> children;
    
    public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public List<CpLanmu> getChildren() {
		return children;
	}

	public void setChildren(List<CpLanmu> children) {
		this.children = children;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getClentIp() {
		return clentIp;
	}

	public void setClentIp(String clentIp) {
		this.clentIp = clentIp;
	}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmage() {
        return emage;
    }

    public void setEmage(String emage) {
        this.emage = emage == null ? null : emage.trim();
    }

    public Integer getQianfa() {
        return qianfa;
    }

    public void setQianfa(Integer qianfa) {
        this.qianfa = qianfa;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}