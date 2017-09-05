package com.deepai.photo.controller.car;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpFavoriteFolderPic;
import com.deepai.photo.bean.CpLanmuGroupPic;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpShopCar;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.MyStringUtil;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.car.CarService;
import com.deepai.photo.service.picture.PictureService;
import com.deepai.photo.service.pictureprice.PicturePriceService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import net.sf.json.JSONObject;

/**
 * * @author huqiankai: * *
 */
@Controller
@RequestMapping("/car")
public class CarController {
	private Logger log = Logger.getLogger(CarController.class);
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private CarService carService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private CpUserMapper cpUserMapper;
	
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;
	/**
	 * 加入到购物车
	 * 
	 * @param request
	 * @param response
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@SkipAuthCheck
	@RequestMapping("/add")
	@LogInfo(content = "加入到购物车", opeType = 0, logTypeCode = CommonConstant.Car)
	public Object add(HttpServletRequest request, HttpServletResponse response,String pictureId,Integer langType) {

		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			Map<String, CpShopCar> hashMap = new HashMap<String, CpShopCar>();
			String car = redisClientTemplate.get("car:" + user.getId() + "_" + langType);
			if (StringUtils.isBlank(car)) {
				String parse = JSONObject.fromObject(hashMap).toString();
				redisClientTemplate.set("car:" + user.getId() + "_" + langType, parse);
				car = redisClientTemplate.get("car:" + user.getId() + "_" + langType);
			}
			Gson gson = new GsonBuilder().create();
			Type type = new TypeToken<Map<String, CpShopCar>>(){}.getType();
			hashMap=gson.fromJson(car, type);
			String[] split = pictureId.split(",");
			List<CpFavoriteFolderPic> notSales = new ArrayList<CpFavoriteFolderPic>();
			List<CpFavoriteFolderPic> contacts = new ArrayList<CpFavoriteFolderPic>();
			Map<String, Object> addToCarFail = new HashMap<String, Object>();
			CpUser loginUser= cpUserMapper.selectByPrimaryKey(user.getId());
			for (String i : split) {   
				if (!hashMap.containsKey(i+"")) {
					CpShopCar cpShopCar = new CpShopCar();
					CpFavoriteFolderPic cpFavoriteFolderPic = cpPicGroupMapper.findGroupPicByPicId(Integer.parseInt(i));
					
					if (cpFavoriteFolderPic.getPricetype() == 2) {//特价图（不可出售图）不能加入购物车
						notSales.add(cpFavoriteFolderPic);
						continue;
					}else if(cpFavoriteFolderPic.getPricetype() == 1 && loginUser.getDownloadType()==1){//张数用户不能将定价图加入到购物车
						contacts.add(cpFavoriteFolderPic);
						continue;
					}
					int a = (int) System.currentTimeMillis();
					cpShopCar.setId(a);
					if (cpFavoriteFolderPic!=null) {
						if (StringUtils.isNotBlank(cpFavoriteFolderPic.getAllPath())) {
							String path=cpFavoriteFolderPic.getAllPath().replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)),
									ipAdd);
							cpFavoriteFolderPic.setAllPath(path);
							cpShopCar.setAllPath(cpFavoriteFolderPic.getAllPath());
						}
						cpShopCar.setCreateUserid(user.getId());
						cpShopCar.setCreateUsername(user.getUserName());
						if (cpFavoriteFolderPic.getPictureHeight()!=null) {
							cpShopCar.setHeight(cpFavoriteFolderPic.getPictureHeight()+"");
						}
						if (cpFavoriteFolderPic.getPicturePrice()!=null) {
							cpShopCar.setPrice(new BigDecimal(Double.valueOf(cpFavoriteFolderPic.getPicturePrice())));
						}
						if (cpFavoriteFolderPic.getPictureId()!=null) {
							cpShopCar.setPicId(cpFavoriteFolderPic.getPictureId());
						}
						if (cpFavoriteFolderPic.getPictureWidth()!=null) {
							cpShopCar.setWidth(cpFavoriteFolderPic.getPictureWidth());
						}
						if (cpFavoriteFolderPic.getPictureFilename()!=null) {
							cpShopCar.setPicName(cpFavoriteFolderPic.getPictureFilename());
						}
						if (cpFavoriteFolderPic.getGroupTitle()!=null) {
							cpShopCar.setPicTitle(cpFavoriteFolderPic.getGroupTitle());
						}
						if (cpFavoriteFolderPic.getPublicTime()!=null) {
							cpShopCar.setPublicTime(cpFavoriteFolderPic.getPublicTime());
						}
						if (cpFavoriteFolderPic.getPictureLength()!=null) {
							cpShopCar.setPictureLengh(cpFavoriteFolderPic.getPictureLength());
						}
						if (cpFavoriteFolderPic.getCreater()!=null) {
							cpShopCar.setPictureCreter(cpFavoriteFolderPic.getCreater());
						}
						hashMap.put(cpFavoriteFolderPic.getPictureId() + "", cpShopCar);
					}
				}
				
			}
			String parse = JSONObject.fromObject(hashMap).toString();
			redisClientTemplate.set("car:" + user.getId() + "_" + langType, parse);
			addToCarFail.put("notSales", notSales);
			addToCarFail.put("contacts", contacts);
			result.setData(addToCarFail);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能加入到购物车， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 查看购物车
	 * 
	 * @param request
	 * @param response
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@SkipAuthCheck
	@RequestMapping("/show")
	public Object show(HttpServletRequest request, HttpServletResponse response,Integer langType) {

		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			CpUser user = SessionUtils.getUser(request);
			String car = redisClientTemplate.get("car:" + user.getId() + "_" + langType);
			Map<String, CpShopCar> hashMap = new HashMap<String, CpShopCar>();
			Gson gson = new GsonBuilder().create();
			Type type = new TypeToken<Map<String, CpShopCar>>(){}.getType();
			hashMap=gson.fromJson(car, type);
			List<CpShopCar> list = new ArrayList<CpShopCar>();
			if (hashMap!=null) {
				Set keySet = hashMap.keySet();
				for (Object object : keySet) {
					list.add(hashMap.get(object));
				}
			}
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			if (MyStringUtil.isEmpty(rows) || !MyStringUtil.isNumeric(rows)){
				rows ="10";
			}
			
			int pages=list.size()%Integer.parseInt(rows)==0 ? 
			list.size()/Integer.parseInt(rows):list.size()/Integer.parseInt(rows)+1;
			List<CpShopCar> resultList = null;
			if (list.size()>0) {
				int begin = (Integer.parseInt(page) -1)*10;
				int end = (pages == Integer.parseInt(page))? list.size():Integer.parseInt(page)*10;
				resultList = list.subList(begin, end);
			}else{
				resultList = list;
			}
			result.setPage(pages);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(resultList);
			result.setOther(list.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("不能查看购物车， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除购物车图片
	 * 
	 * @param request
	 * @param response
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@SkipAuthCheck
	@RequestMapping("/delete")
	@LogInfo(content = "删除购物车图片", opeType = 1, logTypeCode = CommonConstant.Car)
	public Object delete(HttpServletRequest request, HttpServletResponse response, String pictureId,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			String car = redisClientTemplate.get("car:" + user.getId() + "_" + langType);
			Map<String, CpShopCar> hashMap = new HashMap<String, CpShopCar>();
			Gson gson = new GsonBuilder().create();
			Type type = new TypeToken<Map<String, CpShopCar>>(){}.getType();
			hashMap=gson.fromJson(car, type);
			String[] split = pictureId.split(",");
			for (String i : split) {
				hashMap.remove(i);
			}
			String parse = JSONObject.fromObject(hashMap).toString();
			redisClientTemplate.set("car:" + user.getId() + "_" + langType, parse);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除购物车图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}