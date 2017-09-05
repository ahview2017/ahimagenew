package com.deepai.photo.service.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpShopCar;
import com.deepai.photo.bean.CpShopCarExample;
import com.deepai.photo.bean.CpShopCarExample.Criteria;
import com.deepai.photo.mapper.CpShopCarMapper;

/**
 * * @author huqiankai: * @date 创建时间：2017年1月15日 下午4:14:23 * @version 1.0
 * * @parameter * @since * @return
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class CarService {
	@Autowired
  private 	CpShopCarMapper cpShopCarMapper; 

	public CpShopCar findById(Integer pid) {
		 CpShopCarExample example = new CpShopCarExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andPicIdEqualTo(pid);
		 return  cpShopCarMapper.selectByExample(example).get(0);
	}

	public void add(CpShopCar cpShopCar) {
		  cpShopCarMapper.insertSelective(cpShopCar);
	}

	public List<CpShopCar> findByOd(String oid) {
		CpShopCarExample example = new CpShopCarExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andOrderIdEqualTo(oid);
		return cpShopCarMapper.selectByExample(example);
	}

	public void delete(String id) {
		CpShopCarExample example = new CpShopCarExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andOrderIdEqualTo(id);
		 cpShopCarMapper.deleteByExample(example);
	}
	

}
