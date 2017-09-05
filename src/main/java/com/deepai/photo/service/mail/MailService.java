package com.deepai.photo.service.mail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpEmail;
import com.deepai.photo.bean.CpEmailExample;
import com.deepai.photo.bean.CpEmailExample.Criteria;
import com.deepai.photo.mapper.CpEmailMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.CpUserRoleMapper;

/**
 * * @author huqiankai:
 * 
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class MailService {
	@Autowired
	private CpEmailMapper cpEmailMapper;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpUserRoleMapper cpUserRoleMapper;

	public void add(CpEmail email) {
		cpEmailMapper.insertSelective(email);
	}

	public List<CpEmail> show() {
		CpEmailExample example = new CpEmailExample();
		Criteria criteria = example.createCriteria().andStatusEqualTo(0);
		List<CpEmail> list = cpEmailMapper.selectByExample(example);
		return list;
	}

	public CpEmail detail(int id) {
		return cpEmailMapper.selectByPrimaryKey(id);
	}

	public void delete(int id) {
		cpEmailMapper.deleteByPrimaryKey(id);
	}

	public List<CpEmail> search(String search,String reciver,String timeFrom,String timeTo) throws ParseException {
		CpEmailExample example = new CpEmailExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(search)){
			search = "%" + search + "%";
			criteria.andEmailTitleLike(search);
		}
		if(StringUtils.isNotBlank(reciver)){
			reciver = "%" + reciver + "%";
			criteria.andEmailReciverLike(reciver);
		}
		if (StringUtils.isNotBlank(timeFrom)&&StringUtils.isNotBlank(timeTo)) {
			 SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
			 Date from = sim.parse(timeFrom);
			 Date to = sim.parse(timeTo);
			 Calendar calendar = new GregorianCalendar(); 
		     calendar.setTime(to); 
		     calendar.add(calendar.DATE,1);
		     to=calendar.getTime();   
			 criteria.andCreateTimeBetween(from, to);
		 }
			
		return cpEmailMapper.selectByExample(example);
	}

}
