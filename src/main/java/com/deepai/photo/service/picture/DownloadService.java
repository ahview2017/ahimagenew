package com.deepai.photo.service.picture;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpDownloadLevel;
import com.deepai.photo.bean.CpOrderDetail;
import com.deepai.photo.bean.CpOrderForm;
import com.deepai.photo.bean.CpOrderFormExample;
import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicAllpathExample;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupCategory;
import com.deepai.photo.bean.CpPicGroupExample;
import com.deepai.photo.bean.CpPicGroupExample.Criteria;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureDownloadrecord;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.util.MathUtil;
import com.deepai.photo.common.util.NumberUtils;
import com.deepai.photo.common.util.ZipCompress;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImageAnalyseUtil;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.controller.util.UserUtils;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpOrderDetailMapper;
import com.deepai.photo.mapper.CpOrderFormMapper;
import com.deepai.photo.mapper.CpPicAllpathMapper;
import com.deepai.photo.mapper.CpPicGroupCategoryMapper;
import com.deepai.photo.mapper.CpPictureDownloadrecordMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.admin.SysConfigService;


/**
 * @author guoyanhong
 * 下载结算
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class DownloadService {
	private Logger logger=Logger.getLogger(DownloadService.class) ;
	@Autowired
	private ClientPictureMapper clientPictureMapper;
	@Autowired
	private CpPicAllpathMapper allpathMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private CpOrderFormMapper orderFormMapper;
	@Autowired
	private CpOrderDetailMapper cpOrderDetailMapper;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private CpPicGroupCategoryMapper cpPicGroupCategoryMapper;

	/**
	 * 充值方式下载
	 * @param user 下载用户
	 * @param gorupList 下载图片信息
	 * @param totolDealprice 下载总价格
	 * @param count 下载图片张数
	 * @param details 订单详情
	 * @param downType 下载方式
	 * @param langType 
	 * @param fromThree 是否是合同与充值 方式
	 * @return
	 * @throws Exception
	 */
	public CpOrderForm balanceDown(CpUser user,List<Map<String,Object>> gorupList,BigDecimal totolDealprice,int count,List<CpOrderDetail> details,int downType,Integer langType,boolean fromThree)throws Exception{
		//余额
		Integer balancePrice=user.getBalancePerprice();//用户协议价格
		Integer balanceBasePrice=user.getBalanceBasePerprice();//用户分成基价
		Integer balanceSale=user.getBalanceSale();//用户特殊图打折系数
		balanceSale=balanceSale==null?100:balanceSale;
		Integer balanceRevenue=user.getBalanceRevenue();//用户下载税收系数
		balanceRevenue=balanceRevenue==null?100:balanceRevenue;
		Integer balanceLimitType=user.getBalanceLimitType();//余额下载限制类型：0每天，1每月，2每年，3总共
		Integer balanceLimitNum=user.getBalanceLimitNum();//余额下载限制内限制数量:如每天限制下载10张
		balanceLimitNum=balanceLimitNum==null?0:balanceLimitNum;
		Integer balanceLimitDlNum=user.getBalanceLimitDlNum();//余额下载限制内已下载数量
		balanceLimitDlNum=balanceLimitDlNum==null?0:balanceLimitDlNum;
		
		//合同与充值方式
		Integer threeLimitDlNum=user.getThreeLimitDlNum();//合同与充值方式  下载限制内已下载数量
		threeLimitDlNum=threeLimitDlNum==null?0:threeLimitDlNum;
		
		if(balancePrice==null){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NOBALANCETYPEMSG);
		}
		if(balanceLimitType==null){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NOBALANCELIMITTYPEMSG);
		}
		if(!fromThree && balanceLimitDlNum+count>balanceLimitNum ){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NODOWNLOADNUM);
		}
		int priceType=0;
		int price=0;//图片价格
		int picId=0;
		int gId=0;
		int divide=50;//分成系数	
		BigDecimal dividePrice= null;
		for (int i = 0; i < gorupList.size(); i++) {
			priceType=Integer.valueOf(gorupList.get(i).get("PRICE_TYPE").toString());//0不定价，1定价，2不出售
			//图片定价， 不定价关系到图片扣费问题
			if(priceType==2){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,String.format("图片【%s】不支持出售", gorupList.get(i).get("FILENAME")));
			}
			//用户下载图片时，是按照订户的协议价格来扣费的，默认
			price = balancePrice;
			//如果该稿件有定价，那么每张图片按照定价方式扣费
			if(priceType == 1){
				price=Integer.valueOf( gorupList.get(i).get("PRICE").toString());//图片价格
			}
//			price=Integer.valueOf( gorupList.get(i).get("PRICE").toString());//图片价格
			divide=Integer.valueOf( gorupList.get(i).get("USER_DIVIDE").toString());
			BigDecimal dealprice=null;//支付价格
			if(priceType==1){//特殊图
				//支付价格=特殊图价格x打折系数
				dealprice=new BigDecimal(price*balanceSale/100);
				//摄影师分成=支付价格*下载用户税收系数*摄影师分成系数
				dividePrice=MathUtil.mul(dealprice,new BigDecimal(balanceRevenue*divide/1000)) ;
			}
			if(priceType==0){//普通图
				//支付价格=图片价格
				dealprice=new BigDecimal(price);
				//摄影师分成=下载用户分成基价*下载用户税收系数*摄影师分成系数
				dividePrice=new BigDecimal(balanceBasePrice*balanceRevenue*divide/1000);
			}
			picId=Integer.valueOf(gorupList.get(i).get("PID").toString());//图片ID
			gId=Integer.valueOf(gorupList.get(i).get("GID").toString());//稿件ID
			CpOrderDetail od=new CpOrderDetail();
			od.setFilename(gorupList.get(i).get("FILENAME").toString());
			od.setPicId(picId);
			od.setGroupId(gId);
			od.setPrice(new BigDecimal(price));//图片价格
			od.setDealPrice(dealprice);//支付价格
			od.setUserId(user.getId());
			od.setUserName(user.getUserName());
			od.setCreatetime(new Date());
			//摄影师
			od.setPhotoUserId(Integer.valueOf(gorupList.get(i).get("AUTHOR_ID").toString()));
			od.setPhotoUserName(gorupList.get(i).get("AUTHOR_NAME").toString());
			od.setPhotoUserDivide(divide);
			od.setDividePrice(dividePrice);
			od.setDivideStatus(0);//摄影师结算状态：0未结算，1已计算
			totolDealprice=MathUtil.add(totolDealprice, dealprice);
			details.add(od);
		}
		BigDecimal account=user.getAccount();
		account=account==null?new BigDecimal(0.00):account;
		if(totolDealprice.compareTo(account)>0){//如果图片总价大于余额，不可下载
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,String.format("账户余额剩余：%s，不可购买以上图片", account));
		}
		
		Integer balanceLimitDlNum2 = user.getBalanceLimitDlNum();
		
		if (fromThree) {
			user.setThreeLimitDlNum(threeLimitDlNum+count);
		}else {
			if (balanceLimitDlNum2!=null) {
				user.setBalanceLimitDlNum(user.getBalanceLimitDlNum()+count);
			}else{
				user.setBalanceLimitDlNum(count);
			}
		}
	
