package com.deepai.photo.bean;

/**
 * * @author huqiankai: *
 * 
 */
public class CpGroupStatistical {
	private String author; // 作者
	private String roleName; // 角色名
	private Integer sendCount; // 投稿次数
	private Integer pictureCount; // 图片数量
	private Integer throughCount; // 投稿通过数量
	private Integer throughPictureCount;// 投稿通过图片数量
	private Integer siteId; //子站id
	private String startTime; // 开始时间
	private String endTime; // 结束时间
	private Integer type;// 稿件类别
	private String  place; //地域
	private String  categaryName;//分类名
	private String  downloadNum;//下载量
	private String signNewspaperNum;//签报量
	private String orderType;//排序类别
	private String creator;//用户名
	private String firstSignNum;//一审量
	private String secondSignNum;//二审量
	private String thirdSignNum;//三审量
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getFirstSignNum() {
		return firstSignNum;
	}
	public void setFirstSignNum(String firstSignNum) {
		this.firstSignNum = firstSignNum;
	}
	public String getSecondSignNum() {
		return secondSignNum;
	}
	public void setSecondSignNum(String secondSignNum) {
		this.secondSignNum = secondSignNum;
	}
	public String getThirdSignNum() {
		return thirdSignNum;
	}
	public void setThirdSignNum(String thirdSignNum) {
		this.thirdSignNum = thirdSignNum;
	}
	
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getSignNewspaperNum() {
		return signNewspaperNum;
	}
	public void setSignNewspaperNum(String signNewspaperNum) {
		this.signNewspaperNum = signNewspaperNum;
	}
	public String getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(String downloadNum) {
		this.downloadNum = downloadNum;
	}
	public String getCategaryName() {
		return categaryName;
	}
	public void setCategaryName(String categaryName) {
		this.categaryName = categaryName;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getPictureCount() {
		return pictureCount;
	}
	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
	}
	public Integer getThroughCount() {
		if (this.throughCount==null) {
			return throughCount=0;
		}
		return throughCount;
	}
	public void setThroughCount(Integer throughCount) {
		this.throughCount = throughCount;
	}
	public Integer getThroughPictureCount() {
		if (this.throughPictureCount==null) {
			return throughPictureCount=0;
		}
		return throughPictureCount;
	}
	public void setThroughPictureCount(Integer throughPictureCount) {
		this.throughPictureCount = throughPictureCount;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		if (siteId==null) {
			this.siteId =1;
		}
		this.siteId = siteId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
