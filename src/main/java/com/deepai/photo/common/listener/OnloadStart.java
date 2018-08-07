package com.deepai.photo.common.listener;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.deepai.photo.common.PropertiesFileUtil;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.picture.FlowService;

public class OnloadStart extends HttpServlet{
	private Logger log=Logger.getLogger(OnloadStart.class);
	private final String hyphen="-";
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;
	//系统启动后，调用的方法,初始化系统配置
	public void init() throws ServletException{
		try {
			
			
//	RedisClientTemplate redisClientTemplate=(RedisClientTemplate)SpringContextUtil.getBean("redisClientTemplate");
			PropertiesFileUtil fileUtil=new PropertiesFileUtil();
			
			//是否将系统配置同步至redis
			boolean ifSysConfigStart=Boolean.valueOf(fileUtil.QueryValue("/baseConfig.properties", "ifSysConfigStart"));
			if(ifSysConfigStart){
				SysConfigService sysConfigService=(SysConfigService)SpringContextUtil.getBean("sysConfigService");
				sysConfigService.setDbConfigToRedis();			
			}
			//是否将校审配置同步至redis
			boolean ifProofreadStart=Boolean.valueOf(fileUtil.QueryValue("/baseConfig.properties", "ifProofreadStart"));
			if(ifProofreadStart){
				FlowService flowService=(FlowService)SpringContextUtil.getBean("flowService");
				//flowService.getDayProofreadToRedis(null);
				//改为查询站点1内部的校审流程
				flowService.getDayProofreadToRedis(1);	
			}
			
			Map<String,String> config=new HashMap<String, String>();
			//配置名和配置值
			config.put("DOWNLOAD_WATER_POS", "1");//下载图片的默认水印位置
			config.put("UPLOAD_WATER_POSITION", "southeast");//上传时默认的水印图位置
			config.put("DEFAULT_WATERMARK_PIC", "D:/trsphoto/a-gyh/waterMark.png");//默认水印图片
			config.put("DEFAULT_WATERMARK_PATH","D:/trsphoto/watermark/");//水印图路径
			config.put("ORIGINAL_PIC_PATH","D:/trsphoto/original/");//原图路径稍等
			config.put("DEFAULT_CLASSIFICATION_PATH","D:/trsphoto/classification/");//原图路径稍等
			config.put("HEAD_PORTRAIT_PATH","D:/trsphoto/headportrait/");//头像路径稍等
			config.put("BIG_PIC_PATH","D:/trsphoto/big/");//大图路径
			config.put("MEDIUM_PIC_PATH","D:/trsphoto/medium/");//中图路径
			config.put("WATERMARKEDMEDIUM_PIC_PATH","D:/trsphoto/watermarkedmedium/");//水印中图路径
			config.put("SMALL_PIC_PATH","D:/trsphoto/small/");//小图路径
			config.put("BIG_PIC_SIZE","3000");//大图尺寸限制
			config.put("MEDIUM_PIC_SIZE","800");//中图尺寸限制
			config.put("WATERMARKEDMEDIUM_PIC_SIZE","800");//水印中图尺寸限制
			config.put("SMALL_PIC_SIZE","150");//小图尺寸限制
			config.put("PICSIZE_1000","1000");//下载大图图片规格1 最长边为1000
			config.put("PICSIZE_2000","2000");//下载大图图片规格2 最长边为2000
			config.put("PICSIZE_3000","3000");//下载大图图片规格3 最长边为3000
			config.put("DEFAULTNOTICE","0");//默认通知用户方式:0短信，1邮件，2邮件和短信
			config.put("Email_adds","15885042360@163.com");//	163邮箱发送账号
			config.put("Email_password","h3235055");//	163邮箱发送密码
			config.put("Phone_username","h3235055");//	发送手机短信账号
			config.put("Phone_password","geagag11515gag2t2gaegaga");//	发送手机短信密码
			config.put("ExamUserTitle","用户审核成功邮件标题");//用户审核成功邮件标题
			config.put("ExamUserContent","用户审核成功邮件内容");//用户审核成功邮件内容
			config.put("UpdateUserTitle","跟新用户成功邮件标题"); //用户跟新成功邮件标题
			config.put("UpdateUserContent","跟新用户成功邮件内容"); //用户跟新成功邮件标题
			config.put("UpdatePasswordTitle","用户修改密码邮件标题"); //用户修改密码邮件标题
			config.put("UpdatePasswordContent","用户修改密码邮件内容"); //用户修改密码邮件内容
			config.put("RechargeUserTitle","用户充值邮件标题");//用户充值邮件标题
			config.put("RechargeUserContent","用户充值邮件内容");//用户充值邮件内容
			config.put("UNLOCKTIME","30");//用户充值邮件内容
			config.put("CLIENTIP",ipAdd+":80");//客户端页面ip和端口号
			config.put("OLDPHOTOPATH", "D:/trsphoto/old/");//增加老照片存放位置
			config.put("OLDPHOTOZIPPATH", "D:/trsphoto/old/tmp/");
			
			config.put("PICTUREMINHEIGHT","400");//稿件图片上传最小高度（图片高度不能高度小于此值）单位像素
			config.put("PICTUREMINWIDTH","400");//稿件图片上传最小宽度（图片高度不能宽度小于此值）单位像素
			config.put("PICTUREMAXLENGTH","8192");//稿件图片上传大小（文件大小）最大限制（图片文件大小不能高于此值），单位KB 
			config.put("PICTUREMINLENGTH","0");//稿件图片上传大小（文件大小）最小限制（图片文件大小不能低于此值），单位KB 
			config.put("TEMP_PIC_PATH","D:/trsphoto/temp/");//D:/trsphoto/temp/ 系统临时路径
			//TODO 其他配置项
			SysConfigConstant.config=config;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("初始化方法出错："+e.getMessage());
		}
	}
	
}