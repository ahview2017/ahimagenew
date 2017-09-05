package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpOldpictureExcelInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpOldpictureExcelInfoExample() {
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

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andUploadDateIsNull() {
            addCriterion("UPLOAD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUploadDateIsNotNull() {
            addCriterion("UPLOAD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUploadDateEqualTo(Date value) {
            addCriterion("UPLOAD_DATE =", value, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateNotEqualTo(Date value) {
            addCriterion("UPLOAD_DATE <>", value, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateGreaterThan(Date value) {
            addCriterion("UPLOAD_DATE >", value, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPLOAD_DATE >=", value, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateLessThan(Date value) {
            addCriterion("UPLOAD_DATE <", value, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateLessThanOrEqualTo(Date value) {
            addCriterion("UPLOAD_DATE <=", value, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateIn(List<Date> values) {
            addCriterion("UPLOAD_DATE in", values, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateNotIn(List<Date> values) {
            addCriterion("UPLOAD_DATE not in", values, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateBetween(Date value1, Date value2) {
            addCriterion("UPLOAD_DATE between", value1, value2, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadDateNotBetween(Date value1, Date value2) {
            addCriterion("UPLOAD_DATE not between", value1, value2, "uploadDate");
            return (Criteria) this;
        }

        public Criteria andUploadUseridIsNull() {
            addCriterion("UPLOAD_USERID is null");
            return (Criteria) this;
        }

        public Criteria andUploadUseridIsNotNull() {
            addCriterion("UPLOAD_USERID is not null");
            return (Criteria) this;
        }

        public Criteria andUploadUseridEqualTo(Integer value) {
            addCriterion("UPLOAD_USERID =", value, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridNotEqualTo(Integer value) {
            addCriterion("UPLOAD_USERID <>", value, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridGreaterThan(Integer value) {
            addCriterion("UPLOAD_USERID >", value, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("UPLOAD_USERID >=", value, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridLessThan(Integer value) {
            addCriterion("UPLOAD_USERID <", value, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridLessThanOrEqualTo(Integer value) {
            addCriterion("UPLOAD_USERID <=", value, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridIn(List<Integer> values) {
            addCriterion("UPLOAD_USERID in", values, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridNotIn(List<Integer> values) {
            addCriterion("UPLOAD_USERID not in", values, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridBetween(Integer value1, Integer value2) {
            addCriterion("UPLOAD_USERID between", value1, value2, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andUploadUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("UPLOAD_USERID not between", value1, value2, "uploadUserid");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("FILE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("FILE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("FILE_PATH =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("FILE_PATH <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("FILE_PATH >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PATH >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("FILE_PATH <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("FILE_PATH <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("FILE_PATH like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("FILE_PATH not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("FILE_PATH in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("FILE_PATH not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("FILE_PATH between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("FILE_PATH not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumIsNull() {
            addCriterion("PIC_UPLOAD_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumIsNotNull() {
            addCriterion("PIC_UPLOAD_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumEqualTo(Integer value) {
            addCriterion("PIC_UPLOAD_NUM =", value, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumNotEqualTo(Integer value) {
            addCriterion("PIC_UPLOAD_NUM <>", value, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumGreaterThan(Integer value) {
            addCriterion("PIC_UPLOAD_NUM >", value, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("PIC_UPLOAD_NUM >=", value, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumLessThan(Integer value) {
            addCriterion("PIC_UPLOAD_NUM <", value, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumLessThanOrEqualTo(Integer value) {
            addCriterion("PIC_UPLOAD_NUM <=", value, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumIn(List<Integer> values) {
            addCriterion("PIC_UPLOAD_NUM in", values, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumNotIn(List<Integer> values) {
            addCriterion("PIC_UPLOAD_NUM not in", values, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumBetween(Integer value1, Integer value2) {
            addCriterion("PIC_UPLOAD_NUM between", value1, value2, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andPicUploadNumNotBetween(Integer value1, Integer value2) {
            addCriterion("PIC_UPLOAD_NUM not between", value1, value2, "picUploadNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumIsNull() {
            addCriterion("STORAGE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andStorageNumIsNotNull() {
            addCriterion("STORAGE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andStorageNumEqualTo(Integer value) {
            addCriterion("STORAGE_NUM =", value, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumNotEqualTo(Integer value) {
            addCriterion("STORAGE_NUM <>", value, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumGreaterThan(Integer value) {
            addCriterion("STORAGE_NUM >", value, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("STORAGE_NUM >=", value, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumLessThan(Integer value) {
            addCriterion("STORAGE_NUM <", value, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumLessThanOrEqualTo(Integer value) {
            addCriterion("STORAGE_NUM <=", value, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumIn(List<Integer> values) {
            addCriterion("STORAGE_NUM in", values, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumNotIn(List<Integer> values) {
            addCriterion("STORAGE_NUM not in", values, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumBetween(Integer value1, Integer value2) {
            addCriterion("STORAGE_NUM between", value1, value2, "storageNum");
            return (Criteria) this;
        }

        public Criteria andStorageNumNotBetween(Integer value1, Integer value2) {
            addCriterion("STORAGE_NUM not between", value1, value2, "storageNum");
            return (Criteria) this;
        }

        public Criteria andRecordTotalIsNull() {
            addCriterion("RECORD_TOTAL is null");
            return (Criteria) this;
        }

        public Criteria andRecordTotalIsNotNull() {
            addCriterion("RECORD_TOTAL is not null");
            return (Criteria) this;
        }

        public Criteria andRecordTotalEqualTo(Integer value) {
            addCriterion("RECORD_TOTAL =", value, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalNotEqualTo(Integer value) {
            addCriterion("RECORD_TOTAL <>", value, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalGreaterThan(Integer value) {
            addCriterion("RECORD_TOTAL >", value, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("RECORD_TOTAL >=", value, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalLessThan(Integer value) {
            addCriterion("RECORD_TOTAL <", value, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalLessThanOrEqualTo(Integer value) {
            addCriterion("RECORD_TOTAL <=", value, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalIn(List<Integer> values) {
            addCriterion("RECORD_TOTAL in", values, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalNotIn(List<Integer> values) {
            addCriterion("RECORD_TOTAL not in", values, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalBetween(Integer value1, Integer value2) {
            addCriterion("RECORD_TOTAL between", value1, value2, "recordTotal");
            return (Criteria) this;
        }

        public Criteria andRecordTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("RECORD_TOTAL not between", value1, value2, "recordTotal");
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