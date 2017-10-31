package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.EnPicGroupColumn;

public interface CpColumnMapper {
	
	List<CpColumn> selectByExample(CpColumn cpColumn);
	
	CpColumn selectBykey(Integer columnId);
	
	List<CpColumn> selectById(Map<Object,Object> map);
	
	CpColumn selectBykeyNoPname(Integer columnId);
	
	List<CpColumn> selectUpColumnAll(Integer langType);
	
	List<CpColumn> selectNextColumn(Integer pid);
	
	int signColumn(EnPicGroupColumn enPicGroupColumn);

	List<CpColumn> selectByGroupId(@Param("groupId")Integer groupId, @Param("langType")Integer langType);
	
	
	
}
