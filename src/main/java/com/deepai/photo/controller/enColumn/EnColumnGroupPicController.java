package com.deepai.photo.controller.enColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupColumn;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.service.enColumn.EnColumnGroupPicService;
/**
 * @author yangerwen
 * 签发栏目稿件组图
 *
 */
@Controller
@RequestMapping("/getGroupPicCtro")
public class EnColumnGroupPicController {
	
	private Logger log = Logger.getLogger(EnColumnGroupPicController.class);
	@Autowired
	private EnColumnGroupPicService enColumnGroupPicService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param columnId
	 * @param groupType 0: 获取最新栏目组图  1: 获取衍生栏目组图和首页轮训图 <br/>
	 * 				  2:获取单个栏目下组图信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showColumnToGroupPic")
	@SkipLoginCheck
	public Object getColumnToGroupPic(HttpServletRequest request, HttpServletResponse response,
			String columnId,int groupType){
		ResponseMessage result = new ResponseMessage();
		List<Map<String,Object>> picGrouplist = null;
		try {
			switch (groupType) {
			case 0:
				picGrouplist = enColumnGroupPicService.selectNewestPicGroup(columnId,request);
				for (Map<String,Object> map:picGrouplist) {
					if(map.containsKey("FILENAME")){
						map.put("wmPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request));
					}
					List<Map<String ,Object>> pics = (List<Map<String, Object>>) map.get("pics");
					for (Map<String, Object> pic : pics) {
						if(pic.containsKey("pFileName")){
							pic.put("pSmallPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(pic.get("pFileName").toString(),request));
						}
					}
				}
				break;
			case 1:
				picGrouplist = enColumnGroupPicService.getGroupCarouselPicList(columnId,request);
				for (Map<String,Object> map:picGrouplist) {
					if(map.containsKey("FILENAME")){
						map.put("wmPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request));
					}
				}
				break;
			
			case 2:
				picGrouplist = enColumnGroupPicService.getGroupCarouselPicList(columnId,request);
				for (Map<String,Object> map:picGrouplist) {
					if(map.containsKey("FILENAME")){
						map.put("smallPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
					}
				}
				break;
			default:
				break;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(picGrouplist);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("组图信息或图片信息查询失败：" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 获组图取所有 父栏目下子栏目组图 
	 * @param request
	 * @param response
	 * @param columnId 栏目id信息  
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showAllGroupPic")
	@SkipLoginCheck
	public Object getAllGroupPic(HttpServletRequest request, HttpServletResponse response,
			String columnId){
		
		ResponseMessage result = new ResponseMessage();
		List<Map<String,Object>> picGroupList = null;
		try {
			picGroupList = enColumnGroupPicService.getAllPicGroupList(columnId,request);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(picGroupList);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("组图信息或图片信息查询失败：" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 获取单个栏目下所有组图（签发时间）
	 * @param request
	 * @param response
	 * @param columnId 栏目id信息  
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showAllColumnPic")
	@SkipLoginCheck
	public Object getColumnAndGroupPic(HttpServletRequest request, HttpServletResponse response,
			String columnId){
		ResponseMessage result = new ResponseMessage();

		try {
			PageHelper.startPage(request);
			List<Map<String,Object>> list= enColumnGroupPicService.getGroupPicList(columnId, request);
			PageHelper.addPagesAndTotal(result, list);
			
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("smallPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
			}

			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("组图信息或图片信息查询失败：" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
//	
//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @param columnId 栏目id信息  （包括子栏目id、栏目id、派生的栏目id）
//	 * @param groupType	稿件类型	0 获取衍生栏目组图和首页轮训图     1获取最新的组图
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/showColumnToGroupPic")
//	@SkipLoginCheck
//	public Object getColumnToGroupPic(HttpServletRequest request, HttpServletResponse response,
//			String columnId,int groupType){
//		ResponseMessage result = new ResponseMessage();
//		List<CpPicGroupColumn> picGrouplist = null;
//		try {
//			switch (groupType) {
//			case 0:
//				picGrouplist = enColumnGroupPicService.getPicDeriGroupList(columnId,request);
//				break;
//			case 1:
//				picGrouplist = enColumnGroupPicService.getGroupAllPicList(columnId,request);
//				break;
//			default:
//				break;
//			}
//			result.setCode(CommonConstant.SUCCESSCODE);
//			result.setMsg(CommonConstant.SUCCESSSTRING);
//			result.setData(picGrouplist);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			log.error("组图信息或图片信息查询失败：" + e1.getMessage());
//			result.setCode(CommonConstant.EXCEPTIONCODE);
//			result.setMsg(CommonConstant.EXCEPTIONMSG);
//		}
//		return result;
//	}
//	
//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @param columnId 栏目id信息  （包括子栏目id、栏目id、派生的栏目id）
//	 * @param groupType	稿件类型	0 获取的所有组图     1 获取子栏目组图 <br/>
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/showColumnAndGroupPic")
//	@SkipLoginCheck
//	public Object getColumnAndGroupPic(HttpServletRequest request, HttpServletResponse response,
//			Integer columnId,Integer maxPage,Integer currtPage,Integer moreColumn){
//		Integer startPage = 0;
//		if(currtPage == null || currtPage <= 1){
//			startPage=0;
//		}else{
//			startPage=(currtPage-1)*maxPage;
//		}
//		ResponseMessage result = new ResponseMessage();
//		List<CpColumn> cpPicGroupColumnList = null;
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		Integer count = null;
//		try {
//			if(moreColumn == 0){
//				cpPicGroupColumnList = enColumnGroupPicService.getOrderPicGroupList(columnId,maxPage,startPage,request);
//				result.setData(cpPicGroupColumnList);
//			}else{
//				List<CpPicGroup> orderGroupList = enColumnGroupPicService.getOrderGroupList(columnId, maxPage, startPage,request);
//				count = enColumnGroupPicService.getGroupCount(columnId);
//				result.setData(orderGroupList);
//			}
//			
//			map.put("count", count);
//			map.put("currtPage", currtPage);
//			result.setCode(CommonConstant.SUCCESSCODE);
//			result.setMsg(CommonConstant.SUCCESSSTRING);
//			result.setPage(map);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			log.error("组图信息或图片信息查询失败：" + e1.getMessage());
//			result.setCode(CommonConstant.EXCEPTIONCODE);
//			result.setMsg(CommonConstant.EXCEPTIONMSG);
//		}
//		return result;
//	}
	
	
	

}