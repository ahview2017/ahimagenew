package com.deepai.photo.common.constant;

import java.util.Map;

/**
 * @author guoyanhong
 * 系统配置默认值
 */
public class SysConfigConstant {
	//新增目录
	public	static String 	ROOT_PIC_PATH="ROOT_PIC_PATH";//图片根目录
	public	static String 	EN_ROOT_PIC_PATH="EN_ROOT_PIC_PATH";//英文图片根目录
	public	static String 	ZH_ROOT_PIC_PATH="ZH_ROOT_PIC_PATH";//中文图片根目录
	public	static String 	SENSITIVEWORD="SENSITIVEWORD";//中文图片根目录
	
	
	
	public	static String 	DOWNLOAD_WATER_POS="DOWNLOAD_WATER_POS"	;//	1	下载图片的默认水印位置
	public	static String 	DEFAULT_WATERMARK_PIC="DEFAULT_WATERMARK_PIC"	;//	D:/trsphoto/a-gyh/waterMark.png	默认水印图片
	public	static String 	UPLOAD_WATER_POSITION="UPLOAD_WATER_POSITION"	;//	southeast	上传时默认的水印图位置
	public	static String 	CLEAR_CPK_TIME="CLEAR_CPK_TIME"	;//	56 08 11 * * ?	清除成品库图片时间规则
	public	static String 	CLEAR_PICOPE_DAYS="CLEAR_PICOPE_DAYS"	;//	360	清除图片操作记录的天数
	public	static String 	CLEAR_PICOPE_TIME="CLEAR_PICOPE_TIME"	;//	55 55 12 12 12 ?	清除图片操作记录时间
	public	static String 	NOTICE_SUM="NOTICE_SUM"	;//	8	走马灯公告显示数
	public	static String 	CLEAR_FBK_DAYS="CLEAR_FBK_DAYS"	;//	6	清理发布库图片天数
	public	static String 	CLEAR_FBK_TIME="CLEAR_FBK_TIME"	;//	56 32 09 * * ?	清除发布库图片时间规则
	public	static String 	CLEAR_CPK_DAYS="CLEAR_CPK_DAYS"	;//	400	从成品库中移除的天数12
	public	static String 	DEFAULT_COVER_PICTURE_OUT="DEFAULT_COVER_PICTURE_OUT"	;//	cover.jpg	外网默认首页图片 
	public	static String 	DEFAULT_CLASSIFICATION_PATH="DEFAULT_CLASSIFICATION_PATH"	;//	D:/trsphoto/classification/	默认原图路径
	public  static String   DEFAULT_SPECIAL_PATH="DEFAULT_SPECIAL_PATH"; 	//默认专题图片路径
	public  static String   DEFAULT_ADVERTISEMENT_PATH="DEFAULT_ADVERTISEMENT_PATH";  //默认广告位和分类图片地址
	public	static String 	HEAD_PORTRAIT_PATH="HEAD_PORTRAIT_PATH"	;//	D:/trsphoto/classification/	默认分类图片路径
	public	static String 	MEDIUM_PIC_PATH_OUT="MEDIUM_PIC_PATH_OUT"	;//	D:\bjrb_trsphoto\medium\	外网中图路径 
	public	static String 	ORIGINAL_PIC_PATH_OUT="ORIGINAL_PIC_PATH_OUT"	;//	D:\bjrb_trsphoto\original\	外网原图路径  
	public	static String 	BIG_PIC_PATH_OUT="BIG_PIC_PATH_OUT"	;//	D:\bjrb_trsphoto\big\	外网大图路径
	public	static String 	WATERMARKEDMEDIUM_PIC_PATH_OUT="WATERMARKEDMEDIUM_PIC_PATH_OUT"	;//	D:\bjrb_trsphoto\watermarkedmedium\	外网水印中图路径 
	public	static String 	TEMP_PIC_PATH="TEMP_PIC_PATH"	;//	D:/trsphoto/temp/	系统临时路径
	public	static String 	DEFAULT_PICTURE="DEFAULT_PICTURE"	;//	default.jpg	缺省的小图文件
	public	static String 	COVER_PIC_PATH_OUT="COVER_PIC_PATH_OUT"	;//	D:\bjrb_trsphoto\cover\	外网首页封面图片路径 
	public	static String 	ORIGINAL_PIC_PATH="ORIGINAL_PIC_PATH"	;//	D:/trsphoto/original/	原图路径稍等
	public	static String 	BIG_PIC_PATH="BIG_PIC_PATH"	;//	D:/trsphoto/big/	大图路径
	public	static String 	MEDIUM_PIC_PATH="MEDIUM_PIC_PATH"	;//	D:/trsphoto/medium/	中图路径
	public	static String 	WATERMARKEDMEDIUM_PIC_PATH="WATERMARKEDMEDIUM_PIC_PATH"	;//	D:/trsphoto/watermarkedmedium/	水印中图路径
	public	static String 	SMALL_PIC_PATH="SMALL_PIC_PATH"	;//	D:/trsphoto/small/	小图路径
	public	static String 	BIG_PIC_SIZE="BIG_PIC_SIZE"	;//	3000	大图尺寸限制
	public	static String 	MEDIUM_PIC_SIZE="MEDIUM_PIC_SIZE"	;//	800	中图尺寸限制
	public	static String 	WATERMARKEDMEDIUM_PIC_SIZE="WATERMARKEDMEDIUM_PIC_SIZE"	;//	800	水印中图尺寸限制
	public	static String 	SMALL_PIC_SIZE="SMALL_PIC_SIZE"	;//	150	小图尺寸限制
	public	static String 	COVER_PIC_PATH="COVER_PIC_PATH"	;//	D:/trsphoto/cover/	首页封面图片路径
	public	static String 	DEFAULT_COVER_PICTURE="DEFAULT_COVER_PICTURE"	;//	cover.jpg	默认首页图片
	public	static String 	SMALL_PIC_PATH_OUT="SMALL_PIC_PATH_OUT"	;//	D:\bjrb_trsphoto\small\	外网小图路径
	public	static String 	GET_PAPER_TIME="GET_PAPER_TIME"	;//	20 18 17 * * ?	获取图片见报信息定时任务时间设置。此设置意为：在每天23点59分59秒
	public	static String 	GET_PAPER_DAYS="GET_PAPER_DAYS"	;//	1	获取见报信息时间段
	public	static String 	PICSIZE_1000="PICSIZE_1000"	;//	1000	下载大图图片规格1 最长边为1000
	public	static String 	PICSIZE_2000="PICSIZE_2000"	;//	2000	下载大图图片规格2 最长边为2000
	public	static String 	PICSIZE_3000="PICSIZE_3000"	;//	3000	下载大图图片规格3 最长边为3000
	public	static String 	DEFAULT_WATERMARK_PATH="DEFAULT_WATERMARK_PATH"	;//	D:/trsphoto/watermark/	水印路径
	public	static String 	Email_adds="Email_adds"	;//	邮箱账号
	public	static String 	Email_password="Email_password"	;//	邮箱密码
	public	static String 	Phone_username="Phone_username"	;//	发送手机短信账号
	public	static String 	Phone_password="Phone_password"	;//	发送手机短信密码
	
