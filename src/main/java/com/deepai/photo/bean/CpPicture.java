package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CpPicture {
    private Integer id;

    private Byte allowSale;

    private String auditor;

    private Integer authorId;

    private String authorName;

    private String categoryInfo;

    private String colorType;

    private Date createTime;

    private Byte deleteFlag;

    private String editor;

    private Date exDatetime;

    private String exFnumber;

    private String exIso;

    private String exModel;

    private String filename;

    private Date filmTime;

    private Byte isHistoryPicture;

    private Byte isIptc;

    private Byte isLocked;

    private Byte isReferredByProduct;

    private Byte isReferredByPublish;

    private Byte isSourcePicture;

    private String keywords;

    private String lockerName;

    private String memo;

    private Byte negativeType;

    private Byte orientation;

    private String people;

    private Integer pictureHeight;

    private Long pictureLength;

    private Integer pictureSourceId;

    private Integer pictureState;

    private Integer pictureWidth;

    private String place;

    private BigDecimal price;

    private Date productTime;

    private Date publishTime;

    private Byte securityType;

    private Integer siteId;

    private Integer sourceId;

    private Integer sourcePictureId;

    private Integer sourcePictureLength;

    private String sourcePictureName;

    private String title;

    private String uploader;

    private String exExposureTime;

    private Integer uploadProgress;

    private String goodsType;

    private BigDecimal discountPrice;

    private BigDecimal calPrice;

    private BigDecimal discountRate;

    private String forumsInfo;

    private Date forumsTime;

    private BigDecimal reducePrice;

    private Integer isCover;

    private Integer groupId;

    private Integer sortId;
    private String smallPath;
    private String wmPath;
    private String oriAllPath;
    //add by liu.jinfeng@2017年9月14日 下午7:00:20
    private boolean bIsExif;
    
    public boolean isbIsExif() {
        return bIsExif;
    }

    public void setbIsExif(boolean bIsExif) {
        this.bIsExif = bIsExif;
    }
	public String getOriAllPath() {
		return oriAllPath;
	}

	public void setOriAllPath(String oriAllPath) {
		this.oriAllPath = oriAllPath;
	}

	public String getWmPath() {
		return wmPath;
	}

	public void setWmPath(String wmPath) {
		this.wmPath = wmPath;
	}

	public String getSmallPath() {
		return smallPath;
	}

	public void setSmallPath(String smallPath) {
		this.smallPath = smallPath;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getAllowSale() {
        return allowSale;
    }

    public void setAllowSale(Byte allowSale) {
        this.allowSale = allowSale;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(String categoryInfo) {
        this.categoryInfo = categoryInfo == null ? null : categoryInfo.trim();
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType == null ? null : colorType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
    }

    public Date getExDatetime() {
        return exDatetime;
    }

    public void setExDatetime(Date exDatetime) {
        this.exDatetime = exDatetime;
    }

    public String getExFnumber() {
        return exFnumber;
    }

    public void setExFnumber(String exFnumber) {
        this.exFnumber = exFnumber == null ? null : exFnumber.trim();
    }

    public String getExIso() {
        return exIso;
    }

    public void setExIso(String exIso) {
        this.exIso = exIso == null ? null : exIso.trim();
    }

    public String getExModel() {
        return exModel;
    }

    public void setExModel(String exModel) {
        this.exModel = exModel == null ? null : exModel.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Date getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(Date filmTime) {
        this.filmTime = filmTime;
    }

    public Byte getIsHistoryPicture() {
        return isHistoryPicture;
    }

    public void setIsHistoryPicture(Byte isHistoryPicture) {
        this.isHistoryPicture = isHistoryPicture;
    }

    public Byte getIsIptc() {
        return isIptc;
    }

    public void setIsIptc(Byte isIptc) {
        this.isIptc = isIptc;
    }

    public Byte getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Byte isLocked) {
        this.isLocked = isLocked;
    }

    public Byte getIsReferredByProduct() {
        return isReferredByProduct;
    }

    public void setIsReferredByProduct(Byte isReferredByProduct) {
        this.isReferredByProduct = isReferredByProduct;
    }

    public Byte getIsReferredByPublish() {
        return isReferredByPublish;
    }

    public void setIsReferredByPublish(Byte isReferredByPublish) {
        this.isReferredByPublish = isReferredByPublish;
    }

    public Byte getIsSourcePicture() {
        return isSourcePicture;
    }

    public void setIsSourcePicture(Byte isSourcePicture) {
        this.isSourcePicture = isSourcePicture;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getLockerName() {
        return lockerName;
    }

    public void setLockerName(String lockerName) {
        this.lockerName = lockerName == null ? null : lockerName.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Byte getNegativeType() {
        return negativeType;
    }

    public void setNegativeType(Byte negativeType) {
        this.negativeType = negativeType;
    }

    public Byte getOrientation() {
        return orientation;
    }

    public void setOrientation(Byte orientation) {
        this.orientation = orientation;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people == null ? null : people.trim();
    }

    public Integer getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(Integer pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public Long getPictureLength() {
        return pictureLength;
    }

    public void setPictureLength(Long pictureLength) {
        this.pictureLength = pictureLength;
    }

    public Integer getPictureSourceId() {
        return pictureSourceId;
    }

    public void setPictureSourceId(Integer pictureSourceId) {
        this.pictureSourceId = pictureSourceId;
    }

    public Integer getPictureState() {
        return pictureState;
    }

    public void setPictureState(Integer pictureState) {
        this.pictureState = pictureState;
    }

    public Integer getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(Integer pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getProductTime() {
        return productTime;
    }

    public void setProductTime(Date productTime) {
        this.productTime = productTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Byte getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Byte securityType) {
        this.securityType = securityType;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourcePictureId() {
        return sourcePictureId;
    }

    public void setSourcePictureId(Integer sourcePictureId) {
        this.sourcePictureId = sourcePictureId;
    }

    public Integer getSourcePictureLength() {
        return sourcePictureLength;
    }

    public void setSourcePictureLength(Integer sourcePictureLength) {
        this.sourcePictureLength = sourcePictureLength;
    }

    public String getSourcePictureName() {
        return sourcePictureName;
    }

    public void setSourcePictureName(String sourcePictureName) {
        this.sourcePictureName = sourcePictureName == null ? null : sourcePictureName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader == null ? null : uploader.trim();
    }

    public String getExExposureTime() {
        return exExposureTime;
    }

    public void setExExposureTime(String exExposureTime) {
        this.exExposureTime = exExposureTime == null ? null : exExposureTime.trim();
    }

    public Integer getUploadProgress() {
        return uploadProgress;
    }

    public void setUploadProgress(Integer uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getCalPrice() {
        return calPrice;
    }

    public void setCalPrice(BigDecimal calPrice) {
        this.calPrice = calPrice;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public String getForumsInfo() {
        return forumsInfo;
    }

    public void setForumsInfo(String forumsInfo) {
        this.forumsInfo = forumsInfo == null ? null : forumsInfo.trim();
    }

    public Date getForumsTime() {
        return forumsTime;
    }

    public void setForumsTime(Date forumsTime) {
        this.forumsTime = forumsTime;
    }

    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    public Integer getIsCover() {
        return isCover;
    }

    public void setIsCover(Integer isCover) {
        this.isCover = isCover;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}