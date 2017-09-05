package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPicGroupAssign;
import com.deepai.photo.bean.CpPicGroupAssignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpPicGroupAssignMapper {
    int countByExample(CpPicGroupAssignExample example);

    int deleteByExample(CpPicGroupAssignExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicGroupAssign record);

    int insertSelective(CpPicGroupAssign record);

    List<CpPicGroupAssign> selectByExample(CpPicGroupAssignExample example);

    CpPicGroupAssign selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPicGroupAssign record, @Param("example") CpPicGroupAssignExample example);

    int updateByExample(@Param("record") CpPicGroupAssign record, @Param("example") CpPicGroupAssignExample example);

    int updateByPrimaryKeySelective(CpPicGroupAssign record);

    int updateByPrimaryKey(CpPicGroupAssign record);
}