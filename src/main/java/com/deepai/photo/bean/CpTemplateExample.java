package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.List;

public class CpTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpTemplateExample() {
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

        public Criteria andLanmuTypeIsNull() {
            addCriterion("LANMU_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeIsNotNull() {
            addCriterion("LANMU_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeEqualTo(Integer value) {
            addCriterion("LANMU_TYPE =", value, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeNotEqualTo(Integer value) {
            addCriterion("LANMU_TYPE <>", value, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeGreaterThan(Integer value) {
            addCriterion("LANMU_TYPE >", value, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("LANMU_TYPE >=", value, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeLessThan(Integer value) {
            addCriterion("LANMU_TYPE <", value, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeLessThanOrEqualTo(Integer value) {
            addCriterion("LANMU_TYPE <=", value, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeIn(List<Integer> values) {
            addCriterion("LANMU_TYPE in", values, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeNotIn(List<Integer> values) {
            addCriterion("LANMU_TYPE not in", values, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeBetween(Integer value1, Integer value2) {
            addCriterion("LANMU_TYPE between", value1, value2, "lanmuType");
            return (Criteria) this;
        }

        public Criteria andLanmuTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("LANMU_TYPE not between", value1, value2, "lanmuType");
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