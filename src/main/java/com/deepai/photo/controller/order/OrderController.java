package com.deepai.photo.controller.order;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpOrderDetail;
import com.deepai.photo.bean.CpOrderForm;
import com.deepai.photo.bean.CpOrderFormExample;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpShopCar;
import com.deepai.photo.bean.CpSiteMessage;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpOrderFormExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pagehelper.PageInfo;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateTimeUtil;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpOrderFormMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.car.CarService;
import com.deepai.photo.service.order.OrderDetailService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;

/**
 * * @author huqiankai: * *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	private Logger log = Logger.getLogger(OrderController.class);
	@Autowired
	private OrderDetailService OrderService;
	@Autowired
	private CarService carService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpOrderFormMapper cpOrderFormMapper;
	@Autowired
	private CpPictureMapper cpPictureMapper;

	/**
	 * 显示全部订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/show")
	public Object show(HttpServletRequest request, HttpServletResponse response,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			List<Integer> RoseId = userRoleRightService.getRoseId(user.getId());
			PageHelper.startPage(request);
			List<CpOrderForm> list = OrderService.show(RoseId, user.getUserName(),langType);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getLastSubmitTime() != null) {
					String datetime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(list.get(i).getLastSubmitTime());
					list.get(i).setLasttime(datetime2);
				}
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
			log.error("不能显示订单列表，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 订单的复合查询
	 * 
	 * @param request
	 * @param response
	 * @param orderno  订单号
	 * @param userName 用户名
	 * @param orderStatus 订单状态
	 * @param createStartTime 创建订单的开始时间
	 * @param createEndTime 创建订单的结束时间
	 * @param payStartTime 付款订单的开始时间
	 * @param payEndTime 付款订单的结束时间
	 * @param staNum 开始张数
	 * @param endNum 结束张数
	 * @param staMoney 开始金额
	 * @param endMoney 结束金额
	 * @param langType 中英文
	 * @param page 请求页
	 * @param rows 每页显示的条数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/advancedSearch")
	public Object getOrderByQuery(HttpServletRequest request, HttpServletResponse response, 
			String orderno,String userName,String orderStatus,String createStartTime,String createEndTime,
			String payStartTime,String payEndTime,String staNum,String endNum,
			String staMoney,String endMoney, String langType,Integer page,Integer rows){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			
			if(StringUtil.isNotBlank(orderno)){
				param.put("orderno", orderno);
			}
			if(StringUtil.isNotBlank(userName)){
				param.put("userName", userName);
			}
			if(StringUtil.isNotBlank(orderStatus)){
				param.put("orderStatus", Integer.parseInt(orderStatus));
			}
			if(StringUtil.isNotBlank(staNum)){
				param.put("staNum", Integer.parseInt(staNum));
			}
			if(StringUtil.isNotBlank(endNum)){
				param.put("endNum", endNum);
			}
			if(StringUtil.isNotBlank(staMoney)){
				param.put("staMoney", staMoney);
			}
			if(StringUtil.isNotBlank(endMoney)){
				param.put("endMoney", endMoney);
			}
			if(StringUtil.isNotBlank(userName)){
				param.put("userName", userName);
			}
			if(StringUtil.isNotBlank(langType)){
				param.put("langType", langType);
			}
			if(StringUtil.isNotBlank(createStartTime)){
				Date createStartTimeTemp = DateTimeUtil.convertAsDateString(createStartTime);
				param.put("createStartTime", createStartTimeTemp);
			}
			if(StringUtil.isNotBlank(createEndTime)){
				Date createEndTimeTemp = DateTimeUtil.convertAsDateString(createEndTime);
				param.put("createEndTime", createEndTimeTemp);
			}
			if(StringUtil.isNotBlank(payStartTime)){
				Date payStartTimeTemp = DateTimeUtil.convertAsDateString(payStartTime);
				param.put("payStartTime", payStartTimeTemp);
			}
			if(StringUtil.isNotBlank(payEndTime)){
				Date payEndTimeTemp = DateTimeUtil.convertAsDateString(payEndTime);
				param.put("payEndTime", payEndTimeTemp);
			}
			
			page=page==null?1:page;
			rows=rows==null?10:rows;
			PageHelper.startPage(page, rows);
			List<CpOrderForm> orderList = OrderService.getOrderByQuery(param);
			for (int i = 0; i < orderList.size(); i++) {
				if (orderList.get(i).getLastSubmitTime() != null) {
					String datetime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(orderList.get(i).getLastSubmitTime());
					orderList.get(i).setLasttime(datetime2);
				}
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderList.get(i).getCreateTime());
				orderList.get(i).setDatetime(datetime);
			}
			PageInfo pageInfo = new PageInfo(orderList);
			int c = (int)pageInfo.getTotal();
			int p = pageInfo.getPages();
			result.setPage(p);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(orderList);
			result.setOther(c);
		} catch(Exception e){
			e.printStackTrace();
			log.error("复合查询订单信息出错，"+e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 订单查询
	 * 
	 * @param request
	 * @param response
	 * @param orderno
	 * @param username
	 * @param orderStatus
	 * @param Timefrom
	 * @param Timeto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/search")
	@LogInfo(content = "订单查询", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object search(HttpServletRequest request, HttpServletResponse response, String orderno, Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpOrderForm> list = OrderService.search(orderno, langType);
			PageHelper.addPages(result, list);
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
				if (list.get(i).getLastSubmitTime() != null) {
					String datetime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(list.get(i).getLastSubmitTime());
					list.get(i).setLasttime(datetime2);
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("订单查询失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 后台订单详情查看
	 * 
	 * @param request
	 * @param response
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail")
	@LogInfo(content = "订单详情查看", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object detail(HttpServletRequest request, HttpServletResponse response, String orderno) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpOrderForm cpOrderForm = OrderService.detail(orderno);
			String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cpOrderForm.getCreateTime());
			cpOrderForm.setDatetime(datetime);
			String datetime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cpOrderForm.getLastSubmitTime());
			cpOrderForm.setLasttime(datetime2);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpOrderForm);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("订单详情查看失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 购物车内添加订单
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	@LogInfo(content = "添加订单", opeType = 0, logTypeCode = CommonConstant.Order)
	public Object add(HttpServletRequest request, HttpServletResponse response, String[] id, Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			String car = redisClientTemplate.get("car:" + user.getId());
			Map<String, CpShopCar> hashMap = new HashMap<String, CpShopCar>();
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Random random = new Random();
			String time = dateFormat.format(date).replace("-", "").replace(":", "").replace(" ", "");
			for (int i = 0; i < 6; i++) {
				int nextInt = random.nextInt(10);
				time = time + nextInt + "";
			}
			Gson gson = new GsonBuilder().create();
			Type type = new TypeToken<Map<String, CpShopCar>>() {
			}.getType();
			hashMap = gson.fromJson(car, type);
			double a = 0;
			BigDecimal b = new BigDecimal(Double.valueOf(a));
			for (String i : id) {
				CpShopCar cpShopCar = hashMap.get(i);
				cpShopCar.setOrderId(time);
				b = b.add(cpShopCar.getPrice());
				carService.add(cpShopCar);
				CpOrderDetail cpOrderDetail = new CpOrderDetail();
				cpOrderDetail.setOrderNo(time);
				cpOrderDetail.setPrice(cpShopCar.getPrice());
				cpOrderDetail.setPicId(cpShopCar.getPicId());
				cpOrderDetail.setUserId(user.getId());
				cpOrderDetail.setUserName(user.getUserName());
				OrderService.insertDetail(cpOrderDetail);
				Map<String, String> map=cpPictureMapper.FindOrderPicByPid(Integer.parseInt(i));
				cpOrderDetail.setFilename(map.get("fileName"));
				cpOrderDetail.setGroupId(cpOrderDetail.getGroupId());
				hashMap.remove(i);
			}
			CpOrderForm orderForm = new CpOrderForm();
			String parse = JSONObject.fromObject(hashMap).toString();
			redisClientTemplate.set("car:" + user.getId(), parse);
			orderForm.setOrderNo(time);
			orderForm.setUserId(user.getId());
			orderForm.setUserName(user.getUserName());
			orderForm.setOrderStatus(0);
			orderForm.setTotalPrice(b);
			orderForm.setPicCount(id.length);
			orderForm.setLangType(langType);
			OrderService.add(orderForm);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能添加网站信息， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 前台页面查看订单图片
	 * 
	 * @param request
	 * @param response
	 * @param oid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/seepicture")
	public Object seepicture(HttpServletRequest request, HttpServletResponse response, String oid) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpOrderDetail> list = OrderService.findByOd(oid);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能查看订单图片，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 订单删除
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	@LogInfo(content = "订单删除", opeType = 1, logTypeCode = CommonConstant.Order)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String id,String orderNo) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (StringUtils.isNotBlank(id)) {
				carService.delete(id);
//				OrderService.delete(id);
				//修改订单状态，而非真删除订单记录,如果有需要真删除，需修改
				//还有一个问题，订户是没有权限在客户端删除订单的，但是前端有这个选项。。
				OrderService.deleteOrder(Integer.parseInt(id));
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
			}else{
				result.setCode(CommonConstant.PARAMERROR);
				result.setMsg("参数不能为空");
			}
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("订单删除失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	// TODO 客户端订单
	/**
	 * 获取我的订单
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyOrders")
	@SkipAuthCheck
	public Object getMyOrders(HttpServletRequest request, HttpServletResponse response, Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			PageHelper.startPage(request);
			CpOrderFormExample e = new CpOrderFormExample();
			Criteria criteria = e.createCriteria();
			criteria.addLangTypeEqualTo(langType);
			criteria.andUserIdEqualTo(user.getId());
			criteria.andOrderStatusLessThan(3);
			e.setOrderByClause("CREATE_TIME desc");
			List<CpOrderForm> list = cpOrderFormMapper.selectByExample(e);
			PageHelper.addPagesAndTotal(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} catch (Exception e1) {
			log.error("获取我的订单出错，" , e1);
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
