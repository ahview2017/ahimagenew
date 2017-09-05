package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CpLanmu {
	private Integer id;

	private Integer topicId;

	private Integer lanmuNo;

	private Integer typeId;

	private Integer zhanshuCount;

	private String name;

	private Integer pid;
	private String url;
	private String subAdds;
	private String keyWords;
	private String words;
	private Integer showWay;
	private String topicName;
	private CpLanmu cpLanmu1;
	private CpLanmu cpLanmu2;
	private List<CpLanmu> cpLanmuList = new ArrayList<CpLanmu>();
	private List<CpLanmuPicture> cpLanmuPictures = new ArrayList<CpLanmuPicture>();
	private List<CpLanmuPicture> moreLanmuPictures = new ArrayList<CpLanmuPicture>();
	private String pic;

	private List<CpNotice> cpNoticesList = new ArrayList<CpNotice>();

	
	public List<CpLanmuPicture> getMoreLanmuPictures() {
		return moreLanmuPictures;
	}

	public void setMoreLanmuPictures(List<CpLanmuPicture> moreLanmuPictures) {
		this.moreLanmuPictures = moreLanmuPictures;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Integer getShowWay() {
		return showWay;
	}

	public void setShowWay(Integer showWay) {
		this.showWay = showWay;
	}

	public List<CpNotice> getCpNoticesList() {
		return cpNoticesList;
	}

	public void setCpNoticesList(List<CpNotice> cpNoticesList) {
		this.cpNoticesList = cpNoticesList;
	}

	public List<CpLanmu> getCpLanmuList() {
		return cpLanmuList;
	}

	public void setCpLanmuList(List<CpLanmu> cpLanmuList) {
		this.cpLanmuList = cpLanmuList;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public CpLanmu getCpLanmu1() {
		return cpLanmu1;
	}

	public void setCpLanmu1(CpLanmu cpLanmu1) {
		this.cpLanmu1 = cpLanmu1;
	}

	public CpLanmu getCpLanmu2() {
		return cpLanmu2;
	}

	public void setCpLanmu2(CpLanmu cpLanmu2) {
		this.cpLanmu2 = cpLanmu2;
	}

	public List<CpLanmuPicture> getCpLanmuPictures() {
		return cpLanmuPictures;
	}

	public void setCpLanmuPictures(List<CpLanmuPicture> cpLanmuPictures) {
		this.cpLanmuPictures = cpLanmuPictures;
	}

	

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getLanmuNo() {
		return lanmuNo;
	}

	public void setLanmuNo(Integer lanmuNo) {
		this.lanmuNo = lanmuNo;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getZhanshuCount() {
		return zhanshuCount;
	}

	public void setZhanshuCount(Integer zhanshuCount) {
		this.zhanshuCount = zhanshuCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getSubAdds() {
		return subAdds;
	}

	public void setSubAdds(String subAdds) {
		this.subAdds = subAdds == null ? null : subAdds.trim();
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words == null ? null : words.trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CpLanmu other = (CpLanmu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}