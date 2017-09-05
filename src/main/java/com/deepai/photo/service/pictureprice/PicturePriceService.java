package com.deepai.photo.service.pictureprice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpPicturePriceExample;
import com.deepai.photo.bean.CpPicturePriceExample.Criteria;
import com.deepai.photo.mapper.CpPicturePriceMapper;


/**
 *  @author huqiankai: 
 * 
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class PicturePriceService {
	@Autowired
	private CpPicturePriceMapper cpPicturePriceMapper;

	public List<CpPicturePrice> show() {
		 CpPicturePriceExample example = new CpPicturePriceExample();
		  example.createCriteria();
		return cpPicturePriceMapper.selectByExample(example);
	}
	public void add(CpPicturePrice cpAdvPosition) {
		cpPicturePriceMapper.insertSelective(cpAdvPosition);
	}
	public CpPicturePrice showtoedit(Integer id) {
		return cpPicturePriceMapper.selectByPrimaryKey(id);
	}
	public void edit(CpPicturePrice cpAdvPosition) {
		cpPicturePriceMapper.updateByPrimaryKeySelective(cpAdvPosition);
	}
	public void delete(Integer id) {
		cpPicturePriceMapper.deleteByPrimaryKey(id);
		
	}
	
	public List<Map<String, Object>>  selectByIds(String [] ids){
		return cpPicturePriceMapper.selectByIds(ids);
	}
}
