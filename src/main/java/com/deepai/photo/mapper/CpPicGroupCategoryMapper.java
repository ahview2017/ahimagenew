package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPicGroupCategory;
import com.deepai.photo.bean.CpPicGroupCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpPicGroupCategoryMapper {
    int countByExample(CpPicGroupCategoryExample example);

    int deleteByExample(CpPicGroupCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicGroupCategory record);

    int insertSelective(CpPicGroupCategory record);

    List<CpPicGroupCategory> selectByExample(CpPicGroupCategoryExample example);

    CpPicGroupCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPicGroupCategory record, @Param("example") CpPicGroupCategoryExample example);

    int updateByExample(@Param("record") CpPicGroupCategory record, @Param("example") CpPicGroupCategoryExample example);

    int updateByPrimaryKeySelective(CpPicGroupCategory record);

    int updateByPrimaryKey(CpPicGroupCategory record);

	List<CpPicGroupCategory> selectByGroupId(Integer groupId);
}