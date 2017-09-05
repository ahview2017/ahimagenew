package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.List;

public class CpFlActInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpFlActInfoExample() {
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

        public Criteria andActNameIsNull() {
            addCriterion("ACT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andActNameIsNotNull() {
            addCriterion("ACT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andActNameEqualTo(String value) {
            addCriterion("ACT_NAME =", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotEqualTo(String value) {
            addCriterion("ACT_NAME <>", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameGreaterThan(String value) {
            addCriterion("ACT_NAME >", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACT_NAME >=", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameLessThan(String value) {
            addCriterion("ACT_NAME <", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameLessThanOrEqualTo(String value) {
            addCriterion("ACT_NAME <=", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameLike(String value) {
            addCriterion("ACT_NAME like", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotLike(String value) {
            addCriterion("ACT_NAME not like", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameIn(List<String> values) {
            addCriterion("ACT_NAME in", values, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotIn(List<String> values) {
            addCriterion("ACT_NAME not in", values, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameBetween(String value1, String value2) {
            addCriterion("ACT_NAME between", value1, value2, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotBetween(String value1, String value2) {
            addCriterion("ACT_NAME not between", value1, value2, "actName");
            return (Criteria) this;
        }

        public Criteria andActLinkIsNull() {
            addCriterion("ACT_LINK is null");
            return (Criteria) this;
        }

        public Criteria andActLinkIsNotNull() {
            addCriterion("ACT_LINK is not null");
            return (Criteria) this;
        }

        public Criteria andActLinkEqualTo(String value) {
            addCriterion("ACT_LINK =", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkNotEqualTo(String value) {
            addCriterion("ACT_LINK <>", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkGreaterThan(String value) {
            addCriterion("ACT_LINK >", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkGreaterThanOrEqualTo(String value) {
            addCriterion("ACT_LINK >=", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkLessThan(String value) {
            addCriterion("ACT_LINK <", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkLessThanOrEqualTo(String value) {
            addCriterion("ACT_LINK <=", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkLike(String value) {
            addCriterion("ACT_LINK like", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkNotLike(String value) {
            addCriterion("ACT_LINK not like", value, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkIn(List<String> values) {
            addCriterion("ACT_LINK in", values, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkNotIn(List<String> values) {
            addCriterion("ACT_LINK not in", values, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkBetween(String value1, String value2) {
            addCriterion("ACT_LINK between", value1, value2, "actLink");
            return (Criteria) this;
        }

        public Criteria andActLinkNotBetween(String value1, String value2) {
            addCriterion("ACT_LINK not between", value1, value2, "actLink");
            return (Criteria) this;
        }

        public Criteria andActStatusIsNull() {
            addCriterion("ACT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andActStatusIsNotNull() {
            addCriterion("ACT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andActStatusEqualTo(Integer value) {
            addCriterion("ACT_STATUS =", value, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusNotEqualTo(Integer value) {
            addCriterion("ACT_STATUS <>", value, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusGreaterThan(Integer value) {
            addCriterion("ACT_STATUS >", value, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("ACT_STATUS >=", value, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusLessThan(Integer value) {
            addCriterion("ACT_STATUS <", value, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusLessThanOrEqualTo(Integer value) {
            addCriterion("ACT_STATUS <=", value, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusIn(List<Integer> values) {
            addCriterion("ACT_STATUS in", values, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusNotIn(List<Integer> values) {
            addCriterion("ACT_STATUS not in", values, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusBetween(Integer value1, Integer value2) {
            addCriterion("ACT_STATUS between", value1, value2, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("ACT_STATUS not between", value1, value2, "actStatus");
            return (Criteria) this;
        }

        public Criteria andActSortIsNull() {
            addCriterion("ACT_SORT is null");
            return (Criteria) this;
        }

        public Criteria andActSortIsNotNull() {
            addCriterion("ACT_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andActSortEqualTo(Integer value) {
            addCriterion("ACT_SORT =", value, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortNotEqualTo(Integer value) {
            addCriterion("ACT_SORT <>", value, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortGreaterThan(Integer value) {
            addCriterion("ACT_SORT >", value, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("ACT_SORT >=", value, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortLessThan(Integer value) {
            addCriterion("ACT_SORT <", value, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortLessThanOrEqualTo(Integer value) {
            addCriterion("ACT_SORT <=", value, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortIn(List<Integer> values) {
            addCriterion("ACT_SORT in", values, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortNotIn(List<Integer> values) {
            addCriterion("ACT_SORT not in", values, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortBetween(Integer value1, Integer value2) {
            addCriterion("ACT_SORT between", value1, value2, "actSort");
            return (Criteria) this;
        }

        public Criteria andActSortNotBetween(Integer value1, Integer value2) {
            addCriterion("ACT_SORT not between", value1, value2, "actSort");
            return (Criteria) this;
        }

        public Criteria andActMemoIsNull() {
            addCriterion("ACT_MEMO is null");
            return (Criteria) this;
        }

        public Criteria andActMemoIsNotNull() {
            addCriterion("ACT_MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andActMemoEqualTo(String value) {
            addCriterion("ACT_MEMO =", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoNotEqualTo(String value) {
            addCriterion("ACT_MEMO <>", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoGreaterThan(String value) {
            addCriterion("ACT_MEMO >", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoGreaterThanOrEqualTo(String value) {
            addCriterion("ACT_MEMO >=", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoLessThan(String value) {
            addCriterion("ACT_MEMO <", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoLessThanOrEqualTo(String value) {
            addCriterion("ACT_MEMO <=", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoLike(String value) {
            addCriterion("ACT_MEMO like", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoNotLike(String value) {
            addCriterion("ACT_MEMO not like", value, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoIn(List<String> values) {
            addCriterion("ACT_MEMO in", values, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoNotIn(List<String> values) {
            addCriterion("ACT_MEMO not in", values, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoBetween(String value1, String value2) {
            addCriterion("ACT_MEMO between", value1, value2, "actMemo");
            return (Criteria) this;
        }

        public Criteria andActMemoNotBetween(String value1, String value2) {
            addCriterion("ACT_MEMO not between", value1, value2, "actMemo");
            return (Criteria) this;
        }

        public Criteria andOactIdIsNull() {
            addCriterion("OACT_ID is null");
            return (Criteria) this;
        }

        public Criteria andOactIdIsNotNull() {
            addCriterion("OACT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOactIdEqualTo(Integer value) {
            addCriterion("OACT_ID =", value, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdNotEqualTo(Integer value) {
            addCriterion("OACT_ID <>", value, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdGreaterThan(Integer value) {
            addCriterion("OACT_ID >", value, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("OACT_ID >=", value, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdLessThan(Integer value) {
            addCriterion("OACT_ID <", value, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdLessThanOrEqualTo(Integer value) {
            addCriterion("OACT_ID <=", value, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdIn(List<Integer> values) {
            addCriterion("OACT_ID in", values, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdNotIn(List<Integer> values) {
            addCriterion("OACT_ID not in", values, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdBetween(Integer value1, Integer value2) {
            addCriterion("OACT_ID between", value1, value2, "oactId");
            return (Criteria) this;
        }

        public Criteria andOactIdNotBetween(Integer value1, Integer value2) {
            addCriterion("OACT_ID not between", value1, value2, "oactId");
            return (Criteria) this;
        }

        public Criteria andLangTypeIsNull() {
            addCriterion("LANG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLangTypeIsNotNull() {
            addCriterion("LANG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLangTypeEqualTo(Byte value) {
            addCriterion("LANG_TYPE =", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeNotEqualTo(Byte value) {
            addCriterion("LANG_TYPE <>", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeGreaterThan(Byte value) {
            addCriterion("LANG_TYPE >", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("LANG_TYPE >=", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeLessThan(Byte value) {
            addCriterion("LANG_TYPE <", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeLessThanOrEqualTo(Byte value) {
            addCriterion("LANG_TYPE <=", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeIn(List<Byte> values) {
            addCriterion("LANG_TYPE in", values, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeNotIn(List<Byte> values) {
            addCriterion("LANG_TYPE not in", values, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeBetween(Byte value1, Byte value2) {
            addCriterion("LANG_TYPE between", value1, value2, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("LANG_TYPE not between", value1, value2, "langType");
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