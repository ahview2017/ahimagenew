package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpSiteMessage;
import com.deepai.photo.bean.CpSiteMessageExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpSiteMessageMapper {
    int countByExample(CpSiteMessageExample example);

    int deleteByExample(CpSiteMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpSiteMessage record);

    int insertSelective(CpSiteMessage record);

    List<CpSiteMessage> selectByExample(CpSiteMessageExample example);

    CpSiteMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpSiteMessage record, @Param("example") CpSiteMessageExample example);

    int updateByExample(@Param("record") CpSiteMessage record, @Param("example") CpSiteMessageExample example);

    int updateByPrimaryKeySelective(CpSiteMessage record);

    int updateByPrimaryKey(CpSiteMessage record);

	List<CpSiteMessage> showLeavingMsgLim(Map<String, Integer> map);
}