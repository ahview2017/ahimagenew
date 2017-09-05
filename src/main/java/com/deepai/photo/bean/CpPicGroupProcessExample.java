package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpPicGroupProcessExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpPicGroupProcessExample() {
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

        public Criteria andFlowTypeIsNull() {
            addCriterion("FLOW_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeIsNotNull() {
            addCriterion("FLOW_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeEqualTo(Integer value) {
            addCriterion("FLOW_TYPE =", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotEqualTo(Integer value) {
            addCriterion("FLOW_TYPE <>", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeGreaterThan(Integer value) {
            addCriterion("FLOW_TYPE >", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("FLOW_TYPE >=", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeLessThan(Integer value) {
            addCriterion("FLOW_TYPE <", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeLessThanOrEqualTo(Integer value) {
            addCriterion("FLOW_TYPE <=", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeIn(List<Integer> values) {
            addCriterion("FLOW_TYPE in", values, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotIn(List<Integer> values) {
            addCriterion("FLOW_TYPE not in", values, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeBetween(Integer value1, Integer value2) {
            addCriterion("FLOW_TYPE between", value1, value2, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("FLOW_TYPE not between", value1, value2, "flowType");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdIsNull() {
            addCriterion("PICGROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdIsNotNull() {
            addCriterion("PICGROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdEqualTo(Integer value) {
            addCriterion("PICGROUP_ID =", value, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdNotEqualTo(Integer value) {
            addCriterion("PICGROUP_ID <>", value, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdGreaterThan(Integer value) {
            addCriterion("PICGROUP_ID >", value, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICGROUP_ID >=", value, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdLessThan(Integer value) {
            addCriterion("PICGROUP_ID <", value, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("PICGROUP_ID <=", value, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdIn(List<Integer> values) {
            addCriterion("PICGROUP_ID in", values, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdNotIn(List<Integer> values) {
            addCriterion("PICGROUP_ID not in", values, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdBetween(Integer value1, Integer value2) {
            addCriterion("PICGROUP_ID between", value1, value2, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andPicgroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PICGROUP_ID not between", value1, value2, "picgroupId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdIsNull() {
            addCriterion("OPERATE_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdIsNotNull() {
            addCriterion("OPERATE_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdEqualTo(Integer value) {
            addCriterion("OPERATE_USER_ID =", value, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdNotEqualTo(Integer value) {
            addCriterion("OPERATE_USER_ID <>", value, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdGreaterThan(Integer value) {
            addCriterion("OPERATE_USER_ID >", value, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPERATE_USER_ID >=", value, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdLessThan(Integer value) {
            addCriterion("OPERATE_USER_ID <", value, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("OPERATE_USER_ID <=", value, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdIn(List<Integer> values) {
            addCriterion("OPERATE_USER_ID in", values, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdNotIn(List<Integer> values) {
            addCriterion("OPERATE_USER_ID not in", values, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("OPERATE_USER_ID between", value1, value2, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("OPERATE_USER_ID not between", value1, value2, "operateUserId");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameIsNull() {
            addCriterion("OPERATE_USERNAME is null");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameIsNotNull() {
            addCriterion("OPERATE_USERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameEqualTo(String value) {
            addCriterion("OPERATE_USERNAME =", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotEqualTo(String value) {
            addCriterion("OPERATE_USERNAME <>", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameGreaterThan(String value) {
            addCriterion("OPERATE_USERNAME >", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATE_USERNAME >=", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameLessThan(String value) {
            addCriterion("OPERATE_USERNAME <", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameLessThanOrEqualTo(String value) {
            addCriterion("OPERATE_USERNAME <=", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameLike(String value) {
            addCriterion("OPERATE_USERNAME like", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotLike(String value) {
            addCriterion("OPERATE_USERNAME not like", value, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameIn(List<String> values) {
            addCriterion("OPERATE_USERNAME in", values, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotIn(List<String> values) {
            addCriterion("OPERATE_USERNAME not in", values, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameBetween(String value1, String value2) {
            addCriterion("OPERATE_USERNAME between", value1, value2, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateUsernameNotBetween(String value1, String value2) {
            addCriterion("OPERATE_USERNAME not between", value1, value2, "operateUsername");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNull() {
            addCriterion("OPERATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNotNull() {
            addCriterion("OPERATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeEqualTo(Date value) {
            addCriterion("OPERATE_TIME =", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotEqualTo(Date value) {
            addCriterion("OPERATE_TIME <>", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThan(Date value) {
            addCriterion("OPERATE_TIME >", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OPERATE_TIME >=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThan(Date value) {
            addCriterion("OPERATE_TIME <", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThanOrEqualTo(Date value) {
            addCriterion("OPERATE_TIME <=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIn(List<Date> values) {
            addCriterion("OPERATE_TIME in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotIn(List<Date> values) {
            addCriterion("OPERATE_TIME not in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeBetween(Date value1, Date value2) {
            addCriterion("OPERATE_TIME between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotBetween(Date value1, Date value2) {
            addCriterion("OPERATE_TIME not between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateMemoIsNull() {
            addCriterion("OPERATE_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andOperateMemoIsNotNull() {
            addCriterion("OPERATE_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andOperateMemoEqualTo(String value) {
            addCriterion("OPERATE_MEMO =", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoNotEqualTo(String value) {
            addCriterion("OPERATE_MEMO <>", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoGreaterThan(String value) {
            addCriterion("OPERATE_MEMO >", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATE_MEMO >=", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoLessThan(String value) {
            addCriterion("OPERATE_MEMO <", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoLessThanOrEqualTo(String value) {
            addCriterion("OPERATE_MEMO <=", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoLike(String value) {
            addCriterion("OPERATE_MEMO like", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoNotLike(String value) {
            addCriterion("OPERATE_MEMO not like", value, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoIn(List<String> values) {
            addCriterion("OPERATE_MEMO in", values, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoNotIn(List<String> values) {
            addCriterion("OPERATE_MEMO not in", values, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoBetween(String value1, String value2) {
            addCriterion("OPERATE_MEMO between", value1, value2, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andOperateMemoNotBetween(String value1, String value2) {
            addCriterion("OPERATE_MEMO not between", value1, value2, "operateMemo");
            return (Criteria) this;
        }

        public Criteria andProofreadIdIsNull() {
            addCriterion("PROOFREAD_ID is null");
            return (Criteria) this;
        }

        public Criteria andProofreadIdIsNotNull() {
            addCriterion("PROOFREAD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProofreadIdEqualTo(Integer value) {
            addCriterion("PROOFREAD_ID =", value, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdNotEqualTo(Integer value) {
            addCriterion("PROOFREAD_ID <>", value, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdGreaterThan(Integer value) {
            addCriterion("PROOFREAD_ID >", value, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PROOFREAD_ID >=", value, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdLessThan(Integer value) {
            addCriterion("PROOFREAD_ID <", value, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdLessThanOrEqualTo(Integer value) {
            addCriterion("PROOFREAD_ID <=", value, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdIn(List<Integer> values) {
            addCriterion("PROOFREAD_ID in", values, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdNotIn(List<Integer> values) {
            addCriterion("PROOFREAD_ID not in", values, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdBetween(Integer value1, Integer value2) {
            addCriterion("PROOFREAD_ID between", value1, value2, "proofreadId");
            return (Criteria) this;
        }

        public Criteria andProofreadIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PROOFREAD_ID not between", value1, value2, "proofreadId");
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