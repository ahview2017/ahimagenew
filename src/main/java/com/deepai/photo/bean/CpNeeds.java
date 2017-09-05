package com.deepai.photo.bean;

import java.util.Date;

public class CpNeeds {
    private Integer id;

    private Integer count;

    private Date ptime;

    private Date ftime;

    private String username;

    private Date userendtime;

    private Date userstartime;

    private Double price;

    private String descs;

    private String kanzaimeijian;

    private String auditdesc;

    private String pictureuse;

    private Integer status;

    private String userDesc;
    
    private Integer langType;

    public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	private Integer siteId;
    private long Day;
    private String Datetime;
    private String Starttime;
    private String Endtime;
    private String Falsetime;

    public long getDay() {
		return Day;
	}

	public void setDay(long day) {
		Day = day;
	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public String getStarttime() {
		return Starttime;
	}

	public void setStarttime(String starttime) {
		Starttime = starttime;
	}

	public String getEndtime() {
		return Endtime;
	}

	public void setEndtime(String endtime) {
		Endtime = endtime;
	}

	public String getFalsetime() {
		return Falsetime;
	}

	public void setFalsetime(String falsetime) {
		Falsetime = falsetime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public Date getFtime() {
        return ftime;
    }

    public void setFtime(Date ftime) {
        this.ftime = ftime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getUserendtime() {
        return userendtime;
    }

    public void setUserendtime(Date userendtime) {
        this.userendtime = userendtime;
    }

    public Date getUserstartime() {
        return userstartime;
    }

    public void setUserstartime(Date userstartime) {
        this.userstartime = userstartime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs == null ? null : descs.trim();
    }

    public String getKanzaimeijian() {
        return kanzaimeijian;
    }

    public void setKanzaimeijian(String kanzaimeijian) {
        this.kanzaimeijian = kanzaimeijian == null ? null : kanzaimeijian.trim();
    }

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc == null ? null : auditdesc.trim();
    }

    public String getPictureuse() {
        return pictureuse;
    }

    public void setPictureuse(String pictureuse) {
        this.pictureuse = pictureuse == null ? null : pictureuse.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc == null ? null : userDesc.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}