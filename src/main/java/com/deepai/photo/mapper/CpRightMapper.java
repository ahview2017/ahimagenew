package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpRight;
import com.deepai.photo.bean.CpRightExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpRightMapper {
    int countByExample(CpRightExample example);

    int deleteByExample(CpRightExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpRight record);

    int insertSelective(CpRight record);

    List<CpRight> selectByExample(CpRightExample example);

    CpRight selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpRight record, @Param("example") CpRightExample example);

    int updateByExample(@Param("record") CpRight record, @Param("example") CpRightExample example);

    int updateByPrimaryKeySelective(CpRight record);

    int updateByPrimaryKey(CpRight record);
}