package com.deepai.photo.controller.enColumn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.service.enColumn.EnManagerColumnService;

/**
 * @author clong
 *	英文后台栏目
 */
@Controller
@RequestMapping("/enManagerColumn")
public class EnManagerColumnController {
	
	@Autowired
	private EnManagerColumnService enManagerColumnService;
	
	private Logger log = Logger.getLogger(EnManagerColumnController.class);
	
	
	/**
	 * 查询栏目
	 * @param request
	 * @param response
	 * @param cpColumn
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showEnManagerColumn")
	public Object show(HttpServletRequest request, HttpServletResponse response,CpColumn cpColumn) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpColumn> columnList = enManagerColumnService.selectColumnByRandomProperties(cpColumn);
			for (CpColumn cpColumn2 : columnList) {
				CpColumn column = new CpColumn();
				column.setPid(cpColumn2.getId());
				List<CpColumn> list = enManagerColumnService.selectColumnByRandomProperties(column);
				cpColumn2.setColumnList(list);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(columnList);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询栏目失败" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 添加栏目
	 * @param request
	 * @param response
	 * @param cpColumn
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addEnManagerColumn")
	public Object addEnManagerColumn(HttpServletRequest request, HttpServletResponse response,CpColumn cpColumn) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			if(cpColumn.getLangType() != 0){
				CpColumn columnRecommend = new CpColumn(); 
				columnRecommend.setName(cpColumn.getName()+"_recommend");
				columnRecommend.setPosition(2);		//琛嶇敓鏍忕洰
				columnRecommend.setState(1);
				columnRecommend.setLangType(cpColumn.getLangType());
				columnRecommend.setSignPosition(cpColumn.getSignPosition());//默认生成最大签发位
				enManagerColumnService.addEnManagerColumnRecommend(columnRecommend);
				Integer recommendId = enManagerColumnService.selectMaxId();
				cpColumn.setRecommendId(recommendId);
				enManagerColumnService.addEnManagerColumnRecommend(cpColumn);
			}else{
				enManagerColumnService.addEnManagerColumnRecommend(cpColumn);
			}
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("添加栏目失败" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 编辑栏目
	 * @param request
	 * @param response
	 * @param cpColumn
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editEnManagerColumn")
	public Object editEnManagerColumn(HttpServletRequest request, HttpServletResponse response,CpColumn cpColumn) {
		ResponseMessage result = new ResponseMessage();
		try {
			enManagerColumnService.editEnManagerColumn(cpColumn);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("编辑栏目失败" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除栏目
	 * @param request
	 * @param response
	 * @param cpColumn
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteEnManagerColumn")
	public Object deleteEnManagerColumn(HttpServletRequest request, HttpServletResponse response,CpColumn cpColumn) {
		ResponseMessage result = new ResponseMessage();
		try {
			enManagerColumnService.deleteEnManagerColumn(cpColumn);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除栏目失败" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
