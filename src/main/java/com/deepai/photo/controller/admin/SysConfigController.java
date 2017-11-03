package com.deepai.photo.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.READER;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpSystemConfig;
import com.deepai.photo.bean.CpSystemConfigExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpSystemConfigExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpSystemConfigMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;

/**
 * @author guoyanhong
 * 系统配置
 */
@Controller
@RequestMapping("/sysConfigCtro")
public class SysConfigController {
	private Logger log=Logger.getLogger(SysConfigController.class);
	
	@Autowired
	private SysConfigService configService; 
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpSystemConfigMapper configMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	
	
	/**
	 * 查询系统配置
	 * @param request
	 * @param configName 配置名称
	 * @param configCode 配置名
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getConfig")
	public Object getConfig(HttpServletRequest request,String configName,String configCode){
		ResponseMessage res=new ResponseMessage();
		try {
			String strWhere  = request.getParameter("strWhere");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", SessionUtils.getSiteId(request));
			map.put("deleteFlag", CommonConstant.BYTE0);
			map.put("strWhere", strWhere);
			PageHelper.startPage(request);
			List<CpSystemConfig> list=configMapper.getConfig(map);
			PageHelper.addPages(res, list);
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setData(list);
			PageHelper.addPagesAndTotal(res, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加系统内配置报错"+e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}
	/**
	 * 添加系统配置
	 * @param request
	 * @param config 配置详情
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addConfig")
	@LogInfo(content="添加系统配置",logTypeCode=CommonConstant.Config,opeType=0)
	public Object addConfig(HttpServletRequest request,CpSystemConfig config,String userToken){
		ResponseMessage res=new ResponseMessage();
		try {
			int siteId=SessionUtils.getSiteId(request);
			CommonValidation.checkParamBlank(config.getConfigCode(), "配置名");
			CommonValidation.checkParamBlank(config.getConfigName(), "配置名称");
			CommonValidation.checkParamBlank(config.getConfigValue(), "配置值");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId()!=(int)admin.getId()){
				res.setCode(CommonConstant.FAILURECODE);
				res.setMsg("操作失败，请登录！");
				return res;
			}
			CpSystemConfigExample example = new CpSystemConfigExample();
			example.createCriteria().andConfigCodeEqualTo(config.getConfigCode()).andSiteIdEqualTo(siteId).andDeleteFlagEqualTo(CommonConstant.BYTE0);
			List<CpSystemConfig> list=configMapper.selectByExample(example);
			if(list!=null&&list.size()>0){
				res.setCode(CommonConstant.FAILURECODE);
				res.setMsg("配置名已存在");
				return res;
			}
			example.clear();
			example.createCriteria().andConfigNameEqualTo(config.getConfigName()).andSiteIdEqualTo(siteId).andDeleteFlagEqualTo(CommonConstant.BYTE0);
			List<CpSystemConfig> list1=configMapper.selectByExample(example);
			if(list1!=null&&list1.size()>0){
				res.setCode(CommonConstant.FAILURECODE);
				res.setMsg("配置名称已存在");
				return res;
			}
			config.setSiteId(SessionUtils.getSiteId(request));
			config.setCreateTime(new Date());
			config.setCreateUser(SessionUtils.getUser(request).getUserName());
			configMapper.insertSelective(config);
			//同步至redis
			configService.setToRedis(config.getConfigCode(), siteId, config.getConfigValue());
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setOther(String.format("configId=%s", config.getId()));
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		}  catch (Exception e) {
			e.printStackTrace();
			log.error("添加系统内配置报错"+e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}
	/**
	 * 修改系统配置
	 * @param request
	 * @param config 系统配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editConfig")
	@LogInfo(content="修改系统配置",logTypeCode=CommonConstant.Config,opeType=2)
	public Object editConfig(HttpServletRequest request,CpSystemConfig config,String userToken){
		ResponseMessage res=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(config.getId()+"", "配置Id");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId()!= (int)admin.getId()){
				res.setCode(CommonConstant.FAILURECODE);
				res.setMsg("操作失败，请登录！");
				return res;
			}
			int siteId=SessionUtils.getSiteId(request);
			CpSystemConfigExample example = new CpSystemConfigExample();
			if(config.getConfigCode()!=null){
				example.createCriteria().andConfigNameEqualTo(config.getConfigName())
				.andSiteIdEqualTo(siteId).andDeleteFlagEqualTo(CommonConstant.BYTE0);
				List<CpSystemConfig> list=configMapper.selectByExample(example);
				if(list!=null&&list.size()>0&&list.get(0).getId()!=config.getId()){
					res.setCode(CommonConstant.FAILURECODE);
					res.setMsg("配置名已存在");
					return res;
				}
			}
			if(config.getConfigName()!=null){
				example.clear();
				example.createCriteria().andConfigNameEqualTo(config.getConfigName())
				.andSiteIdEqualTo(siteId).andDeleteFlagEqualTo(CommonConstant.BYTE0);
				List<CpSystemConfig> list1=configMapper.selectByExample(example);
				if(list1!=null&&list1.size()>0&&list1.get(0).getId()!=config.getId()){
					res.setCode(CommonConstant.FAILURECODE);
					res.setMsg("配置名称已存在");
					return res;
				}
			}
			config.setUpdateTime(new Date());
			config.setUpdateUser(SessionUtils.getUser(request).getUserName());
			configMapper.updateByPrimaryKeySelective(config);
			//同步至redis
			configService.setToRedis(config.getConfigCode(), siteId, config.getConfigValue());
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setOther(String.format("configId=%s", config.getId()));
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		}  catch (Exception e) {
			e.printStackTrace();
			log.error("修改系统内配置报错"+e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}
	/**
	 * 删除系统内配置
	 * @param request
	 * @param configIds 配置ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delConfigByIds")
	@LogInfo(content="删除系统配置",logTypeCode=CommonConstant.Config,opeType=1)
	public Object delConfigByIds(HttpServletRequest request,String configIds,String userToken){
		ResponseMessage res=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(configIds, "配置Id");
			CpUser cpUser = userRoleRightService.verify(userToken);
			CpUser admin = SessionUtils.getUser(request);
			if (cpUser ==null || (int)cpUser.getId()!=(int)admin.getId()){
				res.setCode(CommonConstant.FAILURECODE);
				res.setMsg("操作失败，请登录！");
				return res;
			}
			configService.delConfigsByIds(configIds,SessionUtils.getSiteId(request));
			
			res.setCode(CommonConstant.SUCCESSCODE);
			res.setMsg(CommonConstant.SUCCESSSTRING);
			res.setOther(String.format("configIds=%s", configIds));
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			res.setCode(e.getCode());
			res.setMsg(e.getMsg());
		}  catch (Exception e) {
			e.printStackTrace();
			log.error("修改系统内配置报错"+e.getMessage());
			res.setCode(CommonConstant.EXCEPTIONCODE);
			res.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return res;
	}
	
}
