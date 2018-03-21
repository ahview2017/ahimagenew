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
	
	/**
	 * 根据作者名统计稿件发稿情况
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param cpGroupStatistical
	 * @return
	 */
	public List<CpGroupStatistical> groupStatisticalForAuthor(CpGroupStatistical cpGroupStatistical) {
		return groupStatisticalMapper.groupStatisticalForAuthor(cpGroupStatistical);
	}
	

	/**
	 * 根据类别统计稿件发稿情况
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param cpGroupStatistical
	 * @return
	 */
	public List<CpGroupStatistical> groupStatisticalForCategory(CpGroupStatistical cpGroupStatistical) {
		return groupStatisticalMapper.groupStatisticalForCategory(cpGroupStatistical);
	}
	
	/**
	 * 根据作者名统计稿件发稿情况
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param cpGroupStatistical
	 * @return
	 */
	public List<CpGroupStatistical> groupStatisticalForAnHuiCity(CpGroupStatistical cpGroupStatistical) {
		return groupStatisticalMapper.groupStatisticalForAnHuiCity(cpGroupStatistical);
	}
	
	/**
	 * 网站展示栏目稿件统计
	 * @author xiayunan
	 * @date 2018年3月20日
	 * @param cpGroupStatistical
	 * @return
	 */
	public List<CpGroupStatistical> groupStatisticalForWebSiteShowColumn(CpGroupStatistical cpGroupStatistical) {
		return groupStatisticalMapper.groupStatisticalForWebSiteShowColumn(cpGroupStatistical);
	}
	
	
	
	
}
