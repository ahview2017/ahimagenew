package com.deepai.photo.service.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpOrderDetail;
import com.deepai.photo.bean.CpOrderDetailExample;
import com.deepai.photo.bean.CpOrderForm;
import com.deepai.photo.bean.CpOrderFormExample;
import com.deepai.photo.bean.CpOrderFormExample.Criteria;
import com.deepai.photo.mapper.CpNoticeMapper;
import com.deepai.photo.mapper.CpOrderDetailMapper;
import com.deepai.photo.mapper.CpOrderFormMapper;

/**
 * * @author huqiankai: *
 * *
 */

@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class OrderDetailService {
@Autowired
private CpOrderFormMapper cpOrderFormMapper;
@Autowired  
private CpOrderDetailMapper  cpOrderDetailMapper;
	public List<CpOrderForm> show(List<Integer> roseId, String username,Integer langType) {
		CpOrderFormExample example = new CpOrderFormExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.addLangTypeEqualTo(langType);
		example.setOrderByClause("CREATE_TIME DESC");
		return cpOrderFormMapper.selectByExample(example);
	}
	public List<CpOrderForm> getOrderByQuery(Map<String, Object> param) {
		return cpOrderFormMapper.selectOrderByQuery(param);
	}
	public List<CpOrderForm> search(String orderno, Integer langType) {
		 CpOrderFormExample example = new CpOrderFormExample();
		 Criteria criteria = example.createCriteria();
		 criteria.addLangTypeEqualTo(langType);
		 if (orderno!=null) {
			 orderno="%"+orderno+"%";
			 criteria.andOrderNoLike(orderno);
		 }
		 return cpOrderFormMapper.selectByExample(example);
	}
	public CpOrderForm detail(String orderno) {
		CpOrderFormExample example = new CpOrderFormExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andOrderNoEqualTo(orderno);
		return cpOrderFormMapper.selectByExample(example).get(0);
	}
	public void add(CpOrderForm orderForm) {
		cpOrderFormMapper.insertSelective(orderForm);
	}
	public void delete(String id) {
		 CpOrderFormExample example = new CpOrderFormExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andOrderNoEqualTo(id);
		cpOrderFormMapper.deleteByExample(example);
	}
	
	public void deleteOrder(int id){
		CpOrderForm order = new CpOrderForm();
		order.setId(id);
		order.setOrderStatus(3);
		
		cpOrderFormMapper.updateByPrimaryKeySelective(order);
	}
	
	public void insertDetail(CpOrderDetail cpOrderDetail) {
		cpOrderDetailMapper.insertSelective(cpOrderDetail);
	}
	public List<CpOrderDetail> findByOd(String oid) {
		CpOrderDetailExample example = new CpOrderDetailExample();
		example.createCriteria().andOrderNoEqualTo(oid);
		return cpOrderDetailMapper.selectByExample(example);
	}
}
