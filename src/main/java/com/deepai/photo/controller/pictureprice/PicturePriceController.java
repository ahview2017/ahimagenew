package com.deepai.photo.controller.pictureprice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.service.pictureprice.PicturePriceService;

/**
 * @author huqiankai:
 * 
 */
@Controller
@RequestMapping("/pictureprice")
public class PicturePriceController {
	private Logger log = Logger.getLogger(PicturePriceController.class);
	@Autowired
	private PicturePriceService picturePriceService;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	/**
	 * @param request
	 * @param response
	 * @return list 显示所有信息
	 */
	@ResponseBody
	@RequestMapping("/show")
	public Object show(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpPicturePrice> list = picturePriceService.show();
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getUpdateTime());
				list.get(i).setDatetime(datetime);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示图片价格管理列表，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 添加图片价格
	 * 
	 * @param request
	 * @param response
	 * @param cpAdvPosition
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@LogInfo(content = "添加图片价格", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object add(HttpServletRequest request, HttpServletResponse response, CpPicturePrice cpPicturePrice) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpPicturePrice> list = picturePriceService.show();
			for (CpPicturePrice cpPicturePrice1 : list) {
				if (cpPicturePrice.getPictureType()==cpPicturePrice1.getPictureType()) {
					result.setCode(CommonConstant.REPEATCODE);
					result.setMsg("类型已存在！");
					return result;
				}
			}
			CpUser user = SessionUtils.getUser(request);
			cpPicturePrice.setEditName(user.getUserName());
			picturePriceService.add(cpPicturePrice);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能添加图片价格， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 修改图片价格
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showtoedit")
	public Object showtoedit(HttpServletRequest request,HttpServletResponse response,Integer id) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpPicturePrice cpPicturePrice=picturePriceService.showtoedit(id);
			String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cpPicturePrice.getUpdateTime());
			cpPicturePrice.setDatetime(datetime);
		result.setCode(CommonConstant.SUCCESSCODE);
		result.setMsg(CommonConstant.SUCCESSSTRING);	
		result.setData(cpPicturePrice);
	}catch(Exception e1){
		e1.printStackTrace();    
		log.error("不能修改图片价格，"+e1.getMessage());
		result.setCode(CommonConstant.EXCEPTIONCODE);
		result.setMsg(CommonConstant.EXCEPTIONMSG);
	}
	return result;
}
	
	/**
	 * 保存修改的图片价格
	 * @param request
	 * @param response
	 * @param cpAdvPosition
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@LogInfo(content = "保存修改的图片价格", opeType = 2, logTypeCode = CommonConstant.Other)
	public Object edit(HttpServletRequest request, HttpServletResponse response, CpPicturePrice cpAdvPosition) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpAdvPosition.setEditName(user.getUserName());
			cpAdvPosition.setUpdateTime(new Date());
			picturePriceService.edit(cpAdvPosition);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能保存修改的图片价格， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 删除图片价格
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content = "删除图片价格", opeType = 1, logTypeCode = CommonConstant.Notice)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String  id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			List<Map<String, Object>>  list = picturePriceService.selectByIds(split);
			for (Map<String, Object> map : list) {
				if ((int)map.get("picture_type")== 0) {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("删除失败,普通图不能删除");
					return result;
				}
			}
			for (String i : split) {
				picturePriceService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除图片价格失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}