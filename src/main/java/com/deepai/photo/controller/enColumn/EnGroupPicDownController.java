package com.deepai.photo.controller.enColumn;

import java.net.HttpURLConnection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.service.enColumn.EnGroupPicDownService;
/**
 * 
 * @author clong
 *
 * 组图和图片下载
 */
@Controller
@RequestMapping("enGroupPicDown")
public class EnGroupPicDownController {
	
	@Resource 
	private EnGroupPicDownService enGroupPicDownService;
	
	/**
	 * 单张原图下载或原图+图片说明
	 * @param picId
	 * @param request
	 * @param response
	 * @param type（0：单张原图，1：原图+图片说明）
	 * @param langType
	 * @param groupId
	 */
	@ResponseBody
	@RequestMapping("downSinglePic")
	public void downSinglePic(String picIds,HttpServletRequest request,HttpServletResponse response,Integer type,Integer langType,Integer groupId){
		try {
			enGroupPicDownService.downSinglePic(picIds,request, response, type, langType,groupId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 多个组图下载或多个组图+图片说明+稿件详情
	 * @param picId
	 * @param request
	 * @param response
	 * @param type（0：多个组图，1：多个组图+图片说明+稿件详情）
	 */
	@ResponseBody
	@RequestMapping("downGrouplePic")
	public void downGrouplePic(String groupIds,HttpServletRequest request,HttpServletResponse response,Integer type, Integer langType){
		try {
			enGroupPicDownService.downGrouplePic(groupIds,request, response, type, langType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
