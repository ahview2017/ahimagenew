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
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpSite;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpWaterMarkPicture;
import com.deepai.photo.bean.CpWaterMarkPictureExample;
import com.deepai.photo.bean.CpWaterMarkPictureExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpWaterMarkPictureMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.WaterMarkService;

/**
 * @author guoyanhong
 * 管理员-水印图片管理
 */
@Controller
@RequestMapping("/waterMarkCtro")
public class WaterMarkController {
	private Logger log=Logger.getLogger(WaterMarkController.class);

	@Autowired
	private CpWaterMarkPictureMapper waterMarkMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private WaterMarkService waterMarkService;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	/**
	 * 查询水印图片
	 * @param request
	 * @param title 标题
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getWaterMarkByQuery")
	public Object getWaterMarkByQuery(HttpServletRequest request,String title){
		ResponseMessage result=new ResponseMessage();
		try {
			PageHelper.startPage(request);
			CpWaterMarkPictureExample example=new CpWaterMarkPictureExample();
			Criteria cr=example.createCriteria();
			if(StringUtil.isNotBlank(title)){
				title="%"+title+"%";
				cr.andTitleLike(title);
			}
			cr.andDeleteFlagEqualTo(CommonConstant.BYTE0);
			List<CpWaterMarkPicture> list=waterMarkMapper.selectByExample(example);
			PageHelper.addPages(result, list);
			for (CpWaterMarkPicture pic:list) {
				if(pic.getFilename()!=null){
					pic.setWmPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getWaterPathByName(pic.getFilename()));
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询水印图片出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 新建水印图片
	 * @param request
	 * @param ,MultipartFile  wmFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addWaterMark")
	@LogInfo(content="新建水印图片信息",opeType=0,logTypeCode=CommonConstant.Picture)
	public Object addWaterMark(HttpServletRequest request,CpWaterMarkPicture wmPic,MultipartFile  wmFile){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(wmPic.getTitle(), "标题");
			if(wmFile.isEmpty()){
				log.info("不支持该格式上传");
				result.setCode(CommonConstant.FILEERRORCODE);
				result.setMsg(CommonConstant.FILEERRORMSG);
				return result;
			}
			wmPic.setCreateUser(SessionUtils.getUser(request).getUserName());
			wmPic.setCreateTime(new Date());
			wmPic.setSiteId(SessionUtils.getSiteId(request));
			
			waterMarkService.insertWaterMark(wmPic, wmFile);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("waterMarkId=%s", wmPic.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("新建水印图片出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 更新水印图片
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateWaterMark")
	@LogInfo(content="修改水印图片信息",opeType=2,logTypeCode=CommonConstant.Picture)
	public Object updateWaterMark(HttpServletRequest request,CpWaterMarkPicture wmPic,MultipartFile  wmFile){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(wmPic.getId()+"", "水印ID");
			CpWaterMarkPicture pic=waterMarkMapper.selectByPrimaryKey(wmPic.getId());
			if(pic==null){
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在id=%s的水印信息", wmPic.getId()));
			}
			wmPic.setUpdateUser(SessionUtils.getUser(request).getUserName());
			wmPic.setUpdateTime(new Date());
			waterMarkService.updateWaterMark(wmPic, wmFile,SessionUtils.getSiteId(request));
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("waterMarkId=%s", wmPic.getId()));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("修改水印图片信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除水印图片
	 * @param wmIds 水印图片ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delWaterMark")
	@LogInfo(content="删除水印图片",opeType=1,logTypeCode=CommonConstant.Picture)
	public Object delWaterMark(HttpServletRequest request,String wmIds){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(wmIds, "水印图片id");

			CpUser user=SessionUtils.getUser(request);
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("username", user.getUserName());
			param.put("updatetime", new Date());
			param.put("ids", wmIds.split(",") );
			aboutPictureMapper.delWaterMarkByIds(param);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setOther(String.format("wmIds=%s",wmIds));
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除水印图片出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
