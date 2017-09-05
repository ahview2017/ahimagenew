package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPhoneMsg;
import com.deepai.photo.bean.CpPhoneMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpPhoneMsgMapper {
    int countByExample(CpPhoneMsgExample example);

    int deleteByExample(CpPhoneMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPhoneMsg record);

    int insertSelective(CpPhoneMsg record);

    List<CpPhoneMsg> selectByExample(CpPhoneMsgExample example);

    CpPhoneMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPhoneMsg record, @Param("example") CpPhoneMsgExample example);

    int updateByExample(@Param("record") CpPhoneMsg record, @Param("example") CpPhoneMsgExample example);

    int updateByPrimaryKeySelective(CpPhoneMsg record);

    int updateByPrimaryKey(CpPhoneMsg record);
}