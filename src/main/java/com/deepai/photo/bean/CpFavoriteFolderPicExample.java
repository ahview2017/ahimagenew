package com.deepai.photo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpFavoriteFolderPicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpFavoriteFolderPicExample() {
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

        public Criteria andPictureIdIsNull() {
            addCriterion("PICTURE_ID is null");
            return (Criteria) this;
        }

        public Criteria andPictureIdIsNotNull() {
            addCriterion("PICTURE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPictureIdEqualTo(Integer value) {
            addCriterion("PICTURE_ID =", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotEqualTo(Integer value) {
            addCriterion("PICTURE_ID <>", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdGreaterThan(Integer value) {
            addCriterion("PICTURE_ID >", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_ID >=", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdLessThan(Integer value) {
            addCriterion("PICTURE_ID <", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdLessThanOrEqualTo(Integer value) {
            addCriterion("PICTURE_ID <=", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdIn(List<Integer> values) {
            addCriterion("PICTURE_ID in", values, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotIn(List<Integer> values) {
            addCriterion("PICTURE_ID not in", values, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_ID between", value1, value2, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PICTURE_ID not between", value1, value2, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameIsNull() {
            addCriterion("PICTURE_FILENAME is null");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameIsNotNull() {
            addCriterion("PICTURE_FILENAME is not null");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameEqualTo(String value) {
            addCriterion("PICTURE_FILENAME =", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameNotEqualTo(String value) {
            addCriterion("PICTURE_FILENAME <>", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameGreaterThan(String value) {
            addCriterion("PICTURE_FILENAME >", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("PICTURE_FILENAME >=", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameLessThan(String value) {
            addCriterion("PICTURE_FILENAME <", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameLessThanOrEqualTo(String value) {
            addCriterion("PICTURE_FILENAME <=", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameLike(String value) {
            addCriterion("PICTURE_FILENAME like", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameNotLike(String value) {
            addCriterion("PICTURE_FILENAME not like", value, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameIn(List<String> values) {
            addCriterion("PICTURE_FILENAME in", values, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameNotIn(List<String> values) {
            addCriterion("PICTURE_FILENAME not in", values, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameBetween(String value1, String value2) {
            addCriterion("PICTURE_FILENAME between", value1, value2, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andPictureFilenameNotBetween(String value1, String value2) {
            addCriterion("PICTURE_FILENAME not between", value1, value2, "pictureFilename");
            return (Criteria) this;
        }

        public Criteria andFolderIdIsNull() {
            addCriterion("FOLDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andFolderIdIsNotNull() {
            addCriterion("FOLDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFolderIdEqualTo(Integer value) {
            addCriterion("FOLDER_ID =", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdNotEqualTo(Integer value) {
            addCriterion("FOLDER_ID <>", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdGreaterThan(Integer value) {
            addCriterion("FOLDER_ID >", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("FOLDER_ID >=", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdLessThan(Integer value) {
            addCriterion("FOLDER_ID <", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdLessThanOrEqualTo(Integer value) {
            addCriterion("FOLDER_ID <=", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdIn(List<Integer> values) {
            addCriterion("FOLDER_ID in", values, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdNotIn(List<Integer> values) {
            addCriterion("FOLDER_ID not in", values, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdBetween(Integer value1, Integer value2) {
            addCriterion("FOLDER_ID between", value1, value2, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("FOLDER_ID not between", value1, value2, "folderId");
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

        public Criteria andGroupTitleIsNull() {
            addCriterion("GROUP_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andGroupTitleIsNotNull() {
            addCriterion("GROUP_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andGroupTitleEqualTo(String value) {
            addCriterion("GROUP_TITLE =", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleNotEqualTo(String value) {
            addCriterion("GROUP_TITLE <>", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleGreaterThan(String value) {
            addCriterion("GROUP_TITLE >", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_TITLE >=", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleLessThan(String value) {
            addCriterion("GROUP_TITLE <", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleLessThanOrEqualTo(String value) {
            addCriterion("GROUP_TITLE <=", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleLike(String value) {
            addCriterion("GROUP_TITLE like", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleNotLike(String value) {
            addCriterion("GROUP_TITLE not like", value, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleIn(List<String> values) {
            addCriterion("GROUP_TITLE in", values, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleNotIn(List<String> values) {
            addCriterion("GROUP_TITLE not in", values, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleBetween(String value1, String value2) {
            addCriterion("GROUP_TITLE between", value1, value2, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andGroupTitleNotBetween(String value1, String value2) {
            addCriterion("GROUP_TITLE not between", value1, value2, "groupTitle");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIsNull() {
            addCriterion("PUBLIC_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIsNotNull() {
            addCriterion("PUBLIC_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPublicTimeEqualTo(Date value) {
            addCriterion("PUBLIC_TIME =", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotEqualTo(Date value) {
            addCriterion("PUBLIC_TIME <>", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeGreaterThan(Date value) {
            addCriterion("PUBLIC_TIME >", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PUBLIC_TIME >=", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLessThan(Date value) {
            addCriterion("PUBLIC_TIME <", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeLessThanOrEqualTo(Date value) {
            addCriterion("PUBLIC_TIME <=", value, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeIn(List<Date> values) {
            addCriterion("PUBLIC_TIME in", values, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotIn(List<Date> values) {
            addCriterion("PUBLIC_TIME not in", values, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeBetween(Date value1, Date value2) {
            addCriterion("PUBLIC_TIME between", value1, value2, "publicTime");
            return (Criteria) this;
        }

        public Criteria andPublicTimeNotBetween(Date value1, Date value2) {
            addCriterion("PUBLIC_TIME not between", value1, value2, "publicTime");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNull() {
            addCriterion("CREATER is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("CREATER is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(String value) {
            addCriterion("CREATER =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(String value) {
            addCriterion("CREATER <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(String value) {
            addCriterion("CREATER >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(String value) {
            addCriterion("CREATER >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(String value) {
            addCriterion("CREATER <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(String value) {
            addCriterion("CREATER <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLike(String value) {
            addCriterion("CREATER like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotLike(String value) {
            addCriterion("CREATER not like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<String> values) {
            addCriterion("CREATER in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<String> values) {
            addCriterion("CREATER not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(String value1, String value2) {
            addCriterion("CREATER between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(String value1, String value2) {
            addCriterion("CREATER not between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andAllPathIsNull() {
            addCriterion("ALL_PATH is null");
            return (Criteria) this;
        }

        public Criteria andAllPathIsNotNull() {
            addCriterion("ALL_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andAllPathEqualTo(String value) {
            addCriterion("ALL_PATH =", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotEqualTo(String value) {
            addCriterion("ALL_PATH <>", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathGreaterThan(String value) {
            addCriterion("ALL_PATH >", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathGreaterThanOrEqualTo(String value) {
            addCriterion("ALL_PATH >=", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathLessThan(String value) {
            addCriterion("ALL_PATH <", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathLessThanOrEqualTo(String value) {
            addCriterion("ALL_PATH <=", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathLike(String value) {
            addCriterion("ALL_PATH like", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotLike(String value) {
            addCriterion("ALL_PATH not like", value, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathIn(List<String> values) {
            addCriterion("ALL_PATH in", values, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotIn(List<String> values) {
            addCriterion("ALL_PATH not in", values, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathBetween(String value1, String value2) {
            addCriterion("ALL_PATH between", value1, value2, "allPath");
            return (Criteria) this;
        }

        public Criteria andAllPathNotBetween(String value1, String value2) {
            addCriterion("ALL_PATH not between", value1, value2, "allPath");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
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