package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.List;

public class CpLanmuPicture {
	private Integer id;

	private Integer lanmuId;

	private Integer potision;

	private Integer groupId;
	
	private CpLanmuGroupPic cpLanmuGroupPic;
	
	private List<CpLanmuGroupPic> moreLanmuGroupPic = new ArrayList<CpLanmuGroupPic>();
	
	private List<CpLanmuGroupPic> cpLanmuGroupPics = new ArrayList<CpLanmuGroupPic>();
	

	public List<CpLanmuGroupPic> getMoreLanmuGroupPic() {
		return moreLanmuGroupPic;
	}

	public void setMoreLanmuGroupPic(List<CpLanmuGroupPic> moreLanmuGroupPic) {
		this.moreLanmuGroupPic = moreLanmuGroupPic;
	}

	public CpLanmuGroupPic getCpLanmuGroupPic() {
		return cpLanmuGroupPic;
	}

	public void setCpLanmuGroupPic(CpLanmuGroupPic cpLanmuGroupPic) {
		this.cpLanmuGroupPic = cpLanmuGroupPic;
	}

	public List<CpLanmuGroupPic> getCpLanmuGroupPics() {
		return cpLanmuGroupPics;
	}

	public void setCpLanmuGroupPics(List<CpLanmuGroupPic> cpLanmuGroupPics) {
		this.cpLanmuGroupPics = cpLanmuGroupPics;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLanmuId() {
		return lanmuId;
	}

	public void setLanmuId(Integer lanmuId) {
		this.lanmuId = lanmuId;
	}

	public Integer getPotision() {
		return potision;
	}

	public void setPotision(Integer potision) {
		this.potision = potision;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}