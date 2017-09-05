package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.EnGroupManagement;

public interface EnGroupManagementMapper {

	List<EnGroupManagement> getGroupManagement(@Param("groupName")String groupName);

	void addGroupManagement(@Param("name")String name, @Param("memo")String memo);

	void editGroupManagement(@Param("groupId")Integer groupId, @Param("name")String name, @Param("memo")String memo);

	void deleteGroupManagement(Integer groupId);

	void deleteGroupManagementUserByGroupId(Integer groupId);

	List<Map<String, Object>> getGroupManagementUser(@Param("groupId")Integer groupId, @Param("userName")String userName);

	void addGroupManagementUser(@Param("userId")Integer userId, @Param("groupId")Integer groupId);

	void deleteGroupManagementUser(@Param("userId")Integer userId, @Param("groupId")Integer groupId);

	EnGroupManagement selectById(int id);

	int getUserCount(Integer id);

}
