package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpDutyUser;
import com.deepai.photo.bean.CpDutyUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpDutyUserMapper {
    int countByExample(CpDutyUserExample example);

    int deleteByExample(CpDutyUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpDutyUser record);

    int insertSelective(CpDutyUser record);

    List<CpDutyUser> selectByExample(CpDutyUserExample example);

    CpDutyUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpDutyUser record, @Param("example") CpDutyUserExample example);

    int updateByExample(@Param("record") CpDutyUser record, @Param("example") CpDutyUserExample example);

    int updateByPrimaryKeySelective(CpDutyUser record);

    int updateByPrimaryKey(CpDutyUser record);
}