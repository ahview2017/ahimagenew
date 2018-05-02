package com.deepai.photo.controller.enColumn;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.EnGroupManagement;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pagehelper.PageInfo;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.mapper.EnGroupManagementMapper;
import com.deepai.photo.service.enColumn.EnGroupManagementService;

/**
 * 群组
 * 
 * @author guodongfeng
 *
 */
@Controller
@RequestMapping("/groupManagementCtrl")
public class EnGroupManagementController {
	
	@Autowired
	private EnGroupManagementService enGroupManagementService;
	@Autowired
	private EnGroupManagementMapper groupManagementMapper;
	private Logger log = Logger.getLogger(EnGroupManagementController.class);
	/**
	 * 添加群组用户
	 * @param request
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addGroupManagementUser")
	public Object addGroupManagementUser(HttpServletRequest request,String userId, Integer groupId){
		ResponseMessage result = new ResponseMessage();
		try {
			String[] id = userId.split(",");
			for (String string : id) {
				enGroupManagementService.addGroupManagementUser(Integer.parseInt(string), groupId);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加群组用户失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除群组用户
	 * @param request
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteGroupManagementUser")
	public Object deleteGroupManagementUser(HttpServletRequest request,String userId, Integer groupId){
		ResponseMessage result = new ResponseMessage();
		try {
			String[] id = userId.split(",");
			for (String string : id) {
				enGroupManagementService.deleteGroupManagementUser(Integer.parseInt(string), groupId);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除群组用户失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查询分页群组
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupManagement")
	public Object getGroupManagement(HttpServletRequest request,Integer page,Integer rows, String groupName) {
		ResponseMessage result = new ResponseMessage();
		try {
			page=page==null?1:page;
			rows=rows==null?5:rows;
			PageHelper.startPage(page, rows);
			List<EnGroupManagement> groupManagementList = enGroupManagementService.getGroupManagement(groupName);
			PageInfo pageInfo = new PageInfo(groupManagementList);
			int c = (int)pageInfo.getTotal();
			int p = pageInfo.getPages();
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(groupManagementList);
			result.setPage(p);
			result.setOther(c);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("群组信息查询失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 查询所有群组
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupManagementAll")
	public Object getGroupManagementAll(HttpServletRequest request,String groupName) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<EnGroupManagement> groupManagementList = enGroupManagementService.getGroupManagement(groupName);
			for (EnGroupManagement enGroupManagement : groupManagementList) {
				int count = groupManagementMapper.getUserCount(enGroupManagement.getId());
				enGroupManagement.setCount(count);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(groupManagementList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("群组信息查询失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 查询群组的用户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupManagementUser")
	public Object getGroupManagementUser(HttpServletRequest request,Integer groupId, String userName,Integer page,Integer rows) {

		ResponseMessage result = new ResponseMessage();

		try {
			List<Map<String, Object>> userList = enGroupManagementService.getGroupManagementUser(groupId, userName);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(userList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("群组用户信息查询失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 添加群组
	 * @param request
	 * @param name
	 * @param memo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addGroupManagement")
	public Object addGroupManagement (HttpServletRequest request, String name, String memo) {
		ResponseMessage result = new ResponseMessage();
		try {
			enGroupManagementService.addGroupManagement(name, memo);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加群组信息失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		
		return result;
	}
	/**
	 * 修改群组
	 * @param request
	 * @param id
	 * @param name
	 * @param memo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editGroupManagement")
	public Object editGroupManagement (HttpServletRequest request, Integer groupId, String name, String memo) {
		ResponseMessage result = new ResponseMessage();
		try {
			enGroupManagementService.editGroupManagement(groupId, name, memo);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改群组信息失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		
		return result;
	}
	/**
	 * 删除群组
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteGroupManagement")
	public Object deleteGroupManagement (HttpServletRequest request, String groupId) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] strings = groupId.split(",");
			for (String string : strings) {
				enGroupManagementService.deleteGroupManagement(Integer.parseInt(string));
				enGroupManagementService.deleteGroupManagementUserByGroupId(Integer.parseInt(string));//删除群组时候同时删除群组里的用户
			
			}
			

			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除群组信息失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		
		return result;
	}
	
}
