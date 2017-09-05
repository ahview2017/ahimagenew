package com.deepai.photo.service.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpSensitiveWord;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.mapper.NumOrderMapper;
import com.deepai.photo.mapper.CpRoleMapper;
import com.deepai.photo.mapper.CpSensitiveWordMapper;


/**
 * @author guoyanhong
 * 排序号
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class NumOrderService {
	
	@Autowired
	private NumOrderMapper orderMapper;
	@Autowired
	private CpSensitiveWordMapper cpSstvWordMapper;
	@Autowired
	private CpRoleMapper cpRoleMapper;
	
	/**
	 * 修改敏感词排序号
	 * @param code
	 * @throws Exception 
	 */
	public void updateUnmOrderSstw(CpSensitiveWord swNew,CpSensitiveWord swOld) throws Exception{
		int orderNew=swNew.getUnmOrder()==null?swOld.getUnmOrder():swNew.getUnmOrder();//新排序号
		int orderOld=swOld.getUnmOrder();//旧排序号
		Map<String,Object> param=new HashMap<String, Object>();
		if(orderNew<orderOld){//排序号变小，旧排序号到新排序号前一位的所有排序都加1
			param.put("big", orderOld-1);
			param.put("small",orderNew);
			orderMapper.addOneOrderSstw(param);
		}else if(orderNew>orderOld){//排序号变大，旧排序号后一位到新排序号的所有排序都减1
			param.put("big", orderNew+1);
			param.put("small", orderOld);
			orderMapper.minusOneOrderSstw(param);
		}
		cpSstvWordMapper.updateByPrimaryKeySelective(swNew);
	}
	/**
	 * 修改角色排序号
	 * @param code
	 * @throws Exception 
	 */
	public void updateUnmOrderRole(CpRole rNew,CpRole rOld) throws Exception{
		int orderNew=rNew.getNumOrder()==null?rOld.getNumOrder():rNew.getNumOrder();//新排序号
		int orderOld=rOld.getNumOrder();//旧排序号
		Map<String,Object> param=new HashMap<String, Object>();
		if(orderNew<orderOld){//排序号变小，旧排序号到新排序号前一位的所有排序都加1
			param.put("big", orderOld-1);
			param.put("small",orderNew);
			orderMapper.addOneOrderRole(param);
		}else if(orderNew>orderOld){//排序号变大，旧排序号后一位到新排序号的所有排序都减1
			param.put("big", orderNew+1);
			param.put("small", orderOld);
			orderMapper.minusOneOrderRole(param);
		}
		cpRoleMapper.updateByPrimaryKeySelective(rNew);
	}
	/**
	 * 校验权限排序号大小，不能小于1，不能大于最大序号
	 * @param orderNum 排序号
	 */
	public void checkRoleOrderNum(int orderNum)throws Exception{
		InvalidHttpArgumentException e=new InvalidHttpArgumentException(CommonConstant.ORDERNUMCODE,CommonConstant.ORDERNUMMSG);
		if(orderNum<1){
			throw e;
		}
		int maxNum=orderMapper.selectRoleMaxUnm();
		if(orderNum>maxNum){
			throw e;
		}
	}
	/**
	 * 校验敏感词排序号大小，不能小于1，不能大于最大序号
	 * @param orderNum 排序号
	 */
	public void checkSstvOrderNum(int orderNum)throws Exception{
		InvalidHttpArgumentException e=new InvalidHttpArgumentException(CommonConstant.ORDERNUMCODE,CommonConstant.ORDERNUMMSG);
		if(orderNum<1){
			throw e;
		}
		int maxNum=orderMapper.selectSstvMaxUnm();
		if(orderNum>maxNum){
			throw e;
		}
	}
	
	/**
	 * 添加敏感词，设置排序号
	 * @param sW
	 */
	public void insertSstv(CpSensitiveWord sW){
		try {
			cpSstvWordMapper.insertSelective(sW);
			//cpOrderMapper.insertSstwOrder(sW);
			CpSensitiveWord nsw=new CpSensitiveWord();
			nsw.setId(sW.getId());
			nsw.setUnmOrder(sW.getId());
			cpSstvWordMapper.updateByPrimaryKeySelective(nsw);
		} catch (Exception e) {
			throw e;
		}
	}
}
