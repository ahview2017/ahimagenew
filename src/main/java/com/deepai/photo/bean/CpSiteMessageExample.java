package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpSiteMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpSiteMessageExample() {
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

        public Criteria andSmContentIsNull() {
            addCriterion("SM_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andSmContentIsNotNull() {
            addCriterion("SM_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andSmContentEqualTo(String value) {
            addCriterion("SM_CONTENT =", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentNotEqualTo(String value) {
            addCriterion("SM_CONTENT <>", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentGreaterThan(String value) {
            addCriterion("SM_CONTENT >", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentGreaterThanOrEqualTo(String value) {
            addCriterion("SM_CONTENT >=", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentLessThan(String value) {
            addCriterion("SM_CONTENT <", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentLessThanOrEqualTo(String value) {
            addCriterion("SM_CONTENT <=", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentLike(String value) {
            addCriterion("SM_CONTENT like", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentNotLike(String value) {
            addCriterion("SM_CONTENT not like", value, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentIn(List<String> values) {
            addCriterion("SM_CONTENT in", values, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentNotIn(List<String> values) {
            addCriterion("SM_CONTENT not in", values, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentBetween(String value1, String value2) {
            addCriterion("SM_CONTENT between", value1, value2, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmContentNotBetween(String value1, String value2) {
            addCriterion("SM_CONTENT not between", value1, value2, "smContent");
            return (Criteria) this;
        }

        public Criteria andSmLinkIsNull() {
            addCriterion("SM_LINK is null");
            return (Criteria) this;
        }

        public Criteria andSmLinkIsNotNull() {
            addCriterion("SM_LINK is not null");
            return (Criteria) this;
        }

        public Criteria andSmLinkEqualTo(String value) {
            addCriterion("SM_LINK =", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkNotEqualTo(String value) {
            addCriterion("SM_LINK <>", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkGreaterThan(String value) {
            addCriterion("SM_LINK >", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkGreaterThanOrEqualTo(String value) {
            addCriterion("SM_LINK >=", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkLessThan(String value) {
            addCriterion("SM_LINK <", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkLessThanOrEqualTo(String value) {
            addCriterion("SM_LINK <=", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkLike(String value) {
            addCriterion("SM_LINK like", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkNotLike(String value) {
            addCriterion("SM_LINK not like", value, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkIn(List<String> values) {
            addCriterion("SM_LINK in", values, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkNotIn(List<String> values) {
            addCriterion("SM_LINK not in", values, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkBetween(String value1, String value2) {
            addCriterion("SM_LINK between", value1, value2, "smLink");
            return (Criteria) this;
        }

        public Criteria andSmLinkNotBetween(String value1, String value2) {
            addCriterion("SM_LINK not between", value1, value2, "smLink");
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