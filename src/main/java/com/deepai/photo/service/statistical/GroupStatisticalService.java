package com.deepai.photo.service.statistical;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpGroupStatistical;
import com.deepai.photo.mapper.GroupStatisticalMapper;

/**
 * 稿件统计
 * * @author huqiankai: * 
 * * 
 */
@Service
@Transactional
public class GroupStatisticalService {

	@Autowired
	private GroupStatisticalMapper groupStatisticalMapper ;
	public List<CpGroupStatistical> showAllGroupStatistical() {
		return groupStatisticalMapper.showAllGroupStatistical();
	}
	public List<CpGroupStatistical> searchGroupStatistical(CpGroupStatistical cpGroupStatistical) {
		return groupStatisticalMapper.searchGroupStatistical(cpGroupStatistical);
	}
	public List<CpGroupStatistical> GroupStatisticalForPlace(CpGroupStatistical cpGroupStatistical ) {
		return groupStatisticalMapper.GroupStatisticalForPlace(cpGroupStatistical);
	}
	public List<CpGroupStatistical> GroupStatisticalForType(CpGroupStatistical cpGroupStatistical) {
		return groupStatisticalMapper.GroupStatisticalForType(cpGroupStatistical);
	}
}
