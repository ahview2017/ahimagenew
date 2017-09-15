package com.deepai.photo.controller.enColumn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.service.enColumn.EnColumnService;

@Controller
@RequestMapping("/enColumn")
public class EnColumnController {
	@Autowired
	private EnColumnService enColumnService;
	private Logger log = Logger.getLogger(EnColumnController.class);
	@ResponseBody
	@RequestMapping("/showEnColumn")
	@SkipLoginCheck
	public Object show(HttpServletRequest request, HttpServletResponse response,  Integer pid, Integer position, Integer state ,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			if(!pid.equals("") && pid != null){
				if(!state.equals("") && state != null){
					List<CpColumn> ColumnList = enColumnService.selectEnColumnList(pid, position, state, langType);
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
					result.setData(ColumnList);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("栏目展示失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 根据栏目id查询栏目的信息
	 * @param request
	 * @param response
	 * @param ColumnId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showColumnById")
	@SkipLoginCheck
	public Object showUpColumn (HttpServletRequest request, HttpServletResponse response, Integer columnId){
		
		ResponseMessage result = new ResponseMessage();
		try {
			if(!columnId.equals("") || columnId != null){
				CpColumn cpColumn = enColumnService.selectEnUpColumnList(columnId);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(cpColumn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("栏目展示失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		
		return result;
		
	}
	 /**
	  * 查询所有的栏目
	  * @param request
	  * @param response
	  * @return
	  */
	@ResponseBody
	@RequestMapping("/showUpColumnAll")
	public Object selectAll(HttpServletRequest request, HttpServletResponse response,Integer langType){
		ResponseMessage result = new ResponseMessage();
		
		try {
			List<CpColumn> cpCoLumnList = enColumnService.selectUpColumnAll(langType);
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(cpCoLumnList);		
		} catch (Exception e) {
			e.printStackTrace();
			log.error("栏目展示失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
		
	}
	
	
	/**
	 * 根据栏目ID查询子栏目
	 * @author xiayunan
	 * @date  2017-09-14
	 * @param request
	 * @param response
	 * @param lanmuid
	 * @return
	 */
	@ResponseBody
	@SkipLoginCheck
	@RequestMapping("/selChildColumn")
	public Object selChildColumn(HttpServletRequest request, HttpServletResponse response, int pColumnId) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpColumn> cpCoLumnList = enColumnService.selectNextColumn(pColumnId);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpCoLumnList);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询栏目ID为【"+pColumnId+"】失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
