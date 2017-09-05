package com.deepai.photo.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.Constants;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpUserMapper;

/**
 * 基本信息(适用于值班编辑、摄影师、销售)
 * 
 * @author zhangshuo
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class BasicInfoService {
	@Autowired
	private CpBasicMapper cpBasicMapper;
	@Autowired
	private CpUserMapper cpUserMapper;

	/**
	 * 查询基本信息
	 * 
	 * @param CpUser
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selCpUserBasicInfo(CpUser cpUser)
			throws Exception {
		cpUser = cpUserMapper.selectByPrimaryKey(cpUser.getId());
		List<CpRole> role = cpBasicMapper.selectUserRoleByUId(cpUser.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(cpUser.getUserName())) {
			map.put("userName", cpUser.getUserName());
		}
		if (cpUser.getSubscriberType() != null) {
			map.put("subscriberType", cpUser.getSubscriberType());
		}
		if (cpUser.getUserStatus() != null) {
			map.put("userStatus", cpUser.getUserStatus());
		}
		if (StringUtil.isNotEmpty(cpUser.getTureName())) {
			map.put("tureName", cpUser.getTureName());
		}
		if (StringUtil.isNotEmpty(cpUser.getEmailBind())) {
			map.put("emailBind", cpUser.getEmailBind());
		}
		if (StringUtil.isNotEmpty(cpUser.getTelBind())) {
			map.put("telBind", cpUser.getTelBind());
		}
		if(cpUser.getIsPublish() != null){
			map.put("isPublish", cpUser.getIsPublish());
		}
		if (!role.isEmpty()) {
			map.put("roleName", role.get(Constants.ZERO).getRoleName());
		}
		if (StringUtil.isNotEmpty(cpUser.getIdCard())) {
			map.put("idCard", cpUser.getIdCard());
		}
		if (StringUtil.isNotEmpty(cpUser.getProvince())) {
			map.put("province", cpUser.getProvince());
		}
		if (StringUtil.isNotEmpty(cpUser.getCity())) {
			map.put("city", cpUser.getCity());
		}
		if (StringUtil.isNotEmpty(cpUser.getUnitName())) {
			map.put("unitName", cpUser.getUnitName());
		}
		if (StringUtil.isNotEmpty(cpUser.getAuthorName())) {
			map.put("authorName", cpUser.getAuthorName());
		}
		if (StringUtil.isNotEmpty(cpUser.getAddress())) {
			map.put("address", cpUser.getAddress());
		}
		if (StringUtil.isNotEmpty(cpUser.getZipcode())) {
			map.put("zipcode", cpUser.getZipcode());
		}
		if (StringUtil.isNotEmpty(cpUser.getTelContact())) {
			map.put("telContact", cpUser.getTelContact());
		}
		if (cpUser.getRegDate() != null) {
			map.put("regDate", cpUser.getRegDate());
		}
		if (StringUtil.isNotEmpty(cpUser.getEmailContact())) {
			map.put("emailContact", cpUser.getEmailContact());
		}
		if (cpUser.getLastLoginTime() != null) {
			map.put("lastLoginTime", cpUser.getLastLoginTime());
		}
		if (cpUser.getLogincount() != null) {
			map.put("logincount", cpUser.getLogincount());
		}
		if (cpUser.getContractNowlimitNum() != null) {
			map.put("contractNowlimitNum", cpUser.getContractNowlimitNum());
		}
		if (cpUser.getContractBuyNum() != null) {
			map.put("contractBuyNum", cpUser.getContractBuyNum());
		}
		if (cpUser.getContractNum() != null) {
			map.put("contractNum", cpUser.getContractNum());
		}
		if (cpUser.getContractStartTime() != null) {
			map.put("contractStartTime", cpUser.getContractStartTime());
		}
		if (cpUser.getContractEndTime() != null) {
			map.put("contractEndTime", cpUser.getContractEndTime());
		}
		if (cpUser.getAllDownloadNum() != null) {
			map.put("allDownloadNum", cpUser.getAllDownloadNum());
		}
		if (cpUser.getStandby3() != null) {
			map.put("standby3", cpUser.getStandby3());
		}
		if (cpUser.getContractLimitNum() != null) {
			map.put("contractLimitNum", cpUser.getContractLimitNum());
		}
		if (cpUser.getDownloadType()!= null) {
			switch (cpUser.getDownloadType()) {
			case 0:
				map.put("balanceLimitType", cpUser.getBalanceLimitType());
				map.put("account", cpUser.getAccount());
				break;
			case 1:
				map.put("balanceLimitType", cpUser.getContractLimitType());
				map.put("account", null);
				break;
			case 2:
				map.put("balanceLimitType", cpUser.getThreeLimitType());
				map.put("account", null);
				break;
			}
		}
		return map;
	}

	/**
	 * 查询下载信息
	 * 
	 * @param CpUser
	 * @return Map<String, Object>
	 */
	public Map<String, Object> selCpUserDownInfo(CpUser cpUser) {

		return null;
	}
}
