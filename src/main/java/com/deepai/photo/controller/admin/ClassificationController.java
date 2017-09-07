package com.deepai.photo.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpCategory;
import com.deepai.photo.bean.CpRight;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.html.HtmlUtil;
import com.deepai.photo.mapper.CpRightMapper;
import com.deepai.photo.service.admin.ClassificationService;
import com.deepai.photo.service.admin.UserRoleRightService;

/**
 * @author zhangshuo 管理员-分类配置
 */
@Controller
@RequestMapping("/classification")
public class ClassificationController {
	private Logger log = Logger.getLogger(ClassificationController.class);

	@Autowired
	private ClassificationService classificationService;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpRightMapper cpRightMapper;

	/**
	 * 查询分类配置信息
	 * 
	 * @param request
	 * @return
	 */
	@SkipLoginCheck
	@RequestMapping("/selCpCategories")
	public void selCpCategories(HttpServletRequest request,
			HttpServletResponse response,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			//老照片权限控制 add by xiayunan 20170907
			CpUser user = SessionUtils.getUser(request);
			CpRight cpRight = cpRightMapper.selectByRightName("老照片管理");
			boolean hasRight = false;
			if(user!=null&&cpRight!=null){
				hasRight = userRoleRightService.checkUserRightByRightId(user.getId(),cpRight.getId());
			}
			Integer siteId = SessionUtils.getSiteId(request);
			List<Map<String, Object>> categorys = classificationService
					.selCpCategorys(siteId,langType);
			//返回信息添加老照片权限标识
			for(Map<String, Object> map:categorys){
				map.put("hasRight", hasRight?1:0);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(categorys);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询分类配置信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		HtmlUtil.writerJson(response, request, result);
		return;
	}

	/**
	 * 修改分类配置信息
	 * 
	 * @param request
	 *            , category, wmFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upCategory")
	@LogInfo(content = "修改分类配置信息", opeType = 2, logTypeCode = CommonConstant.Category)
	public Object upCategory(HttpServletRequest request, CpCategory category,
		@RequestParam(required = false,value="cgFile")MultipartFile cgFile) {
		ResponseMessage result = new ResponseMessage();
		
		try {
			category.setUpdateUser(SessionUtils.getUser(request).getUserName());
			category.setUpdateTime(new Date());
			category.setSiteId(SessionUtils.getSiteId(request));
			if (cgFile!=null&&!cgFile.isEmpty()) {
				classificationService.upCpCategory(category, cgFile,request);
			}else {
				classificationService.upCpCategoryWithOutPic(category);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("cpcategoryId=%s", category.getId()));
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改分类配置信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 新增分类配置信息
	 * 
	 * @param request
	 *            , category, wmFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/inCpCategory")
	@LogInfo(content = "新增分类配置信息", opeType = 0, logTypeCode = CommonConstant.Category)
	public Object inCpCategory(HttpServletRequest request, CpCategory category,
			@RequestParam(required = false,value="cgFile")MultipartFile cgFile,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (category.getParentId()==null) {
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg("添加错误请刷新");
			}
			category.setUpdateUser(SessionUtils.getUser(request).getUserName());
			category.setUpdateTime(new Date());
			category.setSiteId(SessionUtils.getSiteId(request));
			if (cgFile!=null&&!cgFile.isEmpty()) {
				classificationService.inCpCategory(category, cgFile, langType,request);
			}else {
				classificationService.inCpCategoryWithNoPic(category);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(String.format("cpcategoryId=%s", category.getId()));
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("新增分类配置信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除分类配置信息
	 * 
	 * @param request
	 *            , category
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delCpCategory")
	@LogInfo(content = "删除分类配置信息", opeType = 1, logTypeCode = CommonConstant.Category)
	public Object delCpCategory(HttpServletRequest request, CpCategory category) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (category.getId()==167) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("不能删除此分类");
				return result;
			}
			if (category.getParentId()!=null&&category.getParentId()==167) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("不能删除此分类");
				return result;
			}
			category.setSiteId(SessionUtils.getSiteId(request));
			classificationService.delCpCategory(category);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除分类配置信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
