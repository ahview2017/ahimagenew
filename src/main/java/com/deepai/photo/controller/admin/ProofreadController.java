package com.deepai.photo.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpProofread;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpFlowMapper;
import com.deepai.photo.mapper.CpProofreadMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.picture.FlowService;

/**
 * @author guoyanhong
 * 管理员-校审配置
 */
@Controller
@RequestMapping("/proofreadCtro")
public class ProofreadController {
	private Logger log=Logger.getLogger(ProofreadController.class);
	
	@Autowired
	private CpProofreadMapper cpProofreadMapper;
	@Autowired
	private CpFlowMapper cpFlowMapper;
	@Autowired
	private FlowService flowService;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpUserMapper cpUserMapper;
	
	/**
	 * 查询校审配置信息
	 * @param request
	 * @param userName 校审人员用户名
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProofreadByQuery")
	public Object getProofreadByQuery(HttpServletRequest request,String userName,String beginTime,String endTime,Integer page,Integer rows,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			page=page==null?1:page;
			rows=rows==null?10:rows;
			int f=(page-1)*rows;
			if(StringUtil.isNotBlank(userName)){
				param.put("userName", userName);
			}
			if(!StringUtil.isEmpty(beginTime)){
				param.put("beginTime", DateUtils.fromStringToDate("yyyy-MM-dd",beginTime));
			}
			if(!StringUtil.isEmpty(endTime)){
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd",endTime));
			}
			param.put("langType", langType);
//			PageHelper.startPage(request);
			List<CpProofread> data=cpFlowMapper.selectProofreadByQuery(param);
			List<CpProofread> data1=null;
			if(data!=null&&data.size()>0){
				int e=f+rows;
				if(e>data.size()-1){
					e=data.size();
				}
				data1=data.subList(f, e);
				int pages=data.size()/rows;
				result.setPage(data.size()%rows>0?pages+1:pages);
			}
			
//			PageHelper.addPages(result, data);
//			List<CpProofread> siteList=cpFlowMapper.selectProofreadByQuery1(param);
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(data1);
			result.setOther(data.size());
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询校审配置信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 高级查询校审配置信息
	 * @param request
	 * @param userName 校审人员用户名
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/proofreadSearch")
	public Object proofreadSearch(HttpServletRequest request,String proofreadingOne, String proofreadingTwo, String proofreadingThree,String beginTime,String endTime,Integer page,Integer rows,Integer langType, Integer isenable){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			page=page==null?1:page;
			rows=rows==null?10:rows;
			int f=(page-1)*rows;
			if(StringUtil.isNotBlank(proofreadingOne)){
				param.put("proofreadingOne", proofreadingOne);
			}
			if(StringUtil.isNotBlank(proofreadingTwo)){
				param.put("proofreadingTwo", proofreadingTwo);
			}
			if(StringUtil.isNotBlank(proofreadingThree)){
				param.put("proofreadingThree", proofreadingThree);
			}
			if(!StringUtil.isEmpty(beginTime)){
				param.put("beginTime", DateUtils.fromStringToDate("yyyy-MM-dd",beginTime));
			}
			if(isenable != null){
				param.put("isenable" , isenable);
			}
			if(!StringUtil.isEmpty(endTime)){
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd",endTime));
			}
			param.put("langType", langType);
//			PageHelper.startPage(request);
			List<CpProofread> data=cpFlowMapper.proofreadSearch(param);
			List<CpProofread> data1=null;
			if(data!=null&&data.size()>0){
				int e=f+rows;
				if(e>data.size()-1){
					e=data.size();
				}
				data1=data.subList(f, e);
				int pages=data.size()/rows;
				result.setPage(data.size()%rows>0?pages+1:pages);
			}
			
//			PageHelper.addPages(result, data);
//			List<CpProofread> siteList=cpFlowMapper.selectProofreadByQuery1(param);
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(data1);
			result.setOther(data.size());
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询校审配置信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 新建校审配置信息
	 * @param request
	 * @param firstGrade 一级校审用户名，多个用&隔开
	 * @param secondGrade 二级校审用户名，多个用&隔开
	 * @param threeGrade  三级校审用户名，多个用&隔开
	 * @param bTime 开始时间
	 * @param eTime 结束时间
	 * @param isEnable 是否启用 0否，1是
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addProofread")
	@LogInfo(content="新建校审配置",opeType=0,logTypeCode=CommonConstant.Other)
	public Object addProofread(HttpServletRequest request,String firstGrade,String secondGrade,String threeGrade,String bTime,
			String eTime,String isEnable,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(firstGrade, "一级校审人员");
			CommonValidation.checkParamBlank(secondGrade, "二级校审人员");
			CommonValidation.checkParamBlank(threeGrade, "三级校审人员");
			
			CpUser user=SessionUtils.getUser(request);
			
//			Date maxDate=cpFlowMapper.selectMaxDate();//已配置校审流程的最大结束时间
			Date beginDate=null;
			Date endDate=null;
			if(StringUtil.isNotBlank(bTime)&&StringUtil.isNotBlank(eTime)){
				beginDate=DateUtils.fromStringToDate(DateUtils.sdfLong,bTime);
				endDate=DateUtils.fromStringToDate(DateUtils.sdfLong,eTime);
				//校验日期是否重叠
				CpProofread pfd=otherMapper.checkPfdDate(beginDate, endDate, langType);
				if(pfd!=null){//时间重叠，错误
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg(String.format("%s ~ %s校审配置已存在，日期不可重叠", DateUtils.formatDate(pfd.getBeginTime(), "yyyy-MM-dd"),DateUtils.formatDate(pfd.getEndTime(), "yyyy-MM-dd")));	
					return result;
				}
				if(beginDate.after(endDate)){
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("开始时间不得大于结束时间");	
					return result;
				}
			}else{
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("开始时间和结束时间不可为空");	
				return result;
			}
			CpProofread proofread=new CpProofread();
			proofread.setBeginTime(beginDate);
			proofread.setEndTime(endDate);
			if(StringUtil.isNotBlank(isEnable)){
				proofread.setIsenable(Byte.valueOf(isEnable));
			}
			proofread.setCreateUser(user.getUserName());
			proofread.setCreateTime(new Date());
			proofread.setSiteId(SessionUtils.getSiteId(request));
			proofread.setLangType(Integer.valueOf(langType));
			
			flowService.addProofread(proofread, firstGrade, secondGrade, threeGrade);
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("proofreadId=%s",proofread.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("新建校审配置出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 更新校审配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateProofread")
	@LogInfo(content="修改校审配置",opeType=2,logTypeCode=CommonConstant.Other)
	public Object updateProofread(HttpServletRequest request,String pfId,String firstGrade,String secondGrade,String threeGrade,String isEnable){
		ResponseMessage result=new ResponseMessage();
		try {
			
			CommonValidation.checkParamBlank(pfId+"", "校审配置ID");
			CommonValidation.checkParamBlank(firstGrade+"", "一级校审人员");
			CommonValidation.checkParamBlank(secondGrade+"", "二级校审人员");
			CommonValidation.checkParamBlank(threeGrade+"", "三级校审人员");
			
			CpUser user=SessionUtils.getUser(request);
			
			CpProofread s=cpProofreadMapper.selectByPrimaryKey(Integer.valueOf(pfId));
			if(s!=null&&s.getDeleteFlag()==CommonConstant.BYTE0){
				CpProofread proofread=new CpProofread();
				if(StringUtil.isNotBlank(isEnable)){
					proofread.setIsenable(Byte.valueOf(isEnable));
					/*if(isEnable.equals("1")){
						//校验日期是否重叠
						CpProofread pfd=otherMapper.checkPfdDate(s.getBeginTime(), s.getEndTime());
						if(pfd!=null){//时间重叠，错误
							result.setCode(CommonConstant.FAILURECODE);
							result.setMsg(String.format("%s-%s校审配置已存在，日期不可重叠", pfd.getBeginTime(),pfd.getEndTime()));	
							return result;
						}
					}*/
				}
				proofread.setId(Integer.valueOf(pfId));
				proofread.setCreateUser(user.getUserName());
				proofread.setCreateTime(new Date());
				proofread.setSiteId(SessionUtils.getSiteId(request));
				flowService.updateProofread(proofread, firstGrade, secondGrade, threeGrade);
				result.setOther(String.format("proofreadId=%s",proofread.getId()));
			}else{
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的校审配置 ！",pfId));
				return result;
			}				
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("修改校审配置出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 删除校审配置
	 * @param proofreadIds 校审配置ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delProofreadByIds")
	@LogInfo(content="删除校审配置",opeType=1,logTypeCode=CommonConstant.Other)
	public Object delProofreadByIds(HttpServletRequest request,String proofreadIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(proofreadIds, "校审配置id");
			
			CpUser user=SessionUtils.getUser(request);
			flowService.delPdf(user, proofreadIds, SessionUtils.getSiteId(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("proofreadIds=%s",proofreadIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除校审配置出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查询拥有值班权限人员:拥有一二三审权限的人
	 * @param request
	 * @param dutyType 1一审值班人员，2二审值班人员，3三审值班人员
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDutyUser")
	public Object delProofreadByIds(HttpServletRequest request,Integer dutyType,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			dutyType=dutyType==null?0:dutyType;
			int rightId=131;
			switch (dutyType) {
			case 1:
				rightId=131;
				break;
			case 2:
				rightId=136;
				break;
			case 3:
				rightId=141;
				break;
			}
			List<Map<String,Object>> dutyUser=aboutPictureMapper.selectUserByRight(rightId, langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(dutyUser);
			result.setOther(dutyUser.size());
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询拥有值班权限人员，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 修改稿件的一审人员
	 * @param request
	 * @param groupId 稿件Id
	 * @param editorName 一审人员
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateDutyUser")
	public Object updateDutyUser(HttpServletRequest request,Integer groupId,String editorName){
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			
			List<Integer> roles = userRoleRightService.getRoseId(user.getId());
			if(!(roles.contains(1) || roles.contains(26))){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("对不起，您没有管理员的权限！");
				return result;
			}
			CpUser editor = cpUserMapper.findUserByUserName(editorName);
							aboutPictureMapper.updateProcessUser(groupId, editor.getId(), editorName);
			Integer flag = aboutPictureMapper.updateDutyUser(groupId, editorName);
			
			if(!flag.equals(1)){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("修改失败！");
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("查询拥有值班权限人员，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	} 
	
}
