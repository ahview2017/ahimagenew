package com.deepai.photo.service.enColumn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.mapper.EnManagerColumnMapper;
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class EnManagerColumnService {

	@Autowired
	private EnManagerColumnMapper enManagerColumnMapper;
	
	public List<CpColumn> selectColumnByRandomProperties(CpColumn cpColumn) {
		// TODO Auto-generated method stub
		return enManagerColumnMapper.selectColumnByRandomProperties(cpColumn);
	}
	public Integer addEnManagerColumnRecommend(CpColumn columnRecommend) {
		// TODO Auto-generated method stub
		return enManagerColumnMapper.addEnManagerColumnRecommend(columnRecommend);
	}
	public void addEnManagerColumn(CpColumn cpColumn) {
		enManagerColumnMapper.addEnManagerColumnRecommend(cpColumn);
	}
	public Integer selectMaxId() {
		// TODO Auto-generated method stub
		return enManagerColumnMapper.selectMaxId();
	}
	public void editEnManagerColumn(CpColumn cpColumn) {
		enManagerColumnMapper.editEnManagerColumn(cpColumn);
	}
	public void deleteEnManagerColumn(CpColumn cpColumn) {
		CpColumn column = enManagerColumnMapper.selectColumnById(cpColumn);
		if(column != null ){
			CpColumn column2  = new CpColumn();
			column2.setPid(cpColumn.getId());
			List<CpColumn> secondColumn = enManagerColumnMapper.selectColumnByRandomProperties(column2);
			if(!secondColumn.isEmpty()){
				for (CpColumn cpColumn2 : secondColumn) {
					//鍒犻櫎浜岀骇鏍忕洰鍏宠仈鐨勭粍鍥�
					enManagerColumnMapper.deleteGroupColumnByColumnId(cpColumn2.getId());
					//鍒犻櫎浜岀骇琛嶇敓鏍忕洰鍏宠仈鐨勭粍鍥�
					enManagerColumnMapper.deleteGroupColumnByColumnId(cpColumn2.getRecommendId());
					//鍒犻櫎浜岀骇琛嶇敓鏍忕洰
					enManagerColumnMapper.deleteColumnById(cpColumn2.getRecommendId());
					//鍒犻櫎浜岀骇鏍忕洰
					enManagerColumnMapper.deleteColumnById(cpColumn2.getId());
					
				}
			}
			//鍒犻櫎涓�绾ф爮鐩叧鑱旂殑缁勫浘
			enManagerColumnMapper.deleteGroupColumnByColumnId(column.getId());
			//鍒犻櫎涓�绾ц鐢熸爮鐩叧鑱旂殑缁勫浘
			enManagerColumnMapper.deleteGroupColumnByColumnId(column.getRecommendId());
			//鍒犻櫎涓�绾ц鐢熸爮鐩�
			enManagerColumnMapper.deleteColumnById(column.getRecommendId());
			//鍒犻櫎涓�绾ф爮鐩�
			enManagerColumnMapper.deleteColumnById(column.getId());
		}
	}
	


}
