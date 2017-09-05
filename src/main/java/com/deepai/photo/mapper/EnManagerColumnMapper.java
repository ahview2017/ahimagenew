package com.deepai.photo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.deepai.photo.bean.CpColumn;

public interface EnManagerColumnMapper {

	List<CpColumn> selectColumnByRandomProperties(CpColumn cpColumn);

	Integer addEnManagerColumnRecommend(CpColumn ColumnRecommend);

	Integer selectMaxId();

	void editEnManagerColumn(CpColumn cpColumn);

	CpColumn selectColumnById(CpColumn cpColumn);

	void deleteGroupColumnByColumnId(@Param("columnId")Integer id);

	void deleteColumnById(@Param("id")Integer id);


}
