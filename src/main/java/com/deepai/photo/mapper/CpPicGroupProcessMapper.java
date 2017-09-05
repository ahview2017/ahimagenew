package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPicGroupProcess;
import com.deepai.photo.bean.CpPicGroupProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpPicGroupProcessMapper {
    int countByExample(CpPicGroupProcessExample example);

    int deleteByExample(CpPicGroupProcessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicGroupProcess record);

    int insertSelective(CpPicGroupProcess record);

    List<CpPicGroupProcess> selectByExample(CpPicGroupProcessExample example);

    CpPicGroupProcess selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPicGroupProcess record, @Param("example") CpPicGroupProcessExample example);

    int updateByExample(@Param("record") CpPicGroupProcess record, @Param("example") CpPicGroupProcessExample example);

    int updateByPrimaryKeySelective(CpPicGroupProcess record);

    int updateByPrimaryKey(CpPicGroupProcess record);
}