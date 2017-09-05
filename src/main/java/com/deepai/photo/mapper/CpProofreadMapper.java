package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpProofread;
import com.deepai.photo.bean.CpProofreadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpProofreadMapper {
    int countByExample(CpProofreadExample example);

    int deleteByExample(CpProofreadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpProofread record);

    int insertSelective(CpProofread record);

    List<CpProofread> selectByExample(CpProofreadExample example);

    CpProofread selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpProofread record, @Param("example") CpProofreadExample example);

    int updateByExample(@Param("record") CpProofread record, @Param("example") CpProofreadExample example);

    int updateByPrimaryKeySelective(CpProofread record);

    int updateByPrimaryKey(CpProofread record);
}