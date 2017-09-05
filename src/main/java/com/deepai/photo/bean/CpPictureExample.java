package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpPictureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpPictureExample() {
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

        public Criteria andAllowSaleIsNull() {
            addCriterion("ALLOW_SALE is null");
            return (Criteria) this;
        }

        public Criteria andAllowSaleIsNotNull() {
            addCriterion("ALLOW_SALE is not null");
            return (Criteria) this;
        }

        public Criteria andAllowSaleEqualTo(Byte value) {
            addCriterion("ALLOW_SALE =", value, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleNotEqualTo(Byte value) {
            addCriterion("ALLOW_SALE <>", value, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleGreaterThan(Byte value) {
            addCriterion("ALLOW_SALE >", value, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleGreaterThanOrEqualTo(Byte value) {
            addCriterion("ALLOW_SALE >=", value, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleLessThan(Byte value) {
            addCriterion("ALLOW_SALE <", value, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleLessThanOrEqualTo(Byte value) {
            addCriterion("ALLOW_SALE <=", value, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleIn(List<Byte> values) {
            addCriterion("ALLOW_SALE in", values, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleNotIn(List<Byte> values) {
            addCriterion("ALLOW_SALE not in", values, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleBetween(Byte value1, Byte value2) {
            addCriterion("ALLOW_SALE between", value1, value2, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAllowSaleNotBetween(Byte value1, Byte value2) {
            addCriterion("ALLOW_SALE not between", value1, value2, "allowSale");
            return (Criteria) this;
        }

        public Criteria andAuditorIsNull() {
            addCriterion("AUDITOR is null");
            return (Criteria) this;
        }

        public Criteria andAuditorIsNotNull() {
            addCriterion("AUDITOR is not null");
            return (Criteria) this;
        }

        public Criteria andAuditorEqualTo(String value) {
            addCriterion("AUDITOR =", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorNotEqualTo(String value) {
            addCriterion("AUDITOR <>", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorGreaterThan(String value) {
            addCriterion("AUDITOR >", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorGreaterThanOrEqualTo(String value) {
            addCriterion("AUDITOR >=", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorLessThan(String value) {
            addCriterion("AUDITOR <", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorLessThanOrEqualTo(String value) {
            addCriterion("AUDITOR <=", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorLike(String value) {
            addCriterion("AUDITOR like", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorNotLike(String value) {
            addCriterion("AUDITOR not like", value, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorIn(List<String> values) {
            addCriterion("AUDITOR in", values, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorNotIn(List<String> values) {
            addCriterion("AUDITOR not in", values, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorBetween(String value1, String value2) {
            addCriterion("AUDITOR between", value1, value2, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuditorNotBetween(String value1, String value2) {
            addCriterion("AUDITOR not between", value1, value2, "auditor");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNull() {
            addCriterion("AUTHOR_ID is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNotNull() {
            addCriterion("AUTHOR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdEqualTo(Integer value) {
            addCriterion("AUTHOR_ID =", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotEqualTo(Integer value) {
            addCriterion("AUTHOR_ID <>", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThan(Integer value) {
            addCriterion("AUTHOR_ID >", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("AUTHOR_ID >=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThan(Integer value) {
            addCriterion("AUTHOR_ID <", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThanOrEqualTo(Integer value) {
            addCriterion("AUTHOR_ID <=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIn(List<Integer> values) {
            addCriterion("AUTHOR_ID in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotIn(List<Integer> values) {
            addCriterion("AUTHOR_ID not in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdBetween(Integer value1, Integer value2) {
            addCriterion("AUTHOR_ID between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("AUTHOR_ID not between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorNameIsNull() {
            addCriterion("AUTHOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAuthorNameIsNotNull() {
            addCriterion("AUTHOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorNameEqualTo(String value) {
            addCriterion("AUTHOR_NAME =", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotEqualTo(String value) {
            addCriterion("AUTHOR_NAME <>", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameGreaterThan(String value) {
            addCriterion("AUTHOR_NAME >", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameGreaterThanOrEqualTo(String value) {
            addCriterion("AUTHOR_NAME >=", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameLessThan(String value) {
            addCriterion("AUTHOR_NAME <", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameLessThanOrEqualTo(String value) {
            addCriterion("AUTHOR_NAME <=", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameLike(String value) {
            addCriterion("AUTHOR_NAME like", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotLike(String value) {
            addCriterion("AUTHOR_NAME not like", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameIn(List<String> values) {
            addCriterion("AUTHOR_NAME in", values, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotIn(List<String> values) {
            addCriterion("AUTHOR_NAME not in", values, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameBetween(String value1, String value2) {
            addCriterion("AUTHOR_NAME between", value1, value2, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotBetween(String value1, String value2) {
            addCriterion("AUTHOR_NAME not between", value1, value2, "authorName");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoIsNull() {
            addCriterion("CATEGORY_INFO is null");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoIsNotNull() {
            addCriterion("CATEGORY_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoEqualTo(String value) {
            addCriterion("CATEGORY_INFO =", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoNotEqualTo(String value) {
            addCriterion("CATEGORY_INFO <>", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoGreaterThan(String value) {
            addCriterion("CATEGORY_INFO >", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY_INFO >=", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoLessThan(String value) {
            addCriterion("CATEGORY_INFO <", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY_INFO <=", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoLike(String value) {
            addCriterion("CATEGORY_INFO like", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoNotLike(String value) {
            addCriterion("CATEGORY_INFO not like", value, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoIn(List<String> values) {
            addCriterion("CATEGORY_INFO in", values, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoNotIn(List<String> values) {
            addCriterion("CATEGORY_INFO not in", values, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoBetween(String value1, String value2) {
            addCriterion("CATEGORY_INFO between", value1, value2, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andCategoryInfoNotBetween(String value1, String value2) {
            addCriterion("CATEGORY_INFO not between", value1, value2, "categoryInfo");
            return (Criteria) this;
        }

        public Criteria andColorTypeIsNull() {
            addCriterion("COLOR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andColorTypeIsNotNull() {
            addCriterion("COLOR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andColorTypeEqualTo(String value) {
            addCriterion("COLOR_TYPE =", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeNotEqualTo(String value) {
            addCriterion("COLOR_TYPE <>", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeGreaterThan(String value) {
            addCriterion("COLOR_TYPE >", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeGreaterThanOrEqualTo(String value) {
            addCriterion("COLOR_TYPE >=", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeLessThan(String value) {
            addCriterion("COLOR_TYPE <", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeLessThanOrEqualTo(String value) {
            addCriterion("COLOR_TYPE <=", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeLike(String value) {
            addCriterion("COLOR_TYPE like", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeNotLike(String value) {
            addCriterion("COLOR_TYPE not like", value, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeIn(List<String> values) {
            addCriterion("COLOR_TYPE in", values, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeNotIn(List<String> values) {
            addCriterion("COLOR_TYPE not in", values, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeBetween(String value1, String value2) {
            addCriterion("COLOR_TYPE between", value1, value2, "colorType");
            return (Criteria) this;
        }

        public Criteria andColorTypeNotBetween(String value1, String value2) {
            addCriterion("COLOR_TYPE not between", value1, value2, "colorType");
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

        public Criteria andDeleteFlagIsNull() {
            addCriterion("DELETE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("DELETE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(Byte value) {
            addCriterion("DELETE_FLAG =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(Byte value) {
            addCriterion("DELETE_FLAG <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(Byte value) {
            addCriterion("DELETE_FLAG >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("DELETE_FLAG >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(Byte value) {
            addCriterion("DELETE_FLAG <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(Byte value) {
            addCriterion("DELETE_FLAG <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<Byte> values) {
            addCriterion("DELETE_FLAG in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<Byte> values) {
            addCriterion("DELETE_FLAG not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(Byte value1, Byte value2) {
            addCriterion("DELETE_FLAG between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("DELETE_FLAG not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andEditorIsNull() {
            addCriterion("EDITOR is null");
            return (Criteria) this;
        }

        public Criteria andEditorIsNotNull() {
            addCriterion("EDITOR is not null");
            return (Criteria) this;
        }

        public Criteria andEditorEqualTo(String value) {
            addCriterion("EDITOR =", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorNotEqualTo(String value) {
            addCriterion("EDITOR <>", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorGreaterThan(String value) {
            addCriterion("EDITOR >", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorGreaterThanOrEqualTo(String value) {
            addCriterion("EDITOR >=", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorLessThan(String value) {
            addCriterion("EDITOR <", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorLessThanOrEqualTo(String value) {
            addCriterion("EDITOR <=", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorLike(String value) {
            addCriterion("EDITOR like", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorNotLike(String value) {
            addCriterion("EDITOR not like", value, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorIn(List<String> values) {
            addCriterion("EDITOR in", values, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorNotIn(List<String> values) {
            addCriterion("EDITOR not in", values, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorBetween(String value1, String value2) {
            addCriterion("EDITOR between", value1, value2, "editor");
            return (Criteria) this;
        }

        public Criteria andEditorNotBetween(String value1, String value2) {
            addCriterion("EDITOR not between", value1, value2, "editor");
            return (Criteria) this;
        }

        public Criteria andExDatetimeIsNull() {
            addCriterion("EX_DATETIME is null");
            return (Criteria) this;
        }

        public Criteria andExDatetimeIsNotNull() {
            addCriterion("EX_DATETIME is not null");
            return (Criteria) this;
        }

        public Criteria andExDatetimeEqualTo(Date value) {
            addCriterion("EX_DATETIME =", value, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeNotEqualTo(Date value) {
            addCriterion("EX_DATETIME <>", value, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeGreaterThan(Date value) {
            addCriterion("EX_DATETIME >", value, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("EX_DATETIME >=", value, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeLessThan(Date value) {
            addCriterion("EX_DATETIME <", value, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("EX_DATETIME <=", value, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeIn(List<Date> values) {
            addCriterion("EX_DATETIME in", values, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeNotIn(List<Date> values) {
            addCriterion("EX_DATETIME not in", values, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeBetween(Date value1, Date value2) {
            addCriterion("EX_DATETIME between", value1, value2, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("EX_DATETIME not between", value1, value2, "exDatetime");
            return (Criteria) this;
        }

        public Criteria andExFnumberIsNull() {
            addCriterion("EX_FNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andExFnumberIsNotNull() {
            addCriterion("EX_FNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andExFnumberEqualTo(String value) {
            addCriterion("EX_FNUMBER =", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberNotEqualTo(String value) {
            addCriterion("EX_FNUMBER <>", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberGreaterThan(String value) {
            addCriterion("EX_FNUMBER >", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberGreaterThanOrEqualTo(String value) {
            addCriterion("EX_FNUMBER >=", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberLessThan(String value) {
            addCriterion("EX_FNUMBER <", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberLessThanOrEqualTo(String value) {
            addCriterion("EX_FNUMBER <=", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberLike(String value) {
            addCriterion("EX_FNUMBER like", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberNotLike(String value) {
            addCriterion("EX_FNUMBER not like", value, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberIn(List<String> values) {
            addCriterion("EX_FNUMBER in", values, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberNotIn(List<String> values) {
            addCriterion("EX_FNUMBER not in", values, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberBetween(String value1, String value2) {
            addCriterion("EX_FNUMBER between", value1, value2, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExFnumberNotBetween(String value1, String value2) {
            addCriterion("EX_FNUMBER not between", value1, value2, "exFnumber");
            return (Criteria) this;
        }

        public Criteria andExIsoIsNull() {
            addCriterion("EX_ISO is null");
            return (Criteria) this;
        }

        public Criteria andExIsoIsNotNull() {
            addCriterion("EX_ISO is not null");
            return (Criteria) this;
        }

        public Criteria andExIsoEqualTo(String value) {
            addCriterion("EX_ISO =", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoNotEqualTo(String value) {
            addCriterion("EX_ISO <>", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoGreaterThan(String value) {
            addCriterion("EX_ISO >", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoGreaterThanOrEqualTo(String value) {
            addCriterion("EX_ISO >=", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoLessThan(String value) {
            addCriterion("EX_ISO <", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoLessThanOrEqualTo(String value) {
            addCriterion("EX_ISO <=", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoLike(String value) {
            addCriterion("EX_ISO like", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoNotLike(String value) {
            addCriterion("EX_ISO not like", value, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoIn(List<String> values) {
            addCriterion("EX_ISO in", values, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoNotIn(List<String> values) {
            addCriterion("EX_ISO not in", values, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoBetween(String value1, String value2) {
            addCriterion("EX_ISO between", value1, value2, "exIso");
            return (Criteria) this;
        }

        public Criteria andExIsoNotBetween(String value1, String value2) {
            addCriterion("EX_ISO not between", value1, value2, "exIso");
            return (Criteria) this;
        }

        public Criteria andExModelIsNull() {
            addCriterion("EX_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andExModelIsNotNull() {
            addCriterion("EX_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andExModelEqualTo(String value) {
            addCriterion("EX_MODEL =", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelNotEqualTo(String value) {
            addCriterion("EX_MODEL <>", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelGreaterThan(String value) {
            addCriterion("EX_MODEL >", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelGreaterThanOrEqualTo(String value) {
            addCriterion("EX_MODEL >=", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelLessThan(String value) {
            addCriterion("EX_MODEL <", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelLessThanOrEqualTo(String value) {
            addCriterion("EX_MODEL <=", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelLike(String value) {
            addCriterion("EX_MODEL like", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelNotLike(String value) {
            addCriterion("EX_MODEL not like", value, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelIn(List<String> values) {
            addCriterion("EX_MODEL in", values, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelNotIn(List<String> values) {
            addCriterion("EX_MODEL not in", values, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelBetween(String value1, String value2) {
            addCriterion("EX_MODEL between", value1, value2, "exModel");
            return (Criteria) this;
        }

        public Criteria andExModelNotBetween(String value1, String value2) {
            addCriterion("EX_MODEL not between", value1, value2, "exModel");
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

        public Criteria andFilmTimeIsNull() {
            addCriterion("FILM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andFilmTimeIsNotNull() {
            addCriterion("FILM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andFilmTimeEqualTo(Date value) {
            addCriterion("FILM_TIME =", value, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeNotEqualTo(Date value) {
            addCriterion("FILM_TIME <>", value, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeGreaterThan(Date value) {
            addCriterion("FILM_TIME >", value, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("FILM_TIME >=", value, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeLessThan(Date value) {
            addCriterion("FILM_TIME <", value, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeLessThanOrEqualTo(Date value) {
            addCriterion("FILM_TIME <=", value, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeIn(List<Date> values) {
            addCriterion("FILM_TIME in", values, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeNotIn(List<Date> values) {
            addCriterion("FILM_TIME not in", values, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeBetween(Date value1, Date value2) {
            addCriterion("FILM_TIME between", value1, value2, "filmTime");
            return (Criteria) this;
        }

        public Criteria andFilmTimeNotBetween(Date value1, Date value2) {
            addCriterion("FILM_TIME not between", value1, value2, "filmTime");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureIsNull() {
            addCriterion("IS_HISTORY_PICTURE is null");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureIsNotNull() {
            addCriterion("IS_HISTORY_PICTURE is not null");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureEqualTo(Byte value) {
            addCriterion("IS_HISTORY_PICTURE =", value, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureNotEqualTo(Byte value) {
            addCriterion("IS_HISTORY_PICTURE <>", value, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureGreaterThan(Byte value) {
            addCriterion("IS_HISTORY_PICTURE >", value, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_HISTORY_PICTURE >=", value, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureLessThan(Byte value) {
            addCriterion("IS_HISTORY_PICTURE <", value, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureLessThanOrEqualTo(Byte value) {
            addCriterion("IS_HISTORY_PICTURE <=", value, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureIn(List<Byte> values) {
            addCriterion("IS_HISTORY_PICTURE in", values, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureNotIn(List<Byte> values) {
            addCriterion("IS_HISTORY_PICTURE not in", values, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureBetween(Byte value1, Byte value2) {
            addCriterion("IS_HISTORY_PICTURE between", value1, value2, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsHistoryPictureNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_HISTORY_PICTURE not between", value1, value2, "isHistoryPicture");
            return (Criteria) this;
        }

        public Criteria andIsIptcIsNull() {
            addCriterion("IS_IPTC is null");
            return (Criteria) this;
        }

        public Criteria andIsIptcIsNotNull() {
            addCriterion("IS_IPTC is not null");
            return (Criteria) this;
        }

        public Criteria andIsIptcEqualTo(Byte value) {
            addCriterion("IS_IPTC =", value, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcNotEqualTo(Byte value) {
            addCriterion("IS_IPTC <>", value, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcGreaterThan(Byte value) {
            addCriterion("IS_IPTC >", value, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_IPTC >=", value, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcLessThan(Byte value) {
            addCriterion("IS_IPTC <", value, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcLessThanOrEqualTo(Byte value) {
            addCriterion("IS_IPTC <=", value, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcIn(List<Byte> values) {
            addCriterion("IS_IPTC in", values, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcNotIn(List<Byte> values) {
            addCriterion("IS_IPTC not in", values, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcBetween(Byte value1, Byte value2) {
            addCriterion("IS_IPTC between", value1, value2, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsIptcNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_IPTC not between", value1, value2, "isIptc");
            return (Criteria) this;
        }

        public Criteria andIsLockedIsNull() {
            addCriterion("IS_LOCKED is null");
            return (Criteria) this;
        }

        public Criteria andIsLockedIsNotNull() {
            addCriterion("IS_LOCKED is not null");
            return (Criteria) this;
        }

        public Criteria andIsLockedEqualTo(Byte value) {
            addCriterion("IS_LOCKED =", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedNotEqualTo(Byte value) {
            addCriterion("IS_LOCKED <>", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedGreaterThan(Byte value) {
            addCriterion("IS_LOCKED >", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_LOCKED >=", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedLessThan(Byte value) {
            addCriterion("IS_LOCKED <", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedLessThanOrEqualTo(Byte value) {
            addCriterion("IS_LOCKED <=", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedIn(List<Byte> values) {
            addCriterion("IS_LOCKED in", values, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedNotIn(List<Byte> values) {
            addCriterion("IS_LOCKED not in", values, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedBetween(Byte value1, Byte value2) {
            addCriterion("IS_LOCKED between", value1, value2, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_LOCKED not between", value1, value2, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductIsNull() {
            addCriterion("IS_REFERRED_BY_PRODUCT is null");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductIsNotNull() {
            addCriterion("IS_REFERRED_BY_PRODUCT is not null");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PRODUCT =", value, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductNotEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PRODUCT <>", value, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductGreaterThan(Byte value) {
            addCriterion("IS_REFERRED_BY_PRODUCT >", value, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PRODUCT >=", value, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductLessThan(Byte value) {
            addCriterion("IS_REFERRED_BY_PRODUCT <", value, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductLessThanOrEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PRODUCT <=", value, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductIn(List<Byte> values) {
            addCriterion("IS_REFERRED_BY_PRODUCT in", values, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductNotIn(List<Byte> values) {
            addCriterion("IS_REFERRED_BY_PRODUCT not in", values, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductBetween(Byte value1, Byte value2) {
            addCriterion("IS_REFERRED_BY_PRODUCT between", value1, value2, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByProductNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_REFERRED_BY_PRODUCT not between", value1, value2, "isReferredByProduct");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishIsNull() {
            addCriterion("IS_REFERRED_BY_PUBLISH is null");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishIsNotNull() {
            addCriterion("IS_REFERRED_BY_PUBLISH is not null");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PUBLISH =", value, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishNotEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PUBLISH <>", value, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishGreaterThan(Byte value) {
            addCriterion("IS_REFERRED_BY_PUBLISH >", value, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PUBLISH >=", value, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishLessThan(Byte value) {
            addCriterion("IS_REFERRED_BY_PUBLISH <", value, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishLessThanOrEqualTo(Byte value) {
            addCriterion("IS_REFERRED_BY_PUBLISH <=", value, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishIn(List<Byte> values) {
            addCriterion("IS_REFERRED_BY_PUBLISH in", values, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishNotIn(List<Byte> values) {
            addCriterion("IS_REFERRED_BY_PUBLISH not in", values, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishBetween(Byte value1, Byte value2) {
            addCriterion("IS_REFERRED_BY_PUBLISH between", value1, value2, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsReferredByPublishNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_REFERRED_BY_PUBLISH not between", value1, value2, "isReferredByPublish");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureIsNull() {
            addCriterion("IS_SOURCE_PICTURE is null");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureIsNotNull() {
            addCriterion("IS_SOURCE_PICTURE is not null");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureEqualTo(Byte value) {
            addCriterion("IS_SOURCE_PICTURE =", value, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureNotEqualTo(Byte value) {
            addCriterion("IS_SOURCE_PICTURE <>", value, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureGreaterThan(Byte value) {
            addCriterion("IS_SOURCE_PICTURE >", value, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_SOURCE_PICTURE >=", value, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureLessThan(Byte value) {
            addCriterion("IS_SOURCE_PICTURE <", value, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureLessThanOrEqualTo(Byte value) {
            addCriterion("IS_SOURCE_PICTURE <=", value, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureIn(List<Byte> values) {
            addCriterion("IS_SOURCE_PICTURE in", values, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureNotIn(List<Byte> values) {
            addCriterion("IS_SOURCE_PICTURE not in", values, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureBetween(Byte value1, Byte value2) {
            addCriterion("IS_SOURCE_PICTURE between", value1, value2, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andIsSourcePictureNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_SOURCE_PICTURE not between", value1, value2, "isSourcePicture");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("KEYWORDS is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("KEYWORDS is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("KEYWORDS =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("KEYWORDS <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("KEYWORDS >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("KEYWORDS >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("KEYWORDS <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("KEYWORDS <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("KEYWORDS like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("KEYWORDS not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("KEYWORDS in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("KEYWORDS not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("KEYWORDS between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("KEYWORDS not between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andLockerNameIsNull() {
            addCriterion("LOCKER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLockerNameIsNotNull() {
            addCriterion("LOCKER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLockerNameEqualTo(String value) {
            addCriterion("LOCKER_NAME =", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameNotEqualTo(String value) {
            addCriterion("LOCKER_NAME <>", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameGreaterThan(String value) {
            addCriterion("LOCKER_NAME >", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameGreaterThanOrEqualTo(String value) {
            addCriterion("LOCKER_NAME >=", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameLessThan(String value) {
            addCriterion("LOCKER_NAME <", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameLessThanOrEqualTo(String value) {
            addCriterion("LOCKER_NAME <=", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameLike(String value) {
            addCriterion("LOCKER_NAME like", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameNotLike(String value) {
            addCriterion("LOCKER_NAME not like", value, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameIn(List<String> values) {
            addCriterion("LOCKER_NAME in", values, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameNotIn(List<String> values) {
            addCriterion("LOCKER_NAME not in", values, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameBetween(String value1, String value2) {
            addCriterion("LOCKER_NAME between", value1, value2, "lockerName");
            return (Criteria) this;
        }

        public Criteria andLockerNameNotBetween(String value1, String value2) {
            addCriterion("LOCKER_NAME not between", value1, value2, "lockerName");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("MEMO is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("MEMO =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("MEMO <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("MEMO >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("MEMO >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("MEMO <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("MEMO <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("MEMO like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("MEMO not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("MEMO in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("MEMO not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("MEMO between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("MEMO not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeIsNull() {
            addCriterion("NEGATIVE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeIsNotNull() {
            addCriterion("NEGATIVE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeEqualTo(Byte value) {
            addCriterion("NEGATIVE_TYPE =", value, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeNotEqualTo(Byte value) {
            addCriterion("NEGATIVE_TYPE <>", value, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeGreaterThan(Byte value) {
            addCriterion("NEGATIVE_TYPE >", value, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("NEGATIVE_TYPE >=", value, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeLessThan(Byte value) {
            addCriterion("NEGATIVE_TYPE <", value, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("NEGATIVE_TYPE <=", value, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeIn(List<Byte> values) {
            addCriterion("NEGATIVE_TYPE in", values, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeNotIn(List<Byte> values) {
            addCriterion("NEGATIVE_TYPE not in", values, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeBetween(Byte value1, Byte value2) {
            addCriterion("NEGATIVE_TYPE between", value1, value2, "negativeType");
            return (Criteria) this;
        }

        public Criteria andNegativeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("NEGATIVE_TYPE not between", value1, value2, "negativeType");
            return (Criteria) this;
        }

        public Criteria andOrientationIsNull() {
            addCriterion("ORIENTATION is null");
            return (Criteria) this;
        }

        public Criteria andOrientationIsNotNull() {
            addCriterion("ORIENTATION is not null");
            return (Criteria) this;
        }

        public Criteria andOrientationEqualTo(Byte value) {
            addCriterion("ORIENTATION =", value, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationNotEqualTo(Byte value) {
            addCriterion("ORIENTATION <>", value, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationGreaterThan(Byte value) {
            addCriterion("ORIENTATION >", value, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationGreaterThanOrEqualTo(Byte value) {
            addCriterion("ORIENTATION >=", value, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationLessThan(Byte value) {
            addCriterion("ORIENTATION <", value, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationLessThanOrEqualTo(Byte value) {
            addCriterion("ORIENTATION <=", value, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationIn(List<Byte> values) {
            addCriterion("ORIENTATION in", values, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationNotIn(List<Byte> values) {
            addCriterion("ORIENTATION not in", values, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationBetween(Byte value1, Byte value2) {
            addCriterion("ORIENTATION between", value1, value2, "orientation");
            return (Criteria) this;
        }

        public Criteria andOrientationNotBetween(Byte value1, Byte value2) {
            addCriterion("ORIENTATION not between", value1, value2, "orientation");
            return (Criteria) this;
        }

        public Criteria andPeopleIsNull() {
            addCriterion("PEOPLE is null");
            return (Criteria) this;
        }

        public Criteria andPeopleIsNotNull() {
            addCriterion("PEOPLE is not null");
            return (Criteria) this;
        }

        public Criteria andPeopleEqualTo(String value) {
            addCriterion("PEOPLE =", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleNotEqualTo(String value) {
            addCriterion("PEOPLE <>", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleGreaterThan(String value) {
            addCriterion("PEOPLE >", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleGreaterThanOrEqualTo(String value) {
            addCriterion("PEOPLE >=", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleLessThan(String value) {
            addCriterion("PEOPLE <", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleLessThanOrEqualTo(String value) {
            addCriterion("PEOPLE <=", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleLike(String value) {
            addCriterion("PEOPLE like", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleNotLike(String value) {
            addCriterion("PEOPLE not like", value, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleIn(List<String> values) {
            addCriterion("PEOPLE in", values, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleNotIn(List<String> values) {
            addCriterion("PEOPLE not in", values, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleBetween(String value1, String value2) {
            addCriterion("PEOPLE between", value1, value2, "people");
            return (Criteria) this;
        }

        public Criteria andPeopleNotBetween(String value1, String value2) {
            addCriterion("PEOPLE not between", value1, value2, "people");
            return (Criteria) this;
        }

        public Criteria andPictureHeightIsNull() {
            addCriterion("PICTURE_HEIGHT is null");
            return (Criteria) this;
        }

        public Criteria andPictureHeightIsNotNull() {
            addCriterion("PICTURE_HEIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andPictureHeightEqualTo(Integer value) {
            addCriterion("PICTURE_HEIGHT =", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightNotEqualTo(Integer value) {
            addCriterion("PICTURE_HEIGHT <>", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightGreaterThan(Integer value) {
            addCriterion("PICTURE_HEIGHT >", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_HEIGHT >=", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightLessThan(Integer value) {
            addCriterion("PICTURE_HEIGHT <", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_HEIGHT <=", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightIn(List<Integer> values) {
            addCriterion("PICTURE_HEIGHT in", values, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightNotIn(List<Integer> values) {
            addCriterion("PICTURE_HEIGHT not in", values, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_HEIGHT between", value1, value2, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_HEIGHT not between", value1, value2, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureLengthIsNull() {
            addCriterion("PICTURE_LENGTH is null");
            return (Criteria) this;
        }

        public Criteria andPictureLengthIsNotNull() {
            addCriterion("PICTURE_LENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andPictureLengthEqualTo(Integer value) {
            addCriterion("PICTURE_LENGTH =", value, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthNotEqualTo(Integer value) {
            addCriterion("PICTURE_LENGTH <>", value, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthGreaterThan(Integer value) {
            addCriterion("PICTURE_LENGTH >", value, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_LENGTH >=", value, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthLessThan(Integer value) {
            addCriterion("PICTURE_LENGTH <", value, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_LENGTH <=", value, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthIn(List<Integer> values) {
            addCriterion("PICTURE_LENGTH in", values, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthNotIn(List<Integer> values) {
            addCriterion("PICTURE_LENGTH not in", values, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_LENGTH between", value1, value2, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_LENGTH not between", value1, value2, "pictureLength");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdIsNull() {
            addCriterion("PICTURE_SOURCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdIsNotNull() {
            addCriterion("PICTURE_SOURCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdEqualTo(Integer value) {
            addCriterion("PICTURE_SOURCE_ID =", value, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdNotEqualTo(Integer value) {
            addCriterion("PICTURE_SOURCE_ID <>", value, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdGreaterThan(Integer value) {
            addCriterion("PICTURE_SOURCE_ID >", value, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_SOURCE_ID >=", value, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdLessThan(Integer value) {
            addCriterion("PICTURE_SOURCE_ID <", value, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_SOURCE_ID <=", value, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdIn(List<Integer> values) {
            addCriterion("PICTURE_SOURCE_ID in", values, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdNotIn(List<Integer> values) {
            addCriterion("PICTURE_SOURCE_ID not in", values, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_SOURCE_ID between", value1, value2, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_SOURCE_ID not between", value1, value2, "pictureSourceId");
            return (Criteria) this;
        }

        public Criteria andPictureStateIsNull() {
            addCriterion("PICTURE_STATE is null");
            return (Criteria) this;
        }

        public Criteria andPictureStateIsNotNull() {
            addCriterion("PICTURE_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andPictureStateEqualTo(Integer value) {
            addCriterion("PICTURE_STATE =", value, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateNotEqualTo(Integer value) {
            addCriterion("PICTURE_STATE <>", value, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateGreaterThan(Integer value) {
            addCriterion("PICTURE_STATE >", value, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_STATE >=", value, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateLessThan(Integer value) {
            addCriterion("PICTURE_STATE <", value, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_STATE <=", value, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateIn(List<Integer> values) {
            addCriterion("PICTURE_STATE in", values, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateNotIn(List<Integer> values) {
            addCriterion("PICTURE_STATE not in", values, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_STATE between", value1, value2, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureStateNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_STATE not between", value1, value2, "pictureState");
            return (Criteria) this;
        }

        public Criteria andPictureWidthIsNull() {
            addCriterion("PICTURE_WIDTH is null");
            return (Criteria) this;
        }

        public Criteria andPictureWidthIsNotNull() {
            addCriterion("PICTURE_WIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andPictureWidthEqualTo(Integer value) {
            addCriterion("PICTURE_WIDTH =", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthNotEqualTo(Integer value) {
            addCriterion("PICTURE_WIDTH <>", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthGreaterThan(Integer value) {
            addCriterion("PICTURE_WIDTH >", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_WIDTH >=", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthLessThan(Integer value) {
            addCriterion("PICTURE_WIDTH <", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_WIDTH <=", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthIn(List<Integer> values) {
            addCriterion("PICTURE_WIDTH in", values, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthNotIn(List<Integer> values) {
            addCriterion("PICTURE_WIDTH not in", values, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_WIDTH between", value1, value2, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_WIDTH not between", value1, value2, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPlaceIsNull() {
            addCriterion("PLACE is null");
            return (Criteria) this;
        }

        public Criteria andPlaceIsNotNull() {
            addCriterion("PLACE is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceEqualTo(String value) {
            addCriterion("PLACE =", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotEqualTo(String value) {
            addCriterion("PLACE <>", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceGreaterThan(String value) {
            addCriterion("PLACE >", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("PLACE >=", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLessThan(String value) {
            addCriterion("PLACE <", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLessThanOrEqualTo(String value) {
            addCriterion("PLACE <=", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLike(String value) {
            addCriterion("PLACE like", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotLike(String value) {
            addCriterion("PLACE not like", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceIn(List<String> values) {
            addCriterion("PLACE in", values, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotIn(List<String> values) {
            addCriterion("PLACE not in", values, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceBetween(String value1, String value2) {
            addCriterion("PLACE between", value1, value2, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotBetween(String value1, String value2) {
            addCriterion("PLACE not between", value1, value2, "place");
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

        public Criteria andProductTimeIsNull() {
            addCriterion("PRODUCT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andProductTimeIsNotNull() {
            addCriterion("PRODUCT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andProductTimeEqualTo(Date value) {
            addCriterion("PRODUCT_TIME =", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotEqualTo(Date value) {
            addCriterion("PRODUCT_TIME <>", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeGreaterThan(Date value) {
            addCriterion("PRODUCT_TIME >", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PRODUCT_TIME >=", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLessThan(Date value) {
            addCriterion("PRODUCT_TIME <", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLessThanOrEqualTo(Date value) {
            addCriterion("PRODUCT_TIME <=", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeIn(List<Date> values) {
            addCriterion("PRODUCT_TIME in", values, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotIn(List<Date> values) {
            addCriterion("PRODUCT_TIME not in", values, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeBetween(Date value1, Date value2) {
            addCriterion("PRODUCT_TIME between", value1, value2, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotBetween(Date value1, Date value2) {
            addCriterion("PRODUCT_TIME not between", value1, value2, "productTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIsNull() {
            addCriterion("PUBLISH_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIsNotNull() {
            addCriterion("PUBLISH_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPublishTimeEqualTo(Date value) {
            addCriterion("PUBLISH_TIME =", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotEqualTo(Date value) {
            addCriterion("PUBLISH_TIME <>", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeGreaterThan(Date value) {
            addCriterion("PUBLISH_TIME >", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PUBLISH_TIME >=", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeLessThan(Date value) {
            addCriterion("PUBLISH_TIME <", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeLessThanOrEqualTo(Date value) {
            addCriterion("PUBLISH_TIME <=", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIn(List<Date> values) {
            addCriterion("PUBLISH_TIME in", values, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotIn(List<Date> values) {
            addCriterion("PUBLISH_TIME not in", values, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeBetween(Date value1, Date value2) {
            addCriterion("PUBLISH_TIME between", value1, value2, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotBetween(Date value1, Date value2) {
            addCriterion("PUBLISH_TIME not between", value1, value2, "publishTime");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeIsNull() {
            addCriterion("SECURITY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeIsNotNull() {
            addCriterion("SECURITY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeEqualTo(Byte value) {
            addCriterion("SECURITY_TYPE =", value, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeNotEqualTo(Byte value) {
            addCriterion("SECURITY_TYPE <>", value, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeGreaterThan(Byte value) {
            addCriterion("SECURITY_TYPE >", value, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("SECURITY_TYPE >=", value, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeLessThan(Byte value) {
            addCriterion("SECURITY_TYPE <", value, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeLessThanOrEqualTo(Byte value) {
            addCriterion("SECURITY_TYPE <=", value, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeIn(List<Byte> values) {
            addCriterion("SECURITY_TYPE in", values, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeNotIn(List<Byte> values) {
            addCriterion("SECURITY_TYPE not in", values, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeBetween(Byte value1, Byte value2) {
            addCriterion("SECURITY_TYPE between", value1, value2, "securityType");
            return (Criteria) this;
        }

        public Criteria andSecurityTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("SECURITY_TYPE not between", value1, value2, "securityType");
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

        public Criteria andSourceIdIsNull() {
            addCriterion("SOURCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("SOURCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Integer value) {
            addCriterion("SOURCE_ID =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Integer value) {
            addCriterion("SOURCE_ID <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Integer value) {
            addCriterion("SOURCE_ID >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_ID >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Integer value) {
            addCriterion("SOURCE_ID <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_ID <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Integer> values) {
            addCriterion("SOURCE_ID in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Integer> values) {
            addCriterion("SOURCE_ID not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_ID between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_ID not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdIsNull() {
            addCriterion("SOURCE_PICTURE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdIsNotNull() {
            addCriterion("SOURCE_PICTURE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_ID =", value, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdNotEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_ID <>", value, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdGreaterThan(Integer value) {
            addCriterion("SOURCE_PICTURE_ID >", value, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_ID >=", value, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdLessThan(Integer value) {
            addCriterion("SOURCE_PICTURE_ID <", value, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdLessThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_ID <=", value, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdIn(List<Integer> values) {
            addCriterion("SOURCE_PICTURE_ID in", values, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdNotIn(List<Integer> values) {
            addCriterion("SOURCE_PICTURE_ID not in", values, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_PICTURE_ID between", value1, value2, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_PICTURE_ID not between", value1, value2, "sourcePictureId");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthIsNull() {
            addCriterion("SOURCE_PICTURE_LENGTH is null");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthIsNotNull() {
            addCriterion("SOURCE_PICTURE_LENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_LENGTH =", value, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthNotEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_LENGTH <>", value, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthGreaterThan(Integer value) {
            addCriterion("SOURCE_PICTURE_LENGTH >", value, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_LENGTH >=", value, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthLessThan(Integer value) {
            addCriterion("SOURCE_PICTURE_LENGTH <", value, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthLessThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_PICTURE_LENGTH <=", value, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthIn(List<Integer> values) {
            addCriterion("SOURCE_PICTURE_LENGTH in", values, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthNotIn(List<Integer> values) {
            addCriterion("SOURCE_PICTURE_LENGTH not in", values, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_PICTURE_LENGTH between", value1, value2, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_PICTURE_LENGTH not between", value1, value2, "sourcePictureLength");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameIsNull() {
            addCriterion("SOURCE_PICTURE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameIsNotNull() {
            addCriterion("SOURCE_PICTURE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameEqualTo(String value) {
            addCriterion("SOURCE_PICTURE_NAME =", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameNotEqualTo(String value) {
            addCriterion("SOURCE_PICTURE_NAME <>", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameGreaterThan(String value) {
            addCriterion("SOURCE_PICTURE_NAME >", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_PICTURE_NAME >=", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameLessThan(String value) {
            addCriterion("SOURCE_PICTURE_NAME <", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_PICTURE_NAME <=", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameLike(String value) {
            addCriterion("SOURCE_PICTURE_NAME like", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameNotLike(String value) {
            addCriterion("SOURCE_PICTURE_NAME not like", value, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameIn(List<String> values) {
            addCriterion("SOURCE_PICTURE_NAME in", values, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameNotIn(List<String> values) {
            addCriterion("SOURCE_PICTURE_NAME not in", values, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameBetween(String value1, String value2) {
            addCriterion("SOURCE_PICTURE_NAME between", value1, value2, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andSourcePictureNameNotBetween(String value1, String value2) {
            addCriterion("SOURCE_PICTURE_NAME not between", value1, value2, "sourcePictureName");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("TITLE is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("TITLE =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("TITLE <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("TITLE >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("TITLE >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("TITLE <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("TITLE <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("TITLE like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("TITLE not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("TITLE in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("TITLE not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("TITLE between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("TITLE not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andUploaderIsNull() {
            addCriterion("UPLOADER is null");
            return (Criteria) this;
        }

        public Criteria andUploaderIsNotNull() {
            addCriterion("UPLOADER is not null");
            return (Criteria) this;
        }

        public Criteria andUploaderEqualTo(String value) {
            addCriterion("UPLOADER =", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderNotEqualTo(String value) {
            addCriterion("UPLOADER <>", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderGreaterThan(String value) {
            addCriterion("UPLOADER >", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOADER >=", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderLessThan(String value) {
            addCriterion("UPLOADER <", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderLessThanOrEqualTo(String value) {
            addCriterion("UPLOADER <=", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderLike(String value) {
            addCriterion("UPLOADER like", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderNotLike(String value) {
            addCriterion("UPLOADER not like", value, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderIn(List<String> values) {
            addCriterion("UPLOADER in", values, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderNotIn(List<String> values) {
            addCriterion("UPLOADER not in", values, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderBetween(String value1, String value2) {
            addCriterion("UPLOADER between", value1, value2, "uploader");
            return (Criteria) this;
        }

        public Criteria andUploaderNotBetween(String value1, String value2) {
            addCriterion("UPLOADER not between", value1, value2, "uploader");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeIsNull() {
            addCriterion("EX_EXPOSURE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeIsNotNull() {
            addCriterion("EX_EXPOSURE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeEqualTo(String value) {
            addCriterion("EX_EXPOSURE_TIME =", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeNotEqualTo(String value) {
            addCriterion("EX_EXPOSURE_TIME <>", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeGreaterThan(String value) {
            addCriterion("EX_EXPOSURE_TIME >", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeGreaterThanOrEqualTo(String value) {
            addCriterion("EX_EXPOSURE_TIME >=", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeLessThan(String value) {
            addCriterion("EX_EXPOSURE_TIME <", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeLessThanOrEqualTo(String value) {
            addCriterion("EX_EXPOSURE_TIME <=", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeLike(String value) {
            addCriterion("EX_EXPOSURE_TIME like", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeNotLike(String value) {
            addCriterion("EX_EXPOSURE_TIME not like", value, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeIn(List<String> values) {
            addCriterion("EX_EXPOSURE_TIME in", values, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeNotIn(List<String> values) {
            addCriterion("EX_EXPOSURE_TIME not in", values, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeBetween(String value1, String value2) {
            addCriterion("EX_EXPOSURE_TIME between", value1, value2, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andExExposureTimeNotBetween(String value1, String value2) {
            addCriterion("EX_EXPOSURE_TIME not between", value1, value2, "exExposureTime");
            return (Criteria) this;
        }

        public Criteria andUploadProgressIsNull() {
            addCriterion("UPLOAD_PROGRESS is null");
            return (Criteria) this;
        }

        public Criteria andUploadProgressIsNotNull() {
            addCriterion("UPLOAD_PROGRESS is not null");
            return (Criteria) this;
        }

        public Criteria andUploadProgressEqualTo(Integer value) {
            addCriterion("UPLOAD_PROGRESS =", value, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressNotEqualTo(Integer value) {
            addCriterion("UPLOAD_PROGRESS <>", value, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressGreaterThan(Integer value) {
            addCriterion("UPLOAD_PROGRESS >", value, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressGreaterThanOrEqualTo(Integer value) {
            addCriterion("UPLOAD_PROGRESS >=", value, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressLessThan(Integer value) {
            addCriterion("UPLOAD_PROGRESS <", value, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressLessThanOrEqualTo(Integer value) {
            addCriterion("UPLOAD_PROGRESS <=", value, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressIn(List<Integer> values) {
            addCriterion("UPLOAD_PROGRESS in", values, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressNotIn(List<Integer> values) {
            addCriterion("UPLOAD_PROGRESS not in", values, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressBetween(Integer value1, Integer value2) {
            addCriterion("UPLOAD_PROGRESS between", value1, value2, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andUploadProgressNotBetween(Integer value1, Integer value2) {
            addCriterion("UPLOAD_PROGRESS not between", value1, value2, "uploadProgress");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNull() {
            addCriterion("GOODS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNotNull() {
            addCriterion("GOODS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeEqualTo(String value) {
            addCriterion("GOODS_TYPE =", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotEqualTo(String value) {
            addCriterion("GOODS_TYPE <>", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThan(String value) {
            addCriterion("GOODS_TYPE >", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("GOODS_TYPE >=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThan(String value) {
            addCriterion("GOODS_TYPE <", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThanOrEqualTo(String value) {
            addCriterion("GOODS_TYPE <=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLike(String value) {
            addCriterion("GOODS_TYPE like", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotLike(String value) {
            addCriterion("GOODS_TYPE not like", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIn(List<String> values) {
            addCriterion("GOODS_TYPE in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotIn(List<String> values) {
            addCriterion("GOODS_TYPE not in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeBetween(String value1, String value2) {
            addCriterion("GOODS_TYPE between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotBetween(String value1, String value2) {
            addCriterion("GOODS_TYPE not between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceIsNull() {
            addCriterion("DISCOUNT_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceIsNotNull() {
            addCriterion("DISCOUNT_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_PRICE =", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceNotEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_PRICE <>", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceGreaterThan(BigDecimal value) {
            addCriterion("DISCOUNT_PRICE >", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_PRICE >=", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceLessThan(BigDecimal value) {
            addCriterion("DISCOUNT_PRICE <", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_PRICE <=", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceIn(List<BigDecimal> values) {
            addCriterion("DISCOUNT_PRICE in", values, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceNotIn(List<BigDecimal> values) {
            addCriterion("DISCOUNT_PRICE not in", values, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DISCOUNT_PRICE between", value1, value2, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DISCOUNT_PRICE not between", value1, value2, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceIsNull() {
            addCriterion("CAL_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andCalPriceIsNotNull() {
            addCriterion("CAL_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andCalPriceEqualTo(BigDecimal value) {
            addCriterion("CAL_PRICE =", value, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceNotEqualTo(BigDecimal value) {
            addCriterion("CAL_PRICE <>", value, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceGreaterThan(BigDecimal value) {
            addCriterion("CAL_PRICE >", value, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CAL_PRICE >=", value, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceLessThan(BigDecimal value) {
            addCriterion("CAL_PRICE <", value, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CAL_PRICE <=", value, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceIn(List<BigDecimal> values) {
            addCriterion("CAL_PRICE in", values, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceNotIn(List<BigDecimal> values) {
            addCriterion("CAL_PRICE not in", values, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CAL_PRICE between", value1, value2, "calPrice");
            return (Criteria) this;
        }

        public Criteria andCalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CAL_PRICE not between", value1, value2, "calPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountRateIsNull() {
            addCriterion("DISCOUNT_RATE is null");
            return (Criteria) this;
        }

        public Criteria andDiscountRateIsNotNull() {
            addCriterion("DISCOUNT_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountRateEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_RATE =", value, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateNotEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_RATE <>", value, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateGreaterThan(BigDecimal value) {
            addCriterion("DISCOUNT_RATE >", value, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_RATE >=", value, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateLessThan(BigDecimal value) {
            addCriterion("DISCOUNT_RATE <", value, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DISCOUNT_RATE <=", value, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateIn(List<BigDecimal> values) {
            addCriterion("DISCOUNT_RATE in", values, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateNotIn(List<BigDecimal> values) {
            addCriterion("DISCOUNT_RATE not in", values, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DISCOUNT_RATE between", value1, value2, "discountRate");
            return (Criteria) this;
        }

        public Criteria andDiscountRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DISCOUNT_RATE not between", value1, value2, "discountRate");
            return (Criteria) this;
        }

        public Criteria andForumsInfoIsNull() {
            addCriterion("FORUMS_INFO is null");
            return (Criteria) this;
        }

        public Criteria andForumsInfoIsNotNull() {
            addCriterion("FORUMS_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andForumsInfoEqualTo(String value) {
            addCriterion("FORUMS_INFO =", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoNotEqualTo(String value) {
            addCriterion("FORUMS_INFO <>", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoGreaterThan(String value) {
            addCriterion("FORUMS_INFO >", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoGreaterThanOrEqualTo(String value) {
            addCriterion("FORUMS_INFO >=", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoLessThan(String value) {
            addCriterion("FORUMS_INFO <", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoLessThanOrEqualTo(String value) {
            addCriterion("FORUMS_INFO <=", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoLike(String value) {
            addCriterion("FORUMS_INFO like", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoNotLike(String value) {
            addCriterion("FORUMS_INFO not like", value, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoIn(List<String> values) {
            addCriterion("FORUMS_INFO in", values, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoNotIn(List<String> values) {
            addCriterion("FORUMS_INFO not in", values, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoBetween(String value1, String value2) {
            addCriterion("FORUMS_INFO between", value1, value2, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsInfoNotBetween(String value1, String value2) {
            addCriterion("FORUMS_INFO not between", value1, value2, "forumsInfo");
            return (Criteria) this;
        }

        public Criteria andForumsTimeIsNull() {
            addCriterion("FORUMS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andForumsTimeIsNotNull() {
            addCriterion("FORUMS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andForumsTimeEqualTo(Date value) {
            addCriterion("FORUMS_TIME =", value, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeNotEqualTo(Date value) {
            addCriterion("FORUMS_TIME <>", value, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeGreaterThan(Date value) {
            addCriterion("FORUMS_TIME >", value, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("FORUMS_TIME >=", value, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeLessThan(Date value) {
            addCriterion("FORUMS_TIME <", value, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeLessThanOrEqualTo(Date value) {
            addCriterion("FORUMS_TIME <=", value, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeIn(List<Date> values) {
            addCriterion("FORUMS_TIME in", values, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeNotIn(List<Date> values) {
            addCriterion("FORUMS_TIME not in", values, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeBetween(Date value1, Date value2) {
            addCriterion("FORUMS_TIME between", value1, value2, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andForumsTimeNotBetween(Date value1, Date value2) {
            addCriterion("FORUMS_TIME not between", value1, value2, "forumsTime");
            return (Criteria) this;
        }

        public Criteria andReducePriceIsNull() {
            addCriterion("REDUCE_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andReducePriceIsNotNull() {
            addCriterion("REDUCE_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andReducePriceEqualTo(BigDecimal value) {
            addCriterion("REDUCE_PRICE =", value, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceNotEqualTo(BigDecimal value) {
            addCriterion("REDUCE_PRICE <>", value, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceGreaterThan(BigDecimal value) {
            addCriterion("REDUCE_PRICE >", value, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REDUCE_PRICE >=", value, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceLessThan(BigDecimal value) {
            addCriterion("REDUCE_PRICE <", value, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REDUCE_PRICE <=", value, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceIn(List<BigDecimal> values) {
            addCriterion("REDUCE_PRICE in", values, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceNotIn(List<BigDecimal> values) {
            addCriterion("REDUCE_PRICE not in", values, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REDUCE_PRICE between", value1, value2, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andReducePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REDUCE_PRICE not between", value1, value2, "reducePrice");
            return (Criteria) this;
        }

        public Criteria andIsCoverIsNull() {
            addCriterion("IS_COVER is null");
            return (Criteria) this;
        }

        public Criteria andIsCoverIsNotNull() {
            addCriterion("IS_COVER is not null");
            return (Criteria) this;
        }

        public Criteria andIsCoverEqualTo(Integer value) {
            addCriterion("IS_COVER =", value, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverNotEqualTo(Integer value) {
            addCriterion("IS_COVER <>", value, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverGreaterThan(Integer value) {
            addCriterion("IS_COVER >", value, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_COVER >=", value, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverLessThan(Integer value) {
            addCriterion("IS_COVER <", value, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverLessThanOrEqualTo(Integer value) {
            addCriterion("IS_COVER <=", value, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverIn(List<Integer> values) {
            addCriterion("IS_COVER in", values, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverNotIn(List<Integer> values) {
            addCriterion("IS_COVER not in", values, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverBetween(Integer value1, Integer value2) {
            addCriterion("IS_COVER between", value1, value2, "isCover");
            return (Criteria) this;
        }

        public Criteria andIsCoverNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_COVER not between", value1, value2, "isCover");
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

        public Criteria andSortIdIsNull() {
            addCriterion("SORT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSortIdIsNotNull() {
            addCriterion("SORT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSortIdEqualTo(Integer value) {
            addCriterion("SORT_ID =", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdNotEqualTo(Integer value) {
            addCriterion("SORT_ID <>", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdGreaterThan(Integer value) {
            addCriterion("SORT_ID >", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORT_ID >=", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdLessThan(Integer value) {
            addCriterion("SORT_ID <", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdLessThanOrEqualTo(Integer value) {
            addCriterion("SORT_ID <=", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdIn(List<Integer> values) {
            addCriterion("SORT_ID in", values, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdNotIn(List<Integer> values) {
            addCriterion("SORT_ID not in", values, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdBetween(Integer value1, Integer value2) {
            addCriterion("SORT_ID between", value1, value2, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SORT_ID not between", value1, value2, "sortId");
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