//		user.setBalanceLimitDlNum(user.getBalanceLimitDlNum()+count);
		user.setAccount(account.subtract(totolDealprice));
		Integer allDown =user.getAllDownloadNum();
		if (allDown!=null) {
			user.setAllDownloadNum(allDown+count);
		}else{
			user.setAllDownloadNum(count);
		}
		CpOrderForm order= createOrder(user, totolDealprice, downType, count, details, langType);
		return order;
	}

	/**
	 * 合同方式下载
	 * @param user 下载用户
	 * @param gorupList 下载图片信息
	 * @param count 下载图片张数
	 * @param details 订单详情
	 * @param downType 下载方式 
	 * @param langType
	 * @param fromThree 是否是第三种下载方式
	 * @return
	 * @throws Exception
	 */
	public CpOrderForm contractDown(CpUser user,List<Map<String,Object>> gorupList,int count,List<CpOrderDetail> details,int downType,Integer langType,boolean fromThree)throws Exception{
		//合同
		Integer contractBasePrice = user.getContractBasePerprice();//合同-分成基价
		Date contractStartTime=user.getContractStartTime();//开始时间
		Date contractEndTime=user.getContractEndTime();//结束时间
		Integer contractLimitType=user.getContractLimitType();//下载限制类型：0每天，1每月，2每年，3总共
		Integer contractLimitNum=user.getContractLimitNum();//下载限制内限制数量:如每天限制下载10张
		Integer contractLimitDlNum=user.getContractLimitDlNum();//下载限制内已下载数量
		contractLimitDlNum=contractLimitDlNum==null?0:contractLimitDlNum;
		Integer contractBuyNum =user.getContractBuyNum();//当前合同期购买数量；-99不限制
		Integer contractNum=user.getContractNum();//当前合同期内已下载数量
		contractNum=contractNum==null?0:contractNum;
		
		//合同与充值方式
		Integer threeLimitDlNum=user.getThreeLimitDlNum();//合同与充值方式  下载限制内已下载数量
		threeLimitDlNum=threeLimitDlNum==null?0:threeLimitDlNum;
		
		if(contractBasePrice==null||contractStartTime==null||contractEndTime==null||contractLimitType==null||contractLimitNum==null||contractLimitDlNum==null
				||contractBuyNum==null){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADINFO);
		}
		Date now=new Date();
		if(now.before(contractStartTime)||now.after(contractEndTime)){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NODOWNLOADDATE);
		}
		if(!fromThree && (contractLimitDlNum+count>contractLimitNum)||(contractNum+count>contractBuyNum)){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NODOWNLOADNUM);
		}
		int priceType=0;
		int price=0;//图片价格
		int picId=0;
		int gId=0;
		int divide=50;//分成系数	
		BigDecimal dividePrice= null;
		for (int i = 0; i < gorupList.size(); i++) {
			priceType=Integer.valueOf(gorupList.get(i).get("PRICE_TYPE").toString());//0不定价，1定价，2不出售
			if(priceType==2){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,String.format("图片【%s】不支持出售", gorupList.get(i).get("FILENAME")));
			}else if(priceType == 1){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,String.format("定价图片【%s】不支持张数用户，请联系销售人员", gorupList.get(i).get("FILENAME")));
			}
			price=Integer.valueOf(gorupList.get(i).get("PRICE").toString());//图片价格
			divide=Integer.valueOf(gorupList.get(i).get("USER_DIVIDE").toString());
			picId=Integer.valueOf(gorupList.get(i).get("PID").toString());//图片ID
			gId=Integer.valueOf(gorupList.get(i).get("GID").toString());//图片ID
			//摄影师分成=用户合同分成基价
			dividePrice=new BigDecimal(contractBasePrice*divide/100) ;
			CpOrderDetail od=new CpOrderDetail();
			od.setFilename(gorupList.get(i).get("FILENAME").toString());
			od.setPicId(picId);
			od.setGroupId(gId);
			od.setPrice(new BigDecimal(price));//图片价格
			od.setUserId(user.getId());
			od.setUserName(user.getUserName());
			od.setCreatetime(new Date());
			//摄影师
			od.setPhotoUserId(Integer.valueOf(gorupList.get(i).get("AUTHOR_ID").toString()));
			od.setPhotoUserName(gorupList.get(i).get("AUTHOR_NAME").toString());
			od.setPhotoUserDivide(divide);
			od.setDividePrice(dividePrice);
			od.setDivideStatus(0);//摄影师结算状态：0未结算，1已计算
			details.add(od);//DOTO
			contractNum++;
		}
		Integer contractNum2 = user.getContractNum();
		if (fromThree) {
			user.setThreeLimitDlNum(threeLimitDlNum+count);
		}else{
			if (user.getContractLimitDlNum() != null) {
				user.setContractLimitDlNum(user.getContractLimitDlNum()+count);
			}else{
				user.setContractLimitDlNum(count);
			}
		}
		if (contractNum2!=null) {
			user.setContractNum(user.getContractNum()+count);
		}else{
			user.setContractNum(count);
		}
		
		Integer allDownloadNum = user.getAllDownloadNum();
		if (allDownloadNum!=null) {
			user.setAllDownloadNum(user.getAllDownloadNum()+count);
		}else{
			user.setAllDownloadNum(count);
		}
		CpOrderForm order= createOrder(user, new BigDecimal(0.00), downType, count, details, langType);
		return order;		
	}

	/**
	 * 第三种方式下载
	 * @param user 下载用户
	 * @param gorupList 下载图片信息
	 * @param count 下载图片张数
	 * @param details 订单详情
	 */
	public List<CpOrderForm> threeDown(CpUser user,List<Map<String,Object>> gorupList,int count,List<CpOrderDetail> details,Integer langType)throws Exception{
		Integer contractBuyNum =user.getContractBuyNum();//当前合同期购买数量；-99不限制
		//第三种下载方式
		Integer threeLimitType=user.getThreeLimitType();//下载限制类型：0每天，1每月，2每年，3总共
		Integer threeLimitNum=user.getThreeLimitNum();//下载限制内限制数量:如每天限制下载10张
		threeLimitNum=threeLimitNum==null?0:threeLimitNum;
		Integer threetLimitDlNum=user.getThreeLimitDlNum();//下载限制内已下载数量
		threetLimitDlNum=threetLimitDlNum==null?0:threetLimitDlNum;
		
		if(threeLimitType==null||threeLimitNum==null||threetLimitDlNum==null){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADINFO);
		}
		if(threetLimitDlNum+count>threeLimitNum){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NODOWNLOADNUM);
		}		
		int priceType = 0;
		List<Map<String,Object>> contractList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> balanceList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : gorupList) {
			priceType=Integer.valueOf(map.get("PRICE_TYPE").toString());//0不定价，1定价，2不出售
			if (priceType == 1) {
				balanceList.add(map);
			}else{
				if (threetLimitDlNum+contractList.size()<threeLimitNum) {
					contractList.add(map);
				}else{
					balanceList.add(map);
				}
			}
		}
		int contactNums = contractList.size();
		
