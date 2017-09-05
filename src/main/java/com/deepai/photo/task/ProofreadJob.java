package com.deepai.photo.task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deepai.photo.mapper.CpFlowMapper;
import com.deepai.photo.mapper.OtherMapper;
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
} 