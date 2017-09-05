package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpPictureDownloadrecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpPictureDownloadrecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeIsNull() {
            addCriterion("DOWNLOAD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeIsNotNull() {
            addCriterion("DOWNLOAD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeEqualTo(Date value) {
            addCriterion("DOWNLOAD_TIME =", value, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeNotEqualTo(Date value) {
            addCriterion("DOWNLOAD_TIME <>", value, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeGreaterThan(Date value) {
            addCriterion("DOWNLOAD_TIME >", value, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DOWNLOAD_TIME >=", value, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeLessThan(Date value) {
            addCriterion("DOWNLOAD_TIME <", value, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeLessThanOrEqualTo(Date value) {
            addCriterion("DOWNLOAD_TIME <=", value, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeIn(List<Date> values) {
            addCriterion("DOWNLOAD_TIME in", values, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeNotIn(List<Date> values) {
            addCriterion("DOWNLOAD_TIME not in", values, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeBetween(Date value1, Date value2) {
            addCriterion("DOWNLOAD_TIME between", value1, value2, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andDownloadTimeNotBetween(Date value1, Date value2) {
            addCriterion("DOWNLOAD_TIME not between", value1, value2, "downloadTime");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdIsNull() {
            addCriterion("\"PICTURE_GROUP _ID\" is null");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdIsNotNull() {
            addCriterion("\"PICTURE_GROUP _ID\" is not null");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdEqualTo(Integer value) {
            addCriterion("\"PICTURE_GROUP _ID\" =", value, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdNotEqualTo(Integer value) {
            addCriterion("\"PICTURE_GROUP _ID\" <>", value, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdGreaterThan(Integer value) {
            addCriterion("\"PICTURE_GROUP _ID\" >", value, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("\"PICTURE_GROUP _ID\" >=", value, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdLessThan(Integer value) {
            addCriterion("\"PICTURE_GROUP _ID\" <", value, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("\"PICTURE_GROUP _ID\" <=", value, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdIn(List<Integer> values) {
            addCriterion("\"PICTURE_GROUP _ID\" in", values, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdNotIn(List<Integer> values) {
            addCriterion("\"PICTURE_GROUP _ID\" not in", values, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("\"PICTURE_GROUP _ID\" between", value1, value2, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("\"PICTURE_GROUP _ID\" not between", value1, value2, "pictureGroupId");
            return (Criteria) this;
        }

        public Criteria andPictureIdIsNull() {
            addCriterion("PICTURE_ID is null");
            return (Criteria) this;
        }

        public Criteria andPictureIdIsNotNull() {
            addCriterion("PICTURE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPictureIdEqualTo(Integer value) {
            addCriterion("PICTURE_ID =", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotEqualTo(Integer value) {
            addCriterion("PICTURE_ID <>", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdGreaterThan(Integer value) {
            addCriterion("PICTURE_ID >", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_ID >=", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdLessThan(Integer value) {
            addCriterion("PICTURE_ID <", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_ID <=", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdIn(List<Integer> values) {
            addCriterion("PICTURE_ID in", values, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotIn(List<Integer> values) {
            addCriterion("PICTURE_ID not in", values, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_ID between", value1, value2, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_ID not between", value1, value2, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureSizeIsNull() {
            addCriterion("PICTURE_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andPictureSizeIsNotNull() {
            addCriterion("PICTURE_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andPictureSizeEqualTo(String value) {
            addCriterion("PICTURE_SIZE =", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeNotEqualTo(String value) {
            addCriterion("PICTURE_SIZE <>", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeGreaterThan(String value) {
            addCriterion("PICTURE_SIZE >", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeGreaterThanOrEqualTo(String value) {
            addCriterion("PICTURE_SIZE >=", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeLessThan(String value) {
            addCriterion("PICTURE_SIZE <", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeLessThanOrEqualTo(String value) {
            addCriterion("PICTURE_SIZE <=", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeLike(String value) {
            addCriterion("PICTURE_SIZE like", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeNotLike(String value) {
            addCriterion("PICTURE_SIZE not like", value, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeIn(List<String> values) {
            addCriterion("PICTURE_SIZE in", values, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeNotIn(List<String> values) {
            addCriterion("PICTURE_SIZE not in", values, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeBetween(String value1, String value2) {
            addCriterion("PICTURE_SIZE between", value1, value2, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureSizeNotBetween(String value1, String value2) {
            addCriterion("PICTURE_SIZE not between", value1, value2, "pictureSize");
            return (Criteria) this;
        }

        public Criteria andPictureTypeIsNull() {
            addCriterion("PICTURE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPictureTypeIsNotNull() {
            addCriterion("PICTURE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPictureTypeEqualTo(Integer value) {
            addCriterion("PICTURE_TYPE =", value, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeNotEqualTo(Integer value) {
            addCriterion("PICTURE_TYPE <>", value, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeGreaterThan(Integer value) {
            addCriterion("PICTURE_TYPE >", value, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_TYPE >=", value, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeLessThan(Integer value) {
            addCriterion("PICTURE_TYPE <", value, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_TYPE <=", value, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeIn(List<Integer> values) {
            addCriterion("PICTURE_TYPE in", values, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeNotIn(List<Integer> values) {
            addCriterion("PICTURE_TYPE not in", values, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_TYPE between", value1, value2, "pictureType");
            return (Criteria) this;
        }

        public Criteria andPictureTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_TYPE not between", value1, value2, "pictureType");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("SITE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("SITE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(Integer value) {
            addCriterion("SITE_ID =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(Integer value) {
            addCriterion("SITE_ID <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(Integer value) {
            addCriterion("SITE_ID >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SITE_ID >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(Integer value) {
            addCriterion("SITE_ID <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(Integer value) {
            addCriterion("SITE_ID <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<Integer> values) {
            addCriterion("SITE_ID in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<Integer> values) {
            addCriterion("SITE_ID not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(Integer value1, Integer value2) {
            addCriterion("SITE_ID between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SITE_ID not between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andWatermarkerIsNull() {
            addCriterion("WATERMARKER is null");
            return (Criteria) this;
        }

        public Criteria andWatermarkerIsNotNull() {
            addCriterion("WATERMARKER is not null");
            return (Criteria) this;
        }

        public Criteria andWatermarkerEqualTo(Byte value) {
            addCriterion("WATERMARKER =", value, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerNotEqualTo(Byte value) {
            addCriterion("WATERMARKER <>", value, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerGreaterThan(Byte value) {
            addCriterion("WATERMARKER >", value, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerGreaterThanOrEqualTo(Byte value) {
            addCriterion("WATERMARKER >=", value, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerLessThan(Byte value) {
            addCriterion("WATERMARKER <", value, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerLessThanOrEqualTo(Byte value) {
            addCriterion("WATERMARKER <=", value, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerIn(List<Byte> values) {
            addCriterion("WATERMARKER in", values, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerNotIn(List<Byte> values) {
            addCriterion("WATERMARKER not in", values, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerBetween(Byte value1, Byte value2) {
            addCriterion("WATERMARKER between", value1, value2, "watermarker");
            return (Criteria) this;
        }

        public Criteria andWatermarkerNotBetween(Byte value1, Byte value2) {
            addCriterion("WATERMARKER not between", value1, value2, "watermarker");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("USERNAME is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("USERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("USERNAME =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("USERNAME <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("USERNAME >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("USERNAME >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("USERNAME <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("USERNAME <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("USERNAME like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("USERNAME not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("USERNAME in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("USERNAME not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("USERNAME between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("USERNAME not between", value1, value2, "username");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}