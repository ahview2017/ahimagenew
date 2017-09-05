package com.deepai.photo.service.needs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpNeeds;
import com.deepai.photo.bean.CpNeedsContact;
import com.deepai.photo.bean.CpNeedsExample;
import com.deepai.photo.bean.CpNeedsExample.Criteria;
import com.deepai.photo.mapper.CpNeedsContactMapper;
import com.deepai.photo.mapper.CpNeedsMapper;

/**
 * * @author huqiankai: * 
 * * 
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class NeedService {
@Autowired
private CpNeedsMapper cpNeedsMapper;
@Autowired
private CpNeedsContactMapper cpNeedsContactMapper;
	public List<CpNeeds> showtoadmin(Integer roseIds,String strWhere,Integer langType) {   //0是未审核，1是审核通过，2是审核未通过，3保存文档，4审核通过需求关闭
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("langType", langType);
		if (roseIds==1||roseIds==2) {
			if (strWhere!=null) {
				map.put("StrWhere", strWhere);
			}
			return cpNeedsMapper.showAll(map);
		}
		map.put("status", 1);
		if (strWhere!=null) {
			map.put("StrWhere", strWhere);
		}
		return cpNeedsMapper.showtoadmin(map);
	}
	public List<CpNeeds> search(Integer roseIds,String someThing,Integer langType) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (roseIds!=1&&roseIds!=2) {
			map.put("status", 1);
		}
		map.put("langType", langType);
		map.put("something", someThing);
		return  cpNeedsMapper.search(map);
	}
	public List<CpNeeds> advancedSearch(Integer roleId, Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (roleId!=1&&roleId!=2) {
			map.put("status", 1);
		}
		return cpNeedsMapper.selectNeedsByQuery(param);
	}
	public CpNeeds detail(Integer id) {
		CpNeedsExample example = new CpNeedsExample();
		Criteria criteria = example.createCriteria();
	      criteria.andIdEqualTo(id);
	     return cpNeedsMapper.selectByPrimaryKey(id);
	}
	public void delete(Integer id) {
		cpNeedsMapper.deleteByPrimaryKey(id);
	}
	public void changeStatu(CpNeeds cpNeeds) {
		cpNeedsMapper.updateByPrimaryKeySelective(cpNeeds);
	}
	public void add(CpNeeds cpNeeds) {
		cpNeedsMapper.insertSelective(cpNeeds);
	}
	public void tigong(CpNeedsContact cpNeedsContact) {
		cpNeedsContact.setNeedId(cpNeedsContact.getId());
		cpNeedsContactMapper.insertSelective(cpNeedsContact);
	}
	public void edit(CpNeeds cpNeeds) {
		cpNeedsMapper.updateByPrimaryKeySelective(cpNeeds);
		
	}
	public List<CpNeeds> showToFrontPage(int site,String userName,Integer langType) {
		CpNeedsExample example = new CpNeedsExample();
		example.setOrderByClause("PTIME DESC");
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andSiteIdEqualTo(site);
		criteria.andLangTypeEqualTo(langType);
		criteria.andUsernameLike(userName);
		return cpNeedsMapper.selectByExample(example);
	}
	public List<CpNeeds> searchForDutyEditor(String desc, String username, String timefrom, String timeto,Integer langType) throws ParseException {
		 CpNeedsExample example = new CpNeedsExample();
		 Criteria criteria = example.createCriteria();
		 if (StringUtils.isNotBlank(desc)) {
			 desc="%"+desc+"%";
			 criteria.andDescsLike(desc);
		}
		 if (StringUtils.isNotBlank(username)) {
			 username="%"+username+"%";
			 criteria.andUsernameLike(username);
		}
		 if (StringUtils.isNotBlank(timefrom)&&StringUtils.isNotBlank(timeto)) {
			 SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
			 Date from = sim.parse(timefrom);
			 Date to = sim.parse(timeto);
			 Calendar   calendar   =   new   GregorianCalendar(); 
		     calendar.setTime(to); 
		     calendar.add(calendar.DATE,1);
		     to=calendar.getTime();   
			 criteria.andPtimeBetween(from,to);
		 }
		 criteria.andStatusEqualTo(1);
		 criteria.andLangTypeEqualTo(langType);
		return  cpNeedsMapper.selectByExample(example);
	}
	

}
