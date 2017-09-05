package com.deepai.photo.service.leavingmsg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpSiteMessage;
import com.deepai.photo.bean.CpSiteMessageExample;
import com.deepai.photo.bean.CpSiteMessageExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.mapper.CpSiteMessageMapper;

/**
 * * @author huqiankai: 
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class LeavingmsgService {
	@Autowired
	private CpSiteMessageMapper cpSiteMessageMapper;
	public List<CpSiteMessage> showtoadmin() {
		  CpSiteMessageExample example = new CpSiteMessageExample();
		  Criteria criteria = example.createCriteria();
		return cpSiteMessageMapper.selectByExample(example);
	}
	public void delete(int id) {
		cpSiteMessageMapper.deleteByPrimaryKey(id);
	}
	public CpSiteMessage edit(int ids) {
		 CpSiteMessageExample example = new CpSiteMessageExample();
		  Criteria criteria = example.createCriteria();
		  criteria.andIdEqualTo(ids);
		  List<CpSiteMessage> list = cpSiteMessageMapper.selectByExample(example);
		  return list.get(0);
		
	}
	public void add(CpSiteMessage cpAdvPosition) {
		cpSiteMessageMapper.insertSelective(cpAdvPosition);
	}
	public List<CpSiteMessage> serchLeavingmsg(String content, String timefrom,String timeto,String smLink) throws ParseException {
		 CpSiteMessageExample example = new CpSiteMessageExample();
		  Criteria criteria = example.createCriteria();
		 if (StringUtil.isNotBlank(timefrom)&&StringUtil.isNotBlank(timeto)) {
			 SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
			 Date from = sim.parse(timefrom);
			 Date to = sim.parse(timeto);
			 Calendar calendar = new GregorianCalendar(); 
		     calendar.setTime(to); 
		     calendar.add(calendar.DATE,1);
		     to=calendar.getTime();   
			 criteria.andCreateTimeBetween(from, to);
		 }
		 if (StringUtil.isNotBlank(content)) {
			 content="%"+content+"%";
			 criteria.andSmContentLike(content);
		 }
		 if(StringUtil.isNotBlank(smLink)){
			 smLink="%"+smLink+"%";
			 criteria.andSmLinkLike(smLink);
		 }
		return cpSiteMessageMapper.selectByExample(example);
	}
	public List<CpSiteMessage> showLeavingMsgLim(Map<String, Integer> map) {
		return cpSiteMessageMapper.showLeavingMsgLim(map);
	}

}
