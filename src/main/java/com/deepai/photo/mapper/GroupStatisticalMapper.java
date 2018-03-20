package com.deepai.photo.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpGroupStatistical;

/**
 * * @author huqiankai: * 
 * * @
 */
public interface GroupStatisticalMapper {

	public List<CpGroupStatistical> showAllGroupStatistical() ;

	public List<CpGroupStatistical> searchGroupStatistical(CpGroupStatistical cpGroupStatistical);

	public List<CpGroupStatistical> GroupStatisticalForPlace(CpGroupStatistical cpGroupStatistical );

	public List<CpGroupStatistical> GroupStatisticalForType(CpGroupStatistical cpGroupStatistical);
	
	public List<CpGroupStatistical> groupStatisticalForAuthor(CpGroupStatistical cpGroupStatistical);
	
	public List<CpGroupStatistical> groupStatisticalForCategory(CpGroupStatistical cpGroupStatistical);
	
	public List<CpGroupStatistical> groupStatisticalForAnHuiCity(CpGroupStatistical cpGroupStatistical);
	
	
	
	
}
