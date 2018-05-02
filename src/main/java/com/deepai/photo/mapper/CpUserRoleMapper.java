package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpUserRole;
import com.deepai.photo.bean.CpUserRoleExample;

public interface CpUserRoleMapper {
    int countByExample(CpUserRoleExample example);

    int deleteByExample(CpUserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpUserRole record);

    int insertSelective(CpUserRole record);

    List<CpUserRole> selectByExample(CpUserRoleExample example);

    CpUserRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpUserRole record, @Param("example") CpUserRoleExample example);

    int updateByExample(@Param("record") CpUserRole record, @Param("example") CpUserRoleExample example);

    int updateByPrimaryKeySelective(CpUserRole record);

    int updateByPrimaryKey(CpUserRole record);

	List<Integer> selectIdByTeamId(int i);

	String findTeamName(int emailReciverRole);

	List<Integer> getRoseId(Integer id);

	String getRoseName(int id);

	List<String> getRoseNameBuUid(Integer id);

	CpUser findEmailByUserName(String userName);

	List<CpUser> findUserByPhone(String phoneNum);

	CpUser findEmailByUserEmail(String email);
	
	CpUserRole findUserRole(Map<String,Object> map);
}