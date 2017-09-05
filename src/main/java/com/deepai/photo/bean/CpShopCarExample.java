package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpShopCarExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpShopCarExample() {
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

        public Criteria andPicIdIsNull() {
            addCriterion("PIC_ID is null");
            return (Criteria) this;
        }

        public Criteria andPicIdIsNotNull() {
            addCriterion("PIC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPicIdEqualTo(Integer value) {
            addCriterion("PIC_ID =", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdNotEqualTo(Integer value) {
            addCriterion("PIC_ID <>", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdGreaterThan(Integer value) {
            addCriterion("PIC_ID >", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PIC_ID >=", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdLessThan(Integer value) {
            addCriterion("PIC_ID <", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdLessThanOrEqualTo(Integer value) {
            addCriterion("PIC_ID <=", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdIn(List<Integer> values) {
            addCriterion("PIC_ID in", values, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdNotIn(List<Integer> values) {
            addCriterion("PIC_ID not in", values, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdBetween(Integer value1, Integer value2) {
            addCriterion("PIC_ID between", value1, value2, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PIC_ID not between", value1, value2, "picId");
            return (Criteria) this;
        }

        public Criteria andPicTypeIsNull() {
            addCriterion("PIC_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPicTypeIsNotNull() {
            addCriterion("PIC_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPicTypeEqualTo(Integer value) {
            addCriterion("PIC_TYPE =", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeNotEqualTo(Integer value) {
            addCriterion("PIC_TYPE <>", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeGreaterThan(Integer value) {
            addCriterion("PIC_TYPE >", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PIC_TYPE >=", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeLessThan(Integer value) {
            addCriterion("PIC_TYPE <", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PIC_TYPE <=", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeIn(List<Integer> values) {
            addCriterion("PIC_TYPE in", values, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeNotIn(List<Integer> values) {
            addCriterion("PIC_TYPE not in", values, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeBetween(Integer value1, Integer value2) {
            addCriterion("PIC_TYPE between", value1, value2, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PIC_TYPE not between", value1, value2, "picType");
            return (Criteria) this;
        }

        public Criteria andPicNameIsNull() {
            addCriterion("PIC_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPicNameIsNotNull() {
            addCriterion("PIC_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPicNameEqualTo(String value) {
            addCriterion("PIC_NAME =", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameNotEqualTo(String value) {
            addCriterion("PIC_NAME <>", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameGreaterThan(String value) {
            addCriterion("PIC_NAME >", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameGreaterThanOrEqualTo(String value) {
            addCriterion("PIC_NAME >=", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameLessThan(String value) {
            addCriterion("PIC_NAME <", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameLessThanOrEqualTo(String value) {
            addCriterion("PIC_NAME <=", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameLike(String value) {
            addCriterion("PIC_NAME like", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameNotLike(String value) {
            addCriterion("PIC_NAME not like", value, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameIn(List<String> values) {
            addCriterion("PIC_NAME in", values, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameNotIn(List<String> values) {
            addCriterion("PIC_NAME not in", values, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameBetween(String value1, String value2) {
            addCriterion("PIC_NAME between", value1, value2, "picName");
            return (Criteria) this;
        }

        public Criteria andPicNameNotBetween(String value1, String value2) {
            addCriterion("PIC_NAME not between", value1, value2, "picName");
            return (Criteria) this;
        }

        public Criteria andPicTitleIsNull() {
            addCriterion("PIC_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andPicTitleIsNotNull() {
            addCriterion("PIC_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andPicTitleEqualTo(String value) {
            addCriterion("PIC_TITLE =", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleNotEqualTo(String value) {
            addCriterion("PIC_TITLE <>", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleGreaterThan(String value) {
            addCriterion("PIC_TITLE >", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleGreaterThanOrEqualTo(String value) {
            addCriterion("PIC_TITLE >=", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleLessThan(String value) {
            addCriterion("PIC_TITLE <", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleLessThanOrEqualTo(String value) {
            addCriterion("PIC_TITLE <=", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleLike(String value) {
            addCriterion("PIC_TITLE like", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleNotLike(String value) {
            addCriterion("PIC_TITLE not like", value, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleIn(List<String> values) {
            addCriterion("PIC_TITLE in", values, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleNotIn(List<String> values) {
            addCriterion("PIC_TITLE not in", values, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleBetween(String value1, String value2) {
            addCriterion("PIC_TITLE between", value1, value2, "picTitle");
            return (Criteria) this;
        }

        public Criteria andPicTitleNotBetween(String value1, String value2) {
            addCriterion("PIC_TITLE not between", value1, value2, "picTitle");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameIsNull() {
            addCriterion("CREATE_USERNAME is null");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameIsNotNull() {
            addCriterion("CREATE_USERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameEqualTo(String value) {
            addCriterion("CREATE_USERNAME =", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotEqualTo(String value) {
            addCriterion("CREATE_USERNAME <>", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameGreaterThan(String value) {
            addCriterion("CREATE_USERNAME >", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USERNAME >=", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameLessThan(String value) {
            addCriterion("CREATE_USERNAME <", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USERNAME <=", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameLike(String value) {
            addCriterion("CREATE_USERNAME like", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotLike(String value) {
            addCriterion("CREATE_USERNAME not like", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameIn(List<String> values) {
            addCriterion("CREATE_USERNAME in", values, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotIn(List<String> values) {
            addCriterion("CREATE_USERNAME not in", values, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameBetween(String value1, String value2) {
            addCriterion("CREATE_USERNAME between", value1, value2, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotBetween(String value1, String value2) {
            addCriterion("CREATE_USERNAME not between", value1, value2, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUseridIsNull() {
            addCriterion("CREATE_USERID is null");
            return (Criteria) this;
        }

        public Criteria andCreateUseridIsNotNull() {
            addCriterion("CREATE_USERID is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUseridEqualTo(Integer value) {
            addCriterion("CREATE_USERID =", value, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridNotEqualTo(Integer value) {
            addCriterion("CREATE_USERID <>", value, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridGreaterThan(Integer value) {
            addCriterion("CREATE_USERID >", value, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("CREATE_USERID >=", value, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridLessThan(Integer value) {
            addCriterion("CREATE_USERID <", value, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridLessThanOrEqualTo(Integer value) {
            addCriterion("CREATE_USERID <=", value, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridIn(List<Integer> values) {
            addCriterion("CREATE_USERID in", values, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridNotIn(List<Integer> values) {
            addCriterion("CREATE_USERID not in", values, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridBetween(Integer value1, Integer value2) {
            addCriterion("CREATE_USERID between", value1, value2, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("CREATE_USERID not between", value1, value2, "createUserid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("GROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("GROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("GROUP_ID =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("GROUP_ID <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("GROUP_ID >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("GROUP_ID >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("GROUP_ID <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("GROUP_ID <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("GROUP_ID in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("GROUP_ID not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("GROUP_ID between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("GROUP_ID not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andWidthIsNull() {
            addCriterion("WIDTH is null");
            return (Criteria) this;
        }

        public Criteria andWidthIsNotNull() {
            addCriterion("WIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andWidthEqualTo(Integer value) {
            addCriterion("WIDTH =", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotEqualTo(Integer value) {
            addCriterion("WIDTH <>", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThan(Integer value) {
            addCriterion("WIDTH >", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("WIDTH >=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThan(Integer value) {
            addCriterion("WIDTH <", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThanOrEqualTo(Integer value) {
            addCriterion("WIDTH <=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthIn(List<Integer> values) {
            addCriterion("WIDTH in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotIn(List<Integer> values) {
            addCriterion("WIDTH not in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthBetween(Integer value1, Integer value2) {
            addCriterion("WIDTH between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("WIDTH not between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("HEIGHT is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("HEIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(String value) {
            addCriterion("HEIGHT =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(String value) {
            addCriterion("HEIGHT <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(String value) {
            addCriterion("HEIGHT >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(String value) {
            addCriterion("HEIGHT >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(String value) {
            addCriterion("HEIGHT <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(String value) {
            addCriterion("HEIGHT <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLike(String value) {
            addCriterion("HEIGHT like", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotLike(String value) {
            addCriterion("HEIGHT not like", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<String> values) {
            addCriterion("HEIGHT in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<String> values) {
            addCriterion("HEIGHT not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(String value1, String value2) {
            addCriterion("HEIGHT between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(String value1, String value2) {
            addCriterion("HEIGHT not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andAllPathIsNull() {
            addCriterion("ALL_PATH is null");
            return (Criteria) this;
        }

        public Criteria andAllPathIsNotNull() {
            addCriterion("ALL_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andAllPathEqualTo(String value) {
            addCriterion("ALL_PATH =", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotEqualTo(String value) {
            addCriterion("ALL_PATH <>", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathGreaterThan(String value) {
            addCriterion("ALL_PATH >", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathGreaterThanOrEqualTo(String value) {
            addCriterion("ALL_PATH >=", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathLessThan(String value) {
            addCriterion("ALL_PATH <", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathLessThanOrEqualTo(String value) {
            addCriterion("ALL_PATH <=", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathLike(String value) {
            addCriterion("ALL_PATH like", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotLike(String value) {
            addCriterion("ALL_PATH not like", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathIn(List<String> values) {
            addCriterion("ALL_PATH in", values, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotIn(List<String> values) {
            addCriterion("ALL_PATH not in", values, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathBetween(String value1, String value2) {
            addCriterion("ALL_PATH between", value1, value2, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotBetween(String value1, String value2) {
            addCriterion("ALL_PATH not between", value1, value2, "allPath");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIsNull() {
            addCriterion("PUBLIC_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIsNotNull() {
            addCriterion("PUBLIC_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPublicTimeEqualTo(String value) {
            addCriterion("PUBLIC_TIME =", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotEqualTo(String value) {
            addCriterion("PUBLIC_TIME <>", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeGreaterThan(String value) {
            addCriterion("PUBLIC_TIME >", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PUBLIC_TIME >=", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLessThan(String value) {
            addCriterion("PUBLIC_TIME <", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLessThanOrEqualTo(String value) {
            addCriterion("PUBLIC_TIME <=", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLike(String value) {
            addCriterion("PUBLIC_TIME like", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotLike(String value) {
            addCriterion("PUBLIC_TIME not like", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIn(List<String> values) {
            addCriterion("PUBLIC_TIME in", values, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotIn(List<String> values) {
            addCriterion("PUBLIC_TIME not in", values, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeBetween(String value1, String value2) {
            addCriterion("PUBLIC_TIME between", value1, value2, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotBetween(String value1, String value2) {
            addCriterion("PUBLIC_TIME not between", value1, value2, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPictureLenghIsNull() {
            addCriterion("PICTURE_LENGH is null");
            return (Criteria) this;
        }

        public Criteria andPictureLenghIsNotNull() {
            addCriterion("PICTURE_LENGH is not null");
            return (Criteria) this;
        }

        public Criteria andPictureLenghEqualTo(Integer value) {
            addCriterion("PICTURE_LENGH =", value, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghNotEqualTo(Integer value) {
            addCriterion("PICTURE_LENGH <>", value, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghGreaterThan(Integer value) {
            addCriterion("PICTURE_LENGH >", value, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_LENGH >=", value, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghLessThan(Integer value) {
            addCriterion("PICTURE_LENGH <", value, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_LENGH <=", value, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghIn(List<Integer> values) {
            addCriterion("PICTURE_LENGH in", values, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghNotIn(List<Integer> values) {
            addCriterion("PICTURE_LENGH not in", values, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_LENGH between", value1, value2, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureLenghNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_LENGH not between", value1, value2, "pictureLengh");
            return (Criteria) this;
        }

        public Criteria andPictureCreterIsNull() {
            addCriterion("PICTURE_CRETER is null");
            return (Criteria) this;
        }

        public Criteria andPictureCreterIsNotNull() {
            addCriterion("PICTURE_CRETER is not null");
            return (Criteria) this;
        }

        public Criteria andPictureCreterEqualTo(String value) {
            addCriterion("PICTURE_CRETER =", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterNotEqualTo(String value) {
            addCriterion("PICTURE_CRETER <>", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterGreaterThan(String value) {
            addCriterion("PICTURE_CRETER >", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterGreaterThanOrEqualTo(String value) {
            addCriterion("PICTURE_CRETER >=", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterLessThan(String value) {
            addCriterion("PICTURE_CRETER <", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterLessThanOrEqualTo(String value) {
            addCriterion("PICTURE_CRETER <=", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterLike(String value) {
            addCriterion("PICTURE_CRETER like", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterNotLike(String value) {
            addCriterion("PICTURE_CRETER not like", value, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterIn(List<String> values) {
            addCriterion("PICTURE_CRETER in", values, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterNotIn(List<String> values) {
            addCriterion("PICTURE_CRETER not in", values, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterBetween(String value1, String value2) {
            addCriterion("PICTURE_CRETER between", value1, value2, "pictureCreter");
            return (Criteria) this;
        }

        public Criteria andPictureCreterNotBetween(String value1, String value2) {
            addCriterion("PICTURE_CRETER not between", value1, value2, "pictureCreter");
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