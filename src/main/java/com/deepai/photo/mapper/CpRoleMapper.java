package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpRoleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpRoleMapper {
    int countByExample(CpRoleExample example);

    int deleteByExample(CpRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpRole record);

    int insertSelective(CpRole record);

    List<CpRole> selectByExample(CpRoleExample example);

    CpRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpRole record, @Param("example") CpRoleExample example);

    int updateByExample(@Param("record") CpRole record, @Param("example") CpRoleExample example);

    int updateByPrimaryKeySelective(CpRole record);

    int updateByPrimaryKey(CpRole record);
    
    List<Map<String ,Object>> checkDeleteRoles(String [] roleIds);
}