package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpPicturePriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpPicturePriceExample() {
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

        public Criteria andPictureLittleTypeIsNull() {
            addCriterion("PICTURE_LITTLE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeIsNotNull() {
            addCriterion("PICTURE_LITTLE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeEqualTo(String value) {
            addCriterion("PICTURE_LITTLE_TYPE =", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeNotEqualTo(String value) {
            addCriterion("PICTURE_LITTLE_TYPE <>", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeGreaterThan(String value) {
            addCriterion("PICTURE_LITTLE_TYPE >", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PICTURE_LITTLE_TYPE >=", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeLessThan(String value) {
            addCriterion("PICTURE_LITTLE_TYPE <", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeLessThanOrEqualTo(String value) {
            addCriterion("PICTURE_LITTLE_TYPE <=", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeLike(String value) {
            addCriterion("PICTURE_LITTLE_TYPE like", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeNotLike(String value) {
            addCriterion("PICTURE_LITTLE_TYPE not like", value, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeIn(List<String> values) {
            addCriterion("PICTURE_LITTLE_TYPE in", values, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeNotIn(List<String> values) {
            addCriterion("PICTURE_LITTLE_TYPE not in", values, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeBetween(String value1, String value2) {
            addCriterion("PICTURE_LITTLE_TYPE between", value1, value2, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andPictureLittleTypeNotBetween(String value1, String value2) {
            addCriterion("PICTURE_LITTLE_TYPE not between", value1, value2, "pictureLittleType");
            return (Criteria) this;
        }

        public Criteria andSetPriceIsNull() {
            addCriterion("SET_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andSetPriceIsNotNull() {
            addCriterion("SET_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andSetPriceEqualTo(Double value) {
            addCriterion("SET_PRICE =", value, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceNotEqualTo(Double value) {
            addCriterion("SET_PRICE <>", value, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceGreaterThan(Double value) {
            addCriterion("SET_PRICE >", value, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("SET_PRICE >=", value, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceLessThan(Double value) {
            addCriterion("SET_PRICE <", value, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceLessThanOrEqualTo(Double value) {
            addCriterion("SET_PRICE <=", value, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceIn(List<Double> values) {
            addCriterion("SET_PRICE in", values, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceNotIn(List<Double> values) {
            addCriterion("SET_PRICE not in", values, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceBetween(Double value1, Double value2) {
            addCriterion("SET_PRICE between", value1, value2, "setPrice");
            return (Criteria) this;
        }

        public Criteria andSetPriceNotBetween(Double value1, Double value2) {
            addCriterion("SET_PRICE not between", value1, value2, "setPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNull() {
            addCriterion("PAY_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNotNull() {
            addCriterion("PAY_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPayPriceEqualTo(Double value) {
            addCriterion("PAY_PRICE =", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotEqualTo(Double value) {
            addCriterion("PAY_PRICE <>", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThan(Double value) {
            addCriterion("PAY_PRICE >", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("PAY_PRICE >=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThan(Double value) {
            addCriterion("PAY_PRICE <", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThanOrEqualTo(Double value) {
            addCriterion("PAY_PRICE <=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceIn(List<Double> values) {
            addCriterion("PAY_PRICE in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotIn(List<Double> values) {
            addCriterion("PAY_PRICE not in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceBetween(Double value1, Double value2) {
            addCriterion("PAY_PRICE between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotBetween(Double value1, Double value2) {
            addCriterion("PAY_PRICE not between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andEditNameIsNull() {
            addCriterion("EDIT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEditNameIsNotNull() {
            addCriterion("EDIT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEditNameEqualTo(String value) {
            addCriterion("EDIT_NAME =", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameNotEqualTo(String value) {
            addCriterion("EDIT_NAME <>", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameGreaterThan(String value) {
            addCriterion("EDIT_NAME >", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameGreaterThanOrEqualTo(String value) {
            addCriterion("EDIT_NAME >=", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameLessThan(String value) {
            addCriterion("EDIT_NAME <", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameLessThanOrEqualTo(String value) {
            addCriterion("EDIT_NAME <=", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameLike(String value) {
            addCriterion("EDIT_NAME like", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameNotLike(String value) {
            addCriterion("EDIT_NAME not like", value, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameIn(List<String> values) {
            addCriterion("EDIT_NAME in", values, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameNotIn(List<String> values) {
            addCriterion("EDIT_NAME not in", values, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameBetween(String value1, String value2) {
            addCriterion("EDIT_NAME between", value1, value2, "editName");
            return (Criteria) this;
        }

        public Criteria andEditNameNotBetween(String value1, String value2) {
            addCriterion("EDIT_NAME not between", value1, value2, "editName");
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