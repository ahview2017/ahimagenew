package com.deepai.photo.bean;

public class CpDutyUser {
    private Integer id;

    private Integer type;

    private String userName;

    private Integer proofreadId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getProofreadId() {
        return proofreadId;
    }

    public void setProofreadId(Integer proofreadId) {
        this.proofreadId = proofreadId;
    }
}