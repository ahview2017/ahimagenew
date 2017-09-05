package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpPicGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpPicGroupExample() {
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

        public Criteria andAuthorIsNull() {
            addCriterion("AUTHOR is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNotNull() {
            addCriterion("AUTHOR is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorEqualTo(String value) {
            addCriterion("AUTHOR =", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotEqualTo(String value) {
            addCriterion("AUTHOR <>", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThan(String value) {
            addCriterion("AUTHOR >", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("AUTHOR >=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThan(String value) {
            addCriterion("AUTHOR <", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThanOrEqualTo(String value) {
            addCriterion("AUTHOR <=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLike(String value) {
            addCriterion("AUTHOR like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotLike(String value) {
            addCriterion("AUTHOR not like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorIn(List<String> values) {
            addCriterion("AUTHOR in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotIn(List<String> values) {
            addCriterion("AUTHOR not in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorBetween(String value1, String value2) {
            addCriterion("AUTHOR between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotBetween(String value1, String value2) {
            addCriterion("AUTHOR not between", value1, value2, "author");
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

        public Criteria andCoverPictureIdIsNull() {
            addCriterion("COVER_PICTURE_ID is null");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdIsNotNull() {
            addCriterion("COVER_PICTURE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdEqualTo(Integer value) {
            addCriterion("COVER_PICTURE_ID =", value, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdNotEqualTo(Integer value) {
            addCriterion("COVER_PICTURE_ID <>", value, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdGreaterThan(Integer value) {
            addCriterion("COVER_PICTURE_ID >", value, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("COVER_PICTURE_ID >=", value, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdLessThan(Integer value) {
            addCriterion("COVER_PICTURE_ID <", value, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdLessThanOrEqualTo(Integer value) {
            addCriterion("COVER_PICTURE_ID <=", value, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdIn(List<Integer> values) {
            addCriterion("COVER_PICTURE_ID in", values, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdNotIn(List<Integer> values) {
            addCriterion("COVER_PICTURE_ID not in", values, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdBetween(Integer value1, Integer value2) {
            addCriterion("COVER_PICTURE_ID between", value1, value2, "coverPictureId");
            return (Criteria) this;
        }

        public Criteria andCoverPictureIdNotBetween(Integer value1, Integer value2) {
            addCriterion("COVER_PICTURE_ID not between", value1, value2, "coverPictureId");
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

        public Criteria andApplyTimeIsNull() {
            addCriterion("APPLY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("APPLY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("APPLY_TIME =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("APPLY_TIME <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("APPLY_TIME >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("APPLY_TIME >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("APPLY_TIME <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("APPLY_TIME <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("APPLY_TIME in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("APPLY_TIME not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("APPLY_TIME between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("APPLY_TIME not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("CREATOR is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("CREATOR is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("CREATOR =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("CREATOR <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("CREATOR >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("CREATOR >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("CREATOR <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("CREATOR <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("CREATOR like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("CREATOR not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("CREATOR in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("CREATOR not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("CREATOR between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("CREATOR not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("CREATOR_ID is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("CREATOR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Integer value) {
            addCriterion("CREATOR_ID =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Integer value) {
            addCriterion("CREATOR_ID <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Integer value) {
            addCriterion("CREATOR_ID >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("CREATOR_ID >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Integer value) {
            addCriterion("CREATOR_ID <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Integer value) {
            addCriterion("CREATOR_ID <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Integer> values) {
            addCriterion("CREATOR_ID in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Integer> values) {
            addCriterion("CREATOR_ID not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Integer value1, Integer value2) {
            addCriterion("CREATOR_ID between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("CREATOR_ID not between", value1, value2, "creatorId");
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

        public Criteria andPictureCountIsNull() {
            addCriterion("PICTURE_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPictureCountIsNotNull() {
            addCriterion("PICTURE_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPictureCountEqualTo(Integer value) {
            addCriterion("PICTURE_COUNT =", value, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountNotEqualTo(Integer value) {
            addCriterion("PICTURE_COUNT <>", value, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountGreaterThan(Integer value) {
            addCriterion("PICTURE_COUNT >", value, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_COUNT >=", value, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountLessThan(Integer value) {
            addCriterion("PICTURE_COUNT <", value, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_COUNT <=", value, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountIn(List<Integer> values) {
            addCriterion("PICTURE_COUNT in", values, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountNotIn(List<Integer> values) {
            addCriterion("PICTURE_COUNT not in", values, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_COUNT between", value1, value2, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureCountNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_COUNT not between", value1, value2, "pictureCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountIsNull() {
            addCriterion("PICTURE_DELETED_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountIsNotNull() {
            addCriterion("PICTURE_DELETED_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountEqualTo(Integer value) {
            addCriterion("PICTURE_DELETED_COUNT =", value, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountNotEqualTo(Integer value) {
            addCriterion("PICTURE_DELETED_COUNT <>", value, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountGreaterThan(Integer value) {
            addCriterion("PICTURE_DELETED_COUNT >", value, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_DELETED_COUNT >=", value, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountLessThan(Integer value) {
            addCriterion("PICTURE_DELETED_COUNT <", value, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_DELETED_COUNT <=", value, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountIn(List<Integer> values) {
            addCriterion("PICTURE_DELETED_COUNT in", values, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountNotIn(List<Integer> values) {
            addCriterion("PICTURE_DELETED_COUNT not in", values, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_DELETED_COUNT between", value1, value2, "pictureDeletedCount");
            return (Criteria) this;
        }

        public Criteria andPictureDeletedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_DELETED_COUNT not between", value1, value2, "pictureDeletedCount");
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

        public Criteria andFileTimeIsNull() {
            addCriterion("FILE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andFileTimeIsNotNull() {
            addCriterion("FILE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andFileTimeEqualTo(Date value) {
            addCriterion("FILE_TIME =", value, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeNotEqualTo(Date value) {
            addCriterion("FILE_TIME <>", value, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeGreaterThan(Date value) {
            addCriterion("FILE_TIME >", value, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("FILE_TIME >=", value, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeLessThan(Date value) {
            addCriterion("FILE_TIME <", value, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeLessThanOrEqualTo(Date value) {
            addCriterion("FILE_TIME <=", value, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeIn(List<Date> values) {
            addCriterion("FILE_TIME in", values, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeNotIn(List<Date> values) {
            addCriterion("FILE_TIME not in", values, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeBetween(Date value1, Date value2) {
            addCriterion("FILE_TIME between", value1, value2, "fileTime");
            return (Criteria) this;
        }

        public Criteria andFileTimeNotBetween(Date value1, Date value2) {
            addCriterion("FILE_TIME not between", value1, value2, "fileTime");
            return (Criteria) this;
        }

        public Criteria andPropertiesIsNull() {
            addCriterion("PROPERTIES is null");
            return (Criteria) this;
        }

        public Criteria andPropertiesIsNotNull() {
            addCriterion("PROPERTIES is not null");
            return (Criteria) this;
        }

        public Criteria andPropertiesEqualTo(Byte value) {
            addCriterion("PROPERTIES =", value, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesNotEqualTo(Byte value) {
            addCriterion("PROPERTIES <>", value, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesGreaterThan(Byte value) {
            addCriterion("PROPERTIES >", value, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesGreaterThanOrEqualTo(Byte value) {
            addCriterion("PROPERTIES >=", value, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesLessThan(Byte value) {
            addCriterion("PROPERTIES <", value, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesLessThanOrEqualTo(Byte value) {
            addCriterion("PROPERTIES <=", value, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesIn(List<Byte> values) {
            addCriterion("PROPERTIES in", values, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesNotIn(List<Byte> values) {
            addCriterion("PROPERTIES not in", values, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesBetween(Byte value1, Byte value2) {
            addCriterion("PROPERTIES between", value1, value2, "properties");
            return (Criteria) this;
        }

        public Criteria andPropertiesNotBetween(Byte value1, Byte value2) {
            addCriterion("PROPERTIES not between", value1, value2, "properties");
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

        public Criteria andGroupStatusIsNull() {
            addCriterion("GROUP_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNotNull() {
            addCriterion("GROUP_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusEqualTo(Integer value) {
            addCriterion("GROUP_STATUS =", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotEqualTo(Integer value) {
            addCriterion("GROUP_STATUS <>", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThan(Integer value) {
            addCriterion("GROUP_STATUS >", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("GROUP_STATUS >=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThan(Integer value) {
            addCriterion("GROUP_STATUS <", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThanOrEqualTo(Integer value) {
            addCriterion("GROUP_STATUS <=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIn(List<Integer> values) {
            addCriterion("GROUP_STATUS in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotIn(List<Integer> values) {
            addCriterion("GROUP_STATUS not in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusBetween(Integer value1, Integer value2) {
            addCriterion("GROUP_STATUS between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("GROUP_STATUS not between", value1, value2, "groupStatus");
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

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("TYPE not between", value1, value2, "type");
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

        public Criteria andIsLockedEqualTo(Integer value) {
            addCriterion("IS_LOCKED =", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedNotEqualTo(Integer value) {
            addCriterion("IS_LOCKED <>", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedGreaterThan(Integer value) {
            addCriterion("IS_LOCKED >", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_LOCKED >=", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedLessThan(Integer value) {
            addCriterion("IS_LOCKED <", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedLessThanOrEqualTo(Integer value) {
            addCriterion("IS_LOCKED <=", value, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedIn(List<Integer> values) {
            addCriterion("IS_LOCKED in", values, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedNotIn(List<Integer> values) {
            addCriterion("IS_LOCKED not in", values, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedBetween(Integer value1, Integer value2) {
            addCriterion("IS_LOCKED between", value1, value2, "isLocked");
            return (Criteria) this;
        }

        public Criteria andIsLockedNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_LOCKED not between", value1, value2, "isLocked");
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

        public Criteria andUnlockNameIsNull() {
            addCriterion("UNLOCK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUnlockNameIsNotNull() {
            addCriterion("UNLOCK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUnlockNameEqualTo(String value) {
            addCriterion("UNLOCK_NAME =", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameNotEqualTo(String value) {
            addCriterion("UNLOCK_NAME <>", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameGreaterThan(String value) {
            addCriterion("UNLOCK_NAME >", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameGreaterThanOrEqualTo(String value) {
            addCriterion("UNLOCK_NAME >=", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameLessThan(String value) {
            addCriterion("UNLOCK_NAME <", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameLessThanOrEqualTo(String value) {
            addCriterion("UNLOCK_NAME <=", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameLike(String value) {
            addCriterion("UNLOCK_NAME like", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameNotLike(String value) {
            addCriterion("UNLOCK_NAME not like", value, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameIn(List<String> values) {
            addCriterion("UNLOCK_NAME in", values, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameNotIn(List<String> values) {
            addCriterion("UNLOCK_NAME not in", values, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameBetween(String value1, String value2) {
            addCriterion("UNLOCK_NAME between", value1, value2, "unlockName");
            return (Criteria) this;
        }

        public Criteria andUnlockNameNotBetween(String value1, String value2) {
            addCriterion("UNLOCK_NAME not between", value1, value2, "unlockName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameIsNull() {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameIsNotNull() {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameEqualTo(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME =", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameNotEqualTo(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME <>", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameGreaterThan(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME >", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameGreaterThanOrEqualTo(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME >=", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameLessThan(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME <", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameLessThanOrEqualTo(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME <=", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameLike(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME like", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameNotLike(String value) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME not like", value, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameIn(List<String> values) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME in", values, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameNotIn(List<String> values) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME not in", values, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameBetween(String value1, String value2) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME between", value1, value2, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTruckAuthorTrueNameNotBetween(String value1, String value2) {
            addCriterion("TRUCK_AUTHOR_TRUE_NAME not between", value1, value2, "truckAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameIsNull() {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameIsNotNull() {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameEqualTo(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME =", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameNotEqualTo(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME <>", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameGreaterThan(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME >", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameGreaterThanOrEqualTo(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME >=", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameLessThan(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME <", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameLessThanOrEqualTo(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME <=", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameLike(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME like", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameNotLike(String value) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME not like", value, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameIn(List<String> values) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME in", values, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameNotIn(List<String> values) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME not in", values, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameBetween(String value1, String value2) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME between", value1, value2, "trucksAuthorTrueName");
            return (Criteria) this;
        }

        public Criteria andTrucksAuthorTrueNameNotBetween(String value1, String value2) {
            addCriterion("TRUCKS_AUTHOR_TRUE_NAME not between", value1, value2, "trucksAuthorTrueName");
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

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceIsNull() {
            addCriterion("CAL_GROUP_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceIsNotNull() {
            addCriterion("CAL_GROUP_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceEqualTo(String value) {
            addCriterion("CAL_GROUP_PRICE =", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceNotEqualTo(String value) {
            addCriterion("CAL_GROUP_PRICE <>", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceGreaterThan(String value) {
            addCriterion("CAL_GROUP_PRICE >", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceGreaterThanOrEqualTo(String value) {
            addCriterion("CAL_GROUP_PRICE >=", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceLessThan(String value) {
            addCriterion("CAL_GROUP_PRICE <", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceLessThanOrEqualTo(String value) {
            addCriterion("CAL_GROUP_PRICE <=", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceLike(String value) {
            addCriterion("CAL_GROUP_PRICE like", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceNotLike(String value) {
            addCriterion("CAL_GROUP_PRICE not like", value, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceIn(List<String> values) {
            addCriterion("CAL_GROUP_PRICE in", values, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceNotIn(List<String> values) {
            addCriterion("CAL_GROUP_PRICE not in", values, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceBetween(String value1, String value2) {
            addCriterion("CAL_GROUP_PRICE between", value1, value2, "calGroupPrice");
            return (Criteria) this;
        }

        public Criteria andCalGroupPriceNotBetween(String value1, String value2) {
            addCriterion("CAL_GROUP_PRICE not between", value1, value2, "calGroupPrice");
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

        public Criteria andGoodsTypeIsNull() {
            addCriterion("GOODS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNotNull() {
            addCriterion("GOODS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeEqualTo(Integer value) {
            addCriterion("GOODS_TYPE =", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotEqualTo(Integer value) {
            addCriterion("GOODS_TYPE <>", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThan(Integer value) {
            addCriterion("GOODS_TYPE >", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("GOODS_TYPE >=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThan(Integer value) {
            addCriterion("GOODS_TYPE <", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThanOrEqualTo(Integer value) {
            addCriterion("GOODS_TYPE <=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIn(List<Integer> values) {
            addCriterion("GOODS_TYPE in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotIn(List<Integer> values) {
            addCriterion("GOODS_TYPE not in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeBetween(Integer value1, Integer value2) {
            addCriterion("GOODS_TYPE between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("GOODS_TYPE not between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeIsNull() {
            addCriterion("PRICE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPriceTypeIsNotNull() {
            addCriterion("PRICE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceTypeEqualTo(Integer value) {
            addCriterion("PRICE_TYPE =", value, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeNotEqualTo(Integer value) {
            addCriterion("PRICE_TYPE <>", value, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeGreaterThan(Integer value) {
            addCriterion("PRICE_TYPE >", value, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRICE_TYPE >=", value, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeLessThan(Integer value) {
            addCriterion("PRICE_TYPE <", value, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PRICE_TYPE <=", value, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeIn(List<Integer> values) {
            addCriterion("PRICE_TYPE in", values, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeNotIn(List<Integer> values) {
            addCriterion("PRICE_TYPE not in", values, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeBetween(Integer value1, Integer value2) {
            addCriterion("PRICE_TYPE between", value1, value2, "priceType");
            return (Criteria) this;
        }

        public Criteria andPriceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PRICE_TYPE not between", value1, value2, "priceType");
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

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("PRICE not between", value1, value2, "price");
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

        public Criteria andEmergencyIsNull() {
            addCriterion("EMERGENCY is null");
            return (Criteria) this;
        }

        public Criteria andEmergencyIsNotNull() {
            addCriterion("EMERGENCY is not null");
            return (Criteria) this;
        }

        public Criteria andEmergencyEqualTo(Integer value) {
            addCriterion("EMERGENCY =", value, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyNotEqualTo(Integer value) {
            addCriterion("EMERGENCY <>", value, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyGreaterThan(Integer value) {
            addCriterion("EMERGENCY >", value, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("EMERGENCY >=", value, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyLessThan(Integer value) {
            addCriterion("EMERGENCY <", value, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyLessThanOrEqualTo(Integer value) {
            addCriterion("EMERGENCY <=", value, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyIn(List<Integer> values) {
            addCriterion("EMERGENCY in", values, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyNotIn(List<Integer> values) {
            addCriterion("EMERGENCY not in", values, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyBetween(Integer value1, Integer value2) {
            addCriterion("EMERGENCY between", value1, value2, "emergency");
            return (Criteria) this;
        }

        public Criteria andEmergencyNotBetween(Integer value1, Integer value2) {
            addCriterion("EMERGENCY not between", value1, value2, "emergency");
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

        public Criteria andLangTypeEqualTo(Integer value) {
            addCriterion("LANG_TYPE =", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeNotEqualTo(Integer value) {
            addCriterion("LANG_TYPE <>", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeGreaterThan(Integer value) {
            addCriterion("LANG_TYPE >", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("LANG_TYPE >=", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeLessThan(Integer value) {
            addCriterion("LANG_TYPE <", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeLessThanOrEqualTo(Integer value) {
            addCriterion("LANG_TYPE <=", value, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeIn(List<Integer> values) {
            addCriterion("LANG_TYPE in", values, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeNotIn(List<Integer> values) {
            addCriterion("LANG_TYPE not in", values, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeBetween(Integer value1, Integer value2) {
            addCriterion("LANG_TYPE between", value1, value2, "langType");
            return (Criteria) this;
        }

        public Criteria andLangTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("LANG_TYPE not between", value1, value2, "langType");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("UPDATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("UPDATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("UPDATE_USER =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("UPDATE_USER <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("UPDATE_USER >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("UPDATE_USER <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("UPDATE_USER like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("UPDATE_USER not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("UPDATE_USER in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("UPDATE_USER not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("UPDATE_USER between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("UPDATE_USER not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserIsNull() {
            addCriterion("FRIST_PFD_USER is null");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserIsNotNull() {
            addCriterion("FRIST_PFD_USER is not null");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserEqualTo(String value) {
            addCriterion("FRIST_PFD_USER =", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserNotEqualTo(String value) {
            addCriterion("FRIST_PFD_USER <>", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserGreaterThan(String value) {
            addCriterion("FRIST_PFD_USER >", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserGreaterThanOrEqualTo(String value) {
            addCriterion("FRIST_PFD_USER >=", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserLessThan(String value) {
            addCriterion("FRIST_PFD_USER <", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserLessThanOrEqualTo(String value) {
            addCriterion("FRIST_PFD_USER <=", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserLike(String value) {
            addCriterion("FRIST_PFD_USER like", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserNotLike(String value) {
            addCriterion("FRIST_PFD_USER not like", value, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserIn(List<String> values) {
            addCriterion("FRIST_PFD_USER in", values, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserNotIn(List<String> values) {
            addCriterion("FRIST_PFD_USER not in", values, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserBetween(String value1, String value2) {
            addCriterion("FRIST_PFD_USER between", value1, value2, "fristPfdUser");
            return (Criteria) this;
        }

        public Criteria andFristPfdUserNotBetween(String value1, String value2) {
            addCriterion("FRIST_PFD_USER not between", value1, value2, "fristPfdUser");
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

        public Criteria andSginTimeIsNull() {
            addCriterion("SGIN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSginTimeIsNotNull() {
            addCriterion("SGIN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSginTimeEqualTo(Date value) {
            addCriterion("SGIN_TIME =", value, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeNotEqualTo(Date value) {
            addCriterion("SGIN_TIME <>", value, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeGreaterThan(Date value) {
            addCriterion("SGIN_TIME >", value, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SGIN_TIME >=", value, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeLessThan(Date value) {
            addCriterion("SGIN_TIME <", value, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeLessThanOrEqualTo(Date value) {
            addCriterion("SGIN_TIME <=", value, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeIn(List<Date> values) {
            addCriterion("SGIN_TIME in", values, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeNotIn(List<Date> values) {
            addCriterion("SGIN_TIME not in", values, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeBetween(Date value1, Date value2) {
            addCriterion("SGIN_TIME between", value1, value2, "sginTime");
            return (Criteria) this;
        }

        public Criteria andSginTimeNotBetween(Date value1, Date value2) {
            addCriterion("SGIN_TIME not between", value1, value2, "sginTime");
            return (Criteria) this;
        }

        public Criteria andLocationTypeIsNull() {
            addCriterion("LOCATION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLocationTypeIsNotNull() {
            addCriterion("LOCATION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLocationTypeEqualTo(Integer value) {
            addCriterion("LOCATION_TYPE =", value, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeNotEqualTo(Integer value) {
            addCriterion("LOCATION_TYPE <>", value, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeGreaterThan(Integer value) {
            addCriterion("LOCATION_TYPE >", value, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOCATION_TYPE >=", value, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeLessThan(Integer value) {
            addCriterion("LOCATION_TYPE <", value, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("LOCATION_TYPE <=", value, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeIn(List<Integer> values) {
            addCriterion("LOCATION_TYPE in", values, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeNotIn(List<Integer> values) {
            addCriterion("LOCATION_TYPE not in", values, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeBetween(Integer value1, Integer value2) {
            addCriterion("LOCATION_TYPE between", value1, value2, "locationType");
            return (Criteria) this;
        }

        public Criteria andLocationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("LOCATION_TYPE not between", value1, value2, "locationType");
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