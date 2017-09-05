package com.deepai.photo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpLanmuExample;
import com.deepai.photo.bean.CpLanmuGroupPic;
import com.deepai.photo.bean.CpLanmuPicture;
import com.deepai.photo.bean.CpLanmuPictureExample;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpNoticeExample;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.mapper.CpLanmuMapper;
import com.deepai.photo.mapper.CpLanmuPictureMapper;
import com.deepai.photo.mapper.CpNoticeMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.lanmu.LanmuService;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class EnTopicColumService {

	@Resource 
	private LanmuService service;
	@Resource
	private CpLanmuMapper lanmuMapper;
	@Resource 
	private SysConfigService sonfigService;
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;
	@Resource
	private CpLanmuPictureMapper lanmuPictureMapper;
	@Resource
	private CpNoticeMapper noticeMapper;
	@Resource
	private  HttpServletRequest request;
	
	
	private final  int ZERO = 0;
	private final  int ONE = 1;
	private final  int TWO = 2;
	private final  int THREE = 3;
	
	
	public List<CpLanmu> show(CpTopic cpTopic) {
		CpLanmuExample example = new CpLanmuExample();
		example.createCriteria()
		.andTopicIdEqualTo(cpTopic.getId())
		.andPidEqualTo(ZERO);
		List<CpLanmu> cpLanmus = lanmuMapper.selectByExample(example);
			List<CpLanmu> childLanmu = getChildLanmu(cpLanmus);
		return childLanmu;
	}
	
	public List<CpLanmu> getChildLanmu(List<CpLanmu> cpLanmus){
		if(cpLanmus!=null && !cpLanmus.isEmpty()){
			for (CpLanmu cpLanmu : cpLanmus) {
				//默认选择
				if(cpLanmu.getShowWay() == ZERO){
					CpLanmuExample example = new CpLanmuExample();
					example.createCriteria().andPidEqualTo(cpLanmu.getId());
					List<CpLanmu> byExample = lanmuMapper.selectByExample(example);
					List<CpLanmu> childLanmu = getChildLanmu(byExample);
					cpLanmu.setCpLanmuList(childLanmu);
				}else if(cpLanmu.getShowWay() == ONE){
					//轮播图+广告
					if(cpLanmu.getTypeId() == ZERO){
						CpLanmuPictureExample example1 = new CpLanmuPictureExample();
						example1.createCriteria().andLanmuIdEqualTo(cpLanmu.getId()).andPotisionIsNull();
						List<CpLanmuPicture> lanMuPicDetail = lanmuPictureMapper.selectByExample(example1);
						for (CpLanmuPicture cpLanmuPicture : lanMuPicDetail) {
							CpLanmuGroupPic groupPic = lanmuPictureMapper.getGroupPic(cpLanmuPicture.getGroupId());
							groupPic.setAllpath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(groupPic.getPicname(),request));
							cpLanmuPicture.setCpLanmuGroupPic(groupPic);
						}
						cpLanmu.setCpLanmuPictures(lanMuPicDetail);
						
						CpLanmuPictureExample example = new CpLanmuPictureExample();
						example.createCriteria().andLanmuIdEqualTo(cpLanmu.getId()).andPotisionIsNull();
						List<CpLanmuPicture> moreLanMuPicDetail = lanmuPictureMapper.selectByExample(example);
						for (CpLanmuPicture cpLanmuPicture : moreLanMuPicDetail) {
							CpLanmuGroupPic groupPic = lanmuPictureMapper.getGroupPic(cpLanmuPicture.getGroupId());
							groupPic.setAllpath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(groupPic.getPicname(),request));
							cpLanmuPicture.setCpLanmuGroupPic(groupPic);
						}
						cpLanmu.setMoreLanmuPictures(moreLanMuPicDetail);
						List<CpNotice> showMoreLanmuWithAdv = noticeMapper.showMoreLanmuWithAdv(cpLanmu.getId());
						cpLanmu.setCpNoticesList(showMoreLanmuWithAdv);
					}else{
						CpLanmuPictureExample example1 = new CpLanmuPictureExample();
						example1.createCriteria().andLanmuIdEqualTo(cpLanmu.getId()).andPotisionIsNotNull();
						List<CpLanmuPicture> lanMuPicDetail = lanmuPictureMapper.selectByExample(example1);
						for (CpLanmuPicture cpLanmuPicture : lanMuPicDetail) {
							CpLanmuGroupPic groupPic = lanmuPictureMapper.getGroupPic(cpLanmuPicture.getGroupId());
							groupPic.setAllpath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(groupPic.getPicname(),request));
							cpLanmuPicture.setCpLanmuGroupPic(groupPic);
						}
						cpLanmu.setCpLanmuPictures(lanMuPicDetail);
						
						CpLanmuPictureExample example = new CpLanmuPictureExample();
						example.createCriteria().andLanmuIdEqualTo(cpLanmu.getId()).andPotisionIsNull();
						List<CpLanmuPicture> moreLanMuPicDetail = lanmuPictureMapper.selectByExample(example);
						for (CpLanmuPicture cpLanmuPicture : moreLanMuPicDetail) {
							CpLanmuGroupPic groupPic = lanmuPictureMapper.getGroupPic(cpLanmuPicture.getGroupId());
							groupPic.setAllpath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(groupPic.getPicname(),request));
							cpLanmuPicture.setCpLanmuGroupPic(groupPic);
						}
						cpLanmu.setMoreLanmuPictures(moreLanMuPicDetail);
					}
				}else if(cpLanmu.getShowWay() == THREE){
					List<CpLanmu> childLanmu = getChildLanmu(cpLanmu.getCpLanmuList());
					cpLanmu.setCpLanmuList(childLanmu);
				}
			}
		}
		return cpLanmus;
		
	}

	public void insert(String topic,String lanmuIds) {
		System.out.println("================================================================================");
		System.out.println(topic);
		System.out.println("================================================================================");
		System.out.println(lanmuIds);
		System.out.println("================================================================================");
		if(lanmuIds!=null && !lanmuIds.equals("")){
			String[] ids = lanmuIds.split(",");
			for (String id : ids) {
				lanmuMapper.deleteByPrimaryKey(Integer.parseInt(id));
				
				CpNoticeExample exampleNotice = new CpNoticeExample();
				exampleNotice.createCriteria().andTopicIdEqualTo(Integer.parseInt(id));
				noticeMapper.deleteByExample(exampleNotice);
				
				CpLanmuPictureExample exampleLanmuPic = new CpLanmuPictureExample();
				exampleLanmuPic.createCriteria().andLanmuIdEqualTo(Integer.parseInt(id));
				lanmuPictureMapper.deleteByExample(exampleLanmuPic );
			}
		}
		if(!topic.equals("")&&topic!=null){
			CpTopic cpTopic = new CpTopic();
			JSONObject jsonObject =JSONObject.fromObject(topic);
			if(jsonObject.get("id")!=null && !jsonObject.get("id").equals("")){
				cpTopic.setId(Integer.parseInt(jsonObject.get("id").toString()));
			}
			insertFor(cpTopic.getId(),ZERO,jsonObject.get("children").toString());
		}
	}
	public void insertFor(Integer cpTopic,Integer pid,String lanmuList) {
		if(lanmuList != null && !lanmuList.equals("")){
		JSONArray jsonArray = JSONArray.fromObject(lanmuList);
		/*for(CpLanmu cpLanmu : list) {*/
		for (Object object : jsonArray) {
			JSONObject object2 = JSONObject.fromObject(object);
			CpLanmu cpLanmu = new CpLanmu();
			cpLanmu.setTopicId(cpTopic);
			if(object2.get("id") !=null && !object2.get("id").toString().equals("")){
				cpLanmu.setId(Integer.parseInt(object2.get("id").toString()));
			}
			cpLanmu.setName(object2.get("name").toString());
			if(object2.get("lanmuNo") !=null && !object2.get("lanmuNo").toString().equals("")){
				cpLanmu.setLanmuNo(Integer.parseInt(object2.get("lanmuNo").toString()));
			}
			if(object2.get("typeId") !=null && !object2.get("typeId").toString().equals("")){
				cpLanmu.setTypeId(Integer.parseInt(object2.get("typeId").toString()));
			}
			if(object2.get("zhanshuCount") !=null && !object2.get("zhanshuCount").toString().equals("") && !object2.get("zhanshuCount").toString().equals("null")){
				cpLanmu.setZhanshuCount(Integer.parseInt(object2.get("zhanshuCount").toString()));
			}
			cpLanmu.setPid(pid);
			cpLanmu.setUrl(object2.get("url").toString());
			cpLanmu.setKeyWords(object2.get("keyWords").toString());
			cpLanmu.setPic(object2.get("pic").toString());
			if(object2.get("showWay") !=null && !object2.get("showWay").toString().equals("")){
				cpLanmu.setShowWay(Integer.parseInt(object2.get("showWay").toString()));
			}
			//默认选择
			if(cpLanmu.getShowWay() == ZERO){
				Integer columnId = null;
				if(cpLanmu.getId() != null){
					lanmuMapper.updateByPrimaryKey(cpLanmu);
					columnId = cpLanmu.getId();
				}else{
					cpLanmu.setPid(pid);
					lanmuMapper.insert(cpLanmu);
					columnId = lanmuMapper.getMaxId();
				}
				insertFor(cpTopic,columnId,object2.get("cpLanmuList").toString());
			//人工签发
			}else if(cpLanmu.getShowWay() == ONE){
				Integer lanmuId = null;
				if(cpLanmu.getId() != null){
					lanmuMapper.updateByPrimaryKey(cpLanmu);
					lanmuId = cpLanmu.getId();
				}else{
					cpLanmu.setPid(pid);
					lanmuMapper.insert(cpLanmu);
					lanmuId = lanmuMapper.getMaxId();
				}
				//轮播图+公告
				if(cpLanmu.getTypeId() == ZERO){
					if(object2.get("cpLanmuPictures")!=null && !object2.get("cpLanmuPictures").toString().equals("")){
						JSONArray array = JSONArray.fromObject(object2.get("cpLanmuPictures").toString());
						for (Object object3 : array) {
							JSONObject object4 = JSONObject.fromObject(object3);
							CpLanmuPicture cpLanmuPicture = new CpLanmuPicture();
							if(object4.get("id")!=null && !object4.get("id").equals("")){
								cpLanmuPicture.setId(Integer.parseInt(object4.get("id").toString()));
							}
							if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
								cpLanmuPicture.setGroupId(Integer.parseInt(object4.get("groupId").toString()));
							}
							cpLanmuPicture.setLanmuId(pid);
							if(object4.get("potision")!=null && !object4.get("potision").equals("")){
								cpLanmuPicture.setPotision(Integer.parseInt(object4.get("potision").toString()));
							}
							if(object4.get("id")!=null&&!object4.get("id").equals("")){
								lanmuPictureMapper.updateByPrimaryKey(cpLanmuPicture);
							}else{
								cpLanmuPicture.setLanmuId(lanmuId);
								if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
									lanmuPictureMapper.insert(cpLanmuPicture);
								}
							}
						}
					}
					if(object2.get("moreLanmuPictures")!=null && !object2.get("moreLanmuPictures").equals("")){
						JSONArray array = JSONArray.fromObject(object2.get("moreLanmuPictures").toString());
						for (Object object3 : array) {
							JSONObject object4 = JSONObject.fromObject(object3);
							CpLanmuPicture cpLanmuPicture = new CpLanmuPicture();
							if(object4.get("id")!=null && !object4.get("id").equals("")){
								cpLanmuPicture.setId(Integer.parseInt(object4.get("id").toString()));
							}
							if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
								cpLanmuPicture.setGroupId(Integer.parseInt(object4.get("groupId").toString()));
							}
							cpLanmuPicture.setLanmuId(pid);
							if(object4.get("potision")!=null && !object4.get("potision").equals("")){
								cpLanmuPicture.setPotision(Integer.parseInt(object4.get("potision").toString()));
							}
							if(object4.get("id")!=null&&!object4.get("id").toString().equals("")){
								lanmuPictureMapper.updateByPrimaryKey(cpLanmuPicture);
							}else{
								cpLanmuPicture.setLanmuId(lanmuId);
								if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
									lanmuPictureMapper.insert(cpLanmuPicture);
								}
							}
						}
					}
					if(object2.get("cpNoticesList")!=null && !object2.get("cpNoticesList").toString().equals("")){
						JSONArray array = JSONArray.fromObject(object2.get("cpNoticesList").toString());
						for (Object object3 : array) {
							JSONObject object4 = JSONObject.fromObject(object3);
							CpNotice cpNotice = new CpNotice();
							cpNotice.setTopicId(lanmuId);
							cpNotice.setNoticeTitle(object4.get("noticeTitle").toString());
							cpNotice.setNoticeTitle(object4.get("noticeContent").toString());
							cpNotice.setLangType(Integer.parseInt(object4.get("langType").toString()));
							cpNotice.setPosition(Integer.parseInt(object4.get("position").toString()));
							if(object4.get("id")!=null&&!object4.get("id").toString().equals("")){
								cpNotice.setId(Integer.parseInt(object4.get("id").toString()));
								noticeMapper.updateByPrimaryKey(cpNotice);
							}else{
								noticeMapper.insert(cpNotice);
							}
						}
						
					}
					
				//大轮播
				}else{
					if(object2.get("cpLanmuPictures")!=null && !object2.get("cpLanmuPictures").toString().equals("")){
						JSONArray array = JSONArray.fromObject(object2.get("cpLanmuPictures").toString());
						for (Object object3 : array) {
							JSONObject object4 = JSONObject.fromObject(object3);
							CpLanmuPicture cpLanmuPicture = new CpLanmuPicture();
							if(object4.get("id")!=null && !object4.get("id").equals("")){
								cpLanmuPicture.setId(Integer.parseInt(object4.get("id").toString()));
							}
							if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
								cpLanmuPicture.setGroupId(Integer.parseInt(object4.get("groupId").toString()));
							}
							cpLanmuPicture.setLanmuId(pid);
							if(object4.get("potision")!=null && !object4.get("potision").equals("")){
								cpLanmuPicture.setPotision(Integer.parseInt(object4.get("potision").toString()));
							}
							if(object4.get("id")!=null&&!object4.get("id").toString().equals("")){
								lanmuPictureMapper.updateByPrimaryKey(cpLanmuPicture);
							}else{
								cpLanmuPicture.setLanmuId(lanmuId);
								if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
									lanmuPictureMapper.insert(cpLanmuPicture);
								}
							}
						}
					}
					if(object2.get("moreLanmuPictures")!=null && !object2.get("moreLanmuPictures").toString().equals("")){
						JSONArray array = JSONArray.fromObject(object2.get("moreLanmuPictures").toString());
						for (Object object3 : array) {
							JSONObject object4 = JSONObject.fromObject(object3);
							CpLanmuPicture cpLanmuPicture = new CpLanmuPicture();
							if(object4.get("id")!=null && !object4.get("id").equals("")){
								cpLanmuPicture.setId(Integer.parseInt(object4.get("id").toString()));
							}
							if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
								cpLanmuPicture.setGroupId(Integer.parseInt(object4.get("groupId").toString()));
							}
							cpLanmuPicture.setLanmuId(pid);
							if(object4.get("potision")!=null && !object4.get("potision").equals("")){
								cpLanmuPicture.setPotision(Integer.parseInt(object4.get("potision").toString()));
							}
							if(object4.get("id")!=null&&!object4.get("id").toString().equals("")){
								lanmuPictureMapper.updateByPrimaryKey(cpLanmuPicture);
							}else{
								cpLanmuPicture.setLanmuId(lanmuId);
								if(object4.get("groupId")!=null && !object4.get("groupId").equals("")){
									lanmuPictureMapper.insert(cpLanmuPicture);
								}
							}
						}
					}
				//轮播图+说明
				}
			//条件检索
			}else if(cpLanmu.getShowWay() == TWO){
				/*Integer lanmuId = lanmuMapper.insert(cpLanmu);
				insertFor(lanmuId,cpLanmu.getCpLanmuList());*/
				cpLanmu.setWords(object2.get("words").toString());
				if(cpLanmu.getId() != null){
					lanmuMapper.updateByPrimaryKey(cpLanmu);
				}else{
					cpLanmu.setPid(pid);
					lanmuMapper.insert(cpLanmu);
				}
			}else if(cpLanmu.getShowWay() == THREE){
				Integer columnId = null;
				if(cpLanmu.getId() != null){
					lanmuMapper.updateByPrimaryKey(cpLanmu);
					columnId = cpLanmu.getId();
				}else{
					cpLanmu.setPid(pid);
					lanmuMapper.insert(cpLanmu);
					columnId = lanmuMapper.getMaxId();
				}
				insertFor(cpTopic,columnId,object2.get("cpLanmuList").toString());
			}
			
		}
		}
	}

	public String upPic(MultipartFile bannaPic, HttpServletRequest request2) throws Exception {
		String filePath = FileUpload.fileUpName(bannaPic, sonfigService
				.getDbSysConfig(
						SysConfigConstant.DEFAULT_CLASSIFICATION_PATH,
						SessionUtils.getSiteId(request)),
				bannaPic.getOriginalFilename());
		String allPath = ImgFileUtils.getReplacePathByName(filePath,sonfigService.getDbSysConfig(SysConfigConstant.DEFAULT_CLASSIFICATION_PATH,SessionUtils.getSiteId(request))
				, ipAdd, "/classification/", request);
		return allPath;
		
	}


}
