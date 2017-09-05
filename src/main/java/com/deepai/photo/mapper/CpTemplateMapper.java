package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpTemplate;
import com.deepai.photo.bean.CpTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpTemplateMapper {
    int countByExample(CpTemplateExample example);

    int deleteByExample(CpTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpTemplate record);

    int insertSelective(CpTemplate record);

    List<CpTemplate> selectByExample(CpTemplateExample example);

    CpTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpTemplate record, @Param("example") CpTemplateExample example);

    int updateByExample(@Param("record") CpTemplate record, @Param("example") CpTemplateExample example);

    int updateByPrimaryKeySelective(CpTemplate record);

    int updateByPrimaryKey(CpTemplate record);

	int findLastId();
}