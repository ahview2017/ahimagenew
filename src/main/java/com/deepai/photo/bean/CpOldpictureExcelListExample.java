package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpOldpictureExcelListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpOldpictureExcelListExample() {
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

        public Criteria andOldDateIsNull() {
            addCriterion("OLD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andOldDateIsNotNull() {
            addCriterion("OLD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andOldDateEqualTo(String value) {
            addCriterion("OLD_DATE =", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateNotEqualTo(String value) {
            addCriterion("OLD_DATE <>", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateGreaterThan(String value) {
            addCriterion("OLD_DATE >", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_DATE >=", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateLessThan(String value) {
            addCriterion("OLD_DATE <", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateLessThanOrEqualTo(String value) {
            addCriterion("OLD_DATE <=", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateLike(String value) {
            addCriterion("OLD_DATE like", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateNotLike(String value) {
            addCriterion("OLD_DATE not like", value, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateIn(List<String> values) {
            addCriterion("OLD_DATE in", values, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateNotIn(List<String> values) {
            addCriterion("OLD_DATE not in", values, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateBetween(String value1, String value2) {
            addCriterion("OLD_DATE between", value1, value2, "oldDate");
            return (Criteria) this;
        }

        public Criteria andOldDateNotBetween(String value1, String value2) {
            addCriterion("OLD_DATE not between", value1, value2, "oldDate");
            return (Criteria) this;
        }

        public Criteria andYearsIsNull() {
            addCriterion("YEARS is null");
            return (Criteria) this;
        }

        public Criteria andYearsIsNotNull() {
            addCriterion("YEARS is not null");
            return (Criteria) this;
        }

        public Criteria andYearsEqualTo(String value) {
            addCriterion("YEARS =", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsNotEqualTo(String value) {
            addCriterion("YEARS <>", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsGreaterThan(String value) {
            addCriterion("YEARS >", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsGreaterThanOrEqualTo(String value) {
            addCriterion("YEARS >=", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsLessThan(String value) {
            addCriterion("YEARS <", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsLessThanOrEqualTo(String value) {
            addCriterion("YEARS <=", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsLike(String value) {
            addCriterion("YEARS like", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsNotLike(String value) {
            addCriterion("YEARS not like", value, "years");
            return (Criteria) this;
        }

        public Criteria andYearsIn(List<String> values) {
            addCriterion("YEARS in", values, "years");
            return (Criteria) this;
        }

        public Criteria andYearsNotIn(List<String> values) {
            addCriterion("YEARS not in", values, "years");
            return (Criteria) this;
        }

        public Criteria andYearsBetween(String value1, String value2) {
            addCriterion("YEARS between", value1, value2, "years");
            return (Criteria) this;
        }

        public Criteria andYearsNotBetween(String value1, String value2) {
            addCriterion("YEARS not between", value1, value2, "years");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("CATEGORY =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("CATEGORY <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("CATEGORY >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("CATEGORY <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("CATEGORY like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("CATEGORY not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("CATEGORY in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("CATEGORY not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("CATEGORY between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("CATEGORY not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andFigureIsNull() {
            addCriterion("FIGURE is null");
            return (Criteria) this;
        }

        public Criteria andFigureIsNotNull() {
            addCriterion("FIGURE is not null");
            return (Criteria) this;
        }

        public Criteria andFigureEqualTo(String value) {
            addCriterion("FIGURE =", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureNotEqualTo(String value) {
            addCriterion("FIGURE <>", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureGreaterThan(String value) {
            addCriterion("FIGURE >", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureGreaterThanOrEqualTo(String value) {
            addCriterion("FIGURE >=", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureLessThan(String value) {
            addCriterion("FIGURE <", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureLessThanOrEqualTo(String value) {
            addCriterion("FIGURE <=", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureLike(String value) {
            addCriterion("FIGURE like", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureNotLike(String value) {
            addCriterion("FIGURE not like", value, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureIn(List<String> values) {
            addCriterion("FIGURE in", values, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureNotIn(List<String> values) {
            addCriterion("FIGURE not in", values, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureBetween(String value1, String value2) {
            addCriterion("FIGURE between", value1, value2, "figure");
            return (Criteria) this;
        }

        public Criteria andFigureNotBetween(String value1, String value2) {
            addCriterion("FIGURE not between", value1, value2, "figure");
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

        public Criteria andNumberIsNull() {
            addCriterion("NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(String value) {
            addCriterion("NUMBER =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("NUMBER <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("NUMBER >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("NUMBER >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("NUMBER <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("NUMBER <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLike(String value) {
            addCriterion("NUMBER like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotLike(String value) {
            addCriterion("NUMBER not like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<String> values) {
            addCriterion("NUMBER in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<String> values) {
            addCriterion("NUMBER not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(String value1, String value2) {
            addCriterion("NUMBER between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(String value1, String value2) {
            addCriterion("NUMBER not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andPicFileNameIsNull() {
            addCriterion("PIC_FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPicFileNameIsNotNull() {
            addCriterion("PIC_FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPicFileNameEqualTo(String value) {
            addCriterion("PIC_FILE_NAME =", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameNotEqualTo(String value) {
            addCriterion("PIC_FILE_NAME <>", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameGreaterThan(String value) {
            addCriterion("PIC_FILE_NAME >", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("PIC_FILE_NAME >=", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameLessThan(String value) {
            addCriterion("PIC_FILE_NAME <", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameLessThanOrEqualTo(String value) {
            addCriterion("PIC_FILE_NAME <=", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameLike(String value) {
            addCriterion("PIC_FILE_NAME like", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameNotLike(String value) {
            addCriterion("PIC_FILE_NAME not like", value, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameIn(List<String> values) {
            addCriterion("PIC_FILE_NAME in", values, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameNotIn(List<String> values) {
            addCriterion("PIC_FILE_NAME not in", values, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameBetween(String value1, String value2) {
            addCriterion("PIC_FILE_NAME between", value1, value2, "picFileName");
            return (Criteria) this;
        }

        public Criteria andPicFileNameNotBetween(String value1, String value2) {
            addCriterion("PIC_FILE_NAME not between", value1, value2, "picFileName");
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

        public Criteria andHandinIsNull() {
            addCriterion("HANDIN is null");
            return (Criteria) this;
        }

        public Criteria andHandinIsNotNull() {
            addCriterion("HANDIN is not null");
            return (Criteria) this;
        }

        public Criteria andHandinEqualTo(String value) {
            addCriterion("HANDIN =", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinNotEqualTo(String value) {
            addCriterion("HANDIN <>", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinGreaterThan(String value) {
            addCriterion("HANDIN >", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinGreaterThanOrEqualTo(String value) {
            addCriterion("HANDIN >=", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinLessThan(String value) {
            addCriterion("HANDIN <", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinLessThanOrEqualTo(String value) {
            addCriterion("HANDIN <=", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinLike(String value) {
            addCriterion("HANDIN like", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinNotLike(String value) {
            addCriterion("HANDIN not like", value, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinIn(List<String> values) {
            addCriterion("HANDIN in", values, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinNotIn(List<String> values) {
            addCriterion("HANDIN not in", values, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinBetween(String value1, String value2) {
            addCriterion("HANDIN between", value1, value2, "handin");
            return (Criteria) this;
        }

        public Criteria andHandinNotBetween(String value1, String value2) {
            addCriterion("HANDIN not between", value1, value2, "handin");
            return (Criteria) this;
        }

        public Criteria andEdittimeIsNull() {
            addCriterion("EDITTIME is null");
            return (Criteria) this;
        }

        public Criteria andEdittimeIsNotNull() {
            addCriterion("EDITTIME is not null");
            return (Criteria) this;
        }

        public Criteria andEdittimeEqualTo(Date value) {
            addCriterion("EDITTIME =", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotEqualTo(Date value) {
            addCriterion("EDITTIME <>", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeGreaterThan(Date value) {
            addCriterion("EDITTIME >", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeGreaterThanOrEqualTo(Date value) {
            addCriterion("EDITTIME >=", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeLessThan(Date value) {
            addCriterion("EDITTIME <", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeLessThanOrEqualTo(Date value) {
            addCriterion("EDITTIME <=", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeIn(List<Date> values) {
            addCriterion("EDITTIME in", values, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotIn(List<Date> values) {
            addCriterion("EDITTIME not in", values, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeBetween(Date value1, Date value2) {
            addCriterion("EDITTIME between", value1, value2, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotBetween(Date value1, Date value2) {
            addCriterion("EDITTIME not between", value1, value2, "edittime");
            return (Criteria) this;
        }

        public Criteria andExcelidIsNull() {
            addCriterion("EXCELID is null");
            return (Criteria) this;
        }

        public Criteria andExcelidIsNotNull() {
            addCriterion("EXCELID is not null");
            return (Criteria) this;
        }

        public Criteria andExcelidEqualTo(Integer value) {
            addCriterion("EXCELID =", value, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidNotEqualTo(Integer value) {
            addCriterion("EXCELID <>", value, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidGreaterThan(Integer value) {
            addCriterion("EXCELID >", value, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXCELID >=", value, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidLessThan(Integer value) {
            addCriterion("EXCELID <", value, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidLessThanOrEqualTo(Integer value) {
            addCriterion("EXCELID <=", value, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidIn(List<Integer> values) {
            addCriterion("EXCELID in", values, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidNotIn(List<Integer> values) {
            addCriterion("EXCELID not in", values, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidBetween(Integer value1, Integer value2) {
            addCriterion("EXCELID between", value1, value2, "excelid");
            return (Criteria) this;
        }

        public Criteria andExcelidNotBetween(Integer value1, Integer value2) {
            addCriterion("EXCELID not between", value1, value2, "excelid");
            return (Criteria) this;
        }

        public Criteria andFileAllpathIsNull() {
            addCriterion("FILE_ALLPATH is null");
            return (Criteria) this;
        }

        public Criteria andFileAllpathIsNotNull() {
            addCriterion("FILE_ALLPATH is not null");
            return (Criteria) this;
        }

        public Criteria andFileAllpathEqualTo(String value) {
            addCriterion("FILE_ALLPATH =", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathNotEqualTo(String value) {
            addCriterion("FILE_ALLPATH <>", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathGreaterThan(String value) {
            addCriterion("FILE_ALLPATH >", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_ALLPATH >=", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathLessThan(String value) {
            addCriterion("FILE_ALLPATH <", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathLessThanOrEqualTo(String value) {
            addCriterion("FILE_ALLPATH <=", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathLike(String value) {
            addCriterion("FILE_ALLPATH like", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathNotLike(String value) {
            addCriterion("FILE_ALLPATH not like", value, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathIn(List<String> values) {
            addCriterion("FILE_ALLPATH in", values, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathNotIn(List<String> values) {
            addCriterion("FILE_ALLPATH not in", values, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathBetween(String value1, String value2) {
            addCriterion("FILE_ALLPATH between", value1, value2, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andFileAllpathNotBetween(String value1, String value2) {
            addCriterion("FILE_ALLPATH not between", value1, value2, "fileAllpath");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdIsNull() {
            addCriterion("PIC_GROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdIsNotNull() {
            addCriterion("PIC_GROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdEqualTo(Integer value) {
            addCriterion("PIC_GROUP_ID =", value, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdNotEqualTo(Integer value) {
            addCriterion("PIC_GROUP_ID <>", value, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdGreaterThan(Integer value) {
            addCriterion("PIC_GROUP_ID >", value, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PIC_GROUP_ID >=", value, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdLessThan(Integer value) {
            addCriterion("PIC_GROUP_ID <", value, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("PIC_GROUP_ID <=", value, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdIn(List<Integer> values) {
            addCriterion("PIC_GROUP_ID in", values, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdNotIn(List<Integer> values) {
            addCriterion("PIC_GROUP_ID not in", values, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("PIC_GROUP_ID between", value1, value2, "picGroupId");
            return (Criteria) this;
        }

        public Criteria andPicGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PIC_GROUP_ID not between", value1, value2, "picGroupId");
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