package com.deepai.photo.bean;

import java.util.Date;
import java.util.List;

/**
 * 短信群发记录表
 * @author xiayunan
 * @date   2018年4月23日
 *
 */
public class CpMassSMSRecord {
	 	private int id;
	 	private String sender;//发送者真实姓名
	 	private String auditor;//审核人员真实姓名
	    private int receiverType;//接受对象类型
	    private String phoneReceiverUser;//接受用户
	    private List<CpUser> users;//接受用户集合
	    private List<EnGroupManagement> groups;//接受组集合
	    private String phoneReceiverGroup;//接受组
		private String msgContent;//群发短信内容
	    private String msgSuggestion;//审核意见
	    private Integer status;//审核状态
		private Date crtime;//创建时间
	    private Date updateTime;//更新时间
	    private String remark;//备注 
	    private String filePath;//号码簿文件路径
	    private String executeTime;//执行时间
	    
	    public String getExecuteTime() {
			return executeTime;
		}
		public void setExecuteTime(String executeTime) {
			this.executeTime = executeTime;
		}
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public List<EnGroupManagement> getGroups() {
			return groups;
		}
		public void setGroups(List<EnGroupManagement> groups) {
			this.groups = groups;
		}
	    
	    public List<CpUser> getUsers() {
			return users;
		}
		public void setUsers(List<CpUser> users) {
			this.users = users;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		
	    public String getAuditor() {
			return auditor;
		}
		public void setAuditor(String auditor) {
			this.auditor = auditor;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
		public int getReceiverType() {
			return receiverType;
		}
		public void setReceiverType(int receiverType) {
			this.receiverType = receiverType;
		}
		public String getPhoneReceiverUser() {
			return phoneReceiverUser;
		}
		public void setPhoneReceiverUser(String phoneReceiverUser) {
			this.phoneReceiverUser = phoneReceiverUser;
		}
		public String getPhoneReceiverGroup() {
			return phoneReceiverGroup;
		}
		public void setPhoneReceiverGroup(String phoneReceiverGroup) {
			this.phoneReceiverGroup = phoneReceiverGroup;
		}
		public String getMsgContent() {
			return msgContent;
		}
		public void setMsgContent(String msgContent) {
			this.msgContent = msgContent;
		}
		public String getMsgSuggestion() {
			return msgSuggestion;
		}
		public void setMsgSuggestion(String msgSuggestion) {
			this.msgSuggestion = msgSuggestion;
		}
		
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Date getCrtime() {
			return crtime;
		}
		public void setCrtime(Date crtime) {
			this.crtime = crtime;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	    
	    
		
}
