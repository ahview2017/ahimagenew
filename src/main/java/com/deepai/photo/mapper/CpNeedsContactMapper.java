package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpNeedsContact;
import com.deepai.photo.bean.CpNeedsContactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpNeedsContactMapper {
    int countByExample(CpNeedsContactExample example);

    int deleteByExample(CpNeedsContactExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpNeedsContact record);

    int insertSelective(CpNeedsContact record);

    List<CpNeedsContact> selectByExample(CpNeedsContactExample example);

    CpNeedsContact selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpNeedsContact record, @Param("example") CpNeedsContactExample example);

    int updateByExample(@Param("record") CpNeedsContact record, @Param("example") CpNeedsContactExample example);

    int updateByPrimaryKeySelective(CpNeedsContact record);

    int updateByPrimaryKey(CpNeedsContact record);
}