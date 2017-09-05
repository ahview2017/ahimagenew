package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpOrderDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpOrderDetailExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("ORDER_NO =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("ORDER_NO <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("ORDER_NO >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_NO >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("ORDER_NO <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("ORDER_NO <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("ORDER_NO like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("ORDER_NO not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("ORDER_NO in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("ORDER_NO not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("ORDER_NO between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("ORDER_NO not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("FILENAME is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("FILENAME is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("FILENAME =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("FILENAME <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("FILENAME >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("FILENAME >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("FILENAME <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("FILENAME <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("FILENAME like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("FILENAME not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("FILENAME in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("FILENAME not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("FILENAME between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("FILENAME not between", value1, value2, "filename");
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

        public Criteria andDealPriceIsNull() {
            addCriterion("DEAL_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andDealPriceIsNotNull() {
            addCriterion("DEAL_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andDealPriceEqualTo(BigDecimal value) {
            addCriterion("DEAL_PRICE =", value, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceNotEqualTo(BigDecimal value) {
            addCriterion("DEAL_PRICE <>", value, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceGreaterThan(BigDecimal value) {
            addCriterion("DEAL_PRICE >", value, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEAL_PRICE >=", value, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceLessThan(BigDecimal value) {
            addCriterion("DEAL_PRICE <", value, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEAL_PRICE <=", value, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceIn(List<BigDecimal> values) {
            addCriterion("DEAL_PRICE in", values, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceNotIn(List<BigDecimal> values) {
            addCriterion("DEAL_PRICE not in", values, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEAL_PRICE between", value1, value2, "dealPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEAL_PRICE not between", value1, value2, "dealPrice");
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

        public Criteria andUserNameIsNull() {
            addCriterion("USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("USER_NAME =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("USER_NAME <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("USER_NAME >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("USER_NAME >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("USER_NAME <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("USER_NAME <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("USER_NAME like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("USER_NAME not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("USER_NAME in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("USER_NAME not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("USER_NAME between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("USER_NAME not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CREATETIME is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CREATETIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("CREATETIME =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("CREATETIME <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("CREATETIME >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATETIME >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("CREATETIME <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATETIME <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("CREATETIME in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("CREATETIME not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("CREATETIME between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATETIME not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdIsNull() {
            addCriterion("PHOTO_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdIsNotNull() {
            addCriterion("PHOTO_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdEqualTo(Integer value) {
            addCriterion("PHOTO_USER_ID =", value, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdNotEqualTo(Integer value) {
            addCriterion("PHOTO_USER_ID <>", value, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdGreaterThan(Integer value) {
            addCriterion("PHOTO_USER_ID >", value, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PHOTO_USER_ID >=", value, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdLessThan(Integer value) {
            addCriterion("PHOTO_USER_ID <", value, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("PHOTO_USER_ID <=", value, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdIn(List<Integer> values) {
            addCriterion("PHOTO_USER_ID in", values, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdNotIn(List<Integer> values) {
            addCriterion("PHOTO_USER_ID not in", values, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdBetween(Integer value1, Integer value2) {
            addCriterion("PHOTO_USER_ID between", value1, value2, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PHOTO_USER_ID not between", value1, value2, "photoUserId");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameIsNull() {
            addCriterion("PHOTO_USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameIsNotNull() {
            addCriterion("PHOTO_USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameEqualTo(String value) {
            addCriterion("PHOTO_USER_NAME =", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameNotEqualTo(String value) {
            addCriterion("PHOTO_USER_NAME <>", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameGreaterThan(String value) {
            addCriterion("PHOTO_USER_NAME >", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("PHOTO_USER_NAME >=", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameLessThan(String value) {
            addCriterion("PHOTO_USER_NAME <", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameLessThanOrEqualTo(String value) {
            addCriterion("PHOTO_USER_NAME <=", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameLike(String value) {
            addCriterion("PHOTO_USER_NAME like", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameNotLike(String value) {
            addCriterion("PHOTO_USER_NAME not like", value, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameIn(List<String> values) {
            addCriterion("PHOTO_USER_NAME in", values, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameNotIn(List<String> values) {
            addCriterion("PHOTO_USER_NAME not in", values, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameBetween(String value1, String value2) {
            addCriterion("PHOTO_USER_NAME between", value1, value2, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserNameNotBetween(String value1, String value2) {
            addCriterion("PHOTO_USER_NAME not between", value1, value2, "photoUserName");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideIsNull() {
            addCriterion("PHOTO_USER_DIVIDE is null");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideIsNotNull() {
            addCriterion("PHOTO_USER_DIVIDE is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideEqualTo(Integer value) {
            addCriterion("PHOTO_USER_DIVIDE =", value, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideNotEqualTo(Integer value) {
            addCriterion("PHOTO_USER_DIVIDE <>", value, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideGreaterThan(Integer value) {
            addCriterion("PHOTO_USER_DIVIDE >", value, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideGreaterThanOrEqualTo(Integer value) {
            addCriterion("PHOTO_USER_DIVIDE >=", value, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideLessThan(Integer value) {
            addCriterion("PHOTO_USER_DIVIDE <", value, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideLessThanOrEqualTo(Integer value) {
            addCriterion("PHOTO_USER_DIVIDE <=", value, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideIn(List<Integer> values) {
            addCriterion("PHOTO_USER_DIVIDE in", values, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideNotIn(List<Integer> values) {
            addCriterion("PHOTO_USER_DIVIDE not in", values, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideBetween(Integer value1, Integer value2) {
            addCriterion("PHOTO_USER_DIVIDE between", value1, value2, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andPhotoUserDivideNotBetween(Integer value1, Integer value2) {
            addCriterion("PHOTO_USER_DIVIDE not between", value1, value2, "photoUserDivide");
            return (Criteria) this;
        }

        public Criteria andDividePriceIsNull() {
            addCriterion("DIVIDE_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andDividePriceIsNotNull() {
            addCriterion("DIVIDE_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andDividePriceEqualTo(BigDecimal value) {
            addCriterion("DIVIDE_PRICE =", value, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceNotEqualTo(BigDecimal value) {
            addCriterion("DIVIDE_PRICE <>", value, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceGreaterThan(BigDecimal value) {
            addCriterion("DIVIDE_PRICE >", value, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DIVIDE_PRICE >=", value, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceLessThan(BigDecimal value) {
            addCriterion("DIVIDE_PRICE <", value, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DIVIDE_PRICE <=", value, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceIn(List<BigDecimal> values) {
            addCriterion("DIVIDE_PRICE in", values, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceNotIn(List<BigDecimal> values) {
            addCriterion("DIVIDE_PRICE not in", values, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIVIDE_PRICE between", value1, value2, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDividePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIVIDE_PRICE not between", value1, value2, "dividePrice");
            return (Criteria) this;
        }

        public Criteria andDivideStatusIsNull() {
            addCriterion("DIVIDE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andDivideStatusIsNotNull() {
            addCriterion("DIVIDE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andDivideStatusEqualTo(Integer value) {
            addCriterion("DIVIDE_STATUS =", value, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusNotEqualTo(Integer value) {
            addCriterion("DIVIDE_STATUS <>", value, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusGreaterThan(Integer value) {
            addCriterion("DIVIDE_STATUS >", value, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("DIVIDE_STATUS >=", value, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusLessThan(Integer value) {
            addCriterion("DIVIDE_STATUS <", value, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusLessThanOrEqualTo(Integer value) {
            addCriterion("DIVIDE_STATUS <=", value, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusIn(List<Integer> values) {
            addCriterion("DIVIDE_STATUS in", values, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusNotIn(List<Integer> values) {
            addCriterion("DIVIDE_STATUS not in", values, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusBetween(Integer value1, Integer value2) {
            addCriterion("DIVIDE_STATUS between", value1, value2, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("DIVIDE_STATUS not between", value1, value2, "divideStatus");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdIsNull() {
            addCriterion("DIVIDE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdIsNotNull() {
            addCriterion("DIVIDE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdEqualTo(Integer value) {
            addCriterion("DIVIDE_USER_ID =", value, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdNotEqualTo(Integer value) {
            addCriterion("DIVIDE_USER_ID <>", value, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdGreaterThan(Integer value) {
            addCriterion("DIVIDE_USER_ID >", value, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("DIVIDE_USER_ID >=", value, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdLessThan(Integer value) {
            addCriterion("DIVIDE_USER_ID <", value, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("DIVIDE_USER_ID <=", value, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdIn(List<Integer> values) {
            addCriterion("DIVIDE_USER_ID in", values, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdNotIn(List<Integer> values) {
            addCriterion("DIVIDE_USER_ID not in", values, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdBetween(Integer value1, Integer value2) {
            addCriterion("DIVIDE_USER_ID between", value1, value2, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("DIVIDE_USER_ID not between", value1, value2, "divideUserId");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameIsNull() {
            addCriterion("DIVIDE_USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameIsNotNull() {
            addCriterion("DIVIDE_USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameEqualTo(String value) {
            addCriterion("DIVIDE_USER_NAME =", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameNotEqualTo(String value) {
            addCriterion("DIVIDE_USER_NAME <>", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameGreaterThan(String value) {
            addCriterion("DIVIDE_USER_NAME >", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("DIVIDE_USER_NAME >=", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameLessThan(String value) {
            addCriterion("DIVIDE_USER_NAME <", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameLessThanOrEqualTo(String value) {
            addCriterion("DIVIDE_USER_NAME <=", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameLike(String value) {
            addCriterion("DIVIDE_USER_NAME like", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameNotLike(String value) {
            addCriterion("DIVIDE_USER_NAME not like", value, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameIn(List<String> values) {
            addCriterion("DIVIDE_USER_NAME in", values, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameNotIn(List<String> values) {
            addCriterion("DIVIDE_USER_NAME not in", values, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameBetween(String value1, String value2) {
            addCriterion("DIVIDE_USER_NAME between", value1, value2, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideUserNameNotBetween(String value1, String value2) {
            addCriterion("DIVIDE_USER_NAME not between", value1, value2, "divideUserName");
            return (Criteria) this;
        }

        public Criteria andDivideTimeIsNull() {
            addCriterion("DIVIDE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDivideTimeIsNotNull() {
            addCriterion("DIVIDE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDivideTimeEqualTo(Date value) {
            addCriterion("DIVIDE_TIME =", value, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeNotEqualTo(Date value) {
            addCriterion("DIVIDE_TIME <>", value, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeGreaterThan(Date value) {
            addCriterion("DIVIDE_TIME >", value, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DIVIDE_TIME >=", value, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeLessThan(Date value) {
            addCriterion("DIVIDE_TIME <", value, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeLessThanOrEqualTo(Date value) {
            addCriterion("DIVIDE_TIME <=", value, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeIn(List<Date> values) {
            addCriterion("DIVIDE_TIME in", values, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeNotIn(List<Date> values) {
            addCriterion("DIVIDE_TIME not in", values, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeBetween(Date value1, Date value2) {
            addCriterion("DIVIDE_TIME between", value1, value2, "divideTime");
            return (Criteria) this;
        }

        public Criteria andDivideTimeNotBetween(Date value1, Date value2) {
            addCriterion("DIVIDE_TIME not between", value1, value2, "divideTime");
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