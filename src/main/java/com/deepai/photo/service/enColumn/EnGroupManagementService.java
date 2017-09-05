package com.deepai.photo.service.enColumn;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepai.photo.bean.EnGroupManagement;
import com.deepai.photo.mapper.EnGroupManagementMapper;
@Service
public class EnGroupManagementService {
	@Autowired
	private EnGroupManagementMapper groupManagementMapper;
	/**
	 * 查询群组
	 * @param groupName 
	 * @return
	 */
	public List<EnGroupManagement> getGroupManagement(String groupName) {
		List<EnGroupManagement> groupManagementlist = groupManagementMapper.getGroupManagement(groupName);
		return groupManagementlist;
	}
	/**
	 * 添加群组
	 * @param name
	 * @param memo
	 */
	public void addGroupManagement(String name, String memo) {
		// TODO Auto-generated method stub
		groupManagementMapper.addGroupManagement(name, memo);
	}
	/**
	 * 修改群组
	 * @param id
	 * @param name
	 * @param memo
	 */
	public void editGroupManagement(Integer groupId, String name, String memo) {
		groupManagementMapper.editGroupManagement(groupId, name, memo);
		
	}
	/**
	 * 删除群组
	 * @param id
	 */
	public void deleteGroupManagement(Integer groupId) {
		groupManagementMapper.deleteGroupManagement(groupId);
		
	}
	/**
	 * 删除群组的用户
	 * @param id
	 */
	public void deleteGroupManagementUserByGroupId(Integer groupId) {
		groupManagementMapper.deleteGroupManagementUserByGroupId(groupId);
		
	}
	/**
	 * 查询群组的用户
	 * @param groupId
	 * @param userName 
	 * @return
	 */
	public List<Map<String, Object>> getGroupManagementUser(Integer groupId, String userName) {
		List<Map<String, Object>> userList = groupManagementMapper.getGroupManagementUser(groupId, userName);
		return userList;
	}
	/**
	 * 添加群组用户
	 * @param userId
	 * @param groupId 
	 */
	public void addGroupManagementUser(Integer userId, Integer groupId) {
		groupManagementMapper.addGroupManagementUser(userId, groupId);
		
	}
	/**
	 * 删除群组用户
	 * @param userId
	 * @param groupId
	 */
	public void deleteGroupManagementUser(Integer userId, Integer groupId) {
		groupManagementMapper.deleteGroupManagementUser(userId, groupId);
		
	}
	/**
	 * 根据id查询群组
	 * @param id
	 * @return
	 */
	public EnGroupManagement selectById(int id) {
		EnGroupManagement groupManagement = groupManagementMapper.selectById(id);
		return groupManagement;
	}

}
