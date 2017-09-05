package com.deepai.photo.bean;

import java.io.Serializable;
import java.util.Date;

public class CpMsg implements Serializable{
    private int id;

    private String MSG_CONTENT;
    private String msgTitle;
    
    private String datetime;

    private Integer SITE_ID;

    private Integer Statu;

    private Date Create_Time;
    private Integer contextId;
    private Integer AC_ID;

    private Integer SEND_ID;
    private String send_name;
    private String ac_name;
    private Integer unRead;
    
    


	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public Integer getUnRead() {
		return unRead;
	}

	public void setUnRead(Integer unRead) {
		this.unRead = unRead;
	}

	public Integer getContextId() {
		return contextId;
	}

	public void setContextId(Integer contextId) {
		this.contextId = contextId;
	}

	public String getSend_name() {
		return send_name;
	}

	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}

	public String getAc_name() {
		return ac_name;
	}

	public void setAc_name(String ac_name) {
		this.ac_name = ac_name;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getDatetime() {
		return datetime;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMSG_CONTENT() {
		return MSG_CONTENT;
	}

	public void setMSG_CONTENT(String mSG_CONTENT) {
		MSG_CONTENT = mSG_CONTENT;
	}

	public Integer getSITE_ID() {
		return SITE_ID;
	}

	public void setSITE_ID(Integer sITE_ID) {
		SITE_ID = sITE_ID;
	}

	public Integer getStatu() {
		return Statu;
	}

	public void setStatu(Integer statu) {
		Statu = statu;
	}

	public Date getCreate_Time() {
		return Create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		Create_Time = create_Time;
	}

	public Integer getAC_ID() {
		return AC_ID;
	}

	public void setAC_ID(Integer aC_ID) {
		AC_ID = aC_ID;
	}

	public Integer getSEND_ID() {
		return SEND_ID;
	}

	public void setSEND_ID(Integer sEND_ID) {
		SEND_ID = sEND_ID;
	}

}