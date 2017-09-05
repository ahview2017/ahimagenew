package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpUserBank;
import com.deepai.photo.bean.CpUserBankExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpUserBankMapper {
    int countByExample(CpUserBankExample example);

    int deleteByExample(CpUserBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpUserBank record);

    int insertSelective(CpUserBank record);

    List<CpUserBank> selectByExample(CpUserBankExample example);

    CpUserBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpUserBank record, @Param("example") CpUserBankExample example);

    int updateByExample(@Param("record") CpUserBank record, @Param("example") CpUserBankExample example);

    int updateByPrimaryKeySelective(CpUserBank record);

    int updateByPrimaryKey(CpUserBank record);

	void setDefault(Integer id);

	void setBankdef(Map<String, Object> map);
}