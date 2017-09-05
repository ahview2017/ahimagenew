package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpSensitiveWord;
import com.deepai.photo.bean.CpSensitiveWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpSensitiveWordMapper {
    int countByExample(CpSensitiveWordExample example);

    int deleteByExample(CpSensitiveWordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpSensitiveWord record);

    int insertSelective(CpSensitiveWord record);

    List<CpSensitiveWord> selectByExample(CpSensitiveWordExample example);

    CpSensitiveWord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpSensitiveWord record, @Param("example") CpSensitiveWordExample example);

    int updateByExample(@Param("record") CpSensitiveWord record, @Param("example") CpSensitiveWordExample example);

    int updateByPrimaryKeySelective(CpSensitiveWord record);

    int updateByPrimaryKey(CpSensitiveWord record);
}