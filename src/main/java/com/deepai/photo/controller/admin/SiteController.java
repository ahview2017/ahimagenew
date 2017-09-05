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

import com.deepai.photo.bean.CpSite;
import com.deepai.photo.bean.CpSiteExample;
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
import com.deepai.photo.mapper.CpSiteMapper;

/**
 * @author guoyanhong
 * 管理员-子库管理
 */
@Controller
@RequestMapping("/siteCtro")
public class SiteController {
	private Logger log=Logger.getLogger(SiteController.class);
	
	@Autowired
	private CpSiteMapper cpSiteMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	
	/**
	 * 查询子库信息
	 * @param request
	 * @param siteName 子库名称，查询条件，可以为空
	 * @param siteId 子库ID，查询条件，可以为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSiteByQuery")
	public Object getSiteByQuery(HttpServletRequest request,String siteName,String siteId){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			if(StringUtil.isNotBlank(siteId)){
				param.put("siteId", siteId);
			}
			if(StringUtil.isNotBlank(siteName)){
				param.put("siteName", siteName);
			}
			PageHelper.startPage(request);
			List<Map<String,Object>> siteList=basicMapper.selectSiteByQuery(param);
			PageHelper.addPages(result, siteList);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(siteList);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询子库信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 新建子库信息
	 * @param request
	 * @param siteName 子库名称
	 * @param memo 备注
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSite")
	@LogInfo(content="新建子库",opeType=0,logTypeCode=CommonConstant.Site)
	public Object addSite(HttpServletRequest request,String siteName,String memo){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(siteName, "子库名称");
			CommonValidation.checkParamBlank(memo, "备注");
			CpUser user=SessionUtils.getUser(request);
			
			CpSite site=new CpSite();
			site.setSiteName(siteName);
			site.setSiteSummary(memo);			
			CpSiteExample ex=new CpSiteExample();
			ex.createCriteria().andSiteNameEqualTo(siteName).andDeleteFlagEqualTo(CommonConstant.BYTE0);
			List<CpSite> list=cpSiteMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("子库名称为【%s】已存在！",siteName));	
				return result;
			}				
			site.setDeleteFlag(CommonConstant.BYTE0);
			site.setCreateUser(user.getUserName());
			site.setCreateTime(new Date());
			cpSiteMapper.insertSelective(site);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("siteId=%s",site.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("新建子库出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 更新子库信息
	 * @param request
	 * @param siteName 子库名称
	 * @param memo 备注
	 * @param siteId 子库ID 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateSite")
	@LogInfo(content="修改子库",opeType=2,logTypeCode=CommonConstant.Site)
	public Object updateSite(HttpServletRequest request,String siteName,String memo,String siteId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(siteId, "子库ID");
			String[] param=new String[2];
			param[0]=siteName;
			param[1]=memo;
			CommonValidation.checkParamAllBlank(param, "子库名称和备注");
			CpUser user=SessionUtils.getUser(request);
			CpSiteExample ex=new CpSiteExample();
			ex.createCriteria().andSiteNameEqualTo(siteName).andDeleteFlagEqualTo(CommonConstant.BYTE0).andIdNotEqualTo(Integer.valueOf(siteId));
			List<CpSite> list=cpSiteMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("子库名称为【%s】已存在！",siteName));	
				return result;
			}
			CpSite site=new CpSite();
			if(StringUtil.isNotBlank(siteName)){
				site.setSiteName(siteName);
			}
			if(StringUtil.isNotBlank(memo)){
				site.setSiteSummary(memo);		
			}
			CpSite s=cpSiteMapper.selectByPrimaryKey(Integer.valueOf(siteId));
			if(s!=null&&s.getDeleteFlag()==CommonConstant.BYTE0){
				site.setId(Integer.valueOf(siteId));
				site.setUpdateUser(user.getUserName());
				site.setUpdateTime(new Date());
				cpSiteMapper.updateByPrimaryKeySelective(site);
				result.setOther(String.format("siteId=%s",site.getId()));
			}else{
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的子库 ！",siteId));
				return result;
			}				
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("修改子库出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 删除子库信息
	 * @param siteId 子库ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delSiteByIds")
	@LogInfo(content="删除子库",opeType=1,logTypeCode=CommonConstant.Site)
	public Object delSiteByIds(HttpServletRequest request,String siteIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(siteIds, "子库id");
			
			CpUser user=SessionUtils.getUser(request);
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("username", user.getUserName());
			param.put("updatetime", new Date());
			param.put("ids", siteIds.split(",") );
			basicMapper.delSiteByIds(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("siteIds=%s",siteIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除子库出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
