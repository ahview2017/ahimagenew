package com.deepai.photo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpMassSMSRecord;
import com.deepai.photo.bean.CpPhoneMsg;
import com.deepai.photo.bean.CpPhoneMsgExample;

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
    
    int addMassSmsRecord(CpMassSMSRecord cpMassSMSRecord);
    
}