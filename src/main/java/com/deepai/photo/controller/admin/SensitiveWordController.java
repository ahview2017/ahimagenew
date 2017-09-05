package com.deepai.photo.controller.admin;

import java.util.ArrayList;
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

import com.deepai.photo.bean.CpSensitiveWord;
import com.deepai.photo.bean.CpSensitiveWordExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.NumOrderMapper;
import com.deepai.photo.mapper.CpSensitiveWordMapper;
import com.deepai.photo.service.admin.NumOrderService;
import com.deepai.photo.service.admin.UserRoleRightService;

/**
 * @author guoyanhong
 * 敏感词管理
 */
@Controller
@RequestMapping("/sstvWordCtro")
public class SensitiveWordController {
	private Logger log=Logger.getLogger(SensitiveWordController.class);
	
	@Autowired
	private CpSensitiveWordMapper cpSstvWordMapper;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private NumOrderMapper cpOrderMapper;
	@Autowired
	private NumOrderService orderService;
	
	/**
	 * 查询敏感词信息
	 * @param request
	 * @param sstvWord 敏感词内容，查询条件，可以为空
	 * @param sstvWordId 敏感词ID，查询条件，可以为空
	 * @param status 状态0：正常；1：停用，查询条件，可以为空
	 * @param orderBy 排序字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSstvWordByQuery")
	public Object getSstvWordByQuery(HttpServletRequest request,String sstvWord,String sstvWordId,String status,String orderBy){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			if(StringUtil.isNotBlank(sstvWordId)){
				param.put("sstvWordId", sstvWordId);
			}
			if(StringUtil.isNotBlank(sstvWord)){
				param.put("sstvWord", sstvWord);
			}
			if(StringUtil.isNotBlank(status)){
				param.put("status", status);
			}
			if(StringUtil.isNotBlank(orderBy)){
				param.put("orderBy", orderBy);
			}else{
				param.put("orderBy", "ID");
			}
			PageHelper.startPage(request);
			List<Map<String,Object>> sstvWordList=basicMapper.selectSstvWordByQuery(param);
			PageHelper.addPages(result, sstvWordList);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(sstvWordList);
			PageHelper.addPagesAndTotal(result, sstvWordList);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询敏感词出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 新建敏感词信息
	 * @param request
	 * @param sstvWord 敏感词名称
	 * @param memo 备注
	 * @param status 状态0：正常；1：停用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSstvWord")
	@LogInfo(content="新建敏感词",opeType=0,logTypeCode=CommonConstant.Other)
	public Object addSstvWord(HttpServletRequest request,String sstvWord,String memo,String status,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(sstvWord, "敏感词");
			CommonValidation.checkParamBlank(memo, "备注");
			CommonValidation.checkParamBlank(status, "状态");
			CpUser user=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)user.getId()){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			CpSensitiveWordExample ex=new CpSensitiveWordExample();
			ex.createCriteria().andWordContentEqualTo(sstvWord);
			List<CpSensitiveWord> list=cpSstvWordMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("敏感词名称为【%s】已存在！",sstvWord));	
				return result;
			}				
			CpSensitiveWord sW=new CpSensitiveWord();
			sW.setWordContent(sstvWord);;
			sW.setMemo(memo);
			sW.setWordStatus(Byte.valueOf(status));
			sW.setCreateUser(user.getUserName());
			sW.setCreateTime(new Date());
			sW.setSiteId(SessionUtils.getSiteId(request));
			sW.setUpdateUser(user.getUserName());
			sW.setUpdateTime(new Date());
			orderService.insertSstv(sW);
			result.setOther(String.format("sstvWordId=%s",sW.getId()));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("新建敏感词出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 更新敏感词信息的状态
	 * @param request
	 * @param sstvWordId 
	// * @param sstvWord 敏感词 String sstvWord,String memo,
	// * @param memo 备注
	 * @param status 状态0：正常；1：停用
	 * @param unmOrder 排序号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateSstvWord")
	@LogInfo(content="更新敏感词",opeType=2,logTypeCode=CommonConstant.Other,memo="第二个参数表示状态0正常；1停用")
	public Object updateSstvWord(HttpServletRequest request,String sstvWordId,String status,Integer unmOrder,String sstvWord,String memo,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(sstvWordId, "敏感词ID");
			CommonValidation.checkParamBlank(sstvWord, "敏感词");
//			CommonValidation.checkParamBlank(status, "状态");
			CpUser user=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)user.getId()){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			CpSensitiveWord sOld=cpSstvWordMapper.selectByPrimaryKey(Integer.valueOf(sstvWordId));
			if(sOld!=null){
				CpSensitiveWord sW=new CpSensitiveWord();
//				sW.setWordContent(sstvWord);
//				sW.setMemo(memo);	
				if(StringUtil.isNotEmpty(status)){
					sW.setWordStatus(Byte.valueOf(status));
				}
				if(StringUtil.isNotEmpty(status)){
					sW.setMemo(memo);
				}
				sW.setWordContent(sstvWord);
				sW.setId(Integer.valueOf(sstvWordId));
				sW.setUpdateUser(user.getUserName());
				sW.setUpdateTime(new Date());
				if(unmOrder!=null){
					orderService.checkRoleOrderNum(unmOrder);
					sW.setUnmOrder(unmOrder);
				}				
				orderService.updateUnmOrderSstw(sW,	sOld);			
				result.setOther(String.format("sstvWordId=%s",sW.getId()));
			}else{
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的敏感词 ！",sstvWordId));
				return result;
			}				
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("更新敏感词出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 删除敏感词信息
	 * @param sstvWordId 敏感词ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delSstvWordByIds")
	@LogInfo(content="删除敏感词",opeType=1,logTypeCode=CommonConstant.Other)
	public Object delSstvWordByIds(HttpServletRequest request,String sstvWordIds,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(sstvWordIds, "敏感词id");
			CpUser user=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)user.getId()){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			String ids[]=sstvWordIds.split(",");
			List<Integer> values=new ArrayList<Integer>();
			for (int i = 0; i < ids.length; i++) {
				values.add(Integer.valueOf(ids[i]));
			}
			CpSensitiveWordExample example=new CpSensitiveWordExample();
			example.createCriteria().andIdIn(values);
			cpSstvWordMapper.deleteByExample(example);
			result.setOther(String.format("sstvWordIds=%s",sstvWordIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除敏感词出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
