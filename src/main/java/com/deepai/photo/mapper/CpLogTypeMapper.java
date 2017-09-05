package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpLogType;
import com.deepai.photo.bean.CpLogTypeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpLogTypeMapper {
    int countByExample(CpLogTypeExample example);

    int deleteByExample(CpLogTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpLogType record);

    int insertSelective(CpLogType record);

    List<CpLogType> selectByExample(CpLogTypeExample example);

    CpLogType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpLogType record, @Param("example") CpLogTypeExample example);

    int updateByExample(@Param("record") CpLogType record, @Param("example") CpLogTypeExample example);

    int updateByPrimaryKeySelective(CpLogType record);

    int updateByPrimaryKey(CpLogType record);

	List<Map<String, Object>> selectAllLog();
}