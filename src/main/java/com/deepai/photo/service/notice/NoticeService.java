package com.deepai.photo.service.notice;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpAdvPositionExample;
import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpNoticeExample;
import com.deepai.photo.bean.CpNoticeExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.util.date.DateTimeUtil;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.bean.CpSite;
import com.deepai.photo.mapper.CpMsgMapper;
import com.deepai.photo.mapper.CpNoticeMapper;

/**
 * * @author huqiankai: *
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class NoticeService {
	@Autowired
	private CpNoticeMapper cpNoticeMapper;

	// 显示所有信息
	public List<CpNotice> show(Integer langType) {
		Map<String, String>map=new HashMap<>();
		if (langType!=null) {
			map.put("langType", langType+"");
		}
		return cpNoticeMapper.showToAdmin(map);
	}

	public void add(CpNotice cpNotice) {
		if (cpNotice.getPosition()!=null) {
			CpNoticeExample example = new CpNoticeExample();
			Criteria criteria = example.createCriteria();
			criteria.andPositionEqualTo(cpNotice.getPosition());
			criteria.andTopicIdEqualTo(cpNotice.getTopicId());
			List<CpNotice> list = cpNoticeMapper.selectByExample(example);
			if (list!=null&&list.size()>0) {
				cpNoticeMapper.changSomePostion(cpNotice);
			}
			cpNoticeMapper.insertSelective(cpNotice);
		}else{
			cpNoticeMapper.insertSelective(cpNotice);
		}
	}

	public void delete(int id) {
		cpNoticeMapper.deleteByPrimaryKey(id);
	}

	public void edit(CpNotice cpNotice) {
		cpNoticeMapper.updateByPrimaryKeySelective(cpNotice);
	}

	public List<CpNotice> search(String searchName,String timeFrom,String timeTo,
			String creater,Integer status,Integer tipId, Integer langType, int siteId) throws ParseException {
		Map<String, Object>map=new HashMap<>();
		if (!StringUtil.isEmpty(searchName)) {
			  map.put("searchName", searchName);
		}
		if (langType!=null) {
			map.put("langType", langType+"");
		}
		if(status!=null){
			map.put("status", status+"");
		}
		if(tipId!=null){
			map.put("tipId", tipId+"");
		}
		if(!StringUtil.isEmpty(timeFrom)){
			map.put("timeFrom", DateUtils.fromStringToDate("yyyy-MM-dd",timeFrom));
		}
		if(!StringUtil.isEmpty(timeTo)){
			map.put("timeTo", DateUtils.fromStringToDate("yyyy-MM-dd",timeTo));
		}
		if(!StringUtil.isEmpty(creater)){
			map.put("creater", creater);
		}
		
		map.put("siteId", siteId);
		return cpNoticeMapper.search(map);
	}

	public CpNotice showtoedit(Integer id) {
		return cpNoticeMapper.selectByPrimaryKey(id);
	}

	public List<CpNotice> showToHomePage(Map<String, Integer> map) {
		return cpNoticeMapper.showToHomePage(map);
	}

	public void saveLanmuAdv(String[] adv, int topicId, int lanmuPosition, int advID) {
		CpNotice cpNotice = new CpNotice();
		for (String noticecontent : adv) {
			cpNotice.setTopicId(topicId);
			cpNotice.setNoticeContent(noticecontent);
			cpNotice.setPosition(lanmuPosition);
			cpNoticeMapper.insertSelective(cpNotice);
		}
	}

	public void saveLanmuAdv(List<CpNotice> noticelist) {
		for (CpNotice cpNotice : noticelist) {
			if (cpNotice.getId() == null) {
				cpNoticeMapper.insertSelective(cpNotice);
			}
			cpNoticeMapper.updateByPrimaryKeySelective(cpNotice);
		}
	}

	public List<CpNotice> findNoticeByTopicId(Integer topicId) {
		return cpNoticeMapper.findNoticeByTopicId(topicId);
	}

	public List<CpNotice> showMoreLanmuWithAdv(Integer topicId) {
			return cpNoticeMapper.showMoreLanmuWithAdv(topicId);
		}

	public void edit1(CpNotice cpNotice, Integer yposition, Integer position) {
		cpNotice.setPosition(position);
		cpNotice.setYposition(yposition);
		cpNoticeMapper.postionToBig(cpNotice);
		cpNoticeMapper.updateByPrimaryKeySelective(cpNotice);
	}

	public void edit2(CpNotice cpNotice, Integer yposition, Integer position) {
		cpNotice.setPosition(position);
		cpNotice.setYposition(yposition);
		cpNoticeMapper.postionToSmall(cpNotice);
		cpNoticeMapper.updateByPrimaryKeySelective(cpNotice);
//		cpNoticeMapper.chang0(cpNotice);
	}

	public List<CpNotice> findPreview(Integer topicId) {
		return cpNoticeMapper.findPreview(topicId);
	}
}
