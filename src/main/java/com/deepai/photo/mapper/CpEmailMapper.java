package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpEmail;
import com.deepai.photo.bean.CpEmailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpEmailMapper {
    int countByExample(CpEmailExample example);

    int deleteByExample(CpEmailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpEmail record);

    int insertSelective(CpEmail record);

    List<CpEmail> selectByExample(CpEmailExample example);

    CpEmail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpEmail record, @Param("example") CpEmailExample example);

    int updateByExample(@Param("record") CpEmail record, @Param("example") CpEmailExample example);

    int updateByPrimaryKeySelective(CpEmail record);

    int updateByPrimaryKey(CpEmail record);
}