/*		
		
		List<CpOrderForm> orders=new ArrayList<CpOrderForm>();//订单，若为第三种方式下载，可能生成两笔订单
		if(contractBuyNum!=-99&&threetLimitDlNum+contactNums>contractBuyNum){//如果下载数量超过合同内购买数量，则分两单，优先使用合同
			int contractCount=contractBuyNum-threetLimitDlNum;//合同剩余可下载张数
			int balanceCount=contactNums-contractCount;//余额下载张数
			try{
				BigDecimal totolDealprice=new BigDecimal(0.00);
				CpOrderForm o1=balanceDown(user, balanceList, totolDealprice, balanceCount, details,0, langType);
				orders.add(o1);
			} catch (Exception e) {
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("当前合同和余额剩余量不够下载%s张图片",count));
			}
			CpOrderForm o2=contractDown(user, contractList, contractCount, details, 1,langType);
			orders.add(o2);
		}else{//优先使用合同下载
			CpOrderForm o2=contractDown(user, gorupList, count, details, 1, langType);
			orders.add(o2);
		}
*/
		List<CpOrderForm> orders=new ArrayList<CpOrderForm>();//订单，若为第三种方式下载，可能生成两笔订单
		try{
			if (balanceList.size() > 0) {
				BigDecimal totolDealprice=new BigDecimal(0.00);
				CpOrderForm o1=balanceDown(user, balanceList, totolDealprice, balanceList.size(), details,0, langType,true);
				orders.add(o1);
				logger.info("balanceList-=======>充值 ------------------->"+balanceList);
			}
		} catch (Exception e) {
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("当前合同和余额剩余量不够下载%s张图片",count));
		}
		if (contractList.size() > 0) {
			details.clear();
			CpOrderForm o2=contractDown(user, contractList, contractList.size(), details, 1, langType,true);
			orders.add(o2);
			logger.info("contractList-=======>合同 ------------------->"+contractList);
		}
		
		
		return orders;
	}

	/**
	 * 生成订单
	 * @param user 下载用户
	 * @param totolDealprice 总支付
	 * @param downType 下载方式
	 * @param count 下载图片张数
	 * @param details 下载详情
	 * @throws Exception 
	 */
	public CpOrderForm createOrder(CpUser user,BigDecimal totolDealprice,int downType,int count,List<CpOrderDetail> details,Integer langType) throws Exception{
		//生成订单，返回订单ID和订单号
		CpOrderForm order=new CpOrderForm();
		Integer orderNum=otherMapper.selectOrderNum("seq_order");
		String orderNum_= String.format("%s%06d", DateUtils.getNowShortDate(),orderNum);
		order.setOrderNo(orderNum_);
		order.setUserId(user.getId());
		order.setUserName(user.getUserName());
		order.setUserType(0);//TODO(尚未做判断) 0普通订户 1vip 2内部订户
		order.setOrderStatus(0);//0待付款，1取消付款，2已付款
		order.setTotalPrice(totolDealprice);
		order.setCreateTime(new Date());
		order.setPicCount(count);
		order.setPayType(downType);
		order.setLangType(langType);
		orderFormMapper.insertSelective(order);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("details", details);
		param.put("orderId", order.getId());
		param.put("orderNum", order.getOrderNo());
		logger.info(param);
		logger.info("details"+details.size()+"==========================>"+details);
		clientPictureMapper.insertOrderDetails(param);
	    cpUserMapper.updateByPrimaryKeySelective(user);
		return order;
	}
	/**
	 * 下载并生成订单
	 * @param user 下载用户
	 * @param picIds 图片ID
	 * @return
	 * @throws Exception
	 */
	public List<CpOrderForm> downAndOrder(CpUser user,List<Map<String,Object>> hasPayList,String picIds,Integer langType) throws Exception{
		//用户下载方式(也是用户结算方式)：0充值(余额结算)，1合同(张数结算)，2充值与合同(余额和张数结算)
		Integer downType=user.getDownloadType();
		if(downType==null){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADTYPEMSG);
		}
		String picIdArr[]=picIds.split(",");
		int count =picIdArr.length;//下载图片张数

		Byte isDown=user.getIsDownload();//是否允许下载
		if(isDown==null||isDown==CommonConstant.BYTE0){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADMSG);
		}

		BigDecimal totolDealprice=new BigDecimal(0.00);//总支付价格
		List<CpOrderDetail> details=new ArrayList<CpOrderDetail>();//订单详情
		//获取图片list
		List<Map<String,Object>> gorupList=clientPictureMapper.selectDownPics(picIdArr,user.getId());     
		List<Map<String,Object>> hasNotPays = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : gorupList) {
			if ((Long)map.get("payed")==1) {
				hasPayList.add(map);//72小时之内已经下过订单的图片
			}else {
				hasNotPays.add(map);
			}
		}
		
		List<CpOrderForm> orders=new ArrayList<CpOrderForm>();//订单，若为第三种方式下载，可能生成两笔订单
		if (hasNotPays.size()<1) {
			return orders;
		}
		switch (downType) {
		case 0:
			//余额下载
			CpOrderForm o1=balanceDown(user, hasNotPays, totolDealprice, hasNotPays.size(), details,0, langType,false);
			orders.add(o1);
			break;
		case 1:
			//合同下载
			CpOrderForm o2=contractDown(user, hasNotPays, hasNotPays.size(), details,1, langType,false);
			orders.add(o2);
			break;
		case 2:
			orders=threeDown(user, hasNotPays, hasNotPays.size(), details, langType);
			break;
		}
		
		return orders;
	}

	
	/**
	 * 下载图片，并记录下载日志,并扣余额或合同
	 * @param picIds 要下载的图片ID
	 * @param type 0图片下载，1图文下载
	 * @param user 下载用户
	 * @param siteId 站点ID
	 * @param response
	 * @param request 
	 * @param langType 
	 * @throws Exception
	 */
	public void downAndPay(Integer orderId,int type,CpUser user,int siteId,HttpServletResponse response, HttpServletRequest request, Integer langType) throws Exception{
		CpOrderFormExample eo=new CpOrderFormExample();
		eo.createCriteria().andIdEqualTo(orderId).andUserIdEqualTo(user.getId());
		List<CpOrderForm> list=orderFormMapper.selectByExample(eo);
		if(list==null || list.size()==0){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,"订单不存在");
		}
		CpOrderForm oldOrder=list.get(0);
		CpUser userDownInfo=new CpUser();
		boolean flag=false;
		
		List<Map<String, Object>> downPicInfos= cpOrderDetailMapper.getDownListByOrderId(orderId, user.getId());
		boolean hasPayed = false;
		boolean notPay = false;
		for (int i = 0; i < downPicInfos.size(); i++) {
			Integer oid = (Integer) downPicInfos.get(i).get("oid");
			if (oid ==null || oid <1) {//存在未结算的图片
				notPay = true;
			}
			if (oid !=null && oid >0) {//存在已结算的图片
				hasPayed = true;
			}
		}
		if (notPay && hasPayed) {
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,"该订单超包含部分已结算的图片，本次结算失败");
		}
		if(oldOrder.getPayTime()==null && !hasPayed){//未支付
			userDownInfo.setId(user.getId());
			int count=oldOrder.getPicCount();
			switch (oldOrder.getPayType()) {
			case 0:
				//余额
				Integer balanceLimitDlNum=user.getBalanceLimitDlNum();//余额下载限制内已下载数量
				balanceLimitDlNum=balanceLimitDlNum==null?0:balanceLimitDlNum;
				Integer balanceAllNum=user.getBalanceAllNum();//余额内已下载数量				
				balanceAllNum=balanceAllNum==null?0:balanceAllNum;

				balanceLimitDlNum=balanceLimitDlNum+count;
				balanceAllNum=balanceAllNum+count;
//				userDownInfo.setBalanceLimitDlNum(balanceLimitDlNum);
				userDownInfo.setBalanceAllNum(balanceAllNum);
				break;
			case 1:
				//合同
				Integer contractLimitDlNum=user.getContractLimitDlNum();//下载限制内已下载数量
				contractLimitDlNum=contractLimitDlNum==null?0:contractLimitDlNum;
				Integer contractNum=user.getContractNum();//当前合同期内已下载数量
				contractNum=contractNum==null?0:contractNum;
				Integer contractAllNum=user.getContractAllNum();//合同期内已下载数量				
				contractAllNum=contractAllNum==null?0:contractAllNum;

				contractLimitDlNum=contractLimitDlNum+count;
				contractNum=contractNum+count;
				contractAllNum=contractAllNum+count;
//				userDownInfo.setContractNum(contractNum);
//				userDownInfo.setContractLimitDlNum(contractLimitDlNum);
				userDownInfo.setContractAllNum(contractAllNum);
				break;
			/*
			case 2://余额和合同
				//第三种下载方式
				Integer threetLimitDlNum=user.getThreeLimitDlNum();//下载限制内已下载数量
				threetLimitDlNum=threetLimitDlNum==null?0:threetLimitDlNum;

				threetLimitDlNum=threetLimitDlNum+count;
				userDownInfo.setThreeLimitDlNum(threetLimitDlNum);				
				break;
			*/
			}
			flag=true;
		}else if(oldOrder.getPayTime()==null && !notPay){
			CpOrderForm record = new CpOrderForm();
			record.setId(orderId);
			record.setPayTime(new Date((Long) downPicInfos.get(0).get("payTime")));
			record.setOrderStatus(2);
			orderFormMapper.updateByPrimaryKeySelective(record);
		}else if(new Date().getTime()-oldOrder.getPayTime().getTime()>CommonConstant.HOURS72){//已支付
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,"订单超过72小时，请重新购买。");
		}
		Integer allDown =user.getAllDownloadNum();
