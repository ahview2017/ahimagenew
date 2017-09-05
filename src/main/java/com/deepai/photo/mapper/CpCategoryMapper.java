package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpCategory;
import com.deepai.photo.bean.CpCategoryExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CpCategoryMapper {
	int countByExample(CpCategoryExample example);

	int deleteByExample(CpCategoryExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(CpCategory record);

	int insertSelective(CpCategory record);

	List<CpCategory> selectByExample(CpCategoryExample example);

	CpCategory selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") CpCategory record,
			@Param("example") CpCategoryExample example);

	int updateByExample(@Param("record") CpCategory record,
			@Param("example") CpCategoryExample example);

	int updateByPrimaryKeySelective(CpCategory record);

	int updateByPrimaryKey(CpCategory record);

	// 修改某个分类及其子类的状态为已删除
	int delcpcategorys(Integer id);

	void deleaLL(Integer id);
}