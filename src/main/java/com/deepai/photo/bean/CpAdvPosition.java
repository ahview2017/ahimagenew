package com.deepai.photo.bean;

import java.util.Date;
import java.util.List;

public class CpAdvPosition {
    private Integer id;

    private String name;

    private String url;

    private String remarks;

    private Integer display;

    private Integer orderno;

    private Date createTime;

    private String pic;

    private Integer position;

    private Integer swidth;

    private Integer sheight;

    private Integer bwidth;

    private Integer bheight;

    private Integer style;

    private String file;
    private String Datetime;

    private Integer type;

    private Integer topicId;

    private Integer picId;
    private Integer siteId;
    private Integer Yposition;
    private Integer langType;
    
   


	public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	private  List<CpTopic> cpTopicList;
    public List<CpTopic> getCpTopicList() {
		return cpTopicList;
	}

	public void setCpTopicList(List<CpTopic> cpTopicList) {
		this.cpTopicList = cpTopicList;
	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public Integer getYposition() {
		return Yposition;
	}

	public void setYposition(Integer yposition) {
		Yposition = yposition;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getSwidth() {
        return swidth;
    }

    public void setSwidth(Integer swidth) {
        this.swidth = swidth;
    }

    public Integer getSheight() {
        return sheight;
    }

    public void setSheight(Integer sheight) {
        this.sheight = sheight;
    }

    public Integer getBwidth() {
        return bwidth;
    }

    public void setBwidth(Integer bwidth) {
        this.bwidth = bwidth;
    }

    public Integer getBheight() {
        return bheight;
    }

    public void setBheight(Integer bheight) {
        this.bheight = bheight;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}