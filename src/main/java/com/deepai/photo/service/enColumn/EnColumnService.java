package com.deepai.photo.service.enColumn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpColumnExample;
import com.deepai.photo.bean.CpColumnExample.Criteria;
import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.mapper.CpColumnMapper;
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class EnColumnService  {

	@Autowired
	private CpColumnMapper enColumnMapper;
	public List<CpColumn> selectEnColumnList(Integer pid ,Integer position ,Integer state, Integer langType) {
		CpColumn enColumn = new CpColumn();
		enColumn.setPid(pid);
		enColumn.setPosition(position);
		enColumn.setState(state);
		enColumn.setLangType(langType);
		List<CpColumn> enColumnList = enColumnMapper.selectByExample(enColumn);
		return enColumnList;
	}
	//根据id查栏目的信息
	public CpColumn selectEnUpColumnList(Integer columnId) {
		
		CpColumn cpColumn = enColumnMapper.selectBykey(columnId);
		return cpColumn;
	}
	/**
	 * 查寻父栏目栏目
	 * @param lang 
	 * @return
	 */
	public List<CpColumn> selectUpColumnAll(Integer langType){
		List<CpColumn> Columnlist = enColumnMapper.selectUpColumnAll(langType);
		return Columnlist;
		
	}
	/**
	 * 根据pid查询子栏目
	 * @param pid
	 * @return
	 */
	public List<CpColumn> selectNextColumn(Integer pid){
		List<CpColumn> nextColumn = enColumnMapper.selectNextColumn(pid);
		return nextColumn;
	}
	
	

}
