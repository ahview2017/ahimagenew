package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpMsgExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpMsgMapper {
  //发送站内信
	void sendMsg(CpMsg cpMsg);
	//修改站内信状态
	void changeStatu(String i);
	//删除站内信
	void delete(CpMsg cpMsg);
	//显示站内信
	List<CpMsg> showMsg(int aC_ID);
	//发送站内信
	int sendMsgtontext(CpMsg cpMsg);
	//删除站内信
	void deleteMsgtontext(String i);
	//站内信详细信息
	List<CpMsg> detail(CpMsg cpMsg);
	List<CpMsg> serachStationMSG(CpMsg cpMsg);
	int findId();
	CpMsg findSendID(int parseInt);
	List<CpMsg> showToAdminHome(Map<String, Integer> map);
	Integer findUnRead(Map<String, Integer> map);
	//站内信的高级检索
	List<CpMsg> getStationMessageByQuery(Map<String, Object> param);
}