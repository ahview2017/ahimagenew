package com.deepai.photo.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpUserExample() {
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

        public Criteria andUserNameIsNull() {
            addCriterion("USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("USER_NAME =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("USER_NAME <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("USER_NAME >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("USER_NAME >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("USER_NAME <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("USER_NAME <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("USER_NAME like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("USER_NAME not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("USER_NAME in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("USER_NAME not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("USER_NAME between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("USER_NAME not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("PASSWORD =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("PASSWORD <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("PASSWORD >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("PASSWORD >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("PASSWORD <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("PASSWORD <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("PASSWORD like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("PASSWORD not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("PASSWORD in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("PASSWORD not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("PASSWORD between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("PASSWORD not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andTureNameIsNull() {
            addCriterion("TURE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTureNameIsNotNull() {
            addCriterion("TURE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTureNameEqualTo(String value) {
            addCriterion("TURE_NAME =", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameNotEqualTo(String value) {
            addCriterion("TURE_NAME <>", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameGreaterThan(String value) {
            addCriterion("TURE_NAME >", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameGreaterThanOrEqualTo(String value) {
            addCriterion("TURE_NAME >=", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameLessThan(String value) {
            addCriterion("TURE_NAME <", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameLessThanOrEqualTo(String value) {
            addCriterion("TURE_NAME <=", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameLike(String value) {
            addCriterion("TURE_NAME like", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameNotLike(String value) {
            addCriterion("TURE_NAME not like", value, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameIn(List<String> values) {
            addCriterion("TURE_NAME in", values, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameNotIn(List<String> values) {
            addCriterion("TURE_NAME not in", values, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameBetween(String value1, String value2) {
            addCriterion("TURE_NAME between", value1, value2, "tureName");
            return (Criteria) this;
        }

        public Criteria andTureNameNotBetween(String value1, String value2) {
            addCriterion("TURE_NAME not between", value1, value2, "tureName");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("ADDRESS =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("ADDRESS <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("ADDRESS >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ADDRESS >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("ADDRESS <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("ADDRESS <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("ADDRESS like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("ADDRESS not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("ADDRESS in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("ADDRESS not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("ADDRESS between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("ADDRESS not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNull() {
            addCriterion("ZIPCODE is null");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNotNull() {
            addCriterion("ZIPCODE is not null");
            return (Criteria) this;
        }

        public Criteria andZipcodeEqualTo(String value) {
            addCriterion("ZIPCODE =", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotEqualTo(String value) {
            addCriterion("ZIPCODE <>", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThan(String value) {
            addCriterion("ZIPCODE >", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ZIPCODE >=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThan(String value) {
            addCriterion("ZIPCODE <", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThanOrEqualTo(String value) {
            addCriterion("ZIPCODE <=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLike(String value) {
            addCriterion("ZIPCODE like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotLike(String value) {
            addCriterion("ZIPCODE not like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeIn(List<String> values) {
            addCriterion("ZIPCODE in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotIn(List<String> values) {
            addCriterion("ZIPCODE not in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeBetween(String value1, String value2) {
            addCriterion("ZIPCODE between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotBetween(String value1, String value2) {
            addCriterion("ZIPCODE not between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andEmailBindIsNull() {
            addCriterion("EMAIL_BIND is null");
            return (Criteria) this;
        }

        public Criteria andEmailBindIsNotNull() {
            addCriterion("EMAIL_BIND is not null");
            return (Criteria) this;
        }

        public Criteria andEmailBindEqualTo(String value) {
            addCriterion("EMAIL_BIND =", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindNotEqualTo(String value) {
            addCriterion("EMAIL_BIND <>", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindGreaterThan(String value) {
            addCriterion("EMAIL_BIND >", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_BIND >=", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindLessThan(String value) {
            addCriterion("EMAIL_BIND <", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_BIND <=", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindLike(String value) {
            addCriterion("EMAIL_BIND like", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindNotLike(String value) {
            addCriterion("EMAIL_BIND not like", value, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindIn(List<String> values) {
            addCriterion("EMAIL_BIND in", values, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindNotIn(List<String> values) {
            addCriterion("EMAIL_BIND not in", values, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindBetween(String value1, String value2) {
            addCriterion("EMAIL_BIND between", value1, value2, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailBindNotBetween(String value1, String value2) {
            addCriterion("EMAIL_BIND not between", value1, value2, "emailBind");
            return (Criteria) this;
        }

        public Criteria andEmailContactIsNull() {
            addCriterion("EMAIL_CONTACT is null");
            return (Criteria) this;
        }

        public Criteria andEmailContactIsNotNull() {
            addCriterion("EMAIL_CONTACT is not null");
            return (Criteria) this;
        }

        public Criteria andEmailContactEqualTo(String value) {
            addCriterion("EMAIL_CONTACT =", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotEqualTo(String value) {
            addCriterion("EMAIL_CONTACT <>", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactGreaterThan(String value) {
            addCriterion("EMAIL_CONTACT >", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_CONTACT >=", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactLessThan(String value) {
            addCriterion("EMAIL_CONTACT <", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_CONTACT <=", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactLike(String value) {
            addCriterion("EMAIL_CONTACT like", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotLike(String value) {
            addCriterion("EMAIL_CONTACT not like", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactIn(List<String> values) {
            addCriterion("EMAIL_CONTACT in", values, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotIn(List<String> values) {
            addCriterion("EMAIL_CONTACT not in", values, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactBetween(String value1, String value2) {
            addCriterion("EMAIL_CONTACT between", value1, value2, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotBetween(String value1, String value2) {
            addCriterion("EMAIL_CONTACT not between", value1, value2, "emailContact");
            return (Criteria) this;
        }

        public Criteria andTelBindIsNull() {
            addCriterion("TEL_BIND is null");
            return (Criteria) this;
        }

        public Criteria andTelBindIsNotNull() {
            addCriterion("TEL_BIND is not null");
            return (Criteria) this;
        }

        public Criteria andTelBindEqualTo(String value) {
            addCriterion("TEL_BIND =", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindNotEqualTo(String value) {
            addCriterion("TEL_BIND <>", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindGreaterThan(String value) {
            addCriterion("TEL_BIND >", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindGreaterThanOrEqualTo(String value) {
            addCriterion("TEL_BIND >=", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindLessThan(String value) {
            addCriterion("TEL_BIND <", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindLessThanOrEqualTo(String value) {
            addCriterion("TEL_BIND <=", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindLike(String value) {
            addCriterion("TEL_BIND like", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindNotLike(String value) {
            addCriterion("TEL_BIND not like", value, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindIn(List<String> values) {
            addCriterion("TEL_BIND in", values, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindNotIn(List<String> values) {
            addCriterion("TEL_BIND not in", values, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindBetween(String value1, String value2) {
            addCriterion("TEL_BIND between", value1, value2, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelBindNotBetween(String value1, String value2) {
            addCriterion("TEL_BIND not between", value1, value2, "telBind");
            return (Criteria) this;
        }

        public Criteria andTelContactIsNull() {
            addCriterion("TEL_CONTACT is null");
            return (Criteria) this;
        }

        public Criteria andTelContactIsNotNull() {
            addCriterion("TEL_CONTACT is not null");
            return (Criteria) this;
        }

        public Criteria andTelContactEqualTo(String value) {
            addCriterion("TEL_CONTACT =", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactNotEqualTo(String value) {
            addCriterion("TEL_CONTACT <>", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactGreaterThan(String value) {
            addCriterion("TEL_CONTACT >", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactGreaterThanOrEqualTo(String value) {
            addCriterion("TEL_CONTACT >=", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactLessThan(String value) {
            addCriterion("TEL_CONTACT <", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactLessThanOrEqualTo(String value) {
            addCriterion("TEL_CONTACT <=", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactLike(String value) {
            addCriterion("TEL_CONTACT like", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactNotLike(String value) {
            addCriterion("TEL_CONTACT not like", value, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactIn(List<String> values) {
            addCriterion("TEL_CONTACT in", values, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactNotIn(List<String> values) {
            addCriterion("TEL_CONTACT not in", values, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactBetween(String value1, String value2) {
            addCriterion("TEL_CONTACT between", value1, value2, "telContact");
            return (Criteria) this;
        }

        public Criteria andTelContactNotBetween(String value1, String value2) {
            addCriterion("TEL_CONTACT not between", value1, value2, "telContact");
            return (Criteria) this;
        }

        public Criteria andUploadCountIsNull() {
            addCriterion("UPLOAD_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andUploadCountIsNotNull() {
            addCriterion("UPLOAD_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andUploadCountEqualTo(Integer value) {
            addCriterion("UPLOAD_COUNT =", value, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountNotEqualTo(Integer value) {
            addCriterion("UPLOAD_COUNT <>", value, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountGreaterThan(Integer value) {
            addCriterion("UPLOAD_COUNT >", value, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("UPLOAD_COUNT >=", value, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountLessThan(Integer value) {
            addCriterion("UPLOAD_COUNT <", value, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountLessThanOrEqualTo(Integer value) {
            addCriterion("UPLOAD_COUNT <=", value, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountIn(List<Integer> values) {
            addCriterion("UPLOAD_COUNT in", values, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountNotIn(List<Integer> values) {
            addCriterion("UPLOAD_COUNT not in", values, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountBetween(Integer value1, Integer value2) {
            addCriterion("UPLOAD_COUNT between", value1, value2, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andUploadCountNotBetween(Integer value1, Integer value2) {
            addCriterion("UPLOAD_COUNT not between", value1, value2, "uploadCount");
            return (Criteria) this;
        }

        public Criteria andPubCountIsNull() {
            addCriterion("PUB_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPubCountIsNotNull() {
            addCriterion("PUB_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPubCountEqualTo(Integer value) {
            addCriterion("PUB_COUNT =", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountNotEqualTo(Integer value) {
            addCriterion("PUB_COUNT <>", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountGreaterThan(Integer value) {
            addCriterion("PUB_COUNT >", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("PUB_COUNT >=", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountLessThan(Integer value) {
            addCriterion("PUB_COUNT <", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountLessThanOrEqualTo(Integer value) {
            addCriterion("PUB_COUNT <=", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountIn(List<Integer> values) {
            addCriterion("PUB_COUNT in", values, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountNotIn(List<Integer> values) {
            addCriterion("PUB_COUNT not in", values, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountBetween(Integer value1, Integer value2) {
            addCriterion("PUB_COUNT between", value1, value2, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountNotBetween(Integer value1, Integer value2) {
            addCriterion("PUB_COUNT not between", value1, value2, "pubCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountIsNull() {
            addCriterion("KEEP_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andKeepCountIsNotNull() {
            addCriterion("KEEP_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andKeepCountEqualTo(Integer value) {
            addCriterion("KEEP_COUNT =", value, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountNotEqualTo(Integer value) {
            addCriterion("KEEP_COUNT <>", value, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountGreaterThan(Integer value) {
            addCriterion("KEEP_COUNT >", value, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("KEEP_COUNT >=", value, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountLessThan(Integer value) {
            addCriterion("KEEP_COUNT <", value, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountLessThanOrEqualTo(Integer value) {
            addCriterion("KEEP_COUNT <=", value, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountIn(List<Integer> values) {
            addCriterion("KEEP_COUNT in", values, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountNotIn(List<Integer> values) {
            addCriterion("KEEP_COUNT not in", values, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountBetween(Integer value1, Integer value2) {
            addCriterion("KEEP_COUNT between", value1, value2, "keepCount");
            return (Criteria) this;
        }

        public Criteria andKeepCountNotBetween(Integer value1, Integer value2) {
            addCriterion("KEEP_COUNT not between", value1, value2, "keepCount");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("APPLY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("APPLY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(Date value) {
            addCriterion("APPLY_DATE =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(Date value) {
            addCriterion("APPLY_DATE <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(Date value) {
            addCriterion("APPLY_DATE >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("APPLY_DATE >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(Date value) {
            addCriterion("APPLY_DATE <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(Date value) {
            addCriterion("APPLY_DATE <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<Date> values) {
            addCriterion("APPLY_DATE in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<Date> values) {
            addCriterion("APPLY_DATE not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(Date value1, Date value2) {
            addCriterion("APPLY_DATE between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(Date value1, Date value2) {
            addCriterion("APPLY_DATE not between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andRegDateIsNull() {
            addCriterion("REG_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRegDateIsNotNull() {
            addCriterion("REG_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRegDateEqualTo(Date value) {
            addCriterion("REG_DATE =", value, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateNotEqualTo(Date value) {
            addCriterion("REG_DATE <>", value, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateGreaterThan(Date value) {
            addCriterion("REG_DATE >", value, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REG_DATE >=", value, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateLessThan(Date value) {
            addCriterion("REG_DATE <", value, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateLessThanOrEqualTo(Date value) {
            addCriterion("REG_DATE <=", value, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateIn(List<Date> values) {
            addCriterion("REG_DATE in", values, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateNotIn(List<Date> values) {
            addCriterion("REG_DATE not in", values, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateBetween(Date value1, Date value2) {
            addCriterion("REG_DATE between", value1, value2, "regDate");
            return (Criteria) this;
        }

        public Criteria andRegDateNotBetween(Date value1, Date value2) {
            addCriterion("REG_DATE not between", value1, value2, "regDate");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelIsNull() {
            addCriterion("DOWNLOAD_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelIsNotNull() {
            addCriterion("DOWNLOAD_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelEqualTo(Integer value) {
            addCriterion("DOWNLOAD_LEVEL =", value, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelNotEqualTo(Integer value) {
            addCriterion("DOWNLOAD_LEVEL <>", value, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelGreaterThan(Integer value) {
            addCriterion("DOWNLOAD_LEVEL >", value, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("DOWNLOAD_LEVEL >=", value, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelLessThan(Integer value) {
            addCriterion("DOWNLOAD_LEVEL <", value, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelLessThanOrEqualTo(Integer value) {
            addCriterion("DOWNLOAD_LEVEL <=", value, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelIn(List<Integer> values) {
            addCriterion("DOWNLOAD_LEVEL in", values, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelNotIn(List<Integer> values) {
            addCriterion("DOWNLOAD_LEVEL not in", values, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelBetween(Integer value1, Integer value2) {
            addCriterion("DOWNLOAD_LEVEL between", value1, value2, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andDownloadLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("DOWNLOAD_LEVEL not between", value1, value2, "downloadLevel");
            return (Criteria) this;
        }

        public Criteria andIsDownloadIsNull() {
            addCriterion("IS_DOWNLOAD is null");
            return (Criteria) this;
        }

        public Criteria andIsDownloadIsNotNull() {
            addCriterion("IS_DOWNLOAD is not null");
            return (Criteria) this;
        }

        public Criteria andIsDownloadEqualTo(Byte value) {
            addCriterion("IS_DOWNLOAD =", value, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadNotEqualTo(Byte value) {
            addCriterion("IS_DOWNLOAD <>", value, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadGreaterThan(Byte value) {
            addCriterion("IS_DOWNLOAD >", value, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_DOWNLOAD >=", value, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadLessThan(Byte value) {
            addCriterion("IS_DOWNLOAD <", value, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadLessThanOrEqualTo(Byte value) {
            addCriterion("IS_DOWNLOAD <=", value, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadIn(List<Byte> values) {
            addCriterion("IS_DOWNLOAD in", values, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadNotIn(List<Byte> values) {
            addCriterion("IS_DOWNLOAD not in", values, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadBetween(Byte value1, Byte value2) {
            addCriterion("IS_DOWNLOAD between", value1, value2, "isDownload");
            return (Criteria) this;
        }

        public Criteria andIsDownloadNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_DOWNLOAD not between", value1, value2, "isDownload");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeIsNull() {
            addCriterion("DOWNLOAD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeIsNotNull() {
            addCriterion("DOWNLOAD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeEqualTo(Integer value) {
            addCriterion("DOWNLOAD_TYPE =", value, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeNotEqualTo(Integer value) {
            addCriterion("DOWNLOAD_TYPE <>", value, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeGreaterThan(Integer value) {
            addCriterion("DOWNLOAD_TYPE >", value, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("DOWNLOAD_TYPE >=", value, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeLessThan(Integer value) {
            addCriterion("DOWNLOAD_TYPE <", value, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeLessThanOrEqualTo(Integer value) {
            addCriterion("DOWNLOAD_TYPE <=", value, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeIn(List<Integer> values) {
            addCriterion("DOWNLOAD_TYPE in", values, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeNotIn(List<Integer> values) {
            addCriterion("DOWNLOAD_TYPE not in", values, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeBetween(Integer value1, Integer value2) {
            addCriterion("DOWNLOAD_TYPE between", value1, value2, "downloadType");
            return (Criteria) this;
        }

        public Criteria andDownloadTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("DOWNLOAD_TYPE not between", value1, value2, "downloadType");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceIsNull() {
            addCriterion("CONTRACT_PERPRICE is null");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceIsNotNull() {
            addCriterion("CONTRACT_PERPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceEqualTo(Integer value) {
            addCriterion("CONTRACT_PERPRICE =", value, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceNotEqualTo(Integer value) {
            addCriterion("CONTRACT_PERPRICE <>", value, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceGreaterThan(Integer value) {
            addCriterion("CONTRACT_PERPRICE >", value, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_PERPRICE >=", value, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceLessThan(Integer value) {
            addCriterion("CONTRACT_PERPRICE <", value, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_PERPRICE <=", value, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceIn(List<Integer> values) {
            addCriterion("CONTRACT_PERPRICE in", values, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceNotIn(List<Integer> values) {
            addCriterion("CONTRACT_PERPRICE not in", values, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_PERPRICE between", value1, value2, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractPerpriceNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_PERPRICE not between", value1, value2, "contractPerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceIsNull() {
            addCriterion("CONTRACT_BASE_PERPRICE is null");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceIsNotNull() {
            addCriterion("CONTRACT_BASE_PERPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceEqualTo(Integer value) {
            addCriterion("CONTRACT_BASE_PERPRICE =", value, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceNotEqualTo(Integer value) {
            addCriterion("CONTRACT_BASE_PERPRICE <>", value, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceGreaterThan(Integer value) {
            addCriterion("CONTRACT_BASE_PERPRICE >", value, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_BASE_PERPRICE >=", value, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceLessThan(Integer value) {
            addCriterion("CONTRACT_BASE_PERPRICE <", value, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_BASE_PERPRICE <=", value, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceIn(List<Integer> values) {
            addCriterion("CONTRACT_BASE_PERPRICE in", values, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceNotIn(List<Integer> values) {
            addCriterion("CONTRACT_BASE_PERPRICE not in", values, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_BASE_PERPRICE between", value1, value2, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractBasePerpriceNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_BASE_PERPRICE not between", value1, value2, "contractBasePerprice");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeIsNull() {
            addCriterion("CONTRACT_START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeIsNotNull() {
            addCriterion("CONTRACT_START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeEqualTo(Date value) {
            addCriterion("CONTRACT_START_TIME =", value, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeNotEqualTo(Date value) {
            addCriterion("CONTRACT_START_TIME <>", value, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeGreaterThan(Date value) {
            addCriterion("CONTRACT_START_TIME >", value, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CONTRACT_START_TIME >=", value, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeLessThan(Date value) {
            addCriterion("CONTRACT_START_TIME <", value, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("CONTRACT_START_TIME <=", value, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeIn(List<Date> values) {
            addCriterion("CONTRACT_START_TIME in", values, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeNotIn(List<Date> values) {
            addCriterion("CONTRACT_START_TIME not in", values, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeBetween(Date value1, Date value2) {
            addCriterion("CONTRACT_START_TIME between", value1, value2, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("CONTRACT_START_TIME not between", value1, value2, "contractStartTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeIsNull() {
            addCriterion("CONTRACT_END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeIsNotNull() {
            addCriterion("CONTRACT_END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeEqualTo(Date value) {
            addCriterion("CONTRACT_END_TIME =", value, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeNotEqualTo(Date value) {
            addCriterion("CONTRACT_END_TIME <>", value, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeGreaterThan(Date value) {
            addCriterion("CONTRACT_END_TIME >", value, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CONTRACT_END_TIME >=", value, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeLessThan(Date value) {
            addCriterion("CONTRACT_END_TIME <", value, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("CONTRACT_END_TIME <=", value, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeIn(List<Date> values) {
            addCriterion("CONTRACT_END_TIME in", values, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeNotIn(List<Date> values) {
            addCriterion("CONTRACT_END_TIME not in", values, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeBetween(Date value1, Date value2) {
            addCriterion("CONTRACT_END_TIME between", value1, value2, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("CONTRACT_END_TIME not between", value1, value2, "contractEndTime");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeIsNull() {
            addCriterion("CONTRACT_LIMIT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeIsNotNull() {
            addCriterion("CONTRACT_LIMIT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_TYPE =", value, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeNotEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_TYPE <>", value, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeGreaterThan(Integer value) {
            addCriterion("CONTRACT_LIMIT_TYPE >", value, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_TYPE >=", value, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeLessThan(Integer value) {
            addCriterion("CONTRACT_LIMIT_TYPE <", value, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_TYPE <=", value, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeIn(List<Integer> values) {
            addCriterion("CONTRACT_LIMIT_TYPE in", values, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeNotIn(List<Integer> values) {
            addCriterion("CONTRACT_LIMIT_TYPE not in", values, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_LIMIT_TYPE between", value1, value2, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_LIMIT_TYPE not between", value1, value2, "contractLimitType");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumIsNull() {
            addCriterion("CONTRACT_LIMIT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumIsNotNull() {
            addCriterion("CONTRACT_LIMIT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_NUM =", value, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumNotEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_NUM <>", value, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumGreaterThan(Integer value) {
            addCriterion("CONTRACT_LIMIT_NUM >", value, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_NUM >=", value, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumLessThan(Integer value) {
            addCriterion("CONTRACT_LIMIT_NUM <", value, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_NUM <=", value, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumIn(List<Integer> values) {
            addCriterion("CONTRACT_LIMIT_NUM in", values, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumNotIn(List<Integer> values) {
            addCriterion("CONTRACT_LIMIT_NUM not in", values, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_LIMIT_NUM between", value1, value2, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitNumNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_LIMIT_NUM not between", value1, value2, "contractLimitNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumIsNull() {
            addCriterion("CONTRACT_LIMIT_DL_NUM is null");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumIsNotNull() {
            addCriterion("CONTRACT_LIMIT_DL_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_DL_NUM =", value, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumNotEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_DL_NUM <>", value, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumGreaterThan(Integer value) {
            addCriterion("CONTRACT_LIMIT_DL_NUM >", value, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_DL_NUM >=", value, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumLessThan(Integer value) {
            addCriterion("CONTRACT_LIMIT_DL_NUM <", value, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_LIMIT_DL_NUM <=", value, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumIn(List<Integer> values) {
            addCriterion("CONTRACT_LIMIT_DL_NUM in", values, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumNotIn(List<Integer> values) {
            addCriterion("CONTRACT_LIMIT_DL_NUM not in", values, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_LIMIT_DL_NUM between", value1, value2, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractLimitDlNumNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_LIMIT_DL_NUM not between", value1, value2, "contractLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumIsNull() {
            addCriterion("CONTRACT_BUY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumIsNotNull() {
            addCriterion("CONTRACT_BUY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumEqualTo(Integer value) {
            addCriterion("CONTRACT_BUY_NUM =", value, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumNotEqualTo(Integer value) {
            addCriterion("CONTRACT_BUY_NUM <>", value, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumGreaterThan(Integer value) {
            addCriterion("CONTRACT_BUY_NUM >", value, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_BUY_NUM >=", value, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumLessThan(Integer value) {
            addCriterion("CONTRACT_BUY_NUM <", value, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_BUY_NUM <=", value, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumIn(List<Integer> values) {
            addCriterion("CONTRACT_BUY_NUM in", values, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumNotIn(List<Integer> values) {
            addCriterion("CONTRACT_BUY_NUM not in", values, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_BUY_NUM between", value1, value2, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractBuyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_BUY_NUM not between", value1, value2, "contractBuyNum");
            return (Criteria) this;
        }

        public Criteria andContractNumIsNull() {
            addCriterion("CONTRACT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andContractNumIsNotNull() {
            addCriterion("CONTRACT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andContractNumEqualTo(Integer value) {
            addCriterion("CONTRACT_NUM =", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotEqualTo(Integer value) {
            addCriterion("CONTRACT_NUM <>", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumGreaterThan(Integer value) {
            addCriterion("CONTRACT_NUM >", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_NUM >=", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLessThan(Integer value) {
            addCriterion("CONTRACT_NUM <", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_NUM <=", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumIn(List<Integer> values) {
            addCriterion("CONTRACT_NUM in", values, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotIn(List<Integer> values) {
            addCriterion("CONTRACT_NUM not in", values, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_NUM between", value1, value2, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_NUM not between", value1, value2, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumIsNull() {
            addCriterion("CONTRACT_ALL_NUM is null");
            return (Criteria) this;
        }

        public Criteria andContractAllNumIsNotNull() {
            addCriterion("CONTRACT_ALL_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andContractAllNumEqualTo(Integer value) {
            addCriterion("CONTRACT_ALL_NUM =", value, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumNotEqualTo(Integer value) {
            addCriterion("CONTRACT_ALL_NUM <>", value, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumGreaterThan(Integer value) {
            addCriterion("CONTRACT_ALL_NUM >", value, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_ALL_NUM >=", value, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumLessThan(Integer value) {
            addCriterion("CONTRACT_ALL_NUM <", value, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumLessThanOrEqualTo(Integer value) {
            addCriterion("CONTRACT_ALL_NUM <=", value, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumIn(List<Integer> values) {
            addCriterion("CONTRACT_ALL_NUM in", values, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumNotIn(List<Integer> values) {
            addCriterion("CONTRACT_ALL_NUM not in", values, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_ALL_NUM between", value1, value2, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andContractAllNumNotBetween(Integer value1, Integer value2) {
            addCriterion("CONTRACT_ALL_NUM not between", value1, value2, "contractAllNum");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(BigDecimal value) {
            addCriterion("ACCOUNT =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(BigDecimal value) {
            addCriterion("ACCOUNT <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(BigDecimal value) {
            addCriterion("ACCOUNT >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACCOUNT >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(BigDecimal value) {
            addCriterion("ACCOUNT <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACCOUNT <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<BigDecimal> values) {
            addCriterion("ACCOUNT in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<BigDecimal> values) {
            addCriterion("ACCOUNT not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACCOUNT between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACCOUNT not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceIsNull() {
            addCriterion("BALANCE_PERPRICE is null");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceIsNotNull() {
            addCriterion("BALANCE_PERPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceEqualTo(Integer value) {
            addCriterion("BALANCE_PERPRICE =", value, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceNotEqualTo(Integer value) {
            addCriterion("BALANCE_PERPRICE <>", value, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceGreaterThan(Integer value) {
            addCriterion("BALANCE_PERPRICE >", value, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_PERPRICE >=", value, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceLessThan(Integer value) {
            addCriterion("BALANCE_PERPRICE <", value, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_PERPRICE <=", value, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceIn(List<Integer> values) {
            addCriterion("BALANCE_PERPRICE in", values, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceNotIn(List<Integer> values) {
            addCriterion("BALANCE_PERPRICE not in", values, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_PERPRICE between", value1, value2, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalancePerpriceNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_PERPRICE not between", value1, value2, "balancePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceIsNull() {
            addCriterion("BALANCE_BASE_PERPRICE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceIsNotNull() {
            addCriterion("BALANCE_BASE_PERPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceEqualTo(Integer value) {
            addCriterion("BALANCE_BASE_PERPRICE =", value, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceNotEqualTo(Integer value) {
            addCriterion("BALANCE_BASE_PERPRICE <>", value, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceGreaterThan(Integer value) {
            addCriterion("BALANCE_BASE_PERPRICE >", value, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_BASE_PERPRICE >=", value, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceLessThan(Integer value) {
            addCriterion("BALANCE_BASE_PERPRICE <", value, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_BASE_PERPRICE <=", value, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceIn(List<Integer> values) {
            addCriterion("BALANCE_BASE_PERPRICE in", values, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceNotIn(List<Integer> values) {
            addCriterion("BALANCE_BASE_PERPRICE not in", values, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_BASE_PERPRICE between", value1, value2, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceBasePerpriceNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_BASE_PERPRICE not between", value1, value2, "balanceBasePerprice");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleIsNull() {
            addCriterion("BALANCE_SALE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleIsNotNull() {
            addCriterion("BALANCE_SALE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleEqualTo(Integer value) {
            addCriterion("BALANCE_SALE =", value, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleNotEqualTo(Integer value) {
            addCriterion("BALANCE_SALE <>", value, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleGreaterThan(Integer value) {
            addCriterion("BALANCE_SALE >", value, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_SALE >=", value, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleLessThan(Integer value) {
            addCriterion("BALANCE_SALE <", value, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_SALE <=", value, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleIn(List<Integer> values) {
            addCriterion("BALANCE_SALE in", values, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleNotIn(List<Integer> values) {
            addCriterion("BALANCE_SALE not in", values, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_SALE between", value1, value2, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceSaleNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_SALE not between", value1, value2, "balanceSale");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueIsNull() {
            addCriterion("BALANCE_REVENUE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueIsNotNull() {
            addCriterion("BALANCE_REVENUE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueEqualTo(Integer value) {
            addCriterion("BALANCE_REVENUE =", value, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueNotEqualTo(Integer value) {
            addCriterion("BALANCE_REVENUE <>", value, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueGreaterThan(Integer value) {
            addCriterion("BALANCE_REVENUE >", value, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_REVENUE >=", value, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueLessThan(Integer value) {
            addCriterion("BALANCE_REVENUE <", value, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_REVENUE <=", value, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueIn(List<Integer> values) {
            addCriterion("BALANCE_REVENUE in", values, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueNotIn(List<Integer> values) {
            addCriterion("BALANCE_REVENUE not in", values, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_REVENUE between", value1, value2, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceRevenueNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_REVENUE not between", value1, value2, "balanceRevenue");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeIsNull() {
            addCriterion("BALANCE_LIMIT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeIsNotNull() {
            addCriterion("BALANCE_LIMIT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_TYPE =", value, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeNotEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_TYPE <>", value, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeGreaterThan(Integer value) {
            addCriterion("BALANCE_LIMIT_TYPE >", value, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_TYPE >=", value, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeLessThan(Integer value) {
            addCriterion("BALANCE_LIMIT_TYPE <", value, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_TYPE <=", value, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeIn(List<Integer> values) {
            addCriterion("BALANCE_LIMIT_TYPE in", values, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeNotIn(List<Integer> values) {
            addCriterion("BALANCE_LIMIT_TYPE not in", values, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_LIMIT_TYPE between", value1, value2, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_LIMIT_TYPE not between", value1, value2, "balanceLimitType");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumIsNull() {
            addCriterion("BALANCE_LIMIT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumIsNotNull() {
            addCriterion("BALANCE_LIMIT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_NUM =", value, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumNotEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_NUM <>", value, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumGreaterThan(Integer value) {
            addCriterion("BALANCE_LIMIT_NUM >", value, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_NUM >=", value, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumLessThan(Integer value) {
            addCriterion("BALANCE_LIMIT_NUM <", value, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_NUM <=", value, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumIn(List<Integer> values) {
            addCriterion("BALANCE_LIMIT_NUM in", values, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumNotIn(List<Integer> values) {
            addCriterion("BALANCE_LIMIT_NUM not in", values, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_LIMIT_NUM between", value1, value2, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitNumNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_LIMIT_NUM not between", value1, value2, "balanceLimitNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumIsNull() {
            addCriterion("BALANCE_LIMIT_DL_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumIsNotNull() {
            addCriterion("BALANCE_LIMIT_DL_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_DL_NUM =", value, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumNotEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_DL_NUM <>", value, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumGreaterThan(Integer value) {
            addCriterion("BALANCE_LIMIT_DL_NUM >", value, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_DL_NUM >=", value, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumLessThan(Integer value) {
            addCriterion("BALANCE_LIMIT_DL_NUM <", value, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_LIMIT_DL_NUM <=", value, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumIn(List<Integer> values) {
            addCriterion("BALANCE_LIMIT_DL_NUM in", values, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumNotIn(List<Integer> values) {
            addCriterion("BALANCE_LIMIT_DL_NUM not in", values, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_LIMIT_DL_NUM between", value1, value2, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceLimitDlNumNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_LIMIT_DL_NUM not between", value1, value2, "balanceLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumIsNull() {
            addCriterion("BALANCE_ALL_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumIsNotNull() {
            addCriterion("BALANCE_ALL_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumEqualTo(Integer value) {
            addCriterion("BALANCE_ALL_NUM =", value, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumNotEqualTo(Integer value) {
            addCriterion("BALANCE_ALL_NUM <>", value, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumGreaterThan(Integer value) {
            addCriterion("BALANCE_ALL_NUM >", value, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_ALL_NUM >=", value, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumLessThan(Integer value) {
            addCriterion("BALANCE_ALL_NUM <", value, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumLessThanOrEqualTo(Integer value) {
            addCriterion("BALANCE_ALL_NUM <=", value, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumIn(List<Integer> values) {
            addCriterion("BALANCE_ALL_NUM in", values, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumNotIn(List<Integer> values) {
            addCriterion("BALANCE_ALL_NUM not in", values, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_ALL_NUM between", value1, value2, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andBalanceAllNumNotBetween(Integer value1, Integer value2) {
            addCriterion("BALANCE_ALL_NUM not between", value1, value2, "balanceAllNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeIsNull() {
            addCriterion("THREE_LIMIT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeIsNotNull() {
            addCriterion("THREE_LIMIT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_TYPE =", value, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeNotEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_TYPE <>", value, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeGreaterThan(Integer value) {
            addCriterion("THREE_LIMIT_TYPE >", value, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_TYPE >=", value, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeLessThan(Integer value) {
            addCriterion("THREE_LIMIT_TYPE <", value, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeLessThanOrEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_TYPE <=", value, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeIn(List<Integer> values) {
            addCriterion("THREE_LIMIT_TYPE in", values, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeNotIn(List<Integer> values) {
            addCriterion("THREE_LIMIT_TYPE not in", values, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeBetween(Integer value1, Integer value2) {
            addCriterion("THREE_LIMIT_TYPE between", value1, value2, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("THREE_LIMIT_TYPE not between", value1, value2, "threeLimitType");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumIsNull() {
            addCriterion("THREE_LIMIT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumIsNotNull() {
            addCriterion("THREE_LIMIT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_NUM =", value, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumNotEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_NUM <>", value, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumGreaterThan(Integer value) {
            addCriterion("THREE_LIMIT_NUM >", value, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_NUM >=", value, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumLessThan(Integer value) {
            addCriterion("THREE_LIMIT_NUM <", value, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumLessThanOrEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_NUM <=", value, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumIn(List<Integer> values) {
            addCriterion("THREE_LIMIT_NUM in", values, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumNotIn(List<Integer> values) {
            addCriterion("THREE_LIMIT_NUM not in", values, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumBetween(Integer value1, Integer value2) {
            addCriterion("THREE_LIMIT_NUM between", value1, value2, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitNumNotBetween(Integer value1, Integer value2) {
            addCriterion("THREE_LIMIT_NUM not between", value1, value2, "threeLimitNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumIsNull() {
            addCriterion("THREE_LIMIT_DL_NUM is null");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumIsNotNull() {
            addCriterion("THREE_LIMIT_DL_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_DL_NUM =", value, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumNotEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_DL_NUM <>", value, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumGreaterThan(Integer value) {
            addCriterion("THREE_LIMIT_DL_NUM >", value, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_DL_NUM >=", value, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumLessThan(Integer value) {
            addCriterion("THREE_LIMIT_DL_NUM <", value, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumLessThanOrEqualTo(Integer value) {
            addCriterion("THREE_LIMIT_DL_NUM <=", value, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumIn(List<Integer> values) {
            addCriterion("THREE_LIMIT_DL_NUM in", values, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumNotIn(List<Integer> values) {
            addCriterion("THREE_LIMIT_DL_NUM not in", values, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumBetween(Integer value1, Integer value2) {
            addCriterion("THREE_LIMIT_DL_NUM between", value1, value2, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andThreeLimitDlNumNotBetween(Integer value1, Integer value2) {
            addCriterion("THREE_LIMIT_DL_NUM not between", value1, value2, "threeLimitDlNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumIsNull() {
            addCriterion("DOWN_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andDownLineNumIsNotNull() {
            addCriterion("DOWN_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andDownLineNumEqualTo(Integer value) {
            addCriterion("DOWN_LINE_NUM =", value, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumNotEqualTo(Integer value) {
            addCriterion("DOWN_LINE_NUM <>", value, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumGreaterThan(Integer value) {
            addCriterion("DOWN_LINE_NUM >", value, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("DOWN_LINE_NUM >=", value, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumLessThan(Integer value) {
            addCriterion("DOWN_LINE_NUM <", value, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumLessThanOrEqualTo(Integer value) {
            addCriterion("DOWN_LINE_NUM <=", value, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumIn(List<Integer> values) {
            addCriterion("DOWN_LINE_NUM in", values, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumNotIn(List<Integer> values) {
            addCriterion("DOWN_LINE_NUM not in", values, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumBetween(Integer value1, Integer value2) {
            addCriterion("DOWN_LINE_NUM between", value1, value2, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andDownLineNumNotBetween(Integer value1, Integer value2) {
            addCriterion("DOWN_LINE_NUM not between", value1, value2, "downLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumIsNull() {
            addCriterion("ON_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andOnLineNumIsNotNull() {
            addCriterion("ON_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andOnLineNumEqualTo(Integer value) {
            addCriterion("ON_LINE_NUM =", value, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumNotEqualTo(Integer value) {
            addCriterion("ON_LINE_NUM <>", value, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumGreaterThan(Integer value) {
            addCriterion("ON_LINE_NUM >", value, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ON_LINE_NUM >=", value, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumLessThan(Integer value) {
            addCriterion("ON_LINE_NUM <", value, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumLessThanOrEqualTo(Integer value) {
            addCriterion("ON_LINE_NUM <=", value, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumIn(List<Integer> values) {
            addCriterion("ON_LINE_NUM in", values, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumNotIn(List<Integer> values) {
            addCriterion("ON_LINE_NUM not in", values, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumBetween(Integer value1, Integer value2) {
            addCriterion("ON_LINE_NUM between", value1, value2, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andOnLineNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ON_LINE_NUM not between", value1, value2, "onLineNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumIsNull() {
            addCriterion("ALL_DOWNLOAD_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumIsNotNull() {
            addCriterion("ALL_DOWNLOAD_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumEqualTo(Integer value) {
            addCriterion("ALL_DOWNLOAD_NUM =", value, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumNotEqualTo(Integer value) {
            addCriterion("ALL_DOWNLOAD_NUM <>", value, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumGreaterThan(Integer value) {
            addCriterion("ALL_DOWNLOAD_NUM >", value, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ALL_DOWNLOAD_NUM >=", value, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumLessThan(Integer value) {
            addCriterion("ALL_DOWNLOAD_NUM <", value, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumLessThanOrEqualTo(Integer value) {
            addCriterion("ALL_DOWNLOAD_NUM <=", value, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumIn(List<Integer> values) {
            addCriterion("ALL_DOWNLOAD_NUM in", values, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumNotIn(List<Integer> values) {
            addCriterion("ALL_DOWNLOAD_NUM not in", values, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumBetween(Integer value1, Integer value2) {
            addCriterion("ALL_DOWNLOAD_NUM between", value1, value2, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andAllDownloadNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ALL_DOWNLOAD_NUM not between", value1, value2, "allDownloadNum");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNull() {
            addCriterion("UNIT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNotNull() {
            addCriterion("UNIT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNameEqualTo(String value) {
            addCriterion("UNIT_NAME =", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotEqualTo(String value) {
            addCriterion("UNIT_NAME <>", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThan(String value) {
            addCriterion("UNIT_NAME >", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_NAME >=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThan(String value) {
            addCriterion("UNIT_NAME <", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThanOrEqualTo(String value) {
            addCriterion("UNIT_NAME <=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLike(String value) {
            addCriterion("UNIT_NAME like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotLike(String value) {
            addCriterion("UNIT_NAME not like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIn(List<String> values) {
            addCriterion("UNIT_NAME in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotIn(List<String> values) {
            addCriterion("UNIT_NAME not in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameBetween(String value1, String value2) {
            addCriterion("UNIT_NAME between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotBetween(String value1, String value2) {
            addCriterion("UNIT_NAME not between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitAddressIsNull() {
            addCriterion("UNIT_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andUnitAddressIsNotNull() {
            addCriterion("UNIT_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andUnitAddressEqualTo(String value) {
            addCriterion("UNIT_ADDRESS =", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressNotEqualTo(String value) {
            addCriterion("UNIT_ADDRESS <>", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressGreaterThan(String value) {
            addCriterion("UNIT_ADDRESS >", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_ADDRESS >=", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressLessThan(String value) {
            addCriterion("UNIT_ADDRESS <", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressLessThanOrEqualTo(String value) {
            addCriterion("UNIT_ADDRESS <=", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressLike(String value) {
            addCriterion("UNIT_ADDRESS like", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressNotLike(String value) {
            addCriterion("UNIT_ADDRESS not like", value, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressIn(List<String> values) {
            addCriterion("UNIT_ADDRESS in", values, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressNotIn(List<String> values) {
            addCriterion("UNIT_ADDRESS not in", values, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressBetween(String value1, String value2) {
            addCriterion("UNIT_ADDRESS between", value1, value2, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitAddressNotBetween(String value1, String value2) {
            addCriterion("UNIT_ADDRESS not between", value1, value2, "unitAddress");
            return (Criteria) this;
        }

        public Criteria andUnitTelIsNull() {
            addCriterion("UNIT_TEL is null");
            return (Criteria) this;
        }

        public Criteria andUnitTelIsNotNull() {
            addCriterion("UNIT_TEL is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTelEqualTo(String value) {
            addCriterion("UNIT_TEL =", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelNotEqualTo(String value) {
            addCriterion("UNIT_TEL <>", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelGreaterThan(String value) {
            addCriterion("UNIT_TEL >", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_TEL >=", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelLessThan(String value) {
            addCriterion("UNIT_TEL <", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelLessThanOrEqualTo(String value) {
            addCriterion("UNIT_TEL <=", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelLike(String value) {
            addCriterion("UNIT_TEL like", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelNotLike(String value) {
            addCriterion("UNIT_TEL not like", value, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelIn(List<String> values) {
            addCriterion("UNIT_TEL in", values, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelNotIn(List<String> values) {
            addCriterion("UNIT_TEL not in", values, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelBetween(String value1, String value2) {
            addCriterion("UNIT_TEL between", value1, value2, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitTelNotBetween(String value1, String value2) {
            addCriterion("UNIT_TEL not between", value1, value2, "unitTel");
            return (Criteria) this;
        }

        public Criteria andUnitFaxIsNull() {
            addCriterion("UNIT_FAX is null");
            return (Criteria) this;
        }

        public Criteria andUnitFaxIsNotNull() {
            addCriterion("UNIT_FAX is not null");
            return (Criteria) this;
        }

        public Criteria andUnitFaxEqualTo(String value) {
            addCriterion("UNIT_FAX =", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxNotEqualTo(String value) {
            addCriterion("UNIT_FAX <>", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxGreaterThan(String value) {
            addCriterion("UNIT_FAX >", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT_FAX >=", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxLessThan(String value) {
            addCriterion("UNIT_FAX <", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxLessThanOrEqualTo(String value) {
            addCriterion("UNIT_FAX <=", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxLike(String value) {
            addCriterion("UNIT_FAX like", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxNotLike(String value) {
            addCriterion("UNIT_FAX not like", value, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxIn(List<String> values) {
            addCriterion("UNIT_FAX in", values, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxNotIn(List<String> values) {
            addCriterion("UNIT_FAX not in", values, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxBetween(String value1, String value2) {
            addCriterion("UNIT_FAX between", value1, value2, "unitFax");
            return (Criteria) this;
        }

        public Criteria andUnitFaxNotBetween(String value1, String value2) {
            addCriterion("UNIT_FAX not between", value1, value2, "unitFax");
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

        public Criteria andSubscriberTypeIsNull() {
            addCriterion("SUBSCRIBER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeIsNotNull() {
            addCriterion("SUBSCRIBER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeEqualTo(Byte value) {
            addCriterion("SUBSCRIBER_TYPE =", value, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeNotEqualTo(Byte value) {
            addCriterion("SUBSCRIBER_TYPE <>", value, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeGreaterThan(Byte value) {
            addCriterion("SUBSCRIBER_TYPE >", value, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("SUBSCRIBER_TYPE >=", value, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeLessThan(Byte value) {
            addCriterion("SUBSCRIBER_TYPE <", value, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeLessThanOrEqualTo(Byte value) {
            addCriterion("SUBSCRIBER_TYPE <=", value, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeIn(List<Byte> values) {
            addCriterion("SUBSCRIBER_TYPE in", values, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeNotIn(List<Byte> values) {
            addCriterion("SUBSCRIBER_TYPE not in", values, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeBetween(Byte value1, Byte value2) {
            addCriterion("SUBSCRIBER_TYPE between", value1, value2, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andSubscriberTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("SUBSCRIBER_TYPE not between", value1, value2, "subscriberType");
            return (Criteria) this;
        }

        public Criteria andIsPublishIsNull() {
            addCriterion("IS_PUBLISH is null");
            return (Criteria) this;
        }

        public Criteria andIsPublishIsNotNull() {
            addCriterion("IS_PUBLISH is not null");
            return (Criteria) this;
        }

        public Criteria andIsPublishEqualTo(Byte value) {
            addCriterion("IS_PUBLISH =", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotEqualTo(Byte value) {
            addCriterion("IS_PUBLISH <>", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishGreaterThan(Byte value) {
            addCriterion("IS_PUBLISH >", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_PUBLISH >=", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishLessThan(Byte value) {
            addCriterion("IS_PUBLISH <", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishLessThanOrEqualTo(Byte value) {
            addCriterion("IS_PUBLISH <=", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishIn(List<Byte> values) {
            addCriterion("IS_PUBLISH in", values, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotIn(List<Byte> values) {
            addCriterion("IS_PUBLISH not in", values, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishBetween(Byte value1, Byte value2) {
            addCriterion("IS_PUBLISH between", value1, value2, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_PUBLISH not between", value1, value2, "isPublish");
            return (Criteria) this;
        }

        public Criteria andFeeTypeIsNull() {
            addCriterion("FEE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFeeTypeIsNotNull() {
            addCriterion("FEE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFeeTypeEqualTo(Byte value) {
            addCriterion("FEE_TYPE =", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotEqualTo(Byte value) {
            addCriterion("FEE_TYPE <>", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeGreaterThan(Byte value) {
            addCriterion("FEE_TYPE >", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("FEE_TYPE >=", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeLessThan(Byte value) {
            addCriterion("FEE_TYPE <", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("FEE_TYPE <=", value, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeIn(List<Byte> values) {
            addCriterion("FEE_TYPE in", values, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotIn(List<Byte> values) {
            addCriterion("FEE_TYPE not in", values, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeBetween(Byte value1, Byte value2) {
            addCriterion("FEE_TYPE between", value1, value2, "feeType");
            return (Criteria) this;
        }

        public Criteria andFeeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("FEE_TYPE not between", value1, value2, "feeType");
            return (Criteria) this;
        }

        public Criteria andBankAccountIsNull() {
            addCriterion("BANK_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andBankAccountIsNotNull() {
            addCriterion("BANK_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andBankAccountEqualTo(String value) {
            addCriterion("BANK_ACCOUNT =", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotEqualTo(String value) {
            addCriterion("BANK_ACCOUNT <>", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountGreaterThan(String value) {
            addCriterion("BANK_ACCOUNT >", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ACCOUNT >=", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountLessThan(String value) {
            addCriterion("BANK_ACCOUNT <", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountLessThanOrEqualTo(String value) {
            addCriterion("BANK_ACCOUNT <=", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountLike(String value) {
            addCriterion("BANK_ACCOUNT like", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotLike(String value) {
            addCriterion("BANK_ACCOUNT not like", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountIn(List<String> values) {
            addCriterion("BANK_ACCOUNT in", values, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotIn(List<String> values) {
            addCriterion("BANK_ACCOUNT not in", values, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountBetween(String value1, String value2) {
            addCriterion("BANK_ACCOUNT between", value1, value2, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotBetween(String value1, String value2) {
            addCriterion("BANK_ACCOUNT not between", value1, value2, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankUsernameIsNull() {
            addCriterion("BANK_USERNAME is null");
            return (Criteria) this;
        }

        public Criteria andBankUsernameIsNotNull() {
            addCriterion("BANK_USERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andBankUsernameEqualTo(String value) {
            addCriterion("BANK_USERNAME =", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameNotEqualTo(String value) {
            addCriterion("BANK_USERNAME <>", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameGreaterThan(String value) {
            addCriterion("BANK_USERNAME >", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_USERNAME >=", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameLessThan(String value) {
            addCriterion("BANK_USERNAME <", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameLessThanOrEqualTo(String value) {
            addCriterion("BANK_USERNAME <=", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameLike(String value) {
            addCriterion("BANK_USERNAME like", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameNotLike(String value) {
            addCriterion("BANK_USERNAME not like", value, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameIn(List<String> values) {
            addCriterion("BANK_USERNAME in", values, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameNotIn(List<String> values) {
            addCriterion("BANK_USERNAME not in", values, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameBetween(String value1, String value2) {
            addCriterion("BANK_USERNAME between", value1, value2, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankUsernameNotBetween(String value1, String value2) {
            addCriterion("BANK_USERNAME not between", value1, value2, "bankUsername");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("BANK_NAME =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("BANK_NAME <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("BANK_NAME >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_NAME >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("BANK_NAME <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("BANK_NAME <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("BANK_NAME like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("BANK_NAME not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("BANK_NAME in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("BANK_NAME not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("BANK_NAME between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("BANK_NAME not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankIdCardIsNull() {
            addCriterion("BANK_ID_CARD is null");
            return (Criteria) this;
        }

        public Criteria andBankIdCardIsNotNull() {
            addCriterion("BANK_ID_CARD is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdCardEqualTo(String value) {
            addCriterion("BANK_ID_CARD =", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardNotEqualTo(String value) {
            addCriterion("BANK_ID_CARD <>", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardGreaterThan(String value) {
            addCriterion("BANK_ID_CARD >", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ID_CARD >=", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardLessThan(String value) {
            addCriterion("BANK_ID_CARD <", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardLessThanOrEqualTo(String value) {
            addCriterion("BANK_ID_CARD <=", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardLike(String value) {
            addCriterion("BANK_ID_CARD like", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardNotLike(String value) {
            addCriterion("BANK_ID_CARD not like", value, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardIn(List<String> values) {
            addCriterion("BANK_ID_CARD in", values, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardNotIn(List<String> values) {
            addCriterion("BANK_ID_CARD not in", values, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardBetween(String value1, String value2) {
            addCriterion("BANK_ID_CARD between", value1, value2, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andBankIdCardNotBetween(String value1, String value2) {
            addCriterion("BANK_ID_CARD not between", value1, value2, "bankIdCard");
            return (Criteria) this;
        }

        public Criteria andMailAddressIsNull() {
            addCriterion("MAIL_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andMailAddressIsNotNull() {
            addCriterion("MAIL_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andMailAddressEqualTo(String value) {
            addCriterion("MAIL_ADDRESS =", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotEqualTo(String value) {
            addCriterion("MAIL_ADDRESS <>", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressGreaterThan(String value) {
            addCriterion("MAIL_ADDRESS >", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressGreaterThanOrEqualTo(String value) {
            addCriterion("MAIL_ADDRESS >=", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressLessThan(String value) {
            addCriterion("MAIL_ADDRESS <", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressLessThanOrEqualTo(String value) {
            addCriterion("MAIL_ADDRESS <=", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressLike(String value) {
            addCriterion("MAIL_ADDRESS like", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotLike(String value) {
            addCriterion("MAIL_ADDRESS not like", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressIn(List<String> values) {
            addCriterion("MAIL_ADDRESS in", values, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotIn(List<String> values) {
            addCriterion("MAIL_ADDRESS not in", values, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressBetween(String value1, String value2) {
            addCriterion("MAIL_ADDRESS between", value1, value2, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotBetween(String value1, String value2) {
            addCriterion("MAIL_ADDRESS not between", value1, value2, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailUsernameIsNull() {
            addCriterion("MAIL_USERNAME is null");
            return (Criteria) this;
        }

        public Criteria andMailUsernameIsNotNull() {
            addCriterion("MAIL_USERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andMailUsernameEqualTo(String value) {
            addCriterion("MAIL_USERNAME =", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameNotEqualTo(String value) {
            addCriterion("MAIL_USERNAME <>", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameGreaterThan(String value) {
            addCriterion("MAIL_USERNAME >", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("MAIL_USERNAME >=", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameLessThan(String value) {
            addCriterion("MAIL_USERNAME <", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameLessThanOrEqualTo(String value) {
            addCriterion("MAIL_USERNAME <=", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameLike(String value) {
            addCriterion("MAIL_USERNAME like", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameNotLike(String value) {
            addCriterion("MAIL_USERNAME not like", value, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameIn(List<String> values) {
            addCriterion("MAIL_USERNAME in", values, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameNotIn(List<String> values) {
            addCriterion("MAIL_USERNAME not in", values, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameBetween(String value1, String value2) {
            addCriterion("MAIL_USERNAME between", value1, value2, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailUsernameNotBetween(String value1, String value2) {
            addCriterion("MAIL_USERNAME not between", value1, value2, "mailUsername");
            return (Criteria) this;
        }

        public Criteria andMailIdCardIsNull() {
            addCriterion("MAIL_ID_CARD is null");
            return (Criteria) this;
        }

        public Criteria andMailIdCardIsNotNull() {
            addCriterion("MAIL_ID_CARD is not null");
            return (Criteria) this;
        }

        public Criteria andMailIdCardEqualTo(String value) {
            addCriterion("MAIL_ID_CARD =", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardNotEqualTo(String value) {
            addCriterion("MAIL_ID_CARD <>", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardGreaterThan(String value) {
            addCriterion("MAIL_ID_CARD >", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("MAIL_ID_CARD >=", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardLessThan(String value) {
            addCriterion("MAIL_ID_CARD <", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardLessThanOrEqualTo(String value) {
            addCriterion("MAIL_ID_CARD <=", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardLike(String value) {
            addCriterion("MAIL_ID_CARD like", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardNotLike(String value) {
            addCriterion("MAIL_ID_CARD not like", value, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardIn(List<String> values) {
            addCriterion("MAIL_ID_CARD in", values, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardNotIn(List<String> values) {
            addCriterion("MAIL_ID_CARD not in", values, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardBetween(String value1, String value2) {
            addCriterion("MAIL_ID_CARD between", value1, value2, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailIdCardNotBetween(String value1, String value2) {
            addCriterion("MAIL_ID_CARD not between", value1, value2, "mailIdCard");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeIsNull() {
            addCriterion("MAIL_ZIP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeIsNotNull() {
            addCriterion("MAIL_ZIP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeEqualTo(String value) {
            addCriterion("MAIL_ZIP_CODE =", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeNotEqualTo(String value) {
            addCriterion("MAIL_ZIP_CODE <>", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeGreaterThan(String value) {
            addCriterion("MAIL_ZIP_CODE >", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeGreaterThanOrEqualTo(String value) {
            addCriterion("MAIL_ZIP_CODE >=", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeLessThan(String value) {
            addCriterion("MAIL_ZIP_CODE <", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeLessThanOrEqualTo(String value) {
            addCriterion("MAIL_ZIP_CODE <=", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeLike(String value) {
            addCriterion("MAIL_ZIP_CODE like", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeNotLike(String value) {
            addCriterion("MAIL_ZIP_CODE not like", value, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeIn(List<String> values) {
            addCriterion("MAIL_ZIP_CODE in", values, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeNotIn(List<String> values) {
            addCriterion("MAIL_ZIP_CODE not in", values, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeBetween(String value1, String value2) {
            addCriterion("MAIL_ZIP_CODE between", value1, value2, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andMailZipCodeNotBetween(String value1, String value2) {
            addCriterion("MAIL_ZIP_CODE not between", value1, value2, "mailZipCode");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("DISCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("DISCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(Integer value) {
            addCriterion("DISCOUNT =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Integer value) {
            addCriterion("DISCOUNT <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Integer value) {
            addCriterion("DISCOUNT >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Integer value) {
            addCriterion("DISCOUNT >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Integer value) {
            addCriterion("DISCOUNT <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Integer value) {
            addCriterion("DISCOUNT <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Integer> values) {
            addCriterion("DISCOUNT in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Integer> values) {
            addCriterion("DISCOUNT not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Integer value1, Integer value2) {
            addCriterion("DISCOUNT between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Integer value1, Integer value2) {
            addCriterion("DISCOUNT not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionIsNull() {
            addCriterion("PHOTOGRAPHY_DIRECTION is null");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionIsNotNull() {
            addCriterion("PHOTOGRAPHY_DIRECTION is not null");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionEqualTo(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION =", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionNotEqualTo(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION <>", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionGreaterThan(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION >", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionGreaterThanOrEqualTo(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION >=", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionLessThan(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION <", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionLessThanOrEqualTo(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION <=", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionLike(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION like", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionNotLike(String value) {
            addCriterion("PHOTOGRAPHY_DIRECTION not like", value, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionIn(List<String> values) {
            addCriterion("PHOTOGRAPHY_DIRECTION in", values, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionNotIn(List<String> values) {
            addCriterion("PHOTOGRAPHY_DIRECTION not in", values, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionBetween(String value1, String value2) {
            addCriterion("PHOTOGRAPHY_DIRECTION between", value1, value2, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andPhotographyDirectionNotBetween(String value1, String value2) {
            addCriterion("PHOTOGRAPHY_DIRECTION not between", value1, value2, "photographyDirection");
            return (Criteria) this;
        }

        public Criteria andZoneIsNull() {
            addCriterion("ZONE is null");
            return (Criteria) this;
        }

        public Criteria andZoneIsNotNull() {
            addCriterion("ZONE is not null");
            return (Criteria) this;
        }

        public Criteria andZoneEqualTo(String value) {
            addCriterion("ZONE =", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneNotEqualTo(String value) {
            addCriterion("ZONE <>", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneGreaterThan(String value) {
            addCriterion("ZONE >", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneGreaterThanOrEqualTo(String value) {
            addCriterion("ZONE >=", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneLessThan(String value) {
            addCriterion("ZONE <", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneLessThanOrEqualTo(String value) {
            addCriterion("ZONE <=", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneLike(String value) {
            addCriterion("ZONE like", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneNotLike(String value) {
            addCriterion("ZONE not like", value, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneIn(List<String> values) {
            addCriterion("ZONE in", values, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneNotIn(List<String> values) {
            addCriterion("ZONE not in", values, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneBetween(String value1, String value2) {
            addCriterion("ZONE between", value1, value2, "zone");
            return (Criteria) this;
        }

        public Criteria andZoneNotBetween(String value1, String value2) {
            addCriterion("ZONE not between", value1, value2, "zone");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeIsNull() {
            addCriterion("SUBSCRIPTION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeIsNotNull() {
            addCriterion("SUBSCRIPTION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeEqualTo(Byte value) {
            addCriterion("SUBSCRIPTION_TYPE =", value, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeNotEqualTo(Byte value) {
            addCriterion("SUBSCRIPTION_TYPE <>", value, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeGreaterThan(Byte value) {
            addCriterion("SUBSCRIPTION_TYPE >", value, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("SUBSCRIPTION_TYPE >=", value, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeLessThan(Byte value) {
            addCriterion("SUBSCRIPTION_TYPE <", value, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeLessThanOrEqualTo(Byte value) {
            addCriterion("SUBSCRIPTION_TYPE <=", value, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeIn(List<Byte> values) {
            addCriterion("SUBSCRIPTION_TYPE in", values, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeNotIn(List<Byte> values) {
            addCriterion("SUBSCRIPTION_TYPE not in", values, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeBetween(Byte value1, Byte value2) {
            addCriterion("SUBSCRIPTION_TYPE between", value1, value2, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andSubscriptionTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("SUBSCRIPTION_TYPE not between", value1, value2, "subscriptionType");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("PROVINCE is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("PROVINCE is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("PROVINCE =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("PROVINCE <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("PROVINCE >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCE >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("PROVINCE <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("PROVINCE <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("PROVINCE like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("PROVINCE not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("PROVINCE in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("PROVINCE not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("PROVINCE between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("PROVINCE not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("CITY is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("CITY is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("CITY =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("CITY <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("CITY >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("CITY >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("CITY <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("CITY <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("CITY like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("CITY not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("CITY in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("CITY not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("CITY between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("CITY not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNull() {
            addCriterion("ID_CARD is null");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNotNull() {
            addCriterion("ID_CARD is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardEqualTo(String value) {
            addCriterion("ID_CARD =", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("ID_CARD <>", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("ID_CARD >", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("ID_CARD >=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThan(String value) {
            addCriterion("ID_CARD <", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("ID_CARD <=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLike(String value) {
            addCriterion("ID_CARD like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotLike(String value) {
            addCriterion("ID_CARD not like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardIn(List<String> values) {
            addCriterion("ID_CARD in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("ID_CARD not in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("ID_CARD between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("ID_CARD not between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelIsNull() {
            addCriterion("PHOTOGRAPHER_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelIsNotNull() {
            addCriterion("PHOTOGRAPHER_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelEqualTo(Byte value) {
            addCriterion("PHOTOGRAPHER_LEVEL =", value, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelNotEqualTo(Byte value) {
            addCriterion("PHOTOGRAPHER_LEVEL <>", value, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelGreaterThan(Byte value) {
            addCriterion("PHOTOGRAPHER_LEVEL >", value, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("PHOTOGRAPHER_LEVEL >=", value, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelLessThan(Byte value) {
            addCriterion("PHOTOGRAPHER_LEVEL <", value, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelLessThanOrEqualTo(Byte value) {
            addCriterion("PHOTOGRAPHER_LEVEL <=", value, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelIn(List<Byte> values) {
            addCriterion("PHOTOGRAPHER_LEVEL in", values, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelNotIn(List<Byte> values) {
            addCriterion("PHOTOGRAPHER_LEVEL not in", values, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelBetween(Byte value1, Byte value2) {
            addCriterion("PHOTOGRAPHER_LEVEL between", value1, value2, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andPhotographerLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("PHOTOGRAPHER_LEVEL not between", value1, value2, "photographerLevel");
            return (Criteria) this;
        }

        public Criteria andLogincountIsNull() {
            addCriterion("LOGINCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andLogincountIsNotNull() {
            addCriterion("LOGINCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andLogincountEqualTo(Integer value) {
            addCriterion("LOGINCOUNT =", value, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountNotEqualTo(Integer value) {
            addCriterion("LOGINCOUNT <>", value, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountGreaterThan(Integer value) {
            addCriterion("LOGINCOUNT >", value, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOGINCOUNT >=", value, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountLessThan(Integer value) {
            addCriterion("LOGINCOUNT <", value, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountLessThanOrEqualTo(Integer value) {
            addCriterion("LOGINCOUNT <=", value, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountIn(List<Integer> values) {
            addCriterion("LOGINCOUNT in", values, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountNotIn(List<Integer> values) {
            addCriterion("LOGINCOUNT not in", values, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountBetween(Integer value1, Integer value2) {
            addCriterion("LOGINCOUNT between", value1, value2, "logincount");
            return (Criteria) this;
        }

        public Criteria andLogincountNotBetween(Integer value1, Integer value2) {
            addCriterion("LOGINCOUNT not between", value1, value2, "logincount");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNull() {
            addCriterion("LOGIN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNotNull() {
            addCriterion("LOGIN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeEqualTo(Date value) {
            addCriterion("LOGIN_TIME =", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotEqualTo(Date value) {
            addCriterion("LOGIN_TIME <>", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThan(Date value) {
            addCriterion("LOGIN_TIME >", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LOGIN_TIME >=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThan(Date value) {
            addCriterion("LOGIN_TIME <", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("LOGIN_TIME <=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIn(List<Date> values) {
            addCriterion("LOGIN_TIME in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotIn(List<Date> values) {
            addCriterion("LOGIN_TIME not in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeBetween(Date value1, Date value2) {
            addCriterion("LOGIN_TIME between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("LOGIN_TIME not between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("LAST_LOGIN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("LAST_LOGIN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(Date value) {
            addCriterion("LAST_LOGIN_TIME =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(Date value) {
            addCriterion("LAST_LOGIN_TIME <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(Date value) {
            addCriterion("LAST_LOGIN_TIME >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_LOGIN_TIME >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(Date value) {
            addCriterion("LAST_LOGIN_TIME <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("LAST_LOGIN_TIME <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<Date> values) {
            addCriterion("LAST_LOGIN_TIME in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<Date> values) {
            addCriterion("LAST_LOGIN_TIME not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(Date value1, Date value2) {
            addCriterion("LAST_LOGIN_TIME between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("LAST_LOGIN_TIME not between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andUserOrderIsNull() {
            addCriterion("USER_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andUserOrderIsNotNull() {
            addCriterion("USER_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andUserOrderEqualTo(Integer value) {
            addCriterion("USER_ORDER =", value, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderNotEqualTo(Integer value) {
            addCriterion("USER_ORDER <>", value, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderGreaterThan(Integer value) {
            addCriterion("USER_ORDER >", value, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_ORDER >=", value, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderLessThan(Integer value) {
            addCriterion("USER_ORDER <", value, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderLessThanOrEqualTo(Integer value) {
            addCriterion("USER_ORDER <=", value, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderIn(List<Integer> values) {
            addCriterion("USER_ORDER in", values, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderNotIn(List<Integer> values) {
            addCriterion("USER_ORDER not in", values, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderBetween(Integer value1, Integer value2) {
            addCriterion("USER_ORDER between", value1, value2, "userOrder");
            return (Criteria) this;
        }

        public Criteria andUserOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_ORDER not between", value1, value2, "userOrder");
            return (Criteria) this;
        }

        public Criteria andRockTimeIsNull() {
            addCriterion("ROCK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRockTimeIsNotNull() {
            addCriterion("ROCK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRockTimeEqualTo(Date value) {
            addCriterion("ROCK_TIME =", value, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeNotEqualTo(Date value) {
            addCriterion("ROCK_TIME <>", value, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeGreaterThan(Date value) {
            addCriterion("ROCK_TIME >", value, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ROCK_TIME >=", value, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeLessThan(Date value) {
            addCriterion("ROCK_TIME <", value, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeLessThanOrEqualTo(Date value) {
            addCriterion("ROCK_TIME <=", value, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeIn(List<Date> values) {
            addCriterion("ROCK_TIME in", values, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeNotIn(List<Date> values) {
            addCriterion("ROCK_TIME not in", values, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeBetween(Date value1, Date value2) {
            addCriterion("ROCK_TIME between", value1, value2, "rockTime");
            return (Criteria) this;
        }

        public Criteria andRockTimeNotBetween(Date value1, Date value2) {
            addCriterion("ROCK_TIME not between", value1, value2, "rockTime");
            return (Criteria) this;
        }

        public Criteria andLoginNoIsNull() {
            addCriterion("LOGIN_NO is null");
            return (Criteria) this;
        }

        public Criteria andLoginNoIsNotNull() {
            addCriterion("LOGIN_NO is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNoEqualTo(Integer value) {
            addCriterion("LOGIN_NO =", value, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoNotEqualTo(Integer value) {
            addCriterion("LOGIN_NO <>", value, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoGreaterThan(Integer value) {
            addCriterion("LOGIN_NO >", value, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOGIN_NO >=", value, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoLessThan(Integer value) {
            addCriterion("LOGIN_NO <", value, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoLessThanOrEqualTo(Integer value) {
            addCriterion("LOGIN_NO <=", value, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoIn(List<Integer> values) {
            addCriterion("LOGIN_NO in", values, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoNotIn(List<Integer> values) {
            addCriterion("LOGIN_NO not in", values, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoBetween(Integer value1, Integer value2) {
            addCriterion("LOGIN_NO between", value1, value2, "loginNo");
            return (Criteria) this;
        }

        public Criteria andLoginNoNotBetween(Integer value1, Integer value2) {
            addCriterion("LOGIN_NO not between", value1, value2, "loginNo");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNull() {
            addCriterion("USER_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNotNull() {
            addCriterion("USER_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andUserStatusEqualTo(Integer value) {
            addCriterion("USER_STATUS =", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotEqualTo(Integer value) {
            addCriterion("USER_STATUS <>", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThan(Integer value) {
            addCriterion("USER_STATUS >", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_STATUS >=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThan(Integer value) {
            addCriterion("USER_STATUS <", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThanOrEqualTo(Integer value) {
            addCriterion("USER_STATUS <=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusIn(List<Integer> values) {
            addCriterion("USER_STATUS in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotIn(List<Integer> values) {
            addCriterion("USER_STATUS not in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusBetween(Integer value1, Integer value2) {
            addCriterion("USER_STATUS between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_STATUS not between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andQuestionIsNull() {
            addCriterion("QUESTION is null");
            return (Criteria) this;
        }

        public Criteria andQuestionIsNotNull() {
            addCriterion("QUESTION is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionEqualTo(String value) {
            addCriterion("QUESTION =", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotEqualTo(String value) {
            addCriterion("QUESTION <>", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionGreaterThan(String value) {
            addCriterion("QUESTION >", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionGreaterThanOrEqualTo(String value) {
            addCriterion("QUESTION >=", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionLessThan(String value) {
            addCriterion("QUESTION <", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionLessThanOrEqualTo(String value) {
            addCriterion("QUESTION <=", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionLike(String value) {
            addCriterion("QUESTION like", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotLike(String value) {
            addCriterion("QUESTION not like", value, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionIn(List<String> values) {
            addCriterion("QUESTION in", values, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotIn(List<String> values) {
            addCriterion("QUESTION not in", values, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionBetween(String value1, String value2) {
            addCriterion("QUESTION between", value1, value2, "question");
            return (Criteria) this;
        }

        public Criteria andQuestionNotBetween(String value1, String value2) {
            addCriterion("QUESTION not between", value1, value2, "question");
            return (Criteria) this;
        }

        public Criteria andAnswerIsNull() {
            addCriterion("ANSWER is null");
            return (Criteria) this;
        }

        public Criteria andAnswerIsNotNull() {
            addCriterion("ANSWER is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerEqualTo(String value) {
            addCriterion("ANSWER =", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotEqualTo(String value) {
            addCriterion("ANSWER <>", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThan(String value) {
            addCriterion("ANSWER >", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("ANSWER >=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThan(String value) {
            addCriterion("ANSWER <", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThanOrEqualTo(String value) {
            addCriterion("ANSWER <=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLike(String value) {
            addCriterion("ANSWER like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotLike(String value) {
            addCriterion("ANSWER not like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerIn(List<String> values) {
            addCriterion("ANSWER in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotIn(List<String> values) {
            addCriterion("ANSWER not in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerBetween(String value1, String value2) {
            addCriterion("ANSWER between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotBetween(String value1, String value2) {
            addCriterion("ANSWER not between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andIsUnitIsNull() {
            addCriterion("IS_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andIsUnitIsNotNull() {
            addCriterion("IS_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andIsUnitEqualTo(Byte value) {
            addCriterion("IS_UNIT =", value, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitNotEqualTo(Byte value) {
            addCriterion("IS_UNIT <>", value, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitGreaterThan(Byte value) {
            addCriterion("IS_UNIT >", value, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitGreaterThanOrEqualTo(Byte value) {
            addCriterion("IS_UNIT >=", value, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitLessThan(Byte value) {
            addCriterion("IS_UNIT <", value, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitLessThanOrEqualTo(Byte value) {
            addCriterion("IS_UNIT <=", value, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitIn(List<Byte> values) {
            addCriterion("IS_UNIT in", values, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitNotIn(List<Byte> values) {
            addCriterion("IS_UNIT not in", values, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitBetween(Byte value1, Byte value2) {
            addCriterion("IS_UNIT between", value1, value2, "isUnit");
            return (Criteria) this;
        }

        public Criteria andIsUnitNotBetween(Byte value1, Byte value2) {
            addCriterion("IS_UNIT not between", value1, value2, "isUnit");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Byte value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Byte value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Byte value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Byte value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Byte value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Byte> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Byte> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Byte value1, Byte value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andSmsdutyIsNull() {
            addCriterion("SMSDUTY is null");
            return (Criteria) this;
        }

        public Criteria andSmsdutyIsNotNull() {
            addCriterion("SMSDUTY is not null");
            return (Criteria) this;
        }

        public Criteria andSmsdutyEqualTo(Byte value) {
            addCriterion("SMSDUTY =", value, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyNotEqualTo(Byte value) {
            addCriterion("SMSDUTY <>", value, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyGreaterThan(Byte value) {
            addCriterion("SMSDUTY >", value, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyGreaterThanOrEqualTo(Byte value) {
            addCriterion("SMSDUTY >=", value, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyLessThan(Byte value) {
            addCriterion("SMSDUTY <", value, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyLessThanOrEqualTo(Byte value) {
            addCriterion("SMSDUTY <=", value, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyIn(List<Byte> values) {
            addCriterion("SMSDUTY in", values, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyNotIn(List<Byte> values) {
            addCriterion("SMSDUTY not in", values, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyBetween(Byte value1, Byte value2) {
            addCriterion("SMSDUTY between", value1, value2, "smsduty");
            return (Criteria) this;
        }

        public Criteria andSmsdutyNotBetween(Byte value1, Byte value2) {
            addCriterion("SMSDUTY not between", value1, value2, "smsduty");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordIsNull() {
            addCriterion("PLAIN_PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordIsNotNull() {
            addCriterion("PLAIN_PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordEqualTo(String value) {
            addCriterion("PLAIN_PASSWORD =", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordNotEqualTo(String value) {
            addCriterion("PLAIN_PASSWORD <>", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordGreaterThan(String value) {
            addCriterion("PLAIN_PASSWORD >", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("PLAIN_PASSWORD >=", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordLessThan(String value) {
            addCriterion("PLAIN_PASSWORD <", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordLessThanOrEqualTo(String value) {
            addCriterion("PLAIN_PASSWORD <=", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordLike(String value) {
            addCriterion("PLAIN_PASSWORD like", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordNotLike(String value) {
            addCriterion("PLAIN_PASSWORD not like", value, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordIn(List<String> values) {
            addCriterion("PLAIN_PASSWORD in", values, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordNotIn(List<String> values) {
            addCriterion("PLAIN_PASSWORD not in", values, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordBetween(String value1, String value2) {
            addCriterion("PLAIN_PASSWORD between", value1, value2, "plainPassword");
            return (Criteria) this;
        }

        public Criteria andPlainPasswordNotBetween(String value1, String value2) {
            addCriterion("PLAIN_PASSWORD not between", value1, value2, "plainPassword");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
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

        public Criteria andRolenamesIsNull() {
            addCriterion("ROLENAMES is null");
            return (Criteria) this;
        }

        public Criteria andRolenamesIsNotNull() {
            addCriterion("ROLENAMES is not null");
            return (Criteria) this;
        }

        public Criteria andRolenamesEqualTo(String value) {
            addCriterion("ROLENAMES =", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesNotEqualTo(String value) {
            addCriterion("ROLENAMES <>", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesGreaterThan(String value) {
            addCriterion("ROLENAMES >", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesGreaterThanOrEqualTo(String value) {
            addCriterion("ROLENAMES >=", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesLessThan(String value) {
            addCriterion("ROLENAMES <", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesLessThanOrEqualTo(String value) {
            addCriterion("ROLENAMES <=", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesLike(String value) {
            addCriterion("ROLENAMES like", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesNotLike(String value) {
            addCriterion("ROLENAMES not like", value, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesIn(List<String> values) {
            addCriterion("ROLENAMES in", values, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesNotIn(List<String> values) {
            addCriterion("ROLENAMES not in", values, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesBetween(String value1, String value2) {
            addCriterion("ROLENAMES between", value1, value2, "rolenames");
            return (Criteria) this;
        }

        public Criteria andRolenamesNotBetween(String value1, String value2) {
            addCriterion("ROLENAMES not between", value1, value2, "rolenames");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("USER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("USER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("USER_TYPE =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("USER_TYPE <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("USER_TYPE >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_TYPE >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("USER_TYPE <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("USER_TYPE <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("USER_TYPE in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("USER_TYPE not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("USER_TYPE between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_TYPE not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserDivideIsNull() {
            addCriterion("USER_DIVIDE is null");
            return (Criteria) this;
        }

        public Criteria andUserDivideIsNotNull() {
            addCriterion("USER_DIVIDE is not null");
            return (Criteria) this;
        }

        public Criteria andUserDivideEqualTo(Integer value) {
            addCriterion("USER_DIVIDE =", value, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideNotEqualTo(Integer value) {
            addCriterion("USER_DIVIDE <>", value, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideGreaterThan(Integer value) {
            addCriterion("USER_DIVIDE >", value, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_DIVIDE >=", value, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideLessThan(Integer value) {
            addCriterion("USER_DIVIDE <", value, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideLessThanOrEqualTo(Integer value) {
            addCriterion("USER_DIVIDE <=", value, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideIn(List<Integer> values) {
            addCriterion("USER_DIVIDE in", values, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideNotIn(List<Integer> values) {
            addCriterion("USER_DIVIDE not in", values, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideBetween(Integer value1, Integer value2) {
            addCriterion("USER_DIVIDE between", value1, value2, "userDivide");
            return (Criteria) this;
        }

        public Criteria andUserDivideNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_DIVIDE not between", value1, value2, "userDivide");
            return (Criteria) this;
        }

        public Criteria andStandby1IsNull() {
            addCriterion("STANDBY1 is null");
            return (Criteria) this;
        }

        public Criteria andStandby1IsNotNull() {
            addCriterion("STANDBY1 is not null");
            return (Criteria) this;
        }

        public Criteria andStandby1EqualTo(String value) {
            addCriterion("STANDBY1 =", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1NotEqualTo(String value) {
            addCriterion("STANDBY1 <>", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1GreaterThan(String value) {
            addCriterion("STANDBY1 >", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1GreaterThanOrEqualTo(String value) {
            addCriterion("STANDBY1 >=", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1LessThan(String value) {
            addCriterion("STANDBY1 <", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1LessThanOrEqualTo(String value) {
            addCriterion("STANDBY1 <=", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1Like(String value) {
            addCriterion("STANDBY1 like", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1NotLike(String value) {
            addCriterion("STANDBY1 not like", value, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1In(List<String> values) {
            addCriterion("STANDBY1 in", values, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1NotIn(List<String> values) {
            addCriterion("STANDBY1 not in", values, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1Between(String value1, String value2) {
            addCriterion("STANDBY1 between", value1, value2, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby1NotBetween(String value1, String value2) {
            addCriterion("STANDBY1 not between", value1, value2, "standby1");
            return (Criteria) this;
        }

        public Criteria andStandby2IsNull() {
            addCriterion("STANDBY2 is null");
            return (Criteria) this;
        }

        public Criteria andStandby2IsNotNull() {
            addCriterion("STANDBY2 is not null");
            return (Criteria) this;
        }

        public Criteria andStandby2EqualTo(String value) {
            addCriterion("STANDBY2 =", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2NotEqualTo(String value) {
            addCriterion("STANDBY2 <>", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2GreaterThan(String value) {
            addCriterion("STANDBY2 >", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2GreaterThanOrEqualTo(String value) {
            addCriterion("STANDBY2 >=", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2LessThan(String value) {
            addCriterion("STANDBY2 <", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2LessThanOrEqualTo(String value) {
            addCriterion("STANDBY2 <=", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2Like(String value) {
            addCriterion("STANDBY2 like", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2NotLike(String value) {
            addCriterion("STANDBY2 not like", value, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2In(List<String> values) {
            addCriterion("STANDBY2 in", values, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2NotIn(List<String> values) {
            addCriterion("STANDBY2 not in", values, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2Between(String value1, String value2) {
            addCriterion("STANDBY2 between", value1, value2, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby2NotBetween(String value1, String value2) {
            addCriterion("STANDBY2 not between", value1, value2, "standby2");
            return (Criteria) this;
        }

        public Criteria andStandby3IsNull() {
            addCriterion("STANDBY3 is null");
            return (Criteria) this;
        }

        public Criteria andStandby3IsNotNull() {
            addCriterion("STANDBY3 is not null");
            return (Criteria) this;
        }

        public Criteria andStandby3EqualTo(String value) {
            addCriterion("STANDBY3 =", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3NotEqualTo(String value) {
            addCriterion("STANDBY3 <>", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3GreaterThan(String value) {
            addCriterion("STANDBY3 >", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3GreaterThanOrEqualTo(String value) {
            addCriterion("STANDBY3 >=", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3LessThan(String value) {
            addCriterion("STANDBY3 <", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3LessThanOrEqualTo(String value) {
            addCriterion("STANDBY3 <=", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3Like(String value) {
            addCriterion("STANDBY3 like", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3NotLike(String value) {
            addCriterion("STANDBY3 not like", value, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3In(List<String> values) {
            addCriterion("STANDBY3 in", values, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3NotIn(List<String> values) {
            addCriterion("STANDBY3 not in", values, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3Between(String value1, String value2) {
            addCriterion("STANDBY3 between", value1, value2, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby3NotBetween(String value1, String value2) {
            addCriterion("STANDBY3 not between", value1, value2, "standby3");
            return (Criteria) this;
        }

        public Criteria andStandby4IsNull() {
            addCriterion("STANDBY4 is null");
            return (Criteria) this;
        }

        public Criteria andStandby4IsNotNull() {
            addCriterion("STANDBY4 is not null");
            return (Criteria) this;
        }

        public Criteria andStandby4EqualTo(String value) {
            addCriterion("STANDBY4 =", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4NotEqualTo(String value) {
            addCriterion("STANDBY4 <>", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4GreaterThan(String value) {
            addCriterion("STANDBY4 >", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4GreaterThanOrEqualTo(String value) {
            addCriterion("STANDBY4 >=", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4LessThan(String value) {
            addCriterion("STANDBY4 <", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4LessThanOrEqualTo(String value) {
            addCriterion("STANDBY4 <=", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4Like(String value) {
            addCriterion("STANDBY4 like", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4NotLike(String value) {
            addCriterion("STANDBY4 not like", value, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4In(List<String> values) {
            addCriterion("STANDBY4 in", values, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4NotIn(List<String> values) {
            addCriterion("STANDBY4 not in", values, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4Between(String value1, String value2) {
            addCriterion("STANDBY4 between", value1, value2, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby4NotBetween(String value1, String value2) {
            addCriterion("STANDBY4 not between", value1, value2, "standby4");
            return (Criteria) this;
        }

        public Criteria andStandby5IsNull() {
            addCriterion("STANDBY5 is null");
            return (Criteria) this;
        }

        public Criteria andStandby5IsNotNull() {
            addCriterion("STANDBY5 is not null");
            return (Criteria) this;
        }

        public Criteria andStandby5EqualTo(String value) {
            addCriterion("STANDBY5 =", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5NotEqualTo(String value) {
            addCriterion("STANDBY5 <>", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5GreaterThan(String value) {
            addCriterion("STANDBY5 >", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5GreaterThanOrEqualTo(String value) {
            addCriterion("STANDBY5 >=", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5LessThan(String value) {
            addCriterion("STANDBY5 <", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5LessThanOrEqualTo(String value) {
            addCriterion("STANDBY5 <=", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5Like(String value) {
            addCriterion("STANDBY5 like", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5NotLike(String value) {
            addCriterion("STANDBY5 not like", value, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5In(List<String> values) {
            addCriterion("STANDBY5 in", values, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5NotIn(List<String> values) {
            addCriterion("STANDBY5 not in", values, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5Between(String value1, String value2) {
            addCriterion("STANDBY5 between", value1, value2, "standby5");
            return (Criteria) this;
        }

        public Criteria andStandby5NotBetween(String value1, String value2) {
            addCriterion("STANDBY5 not between", value1, value2, "standby5");
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