//		if (allDown!=null) {
//			user.setAllDownloadNum(allDown+count);
//		}else{
//			user.setAllDownloadNum(count);
//		}

		//获取用户下载级别
		CpDownloadLevel downLevel =clientPictureMapper.selectUserDownLevel(user.getId());
		if (downLevel!=null) {//用户存在下载级别
			int isWaterMark = downLevel.getIsWatermark();//是否水印：1否，0是
			int pxH = downLevel.getLimitPxH();//限制高
			String wMPath=null;//水印图片地址
			if(isWaterMark==0){//有水印
				Integer wmId=downLevel.getWatermarkPicId();//水印图片ID
				/*wMPath="D:/trsphoto/watermark/20170313124850_1489380530096_p.png";
//				wMPath=clientPictureMapper.findDefWaterPic();
				if(wMPath==null){
					wMPath=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);
				}*/
					CpPicAllpathExample e=new CpPicAllpathExample();
					e.createCriteria().andPicTypeEqualTo(5).andTragetIdEqualTo(wmId);
					List<CpPicAllpath> all=allpathMapper.selectByExample(e);//水印图片地址
					wMPath=clientPictureMapper.findDefWaterPic();
					if(!all.isEmpty()&&wMPath==null){
						wMPath=all.get(0).getAllPath();
					}
					
				}
				/*else{
				wMPath=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);}
//					wMPath="D:/trsphoto/small/2017/20170218/20170218161108_1487405468497_p.jpg";
			}*/

			Integer postion=downLevel.getWatermarkLocation();//水印位置
			//处理图片
			int nRandom = 1000;
			String stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
			String tempPath=sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId);
			// 图片及xml文件的临时存放目录
			String filePath = tempPath + stemp;
			// 确保不存在同名的目录
			while (new File(filePath).exists()) {
				stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
				filePath = tempPath + stemp;
			}
			logger.debug("filepath = "+filePath);
			ImgFileUtils.makeDirectory(filePath + File.separator + "dir");

			String fileName = null;
			String oriPicPath = null;//原图
			String txtPicInfo = "";
			Integer priceType=null;//0不定价，1定价，2不出售
			Integer price=null;//特殊定价价格
			String zipName = filePath + ".zip";
			//TODO 配置中的图片大小,暂时使用限制高
			int picRealSize = pxH;
			//获取图片list ,包括原图地址
			List<CpPicGroup> gorupList=aboutPictureMapper.selectPicInfoByOrder(orderId);
			List<CpPictureDownloadrecord> recordList=new ArrayList<CpPictureDownloadrecord>();
			// 遍历列表
			for (int i = 0; i < gorupList.size(); i++) {
				CpPicGroup group=gorupList.get(i);

				try {
					for (int j = 0; j < group.getPics().size(); j++) {
						//						System.out.println("..........................."+j);
						CpPicture picture=group.getPics().get(j);
						// 得到对象、对象filename
						fileName = group.getPics().get(j).getFilename();
						//						System.out.println("..........................."+fileName);
						oriPicPath= picture.getOriAllPath();
						/*priceType=group.getPriceType();
						if(priceType!=null&&priceType==2){
							throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片【%s】暂不出售", fileName));
						}*/
						txtPicInfo = fileName.substring(0, fileName .lastIndexOf(".")) + ".txt";
						
						try {
							BufferedImage alterdImage = ImageIO.read(new FileInputStream(oriPicPath));
							BufferedImage waterPic = null;
							BufferedImage finalImage = alterdImage;
							int bigPicHeight = 0;
							int bigPicWidth = 0;
							
							int[] size = ImageAnalyseUtil.computeSize(alterdImage, picRealSize);
							if (size.length!=0) {
								bigPicWidth = size[0];
								bigPicHeight = size[1];
							}
							//最长变限定
							if(pxH != 0){
								//改变图片大小
								alterdImage = ImageAnalyseUtil.resize(alterdImage, pxH);
								//水印图
								
								finalImage = ImageAnalyseUtil.computePosAndMark(isWaterMark, postion, wMPath, waterPic, alterdImage);
								//将图片保存为JPG格式
								ImageAnalyseUtil.saveJPG(finalImage, filePath+".jpg");
							}else{
								//改变图片大小
//								alterdImage = ImageAnalyseUtil.alterSize(alterdImage, bigPicWidth, bigPicHeight);
//								finalImage = ImageAnalyseUtil.computePosAndMark(isWaterMark, postion, wMPath, waterPic, alterdImage);
								//将图片保存为JPG格式
								ImageAnalyseUtil.saveJPG(finalImage, filePath+".jpg");
							}
							// 根据对象filename找到大图复制到临时目录
							//使用本地存储情况下
							ImgFileUtils.copyFile(filePath+".jpg", filePath+ File.separator + fileName);
							ImgFileUtils.deleteFile(filePath+".jpg");
							//稿件文本文件
							if(type==1){
								//								createPictureInfoTxt(group,picture,filePath+ File.separator + txtPicInfo);
								String txtPath=oriPicPath.substring(0, oriPicPath.lastIndexOf(".")) + ".txt";//txt原路径
								ImgFileUtils.copyFile(txtPath, filePath+ File.separator + txtPicInfo);
							}
						} catch (FileNotFoundException e) {
							logger.error("找不到图片文件。", e);
						} catch (IOException e) {
							logger.error("读取图片文件异常", e);
						}catch (Exception e) {
							logger.error("生成水印图片异常",e);
						}
						//记录图片下载日志
						String id = "";//稿件类别
						List<CpPicGroupCategory> categoryList =cpPicGroupCategoryMapper.selectByGroupId(group.getId());
						for (CpPicGroupCategory cpPicGroupCategory : categoryList) {
							Integer categoryId = cpPicGroupCategory.getCategoryId();
							id +=" "+categoryId.toString();
						}						
						String userName = cpUserMapper.findUserNameByUid(group.getAuthorId());
						CpPictureDownloadrecord record=new CpPictureDownloadrecord();
						record.setDownloadTime(new Date());
						record.setAuthorId(group.getAuthorId());
						record.setPictureGroupId(group.getId());
						record.setPictureId(picture.getId());
						record.setPictureGroup(id);
						record.setPictureSignTime(group.getSginTime());						
						record.setPictureAuthor(group.getAuthor());
						record.setAuthorLoginName(userName);//作者登录名
						record.setPictureFileName(fileName);
						record.setPictureFilePath(oriPicPath);
						record.setPictureTitle(picture.getTitle());
						record.setSiteId(siteId);
						record.setUserIP(UserUtils.getLocalIp(request));
						record.setUserName(user.getUserName());
						if (user.getSubscriptionType() == null) {
							record.setSubscriberType(null);
						}else{
							record.setSubscriberType((int)user.getSubscriptionType());
						}
						record.setEditUser(group.getFristPfdUser());
						record.setUserId(user.getId());
						record.setIsInteriorDownLoad(0);
						record.setLangType(langType);
						Byte isw=isWaterMark==0?CommonConstant.BYTE1:CommonConstant.BYTE0;
						record.setWatermarker(isw);
						recordList.add(record);
					}
				} catch (Exception e) {
					logger.error("压缩图片", e);
				}
			}
			//批量记录图片下载日志
			clientPictureMapper.insertDownRecords(recordList);
			if(flag){
				//修改订单状态
				CpOrderForm order=new CpOrderForm();
				order.setId(orderId);
				order.setPayTime(new Date());
				order.setOrderStatus(2);
				orderFormMapper.updateByPrimaryKeySelective(order);
				//修改用户下载数量
				cpUserMapper.updateByPrimaryKeySelective(userDownInfo);
			}
			try {
				// 遍历所有列表里面的id之后将目录里面的文件进行压缩
				ZipCompress.writeByApacheZipOutputStream(filePath,zipName, "");
				// 将生成的zip文件输出
				downloadZipFile(zipName, response);

				// 删除临时文件
				ImgFileUtils.deleteDirectory(filePath);
				if (ImgFileUtils.isFileExist(zipName)) {
					ImgFileUtils.deleteFile(zipName);
				}
			} catch (FileNotFoundException e) {
				logger.error("找不到压缩文件。", e);
			} catch (IOException e) {
				logger.error("读取压缩文件异常", e);
			}
		}else{
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADTYPEMSG);
		}
	}
	
	/**
	 * 下载已经结算过的图片，这些图片可能不在同一个订单中
	 * @param detailIds
	 * @param type
	 * @param user
	 * @param siteId
	 * @param response
	 * @param langType 
	 * @throws Exception
	 */
	public void downPicsByOrderDetailId(int [] detailIds,int type,CpUser user,int siteId,HttpServletResponse response,HttpServletRequest request, Integer langType) throws Exception{
		List<Integer> pics = cpOrderDetailMapper.getPicIdsByDetailId(detailIds, user.getId());
		if(pics==null || pics.size()==0){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,"订单结算已超过72小时，下载失败");
		}
		
		//获取用户下载级别
		CpDownloadLevel downLevel =clientPictureMapper.selectUserDownLevel(user.getId());
		if (downLevel!=null) {//用户存在下载级别
			int isWaterMark = downLevel.getIsWatermark();//是否水印：1否，0是
			int pxH = downLevel.getLimitPxH();//限制高
			String wMPath=null;//水印图片地址
			if(isWaterMark==0){//有水印
				Integer wmId=downLevel.getWatermarkPicId();//水印图片ID
				/*wMPath="D:/trsphoto/watermark/20170313124850_1489380530096_p.png";
//				wMPath=clientPictureMapper.findDefWaterPic();
				if(wMPath==null){
					wMPath=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);
				}*/
					CpPicAllpathExample e=new CpPicAllpathExample();
					e.createCriteria().andPicTypeEqualTo(5).andTragetIdEqualTo(wmId);
					List<CpPicAllpath> all=allpathMapper.selectByExample(e);//水印图片地址
					wMPath=clientPictureMapper.findDefWaterPic();
					if(!all.isEmpty()&&wMPath==null){
						wMPath=all.get(0).getAllPath();
					}
					
				}
				/*else{
				wMPath=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);}
//					wMPath="D:/trsphoto/small/2017/20170218/20170218161108_1487405468497_p.jpg";
			}*/

			Integer postion=downLevel.getWatermarkLocation();//水印位置
			//处理图片
			int nRandom = 1000;
			String stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
			String tempPath=sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId);
			// 图片及xml文件的临时存放目录
			String filePath = tempPath + stemp;
			// 确保不存在同名的目录
			while (new File(filePath).exists()) {
				stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
				filePath = tempPath + stemp;
			}
			logger.debug("filepath = "+filePath);
			ImgFileUtils.makeDirectory(filePath + File.separator + "dir");

			String fileName = null;
			String oriPicPath = null;//原图
			String txtPicInfo = "";
			String zipName = filePath + ".zip";
			//TODO 配置中的图片大小,暂时使用限制高
			int picRealSize = pxH;
			//获取图片list ,包括原图地址
			List<CpPicGroup> gorupList=aboutPictureMapper.selectPicInfoByOrderDetail(pics);
			List<CpPictureDownloadrecord> recordList=new ArrayList<CpPictureDownloadrecord>();
			// 遍历列表
			for (int i = 0; i < gorupList.size(); i++) {
				CpPicGroup group=gorupList.get(i);

				try {
					for (int j = 0; j < group.getPics().size(); j++) {
						//						System.out.println("..........................."+j);
						CpPicture picture=group.getPics().get(j);
						// 得到对象、对象filename
						fileName = group.getPics().get(j).getFilename();
						//						System.out.println("..........................."+fileName);
						oriPicPath= picture.getOriAllPath();
						/*priceType=group.getPriceType();
						if(priceType!=null&&priceType==2){
							throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片【%s】暂不出售", fileName));
						}*/
						txtPicInfo = fileName.substring(0, fileName .lastIndexOf(".")) + ".txt";
						
						try {
							BufferedImage alterdImage = ImageIO.read(new FileInputStream(oriPicPath));
							BufferedImage waterPic = null;
							BufferedImage finalImage = alterdImage;
							int bigPicHeight = 0;
							int bigPicWidth = 0;
							
							int[] size = ImageAnalyseUtil.computeSize(alterdImage, picRealSize);
							if (size.length!=0) {
								bigPicWidth = size[0];
								bigPicHeight = size[1];
							}
							//最长变限定
							if(pxH != 0){
								//改变图片大小
								alterdImage = ImageAnalyseUtil.resize(alterdImage, pxH);
								//水印图
								finalImage = ImageAnalyseUtil.computePosAndMark(isWaterMark, postion, wMPath, waterPic, alterdImage);
								//将图片保存为JPG格式
								ImageAnalyseUtil.saveJPG(finalImage, filePath+".jpg");
							}else{
								//改变图片大小
//								alterdImage = ImageAnalyseUtil.alterSize(alterdImage, bigPicWidth, bigPicHeight);
//								finalImage = ImageAnalyseUtil.computePosAndMark(isWaterMark, postion, wMPath, waterPic, alterdImage);
								//将图片保存为JPG格式
								ImageAnalyseUtil.saveJPG(finalImage, filePath+".jpg");
							}
							// 根据对象filename找到大图复制到临时目录
							//使用本地存储情况下
							ImgFileUtils.copyFile(filePath+".jpg", filePath+ File.separator + fileName);
							ImgFileUtils.deleteFile(filePath+".jpg");
							//稿件文本文件
							if(type==1){
								//								createPictureInfoTxt(group,picture,filePath+ File.separator + txtPicInfo);
								String txtPath=oriPicPath.substring(0, oriPicPath.lastIndexOf(".")) + ".txt";//txt原路径
								ImgFileUtils.copyFile(txtPath, filePath+ File.separator + txtPicInfo);
							}
						} catch (FileNotFoundException e) {
							logger.error("找不到图片文件。", e);
						} catch (IOException e) {
							logger.error("读取图片文件异常", e);
						}catch (Exception e) {
							logger.error("生成水印图片异常",e);
						}
						//记录图片下载日志
						String id = "";//稿件类别
						List<CpPicGroupCategory> categoryList =cpPicGroupCategoryMapper.selectByGroupId(group.getId());
						for (CpPicGroupCategory cpPicGroupCategory : categoryList) {
							Integer categoryId = cpPicGroupCategory.getCategoryId();
							id +=" "+categoryId.toString();
						}
						
						String userName = cpUserMapper.findUserNameByUid(group.getAuthorId());
						CpPictureDownloadrecord record=new CpPictureDownloadrecord();
						record.setDownloadTime(new Date());
						record.setAuthorId(group.getAuthorId());
						record.setPictureGroupId(group.getId());
						record.setPictureId(picture.getId());
						record.setPictureGroup(id);
						record.setPictureSignTime(group.getSginTime());						
						record.setPictureAuthor(group.getAuthor());
						record.setAuthorLoginName(userName);//作者登录名
						//add by xiayunan 2017-09-12
						if(user.getBalanceBasePerprice()!=null){
							record.setDownLoadPrice(user.getBalanceBasePerprice());
						}
						record.setPictureFileName(fileName);
						record.setPictureFilePath(oriPicPath);
						record.setPictureTitle(picture.getTitle());
						record.setSiteId(siteId);
						record.setUserIP(UserUtils.getLocalIp(request));
						record.setUserName(user.getUserName());
						record.setLangType(langType);
						if (user.getSubscriptionType() == null) {
							record.setSubscriberType(null);
						}else{
							record.setSubscriberType((int)user.getSubscriptionType());
						}
						record.setEditUser(group.getFristPfdUser());
						record.setUserId(user.getId());
						record.setIsInteriorDownLoad(0);
						Byte isw=isWaterMark==0?CommonConstant.BYTE1:CommonConstant.BYTE0;
						record.setWatermarker(isw);
						recordList.add(record); 
					}
				} catch (Exception e) {
					logger.error("压缩图片", e);
				}
			}
			//批量记录图片下载日志
			clientPictureMapper.insertDownRecords(recordList);
			
			try {
				// 遍历所有列表里面的id之后将目录里面的文件进行压缩
				ZipCompress.writeByApacheZipOutputStream(filePath,zipName, "");
				// 将生成的zip文件输出
				downloadZipFile(zipName, response);

				// 删除临时文件
				ImgFileUtils.deleteDirectory(filePath);
				if (ImgFileUtils.isFileExist(zipName)) {
					ImgFileUtils.deleteFile(zipName);
				}
			} catch (FileNotFoundException e) {
				logger.error("找不到压缩文件。", e);
			} catch (IOException e) {
				logger.error("读取压缩文件异常", e);
			}
		}else{
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, CommonConstant.NODOWNLOADTYPEMSG);
		}
	}
	
	/**
	 * 管理员下载图片，并记录下载日志
	 * @param picIds 要下载的图片ID
	 * @param type 0图片下载，1图文下载
	 * @param user 下载用户
	 * @param siteId 站点ID
	 * @param response
	 * @param langType 
	 * @throws Exception
	 */
	public void adminDown(Integer orderId,int type,CpUser user,int siteId,HttpServletResponse response,HttpServletRequest request, Integer langType) throws Exception{
		CpOrderFormExample eo=new CpOrderFormExample();
		eo.createCriteria().andIdEqualTo(orderId);
		List<CpOrderForm> list=orderFormMapper.selectByExample(eo);
		if(list==null || list.size()==0){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,"订单不存在");
		}
		//处理图片
		int nRandom = 1000;
		String stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
		String tempPath=sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId);
		// 图片及xml文件的临时存放目录
		String filePath = tempPath + stemp;
		// 确保不存在同名的目录
		while (new File(filePath).exists()) {
			stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
			filePath = tempPath + stemp;
		}
		logger.debug("filepath = "+filePath);
		ImgFileUtils.makeDirectory(filePath + File.separator + "dir");

		String fileName = null;
		String oriPicPath = null;//原图
		String txtPicInfo = "";
		String zipName = filePath + ".zip";
		//获取图片list ,包括原图地址
		List<CpPicGroup> gorupList=aboutPictureMapper.selectPicInfoByOrder(orderId);
		List<CpPictureDownloadrecord> recordList=new ArrayList<CpPictureDownloadrecord>();
		// 遍历列表
		for (int i = 0; i < gorupList.size(); i++) {
			CpPicGroup group=gorupList.get(i);
			try {
				for (int j = 0; j < group.getPics().size(); j++) {
					CpPicture picture=group.getPics().get(j);
					// 得到对象、对象filename
					fileName = group.getPics().get(j).getFilename();
					oriPicPath= picture.getOriAllPath();
					//add by xiayunan 2017-09-12
					txtPicInfo = fileName.substring(0, fileName .lastIndexOf(".")) + ".txt";
					try {
						// 根据对象filename找到原图复制到临时目录
						
						ImgFileUtils.copyFile(oriPicPath, filePath+ File.separator + fileName);
						//稿件文本文件
						if(type==1){
							String txtPath=oriPicPath.substring(0, oriPicPath.lastIndexOf(".")) + ".txt";//txt原路径
							System.out.println("<<<<<<<<<<<<<<txtPicInfo:"+txtPicInfo);
							System.out.println("<<<<<<<<<<<<<<txtPath:"+txtPath);
							System.out.println("<<<<<<<<<<<<<<filePath:"+filePath+ File.separator + txtPicInfo);
							ImgFileUtils.copyFile(txtPath, filePath+ File.separator + txtPicInfo);
						}
					} catch (FileNotFoundException e) {
						logger.error("找不到图片文件。", e);
					} catch (IOException e) {
						logger.error("读取图片文件异常", e);
					}catch (Exception e) {
						logger.error("生成水印图片异常",e);
					}
					//记录图片下载日志
					String id = "";//稿件类别
					List<CpPicGroupCategory> categoryList =cpPicGroupCategoryMapper.selectByGroupId(group.getId());
					for (CpPicGroupCategory cpPicGroupCategory : categoryList) {
						Integer categoryId = cpPicGroupCategory.getCategoryId();
						id +=" "+categoryId.toString();
					}
					
					String userName = cpUserMapper.findUserNameByUid(group.getAuthorId());
					CpPictureDownloadrecord record=new CpPictureDownloadrecord();
					record.setDownloadTime(new Date());
					record.setAuthorId(group.getAuthorId());
					record.setPictureGroupId(group.getId());
					record.setPictureId(picture.getId());
					record.setPictureGroup(id);
					record.setPictureSignTime(group.getSginTime());						
					record.setPictureAuthor(group.getAuthor());
					record.setAuthorLoginName(userName);//作者登录名
					record.setDownLoadPrice(user.getBalanceBasePerprice());
					record.setPictureFileName(fileName);
					record.setPictureFilePath(oriPicPath);
					record.setPictureTitle(picture.getTitle());
					record.setSiteId(siteId);
					record.setUserIP(UserUtils.getLocalIp(request));
					record.setUserName(user.getUserName());
					record.setEditUser(group.getFristPfdUser());
					record.setUserId(user.getId());
					record.setIsInteriorDownLoad(0);
					record.setLangType(langType);
					if (user.getSubscriptionType() == null) {
						record.setSubscriberType(null);
					}else{
						record.setSubscriberType((int)user.getSubscriptionType());
					}
					record.setEditUser(group.getFristPfdUser());
					record.setWatermarker(CommonConstant.BYTE1);
					recordList.add(record);
				}
			} catch (Exception e) {
				logger.error("压缩图片", e);
			}
		}
		//批量记录图片下载日志
		clientPictureMapper.insertDownRecords(recordList);
		try {
			// 遍历所有列表里面的id之后将目录里面的文件进行压缩
			ZipCompress.writeByApacheZipOutputStream(filePath,zipName, "");
			// 将生成的zip文件输出
			downloadZipFile(zipName, response);

			// 删除临时文件
			ImgFileUtils.deleteDirectory(filePath);
			if (ImgFileUtils.isFileExist(zipName)) {
				ImgFileUtils.deleteFile(zipName);
			}
		} catch (FileNotFoundException e) {
			logger.error("找不到压缩文件。", e);
		} catch (IOException e) {
			logger.error("读取压缩文件异常", e);
		}
	}

	/**
	 * 下载zip文件
	 * @param filename 文件的绝对路径
	 * @param res 输出流
	 */
	public static void downloadZipFile(String filename,
			HttpServletResponse res) {
		if (StringUtils.isNotBlank(filename)) {
			BufferedInputStream bis = null;
			OutputStream os = null;
			try {
				String zipFile = new File(filename).getName();
				zipFile = new String(zipFile.getBytes("GBK"),"ISO8859_1");

				res.reset();
				res.getCharacterEncoding();
				res.setContentType("application/x-tar");
				//				res.setContentType("application/x-gzip");
				res.setHeader("Content-Disposition", "attachment; filename="
						+ zipFile);
				os = res.getOutputStream();
				// res.setHeader("Content_Length",String.valueOf(new
				// File(filename).length()));
				bis = new BufferedInputStream(new FileInputStream(filename));
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = bis.read(buf)) > 0) {
					os.write(buf, 0, len);
				}

			} catch (FileNotFoundException e) {
				// logger.error("找不到图片 {}. ", filename, e);
			} catch (IOException e) {
				// logger.error("读取文件 { }出错. " + filename, e);
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
					} // 关闭流
				}
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
					} // 关闭输出
				}
			}
		}
	}

	/**
	 * 生成图片说明
	 * @param group 稿件信息 
	 * @param picture  单图信息
	 */
	public void createPictureInfoTxt(CpPicGroup group,CpPicture picture,String filePath){
		try{ 
			File txtfile=new File(filePath);
			if (txtfile.isFile() && txtfile.exists()) {  
				txtfile.delete();  
			} 
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile));

			bw.write("稿件信息："+"\r\n\r\n");
			//			bw.write("稿件ID："+group.getId()+"\r\n\r\n");
			//			bw.write("拍摄时间："+DateUtils.format(group.getFileTime(), DateUtils.sdfLong)+"\r\n\r\n");
			bw.write("稿件标题："+group.getTitle()+"\r\n\r\n");
			bw.write("稿件作者："+group.getAuthor()+"\r\n\r\n");
			//			bw.write("编辑人员："+group.getUpdateUser()+"\r\n\r\n");
			StringBuffer cateName=null;
			String cate=null;
			for (int i = 0; i < group.getCates().size(); i++) {
				cate=group.getCates().get(i).getCategoryName();
				if(cateName==null){
					cateName=new StringBuffer();
					cateName.append(cate);
				}else{
					cateName.append("、").append(cate);
				}
			}
			bw.write("稿件类别："+cateName+"\r\n\r\n");
			bw.write("发布时间："+DateUtils.format(group.getSginTime(), DateUtils.sdfLongTimePlus)+"\r\n\r\n");
			bw.write("人物："+group.getPeople()+"\r\n\r\n");
			bw.write("地点："+group.getPlace()+"\r\n\r\n");
			/*String pro=group.getProperties()==CommonConstant.BYTE0?"新闻图片":"专题图片";
			bw.write("稿件属性："+pro+"\r\n\r\n");
			bw.write("上传时间："+DateUtils.format(group.getApplyTime(), DateUtils.sdfLongTimePlus)+"\r\n\r\n");
			String priceTye=null;
			if(group.getPriceType()==null||group.getPriceType()==0){
				priceTye="不定价";
			}else if(group.getPriceType()==1){
				priceTye="定价";
			}else if(group.getPriceType()==2){
				priceTye="不出售";
			}
			bw.write("是否限价："+priceTye+"\r\n\r\n");
			if("定价".equals(priceTye)){
				bw.write("特殊定价："+group.getPrice()+"\r\n\r\n");
			}*/
			bw.write("关键字："+group.getKeywords()+"\r\n\r\n");
			bw.write("稿件说明："+removeHtmlTag(group.getMemo())+"\r\n\r\n");
			//			bw.write("备注："+group.getRemark()+"\r\n\r\n");

			bw.write("\r\n\r\n");
			bw.write("图片信息："+"\r\n\r\n");
			/*bw.write("图片ID："+picture.getId()+"\r\n\r\n");
			bw.write("文件名："+picture.getFilename()+"\r\n\r\n");
			bw.write("文件大小："+picture.getPictureLength()/1024+"K\r\n\r\n");
			bw.write("拍摄时间："+DateUtils.format(picture.getFilmTime(), DateUtils.sdfLong)+"\r\n\r\n");*/
			bw.write("作者："+picture.getAuthorName()+"\r\n\r\n");
			/*bw.write("人物："+picture.getPeople()+"\r\n\r\n");
			bw.write("关键字："+picture.getKeywords()+"\r\n\r\n");*/
			bw.write("图片说明："+removeHtmlTag(picture.getMemo())+"\r\n\r\n");
			String pAuthorName=picture.getAuthorName();
			pAuthorName=pAuthorName==null||StringUtils.isEmpty(pAuthorName)?group.getAuthor():pAuthorName;
			bw.write("中新社发  "+pAuthorName+" 摄\r\n\r\n");

			bw.flush();
			bw.close();
		}catch(Exception e){
			logger.error("生成图片信息txt失败",e);
		}
	}


	/**
	 * 将限制类型下载数量重置为0
	 * @param type 0每天，1每月，2每年
	 */
	public void putZeroByType(int type){
		try {
			clientPictureMapper.updateLimitDlMun(type);
		} catch (Exception e) {
			logger.error("重置下载数量出错"+e.getMessage());
		}
	}
	
	public String removeHtmlTag(String content){
		// 定义HTML标签的正则表达式
		String regEx_html = "<[^>]+>";
		// 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		String regEx_special = "\\&[a-zA-Z]{1,10};";
		
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(content);
        content = m_html.replaceAll(""); // 过滤script标签
   
        Pattern p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        Matcher m_special = p_special.matcher(content);
        content = m_special.replaceAll(""); // 过滤style标签
        return content;
	}
}
