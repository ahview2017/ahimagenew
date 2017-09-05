package com.deepai.photo.service.picture;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpDutyUser;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupAssign;
import com.deepai.photo.bean.CpPicGroupProcess;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpProofread;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.json.GsonUtil;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpPicGroupAssignMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPicGroupProcessMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.mapper.OtherMapper;


/**
 * @author guoyanhong
 * 老照片 zip 解压分析入库
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class OldFlowService {
	private Logger logger=Logger.getLogger(OldFlowService.class) ;
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;	
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private CpPicGroupProcessMapper groupProcessMapper;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private OldPictureService oldPictureService;
	@Autowired
	private CpPictureMapper cpPictureMapper;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private CpPicGroupAssignMapper assignMapper;
	
	/**
	 * 新建稿件
	 * @param pics 各单图信息
	 * @param group 组图信息
	 * @param isIpTc 是否为iptc
	 * @param isFlash 是否为flash上传
	 * @throws Exception 
	 */
	public int makePicGroup(List<CpPicture> pics,CpPicGroup group,boolean isIpTc,CpUser user,int siteId,int type,int roleId,Map<String, String> map) throws Exception{
		try {
			
			/* 新建稿件不做限制
			 * String data=JsonUtil.getString(group);
			 * if(group.getGoodsType()!=null&&group.getGoodsType()!=2){
				checkSstvWord(data);
			}*/
			int coverPictureId=0;
			int count=0;
			Set<Integer> set=new HashSet<Integer>();
			for (CpPicture cpPicture : pics) {
				CommonValidation.checkParamBlank(cpPicture.getId()+"", "单图ID");
				if(set.add(cpPicture.getId())){
					count++;
				}
//				if(group.getGoodsType()!=null&&group.getGoodsType()!=2){
				if(cpPicture.getIsCover()!=null&&cpPicture.getIsCover()==1){
					coverPictureId=cpPicture.getId();
				}
//				data=JsonUtil.getString(cpPicture);
					//新建稿件不做限制
//					checkSstvWord(data);
				
			}
			if(coverPictureId==0){//默认第一个为主图
				coverPictureId=pics.get(0).getId();
				pics.get(0).setIsCover(1);						
			}
			group.setCreateTime(new Date());
			group.setCreator(user.getUserName());
			group.setCreatorId(user.getId());
			group.setPictureCount(count);
			group.setCoverPictureId(coverPictureId);//封面图
			group.setSiteId(siteId);
			group.setGroupStatus(type);//状态：0保存，1提交
			group.setSourceId(0);//原始稿件
			group.setDeleteFlag(CommonConstant.BYTE0);
			group.setGoodsType(0);//图片来源（即图片类型）分为：0普通图、1活动图、2老照片、3ta图和4fa图五种
			group.setPriceType(0);//默认普通图不特殊定价
			//查询普通图价格
			Integer price=otherMapper.selectGoodsPrice(0);
			price=price==null?80:price;//TODO 没有设置的话默认为80
			group.setPrice(price);//价格为 价格管理中设置的普通图价格
			//记录组图基本信息
			cpPicGroupMapper.insertSelective(group);
			if(type==1){//提交，自动分配
				submitGruop(siteId, group.getId(), user,roleId, group.getLangType());
			}else{
				addFlowLog(group.getId(), -1, null, null, user);
			}
			for (int i=0;i<pics.size();i++) {
				CpPicture cpPicture=pics.get(i);
				cpPicture.setSiteId(siteId);
				//单图处理:大、中、水印图、IpTc信息
				cpPicture=oldPictureService.loadSinglePicInfo(cpPicture,isIpTc,siteId, map);
				cpPicture.setUploader(user.getUserName());
				cpPicture.setEditor(user.getUserName());
				cpPicture.setGroupId(group.getId());
//				cpPicture.setSortId(i);
				cpPicture.setCreateTime(new Date());
				cpPicture.setDeleteFlag(CommonConstant.BYTE0);
				cpPictureMapper.updateByPrimaryKeySelective(cpPicture);
			}
			return 1;
		} catch (Exception e) {
			logger.error("新建稿件异常......", e);
			throw e;
		}
		
	}
	
	/**
	 * 新建提交稿件：自动分配给一级校审人员
	 * @param siteId
	 * @param groupId
	 * @param user
	 */
	public void submitGruop(int siteId,int groupId,CpUser user,int roleId,Integer langType)throws Exception{
		try {
			//获取当前校审配置信息
			String key="pfd-"+siteId;
			String value=redisClientTemplate.get(key);
			CpProofread pd=null;
			int pdId=-1;
			if(StringUtil.isNotEmpty(value)){
				pd=(CpProofread) GsonUtil.fromJson(value, CpProofread.class);
			}
			String memo="使用校审配置proofreadId=";
			CpPicGroup group=new CpPicGroup();
			group.setId(groupId);
			group.setGroupStatus(1);//待一审
			group.setApplyTime(new Date());
			//分配记录
			CpPicGroupAssign assign=new CpPicGroupAssign();
			if(roleId==2){//值班编辑的稿子分配给自己
				group.setFristPfdUser(user.getUserName());
				memo="约稿自动分配给自己";
				assign.setType(0);
			}else{
				assign.setType(1);
				if(pd!=null){
					memo=memo+pd.getId();
					pdId=pd.getId();
					//值班人员
					List<CpDutyUser> dutyUser=pd.getDutys();
//					logger.info("dutyUser>>>>"+JsonUtil.getString(dutyUser));
					//一审人员，自动分配，只有一个
					List<CpDutyUser> fristUser=new ArrayList<CpDutyUser>();
					for (CpDutyUser duty : dutyUser) {
						if(duty.getType()==1){
							fristUser.add(duty);
						}
					}
//					logger.info("fristUser>>>>"+JsonUtil.getString(fristUser));
					String lastUser=otherMapper.selectAssignUser();//最后一个自动分配的值班人员
					boolean flag=false;
					if(lastUser!=null){
						for (int i=0;i<fristUser.size();i++) {
							CpDutyUser frist= fristUser.get(i);
							if(frist.getUserName().equals(lastUser)){
								i=i==fristUser.size()-1?0:i+1;
								group.setFristPfdUser(fristUser.get(i).getUserName());//分配给后一个人
								flag=true;
								break;
							}
						}
					}else{
						group.setFristPfdUser(fristUser.get(0).getUserName());//分配给第一个人
					}
					if(!flag){
						group.setFristPfdUser(fristUser.get(0).getUserName());//分配给第一人
					}
					/*logger.info("dutyUser>>>>"+JsonUtil.getString(dutyUser));
					logger.info("fristUser>>>>"+JsonUtil.getString(fristUser));
					logger.info("FristPfdUser>>>>"+group.getFristPfdUser());
					logger.info("lastUser="+lastUser);
					logger.info("fristUser="+fristUser);
					logger.info("setFristPfdUser="+group.getFristPfdUser());*/
				}else{
					memo="当前无可用校审配置，默认自动分配给有一审权限的用户";
					//查询有一审权限的所有人，并自动分配
					List<Map<String,Object>> fristUser=aboutPictureMapper.selectUserByRight(131, langType);//根据权限id，查询拥有此权限的用户
					/*int order=groupId % fristUser.size();
					group.setFristPfdUser(fristUser.get(order).get("USER_NAME").toString());*/
					String lastUser=otherMapper.selectAssignUser();//最后一个自动分配的值班人员
					boolean flag=false;
					if(lastUser!=null){
						for (int i=0;i<fristUser.size();i++) {
							Map<String,Object> frist= fristUser.get(i);
							if(frist.get("USER_NAME").toString().equals(lastUser)){
								i=i==fristUser.size()-1?0:i+1;
								group.setFristPfdUser(fristUser.get(i).get("USER_NAME").toString());//分配给后一个人
								flag=true;
								break;
							}
						}
					}
					if(!flag){
						group.setFristPfdUser(fristUser.get(0).get("USER_NAME").toString());//分配给第一人
					}
					/*logger.info("lastUser="+lastUser);
					logger.info("fristUser="+fristUser);
					logger.info("setFristPfdUser="+group.getFristPfdUser());*/
				}
			}
			group.setUpdateTime(new Date());
			group.setUpdateUser(null);
			cpPicGroupMapper.updateByPrimaryKeySelective(group);
			//分配记录
			assign.setGroupId(groupId);
			assign.setUserName(group.getFristPfdUser());
			assign.setCreateTime(new Date());
			assignMapper.insertSelective(assign);
			addFlowLog(group.getId(), 0, memo, pdId, user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	
	
	/**
	 * 记录稿件流程日志
	 * @param groupId 稿件id
	 * @param type 操作类型：-1保存稿件，0发稿提交; 1级审提交，2二审提交；3三审签发，4补签，5内部留资，6撤稿，7上架，8删除，9推送，10编辑，11一审退稿，12二审退稿，13三审退稿，14取回，15稿件合并，16强制解锁，17彻底删除，18恢复
	 * @param memo 备注(可为null)
	 * @param proofreadId 校审配置ID(可为null)
	 * @param user 操作人
	 * @throws Exception
	 */
	public void addFlowLog(int groupId,int type,String memo,Integer proofreadId,CpUser user)throws Exception{
		CpPicGroupProcess pro=new CpPicGroupProcess();
		pro.setFlowType(type);
		pro.setOperateTime(new Date());
		pro.setOperateUserId(user.getId());
		pro.setOperateUsername(user.getUserName());
		pro.setPicgroupId(groupId);
		if(memo!=null){
			pro.setOperateMemo(memo);
		}
		if(proofreadId!=null){
			pro.setProofreadId(proofreadId);
		}
		groupProcessMapper.insertSelective(pro);
	}
}