	public	static String 	Email_edit="Email_edit"	;//	编辑邮箱账号
	public	static String 	Email_sell="Email_sell"	;//	销售邮箱账号
	
	public	static String   ExamUserTitle="ExamUserTitle";    //用户审核成功邮件标题
	public	static String   ExamUserContent="ExamUserContent";    //用户审核成功邮件内容
	public	static String   UpdateUserTitle="UpdateUserTitle";       //更新用户成功邮件标题
	public	static String   UpdateUserContent="UpdateUserContent";       //更新用户成功邮件内容
	public	static String   UpdatePasswordTitle="UpdatePasswordTitle"; //用户修改密码邮件标题
	public	static String   UpdatePasswordContent="UpdatePasswordContent"; //用户修改密码邮件内容
	public	static String   RechargeUserTitle="RechargeUserTitle"; //用户充值邮件标题
	public	static String   RechargeUserContent="RechargeUserContent"; //用户充值邮件内容
	
	public	static String 	DEFAULTNOTICE="DEFAULTNOTICE"	;//	默认通知用户方式:0短信，1邮件，2邮件和短信
	public  static String   CLIENTIP = "CLIENTIP";  //客户端ip
	public	static String 	UNLOCKTIME="UNLOCKTIME";  //登陆错误超出限制时的限制时间（小时计算）

