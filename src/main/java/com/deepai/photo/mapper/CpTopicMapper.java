package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpTopicExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpTopicMapper {

	int countByExample(CpTopicExample example);

    int deleteByExample(CpTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpTopic record);

    int insertSelective(CpTopic record);

    List<CpTopic> selectByExample(CpTopicExample example);

    CpTopic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpTopic record, @Param("example") CpTopicExample example);

    int updateByExample(@Param("record") CpTopic record, @Param("example") CpTopicExample example);

    int updateByPrimaryKeySelective(CpTopic record);

    int updateByPrimaryKey(CpTopic record);

	List<Map<String, Object>> showTopicToAdv();

	List<CpTopic> getTopicByQuery(CpTopic cpTopic);
}