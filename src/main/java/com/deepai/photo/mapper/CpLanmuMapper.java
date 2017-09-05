package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpLanmuExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpLanmuMapper {
    int countByExample(CpLanmuExample example);

    int deleteByExample(CpLanmuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpLanmu record);

    int insertSelective(CpLanmu record);

    List<CpLanmu> selectByExampleWithBLOBs(CpLanmuExample example);

    List<CpLanmu> selectByExample(CpLanmuExample example);

    CpLanmu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpLanmu record, @Param("example") CpLanmuExample example);

    int updateByExampleWithBLOBs(@Param("record") CpLanmu record, @Param("example") CpLanmuExample example);

    int updateByExample(@Param("record") CpLanmu record, @Param("example") CpLanmuExample example);

    int updateByPrimaryKeySelective(CpLanmu record);

    int updateByPrimaryKeyWithBLOBs(CpLanmu record);

    int updateByPrimaryKey(CpLanmu record);

	String findTopicNameByTopicId(int topicId);

	void changSomePostion(CpLanmu cpLanmu);
	void postionToBig(Map map);
	void postionToSmall(Map map);

	Integer getMaxId();
}