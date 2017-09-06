package com.deepai.photo.controller.picture;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.GroupQuery;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpWaterMarkPictureMapper;

/**
 * @Title 获取图片大小类型并显示；下载图片
 * @Author guoyanhong
 */
@Controller
@RequestMapping("/getPicture")
public class GetPicture {
	

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CpWaterMarkPictureMapper waterMarkMapper;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private ClientPictureMapper clientPictureMapper;
	/**
	 * 缺省的读文件的缓冲区大小
	 */
	final int Buffer_Size = 2048;

	
	/**
	 * 根据水印id获取水印图片
	 * @param request
	 * @param response
	 * @param wmId 水印记录id
	 */
	@RequestMapping("/getWatermarkPic")
	@SkipAuthCheck
	public void getWaterMarkPicture(HttpServletRequest request,HttpServletResponse response,int wmId) {
		
		InputStream is = null;
		ServletOutputStream sos = null;
		try {
			//根据水印id，查水印图片全路径
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("picType", 5);
			param.put("tragetId", wmId);
			String filePath=aboutPictureMapper.selectPicPathByType(param);
			if(filePath==null){
				return;
			}
			if(filePath.indexOf("..")>-1){
				return;
			}
			response.setContentType("image/*");
			is = new FileInputStream(filePath);
			sos = response.getOutputStream();
			byte[] buf = new byte[Buffer_Size];
			int len = 0;
			while ((len = is.read(buf)) > 0) {
				sos.write(buf, 0, len);
				sos.flush();
			}
		} catch (FileNotFoundException e) {
			logger.error("找不到水印图片 {}. ", wmId, e);
		} catch (Exception e) {
			logger.error("读取水印图片 {}出错. " + wmId, e);
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
				} // 关闭输出
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				} // 关闭流
			}
		}
	}
	/**
	 * 根据图片id获取图片小图
	 * @param request
	 * @param response
	 * @param picId 图片id
	 */
	@RequestMapping("/getSamllPic")
	@SkipAuthCheck
	public void getSamllPic(HttpServletRequest request,HttpServletResponse response,int picId) {
		
		InputStream is = null;
		ServletOutputStream sos = null;
		try {
			//根据图片id，查小图图片全路径
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("picType", 2);
			param.put("tragetId", picId);
			String filePath=aboutPictureMapper.selectPicPathByType(param);
			if(filePath==null){
				return;
			}
			if(filePath.indexOf("..")>-1){
				return;
			}
			response.setContentType("image/*");
			is = new FileInputStream(filePath);
			sos = response.getOutputStream();
			byte[] buf = new byte[Buffer_Size];
			int len = 0;
			while ((len = is.read(buf)) > 0) {
				sos.write(buf, 0, len);
				sos.flush();
			}
		} catch (FileNotFoundException e) {
			logger.error("找不到水印图片 {}. ", picId, e);
		} catch (Exception e) {
			logger.error("读取水印图片 {}出错. " + picId, e);
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
				} // 关闭输出
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				} // 关闭流
			}
		}
	}
	
	//TODO(客户端获取稿件)
	/**
	 * 获取客户端首页稿件
	 * @param request
	 * @param signId 签发分类id：1资料图片，2最新发布，3今日头条，4每日推荐，5一周最佳采用，6娱乐风尚，7财富经济，8台湾视角，9国际风采，10限价图片，11漫画图表，12两会
	 * @param limit 查询几条记录：如10条，8条，默认5
	 * @param picType 图片类型 1:水印，其他：无水印 默认无水印
	 * @param size 图片大小 （1:400，2:800，3:1200...）默认返回最小的
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/getClientGroups")
	@SkipLoginCheck
	public Object getClientGroups(HttpServletRequest request,Integer sginId,Integer limit,Integer picType,Integer size){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(sginId+"", "签发分类id");
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("sginId", sginId);
			limit=limit==null?5:limit;
			param.put("limit", limit);
			List<Map<String,Object>> list=clientPictureMapper.selectClientGroup(param);
			if(picType == 1){
			    for (Map<String,Object> map:list) {
                    if(map.containsKey("FILENAME")){
                        map.put("FilePath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByNameAndSize(map.get("FILENAME").toString(),request,size));
                    }
			    }
			}else{
			    for (Map<String,Object> map:list) {
                    if(map.containsKey("FILENAME")){
                        map.put("FilePath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getPathByNameAndSize(map.get("FILENAME").toString(),request,size));
                    }
                }
			}
			
			/* if(sginId==23||sginId==133||sginId==513){
				for (Map<String,Object> map:list) {
					if(map.containsKey("FILENAME")){
						map.put("wmPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(map.get("FILENAME").toString(),request));
					}
				}
			}else{
				for (Map<String,Object> map:list) {
					if(map.containsKey("FILENAME")){
						map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
					}
				}
			}*/
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("查询clint首页稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 获取客户端首页稿件
	 * @param request
	 * @param signId 签发分类id：1资料图片，2最新发布，3今日头条，4每日推荐，5一周最佳采用，6娱乐风尚，7财富经济，8台湾视角，9国际风采，10限价图片，11漫画图表，12两会
	 * @param cateId 稿件分类id
	 * @param parameter 关键字
	 * @param pType 0作者，1文件名，2标题
	 * @param timeType 0表示7天内，1一月内，2不限
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/getMoreGroups")
	@SkipLoginCheck
	public Object getMoreGroups(HttpServletRequest request,String sginId,Integer cateId,String parameter,Integer pType,Integer timeType){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			pType=pType==null?0:pType;
			param.put("pType", pType);
			if(parameter!=null && !parameter.equals("")){
				param.put("parameter", parameter);
			}
			timeType=timeType==null?2:timeType;//默认为不限
			param.put("timeType", timeType);
			if(sginId!=null){
				param.put("sginId", sginId);
			}
			if(cateId!=null){
				param.put("cateId", cateId);
			}
			PageHelper.startPage(request);
			List<Map<String,Object>> list=clientPictureMapper.selectMoreGroup(param);
			PageHelper.addPagesAndTotal(result, list);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("查询clint更多稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取客户端首页稿件
	 * @param request
	 * @param type 0：新闻图片，1：专题图片
	 * @param parameter 关键字
	 * @param pType 0作者，1文件名，2标题
	 * @param timeType 0表示7天内，1一月内，2不限
	 * @param langType 0:中文；1:英文
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/getPropertiesGroups")
	@SkipLoginCheck
	public Object getPropertiesGroups(HttpServletRequest request,Integer type,String parameter,Integer pType,Integer timeType,Integer langType){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(type+"", "图片类型");
			Map<String,Object> param=new HashMap<String, Object>();	
			param.put("properties", type);
			pType=pType==null?0:pType;
			param.put("pType", pType);
			if(parameter!=null){
				param.put("parameter", parameter);
			}
			if(langType!=null){
				param.put("langType", langType);
			}
			timeType=timeType==null?2:timeType;//默认为不限
			param.put("timeType", timeType);
			PageHelper.startPage(request);
			List<Map<String,Object>> list=clientPictureMapper.selectPropertiesGroup(param);
			PageHelper.addPagesAndTotal(result, list);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("查询clint更多稿件，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 获取client稿件详情
	 * @param request
	 * @param groupId 稿件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getClientGroupPics")
	@SkipLoginCheck
	public Object getClientGroupPics(HttpServletRequest request,Integer groupId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CpPicGroup group= clientPictureMapper.selectClientGroupPics(groupId);
			if(group==null){
				throw new InvalidHttpArgumentException(CommonConstant.NULLCODE, String.format("不存在稿件Id=%s的稿子", groupId));
			}
			if(group.getPics()!=null){
				for (CpPicture pic:group.getPics()) {
					if(pic.getFilename()!=null){
						pic.setSmallPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(pic.getFilename(),request));
						pic.setWmPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(pic.getFilename(),request));
					}
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(group);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("获取稿件详情，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 获取client图片详情
	 * @param request
	 * @param groupId 稿件id
	 * @param pictureId 稿件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getClientPicture")
	@SkipLoginCheck
	public Object getClientPicture(HttpServletRequest request,Integer groupId,Integer pictureId){
		ResponseMessage result=new ResponseMessage();
		try {
//			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CommonValidation.checkParamBlank(pictureId+"", "图片id");
			CpPicGroup group= clientPictureMapper.selectClientPicture(groupId,pictureId);
			if(group==null){
				throw new InvalidHttpArgumentException(CommonConstant.NULLCODE, String.format("不存在稿件Id=%s的图片", groupId));
			}
			if(group.getPics()!=null){
				for (CpPicture pic:group.getPics()) {
					if(pic.getFilename()!=null){
						pic.setWmPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(pic.getFilename(),request));
					}
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(group);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			logger.error("获取图片详情，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
