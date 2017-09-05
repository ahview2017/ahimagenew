package com.deepai.photo.service.enColumn;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupColumn;
import com.deepai.photo.bean.EnPicGroupColumn;
import com.deepai.photo.mapper.CpColumnMapper;
import com.deepai.photo.mapper.CpPicGroupColumnMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;

@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class EnSignColumnService {
	@Autowired
	private CpColumnMapper cpColumnMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private CpPicGroupColumnMapper cpPicGroupColumnMapper;
	/**
	 * 签发栏目
	 * @param groupId
	 * @param signId
	 * @param columnId
	 * @param langType 
	 */
	public void signColumn(Integer groupId,Integer signId, Integer columnId, Integer langType) {
		
		EnPicGroupColumn enPicGroupColumn = new EnPicGroupColumn();
		enPicGroupColumn.setColumnId(columnId);
		enPicGroupColumn.setGroupId(groupId);
		enPicGroupColumn.setSignShow(signId);
		enPicGroupColumn.setLangType(langType);
		cpColumnMapper.signColumn(enPicGroupColumn);
		
	}
	/**
	 * 修改稿件的状态
	 * @param groupId
	 * @param date 
	 * @param user 
	 */
	public void updateGroupStatus(Integer groupId, Date date, String user){
		cpPicGroupMapper.updateGroupStatus(groupId, date, user);
	}
	/**
	 * 查询已经签发到当前栏目的稿件
	 * @param columnId
	 */
	public List<Map<String,Object>> browsePosition(Integer columnId){
		List<Map<String,Object>> cpPicGroupList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> cpPicGroupColumnMap = new HashMap<String,Object>();
		CpColumn cpColumn = cpColumnMapper.selectBykey(columnId);
		Integer signPosition = cpColumn.getSignPosition();
		cpPicGroupColumnMap.put("columnId", columnId);
		cpPicGroupColumnMap.put("signPosition", signPosition);		
		List<CpPicGroupColumn> cpPicGroupColumn = cpPicGroupColumnMapper.selectByGroupId(columnId, signPosition);
		for (CpPicGroupColumn cpPicGroupColumn2 : cpPicGroupColumn) {
			Map<String,Object> map = new HashMap<String,Object>();
			Integer groupId = cpPicGroupColumn2.getGroupId();
			Integer sginShow = cpPicGroupColumn2.getSginShow();
			CpPicGroup cpPicGroup = cpPicGroupMapper.selectByGroupId(groupId);
			map.put("TITLE", cpPicGroup.getTitle());
			map.put("POSITION", sginShow);
			cpPicGroupList.add(map);
		}
		return cpPicGroupList;
		
	}
	/**
	 * 修改稿件的位置
	 * @param signId
	 * @param columnId
	 * @param groupId 
	 */
	public void updateCpPicGroupColumn(Integer signId, Integer columnId, Integer groupId) {
		// TODO Auto-generated method stub
		cpPicGroupColumnMapper.updateCpPicGroupColumn(signId, columnId, groupId);
	}
}
