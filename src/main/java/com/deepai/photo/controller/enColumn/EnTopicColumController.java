package com.deepai.photo.controller.enColumn;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.controller.adver.AdverController;
import com.deepai.photo.service.EnTopicColumService;
import com.deepai.photo.service.admin.SysConfigService;

/**
 * 后台专题栏目
 * @author clong
 *
 */
@Controller
@RequestMapping("enTopicColum")
public class EnTopicColumController {

	private Logger log = Logger.getLogger(AdverController.class);
	
	@Resource
	private EnTopicColumService enTopicColumService;
	
	/**
	 * 展示栏目
	 * @param cpTopic
	 * @return
	 */
	@RequestMapping("show")
	@ResponseBody
	public ResponseMessage show(CpTopic cpTopic){
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpLanmu> list = enTopicColumService.show(cpTopic);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("专题栏目，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 添加修改删除栏目
	 * @param cpTopic
	 * @param lanmuIds
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public ResponseMessage add(String cpTopic,String lanmuIds){
		ResponseMessage result = new ResponseMessage();
		try {
			enTopicColumService.insert(cpTopic, lanmuIds);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData("");
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("专题栏目，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 上传图片
	 * @param pic
	 * @param request
	 * @return
	 */
	@RequestMapping("upPic")
	@ResponseBody
	public ResponseMessage upPic(@RequestParam(required = false, value = "pic") MultipartFile pic,HttpServletRequest request){
		ResponseMessage result = new ResponseMessage();
		try {
			String allPath = enTopicColumService.upPic(pic,request);
			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(allPath);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("专题栏目上传图片失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
