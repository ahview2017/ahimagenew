package com.deepai.photo.controller.enColumn;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpFriendshipLink;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.service.enColumn.FriendshipLinkService;
/**
 * 友情连接
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/friendshipLink")
public class FriendshipLinkController {
	@Autowired FriendshipLinkService friendshipLinkService;
	private Logger log = Logger.getLogger(EnColumnController.class);
/**
 * 友情链接列表
 * @param request
 * @param langType
 * @return
 */
	@SkipLoginCheck
	@ResponseBody
	@RequestMapping("/friendshipLinkShow")
	public Object getFriendshipLinkList(HttpServletRequest request, Integer langType){
		ResponseMessage result = new ResponseMessage();
		try {
			
			List<CpFriendshipLink> friendshipLinkList = friendshipLinkService.selectFriendshipLinkList(langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(friendshipLinkList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("友情连接展示失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 添加友情连接
	 * @param request
	 * @param langType
	 * @param title
	 * @param orderId
	 * @param friendshipLink
	 * @param remark
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addFriendshipLink")
	public Object addFriendshipLink(HttpServletRequest request, Integer langType, String title, Integer orderId, String friendshipLink, String remark, Integer state){
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			Date createTime = new Date();
			friendshipLinkService.addFriendshipLink(title, orderId, friendshipLink, createTime, cpUser.getUserName(), cpUser.getId(), remark, langType, state);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("友情连接添加失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除友情链接
	 * @param request
	 * @param langType
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFriendshipLink")
	public Object deleteFriendshipLink(HttpServletRequest request, Integer langType,String id){
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String string : split) {
				friendshipLinkService.deleteFriendshipLink(Integer.parseInt(string));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("友情连接删除失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 修改友情连接
	 * @param request
	 * @param id
	 * @param title
	 * @param orderId
	 * @param friendshipLink
	 * @param remark
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editFriendshipLink")
	public Object editFriendshipLink(HttpServletRequest request, Integer id,String title,  Integer orderId, String friendshipLink, String remark, Integer state){
		ResponseMessage result = new ResponseMessage();
		try {
			
			friendshipLinkService.editFriendshipLink(id, title, orderId, friendshipLink, remark, state);

			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("友情连接修改失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
