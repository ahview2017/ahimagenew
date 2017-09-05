package com.deepai.photo.service.websiteInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpSite;
import com.deepai.photo.bean.CpWebsiteInfo;
import com.deepai.photo.bean.CpWebsiteInfoExample;
import com.deepai.photo.bean.CpWebsiteInfoExample.Criteria;
import com.deepai.photo.mapper.CpWebsiteInfoMapper;

/**
 * * @author huqiankai: 
 * *
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class CpWebsiteInfoService {
	@Autowired
	private CpWebsiteInfoMapper cpWebsiteInfoMapper;

	public List<CpWebsiteInfo> show(String strWhere, Integer langType) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("langType", langType);
		 map.put("strWhere", strWhere);
		return cpWebsiteInfoMapper.show(map);
	}

	public void add(CpWebsiteInfo cpWebsiteInfo) {
		cpWebsiteInfoMapper.insertSelective(cpWebsiteInfo);
	}

	public CpWebsiteInfo showtoedit(Integer id) {
		return cpWebsiteInfoMapper.showtoedit(id);
	}

	public void edit(CpWebsiteInfo cpWebsiteInfo) {
		cpWebsiteInfoMapper.updateByPrimaryKeySelective(cpWebsiteInfo);
	}

	public void delete(int i) {
		cpWebsiteInfoMapper.deleteByPrimaryKey(i);
	}

	public List<CpWebsiteInfo> search(String search, Integer langType) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("search", search);
		 map.put("langType", langType);
		return cpWebsiteInfoMapper.search(map);
	}

	public List<CpWebsiteInfo> showtoindex(int site, int geLangType) {
		CpWebsiteInfoExample example = new CpWebsiteInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andSiteIdEqualTo(site);
		criteria.andLangTypeEqualTo(geLangType);
		List<CpWebsiteInfo> selectByExample = cpWebsiteInfoMapper.selectByExample(example);
		return selectByExample;
	}
}
