package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CpUser {
    private Integer id;

    private String userName;

    private String password;

    private String tureName;

    private String address;

    private String zipcode;

    private String emailBind;

    private String emailContact;

    private String telBind;

    private String telContact;

    private Integer uploadCount;

    private Integer pubCount;

    private Integer keepCount;

    private Date applyDate;

    private Date regDate;

    private Integer downloadLevel;

    private Byte isDownload;

    private Integer downloadType;

    private Integer contractPerprice;

    private Integer contractBasePerprice;

    private Date contractStartTime;

    private Date contractEndTime;

    private Integer contractLimitType;

    private Integer contractLimitNum;

    private Integer contractLimitDlNum;

    private Integer contractBuyNum;

    private Integer contractNum;

    private Integer contractAllNum;

    private BigDecimal account;

    private Integer balancePerprice;

    private Integer balanceBasePerprice;

    private Integer balanceSale;

    private Integer balanceRevenue;

    private Integer balanceLimitType;

    private Integer balanceLimitNum;

    private Integer balanceLimitDlNum;

    private Integer balanceAllNum;

    private Integer threeLimitType;

    private Integer threeLimitNum;

    private Integer threeLimitDlNum;

    private Integer downLineNum;

    private Integer onLineNum;

    private Integer allDownloadNum;

    private String unitName;

    private String unitAddress;

    private String unitTel;

    private String unitFax;

    private String memo;

    private String authorName;

    private Byte subscriberType;

    private Byte isPublish;

    private Byte feeType;

    private String bankAccount;

    private String bankUsername;

    private String bankName;

    private String bankIdCard;

    private String mailAddress;

    private String mailUsername;

    private String mailIdCard;

    private String mailZipCode;

    private Integer discount;

    private String photographyDirection;

    private String zone;

    private Byte subscriptionType;

    private String province;

    private String city;

    private String idCard;

    private Byte photographerLevel;

    private Integer logincount;

    private Date loginTime;

    private Date lastLoginTime;

    private Integer userOrder;

    private Date rockTime;

    private Integer loginNo;

    private Integer userStatus;

    private String question;

    private String answer;

    private Byte isUnit;

    private Byte payType;

    private Byte smsduty;

    private String plainPassword;

    private Integer siteId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String rolenames;

    private Integer userType;
    private Integer contractNowlimitNum;

    private Integer userDivide;

    private String standby1;

    private String standby2;

    private String standby3;

    private String standby4;

    private String standby5;
    
    private Integer langType;
    
    private String userDetail;
    
    private int homepageColumnId;
    
    private int userClass;
    
    
    public int getUserClass() {
		return userClass;
	}

	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}

	public String getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(String userDetail) {
		this.userDetail = userDetail;
	}

	public int getHomepageColumnId() {
		return homepageColumnId;
	}

	public void setHomepageColumnId(int homepageColumnId) {
		this.homepageColumnId = homepageColumnId;
	}

	public Integer getLangType() {
		return langType;
	}

	public void setLangType(Integer langType) {
		this.langType = langType;
	}

	public Integer getContractNowlimitNum() {
		return contractNowlimitNum;
	}

	public void setContractNowlimitNum(Integer contractNowlimitNum) {
		this.contractNowlimitNum = contractNowlimitNum;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTureName() {
        return tureName;
    }

    public void setTureName(String tureName) {
        this.tureName = tureName == null ? null : tureName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getEmailBind() {
        return emailBind;
    }

    public void setEmailBind(String emailBind) {
        this.emailBind = emailBind == null ? null : emailBind.trim();
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact == null ? null : emailContact.trim();
    }

    public String getTelBind() {
        return telBind;
    }

    public void setTelBind(String telBind) {
        this.telBind = telBind == null ? null : telBind.trim();
    }

    public String getTelContact() {
        return telContact;
    }

    public void setTelContact(String telContact) {
        this.telContact = telContact == null ? null : telContact.trim();
    }

    public Integer getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(Integer uploadCount) {
        this.uploadCount = uploadCount;
    }

    public Integer getPubCount() {
        return pubCount;
    }

    public void setPubCount(Integer pubCount) {
        this.pubCount = pubCount;
    }

    public Integer getKeepCount() {
        return keepCount;
    }

    public void setKeepCount(Integer keepCount) {
        this.keepCount = keepCount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getDownloadLevel() {
        return downloadLevel;
    }

    public void setDownloadLevel(Integer downloadLevel) {
        this.downloadLevel = downloadLevel;
    }

    public Byte getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(Byte isDownload) {
        this.isDownload = isDownload;
    }

    public Integer getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(Integer downloadType) {
        this.downloadType = downloadType;
    }

    public Integer getContractPerprice() {
        return contractPerprice;
    }

    public void setContractPerprice(Integer contractPerprice) {
        this.contractPerprice = contractPerprice;
    }

    public Integer getContractBasePerprice() {
        return contractBasePerprice;
    }

    public void setContractBasePerprice(Integer contractBasePerprice) {
        this.contractBasePerprice = contractBasePerprice;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Integer getContractLimitType() {
        return contractLimitType;
    }

    public void setContractLimitType(Integer contractLimitType) {
        this.contractLimitType = contractLimitType;
    }

    public Integer getContractLimitNum() {
        return contractLimitNum;
    }

    public void setContractLimitNum(Integer contractLimitNum) {
        this.contractLimitNum = contractLimitNum;
    }

    public Integer getContractLimitDlNum() {
        return contractLimitDlNum;
    }

    public void setContractLimitDlNum(Integer contractLimitDlNum) {
        this.contractLimitDlNum = contractLimitDlNum;
    }

    public Integer getContractBuyNum() {
        return contractBuyNum;
    }

    public void setContractBuyNum(Integer contractBuyNum) {
        this.contractBuyNum = contractBuyNum;
    }

    public Integer getContractNum() {
        return contractNum;
    }

    public void setContractNum(Integer contractNum) {
        this.contractNum = contractNum;
    }

    public Integer getContractAllNum() {
        return contractAllNum;
    }

    public void setContractAllNum(Integer contractAllNum) {
        this.contractAllNum = contractAllNum;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getBalancePerprice() {
        return balancePerprice;
    }

    public void setBalancePerprice(Integer balancePerprice) {
        this.balancePerprice = balancePerprice;
    }

    public Integer getBalanceBasePerprice() {
        return balanceBasePerprice;
    }

    public void setBalanceBasePerprice(Integer balanceBasePerprice) {
        this.balanceBasePerprice = balanceBasePerprice;
    }

    public Integer getBalanceSale() {
        return balanceSale;
    }

    public void setBalanceSale(Integer balanceSale) {
        this.balanceSale = balanceSale;
    }

    public Integer getBalanceRevenue() {
        return balanceRevenue;
    }

    public void setBalanceRevenue(Integer balanceRevenue) {
        this.balanceRevenue = balanceRevenue;
    }

    public Integer getBalanceLimitType() {
        return balanceLimitType;
    }

    public void setBalanceLimitType(Integer balanceLimitType) {
        this.balanceLimitType = balanceLimitType;
    }

    public Integer getBalanceLimitNum() {
        return balanceLimitNum;
    }

    public void setBalanceLimitNum(Integer balanceLimitNum) {
        this.balanceLimitNum = balanceLimitNum;
    }

    public Integer getBalanceLimitDlNum() {
        return balanceLimitDlNum;
    }

    public void setBalanceLimitDlNum(Integer balanceLimitDlNum) {
        this.balanceLimitDlNum = balanceLimitDlNum;
    }

    public Integer getBalanceAllNum() {
        return balanceAllNum;
    }

    public void setBalanceAllNum(Integer balanceAllNum) {
        this.balanceAllNum = balanceAllNum;
    }

    public Integer getThreeLimitType() {
        return threeLimitType;
    }

    public void setThreeLimitType(Integer threeLimitType) {
        this.threeLimitType = threeLimitType;
    }

    public Integer getThreeLimitNum() {
        return threeLimitNum;
    }

    public void setThreeLimitNum(Integer threeLimitNum) {
        this.threeLimitNum = threeLimitNum;
    }

    public Integer getThreeLimitDlNum() {
        return threeLimitDlNum;
    }

    public void setThreeLimitDlNum(Integer threeLimitDlNum) {
        this.threeLimitDlNum = threeLimitDlNum;
    }

    public Integer getDownLineNum() {
        return downLineNum;
    }

    public void setDownLineNum(Integer downLineNum) {
        this.downLineNum = downLineNum;
    }

    public Integer getOnLineNum() {
        return onLineNum;
    }

    public void setOnLineNum(Integer onLineNum) {
        this.onLineNum = onLineNum;
    }

    public Integer getAllDownloadNum() {
        return allDownloadNum;
    }

    public void setAllDownloadNum(Integer allDownloadNum) {
        this.allDownloadNum = allDownloadNum;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress == null ? null : unitAddress.trim();
    }

    public String getUnitTel() {
        return unitTel;
    }

    public void setUnitTel(String unitTel) {
        this.unitTel = unitTel == null ? null : unitTel.trim();
    }

    public String getUnitFax() {
        return unitFax;
    }

    public void setUnitFax(String unitFax) {
        this.unitFax = unitFax == null ? null : unitFax.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public Byte getSubscriberType() {
        return subscriberType;
    }

    public void setSubscriberType(Byte subscriberType) {
        this.subscriberType = subscriberType;
    }

    public Byte getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Byte isPublish) {
        this.isPublish = isPublish;
    }

    public Byte getFeeType() {
        return feeType;
    }

    public void setFeeType(Byte feeType) {
        this.feeType = feeType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBankUsername() {
        return bankUsername;
    }

    public void setBankUsername(String bankUsername) {
        this.bankUsername = bankUsername == null ? null : bankUsername.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankIdCard() {
        return bankIdCard;
    }

    public void setBankIdCard(String bankIdCard) {
        this.bankIdCard = bankIdCard == null ? null : bankIdCard.trim();
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress == null ? null : mailAddress.trim();
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public void setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername == null ? null : mailUsername.trim();
    }

    public String getMailIdCard() {
        return mailIdCard;
    }

    public void setMailIdCard(String mailIdCard) {
        this.mailIdCard = mailIdCard == null ? null : mailIdCard.trim();
    }

    public String getMailZipCode() {
        return mailZipCode;
    }

    public void setMailZipCode(String mailZipCode) {
        this.mailZipCode = mailZipCode == null ? null : mailZipCode.trim();
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPhotographyDirection() {
        return photographyDirection;
    }

    public void setPhotographyDirection(String photographyDirection) {
        this.photographyDirection = photographyDirection == null ? null : photographyDirection.trim();
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone == null ? null : zone.trim();
    }

    public Byte getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Byte subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Byte getPhotographerLevel() {
        return photographerLevel;
    }

    public void setPhotographerLevel(Byte photographerLevel) {
        this.photographerLevel = photographerLevel;
    }

    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(Integer userOrder) {
        this.userOrder = userOrder;
    }

    public Date getRockTime() {
        return rockTime;
    }

    public void setRockTime(Date rockTime) {
        this.rockTime = rockTime;
    }

    public Integer getLoginNo() {
        return loginNo;
    }

    public void setLoginNo(Integer loginNo) {
        this.loginNo = loginNo;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Byte getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(Byte isUnit) {
        this.isUnit = isUnit;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Byte getSmsduty() {
        return smsduty;
    }

    public void setSmsduty(Byte smsduty) {
        this.smsduty = smsduty;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword == null ? null : plainPassword.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
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

    public String getRolenames() {
        return rolenames;
    }

    public void setRolenames(String rolenames) {
        this.rolenames = rolenames == null ? null : rolenames.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserDivide() {
        return userDivide;
    }

    public void setUserDivide(Integer userDivide) {
        this.userDivide = userDivide;
    }

    public String getStandby1() {
        return standby1;
    }

    public void setStandby1(String standby1) {
        this.standby1 = standby1 == null ? null : standby1.trim();
    }

    public String getStandby2() {
        return standby2;
    }

    public void setStandby2(String standby2) {
        this.standby2 = standby2 == null ? null : standby2.trim();
    }

    public String getStandby3() {
        return standby3;
    }

    public void setStandby3(String standby3) {
        this.standby3 = standby3 == null ? null : standby3.trim();
    }

    public String getStandby4() {
        return standby4;
    }

    public void setStandby4(String standby4) {
        this.standby4 = standby4 == null ? null : standby4.trim();
    }

    public String getStandby5() {
        return standby5;
    }

    public void setStandby5(String standby5) {
        this.standby5 = standby5 == null ? null : standby5.trim();
    }
}