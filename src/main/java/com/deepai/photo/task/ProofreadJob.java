package com.deepai.photo.task;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deepai.photo.bean.CpMassSMSRecord;
import com.deepai.photo.bean.CpMsgTimer;
import com.deepai.photo.common.PropertiesFileUtil;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.mapper.CpFlowMapper;
import com.deepai.photo.mapper.CpMassSMSRecordMapper;
import com.deepai.photo.mapper.CpMsgTimerMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.phonemsg.PhoneMSGService;
import com.deepai.photo.service.picture.DownloadService;
import com.deepai.photo.service.picture.FlowService;
@Service
public class ProofreadJob {  
	private Logger log=Logger.getLogger(ProofreadJob.class);
	@Autowired
	private CpFlowMapper cpFlowMapper;
	@Autowired
	private FlowService flowService;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private CpMsgTimerMapper cpMsgTimerMapper;
	@Autowired
	private CpMassSMSRecordMapper cpMassSMSRecordMapper;
    @Autowired
    private PhoneMSGService phoneMSGService;
    
    
	
    /**
     * 自动将过期校审配置设置为不启动状态
     */
	@Scheduled(cron="0 0 0 * * ?")   //每日凌晨执行   
//	@Scheduled(cron="*/3 * * * * ?")  
    public void changeUnEnabled() {  
    	try {
			cpFlowMapper.changeUnEnabled();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("定时任务出错："+e.getMessage());
		}
    }  
	
	/**
	 * 每天凌晨同步当前校审配置
	 */
	@Scheduled(cron="0 0 0 * * ?")   //每日凌晨执行   
	public void pfdToRedis() {  
		try {
			flowService.getDayProofreadToRedis(null);
		} catch (Exception e) {
			log.error("定时任务出错："+e.getMessage());
		}
	}  
	
	 /**
     * 每天重置序列从1开始
     */
	@Scheduled(cron="0 0 0 * * ?")   //每日凌晨执行   
    public void resetSequence() {  
    	try {
    		otherMapper.resetSequence();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("定时任务出错："+e.getMessage());
		}
    }  
	
	/**
	 * 每天0点30分将下载类型限制是每天的用户，下载限制内数量重置为0
	 */
	@Scheduled(cron="0 30 0 * * ?")   //每日凌晨执行   
	public void putZeroEveryDay() {  
		try {
			downloadService.putZeroByType(0);
		} catch (Exception e) {
			log.error("定时任务出错："+e.getMessage());
		}
	}  
	/**
	 * 每月1号1点执行，将下载类型限制是每月的用户，下载限制内数量重置为0
	 */
	@Scheduled(cron="0 0 1 1 * ?")    
	public void putZeroEveryMonth() {  
		try {
			downloadService.putZeroByType(1);
		} catch (Exception e) {
			log.error("定时任务出错："+e.getMessage());
		}
	}  
	/**
	 * 每年1月1号1点30执行，将下载类型限制是每年的用户，下载限制内数量重置为0
	 */
	@Scheduled(cron="0 30 1 1 1 ?")    
	public void putZeroEveryYear() {  
		try {
			downloadService.putZeroByType(2);
		} catch (Exception e) {
			log.error("定时任务出错："+e.getMessage());
		}
	}  
	
	
	/**
	 * 定时推送新华社稿件
	 * 每两个小时跑一次
	 */
//	@Scheduled(cron="0 0 0/2 * * ?") 
	@Scheduled(cron="0 0/5 * * * ?") 
	public void pushXHPhoto() {  
		try {
			//add by xiayunan@20180308 添加推送新华社图片标识
			PropertiesFileUtil fileUtil=new PropertiesFileUtil();
			File file = new File("/opt/conf/ip.properties");
			if(!file.exists()){
				log.error("文件/opt/conf/ip.properties不存在");
				throw new Exception("文件/opt/conf/ip.properties不存在");
			}
			boolean xinhuaflag=Boolean.valueOf(fileUtil.QueryServerValue("/opt/conf/ip.properties", "xinhuaflag"));
			log.info("<<<xinhuaflag:"+xinhuaflag);
			if(xinhuaflag){
				log.info("开始推送新华社稿件");
				String result = "";
				String serverIp=String.valueOf(fileUtil.QueryValue("/ip.properties", "serverIp"));
				if(StringUtil.isNotBlank(serverIp)){
					HttpGet request = new HttpGet("http://"+serverIp+"/photo/xhDataMigrationCtro/dataMigration.do");
					HttpResponse response = HttpClients.createDefault().execute(request);
					if(response.getStatusLine().getStatusCode()==200){
						result = EntityUtils.toString(response.getEntity());
					}
					log.info("<<<result:"+result);
				}
				
			}
		} catch (Exception e) {
			log.error("定时任务出错："+e.getMessage());
		}
	}  
	
	/**
	 * 定时扫描短信群发,执行定时任务
	 * 每两个小时跑一次
	 */
//	@Scheduled(cron="0 0 0/2 * * ?") 
	@Scheduled(cron="0 0/1 * * * ?") 
	public void sendMassSMS() {  
		log.info("扫描短信群发定时任务开始！！！");
		List<CpMsgTimer> lists = null;
		Set<String> phoneNumSet = new HashSet<String>();
		try {
			lists =  cpMsgTimerMapper.selectMassSMSTasks();
			if(lists.size()>0){
				for(CpMsgTimer iTimer:lists){
					if(iTimer!=null){
						int massSmsId = iTimer.getClassId();//群发短信记录ID
						CpMassSMSRecord cpMassSMSRecord =  cpMassSMSRecordMapper.selectMassSMSById(massSmsId);
						if(cpMassSMSRecord!=null&&cpMassSMSRecord.getStatus()==1){//稿件审核状态为审核通过开始发售短信
							 Date executeTime = iTimer.getExecuteTime();
							 //当前时间和执行时间相等，执行定时任务
							 if(executeTime!=null&&(System.currentTimeMillis()-executeTime.getTime())/(1000*60)==0){
								 phoneNumSet = phoneMSGService.getPhoneNumSet(massSmsId);
								 if(phoneNumSet.size()==0){
					         		 throw new Exception("接受对象的手机号集合为空，请核对后再发生");
					         	 } 
								 String massSmsContent = cpMassSMSRecord.getMsgContent();
					         	 if(StringUtil.isBlank(massSmsContent)||massSmsContent.length()>60){
					         		 throw new Exception("短信内容为空或总字数超过60，请核对后再进行发送！");
					         	 }
					         	 boolean isSuccess = phoneMSGService.sendMassSMS(massSmsId,phoneNumSet);
					         	 if(isSuccess){
					         		 log.info("短信发送成功！！！");
					         		 CpMsgTimer cpMsgTimer = new CpMsgTimer();
					         		 cpMsgTimer.setId(iTimer.getId());
					         		 cpMsgTimer.setStatus(1);
					         		 log.info("开始更新定时任务执行状态！");
					         		 cpMsgTimerMapper.updateByPrimaryKey(cpMsgTimer);
					         		 log.info("更新定时任务执行状态成功！");
					         		 //短信发送成功更新定时任务状态为已执行;
					         	 }else{
					         		 log.info("短信发送失败，请检查短信内容是否含有特殊字符或者字数超出限制！！！");
					         	 }
							 }
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long l1 = sdf.parse("2018-05-29 15:06:23").getTime();
			long l2= sdf.parse("2018-05-29 10:49:23").getTime();
			
			System.out.println("l1:"+l1);
			System.out.println((System.currentTimeMillis()-l1)/(1000*60));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 