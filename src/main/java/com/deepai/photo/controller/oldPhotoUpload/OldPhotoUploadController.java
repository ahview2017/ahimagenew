package com.deepai.photo.controller.oldPhotoUpload;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpOldpictureExcelInfo;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.service.oldPhotoUpload.OldPhotoUploadService;

@Controller
@RequestMapping("/oldPhotoUpload")
public class OldPhotoUploadController {
	private Logger log = Logger.getLogger(OldPhotoUploadController.class);
	@Autowired
	private OldPhotoUploadService oldPhotoUploadService;
	
	/**
	 * @param request
	 * @param response
	 * 老照片导入上传压缩包rar
	 */
	@ResponseBody
	@RequestMapping("/photoExcelUpload")
	@SkipAuthCheck
	public Object photoExcelUpload(HttpServletRequest request, HttpServletResponse response,MultipartFile zipFile) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser= SessionUtils.getUser(request);
			int siteId = SessionUtils.getSiteId(request);
			oldPhotoUploadService.uploadZip(zipFile, cpUser,siteId);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			String msg = e1.toString();
			if(msg.indexOf("RuntimeException")!=-1){
				e1.printStackTrace();
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(e1.getMessage());
			}else{
				e1.printStackTrace();
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg(CommonConstant.EXCEPTIONMSG);
			}
		}
		System.out.println("-------------------------------------------->");
		log.info("--------------------老照片上传成功------------------------>");
		return result;
	}
	
	
	/**
	 * @param request
	 * @param response
	 * 老照片导入上传压缩包rar
	 */
	@ResponseBody
	@RequestMapping("/localZipUpload")
	@SkipAuthCheck
	public Object localZipUpload(HttpServletRequest request, HttpServletResponse response,String fileName) {
		ResponseMessage result = new ResponseMessage();
		long startTime = System.currentTimeMillis();  
		log.info("--------------------本地老照片开始入库------------------------>");
		try {
			CpUser cpUser= SessionUtils.getUser(request);
			int siteId = SessionUtils.getSiteId(request);
			String excelReuslt = oldPhotoUploadService.moveZip(fileName, cpUser,siteId);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(""+excelReuslt);
		} catch (Exception e) {
			String msg = e.toString();
			if(msg.indexOf("RuntimeException")!=-1){
				e.printStackTrace();
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(e.getMessage());
			}else{
				e.printStackTrace();
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg(CommonConstant.EXCEPTIONMSG);
			}
			log.error("老照片分析入库异常信息", e);
		}
		long endTime = System.currentTimeMillis(); 
		log.info("--------------------老照片入库完成------------------------>");
		log.info("耗时----------------------->"+(startTime - endTime));
		return result;
	}
	
	
	/**
	 * 查询已上传的zip包列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPhotoExcelList")
	@SkipAuthCheck
	public Object getPhotoExcelList(HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			CpUser cpUser = SessionUtils.getUser(request);
			int siteId = SessionUtils.getSiteId(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("userId", cpUser.getId());
			List<CpOldpictureExcelInfo> list = oldPhotoUploadService
					.getPhotoExcelList(map);
			if (list!=null&&list.size()==0) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg("无数据");
			} else {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(list);
				PageHelper.addPages(result, list);
				PageHelper.addPagesAndTotal(result, list);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	/**
	 * 查询zip中excel中的图片数据列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getExcelPhotoExcelList")
	@SkipAuthCheck
	public Object getExcelPhotoExcelList(HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			String excelId = request.getParameter("excelId");
			if(StringUtil.isEmpty(excelId)){
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(CommonConstant.PARAMERRORMSG);
				return result;
			}
			CpUser cpUser = SessionUtils.getUser(request);
			int siteId = SessionUtils.getSiteId(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("userId", cpUser.getId());
			map.put("excelId", excelId);
			List<Map<String, Object>> list = oldPhotoUploadService
					.getExcelPhotoExcelList(map);
			if (list!=null&&list.size()==0) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg("无数据");
			} else {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(list);
				PageHelper.addPages(result, list);
				PageHelper.addPagesAndTotal(result, list);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 查询zip中excel中的图片数据列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOldPicResult")
	@SkipAuthCheck
	public Object getOldPicResult(HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage result = new ResponseMessage();
		try {
			String excelId = request.getParameter("excelId");
			if(StringUtil.isEmpty(excelId)){
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg(CommonConstant.PARAMERRORMSG);
				return result;
			}
			CpUser cpUser = SessionUtils.getUser(request);
			int siteId = SessionUtils.getSiteId(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("userId", cpUser.getId());
			map.put("excelId", excelId);
			log.info("excelId----------------------->"+excelId);
			List<Map<String, Object>> list = oldPhotoUploadService.getExcelPhotoExcelList(map);
			CpOldpictureExcelInfo excelInfo = oldPhotoUploadService.getPhotoExcelInfo(Integer.valueOf(excelId));
			String path = excelInfo.getFileAllpath();
			path = path.substring(0, path.lastIndexOf("/"));
			int picNums = oldPhotoUploadService.getPicNums(path);
//			log.info(path+"---------------------路径下有图片："+picNums);
//			log.info("入库成功的数目----------------------->"+list.size());
//			log.info("入库失败的数目----------------------->"+excelInfo.getPicUploadNum());
			Date date = new Date();
			int flag = (int) ((date.getTime()-excelInfo.getUploadDate().getTime())/1000);
			
			if ( excelInfo.getPicUploadNum()+list.size() != picNums&&(flag < picNums*3)) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg("处理还未完成，请稍后查询");
			} else {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				List<String> failList =oldPhotoUploadService.getOldPicFailList(list, path);
				result.setData(failList);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
