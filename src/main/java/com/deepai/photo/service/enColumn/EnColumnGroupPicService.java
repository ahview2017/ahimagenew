package com.deepai.photo.service.enColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpColumn;
import com.deepai.photo.bean.CpLanmuGroupPic;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupColumn;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureExample;
import com.deepai.photo.bean.CpPictureExample.Criteria;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpPicGroupColumnMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.service.admin.SysConfigService;

@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class EnColumnGroupPicService {
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CpPicGroupColumnMapper cpPicGroupColumnMapper;
	@Autowired
	private EnColumnService enColumnService;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private CpPictureMapper cpPictureMapper;
	
	private static Integer langType = 1;	// 中英文类型
//	@Autowired
//	private HttpServletRequest request;
	
	
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;
	
	public List<CpPicGroupColumn> getPicGroupList(HttpServletRequest request) throws Exception {
		List<CpPicGroupColumn> cpgcList = cpPicGroupColumnMapper.selectGroupByColumn();
		return findCpLanmuGroupPic(cpgcList,request);
	}

	
	/**
	 * 获取衍生栏目组图和首页轮训图
	 */
	public List<CpPicGroupColumn> getPicDeriGroupList(String columnId,HttpServletRequest request) throws Exception {
		List<CpPicGroupColumn> cpgcList = cpPicGroupColumnMapper.selectDeriGroupByColumnId(columnId);
		return findCpLanmuGroupPic(cpgcList,request);
	}
	
	public List<CpPicGroupColumn> findCpColumnGroupPic(List<CpPicGroupColumn> cpPicGroupColumnList,HttpServletRequest request) throws Exception {
		for (CpPicGroupColumn cpPicGroupColumn : cpPicGroupColumnList) {
			if(cpPicGroupColumn != null){
				int groupId = cpPicGroupColumn.getGroupId();
				CpLanmuGroupPic cpLanmuGroupPic = cpPicGroupColumnMapper.findGroupPicByGroupId(groupId,4);
				if (cpLanmuGroupPic != null) {
					String allpath = cpLanmuGroupPic.getAllpath();
					if (StringUtils.isNotBlank(allpath)) {
						String allPath = allpath.replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)), ipAdd);
						cpLanmuGroupPic.setAllpath(allPath);
					}
					cpPicGroupColumn.getCpPicGroup().setCpLanmuGroupPic(cpLanmuGroupPic);
				}
			}
		}
		return cpPicGroupColumnList;
	}
	
	
	
	/**
	 * 获取的所有组图 
	 * @param columnId
	 * @param maxPage
	 * @param startPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CpColumn> getOrderPicGroupList(Integer columnId, Integer maxPage, Integer startPage,HttpServletRequest request) throws Exception {
		List<CpColumn> cpColumnLists = enColumnService.selectEnColumnList(columnId, 1, 1, langType);
		if(cpColumnLists != null){
			for (CpColumn cpColumn : cpColumnLists) {
				List<CpPicGroup> CpPicGroupList = new ArrayList<CpPicGroup>();
				if(cpColumn!=null){
					List<Integer> groupIdLists = 
							cpPicGroupColumnMapper.selectOrderGroupIdByColumnId(cpColumn.getId(),startPage,maxPage);
					for (Integer groupId : groupIdLists) {
						CpPicGroup cpPicGroup = cpPicGroupColumnMapper.selectGroupByGroupId(groupId);
						CpLanmuGroupPic cpLanmuGroupPic = cpPicGroupColumnMapper.findGroupPicByGroupId(groupId,2);
						if (cpLanmuGroupPic != null) {
							String allpath = cpLanmuGroupPic.getAllpath();
							if (StringUtils.isNotBlank(allpath)) {
								String allPath = allpath.replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)), ipAdd);
								cpLanmuGroupPic.setAllpath(allPath);
							}
							cpPicGroup.setCpLanmuGroupPic(cpLanmuGroupPic);
						}
						CpPicGroupList.add(cpPicGroup);
					}
				}
				cpColumn.setCpgList(CpPicGroupList);
			}
		}

		return cpColumnLists;
	}
	/**
	 * 
	 * @param columnId
	 * @param maxPage
	 * @param startPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CpPicGroup> getOrderGroupList(Integer columnId, Integer maxPage, Integer startPage,HttpServletRequest request) throws Exception{
		List<CpPicGroup> CpPicGroupList = new ArrayList<CpPicGroup>();
		if(columnId!=null){
			List<Integer> groupIdLists = 
					cpPicGroupColumnMapper.selectOrderGroupIdByColumnId(columnId,startPage,maxPage);
			for (Integer groupId : groupIdLists) {
				CpPicGroup cpPicGroup = cpPicGroupColumnMapper.selectGroupByGroupId(groupId);
				CpLanmuGroupPic cpLanmuGroupPic = cpPicGroupColumnMapper.findGroupPicByGroupId(groupId,2);
				if (cpLanmuGroupPic != null) {
					String allpath = cpLanmuGroupPic.getAllpath();
					if (StringUtils.isNotBlank(allpath)) {
						String allPath = allpath.replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)), ipAdd);
						cpLanmuGroupPic.setAllpath(allPath);
					}
					cpPicGroup.setCpLanmuGroupPic(cpLanmuGroupPic);
				}
				CpPicGroupList.add(cpPicGroup);
			}
		}
		return CpPicGroupList;
	}
	/**
	 * 获取最新栏目组图
	 * @param columnId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CpPicGroupColumn> getGroupAllPicList(String columnId,HttpServletRequest request) throws Exception {
		List<CpPicGroupColumn> cpgcList = cpPicGroupColumnMapper.selectGroupByColumnIdAndTime(columnId);
		int groupId = cpgcList.get(0).getGroupId();
		CommonValidation.checkParamBlank(groupId+"", "稿件id");
		if(cpgcList.get(0).getCpPicGroup()==null){
			throw new InvalidHttpArgumentException(CommonConstant.NULLCODE, String.format("不存在稿件Id=%s的稿子", groupId));
		}
		List<CpPicture> cpPictureLists = cpPicGroupColumnMapper.selectPicPictureByGroupId(groupId);
		CpPicGroup cpPg = cpPicGroupMapper.selectByPrimaryKey(groupId);
		Integer coverPicId = cpPg.getCoverPictureId();
		
		if(cpPictureLists != null){
			for (CpPicture pic:cpPictureLists) {
				if(pic.getFilename()!=null) {
					if(pic.getId().intValue() == coverPicId.intValue()) {
						pic.setWmPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getWMPathByName(pic.getFilename(),request));
					} else {
						pic.setSmallPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(pic.getFilename(),request));
					}
				}
			}
			cpgcList.get(0).getCpPicGroup().setPics(cpPictureLists);
		}
		return findCpLanmuGroupPic(cpgcList,request);
	}
	
	public List<CpPicGroupColumn> findCpLanmuGroupPic(List<CpPicGroupColumn> cpPicGroupColumnList,HttpServletRequest request) throws Exception {
		for (CpPicGroupColumn cpPicGroupColumn : cpPicGroupColumnList) {
			if(cpPicGroupColumn != null){
				int groupId = cpPicGroupColumn.getGroupId();
				CpLanmuGroupPic cpLanmuGroupPic = cpPicGroupColumnMapper.findGroupPicByGroupId(groupId,4);
				if (cpLanmuGroupPic != null) {
					String allpath = cpLanmuGroupPic.getAllpath();
					if (StringUtils.isNotBlank(allpath)) {
						String allPath = allpath.replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)), ipAdd);
						cpLanmuGroupPic.setAllpath(allPath);
					}
					cpPicGroupColumn.getCpPicGroup().setCpLanmuGroupPic(cpLanmuGroupPic);
				}
			}
		}
		return cpPicGroupColumnList;
	}


	public Integer getGroupCount(Integer columnId) {
		
		return cpPicGroupColumnMapper.getGroupCountByColumn(columnId);
	}

	/**
	 * 获取最新栏目组图
	 * @param columnId
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> selectNewestPicGroup(String columnId, HttpServletRequest request) {
		List<Map<String ,Object>> picGrouplist = cpPicGroupColumnMapper.selectNewestPicGroup(columnId);
		List<Map<String ,Object>> pics = new ArrayList<Map<String ,Object>>();
		if(picGrouplist.get(0).containsKey("ID")){
			CpPictureExample example = new  CpPictureExample();
			Criteria criteria = example.createCriteria();
			criteria.andGroupIdEqualTo(Integer.parseInt(picGrouplist.get(0).get("ID").toString()));
			List<CpPicture> cpPictureList = cpPictureMapper.selectByExample(example);
			for (CpPicture cpPicture : cpPictureList) {
				Map<String, Object> pic = new HashMap<String, Object>();
				pic.put("picId", cpPicture.getId());
				pic.put("pFileName", cpPicture.getFilename());
				pic.put("pTiltle", cpPicture.getTitle());
				pic.put("pMemo", cpPicture.getMemo());
				pic.put("pAuditor", cpPicture.getAuditor());
				pics.add(pic);
			}
		}
		picGrouplist.get(0).put("pics", pics);
		return picGrouplist;
	}

	/**
	 * 获取衍生栏目组图和首页轮训图
	 * @param columnId
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getGroupCarouselPicList(String columnId, HttpServletRequest request) {
		
		CpColumn cpColumn = enColumnService.selectEnUpColumnList(Integer.parseInt(columnId));
		Integer maxSignNum = cpColumn.getSignPosition();
		List<Map<String ,Object>> picGrouplist = null;
		List<Map<String, Object>> newPicGroupList = new ArrayList<Map<String,Object>>();
		if(maxSignNum != 0){
			picGrouplist = cpPicGroupColumnMapper.getGroupCarouselPicList(columnId,maxSignNum);
			int picNum = 0;
			for (int i = 0; i < maxSignNum; i++) {
				if(picGrouplist.size()>picNum&&picGrouplist.get(picNum).get("sgin_show").equals(i+1)){
					newPicGroupList.add(i, picGrouplist.get(picNum));
					picNum++;
				}else{
					Map<String ,Object> picGroup = new HashMap<>();
					picGroup.put("TITLE", "尚未签发");
					picGroup.put("path", "");
					newPicGroupList.add(i, picGroup);
				}
				
			}
		}
		return newPicGroupList;
	}

	/**
	 * 获组图取所有 父栏目下子栏目组图
	 * @param columnId
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getAllPicGroupList(String columnId, HttpServletRequest request) {
		List<CpColumn> cpColumnLists = enColumnService.selectEnColumnList(Integer.parseInt(columnId), 1, 1, langType);
		List<Map<String,Object>> cpPicGroupList = new ArrayList<Map<String,Object>>();
		if(cpColumnLists != null){
			for (CpColumn cpColumn : cpColumnLists) {
//				List<Map<String, Object>> cpSubPicGroupList = new ArrayList<Map<String, Object>>();
				if(cpColumn!=null){
					Integer maxSignNum = cpColumn.getSignPosition();
					List<Map<String ,Object>> subPicGrouplist = null;
					List<Map<String, Object>> newPicGroupList = new ArrayList<Map<String,Object>>();
					if(maxSignNum !=0){
						subPicGrouplist = cpPicGroupColumnMapper.getGroupCarouselPicList(cpColumn.getId()+"",maxSignNum);
						
						
						int picNum = 0;
						for (int i = 0; i < maxSignNum; i++) {
							if(subPicGrouplist.size()>picNum&&subPicGrouplist.get(picNum).get("sgin_show").equals(i+1)){
								newPicGroupList.add(i, subPicGrouplist.get(picNum));
								picNum++;
							}else{
								Map<String ,Object> picGroup = new HashMap<>();
								picGroup.put("TITLE", "尚未签发");
								picGroup.put("path", "");
								newPicGroupList.add(i, picGroup);
							}
							
						}
						
						Map<String,Object> cpPicGroup = new HashMap<String,Object>();
						if(newPicGroupList!=null && newPicGroupList.size()!=0){
							for (Map<String, Object> map : newPicGroupList) {
								if(map.containsKey("FILENAME")){
									map.put("smallPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(map.get("FILENAME").toString(),request));
								}
							}
							cpPicGroup.put("columnId", cpColumn.getId());
							cpPicGroup.put("columnName", cpColumn.getName());
							cpPicGroup.put("groupList", newPicGroupList);
							cpPicGroup.put("recommendId", cpColumn.getRecommendId());
						}
						cpPicGroupList.add(cpPicGroup);
						
					}
				}
			}
		}
		return cpPicGroupList;
	}


	/**
	 * 获取单个栏目下所有组图（签发时间）
	 * @param columnId
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getGroupPicList(String columnId, HttpServletRequest request) {		
		return cpPicGroupColumnMapper.getGroupPicList(columnId);
	}


	
	
	

	
	
	
	
	

}
