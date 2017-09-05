package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpLog;
import com.deepai.photo.bean.CpLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpLogMapper {
    int countByExample(CpLogExample example);

    int deleteByExample(CpLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpLog record);

    int insertSelective(CpLog record);

    List<CpLog> selectByExample(CpLogExample example);

    CpLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpLog record, @Param("example") CpLogExample example);

    int updateByExample(@Param("record") CpLog record, @Param("example") CpLogExample example);

    int updateByPrimaryKeySelective(CpLog record);

    int updateByPrimaryKey(CpLog record);
}