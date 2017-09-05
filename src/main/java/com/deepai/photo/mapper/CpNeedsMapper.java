package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpNeeds;
import com.deepai.photo.bean.CpNeedsExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpNeedsMapper {
    int countByExample(CpNeedsExample example);

    int deleteByExample(CpNeedsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpNeeds record);

    int insertSelective(CpNeeds record);

    List<CpNeeds> selectByExample(CpNeedsExample example);

    CpNeeds selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpNeeds record, @Param("example") CpNeedsExample example);

    int updateByExample(@Param("record") CpNeeds record, @Param("example") CpNeedsExample example);

    int updateByPrimaryKeySelective(CpNeeds record);

    int updateByPrimaryKey(CpNeeds record);

	List<CpNeeds> showtoadmin( Map<String, Object> map);

	List<CpNeeds> showAll(Map<String, Object> map);

	List<CpNeeds> search(Map<String, Object> map);

	List<CpNeeds> selectNeedsByQuery(Map<String, Object> param);
}