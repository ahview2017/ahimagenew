package com.deepai.photo.common.constant;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置，一经定义只能添加，不能修改！
 */
/**         
* 类描述：   
* 创建人：huqiankai   
* 创建时间：2017年4月27日 下午1:52:25      
* 修改备注：        
*/
@Component
public  class CommonConstant {
	
	public static final Byte BYTE0 = 0;
	public static final Byte BYTE1 = 1;
	public static final Byte BYTE2 = 2;
	public static final Byte BYTE3 = 3;
	
	public static final BigDecimal BIGDECIMAL0 = new BigDecimal(0);
	public static final BigDecimal BIGDECIMAL1 = new BigDecimal(1);
	public static final BigDecimal BIGDECIMAL2 = new BigDecimal(2);
	
	public static final int SUCCESSCODE = 211;
	public static final int SUCCESSCODE212 = 212;
	public static final String SUCCESSSTRING = "操作成功";
	
	public static final int EXCEPTIONCODE = 510;//服务器异常
	public static final String EXCEPTIONMSG = "服务器异常";//服务器异常
	public static final int PARAMERROR = 511;//参数错误
	public static final int NULLCODE = 512;//结果不存在
	public static final int REPEATCODE = 513;//结果重复
	public static final int FAILURECODE = 514;//操作失败
	public static final String EMAILEXCEPTIONMSG = "邮件发送异常";
	
	public static final int FILEERRORCODE = 515;//服务器异常
	public static final String FILEERRORMSG = "不支持该格式上传";//服务器异常
	public static final String NOFILEERRORMSG = "请上传图片";//请上传图片
	public static final String CNTEDITMSG = "当前稿件处于【%s】阶段，您没有权限操作";//不可编辑
	public static final String NOTCNTEDITMSG = "当前稿件不在【%s】阶段，不能进行【%s】操作";//不可编辑
	public static final String PARAMERRORMSG = "参数异常";//参数异常
	
	public static final int NOTLOGINCODE = 520;//未登录
	public static final String NOTLOGINMSG = "未登录";//未登录
	public static final int ISEDITCODE = 522;//在编状态
	public static final String ISEDITMSG = "当前稿件正在由【%s】编辑，您不可操作";//在编状态提示
	public static final int ORDERNUMCODE = 523;//排序号
	public static final String ORDERNUMMSG = "排序号错误";//排序号错误
	public static final int UNISEDITCODE = 524;//强制解锁
	public static final String UNISEDITMSG = "当前稿件已被【%s】强制解锁，您不可操作";//强制解锁
	public static final int SSTVWORDCODE = 525;//敏感词
	public static final String SSTVWORDMSG = "当前稿件存在敏感词【%s】，不可操作";//敏感词
	public static final String NODOWNLOADMSG = "用户不可下载，请联系管理员。";
	public static final String NODOWNLOADLEVELMSG = "用户尚未设置下载级别，不可下载，请联系管理员。";
	public static final String NODOWNLOADTYPEMSG = "用户尚未设置下载方式，不可下载，请联系管理员。";
	public static final String NOBALANCETYPEMSG = "用户尚未设置余额协议下载价格，不可下载，请联系管理员。";
	public static final String NOBALANCEBASETYPEMSG = "用户尚未设置余额分成基价，不可下载，请联系管理员。";
	public static final String NOBALANCELIMITTYPEMSG = "用户尚未设置下载限制类型，不可下载，请联系管理员。";
	public static final String NODOWNLOADINFO = "用户尚未设置下载信息，不可下载，请联系管理员。";
	public static final String NODOWNLOADNUM = "超过下载限制，不可下载";
	public static final String NODOWNLOADDATE = "合同期限已过，不可下载";
	
	public static final int NORIGHT = 521;//没有权限
	
	
	
	public static final int FAILUREMILLSSALT = 300;
	//TODO
	public static final int FAILUREMILLSTOKEN = 1800;//半小时1800s
	public static final Long HOURS72 = 259200000L;//72小时1800s
	public static final String USERSALT = "userSalt_";
	public static final String REDISID = "redisId_";
//	public static final String ONLINUSERSTE = "onlineUserSet";
	public static final String ONLINUSERLIST= "onlineUserlist";
	
	public static final String doubleSprit= "\\";
	public static final String oneSprit= "/";
	public static final String NEWLINE= "<br/>";
	public static final String EN = "en";
	public static final String ZH = "zh";
	public static final String SMALL= "small";
	public static final String WATER= "watermark";
	public static final String WATERMEDIUM= "watermarkedmedium";
	public static  String SMALLHTTPPath;
	@Value("${ipAdd}")
	public  void setIpAdd(String ipAdd) {
		CommonConstant.SMALLHTTPPath = ipAdd+"/";
	}
	
	public static final String Site = "Site";
	public static final String Role = "Role";
	public static final String Right = "Right";
	public static final String Other = "Other";
	public static final String User = "User";
	public static final String Message = "Message";//站内信
	public static final String Notice = "Notice";//公告
	public static final String PostComment = "PostComment";//网站留言
	public static final String UserLogin = "UserLogin";
	public static final String Config = "Config";
	public static final String Email = "Email";
	public static final String Car = "Car";
	public static final String Adver = "Adver";
	public static final String Picture = "Picture";
	public static final String Favorite = "Favorite";
	public static final String LeavingMsg = "LeavingMsg";
	public static final String Needs = "Needs";
	public static final String Order = "Order";
	public static final String PhoneMsg = "PhoneMsg";
	public static final String Topic = "Topic";

	public static final String PicGroupOperation = "PicGroupOperation";

	public static final String Category = "Category";

	
	public static class OpCode {
		public static final short SUCCESS = 200;
		public static final short INVALID_PARAMS = 400;
		public static final short NEED_AUTHORIZATION = 401;
		public static final short NOT_PERMITED = 403;
		public static final short RESOURCE_NOT_FOUND = 404;
		public static final short INTERNAL_EXCEPTION = 500;
	}
	
}























