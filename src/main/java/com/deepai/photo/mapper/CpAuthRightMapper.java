package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpAuthRight;
import com.deepai.photo.bean.CpAuthRightExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpAuthRightMapper {
    int countByExample(CpAuthRightExample example);

    int deleteByExample(CpAuthRightExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpAuthRight record);

    int insertSelective(CpAuthRight record);

    List<CpAuthRight> selectByExample(CpAuthRightExample example);

    CpAuthRight selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpAuthRight record, @Param("example") CpAuthRightExample example);

    int updateByExample(@Param("record") CpAuthRight record, @Param("example") CpAuthRightExample example);

    int updateByPrimaryKeySelective(CpAuthRight record);

    int updateByPrimaryKey(CpAuthRight record);
}