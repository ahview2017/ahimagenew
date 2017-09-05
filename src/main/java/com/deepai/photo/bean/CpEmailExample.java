package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpEmailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpEmailExample() {
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

        public Criteria andEmailTitleIsNull() {
            addCriterion("EMAIL_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andEmailTitleIsNotNull() {
            addCriterion("EMAIL_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andEmailTitleEqualTo(String value) {
            addCriterion("EMAIL_TITLE =", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleNotEqualTo(String value) {
            addCriterion("EMAIL_TITLE <>", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleGreaterThan(String value) {
            addCriterion("EMAIL_TITLE >", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_TITLE >=", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleLessThan(String value) {
            addCriterion("EMAIL_TITLE <", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_TITLE <=", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleLike(String value) {
            addCriterion("EMAIL_TITLE like", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleNotLike(String value) {
            addCriterion("EMAIL_TITLE not like", value, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleIn(List<String> values) {
            addCriterion("EMAIL_TITLE in", values, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleNotIn(List<String> values) {
            addCriterion("EMAIL_TITLE not in", values, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleBetween(String value1, String value2) {
            addCriterion("EMAIL_TITLE between", value1, value2, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailTitleNotBetween(String value1, String value2) {
            addCriterion("EMAIL_TITLE not between", value1, value2, "emailTitle");
            return (Criteria) this;
        }

        public Criteria andEmailContentIsNull() {
            addCriterion("EMAIL_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andEmailContentIsNotNull() {
            addCriterion("EMAIL_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andEmailContentEqualTo(String value) {
            addCriterion("EMAIL_CONTENT =", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotEqualTo(String value) {
            addCriterion("EMAIL_CONTENT <>", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentGreaterThan(String value) {
            addCriterion("EMAIL_CONTENT >", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_CONTENT >=", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentLessThan(String value) {
            addCriterion("EMAIL_CONTENT <", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_CONTENT <=", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentLike(String value) {
            addCriterion("EMAIL_CONTENT like", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotLike(String value) {
            addCriterion("EMAIL_CONTENT not like", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentIn(List<String> values) {
            addCriterion("EMAIL_CONTENT in", values, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotIn(List<String> values) {
            addCriterion("EMAIL_CONTENT not in", values, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentBetween(String value1, String value2) {
            addCriterion("EMAIL_CONTENT between", value1, value2, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotBetween(String value1, String value2) {
            addCriterion("EMAIL_CONTENT not between", value1, value2, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleIsNull() {
            addCriterion("EMAIL_RECIVER_ROLE is null");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleIsNotNull() {
            addCriterion("EMAIL_RECIVER_ROLE is not null");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleEqualTo(String value) {
            addCriterion("EMAIL_RECIVER_ROLE =", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleNotEqualTo(String value) {
            addCriterion("EMAIL_RECIVER_ROLE <>", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleGreaterThan(String value) {
            addCriterion("EMAIL_RECIVER_ROLE >", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_RECIVER_ROLE >=", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleLessThan(String value) {
            addCriterion("EMAIL_RECIVER_ROLE <", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_RECIVER_ROLE <=", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleLike(String value) {
            addCriterion("EMAIL_RECIVER_ROLE like", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleNotLike(String value) {
            addCriterion("EMAIL_RECIVER_ROLE not like", value, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleIn(List<String> values) {
            addCriterion("EMAIL_RECIVER_ROLE in", values, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleNotIn(List<String> values) {
            addCriterion("EMAIL_RECIVER_ROLE not in", values, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleBetween(String value1, String value2) {
            addCriterion("EMAIL_RECIVER_ROLE between", value1, value2, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverRoleNotBetween(String value1, String value2) {
            addCriterion("EMAIL_RECIVER_ROLE not between", value1, value2, "emailReciverRole");
            return (Criteria) this;
        }

        public Criteria andEmailReciverIsNull() {
            addCriterion("EMAIL_RECIVER is null");
            return (Criteria) this;
        }

        public Criteria andEmailReciverIsNotNull() {
            addCriterion("EMAIL_RECIVER is not null");
            return (Criteria) this;
        }

        public Criteria andEmailReciverEqualTo(String value) {
            addCriterion("EMAIL_RECIVER =", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverNotEqualTo(String value) {
            addCriterion("EMAIL_RECIVER <>", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverGreaterThan(String value) {
            addCriterion("EMAIL_RECIVER >", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_RECIVER >=", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverLessThan(String value) {
            addCriterion("EMAIL_RECIVER <", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_RECIVER <=", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverLike(String value) {
            addCriterion("EMAIL_RECIVER like", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverNotLike(String value) {
            addCriterion("EMAIL_RECIVER not like", value, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverIn(List<String> values) {
            addCriterion("EMAIL_RECIVER in", values, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverNotIn(List<String> values) {
            addCriterion("EMAIL_RECIVER not in", values, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverBetween(String value1, String value2) {
            addCriterion("EMAIL_RECIVER between", value1, value2, "emailReciver");
            return (Criteria) this;
        }

        public Criteria andEmailReciverNotBetween(String value1, String value2) {
            addCriterion("EMAIL_RECIVER not between", value1, value2, "emailReciver");
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

        public Criteria andSenderIsNull() {
            addCriterion("SENDER is null");
            return (Criteria) this;
        }

        public Criteria andSenderIsNotNull() {
            addCriterion("SENDER is not null");
            return (Criteria) this;
        }

        public Criteria andSenderEqualTo(String value) {
            addCriterion("SENDER =", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotEqualTo(String value) {
            addCriterion("SENDER <>", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThan(String value) {
            addCriterion("SENDER >", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThanOrEqualTo(String value) {
            addCriterion("SENDER >=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThan(String value) {
            addCriterion("SENDER <", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThanOrEqualTo(String value) {
            addCriterion("SENDER <=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLike(String value) {
            addCriterion("SENDER like", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotLike(String value) {
            addCriterion("SENDER not like", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderIn(List<String> values) {
            addCriterion("SENDER in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotIn(List<String> values) {
            addCriterion("SENDER not in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderBetween(String value1, String value2) {
            addCriterion("SENDER between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotBetween(String value1, String value2) {
            addCriterion("SENDER not between", value1, value2, "sender");
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