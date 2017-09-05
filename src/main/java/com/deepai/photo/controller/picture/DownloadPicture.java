package com.deepai.photo.controller.picture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpDownloadLevel;
import com.deepai.photo.bean.CpOrderForm;
import com.deepai.photo.bean.CpOrderFormExample;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpOrderFormMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.picture.DownloadService;

import net.sf.json.JSONObject;


/**
 * @Title 用户下载图片，生成订单，处理结算
 * @Author guoyanhong
 */
@Controller
@RequestMapping("/downloadPicture")
public class DownloadPicture {


	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private ClientPictureMapper clientPictureMapper;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private   CpOrderFormMapper  orderFormMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	/**
	 * 缺省的读文件的缓冲区大小
	 */
	final int Buffer_Size = 2048;


	/**
	 * 客户端用户下载生成订单，根据用户下载类型判断是否可以下载，余额或合同是否够用
	 * @param request
	 * @param picIds 下载图片id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("downForOrder")
	@SkipAuthCheck
	public Object downForOrder(HttpServletRequest request,String picIds,Integer langType) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(picIds, "下载图片id");
			
			CpUser loginUser=SessionUtils.getUser(request);
			CpUser user=cpUserMapper.selectByPrimaryKey(loginUser.getId());
			
			//获取用户下载级别
			CpDownloadLevel downLevel =clientPictureMapper.selectUserDownLevel(user.getId());
			if (downLevel==null) {
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADLEVELMSG);
			}
			if (langType == null) {
				langType = 0;
			}
			//判断本次多下载的图片中包含（72小时内）已经下载过的图片，这些图片不能在计费，将这些图片独立出来不放入新订单中
			List<Map<String,Object>> hasPayList = new ArrayList<Map<String,Object>>();
			List<CpOrderForm> orders=downloadService.downAndOrder(user,hasPayList, picIds, langType);
			result.setData(hasPayList);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setOther(orders);
		}catch (InvalidHttpArgumentException e) {
			logger.error("下载出错，",e);
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch(Exception e1){
			e1.printStackTrace();
			logger.error("下载出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 根据订单ID，下载图片
	 * @param request
	 * @param res
	 * @param orderId 订单号
	 * @param type 0图片下载，1图文下载
	 * @param //picData=[{"picId":"1","groupId":"1"}] picId为图片ID，groupId为图片稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("downByOrder")
	@SkipAuthCheck
	public Object downByOrder(HttpServletRequest request,HttpServletResponse response,Integer orderId,String picId,Integer type, Integer langType) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(orderId+"", "订单id");
			CpUser loginUser=SessionUtils.getUser(request);
			CpUser user=cpUserMapper.selectByPrimaryKey(loginUser.getId());
			int siteId =SessionUtils.getSiteId(request);
			JSONObject jsonObject = new JSONObject();
			List<CpRole> userRole = basicMapper.selectUserRoleByUId(user.getId());
			for (CpRole cpRole : userRole) {
				String key = cpRole.getId().toString();
				String value = cpRole.getRoleName();
				jsonObject.accumulate(key, value);
			}
			if(jsonObject.containsKey("1")){//是否是管理员
				adminDownByOrder( request,response, orderId, type, langType);
			}else{
				downloadService.downAndPay(orderId, type, user, siteId, response, request, langType);
			}
			
			//=============================================>图片下载总数是不是要和下载记录中的图片都一致
			CpOrderFormExample eo=new CpOrderFormExample();
			eo.createCriteria().andIdEqualTo(orderId).andUserIdEqualTo(user.getId());
			List<CpOrderForm> list=orderFormMapper.selectByExample(eo);
			user.setAllDownloadNum(list.get(0).getPicCount()+user.getAllDownloadNum());
//			cpUserMapper.updateByPrimaryKeySelective(user);
			//=============================================>
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		}catch (InvalidHttpArgumentException e) {
			logger.error("下载出错，",e);
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch(Exception e1){
			e1.printStackTrace();
			logger.error("下载出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 根据已经结算并且有效的图片ID，下载图片
	 * @param request
	 * @param res
	 * @param picIds 图片Id
	 * @param type 0图片下载，1图文下载
	 * @param //picData=[{"picId":"1","groupId":"1"}] picId为图片ID，groupId为图片稿件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("downByPicId")
	@SkipAuthCheck
	public Object downByPicId(HttpServletRequest request,HttpServletResponse response,String picIds,Integer type, Integer langType) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(picIds+"", "图片id");
			int [] detailIds = null; 
			if (StringUtils.isNotBlank(picIds)) {
				String [] uid2 = picIds.split(",");
				detailIds = new int[uid2.length];
				for (int i = 0; i < detailIds.length; i++) {
					detailIds[i] = Integer.parseInt(uid2[i]);
				}
			}
			CpUser loginUser=SessionUtils.getUser(request);
			CpUser user=cpUserMapper.selectByPrimaryKey(loginUser.getId());
			int siteId =SessionUtils.getSiteId(request);			
				downloadService.downPicsByOrderDetailId(detailIds, type, user, siteId, response, request, langType);		
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		}catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch(Exception e1){
			e1.printStackTrace();
			logger.error("下载出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 管理员根据订单ID，下载图片
	 * @param request
	 * @param res
	 * @param orderId 订单号
	 * @param langType 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("adminDownByOrder")
	public Object adminDownByOrder(HttpServletRequest request,HttpServletResponse response,Integer orderId,Integer type, Integer langType) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(orderId+"", "订单id");
			CpUser loginUser=SessionUtils.getUser(request);
			CpUser user=cpUserMapper.selectByPrimaryKey(loginUser.getId());
			int siteId =SessionUtils.getSiteId(request);
			//默认图文下载
			type=type==null?1:type;
			downloadService.adminDown(orderId, type, user, siteId, response, request, langType);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		}catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch(Exception e1){
			e1.printStackTrace();
			logger.error("下载出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 查询用户订单图片
	 * @param request
	 * @param orderId 订单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMyOrderPics")
	@SkipAuthCheck
	public Object getMyOrderPics(HttpServletRequest request,Integer orderId) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(orderId+"", "订单id");

			List<Map<String,Object>> list=clientPictureMapper.selectOrderPics(orderId);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
					map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		}catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch(Exception e1){
			e1.printStackTrace();
			logger.error("查询用户订单图片出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 查询我下载的图片
	 * @param request
	 * @param orderId 订单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMyDownPics")
	@SkipAuthCheck
	public Object getMyDownPics(HttpServletRequest request ,Integer flag) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user=SessionUtils.getUser(request);
			  Map<String, Object>map =new HashMap<>();
			  map.put("uid", user.getId());
			  map.put("flag", flag);
			  PageHelper.startPage(request);
			 List<Map<String,Object>> list=clientPictureMapper.selectMyDownPics(map);
			PageHelper.addPagesAndTotal(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		}catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch(Exception e1){
			e1.printStackTrace();
			logger.error("查询我下载的图片出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
