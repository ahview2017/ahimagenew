package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.List;

public class CpLanmuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpLanmuExample() {
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

        public Criteria andTopicIdIsNull() {
            addCriterion("TOPIC_ID is null");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNotNull() {
            addCriterion("TOPIC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTopicIdEqualTo(Integer value) {
            addCriterion("TOPIC_ID =", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotEqualTo(Integer value) {
            addCriterion("TOPIC_ID <>", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThan(Integer value) {
            addCriterion("TOPIC_ID >", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TOPIC_ID >=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThan(Integer value) {
            addCriterion("TOPIC_ID <", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThanOrEqualTo(Integer value) {
            addCriterion("TOPIC_ID <=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdIn(List<Integer> values) {
            addCriterion("TOPIC_ID in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotIn(List<Integer> values) {
            addCriterion("TOPIC_ID not in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdBetween(Integer value1, Integer value2) {
            addCriterion("TOPIC_ID between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TOPIC_ID not between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andLanmuNoIsNull() {
            addCriterion("LANMU_NO is null");
            return (Criteria) this;
        }

        public Criteria andLanmuNoIsNotNull() {
            addCriterion("LANMU_NO is not null");
            return (Criteria) this;
        }

        public Criteria andLanmuNoEqualTo(Integer value) {
            addCriterion("LANMU_NO =", value, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoNotEqualTo(Integer value) {
            addCriterion("LANMU_NO <>", value, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoGreaterThan(Integer value) {
            addCriterion("LANMU_NO >", value, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("LANMU_NO >=", value, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoLessThan(Integer value) {
            addCriterion("LANMU_NO <", value, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoLessThanOrEqualTo(Integer value) {
            addCriterion("LANMU_NO <=", value, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoIn(List<Integer> values) {
            addCriterion("LANMU_NO in", values, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoNotIn(List<Integer> values) {
            addCriterion("LANMU_NO not in", values, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoBetween(Integer value1, Integer value2) {
            addCriterion("LANMU_NO between", value1, value2, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andLanmuNoNotBetween(Integer value1, Integer value2) {
            addCriterion("LANMU_NO not between", value1, value2, "lanmuNo");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("TYPE_ID =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("TYPE_ID <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("TYPE_ID >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TYPE_ID >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("TYPE_ID <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("TYPE_ID <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("TYPE_ID in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("TYPE_ID not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("TYPE_ID between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TYPE_ID not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountIsNull() {
            addCriterion("ZHANSHU_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountIsNotNull() {
            addCriterion("ZHANSHU_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountEqualTo(Integer value) {
            addCriterion("ZHANSHU_COUNT =", value, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountNotEqualTo(Integer value) {
            addCriterion("ZHANSHU_COUNT <>", value, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountGreaterThan(Integer value) {
            addCriterion("ZHANSHU_COUNT >", value, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ZHANSHU_COUNT >=", value, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountLessThan(Integer value) {
            addCriterion("ZHANSHU_COUNT <", value, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountLessThanOrEqualTo(Integer value) {
            addCriterion("ZHANSHU_COUNT <=", value, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountIn(List<Integer> values) {
            addCriterion("ZHANSHU_COUNT in", values, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountNotIn(List<Integer> values) {
            addCriterion("ZHANSHU_COUNT not in", values, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountBetween(Integer value1, Integer value2) {
            addCriterion("ZHANSHU_COUNT between", value1, value2, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andZhanshuCountNotBetween(Integer value1, Integer value2) {
            addCriterion("ZHANSHU_COUNT not between", value1, value2, "zhanshuCount");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("PID is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("PID is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("PID =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("PID <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("PID >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("PID >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("PID <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("PID <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("PID in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("PID not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("PID between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("PID not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andSubAddsIsNull() {
            addCriterion("SUB_ADDS is null");
            return (Criteria) this;
        }

        public Criteria andSubAddsIsNotNull() {
            addCriterion("SUB_ADDS is not null");
            return (Criteria) this;
        }

        public Criteria andSubAddsEqualTo(String value) {
            addCriterion("SUB_ADDS =", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsNotEqualTo(String value) {
            addCriterion("SUB_ADDS <>", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsGreaterThan(String value) {
            addCriterion("SUB_ADDS >", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_ADDS >=", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsLessThan(String value) {
            addCriterion("SUB_ADDS <", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsLessThanOrEqualTo(String value) {
            addCriterion("SUB_ADDS <=", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsLike(String value) {
            addCriterion("SUB_ADDS like", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsNotLike(String value) {
            addCriterion("SUB_ADDS not like", value, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsIn(List<String> values) {
            addCriterion("SUB_ADDS in", values, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsNotIn(List<String> values) {
            addCriterion("SUB_ADDS not in", values, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsBetween(String value1, String value2) {
            addCriterion("SUB_ADDS between", value1, value2, "subAdds");
            return (Criteria) this;
        }

        public Criteria andSubAddsNotBetween(String value1, String value2) {
            addCriterion("SUB_ADDS not between", value1, value2, "subAdds");
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