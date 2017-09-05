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

import com.deepai.photo.bean.CpDownloadLevel;
import com.deepai.photo.bean.CpDownloadLevelExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpWaterMarkPicture;
import com.deepai.photo.bean.CpWaterMarkPictureExample;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpDownloadLevelMapper;
import com.deepai.photo.mapper.CpWaterMarkPictureMapper;

/**
 * @author guoyanhong
 * 管理员-下载级别
 */
@Controller
@RequestMapping("/downloadLevelCtro")
public class DownLoadLevelController {
	private Logger log=Logger.getLogger(DownLoadLevelController.class);
	
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private CpDownloadLevelMapper downloadLevelMapper;
	@Autowired
	private CpWaterMarkPictureMapper cpWaterMarkPictureMapper;
	
	/**
	 * 查询下载级别
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDownLevelByQuery")
	public Object getDownLevelByQuery(HttpServletRequest request,String levelName){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			if(StringUtil.isNotBlank(levelName)){
				param.put("levelName", levelName);
			}
			param.put("siteId", SessionUtils.getSiteId(request));
			PageHelper.startPage(request);
			List<Map<String,Object>> siteList=aboutPictureMapper.selectDownLevelByQuery(param);
			PageHelper.addPages(result, siteList);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(siteList);
			PageHelper.addPagesAndTotal(result, siteList);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询下载级别出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 这和上面的接口一样，但是该接口不经过权限验证~~只用来查询下载级别列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDownLevelList")
	@SkipAuthCheck
	public Object getDownLevelList(HttpServletRequest request,String levelName){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			if(StringUtil.isNotBlank(levelName)){
				param.put("levelName", levelName);
			}
			param.put("siteId", SessionUtils.getSiteId(request));
			List<Map<String,Object>> siteList=aboutPictureMapper.selectDownLevelByQuery(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(siteList);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询下载级别出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 新建下载级别
	 * @param request
	 * @param level 下载级别信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addDownloadLevel")
	@LogInfo(content="新建下载级别",opeType=0,logTypeCode=CommonConstant.Picture)
	public Object addDownloadLevel(HttpServletRequest request,CpDownloadLevel level){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(level.getLevelName(), "级别名称");
			CommonValidation.checkParamBlank(level.getLimitPxH()+"", "限制像素-h");
//			CommonValidation.checkParamBlank(level.getLimitPxW()+"", "限制像素-w");
			CpUser user=SessionUtils.getUser(request);
			int siteId=SessionUtils.getSiteId(request);
			
			CpDownloadLevelExample ex=new CpDownloadLevelExample();
			ex.createCriteria().andLevelNameEqualTo(level.getLevelName()).andDeleteFlagEqualTo(CommonConstant.BYTE0).andSiteIdEqualTo(siteId);
			List<CpDownloadLevel> list=downloadLevelMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("级别名称为【%s】已存在！",level.getLevelName()));	
				return result;
			}				
			if(level.getIsWatermark()==CommonConstant.BYTE0){//获取默认水印id
				CpWaterMarkPictureExample w=new CpWaterMarkPictureExample();
				w.createCriteria().andIsDefaultWmpicEqualTo(1).andDeleteFlagEqualTo(CommonConstant.BYTE0).andSiteIdEqualTo(siteId);
				List<CpWaterMarkPicture> l=cpWaterMarkPictureMapper.selectByExample(w);
				if(l!=null&&l.size()>0){
					level.setWatermarkPicId(l.get(0).getId());
				}
				if (l==null||l.size()<1) {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("请在水印管理中设置默认水印");
				}
			}
			level.setDeleteFlag(CommonConstant.BYTE0);
			level.setCreateUser(user.getUserName());
			level.setCreateTime(new Date());
			level.setSiteId(siteId);
			downloadLevelMapper.insertSelective(level);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("levelId=%s",level.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("新建下载级别出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 更新下载级别
	 * @param request
	 * @param level 下载级别   
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateDownloadLevel")
	@LogInfo(content="修改下载级别",opeType=2,logTypeCode=CommonConstant.Picture)
	public Object updateDownloadLevel(HttpServletRequest request,CpDownloadLevel level){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(level.getId()+"", "级别ID");
			CpUser user=SessionUtils.getUser(request);
			int siteId=SessionUtils.getSiteId(request);
			
			CpDownloadLevelExample ex=new CpDownloadLevelExample();
			ex.createCriteria().andDeleteFlagEqualTo(CommonConstant.BYTE0).andSiteIdEqualTo(siteId).andIdNotEqualTo(level.getId());
			List<CpDownloadLevel> list=downloadLevelMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				if(level.getLevelName()!=null&&level.getLevelName().equals(list.get(0).getLevelName())){
					result.setCode(CommonConstant.REPEATCODE);
					result.setMsg(String.format("下载级别名称为【%s】已存在！",level.getLevelName()));	
					return result;
				}
				if(level.getIsWatermark()==CommonConstant.BYTE0){//获取默认水印id
					CpWaterMarkPictureExample w=new CpWaterMarkPictureExample();
					w.createCriteria().andIsDefaultWmpicEqualTo(1).andDeleteFlagEqualTo(CommonConstant.BYTE0).andSiteIdEqualTo(siteId);
					List<CpWaterMarkPicture> l=cpWaterMarkPictureMapper.selectByExample(w);
					if(l!=null&&l.size()>0){
						level.setWatermarkPicId(l.get(0).getId());
					}
				}
				level.setUpdateTime(new Date());
				level.setUpdateUser(user.getUserName());
				downloadLevelMapper.updateByPrimaryKeySelective(level);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setOther(String.format("levelId=%s",level.getId()));
			}else{
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的下载级别 ！",level.getId()));
				return result;
			}				
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("修改下载级别出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 删除下载级别
	 * @param siteId 级别ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delLevelByIds")
	@LogInfo(content="删除下载级别",opeType=1,logTypeCode=CommonConstant.Picture)
	public Object delLevelByIds(HttpServletRequest request,String levelIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(levelIds, "下载级别id");
			
			CpUser user=SessionUtils.getUser(request);
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("username", user.getUserName());
			param.put("updatetime", new Date());
			param.put("ids", levelIds.split(",") );
			aboutPictureMapper.delDownLevelByIds(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("levelIds=%s",levelIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除下载级别出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
