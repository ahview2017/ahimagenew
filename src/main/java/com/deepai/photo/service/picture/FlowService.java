package com.deepai.photo.service.picture;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpDutyUser;
import com.deepai.photo.bean.CpDutyUserExample;
import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpLanmuPicture;
import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicAllpathExample;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupAssign;
import com.deepai.photo.bean.CpPicGroupCategory;
import com.deepai.photo.bean.CpPicGroupCategoryExample;
import com.deepai.photo.bean.CpPicGroupColumn;
import com.deepai.photo.bean.CpPicGroupExample;
import com.deepai.photo.bean.CpPicGroupProcess;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureDownloadrecord;
import com.deepai.photo.bean.CpPictureExample;
import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpPicturePriceExample;
import com.deepai.photo.bean.CpProofread;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.json.GsonUtil;
import com.deepai.photo.common.util.json.JSONUtils;
import com.deepai.photo.common.util.json.JsonUtil;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpColumnMapper;
import com.deepai.photo.mapper.CpDutyUserMapper;
import com.deepai.photo.mapper.CpFlowMapper;
import com.deepai.photo.mapper.CpPicGroupAssignMapper;
import com.deepai.photo.mapper.CpPicGroupCategoryMapper;
import com.deepai.photo.mapper.CpPicGroupColumnMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPicGroupProcessMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.mapper.CpProofreadMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author guoyanhong
 * 校审流程
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class FlowService {
	private Logger logger=Logger.getLogger(FlowService.class) ;
	private final String header="pfd-";
	private final String enFooter="En";
	private final String zhFooter="Zh";
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;	
	@Autowired
	private CpProofreadMapper cpProofreadMapper;
	@Autowired
	private CpFlowMapper cpFlowMapper;
	@Autowired
	private CpDutyUserMapper cpDutyUserMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private CpPicGroupProcessMapper groupProcessMapper;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private CpPictureMapper cpPictureMapper;
	@Autowired
	private OtherMapper otherMapper;
	@Autowired
	private CpPicGroupAssignMapper assignMapper;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private CpPicGroupCategoryMapper cpPicGroupCategoryMapper;
	@Autowired
	private CpPicGroupCategoryMapper categoryMapper;
	@Autowired
	private CpPicGroupColumnMapper cpPicGroupColumnMapper;
	@Autowired
	private CpColumnMapper columnMapper;
	/**
	 * 添加校审配置
	 * @param code
	 * @throws Exception 
	 */
	public void addProofread(CpProofread proofread,String firstGrade,String secondGrade,String threeGrade) throws Exception{
		cpProofreadMapper.insertSelective(proofread);
		Map<String,Object> param=new HashMap<String, Object>();
		//配置校审人员
		param.put("proofreadId", proofread.getId());
		//一级校审
		param.put("type", 1);
		assignUser(firstGrade, param);
		//二级校审
		param.put("type", 2);
		assignUser(secondGrade, param);
		//三级校审
		param.put("type", 3);
		assignUser(threeGrade, param);
		//同步redis
		getDayProofreadToRedis(proofread.getSiteId());
	}
	/**
	 * 修改校审配置
	 * @param code
	 * @throws Exception 
	 */
	public void updateProofread(CpProofread proofread,String firstGrade,String secondGrade,String threeGrade) throws Exception{
		try {
			cpProofreadMapper.updateByPrimaryKeySelective(proofread);
			Map<String,Object> param=new HashMap<String, Object>();
			//配置校审人员
			param.put("proofreadId", proofread.getId());
			CpDutyUserExample example=new CpDutyUserExample();
			//一级校审
			example.clear();
			example.createCriteria().andTypeEqualTo(1).andProofreadIdEqualTo(proofread.getId());
			cpDutyUserMapper.deleteByExample(example);
			param.put("type", 1);
			assignUser(firstGrade, param);
			//二级校审
			example.clear();
			example.createCriteria().andTypeEqualTo(2).andProofreadIdEqualTo(proofread.getId());
			cpDutyUserMapper.deleteByExample(example);
			param.put("type", 2);
			assignUser(secondGrade, param);
			//三级校审
			example.clear();
			example.createCriteria().andTypeEqualTo(3).andProofreadIdEqualTo(proofread.getId());
			cpDutyUserMapper.deleteByExample(example);
			param.put("type", 3);
			assignUser(threeGrade, param);
			//同步redis
			getDayProofreadToRedis(proofread.getSiteId());
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * 分配校审级别的值班人员
	 * @param gradeNames 待分配人员
	 * @param grade 级别
	 * @throws Exception 
	 */
	public void assignUser(String gradeNames,Map<String,Object> param) throws Exception{		
		String names[]=gradeNames.split("%");
		param.put("names", names);
		cpFlowMapper.insertDutyUserBatch(param);
	}
	/**
	 * 删除校审配置
	 * @param 
	 */
	public void delPdf(CpUser user,String proofreadIds,int siteId) throws Exception{		
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("username", user.getUserName());
		param.put("updatetime", new Date());
		param.put("ids", proofreadIds.split(",") );
		cpFlowMapper.delProofreadByIds(param);
		getDayProofreadToRedis(siteId);
	}
	

	/**
	 * 将校审配置信息，同步至redis
	 * @throws Exception 
	 */
	public void getDayProofreadToRedis(Integer siteId) throws Exception{
		List<CpProofread> list=cpFlowMapper.selectNowProofread(siteId);
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				CpProofread pd=list.get(i);
				if(0 == pd.getLangType()) {
					redisClientTemplate.set(header+siteId+zhFooter, GsonUtil.toJson(pd));//将siteId作为键，将当前校审配置信息和人员作为值
				} else if(1 == pd.getLangType()){
					redisClientTemplate.set(header+siteId+enFooter, GsonUtil.toJson(pd));//将siteId作为键，将当前校审配置信息和人员作为值
				}
				
			}
		}else{
			redisClientTemplate.del(header+siteId);
		}
		
	}
	/**
	 * 新建稿件
	 * @param request2 
	 * @param pics 各单图信息
	 * @param group 组图信息
	 * @param isIpTc 是否为iptc
	 * @param isFlash 是否为flash上传
	 * @throws Exception 
	 */
	public int makePicGroup(List<CpPicture> pics,CpPicGroup group,boolean isIpTc,CpUser user,int siteId,int type,int roleId) throws Exception{
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
			if(group.getVideoId()!=null&&group.getVideoId()!=0){
				group.setMasvideoSign(1);
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
				submitGruop(siteId, group.getId(), user,roleId, group.getLangType(),0);
			}else{
				addFlowLog(group.getId(), -1, null, null, user);
			}
			for (int i=0;i<pics.size();i++) {
				CpPicture cpPicture=pics.get(i);
				cpPicture.setSiteId(siteId);
				//单图处理:大、中、水印图、IpTc信息
				cpPicture=pictureService.loadSinglePicInfo(cpPicture,isIpTc,siteId);
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
			throw e;
		}
		
	}
	public int makePicGroup(List<CpPicture> pics,CpPicGroup group,boolean isIpTc,CpUser user,int siteId,int type,int roleId,String cateIds) throws Exception{
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
//              if(group.getGoodsType()!=null&&group.getGoodsType()!=2){
                if(cpPicture.getIsCover()!=null&&cpPicture.getIsCover()==1){
                    coverPictureId=cpPicture.getId();
                }
//              data=JsonUtil.getString(cpPicture);
                    //新建稿件不做限制
//                  checkSstvWord(data);
                
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
                submitGruop(siteId, group.getId(), user,roleId, group.getLangType(),0,cateIds);
            }else{
                addFlowLog(group.getId(), -1, null, null, user);
            }
            for (int i=0;i<pics.size();i++) {
                CpPicture cpPicture=pics.get(i);
                cpPicture.setSiteId(siteId);
                //单图处理:大、中、水印图、IpTc信息
                cpPicture=pictureService.loadSinglePicInfo(cpPicture,isIpTc,siteId);
                cpPicture.setUploader(user.getUserName());
                cpPicture.setEditor(user.getUserName());
                cpPicture.setGroupId(group.getId());
//              cpPicture.setSortId(i);
                cpPicture.setCreateTime(new Date());
                cpPicture.setDeleteFlag(CommonConstant.BYTE0);
                cpPictureMapper.updateByPrimaryKeySelective(cpPicture);
            }
            return 1;
        } catch (Exception e) {
            throw e;
        }
        
    }
	/**
	 * 新建稿件
	 * @param request2 
	 * @param pics 各单图信息
	 * @param group 组图信息
	 * @param isIpTc 是否为iptc
	 * @param isFlash 是否为flash上传
	 * @throws Exception 
	 */
	public int makePicGroupForDataExchange(List<CpPicture> pics,CpPicGroup group,boolean isIpTc,CpUser user,int siteId,int type,int roleId) throws Exception{
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
				System.out.println("coverPictureId1:"+coverPictureId);
//				data=JsonUtil.getString(cpPicture);
					//新建稿件不做限制
//					checkSstvWord(data);
				
			}
			if(coverPictureId==0){//默认第一个为主图
				coverPictureId=pics.get(0).getId();
				pics.get(0).setIsCover(1);
				if(pics.size()>1){
					for(int i=1;i<pics.size();i++){
						pics.get(i).setIsCover(0);
					}
				}
				System.out.println("coverPictureId2:"+coverPictureId);
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
				submitGruop(siteId, group.getId(), user,roleId, group.getLangType(),0);
			}else{
				addFlowLog(group.getId(), -1, null, null, user);
			}
			for (int i=0;i<pics.size();i++) {
				CpPicture cpPicture=pics.get(i);
				cpPicture.setSiteId(siteId);
				//单图处理:大、中、水印图、IpTc信息
				cpPicture=pictureService.loadSinglePicInfo(cpPicture,isIpTc,siteId);
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
			throw e;
		}
		
	}
	
	/**
	 * 新建提交稿件：自动分配给一级校审人员
	 * @param siteId
	 * @param groupId
	 * @param user
	 */
	public void submitGruop(int siteId,int groupId,CpUser user,int roleId, Integer langType,Integer sourceSign)throws Exception{
		try {
			//获取当前校审配置信息
			String key="pfd-"+siteId;
			if(langType == 0) {
				key+= "Zh";
			} else if(langType == 1) {
				key+= "En";
			}
			
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
			//定义用户类型 一审1 二审2 三审3
            boolean is1Shen = false;
            boolean is2Shen = false;
            boolean is3Shen = false;
            boolean pdExist = false;
			if((roleId==2 || roleId==63)&&sourceSign==0){//值班编辑的稿子分配给自己
				group.setFristPfdUser(user.getUserName());
				memo="约稿自动分配给自己";
				assign.setType(0);	
				
				//add by liu.jinfeng@2017年9月11日 下午9:52:47 值班编辑的稿件如下处理
                //值班编辑在几审稿件就到几审列表，如果属于多个审核，以最高审为准
                if(pd!=null){
                    pdExist = true;
                  //值班人员。包括一审二审三审
                    List<CpDutyUser> dutyUser=pd.getDutys();
                    
                    for(CpDutyUser duty : dutyUser){
                        //一审 
                        if(duty.getType()==1){
                            is1Shen = true;
                        }
                        //二审
                        if(duty.getType()==2&&duty.getUserName().equals(user.getUserName())){
                            is2Shen = true;
                        }
                        //三审
                        if(duty.getType()==3&&duty.getUserName().equals(user.getUserName())){
                            is3Shen = true;
                        }
                    }
                }
                
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
					List<Map<String,Object>> fristUser=aboutPictureMapper.selectUserByRight(131, group.getLangType());//根据权限id，查询拥有此权限的用户
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
			group.setCreatorId(user.getId());
			cpPicGroupMapper.updateByPrimaryKeySelective(group);
			//分配记录
			assign.setGroupId(groupId);
			assign.setUserName(group.getFristPfdUser());
			assign.setCreateTime(new Date());
			assignMapper.insertSelective(assign);
			addFlowLog(group.getId(), 0, memo, pdId, user);
			
			// 如果是3审直接进3审稿件列表
            if (is3Shen) {
                examByProofread(group, user, 2);
                group.setGroupStatus(3);// 待3审
            } else if (!is3Shen && is2Shen) {
                examByProofread(group, user, 1);
                group.setGroupStatus(2);// 待2审
            } 
		} catch (Exception e) {
			throw e;
		}
	}
	public void submitGruop(int siteId,int groupId,CpUser user,int roleId, Integer langType,Integer sourceSign,String cateIds)throws Exception{
        try {
            //获取当前校审配置信息
            String key="pfd-"+siteId;
            if(langType == 0) {
                key+= "Zh";
            } else if(langType == 1) {
                key+= "En";
            }
            
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
            //定义用户类型 一审1 二审2 三审3
            boolean is1Shen = false;
            boolean is2Shen = false;
            boolean is3Shen = false;
            boolean pdExist = false;
            if((roleId==2 || roleId==63)&&sourceSign==0){//值班编辑的稿子分配给自己
                group.setFristPfdUser(user.getUserName());
                memo="约稿自动分配给自己";
                assign.setType(0);  
                
                //add by liu.jinfeng@2017年9月11日 下午9:52:47 值班编辑的稿件如下处理
                //值班编辑在几审稿件就到几审列表，如果属于多个审核，以最高审为准
                if(pd!=null){
                    pdExist = true;
                  //值班人员。包括一审二审三审
                    List<CpDutyUser> dutyUser=pd.getDutys();
                    
                    for(CpDutyUser duty : dutyUser){
                        //一审 
                        if(duty.getType()==1){
                            is1Shen = true;
                        }
                        //二审
                        if(duty.getType()==2&&duty.getUserName().equals(user.getUserName())){
                            is2Shen = true;
                        }
                        //三审
                        if(duty.getType()==3&&duty.getUserName().equals(user.getUserName())){
                            is3Shen = true;
                        }
                    }
                }
                
                
            }

            if((!is1Shen&&!is2Shen&&!is3Shen)||!pdExist){
                assign.setType(1);
                if(pd!=null){
                    memo=memo+pd.getId();
                    pdId=pd.getId();
                    //值班人员
                    List<CpDutyUser> dutyUser=pd.getDutys();
//                  logger.info("dutyUser>>>>"+JsonUtil.getString(dutyUser));
                    //一审人员，自动分配，只有一个
                    List<CpDutyUser> fristUser=new ArrayList<CpDutyUser>();
                    for (CpDutyUser duty : dutyUser) {
                        if(duty.getType()==1){
                            fristUser.add(duty);
                        }
                    }
//                  logger.info("fristUser>>>>"+JsonUtil.getString(fristUser));
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
                    List<Map<String,Object>> fristUser=aboutPictureMapper.selectUserByRight(131, group.getLangType());//根据权限id，查询拥有此权限的用户
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
            group.setCreatorId(user.getId());
            cpPicGroupMapper.updateByPrimaryKeySelective(group);
            //分配记录
            assign.setGroupId(groupId);
            assign.setUserName(group.getFristPfdUser());
            assign.setCreateTime(new Date());
            assignMapper.insertSelective(assign);
            addFlowLog(group.getId(), 0, memo, pdId, user);
            

            // 如果是3审直接进3审稿件列表
            if (is3Shen) {
                examByProofread(group, user, 2);
                group.setGroupStatus(3);// 待3审
            } else if (!is3Shen && is2Shen) {
                examByProofread(group, user, 1);
                group.setGroupStatus(2);// 待2审
            } 
            
            //add by liu.jinfeng@20170912
            addCategoryForGroup(groupId, cateIds);
        } catch (Exception e) {
            throw e;
        }
    }
	/**
     * 值班编辑提交稿件处理逻辑
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月12日 上午9:18:25
     * @param oldGroup
     * @param user
     * @param type
     * @param cates
     * @throws Exception
     */
    private void examByProofread(CpPicGroup oldGroup,CpUser user,int type)throws Exception{
        //TODO 校验稿件状态与校审用户级别
        CpPicGroup group=new CpPicGroup();
        group.setId(oldGroup.getId());
        group.setGroupStatus(type+1);//待下级审核
        group.setUpdateTime(new Date());
        group.setUpdateUser(user.getUserName());
        if(type == 2){
            group.setFristPfdUser(oldGroup.getFristPfdUser() + "、" + user.getUserName());
        }
        //修改稿件状态
        cpPicGroupMapper.updateByPrimaryKeySelective(group);
        //记录流程日志
        addFlowLog(oldGroup.getId(), type, null, null, user);
    }
	/**
	 * 编辑稿件
	 * @param oldGroup 编辑前
	 * @param newGroup 编辑后
	 * @param pics 编辑后图片
	 * @param type2 
	 * @throws Exception
	 */
	public String editGroup(CpPicGroup oldGroup,CpPicGroup newGroup,List<CpPicture> pics,CpUser user,String cateIds, Integer type2) throws Exception{
		try {
			String data=JsonUtil.getString(newGroup);
			String res=checkSstvWordAndRes(data);
			int sourceGroupId=oldGroup.getId();
			int coverPictureId=0;
//			int coverPictureId=oldGroup.getCoverPictureId();
			int count=0;
			Set<Integer> set=new HashSet<Integer>();
			for (CpPicture cpPicture : pics) {
				CommonValidation.checkParamBlank(cpPicture.getId()+"", "单图ID");
				if(set.add(cpPicture.getId())){
					count++;
				}
//				CommonValidation.checkParamBlank(cpPicture.getPeople(), "单图人物");
//				CommonValidation.checkParamBlank(cpPicture.getKeywords(), "单图关键词");
				if(cpPicture.getIsCover()!=null&&cpPicture.getIsCover()==1){
					coverPictureId=cpPicture.getId();
				}
				cpPicture.setGroupId(sourceGroupId);
				data=JsonUtil.getString(cpPicture);
				String res1=checkSstvWordAndRes(data);
				if(res==null&&res1!=null){
					res=res1;
				}else if(res!=null&&res1!=null){
					res=res+"、"+res1;
				}
			}
			if(coverPictureId==0){//默认第一个为主图
				coverPictureId=pics.get(0).getId();
				pics.get(0).setIsCover(1);						
			}
			int delCount=oldGroup.getPictureCount()==null?0:oldGroup.getPictureCount()-count;
			if(StringUtil.isNotBlank(cateIds)){
				addCategoryForGroup(sourceGroupId, cateIds);
			}else{
				cpFlowMapper.delGroupCategory(sourceGroupId);
			}
			newGroup.setPictureCount(count);
			newGroup.setPictureDeletedCount(delCount);
			newGroup.setCoverPictureId(coverPictureId);//封面图
			newGroup.setUpdateTime(new Date());
			newGroup.setUpdateUser(user.getUserName());

			//保存历史版本:稿件，及图片
			addHistory(oldGroup, newGroup, type2);

			for (int i=0;i<pics.size();i++) {
				CpPicture cpPicture=pics.get(i);
				//单图处理:大、中、水印图、IpTc信息
				cpPicture=pictureService.loadSinglePicInfo(cpPicture,true,oldGroup.getSiteId());
				//处理水印中图
				cpPicture.setEditor(user.getUserName());
//				cpPicture.setSortId(i);
				cpPicture.setDeleteFlag(CommonConstant.BYTE0);
				cpPictureMapper.updateByPrimaryKeySelective(cpPicture);
			}
			//修改图片txt
			CpPicGroup pinfo=aboutPictureMapper.selectPicInfoByGroupId(sourceGroupId);
			String txtPicInfo = null;//txt文件名
			String oriPicPath = null;//原图
			for (int j = 0; j < pinfo.getPics().size(); j++) {
				CpPicture picture=pinfo.getPics().get(j);
				oriPicPath= picture.getOriAllPath();
				txtPicInfo=oriPicPath.substring(0, oriPicPath.lastIndexOf(".")) + ".txt";
				downloadService.createPictureInfoTxt(pinfo, picture, txtPicInfo);
			}
			addFlowLog(sourceGroupId, 10, null, null, user);
			return res;
		} catch (Exception e) {
			logger.error("编辑稿件出错");
			throw e;
		}
	}
	
	/** 保存历史版本
	 * @param oldGroup
	 * @param newGroup
	 * @param type2 
	 * @throws Exception
	 */
	public void addHistory(CpPicGroup oldGroup,CpPicGroup newGroup, Integer type2) throws Exception{
		int sourceGroupId=oldGroup.getId();
		//保存历史版本:稿件，及图片
		oldGroup.setId(null);
		oldGroup.setSourceId(sourceGroupId);
		//复制稿件生成历史版本，新生成的一条数据，id为新的
		String updateUser = newGroup.getUpdateUser();
		if(type2 == 1){
			updateUser="(强制解锁)"+updateUser;		
		}
		newGroup.setUpdateUser(updateUser);
		cpPicGroupMapper.insert(oldGroup);
		int copyGId=oldGroup.getId();
		//复制原稿的图片，并指向复制出的稿件
		aboutPictureMapper.copyPicsByGroupId(sourceGroupId,copyGId);
		//将原稿的所有图片deleteFlag置为1
		aboutPictureMapper.delPicsByGroupId(sourceGroupId);
		newGroup.setIsLocked(0);
		newGroup.setLockerName("");
		
		//保存修改信息
		cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
	}
	
	/**
	 * 修改稿件分类
	 * @param groupId 稿件id
	 * @param cateIds 分类ids,逗号隔开
	 * @param type 0稿件分类（cp_categroy）,1签发分类
	 * @throws Exception 
	 */
	public void addCategoryForGroup(int groupId,String cateIds) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("cateIds", cateIds.split(","));
		//map.put("type", type);
		//删除已有分类
		cpFlowMapper.delGroupCategory(groupId);
		//添加分类
		cpFlowMapper.insertGroupCategroy(map);
	}
	/**
	 * 修改稿件分类
	 * @param groupId 稿件id
	 * @param cates  签发类型多选，格式：[{type=0,signId:1,position:null},{type=0,signId:2,position:1},{type=1,topicId:2}]，
	 * 		      其中type=0为签发类型，1为专题id，signId为签发类型，position为位置，topicId=专题id
	 * @throws Exception 
	 */
	public void addCategoryForGroup(int groupId,List<Map<String,Object>> cates) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("cates", cates);
		boolean isCate=false;//签发分类
		boolean isTopic=false;//签发专题
		for(Map<String,Object> m : cates){
			if(m.get("type").toString().equals("0")){
				isCate=true;
				//如果签发选择位置，则将之前相应位置的稿件位置设为空
				if(m.containsKey("position")&&m.get("position")!=null&&StringUtil.isNotBlank(m.get("position").toString())){
					cpFlowMapper.updateCateBleak(m);
				}else{
					m.put("position", null);
				}
			}
			if(m.get("type").toString().equals("1")){
				isTopic=true;
			}
			
		}
		//map.put("type", type);
		if(isCate){
			//删除已有签发分类
			//2017-07-21 del
			//cpFlowMapper.delGroupCategory(groupId);
			//添加签发分类
			cpFlowMapper.insertGroupCategroy1(map);
		}
		if(isTopic){
			//添加专题稿件
			cpFlowMapper.insertGroupTopic(map);
		}
		
	}
	
	/**
	 * 一二三级校审提交
	 * @param oldGroup 提交前稿件
	 * @param user 审核用户
	 * @param type 审核级别：1一级，2二级，3三级
	 * @param signIds 签发类型ids
	 */
	public void examByProofread(CpPicGroup oldGroup,CpUser user,int type,List<Map<String,Object>> cates)throws Exception{
		//TODO 校验稿件状态与校审用户级别
		checkUser(oldGroup, user, type, oldGroup.getSiteId());
		CpPicGroup group=new CpPicGroup();
		group.setId(oldGroup.getId());
		if(oldGroup.getVideoId()!=null&&oldGroup.getVideoId()!=0){
			group.setMasvideoSign(1);
			group.setVideoId(oldGroup.getVideoId());
		}
		group.setGroupStatus(type+1);//待下级审核
		group.setUpdateTime(new Date());
		group.setUpdateUser(user.getUserName());
		if(type == 2){
			group.setFristPfdUser(oldGroup.getFristPfdUser() + "、" + user.getUserName());
		}
		if(type==3){
			group.setSginTime(new Date());//发布时间
			//签发
			addCategoryForGroup(oldGroup.getId(), cates);
		}
		
		
		//修改稿件状态
		cpPicGroupMapper.updateByPrimaryKeySelective(group);
		if(type==3){
			//生成图片的TXT
			CpPicGroup pinfo=aboutPictureMapper.selectPicInfoByGroupId(oldGroup.getId());
//			String fileName = null;//图片文件名
			String txtPicInfo = null;//txt文件名
			String oriPicPath = null;//原图
			for (int j = 0; j < pinfo.getPics().size(); j++) {
				CpPicture picture=pinfo.getPics().get(j);
				/*fileName = group.getPics().get(j).getFilename();
				txtPicInfo = fileName.substring(0, fileName.lastIndexOf(".")) + ".txt";*/
				oriPicPath= picture.getOriAllPath();
				txtPicInfo=oriPicPath.substring(0, oriPicPath.lastIndexOf(".")) + ".txt";
				downloadService.createPictureInfoTxt(pinfo, picture, txtPicInfo);
			}
		}
		//记录流程日志
		addFlowLog(oldGroup.getId(), type, null, null, user);
	}
	
	
	/**
	 * 老照片专用三审提交
	 * @param oldGroup 提交前稿件
	 * @param user 审核用户
	 * @param type 审核级别：1一级，2二级，3三级
	 * @param signIds 签发类型ids
	 */
	public void examByProofreadForHistory(CpPicGroup oldGroup,CpUser user,int type,List<Map<String,Object>> cates)throws Exception{
		//TODO 校验稿件状态与校审用户级别
		checkUser(oldGroup, user, type, oldGroup.getSiteId());
		CpPicGroup group=new CpPicGroup();
		group.setId(oldGroup.getId());
		if(oldGroup.getVideoId()!=null&&oldGroup.getVideoId()!=0){
			group.setMasvideoSign(1);
			group.setVideoId(oldGroup.getVideoId());
		}
		group.setGroupStatus(type+1);//待下级审核
		group.setUpdateTime(new Date());
		group.setUpdateUser(user.getUserName());
		if(type == 2){
			group.setFristPfdUser(oldGroup.getFristPfdUser() + "、" + user.getUserName());
		}
		if(type==3){
			group.setSginTime(new Date());//发布时间
			//签发
			addCategoryForGroup(oldGroup.getId(), cates);
		}
		group.setProperties((byte)2);
		
		System.out.println("Properties:"+group.getProperties());
		//修改稿件状态
		cpPicGroupMapper.updateByPrimaryKeySelective(group);
		if(type==3){
			//生成图片的TXT
			CpPicGroup pinfo=aboutPictureMapper.selectPicInfoByGroupId(oldGroup.getId());
//			String fileName = null;//图片文件名
			String txtPicInfo = null;//txt文件名
			String oriPicPath = null;//原图
			for (int j = 0; j < pinfo.getPics().size(); j++) {
				CpPicture picture=pinfo.getPics().get(j);
				/*fileName = group.getPics().get(j).getFilename();
				txtPicInfo = fileName.substring(0, fileName.lastIndexOf(".")) + ".txt";*/
				oriPicPath= picture.getOriAllPath();
				txtPicInfo=oriPicPath.substring(0, oriPicPath.lastIndexOf(".")) + ".txt";
				downloadService.createPictureInfoTxt(pinfo, picture, txtPicInfo);
			}
		}
		//记录流程日志
		addFlowLog(oldGroup.getId(), type, null, null, user);
	}
	
	
	
	/**
	 * 补签稿件
	 * @param oldGroup 提交前稿件
	 * @param user 审核用户
	 * @param type 审核级别：1一级，2二级，3三级
	 * @param signIds 签发类型ids
	 */
	public void signAgainGroup(CpPicGroup oldGroup,CpUser user,List<Map<String,Object>> cates)throws Exception{
		//补签
		addCategoryForGroup(oldGroup.getId(), cates);
		//记录流程日志
		addFlowLog(oldGroup.getId(), 4, null, null, user);
	}
	
	/**
	 * 记录稿件流程日志
	 * @param groupId 稿件id
	 * @param type 操作类型：-1保存稿件，0发稿提交; 1级审提交，2二审提交；3三审签发，4补签，5内部留资，6撤稿，7上架，8删除，9推送，10编辑，11一审退稿，12二审退稿，13三审退稿，14取回，15稿件合并，16强制解锁，17彻底删除，18恢复,19签报
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
	/**
	 * 批量记录稿件流程日志
	 * @param groupIds 稿件ids
	 * @param type 操作类型：-1保存稿件，0发稿提交; 1级审提交，2二审提交；3三审签发，4补签，5内部留资，6撤稿，7上架，8删除，9推送，10编辑，11一审退稿，12二审退稿，13三审退稿，14取回，15稿件合并，16强制解锁，17彻底删除，18恢复
	 * @param user 操作人
	 * @throws Exception
	 */
	public void addFlowLogBatch(String[] groupIds,int type,CpUser user)throws Exception{
		List<CpPicGroupProcess> logs=new ArrayList<CpPicGroupProcess>();
		for (int i = 0; i < groupIds.length; i++) {
			CpPicGroupProcess pro=new CpPicGroupProcess();
			pro.setFlowType(type);
			pro.setOperateTime(new Date());
			pro.setOperateUserId(user.getId());
			pro.setOperateUsername(user.getUserName());
			pro.setPicgroupId(Integer.valueOf(groupIds[i]));
			logs.add(pro);
		}
		if(logs.size()>0){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("logs", logs);
			cpFlowMapper.insertFlowLogBatch(map);
		}
		
	}
	
	/**
	 * 编辑稿件
	 * @param picData 图片详情
	 * @param group 稿件详情
	 * @param user 操作用户
	 * @param fTime 拍摄时间
	 * @param siteId 站点id
	 * @param type 0作者编辑groupStatus=0||7的稿子，1一审编辑groupStatus=1的稿子，2二审编辑groupStatus=2的稿子，3三审编辑groupStatus=3的稿子
	 * @param cateIds 分类id
	 * @param type2 
	 * @throws Exception
	 */
	public String checkAndEditGroup(String picData,CpPicGroup group,CpUser user,String fTime,int siteId,int type,String cateIds, Integer type2)throws Exception{
		CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(group.getId());
		group.setUpdateUser(user.getUserName());//最后修改人
		//校验稿件是否正在编辑
		CommonValidation.checkGroupSaveEdit(oldGroup,group.getId(),user);
		//TODO 校验稿件状态与校审用户级别
		checkUser(oldGroup, user, type, siteId);
		if(StringUtil.isBlank(picData)){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NOFILEERRORMSG);
		}
		Gson gson = new Gson();  
		List<CpPicture> pics = gson.fromJson(picData, new TypeToken<List<CpPicture>>(){}.getType());
		if(pics==null||pics.size()==0){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.NOFILEERRORMSG);
		}
		if(StringUtil.isNotBlank(fTime)){
			group.setFileTime(DateUtils.sdfLong.parse(fTime));
		}
		//编辑稿件
		return editGroup(oldGroup, group, pics, user,cateIds,type2);
	}
	/**
	 * 校验稿件状态与校审用户级别
	 * @param oldGroup
	 * @param type
	 * @throws Exception 
	 */
	public void checkUser(CpPicGroup oldGroup,CpUser user,int type,int siteId) throws Exception{
		//校验用户与稿件状态
		switch (type) {
			case 0:
				if((oldGroup.getGroupStatus()==0||oldGroup.getGroupStatus()==7)&&user.getId().equals(oldGroup.getCreatorId())){
					break;
				}else{
					throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.CNTEDITMSG, "草稿箱或被退稿件"));
				}
			case 1:
				if(oldGroup.getGroupStatus()==1){
					if(user.getUserName().equals(oldGroup.getFristPfdUser())){
						break;
					}else{
						throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.CNTEDITMSG, "待一审"));
					}
					
				}else{
					throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.NOTCNTEDITMSG, "待一审","一审"));
				}
			case 2:
				//二审值班
				List<String> dutys=getNowDuty(siteId, 2,oldGroup.getLangType());
				boolean flag=false;
				if(oldGroup.getGroupStatus()==2){
					for (int i = 0; i < dutys.size(); i++) {
						if(user.getUserName().equals(dutys.get(i))){
							flag=true;
						}
					}
				}else{
					throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.NOTCNTEDITMSG, "待二审","二审"));
				}
				if(!flag){
					throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.CNTEDITMSG, "待二审"));
				}
				break;
			case 3:
				//三审值班
				List<String> dutys3=getNowDuty(siteId, 3, oldGroup.getLangType());
				boolean flag3=false;
				if(oldGroup.getGroupStatus()==3){
					for (int i = 0; i < dutys3.size(); i++) {
						if(user.getUserName().equals(dutys3.get(i))){
							flag3=true;
						}
					}
				}else{
					throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.NOTCNTEDITMSG, "待三审","三审"));
				}
				if(!flag3){
					throw new InvalidHttpArgumentException(CommonConstant.NORIGHT,String.format(CommonConstant.CNTEDITMSG, "待三审"));
				}
				break;
			default:
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,CommonConstant.PARAMERRORMSG);
		}
	}
	/**
	 * 获取当天值班人员
	 * @param siteId
	 * @param type 1一审值班，2二审值班，3审值班
	 * @return
	 * @throws Exception 
	 */
	public List<String> getNowDuty(int siteId,int type,Integer langType) throws Exception{
		List<String> uNames=new ArrayList<String>();
		//获取当前校审配置信息
		String key=header+siteId;
		String value=redisClientTemplate.get(key);
		CpProofread pd=null;
		if(StringUtil.isNotEmpty(value)){
			pd=(CpProofread) GsonUtil.fromJson(value, CpProofread.class);
			for (CpDutyUser duty: pd.getDutys() ) {
				if(duty.getType()==type){
					uNames.add(duty.getUserName());
				}
			}
		}else{
			int rightId=0;
			switch (type) {
			case 1:
				rightId=131;
				break;
			case 2:
				rightId=136;
				break;
			case 3:
				rightId=141;
				break;
			}
			List<Map<String,Object>> fristUser=aboutPictureMapper.selectUserByRight(rightId, langType);//根据权限id，查询拥有此权限的用户
			for (Map<String,Object> map:fristUser) {
				uNames.add(map.get("USER_NAME").toString());
			}
		}
		return uNames;
	}
	/**
	 * 获取当天值班人员
	 * @param siteId
	 * @param type 1一审值班，2二审值班，3审值班
	 * @return
	 * @throws Exception 
	 */
	public List<Integer> getMyDuty(CpUser user,int siteId,int langType) throws Exception{
		List<Integer> dutys=new ArrayList<Integer>();
		//获取当前校审配置信息
		String key = header;
		if (langType == 0) {
			key += siteId+zhFooter;
		} else if (langType == 1) {
			key += siteId+enFooter;
		}
		String value=redisClientTemplate.get(key);
		CpProofread pd=null;
		if(StringUtil.isNotEmpty(value)){
			pd=(CpProofread) GsonUtil.fromJson(value, CpProofread.class);
			for (CpDutyUser duty: pd.getDutys() ) {
				if(duty.getType()==1&&duty.getUserName().equals(user.getUserName())){
					dutys.add(1);
				}
				if(duty.getType()==2&&duty.getUserName().equals(user.getUserName())){
					dutys.add(2);
				}
				if(duty.getType()==3&&duty.getUserName().equals(user.getUserName())){
					dutys.add(3);
				}
			}
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userId", user.getId());
			if(langType == 0){
				for (int i = 1; i < 4; i++) {
					int rightId=131;
					switch (i) {
					case 1:
						rightId=131;
						break;
					case 2:
						rightId=136;
						break;
					case 3:
						rightId=141;
						break;
					}
					map.put("rightId", rightId);
					if(aboutPictureMapper.isUserRight(map)>0){
						dutys.add(i);
					}
				}
			} else if (langType == 1) {
				for (int i = 1; i < 4; i++) {
					int rightId=345;
					switch (i) {
					case 1:
						rightId=345;
						break;
					case 2:
						rightId=350;
						break;
					case 3:
						rightId=355;
						break;
					}
					map.put("rightId", rightId);
					if(aboutPictureMapper.isUserRight(map)>0){
						dutys.add(i);
					}
				}
			}
			
			
		}
		return dutys;
	}
	
	/**
	 * 改变稿件状态：内部留资稿件
	 * @param groupId 稿件id
	 * @param user 操作人
	 * @param status 稿件状态： 0保存，1提交（待一审），2待二审，3待三审，4已发布（已签发），5内部留资，6撤稿，7被退稿件
	 * @param logType 操作类型：-1保存稿件，0发稿提交; 1级审提交，2二审提交；3三审签发，4补签，5内部留资，6撤稿，7上架，8删除，9推送，10编辑，11一审退稿，12二审退稿，13三审退稿，14取回，15稿件合并，16强制解锁，17彻底删除，18恢复
	 * @param content 备注
	 * @param categoryId 
	 * @param langType 
	 * @throws Exception 
	 */
	public void changeGroupStatus(int groupId,CpUser user,int status,int logType,String content, Integer categoryId, Integer langType) throws Exception{
		try {
			if(langType == 1){
				if(categoryId != null){
					if(categoryId == 0){
						cpPicGroupColumnMapper.updateByGroupIdAndColumnId(groupId, null);
						CpPicGroup newGroup=new CpPicGroup();
						newGroup.setId(groupId);
						newGroup.setUpdateTime(new Date());
						newGroup.setUpdateUser(user.getUserName());
						newGroup.setGroupStatus(status);//撤稿	
						cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
					}else{
						List<CpColumn> groupColumnList = columnMapper.selectByGroupId(groupId,langType);
						if(groupColumnList.size() > 1){
							//修改以签发栏目下的某个稿件的状态为1
							cpPicGroupColumnMapper.updateByGroupIdAndColumnId(groupId, categoryId);
						}else{
							CpPicGroup newGroup=new CpPicGroup();
							newGroup.setId(groupId);
							newGroup.setUpdateTime(new Date());
							newGroup.setUpdateUser(user.getUserName());
							newGroup.setGroupStatus(status);//内部留资	
							cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
						}
					}
					
				}else{
					CpPicGroup newGroup=new CpPicGroup();
					newGroup.setId(groupId);
					newGroup.setUpdateTime(new Date());
					newGroup.setUpdateUser(user.getUserName());
					newGroup.setGroupStatus(status);//内部留资	
					cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
				}
				
			}else{
				if(categoryId != null){
					if(categoryId == 473){
						CpPicGroupCategoryExample e = new CpPicGroupCategoryExample();
						e.createCriteria().andGroupIdEqualTo(groupId).andTypeEqualTo(1);
						CpPicGroupCategory pgc=new CpPicGroupCategory();
						pgc.setType(3);
						cpPicGroupCategoryMapper.updateByExampleSelective(pgc, e);
						CpPicGroup newGroup=new CpPicGroup();
						newGroup.setId(groupId);
						newGroup.setUpdateTime(new Date());
						newGroup.setUpdateUser(user.getUserName());
						newGroup.setGroupStatus(status);//内部留资	
						cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
					}else{
						CpPicGroupCategoryExample e=new CpPicGroupCategoryExample();
						e.createCriteria().andTypeEqualTo(1).andGroupIdEqualTo(groupId);
						List<CpPicGroupCategory> cates=categoryMapper.selectByExample(e);//以前发的栏目
						if(cates.size() > 1){
							beforeChangeGroupStatus(groupId, status, categoryId);	
						}else{
							CpPicGroup newGroup=new CpPicGroup();
							newGroup.setId(groupId);
							newGroup.setUpdateTime(new Date());
							newGroup.setUpdateUser(user.getUserName());
							newGroup.setGroupStatus(status);//内部留资	
							cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
						}
					}					
				}else{
					CpPicGroup newGroup=new CpPicGroup();
					newGroup.setId(groupId);
					newGroup.setUpdateTime(new Date());
					newGroup.setUpdateUser(user.getUserName());
					newGroup.setGroupStatus(status);//内部留资	
					cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
				}
				
			}
			
			addFlowLog(groupId, logType, content, null, user);
		} catch (Exception e) {
			throw e;
		}
	}
	private void beforeChangeGroupStatus(int groupId, int status, Integer categoryId) {
		//撤回操作
		if(status == 6) {
			//撤回的稿件有可能再上架（签发至原位置，设定撤回的稿件Type=3）
			CpPicGroupCategoryExample e = new CpPicGroupCategoryExample();
			e.createCriteria().andGroupIdEqualTo(groupId).andTypeEqualTo(1).andCategoryIdEqualTo(categoryId);
			CpPicGroupCategory pgc=new CpPicGroupCategory();
			pgc.setType(3);
			cpPicGroupCategoryMapper.updateByExampleSelective(pgc, e);
			
			//撤回的稿件有可能再上架或重新签发（签发时需要原分类，设定撤回的稿件签发分类的Type=2）
//			CpPicGroupCategoryExample e1 = new CpPicGroupCategoryExample();
//			e1.createCriteria().andGroupIdEqualTo(groupId).andTypeEqualTo(0);
//			
//			CpPicGroupCategory pgc1=new CpPicGroupCategory();
//			pgc1.setType(2);	
//			cpPicGroupCategoryMapper.updateByExampleSelective(pgc1, e1);
		//推送操作
		} else if (status == 7) {
			//推送的稿件将原有签发栏目关系删除
			CpPicGroupCategoryExample e2 = new CpPicGroupCategoryExample();
			e2.createCriteria().andGroupIdEqualTo(groupId).andTypeEqualTo(3);
			cpPicGroupCategoryMapper.deleteByExample(e2);
			
			//推送的稿件将原有分类关系恢复，若再次被用户提交，可恢复原分类
			CpPicGroupCategoryExample e3 = new CpPicGroupCategoryExample();
			e3.createCriteria().andGroupIdEqualTo(groupId).andTypeEqualTo(2);
			
			CpPicGroupCategory pgc3=new CpPicGroupCategory();
			pgc3.setType(0);
			
			cpPicGroupCategoryMapper.updateByExampleSelective(pgc3, e3);
		}
		
	}
	/**
	 * 批量改变稿件状态：内部留资稿件
	 * @param groupId 稿件id
	 * @param user 操作人
	 * @param status 稿件状态： 0保存，1提交（待一审），2待二审，3待三审，4已发布（已签发），5内部留资，6撤稿，7被退稿件
	 * @param logType 操作类型：-1保存稿件，0发稿提交; 1级审提交，2二审提交；3三审签发，4补签，5内部留资，6撤稿，7上架，8删除，9推送，10编辑，11一审退稿，12二审退稿，13三审退稿，14取回，15稿件合并，16强制解锁，17彻底删除，18恢复
	 * @throws Exception 
	 */
	public void changeGroupStatusBatch(String groupIds,CpUser user,int status,int logType) throws Exception{
		try {
			String arr[]=groupIds.split(",");
			List<CpPicGroup> groups=new ArrayList<CpPicGroup>();
			for (int i = 0; i < arr.length; i++) {
				CpPicGroup newGroup=new CpPicGroup();
				newGroup.setId(Integer.valueOf(arr[i]));
				newGroup.setUpdateTime(new Date());
				newGroup.setUpdateUser(user.getUserName());
				newGroup.setGroupStatus(status);//内部留资
				
				beforeChangeGroupStatus(Integer.valueOf(arr[i]), status, null);
				
				groups.add(newGroup);
			}
			aboutPictureMapper.changeStatusBatch(groups);
			addFlowLogBatch(arr, logType, user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 校审退稿：逐级退稿，一审退至用户被退稿件
	 * @param groupId 稿件id
	 * @param user 操作人
	 * @throws Exception 
	 */
	public void backGroup(CpPicGroup oldGroup,CpUser user,String content) throws Exception{
		try {
			int logType=0;
			CpPicGroup newGroup=new CpPicGroup();
			newGroup.setId(oldGroup.getId());
			if(oldGroup.getGroupStatus()==1){//一审退稿：退至用户被退稿件
				newGroup.setGroupStatus(7);
				logType=11;
			}else if(oldGroup.getGroupStatus()==2){//二审退稿：退至一审
				newGroup.setGroupStatus(1);
				logType=12;
			}else if(oldGroup.getGroupStatus()==3){//三审退稿：退至二审
				newGroup.setGroupStatus(2);
				logType=13;
			}else{
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE,"当前状态不可退稿");
			}
			newGroup.setUpdateTime(new Date());
			newGroup.setUpdateUser(user.getUserName());
			cpPicGroupMapper.updateByPrimaryKeySelective(newGroup);
			addFlowLog(oldGroup.getId(), logType, content, null, user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 批量草稿箱删除稿件
	 * @param groupIds 稿件ids
	 * @throws Exception
	 */
	public void delDraftsGroups(String groupIds,CpUser user) throws Exception{
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("ids", groupIds.split(","));
			param.put("flag", 1);	
			param.put("createId", user.getId());
			aboutPictureMapper.delGroupByIds(param);
			addFlowLogBatch(groupIds.split(","), 8, user);
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 批量恢复稿件
	 * @param groupIds 稿件ids
	 * @throws Exception
	 */
	public void reNewGroups(String groupIds,CpUser user) throws Exception{
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("ids", groupIds.split(","));
			param.put("flag", 0);	
			aboutPictureMapper.delGroupByIds(param);
			addFlowLogBatch(groupIds.split(","), 18, user);
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 批量彻底删除稿件
	 * @param groupIds 稿件ids
	 * @throws Exception
	 */
	public void delGroups(String groupIds,CpUser user) throws Exception{
		try {
			List<Integer> values=new ArrayList<Integer>();
			String arr[]= groupIds.split(",");
			for (int i = 0; i < arr.length; i++) {
				values.add(Integer.valueOf(arr[i]));
			}
			//删除稿件
			CpPicGroupExample example=new CpPicGroupExample();
			example.createCriteria().andIdIn(values);
			cpPicGroupMapper.deleteByExample(example);
			//删除图片
			CpPictureExample pe=new CpPictureExample();
			pe.createCriteria().andGroupIdIn(values);
			cpPictureMapper.deleteByExample(pe);
			addFlowLogBatch(groupIds.split(","), 17, user);
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 删除稿件并添加评语
	 * @param groupId 稿件id
	 * @param memo 评语
	 * @throws Exception
	 */
	public void delGroup(String groupId,CpUser user,String memo) throws Exception{
		try {
			/*
			 * ,int type,int siteId
			 * CpPicGroup oldGroup=cpPicGroupMapper.selectByPrimaryKey(Integer.valueOf(groupId));
			//校验值班人员
			checkUser(oldGroup, user, type, siteId);*/
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("ids", groupId.split(","));
			param.put("flag", 1);
			aboutPictureMapper.delGroupByIds(param);
			addFlowLog(Integer.valueOf(groupId), 8, memo, null, user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 合并稿件
	 * @param groupId 主稿稿件ID
	 * @param gIds 其他稿件ids,用英文逗号隔开
	 * @param user 操作人
	 * @throws Exception
	 */
	public void mergeGroups(int groupId,String gIds,CpUser user) throws Exception{
		try {
			CpPicGroup oldG=cpPicGroupMapper.selectByPrimaryKey(groupId);
			CommonValidation.checkGroupBeginEdit(oldG,groupId);
			//查询其他稿件信息
			List<Map<String,Object>> gpis=aboutPictureMapper.selectMergeGroup(gIds.split(","));
			if(gpis==null||gpis.size()==0){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, "请至少选择两条稿件");
			}
			String memo=oldG.getMemo();
			Set<String> set=new HashSet<String>();
			List<CpPicture> pics=new ArrayList<CpPicture>();
			int sort=oldG.getPictureCount()+1;
			for (Map<String,Object> map : gpis) {
				CpPicture pic=new CpPicture();
				pic.setId(Integer.valueOf(map.get("pid").toString()));
				pic.setGroupId(groupId);
				pic.setIsCover(0);
				pic.setSortId(sort++);
				if(set.add(map.get("MEMO").toString())){
					memo=memo+CommonConstant.NEWLINE+map.get("MEMO").toString();
				}
				pics.add(pic);
			}
			if(pics.size()>0){
				/*//图片合并到主稿
				cpPictureMapper.updateByPrimaryKeySelective(pic);*/
				otherMapper.updatePicGroupId(pics);
			}
			CpPicGroup newG=new CpPicGroup();
			newG.setId(groupId);
			newG.setMemo(memo);
			newG.setPictureCount(oldG.getPictureCount()+gpis.size());
			newG.setUpdateTime(new Date());
			newG.setUpdateUser(user.getUserName());
			//删除其他稿件
			List<Integer> values=new ArrayList<Integer>();
			String[] arr=gIds.split(",");
			for (int i = 0; i < arr.length; i++) {
				values.add(Integer.valueOf(arr[i]));
			}
			CpPicGroupExample example=new CpPicGroupExample();
			example.createCriteria().andIdIn(values);
			cpPicGroupMapper.deleteByExample(example);
			//保存历史版本:稿件，及图片
			//记录日志
			addHistory(oldG, newG, 0);
			//重置新稿所有图片
			otherMapper.updateGroupPicNotDel(newG.getId());
			addFlowLog(groupId, 15, null, null, user);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 校验是否存在敏感词
	 * @param group
	 * @throws Exception 
	 */
	public void checkSstvWord(String data) throws Exception{
		//获取全部敏感词
		List<String> sstv=otherMapper.selectAllSstvWord();
		for (String word : sstv) {
			if(data.contains(word)){
				throw new InvalidHttpArgumentException(CommonConstant.SSTVWORDCODE, String.format(CommonConstant.SSTVWORDMSG, word));
			}
		}
	}
	/**
	 * 校验是否存在敏感词
	 * @param group
	 * @throws Exception 
	 */
	public String checkSstvWordAndRes(String data) throws Exception{
		StringBuffer res=null;
		//获取全部敏感词
		List<String> sstv=otherMapper.selectAllSstvWord();
		for (String word : sstv) {
			if(data.contains(word)){
				if(res==null){
					res=new StringBuffer(word);
				}else{
					res.append("、").append(word);
				}
			}
		}
		return res==null?null:res.toString();
	}
	
	 /**
     * @Description: 签发时校验栏目是否签发过 <BR>
     * @author liu.jinfeng
     * @date 2017年9月7日 下午9:26:57
     * @param cates
     * @return
     */
    public String checkSignClnum(Integer groupId,
            List<Map<String, Object>> cates) {
        StringBuffer sb = new StringBuffer(50);
        StringBuffer sbIds = new StringBuffer(",");
        // 依次查询栏目是否签发过
        for (Map<String, Object> m : cates) {
            if (m.get("type").toString().equals("0")) {// 签发
                String sid = String.valueOf(m.get("signId"));
                Integer signId = Integer.parseInt(sid.substring(0, sid.lastIndexOf(".")));
                if(sbIds.toString().indexOf(","+signId+",")>-1){
                    continue;
                }
                sbIds.append(signId).append(",");
                
                CpPicGroupCategoryExample e = new CpPicGroupCategoryExample();
                e.createCriteria().andGroupIdEqualTo(groupId).andTypeEqualTo(1)
                        .andCategoryIdEqualTo(signId);
                int nCount = cpPicGroupCategoryMapper.countByExample(e);
                if (nCount > 0) {// 表示这个栏目之前已经签过
                    // sb.append(signId).append(",");
                    CpColumn colume = columnMapper.selectBykey(signId);
                    sb.append(colume.getName()).append(",");
                }
            }
        }

        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }
}
