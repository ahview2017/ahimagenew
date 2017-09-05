package com.deepai.photo.service.instant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.mapper.CpMsgMapper;

/**
 * *
 * 
 * @author huqiankai
 * 
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class StationMessiageService {
	@Autowired
	private CpMsgMapper cpMsgMapper;

	// 显示所有信息
	public List<CpMsg> showMsg(int AC_ID) {
		return cpMsgMapper.showMsg(AC_ID);

	}
	// 发送站内信

	public int sendMsg(CpMsg cpMsg) {
			return cpMsgMapper.sendMsgtontext(cpMsg);
	}
	public void sendMsg2(CpMsg cpMsg) {
		cpMsgMapper.sendMsg(cpMsg);
	}

	// 修改状态
	public void changeStatu(String i) {
		cpMsgMapper.changeStatu(i);
	}

	// 删除信息
	public void delete(CpMsg cpMsg) {
		cpMsgMapper.delete(cpMsg);
	}

	public List<CpMsg> detail(CpMsg cpMsg) {
		return cpMsgMapper.detail( cpMsg);
	}

	public List<CpMsg> serachStationMSG(CpMsg cpMsg) {
		return cpMsgMapper.serachStationMSG(cpMsg);
	}

	public int findId() {
		return cpMsgMapper.findId();
	}

	public CpMsg findSendID(int parseInt) {
		return cpMsgMapper.findSendID(parseInt);
	}

	public List<CpMsg> showToAdminHome(Map<String, Integer> map) {
		List<CpMsg> showToAdminHome = cpMsgMapper.showToAdminHome(map);
			Integer a =cpMsgMapper.findUnRead(map);
			for (CpMsg cpMsg : showToAdminHome) {
				cpMsg.setUnRead(a);
			}
		return showToAdminHome;
	}

	public List<CpMsg> getStationMessageByQuery(Map<String, Object> param) {
		return cpMsgMapper.getStationMessageByQuery(param);
	}


}
