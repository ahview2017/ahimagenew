package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpMassSMSRecord;

public interface CpMassSMSRecordMapper {
	int insertSelective(CpMassSMSRecord cpMassSMSRecord);
	List<CpMassSMSRecord> showMassSMS();
	//更新群发短信
	void updateByPrimaryKeySelective(CpMassSMSRecord cpMassSMSRecord);
	//根据ID删除指定群发短信
	int deleteByPrimaryKey(Integer id);
	//根据ID查找指定群发短信
	CpMassSMSRecord  selectMassSMSById(Integer id);
	//群发短信检索以及高级检索
	List<CpMassSMSRecord> search(@Param("map")Map<String,Object> map);
	
}