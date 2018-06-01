package com.deepai.photo.mapper;

import java.util.List;

import com.deepai.photo.bean.CpMsgTimer;;

/**
 * 定时任务执行接口
 * @author xiayunan
 * @date   2018年5月28日
 *
 */
public interface CpMsgTimerMapper {
	int insertSelective(CpMsgTimer cpMsgTimer);
	List<CpMsgTimer> selectMassSMSTasks();
	CpMsgTimer selectByClassId(Integer classId); 
	void updateByPrimaryKey(CpMsgTimer cpMsgTimer);
	int deleteByPrimaryKey(Integer id);
	int deleteByClassId(Integer id);
}
