package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpSystemConfig;
import com.deepai.photo.bean.CpSystemConfigExample;

public interface CpSystemConfigMapper {
    int countByExample(CpSystemConfigExample example);

    int deleteByExample(CpSystemConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpSystemConfig record);

    int insertSelective(CpSystemConfig record);

    List<CpSystemConfig> selectByExample(CpSystemConfigExample example);

    CpSystemConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpSystemConfig record, @Param("example") CpSystemConfigExample example);

    int updateByExample(@Param("record") CpSystemConfig record, @Param("example") CpSystemConfigExample example);

    int updateByPrimaryKeySelective(CpSystemConfig record);

    int updateByPrimaryKey(CpSystemConfig record);
    
    List<CpSystemConfig>  getConfig(@Param("map") Map<String, Object> map);
}