	public	static String 	PICTUREMINHEIGHT="PICTUREMINHEIGHT";//稿件图片上传最小高度（图片高度不能高度小于此值）
	public	static String 	PICTUREMINWIDTH="PICTUREMINWIDTH";//稿件图片上传最小宽度（图片高度不能宽度小于此值）
	public	static String 	PICTUREMAXLENGTH="PICTUREMAXLENGTH";//稿件图片上传大小（文件大小）最大限制（图片文件大小不能高于此值），单位KB，8192字节=8M
	public	static String 	PICTUREMINLENGTH="PICTUREMINLENGTH";//稿件图片上传大小（文件大小）最小限制（图片文件大小不能低于此值），单位M，1048576字节=1M
	public 	static String   QIANBAO_FILE_PATH="QIANBAO_FILE_PATH" ;
	// add by liu.jinfeng@2017年9月5日 下午9:03:52
    public static String SMALL_PIC_SIZE400 = "SMALL_PIC_SIZE400";// 400小图尺寸限制
    public static String SMALL_PIC_PATH400 = "SMALL_PIC_PATH400";// 400小图位置
    public static String WATERMARK_PIC_PATH1200 = "WATERMARK_PIC_PATH1200";// 1200
    public static String MEDIUM_PIC_SIZE1200 = "MEDIUM_PIC_SIZE1200";// D:/trsphoto/medium/
    public static String MEDIUM_PIC_PATH1200 = "MEDIUM_PIC_PATH1200";// D:/trsphoto/medium/
    // add by xia.yunan 2017-09-06
    public static String LOCAL_GM_PATH = "LOCAL_GM_PATH";// /usr/local/graphicsmagick/bin
    public static String MAS_BASE_URL = "MAS_BASE_URL";// http://192.168.18.85:8081/mas/openapi/pages.do?appKey=TRSPMS123
    
    // add by xia.yunan 2017-09-11
    public static String WATERMAKER_TRANSPARENCY = "WATERMAKER_TRANSPARENCY";//100
    public static String WATERMAKER_POSITION = "WATERMAKER_POSITION";//0.6
    
    // add by xia.yunan 2017-12-18
    public static String SIGN_BASE_COLUMN = "SIGN_BASE_COLUMN";
    
	public static Map<String,String> config;
	
    /** 注册发送短信模版 */
   public static final String MSG_SEND_CODE = "MSG_SEND";
   /** 忘记密码发送短信模版 */
   public static final String MSG_FORGET_CODE = "MSG_FORGET_SEND";
   /** 前台登录发送短信模版 */
   public static final String MSG_LOGIN_CODE = "MSG_LOGIN_SEND";
   /** 注册成功后发送短信模版 */
   public static final String MSG_SUCCESS_CODE = "MSG_SUCCESS_SEND";
   /** 投稿发送验证码模版 */
   public static final String MSG_PHONE_CODE = "MSG_PHONE_SEND";
   /**注册成功后发送邮件内容模版*/
   public static final String MAIL_SUCCESS_CODE = "MAIL_SUCCESS_SEND";
   /**注册成功后发送邮件标题模版*/
   public static final String MAIL_TITLE_CODE = "MAIL_TITLE_SEND";
   /**忘记密码发送邮件模版*/
   public static final String MAIL_FORGET_CODE = "MAIL_FORGET_SEND";
   /**找回密码邮件模版*/
   public static final String MAIL_GET_CODE = "MAIL_GET_SEND";
   /**找回密码邮件标题*/
   public static final String MAIL_FORGETTITLE_CODE = "MAIL_FORGETTITLE_SEND";
   
   /**手机版投稿专用通讯员用户名*/
   public static final String MOBILE_SEND_USERNAME = "MOBILE_SEND_USERNAME";
   
   /**禁止打水印栏目ID*/
   public static final String FORBIT_WATERMARK_COLUMN = "FORBIT_WATERMARK_COLUMN";
   
   /**禁止显示购物车栏目ID*/
   public static final String FORBIT_SHOPPING_COLUMN = "FORBIT_SHOPPING_COLUMN";
   
   
   
}
