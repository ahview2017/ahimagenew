package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpOrderForm;
import com.deepai.photo.bean.CpOrderFormExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpOrderFormMapper {
    int countByExample(CpOrderFormExample example);

    int deleteByExample(CpOrderFormExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpOrderForm record);

    int insertSelective(CpOrderForm record);

    List<CpOrderForm> selectByExample(CpOrderFormExample example);

    CpOrderForm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpOrderForm record, @Param("example") CpOrderFormExample example);

    int updateByExample(@Param("record") CpOrderForm record, @Param("example") CpOrderFormExample example);

    int updateByPrimaryKeySelective(CpOrderForm record);

    int updateByPrimaryKey(CpOrderForm record);

	CpOrderForm detail(String orderno);

	List<CpOrderForm> selectOrderByQuery(Map<String, Object> param);
}