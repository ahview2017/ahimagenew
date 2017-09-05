package com.deepai.photo.controller.adver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpMsg;
import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicAllpathExample;
import com.deepai.photo.bean.CpPicAllpathExample.Criteria;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpPicAllpathMapper;
import com.deepai.photo.service.admin.WaterMarkService;
import com.deepai.photo.service.adver.AdverService;
import com.deepai.photo.service.instant.StationMessiageService;
import com.deepai.photo.service.topic.TopicService;

/**
 * @author huqiankai: *
 * 
 */
@Controller
@RequestMapping("/adver")
public class AdverController {
	private Logger log = Logger.getLogger(AdverController.class);
	@Autowired
	private AdverService adverService;
	@Autowired
	private WaterMarkService waterMarkService;
	@Autowired
	private CpPicAllpathMapper cpPicAllpathMapper;
	@Autowired 
	private TopicService topicService;
	/**
	 * 显示广告位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/show")
	@SkipLoginCheck
	public Object show(HttpServletRequest request, HttpServletResponse response, Integer langType) {
		ResponseMessage result = new ResponseMessage();

		try {
			PageHelper.startPage(request);
			String strWhere = request.getParameter("strWhere");
			List<CpAdvPosition> list = adverService.search(strWhere,langType);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
			System.out.println(CommonConstant.SMALLHTTPPath);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示广告位信息，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 显示广告位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/adverToHomePage")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showToHomePage(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpAdvPosition> list = adverService.showToHomePage(SessionUtils.getSiteId(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示广告位信息，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 *  英文版前台广告位显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showToEnAdver")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showToEnAdver(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			List<CpAdvPosition> list = adverService.showToEnAdver(SessionUtils.getSiteId(request),SessionUtils.geLangType(request));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能显示广告位信息，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	
	/**
	 * 根据ID返回修改对象
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showtoedit")
	public Object showtoedit(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpAdvPosition showtoedit = adverService.showtoedit(id);
			String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(showtoedit.getCreateTime());
			showtoedit.setDatetime(datetime);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(showtoedit);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能查看广告位信息详情，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 添加广告位信息
	 * 
	 * @param request
	 * @param response
	 * @param cpAdvPosition
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@LogInfo(content="创建广告位信息",opeType=0,logTypeCode=CommonConstant.Adver)
	public Object add(HttpServletRequest request, MultipartFile wmFile, CpAdvPosition cpAdvPosition) {
		ResponseMessage result = new ResponseMessage();
		try {
			BufferedImage bi = ImageIO.read(wmFile.getInputStream());
			int width = bi.getWidth();
			int height = bi.getHeight();
			if (!wmFile.isEmpty()) {
			String pic_path = waterMarkService.insertOnePicture(wmFile,request);
			String originalFilename = wmFile.getOriginalFilename();
			cpAdvPosition.setFile(pic_path);
			cpAdvPosition.setPic(originalFilename);
			}
			cpAdvPosition.setSheight(height);
			cpAdvPosition.setSwidth(width);
			cpAdvPosition.setBheight(height);
			cpAdvPosition.setBwidth(width);
			cpAdvPosition.setSiteId(SessionUtils.getSiteId(request));
			adverService.add(cpAdvPosition);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("创建广告位信息失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * @param request
	 * @param response
	 * @param cpAdvPosition
	 * @param yposition
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@LogInfo(content = "修改广告位信息", opeType = 2, logTypeCode = CommonConstant.Adver)
	public Object edit(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false, value = "wmFile")MultipartFile[]  wmFile, CpAdvPosition cpAdvPosition) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (wmFile!=null&&wmFile.length>0) {
				String pic_path = waterMarkService.insertOnePicture(wmFile[0],request);
				cpAdvPosition.setFile(pic_path);
			}
			adverService.save(cpAdvPosition);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("修改广告位信息失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除广告位信息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@LogInfo(content = "删除广告位信息", opeType = 1, logTypeCode = CommonConstant.Adver)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				adverService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除广告位信息失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 搜索广告位信息
	 * 
	 * @param request
	 * @param response
	 * @param searchName
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping("/search")
	public Object search(HttpServletRequest request, HttpServletResponse response, String searchName) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			
			List<CpAdvPosition> list = adverService.search(searchName, null);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
			}
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("搜索广告位信息失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}*/
}
