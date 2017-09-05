package com.deepai.photo.service.topic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpTopicExample;
import com.deepai.photo.bean.CpTopicExample.Criteria;
import com.deepai.photo.mapper.CpLanmuMapper;
import com.deepai.photo.mapper.CpTopicMapper;

/**
 * * @author huqiankai: * @date 创建时间：2017年1月12日 下午3:48:15 * @version 1.0
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class TopicService {
	@Autowired
	private CpTopicMapper cpTopicMapper;
	@Autowired
	private CpLanmuMapper cpLanmuMapper;

	public void add(CpTopic cpTopic) {
		cpTopicMapper.insertSelective(cpTopic);
	}

	public List<CpTopic> show(List<Integer> roseId, Integer langType) {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria().andLangTypeEqualTo(langType);
		if (roseId.contains(2)&&!roseId.contains(1)) {
			criteria.andQianfaEqualTo(1);
		}
		example
		.setOrderByClause("CREATE_TIME desc");
		return cpTopicMapper.selectByExample(example);
	}

	public void delete(int i) {
		cpTopicMapper.deleteByPrimaryKey(i);
	}

	public String findTopicNameByTopicId(Integer topicId) {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(topicId);
		return cpTopicMapper.selectByExample(example).get(0).getName();
	}

	public void edit(CpTopic cpTopic) {
		cpTopicMapper.updateByPrimaryKeySelective(cpTopic);
	}

	public List<CpTopic> searchTopic(String name, List<Integer> roseId, Integer langType) {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria();
		name="%"+name+"%";
		criteria.andNameLike(name);
		criteria.andLangTypeEqualTo(langType);
		if (roseId.contains(2)) {
			criteria.andQianfaEqualTo(1);
		}
		return cpTopicMapper.selectByExample(example);
	}
	public CpTopic findTopicTopicId(Integer topicId) {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(topicId);
		return cpTopicMapper.selectByExample(example).get(0);
	}

	public List<CpTopic> showQianFaTopic() {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria();
		criteria.andQianfaEqualTo(1);
		return cpTopicMapper.selectByExample(example);
	}

	public List<CpTopic> showTopicToClient() {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria();
		criteria.andDisplayEqualTo(1);
		return cpTopicMapper.selectByExample(example);
	}

	public List<Map<String, Object>> showTopicToAdv() {
		return cpTopicMapper.showTopicToAdv();
	}

	public List<CpTopic> getTopicByQuery(Integer siteShow, Integer signShow, String createUser, String topicName,
			String endTime, String beginTime) {
		CpTopicExample example = new CpTopicExample();
		Criteria criteria = example.createCriteria();
		if(createUser != null && !createUser.equals("")){
			criteria.andCreateUserLike(createUser);
		}
		if(endTime != null && !endTime.equals("") && beginTime!= null && !beginTime.equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				//criteria.andCreateTimeBetween(sdf.parse(beginTime), sdf.parse(endTime));
				//criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(beginTime));
				//criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(endTime));
				 SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
				 Date from = sim.parse(beginTime);
				 Date to = sim.parse(endTime);
				 Calendar calendar = new GregorianCalendar(); 
			     calendar.setTime(to); 
			     calendar.add(calendar.DATE,1);
			     to=calendar.getTime();   
				 criteria.andCreateTimeBetween(from, to);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(siteShow != null && !siteShow.equals("") ){
			criteria.andSiteIdEqualTo(siteShow);
		}
		if(signShow != null && !signShow.equals("") ){
			criteria.andQianfaEqualTo(signShow);
		}
		if(topicName != null && !topicName.equals("")){
			criteria.andNameLike(topicName);
		}

		List<CpTopic> cpTopicList = cpTopicMapper.selectByExample(example);
		return cpTopicList;
	
	}


}
