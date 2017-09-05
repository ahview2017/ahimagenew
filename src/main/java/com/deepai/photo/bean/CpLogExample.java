package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpLogExample() {
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

        public Criteria andLogtypeIdIsNull() {
            addCriterion("LOGTYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdIsNotNull() {
            addCriterion("LOGTYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdEqualTo(Integer value) {
            addCriterion("LOGTYPE_ID =", value, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdNotEqualTo(Integer value) {
            addCriterion("LOGTYPE_ID <>", value, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdGreaterThan(Integer value) {
            addCriterion("LOGTYPE_ID >", value, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOGTYPE_ID >=", value, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdLessThan(Integer value) {
            addCriterion("LOGTYPE_ID <", value, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("LOGTYPE_ID <=", value, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdIn(List<Integer> values) {
            addCriterion("LOGTYPE_ID in", values, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdNotIn(List<Integer> values) {
            addCriterion("LOGTYPE_ID not in", values, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdBetween(Integer value1, Integer value2) {
            addCriterion("LOGTYPE_ID between", value1, value2, "logtypeId");
            return (Criteria) this;
        }

        public Criteria andLogtypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("LOGTYPE_ID not between", value1, value2, "logtypeId");
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

        public Criteria andOpeIpIsNull() {
            addCriterion("OPE_IP is null");
            return (Criteria) this;
        }

        public Criteria andOpeIpIsNotNull() {
            addCriterion("OPE_IP is not null");
            return (Criteria) this;
        }

        public Criteria andOpeIpEqualTo(String value) {
            addCriterion("OPE_IP =", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpNotEqualTo(String value) {
            addCriterion("OPE_IP <>", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpGreaterThan(String value) {
            addCriterion("OPE_IP >", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_IP >=", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpLessThan(String value) {
            addCriterion("OPE_IP <", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpLessThanOrEqualTo(String value) {
            addCriterion("OPE_IP <=", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpLike(String value) {
            addCriterion("OPE_IP like", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpNotLike(String value) {
            addCriterion("OPE_IP not like", value, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpIn(List<String> values) {
            addCriterion("OPE_IP in", values, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpNotIn(List<String> values) {
            addCriterion("OPE_IP not in", values, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpBetween(String value1, String value2) {
            addCriterion("OPE_IP between", value1, value2, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeIpNotBetween(String value1, String value2) {
            addCriterion("OPE_IP not between", value1, value2, "opeIp");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdIsNull() {
            addCriterion("OPE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdIsNotNull() {
            addCriterion("OPE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdEqualTo(Integer value) {
            addCriterion("OPE_USER_ID =", value, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdNotEqualTo(Integer value) {
            addCriterion("OPE_USER_ID <>", value, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdGreaterThan(Integer value) {
            addCriterion("OPE_USER_ID >", value, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPE_USER_ID >=", value, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdLessThan(Integer value) {
            addCriterion("OPE_USER_ID <", value, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("OPE_USER_ID <=", value, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdIn(List<Integer> values) {
            addCriterion("OPE_USER_ID in", values, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdNotIn(List<Integer> values) {
            addCriterion("OPE_USER_ID not in", values, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdBetween(Integer value1, Integer value2) {
            addCriterion("OPE_USER_ID between", value1, value2, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("OPE_USER_ID not between", value1, value2, "opeUserId");
            return (Criteria) this;
        }

        public Criteria andOpeUserIsNull() {
            addCriterion("OPE_USER is null");
            return (Criteria) this;
        }

        public Criteria andOpeUserIsNotNull() {
            addCriterion("OPE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andOpeUserEqualTo(String value) {
            addCriterion("OPE_USER =", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserNotEqualTo(String value) {
            addCriterion("OPE_USER <>", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserGreaterThan(String value) {
            addCriterion("OPE_USER >", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_USER >=", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserLessThan(String value) {
            addCriterion("OPE_USER <", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserLessThanOrEqualTo(String value) {
            addCriterion("OPE_USER <=", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserLike(String value) {
            addCriterion("OPE_USER like", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserNotLike(String value) {
            addCriterion("OPE_USER not like", value, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserIn(List<String> values) {
            addCriterion("OPE_USER in", values, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserNotIn(List<String> values) {
            addCriterion("OPE_USER not in", values, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserBetween(String value1, String value2) {
            addCriterion("OPE_USER between", value1, value2, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeUserNotBetween(String value1, String value2) {
            addCriterion("OPE_USER not between", value1, value2, "opeUser");
            return (Criteria) this;
        }

        public Criteria andOpeTimeIsNull() {
            addCriterion("OPE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOpeTimeIsNotNull() {
            addCriterion("OPE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOpeTimeEqualTo(Date value) {
            addCriterion("OPE_TIME =", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeNotEqualTo(Date value) {
            addCriterion("OPE_TIME <>", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeGreaterThan(Date value) {
            addCriterion("OPE_TIME >", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OPE_TIME >=", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeLessThan(Date value) {
            addCriterion("OPE_TIME <", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeLessThanOrEqualTo(Date value) {
            addCriterion("OPE_TIME <=", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeIn(List<Date> values) {
            addCriterion("OPE_TIME in", values, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeNotIn(List<Date> values) {
            addCriterion("OPE_TIME not in", values, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeBetween(Date value1, Date value2) {
            addCriterion("OPE_TIME between", value1, value2, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeNotBetween(Date value1, Date value2) {
            addCriterion("OPE_TIME not between", value1, value2, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeContentIsNull() {
            addCriterion("OPE_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andOpeContentIsNotNull() {
            addCriterion("OPE_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andOpeContentEqualTo(String value) {
            addCriterion("OPE_CONTENT =", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentNotEqualTo(String value) {
            addCriterion("OPE_CONTENT <>", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentGreaterThan(String value) {
            addCriterion("OPE_CONTENT >", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_CONTENT >=", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentLessThan(String value) {
            addCriterion("OPE_CONTENT <", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentLessThanOrEqualTo(String value) {
            addCriterion("OPE_CONTENT <=", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentLike(String value) {
            addCriterion("OPE_CONTENT like", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentNotLike(String value) {
            addCriterion("OPE_CONTENT not like", value, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentIn(List<String> values) {
            addCriterion("OPE_CONTENT in", values, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentNotIn(List<String> values) {
            addCriterion("OPE_CONTENT not in", values, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentBetween(String value1, String value2) {
            addCriterion("OPE_CONTENT between", value1, value2, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeContentNotBetween(String value1, String value2) {
            addCriterion("OPE_CONTENT not between", value1, value2, "opeContent");
            return (Criteria) this;
        }

        public Criteria andOpeParamIsNull() {
            addCriterion("OPE_PARAM is null");
            return (Criteria) this;
        }

        public Criteria andOpeParamIsNotNull() {
            addCriterion("OPE_PARAM is not null");
            return (Criteria) this;
        }

        public Criteria andOpeParamEqualTo(String value) {
            addCriterion("OPE_PARAM =", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamNotEqualTo(String value) {
            addCriterion("OPE_PARAM <>", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamGreaterThan(String value) {
            addCriterion("OPE_PARAM >", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_PARAM >=", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamLessThan(String value) {
            addCriterion("OPE_PARAM <", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamLessThanOrEqualTo(String value) {
            addCriterion("OPE_PARAM <=", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamLike(String value) {
            addCriterion("OPE_PARAM like", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamNotLike(String value) {
            addCriterion("OPE_PARAM not like", value, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamIn(List<String> values) {
            addCriterion("OPE_PARAM in", values, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamNotIn(List<String> values) {
            addCriterion("OPE_PARAM not in", values, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamBetween(String value1, String value2) {
            addCriterion("OPE_PARAM between", value1, value2, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeParamNotBetween(String value1, String value2) {
            addCriterion("OPE_PARAM not between", value1, value2, "opeParam");
            return (Criteria) this;
        }

        public Criteria andOpeIdsIsNull() {
            addCriterion("OPE_IDS is null");
            return (Criteria) this;
        }

        public Criteria andOpeIdsIsNotNull() {
            addCriterion("OPE_IDS is not null");
            return (Criteria) this;
        }

        public Criteria andOpeIdsEqualTo(String value) {
            addCriterion("OPE_IDS =", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsNotEqualTo(String value) {
            addCriterion("OPE_IDS <>", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsGreaterThan(String value) {
            addCriterion("OPE_IDS >", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_IDS >=", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsLessThan(String value) {
            addCriterion("OPE_IDS <", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsLessThanOrEqualTo(String value) {
            addCriterion("OPE_IDS <=", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsLike(String value) {
            addCriterion("OPE_IDS like", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsNotLike(String value) {
            addCriterion("OPE_IDS not like", value, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsIn(List<String> values) {
            addCriterion("OPE_IDS in", values, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsNotIn(List<String> values) {
            addCriterion("OPE_IDS not in", values, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsBetween(String value1, String value2) {
            addCriterion("OPE_IDS between", value1, value2, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeIdsNotBetween(String value1, String value2) {
            addCriterion("OPE_IDS not between", value1, value2, "opeIds");
            return (Criteria) this;
        }

        public Criteria andOpeResultIsNull() {
            addCriterion("OPE_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andOpeResultIsNotNull() {
            addCriterion("OPE_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andOpeResultEqualTo(String value) {
            addCriterion("OPE_RESULT =", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultNotEqualTo(String value) {
            addCriterion("OPE_RESULT <>", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultGreaterThan(String value) {
            addCriterion("OPE_RESULT >", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_RESULT >=", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultLessThan(String value) {
            addCriterion("OPE_RESULT <", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultLessThanOrEqualTo(String value) {
            addCriterion("OPE_RESULT <=", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultLike(String value) {
            addCriterion("OPE_RESULT like", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultNotLike(String value) {
            addCriterion("OPE_RESULT not like", value, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultIn(List<String> values) {
            addCriterion("OPE_RESULT in", values, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultNotIn(List<String> values) {
            addCriterion("OPE_RESULT not in", values, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultBetween(String value1, String value2) {
            addCriterion("OPE_RESULT between", value1, value2, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeResultNotBetween(String value1, String value2) {
            addCriterion("OPE_RESULT not between", value1, value2, "opeResult");
            return (Criteria) this;
        }

        public Criteria andOpeTypeIsNull() {
            addCriterion("OPE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOpeTypeIsNotNull() {
            addCriterion("OPE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOpeTypeEqualTo(Byte value) {
            addCriterion("OPE_TYPE =", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotEqualTo(Byte value) {
            addCriterion("OPE_TYPE <>", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeGreaterThan(Byte value) {
            addCriterion("OPE_TYPE >", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("OPE_TYPE >=", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeLessThan(Byte value) {
            addCriterion("OPE_TYPE <", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("OPE_TYPE <=", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeIn(List<Byte> values) {
            addCriterion("OPE_TYPE in", values, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotIn(List<Byte> values) {
            addCriterion("OPE_TYPE not in", values, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeBetween(Byte value1, Byte value2) {
            addCriterion("OPE_TYPE between", value1, value2, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("OPE_TYPE not between", value1, value2, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeMemoIsNull() {
            addCriterion("OPE_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andOpeMemoIsNotNull() {
            addCriterion("OPE_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andOpeMemoEqualTo(String value) {
            addCriterion("OPE_MEMO =", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoNotEqualTo(String value) {
            addCriterion("OPE_MEMO <>", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoGreaterThan(String value) {
            addCriterion("OPE_MEMO >", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoGreaterThanOrEqualTo(String value) {
            addCriterion("OPE_MEMO >=", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoLessThan(String value) {
            addCriterion("OPE_MEMO <", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoLessThanOrEqualTo(String value) {
            addCriterion("OPE_MEMO <=", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoLike(String value) {
            addCriterion("OPE_MEMO like", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoNotLike(String value) {
            addCriterion("OPE_MEMO not like", value, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoIn(List<String> values) {
            addCriterion("OPE_MEMO in", values, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoNotIn(List<String> values) {
            addCriterion("OPE_MEMO not in", values, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoBetween(String value1, String value2) {
            addCriterion("OPE_MEMO between", value1, value2, "opeMemo");
            return (Criteria) this;
        }

        public Criteria andOpeMemoNotBetween(String value1, String value2) {
            addCriterion("OPE_MEMO not between", value1, value2, "opeMemo");
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