package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpAdvPositionExample {
    protected String orderByClause;
    
    protected Integer langType;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpAdvPositionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
    public void setLangType(Integer langType) {
        this.langType = langType;
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

        public Criteria andUrlIsNull() {
            addCriterion("URL is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("URL is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("URL =", value, "url");
            return (Criteria) this;
        }
        public Criteria andLangTypeTo(Integer value) {
            addCriterion("LANG_TYPE =", value, "langType");
            return (Criteria) this;
        }
        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("URL <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("URL >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("URL >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("URL <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("URL <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("URL like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("URL not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("URL in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("URL not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("URL between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("URL not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("REMARKS is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("REMARKS is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("REMARKS =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("REMARKS <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("REMARKS >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("REMARKS >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("REMARKS <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("REMARKS <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("REMARKS like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("REMARKS not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("REMARKS in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("REMARKS not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("REMARKS between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("REMARKS not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNull() {
            addCriterion("DISPLAY is null");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNotNull() {
            addCriterion("DISPLAY is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayEqualTo(Integer value) {
            addCriterion("DISPLAY =", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotEqualTo(Integer value) {
            addCriterion("DISPLAY <>", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThan(Integer value) {
            addCriterion("DISPLAY >", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThanOrEqualTo(Integer value) {
            addCriterion("DISPLAY >=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThan(Integer value) {
            addCriterion("DISPLAY <", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThanOrEqualTo(Integer value) {
            addCriterion("DISPLAY <=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayIn(List<Integer> values) {
            addCriterion("DISPLAY in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotIn(List<Integer> values) {
            addCriterion("DISPLAY not in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayBetween(Integer value1, Integer value2) {
            addCriterion("DISPLAY between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotBetween(Integer value1, Integer value2) {
            addCriterion("DISPLAY not between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("ORDERNO is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("ORDERNO is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(Integer value) {
            addCriterion("ORDERNO =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(Integer value) {
            addCriterion("ORDERNO <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(Integer value) {
            addCriterion("ORDERNO >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORDERNO >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(Integer value) {
            addCriterion("ORDERNO <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(Integer value) {
            addCriterion("ORDERNO <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<Integer> values) {
            addCriterion("ORDERNO in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<Integer> values) {
            addCriterion("ORDERNO not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(Integer value1, Integer value2) {
            addCriterion("ORDERNO between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(Integer value1, Integer value2) {
            addCriterion("ORDERNO not between", value1, value2, "orderno");
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

        public Criteria andPicIsNull() {
            addCriterion("PIC is null");
            return (Criteria) this;
        }

        public Criteria andPicIsNotNull() {
            addCriterion("PIC is not null");
            return (Criteria) this;
        }

        public Criteria andPicEqualTo(String value) {
            addCriterion("PIC =", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotEqualTo(String value) {
            addCriterion("PIC <>", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicGreaterThan(String value) {
            addCriterion("PIC >", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicGreaterThanOrEqualTo(String value) {
            addCriterion("PIC >=", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLessThan(String value) {
            addCriterion("PIC <", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLessThanOrEqualTo(String value) {
            addCriterion("PIC <=", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLike(String value) {
            addCriterion("PIC like", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotLike(String value) {
            addCriterion("PIC not like", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicIn(List<String> values) {
            addCriterion("PIC in", values, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotIn(List<String> values) {
            addCriterion("PIC not in", values, "pic");
            return (Criteria) this;
        }

        public Criteria andPicBetween(String value1, String value2) {
            addCriterion("PIC between", value1, value2, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotBetween(String value1, String value2) {
            addCriterion("PIC not between", value1, value2, "pic");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("POSITION is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("POSITION is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(Integer value) {
            addCriterion("POSITION =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(Integer value) {
            addCriterion("POSITION <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(Integer value) {
            addCriterion("POSITION >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(Integer value) {
            addCriterion("POSITION >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(Integer value) {
            addCriterion("POSITION <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(Integer value) {
            addCriterion("POSITION <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<Integer> values) {
            addCriterion("POSITION in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<Integer> values) {
            addCriterion("POSITION not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(Integer value1, Integer value2) {
            addCriterion("POSITION between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(Integer value1, Integer value2) {
            addCriterion("POSITION not between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andSwidthIsNull() {
            addCriterion("SWIDTH is null");
            return (Criteria) this;
        }

        public Criteria andSwidthIsNotNull() {
            addCriterion("SWIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andSwidthEqualTo(Integer value) {
            addCriterion("SWIDTH =", value, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthNotEqualTo(Integer value) {
            addCriterion("SWIDTH <>", value, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthGreaterThan(Integer value) {
            addCriterion("SWIDTH >", value, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("SWIDTH >=", value, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthLessThan(Integer value) {
            addCriterion("SWIDTH <", value, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthLessThanOrEqualTo(Integer value) {
            addCriterion("SWIDTH <=", value, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthIn(List<Integer> values) {
            addCriterion("SWIDTH in", values, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthNotIn(List<Integer> values) {
            addCriterion("SWIDTH not in", values, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthBetween(Integer value1, Integer value2) {
            addCriterion("SWIDTH between", value1, value2, "swidth");
            return (Criteria) this;
        }

        public Criteria andSwidthNotBetween(Integer value1, Integer value2) {
            addCriterion("SWIDTH not between", value1, value2, "swidth");
            return (Criteria) this;
        }

        public Criteria andSheightIsNull() {
            addCriterion("SHEIGHT is null");
            return (Criteria) this;
        }

        public Criteria andSheightIsNotNull() {
            addCriterion("SHEIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andSheightEqualTo(Integer value) {
            addCriterion("SHEIGHT =", value, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightNotEqualTo(Integer value) {
            addCriterion("SHEIGHT <>", value, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightGreaterThan(Integer value) {
            addCriterion("SHEIGHT >", value, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightGreaterThanOrEqualTo(Integer value) {
            addCriterion("SHEIGHT >=", value, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightLessThan(Integer value) {
            addCriterion("SHEIGHT <", value, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightLessThanOrEqualTo(Integer value) {
            addCriterion("SHEIGHT <=", value, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightIn(List<Integer> values) {
            addCriterion("SHEIGHT in", values, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightNotIn(List<Integer> values) {
            addCriterion("SHEIGHT not in", values, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightBetween(Integer value1, Integer value2) {
            addCriterion("SHEIGHT between", value1, value2, "sheight");
            return (Criteria) this;
        }

        public Criteria andSheightNotBetween(Integer value1, Integer value2) {
            addCriterion("SHEIGHT not between", value1, value2, "sheight");
            return (Criteria) this;
        }

        public Criteria andBwidthIsNull() {
            addCriterion("BWIDTH is null");
            return (Criteria) this;
        }

        public Criteria andBwidthIsNotNull() {
            addCriterion("BWIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andBwidthEqualTo(Integer value) {
            addCriterion("BWIDTH =", value, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthNotEqualTo(Integer value) {
            addCriterion("BWIDTH <>", value, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthGreaterThan(Integer value) {
            addCriterion("BWIDTH >", value, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("BWIDTH >=", value, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthLessThan(Integer value) {
            addCriterion("BWIDTH <", value, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthLessThanOrEqualTo(Integer value) {
            addCriterion("BWIDTH <=", value, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthIn(List<Integer> values) {
            addCriterion("BWIDTH in", values, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthNotIn(List<Integer> values) {
            addCriterion("BWIDTH not in", values, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthBetween(Integer value1, Integer value2) {
            addCriterion("BWIDTH between", value1, value2, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBwidthNotBetween(Integer value1, Integer value2) {
            addCriterion("BWIDTH not between", value1, value2, "bwidth");
            return (Criteria) this;
        }

        public Criteria andBheightIsNull() {
            addCriterion("BHEIGHT is null");
            return (Criteria) this;
        }

        public Criteria andBheightIsNotNull() {
            addCriterion("BHEIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andBheightEqualTo(Integer value) {
            addCriterion("BHEIGHT =", value, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightNotEqualTo(Integer value) {
            addCriterion("BHEIGHT <>", value, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightGreaterThan(Integer value) {
            addCriterion("BHEIGHT >", value, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightGreaterThanOrEqualTo(Integer value) {
            addCriterion("BHEIGHT >=", value, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightLessThan(Integer value) {
            addCriterion("BHEIGHT <", value, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightLessThanOrEqualTo(Integer value) {
            addCriterion("BHEIGHT <=", value, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightIn(List<Integer> values) {
            addCriterion("BHEIGHT in", values, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightNotIn(List<Integer> values) {
            addCriterion("BHEIGHT not in", values, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightBetween(Integer value1, Integer value2) {
            addCriterion("BHEIGHT between", value1, value2, "bheight");
            return (Criteria) this;
        }

        public Criteria andBheightNotBetween(Integer value1, Integer value2) {
            addCriterion("BHEIGHT not between", value1, value2, "bheight");
            return (Criteria) this;
        }

        public Criteria andStyleIsNull() {
            addCriterion("STYLE is null");
            return (Criteria) this;
        }

        public Criteria andStyleIsNotNull() {
            addCriterion("STYLE is not null");
            return (Criteria) this;
        }

        public Criteria andStyleEqualTo(Integer value) {
            addCriterion("STYLE =", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleNotEqualTo(Integer value) {
            addCriterion("STYLE <>", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleGreaterThan(Integer value) {
            addCriterion("STYLE >", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("STYLE >=", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleLessThan(Integer value) {
            addCriterion("STYLE <", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleLessThanOrEqualTo(Integer value) {
            addCriterion("STYLE <=", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleIn(List<Integer> values) {
            addCriterion("STYLE in", values, "style");
            return (Criteria) this;
        }

        public Criteria andStyleNotIn(List<Integer> values) {
            addCriterion("STYLE not in", values, "style");
            return (Criteria) this;
        }

        public Criteria andStyleBetween(Integer value1, Integer value2) {
            addCriterion("STYLE between", value1, value2, "style");
            return (Criteria) this;
        }

        public Criteria andStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("STYLE not between", value1, value2, "style");
            return (Criteria) this;
        }

        public Criteria andFileIsNull() {
            addCriterion("FILE is null");
            return (Criteria) this;
        }

        public Criteria andFileIsNotNull() {
            addCriterion("FILE is not null");
            return (Criteria) this;
        }

        public Criteria andFileEqualTo(String value) {
            addCriterion("FILE =", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotEqualTo(String value) {
            addCriterion("FILE <>", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThan(String value) {
            addCriterion("FILE >", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThanOrEqualTo(String value) {
            addCriterion("FILE >=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThan(String value) {
            addCriterion("FILE <", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThanOrEqualTo(String value) {
            addCriterion("FILE <=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLike(String value) {
            addCriterion("FILE like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotLike(String value) {
            addCriterion("FILE not like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileIn(List<String> values) {
            addCriterion("FILE in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotIn(List<String> values) {
            addCriterion("FILE not in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileBetween(String value1, String value2) {
            addCriterion("FILE between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotBetween(String value1, String value2) {
            addCriterion("FILE not between", value1, value2, "file");
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

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("TYPE not between", value1, value2, "type");
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

        public Criteria andPicIdIsNull() {
            addCriterion("PIC_ID is null");
            return (Criteria) this;
        }

        public Criteria andPicIdIsNotNull() {
            addCriterion("PIC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPicIdEqualTo(Integer value) {
            addCriterion("PIC_ID =", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdNotEqualTo(Integer value) {
            addCriterion("PIC_ID <>", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdGreaterThan(Integer value) {
            addCriterion("PIC_ID >", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PIC_ID >=", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdLessThan(Integer value) {
            addCriterion("PIC_ID <", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdLessThanOrEqualTo(Integer value) {
            addCriterion("PIC_ID <=", value, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdIn(List<Integer> values) {
            addCriterion("PIC_ID in", values, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdNotIn(List<Integer> values) {
            addCriterion("PIC_ID not in", values, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdBetween(Integer value1, Integer value2) {
            addCriterion("PIC_ID between", value1, value2, "picId");
            return (Criteria) this;
        }

        public Criteria andPicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PIC_ID not between", value1, value2, "picId");
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