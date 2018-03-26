package com.deepai.photo.controller.statistical;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpGroupStatistical;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.service.statistical.GroupStatisticalService;

/**
 * 统计
 * 
 * * @author huqiankai: * *
 */
@Controller
@RequestMapping("groupStatistical")
public class GroupStatisticalController {
	private Logger log = Logger.getLogger(GroupStatisticalController.class);
	@Autowired
	private GroupStatisticalService groupStatisticalService;

	/**
	 * 统计初始图
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("showAllGroupStatistical")
	@ResponseBody
	public Object showAllGroupStatistical(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.showAllGroupStatistical();
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	

	/**
	 * 按照作责名字搜索投稿统计
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("searchGroupStatistical")
	@ResponseBody
	public Object searchGroupStatistical(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.searchGroupStatistical(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 按照地域查看投稿统计
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("GroupStatisticalForPlace")
	@ResponseBody
	public Object GroupStatisticalForPlace(HttpServletRequest request, HttpServletResponse response ,CpGroupStatistical cpGroupStatistical,Integer page,Integer rows) {
		ResponseMessage result = new ResponseMessage();
		try {
			rows=rows==null?10:rows;
			List<CpGroupStatistical> list = groupStatisticalService.GroupStatisticalForPlace(cpGroupStatistical);
			Map<String, Integer> map=new  HashMap<String, Integer>();
			for (CpGroupStatistical cpGroupStatistical2 : list) {
				map.put(cpGroupStatistical2.getPlace(), cpGroupStatistical2.getSendCount());
			}
			page=map.size()%rows==0?map.size()/rows:map.size()/rows+1;
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(map);
			result.setPage(page);
			result.setOther(map.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("按照地域查看投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 按照稿件类型查看投稿统计
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("GroupStatisticalForType")
	@ResponseBody
	public Object GroupStatisticalForType(HttpServletRequest request, HttpServletResponse response ,CpGroupStatistical cpGroupStatistical,Integer page,Integer rows ) {
		ResponseMessage result = new ResponseMessage();
		try {
			rows=rows==null?10:rows;
			List<CpGroupStatistical> list = groupStatisticalService.GroupStatisticalForType(cpGroupStatistical);
			       Map<String, Integer> map=new  HashMap<String, Integer>();
			for (CpGroupStatistical cpGroupStatistical2 : list) {
				map.put(cpGroupStatistical2.getCategaryName(), cpGroupStatistical2.getSendCount());
			}
			page=map.size()%rows==0?map.size()/rows:map.size()/rows+1;
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(map);
			result.setOther(map.size());
			result.setPage(page);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("按照稿件类型查看投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 按照稿件类型查看投稿统计（列表形式）
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("GroupStatisticalForTypeList")
	@ResponseBody
	public Object GroupStatisticalForTypeList(HttpServletRequest request, HttpServletResponse response ,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.GroupStatisticalForType(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("按照稿件类型查看投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 
	 * 按照地域查看投稿统计（列表形式）
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 *
	 */
	@RequestMapping("GroupStatisticalForPlaceList")
	@ResponseBody
	public Object GroupStatisticalForPlaceList(HttpServletRequest request, HttpServletResponse response ,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.GroupStatisticalForPlace(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("按照地域查看投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	/**
	 * 按照作者，稿件上传时间搜索投稿统计
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("GroupStatisticalForAuthorList")
	@ResponseBody
	public Object groupStatisticalForAuthorList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForAuthor(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示作者投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 按照稿件类别进行投稿统计
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("groupStatisticalForCategoryList")
	@ResponseBody
	public Object groupStatisticalForCategoryList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForCategory(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示作者投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 按照安徽省属城市投稿统计
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("groupStatisticalForAnHuiCityList")
	@ResponseBody
	public Object groupStatisticalForAnHuiCityList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForAnHuiCity(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示作者投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 网站展示栏目投稿统计
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("groupStatisticalForWebSiteShowColumnList")
	@ResponseBody
	public Object groupStatisticalForWebSiteShowColumnList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForWebSiteShowColumn(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示作者投稿统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 图片下载统计
	 * @author xiayunan
	 * @date 2018年3月13日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("groupStatisticalForPicDownloadList")
	@ResponseBody
	public Object groupStatisticalForPicDownloadList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForPicDownload(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示图片下载统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 编辑人员审核情况统计
	 * @author xiayunan
	 * @date 2018年3月22日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("groupStatisticalForEditorList")
	@ResponseBody
	public Object groupStatisticalForEditorList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForEditor(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示图片下载统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 用户投稿情况统计
	 * @author xiayunan
	 * @date 2018年3月22日
	 * @param request
	 * @param response
	 * @param cpGroupStatistical
	 * @return
	 */
	@RequestMapping("groupStatisticalForUserList")
	@ResponseBody
	public Object groupStatisticalForUserList(HttpServletRequest request, HttpServletResponse response,CpGroupStatistical cpGroupStatistical) {
		ResponseMessage result = new ResponseMessage();
		try {
			
			PageHelper.startPage(request);
			List<CpGroupStatistical> list = groupStatisticalService.groupStatisticalForUserList(cpGroupStatistical);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示图片下载统计，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	
}
