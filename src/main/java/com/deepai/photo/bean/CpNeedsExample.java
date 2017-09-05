package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpNeedsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpNeedsExample() {
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

        public Criteria andCountIsNull() {
            addCriterion("COUNT is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Integer value) {
            addCriterion("COUNT =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Integer value) {
            addCriterion("COUNT <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Integer value) {
            addCriterion("COUNT >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("COUNT >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Integer value) {
            addCriterion("COUNT <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Integer value) {
            addCriterion("COUNT <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Integer> values) {
            addCriterion("COUNT in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Integer> values) {
            addCriterion("COUNT not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Integer value1, Integer value2) {
            addCriterion("COUNT between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Integer value1, Integer value2) {
            addCriterion("COUNT not between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andPtimeIsNull() {
            addCriterion("PTIME is null");
            return (Criteria) this;
        }

        public Criteria andPtimeIsNotNull() {
            addCriterion("PTIME is not null");
            return (Criteria) this;
        }

        public Criteria andPtimeEqualTo(Date value) {
            addCriterion("PTIME =", value, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeNotEqualTo(Date value) {
            addCriterion("PTIME <>", value, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeGreaterThan(Date value) {
            addCriterion("PTIME >", value, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PTIME >=", value, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeLessThan(Date value) {
            addCriterion("PTIME <", value, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeLessThanOrEqualTo(Date value) {
            addCriterion("PTIME <=", value, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeIn(List<Date> values) {
            addCriterion("PTIME in", values, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeNotIn(List<Date> values) {
            addCriterion("PTIME not in", values, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeBetween(Date value1, Date value2) {
            addCriterion("PTIME between", value1, value2, "ptime");
            return (Criteria) this;
        }

        public Criteria andPtimeNotBetween(Date value1, Date value2) {
            addCriterion("PTIME not between", value1, value2, "ptime");
            return (Criteria) this;
        }

        public Criteria andFtimeIsNull() {
            addCriterion("FTIME is null");
            return (Criteria) this;
        }

        public Criteria andFtimeIsNotNull() {
            addCriterion("FTIME is not null");
            return (Criteria) this;
        }

        public Criteria andFtimeEqualTo(Date value) {
            addCriterion("FTIME =", value, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeNotEqualTo(Date value) {
            addCriterion("FTIME <>", value, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeGreaterThan(Date value) {
            addCriterion("FTIME >", value, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("FTIME >=", value, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeLessThan(Date value) {
            addCriterion("FTIME <", value, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeLessThanOrEqualTo(Date value) {
            addCriterion("FTIME <=", value, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeIn(List<Date> values) {
            addCriterion("FTIME in", values, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeNotIn(List<Date> values) {
            addCriterion("FTIME not in", values, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeBetween(Date value1, Date value2) {
            addCriterion("FTIME between", value1, value2, "ftime");
            return (Criteria) this;
        }

        public Criteria andFtimeNotBetween(Date value1, Date value2) {
            addCriterion("FTIME not between", value1, value2, "ftime");
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

        public Criteria andUserendtimeIsNull() {
            addCriterion("USERENDTIME is null");
            return (Criteria) this;
        }

        public Criteria andUserendtimeIsNotNull() {
            addCriterion("USERENDTIME is not null");
            return (Criteria) this;
        }

        public Criteria andUserendtimeEqualTo(Date value) {
            addCriterion("USERENDTIME =", value, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeNotEqualTo(Date value) {
            addCriterion("USERENDTIME <>", value, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeGreaterThan(Date value) {
            addCriterion("USERENDTIME >", value, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("USERENDTIME >=", value, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeLessThan(Date value) {
            addCriterion("USERENDTIME <", value, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeLessThanOrEqualTo(Date value) {
            addCriterion("USERENDTIME <=", value, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeIn(List<Date> values) {
            addCriterion("USERENDTIME in", values, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeNotIn(List<Date> values) {
            addCriterion("USERENDTIME not in", values, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeBetween(Date value1, Date value2) {
            addCriterion("USERENDTIME between", value1, value2, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserendtimeNotBetween(Date value1, Date value2) {
            addCriterion("USERENDTIME not between", value1, value2, "userendtime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeIsNull() {
            addCriterion("USERSTARTIME is null");
            return (Criteria) this;
        }

        public Criteria andUserstartimeIsNotNull() {
            addCriterion("USERSTARTIME is not null");
            return (Criteria) this;
        }

        public Criteria andUserstartimeEqualTo(Date value) {
            addCriterion("USERSTARTIME =", value, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeNotEqualTo(Date value) {
            addCriterion("USERSTARTIME <>", value, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeGreaterThan(Date value) {
            addCriterion("USERSTARTIME >", value, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeGreaterThanOrEqualTo(Date value) {
            addCriterion("USERSTARTIME >=", value, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeLessThan(Date value) {
            addCriterion("USERSTARTIME <", value, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeLessThanOrEqualTo(Date value) {
            addCriterion("USERSTARTIME <=", value, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeIn(List<Date> values) {
            addCriterion("USERSTARTIME in", values, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeNotIn(List<Date> values) {
            addCriterion("USERSTARTIME not in", values, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeBetween(Date value1, Date value2) {
            addCriterion("USERSTARTIME between", value1, value2, "userstartime");
            return (Criteria) this;
        }

        public Criteria andUserstartimeNotBetween(Date value1, Date value2) {
            addCriterion("USERSTARTIME not between", value1, value2, "userstartime");
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

        public Criteria andPriceEqualTo(Double value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Double value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Double value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Double value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Double value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Double> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Double> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Double value1, Double value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Double value1, Double value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDescsIsNull() {
            addCriterion("DESCS is null");
            return (Criteria) this;
        }

        public Criteria andDescsIsNotNull() {
            addCriterion("DESCS is not null");
            return (Criteria) this;
        }

        public Criteria andDescsEqualTo(String value) {
            addCriterion("DESCS =", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsNotEqualTo(String value) {
            addCriterion("DESCS <>", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsGreaterThan(String value) {
            addCriterion("DESCS >", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsGreaterThanOrEqualTo(String value) {
            addCriterion("DESCS >=", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsLessThan(String value) {
            addCriterion("DESCS <", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsLessThanOrEqualTo(String value) {
            addCriterion("DESCS <=", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsLike(String value) {
            addCriterion("DESCS like", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsNotLike(String value) {
            addCriterion("DESCS not like", value, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsIn(List<String> values) {
            addCriterion("DESCS in", values, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsNotIn(List<String> values) {
            addCriterion("DESCS not in", values, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsBetween(String value1, String value2) {
            addCriterion("DESCS between", value1, value2, "descs");
            return (Criteria) this;
        }

        public Criteria andDescsNotBetween(String value1, String value2) {
            addCriterion("DESCS not between", value1, value2, "descs");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianIsNull() {
            addCriterion("KANZAIMEIJIAN is null");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianIsNotNull() {
            addCriterion("KANZAIMEIJIAN is not null");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianEqualTo(String value) {
            addCriterion("KANZAIMEIJIAN =", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianNotEqualTo(String value) {
            addCriterion("KANZAIMEIJIAN <>", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianGreaterThan(String value) {
            addCriterion("KANZAIMEIJIAN >", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianGreaterThanOrEqualTo(String value) {
            addCriterion("KANZAIMEIJIAN >=", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianLessThan(String value) {
            addCriterion("KANZAIMEIJIAN <", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianLessThanOrEqualTo(String value) {
            addCriterion("KANZAIMEIJIAN <=", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianLike(String value) {
            addCriterion("KANZAIMEIJIAN like", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianNotLike(String value) {
            addCriterion("KANZAIMEIJIAN not like", value, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianIn(List<String> values) {
            addCriterion("KANZAIMEIJIAN in", values, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianNotIn(List<String> values) {
            addCriterion("KANZAIMEIJIAN not in", values, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianBetween(String value1, String value2) {
            addCriterion("KANZAIMEIJIAN between", value1, value2, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andKanzaimeijianNotBetween(String value1, String value2) {
            addCriterion("KANZAIMEIJIAN not between", value1, value2, "kanzaimeijian");
            return (Criteria) this;
        }

        public Criteria andAuditdescIsNull() {
            addCriterion("AUDITDESC is null");
            return (Criteria) this;
        }

        public Criteria andAuditdescIsNotNull() {
            addCriterion("AUDITDESC is not null");
            return (Criteria) this;
        }

        public Criteria andAuditdescEqualTo(String value) {
            addCriterion("AUDITDESC =", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescNotEqualTo(String value) {
            addCriterion("AUDITDESC <>", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescGreaterThan(String value) {
            addCriterion("AUDITDESC >", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescGreaterThanOrEqualTo(String value) {
            addCriterion("AUDITDESC >=", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescLessThan(String value) {
            addCriterion("AUDITDESC <", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescLessThanOrEqualTo(String value) {
            addCriterion("AUDITDESC <=", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescLike(String value) {
            addCriterion("AUDITDESC like", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescNotLike(String value) {
            addCriterion("AUDITDESC not like", value, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescIn(List<String> values) {
            addCriterion("AUDITDESC in", values, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescNotIn(List<String> values) {
            addCriterion("AUDITDESC not in", values, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescBetween(String value1, String value2) {
            addCriterion("AUDITDESC between", value1, value2, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andAuditdescNotBetween(String value1, String value2) {
            addCriterion("AUDITDESC not between", value1, value2, "auditdesc");
            return (Criteria) this;
        }

        public Criteria andPictureuseIsNull() {
            addCriterion("PICTUREUSE is null");
            return (Criteria) this;
        }

        public Criteria andPictureuseIsNotNull() {
            addCriterion("PICTUREUSE is not null");
            return (Criteria) this;
        }

        public Criteria andPictureuseEqualTo(String value) {
            addCriterion("PICTUREUSE =", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseNotEqualTo(String value) {
            addCriterion("PICTUREUSE <>", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseGreaterThan(String value) {
            addCriterion("PICTUREUSE >", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseGreaterThanOrEqualTo(String value) {
            addCriterion("PICTUREUSE >=", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseLessThan(String value) {
            addCriterion("PICTUREUSE <", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseLessThanOrEqualTo(String value) {
            addCriterion("PICTUREUSE <=", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseLike(String value) {
            addCriterion("PICTUREUSE like", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseNotLike(String value) {
            addCriterion("PICTUREUSE not like", value, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseIn(List<String> values) {
            addCriterion("PICTUREUSE in", values, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseNotIn(List<String> values) {
            addCriterion("PICTUREUSE not in", values, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseBetween(String value1, String value2) {
            addCriterion("PICTUREUSE between", value1, value2, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andPictureuseNotBetween(String value1, String value2) {
            addCriterion("PICTUREUSE not between", value1, value2, "pictureuse");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }
        public Criteria andLangTypeEqualTo(Integer value) {
            addCriterion("LANG_TYPE =", value, "langType");
            return (Criteria) this;
        }
        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andUserDescIsNull() {
            addCriterion("USER_DESC is null");
            return (Criteria) this;
        }

        public Criteria andUserDescIsNotNull() {
            addCriterion("USER_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andUserDescEqualTo(String value) {
            addCriterion("USER_DESC =", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescNotEqualTo(String value) {
            addCriterion("USER_DESC <>", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescGreaterThan(String value) {
            addCriterion("USER_DESC >", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescGreaterThanOrEqualTo(String value) {
            addCriterion("USER_DESC >=", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescLessThan(String value) {
            addCriterion("USER_DESC <", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescLessThanOrEqualTo(String value) {
            addCriterion("USER_DESC <=", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescLike(String value) {
            addCriterion("USER_DESC like", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescNotLike(String value) {
            addCriterion("USER_DESC not like", value, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescIn(List<String> values) {
            addCriterion("USER_DESC in", values, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescNotIn(List<String> values) {
            addCriterion("USER_DESC not in", values, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescBetween(String value1, String value2) {
            addCriterion("USER_DESC between", value1, value2, "userDesc");
            return (Criteria) this;
        }

        public Criteria andUserDescNotBetween(String value1, String value2) {
            addCriterion("USER_DESC not between", value1, value2, "userDesc");
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