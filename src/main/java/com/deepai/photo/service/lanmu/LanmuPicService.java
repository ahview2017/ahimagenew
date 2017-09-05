package com.deepai.photo.service.lanmu;

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

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpLanmuGroupPic;
import com.deepai.photo.bean.CpLanmuPicture;
import com.deepai.photo.bean.CpLanmuPictureExample;
import com.deepai.photo.bean.CpLanmuPictureExample.Criteria;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.bean.CpLanmulist;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.mapper.CpAdvPositionMapper;
import com.deepai.photo.mapper.CpLanmuMapper;
import com.deepai.photo.mapper.CpLanmuPictureMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.notice.NoticeService;

/**
 * * @author huqiankai: * *
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class LanmuPicService {
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CpLanmuPictureMapper cpLanmuPictureMapper;
	@Autowired
	private LanmuService lanmuService;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private NoticeService noticeService;
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;

	public void addLanmuPics(List<CpLanmuPicture> cpLanmuPictures) {
		int a = 0;
		for (CpLanmuPicture cpLanmuPicture : cpLanmuPictures) {
			a++;
			cpLanmuPicture.setPotision(a);
			cpLanmuPictureMapper.insertSelective(cpLanmuPicture);
		}
	}

	public void changePosition(CpLanmuPicture cpLanmuPicture) {
		cpLanmuPictureMapper.changePosition(cpLanmuPicture);
		cpLanmuPictureMapper.upPosition(cpLanmuPicture);
	}

	public List<CpLanmuPicture> LanmuPictureBylanmuId(int lanmuid) {
		CpLanmuPictureExample example = new CpLanmuPictureExample();
		Criteria criteria = example.createCriteria();
		criteria.andLanmuIdEqualTo(lanmuid);
		return cpLanmuPictureMapper.selectByExample(example);
	}

	public void save(List<CpLanmuPicture> list) {
		for (CpLanmuPicture cpLanmuPicture : list) {
			cpLanmuPictureMapper.updateByPrimaryKeySelective(cpLanmuPicture);
		}
	}

	public void delete(int lanmuID) {
		cpLanmuPictureMapper.deleteByPrimaryKey(lanmuID);
	}

	public CpLanmu lanMuPicDetail(CpLanmu cpLanmu,HttpServletRequest request) throws Exception {
		if (cpLanmu.getPid() == null) {
			List<CpLanmuPicture> lanMuPicDetail = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getId());
//			List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(lanMuPicDetail);
			List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(lanMuPicDetail,2,request);
			cpLanmu.setCpLanmuPictures(findCpLanmuGroupPic);
			return cpLanmu;
		}
		List<CpLanmuPicture> lanMuPicDetail = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getCpLanmu1().getId());
//		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(lanMuPicDetail);
		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(lanMuPicDetail,2,request);
		cpLanmu.getCpLanmu1().setCpLanmuPictures(findCpLanmuGroupPic);
		List<CpLanmuPicture> lanMuPicDetail2 = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getCpLanmu2().getId());
//		List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic(lanMuPicDetail2);
		List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic_1(lanMuPicDetail2,2,request);
		cpLanmu.getCpLanmu2().setCpLanmuPictures(findCpLanmuGroupPic2);
		return cpLanmu;

	}

	public List<CpLanmuPicture> lanMuPicMoreDetail(int lanmuid,HttpServletRequest request) throws Exception {
		List<CpLanmuPicture> lanMuPicMoreDetail = cpLanmuPictureMapper.lanMuPicMoreDetail(lanmuid);
//		List<CpLanmuPicture> findCpLanmuGroupPic1 = findCpLanmuGroupPic(lanMuPicMoreDetail);
		List<CpLanmuPicture> findCpLanmuGroupPic1 = findCpLanmuGroupPic_1(lanMuPicMoreDetail,2,request);
		return findCpLanmuGroupPic1;
	}

	public void add(CpLanmuPicture cpLanmuPicture) {
		Integer potision = cpLanmuPicture.getPotision();
		CpLanmuPictureExample example = new CpLanmuPictureExample();
		Criteria criteria = example.createCriteria();
		criteria.andPotisionEqualTo(potision);
		criteria.andLanmuIdEqualTo(cpLanmuPicture.getLanmuId());
		List<CpLanmuPicture> cpLanmuPictureList = cpLanmuPictureMapper.selectByExample(example);
		if (cpLanmuPictureList.size() > 0) {
			cpLanmuPictureList.get(0).setPotision(100);
			cpLanmuPictureMapper.updateByPrimaryKey(cpLanmuPictureList.get(0));
		}
		cpLanmuPictureMapper.insertSelective(cpLanmuPicture);
	}

	public List<CpLanmu> findAllPicWithLanmuId(List<CpLanmu> cpLanmu,HttpServletRequest request) throws Exception {
		for (int i = 0; i < cpLanmu.size(); i++) {
			if (cpLanmu.get(i).getPid() != null && cpLanmu.get(i).getPid() == 0) {
				List<CpLanmuPicture> Zilanmu1 = cpLanmuPictureMapper
						.lanMuPicDetail(cpLanmu.get(i).getCpLanmu1().getId());
//				List<CpLanmuPicture> findCpLanmuGroupPic1 = findCpLanmuGroupPic(Zilanmu1);
				List<CpLanmuPicture> findCpLanmuGroupPic1 = findCpLanmuGroupPic_1(Zilanmu1,2,request);
				cpLanmu.get(i).getCpLanmu1().setCpLanmuPictures(findCpLanmuGroupPic1);
				List<CpLanmuPicture> Zilanmu2 = cpLanmuPictureMapper
						.lanMuPicDetail(cpLanmu.get(i).getCpLanmu2().getId());
//				List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic(Zilanmu2);
				List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic_1(Zilanmu2,2,request);
				cpLanmu.get(i).getCpLanmu2().setCpLanmuPictures(findCpLanmuGroupPic2);
			}
			List<CpLanmuPicture> PciList = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.get(i).getId());
			int type = 2;
			if (cpLanmu.get(i).getTypeId()==2 || cpLanmu.get(i).getTypeId()==3) {
				type = 4;
			}
//			List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic(PciList);
			List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic_1(PciList,type,request);
			cpLanmu.get(i).setCpLanmuPictures(findCpLanmuGroupPic2);
		}
		return cpLanmu;
	}

	public List<CpLanmuPicture> AllLanmuPic(int lanmuid,HttpServletRequest request) throws Exception {
		CpLanmuPictureExample example = new CpLanmuPictureExample();
		Criteria criteria = example.createCriteria();
		criteria.andLanmuIdEqualTo(lanmuid);
		List<CpLanmuPicture> selectByExample = cpLanmuPictureMapper.selectByExample(example);
//		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(selectByExample);
		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(selectByExample,2,request);
		return findCpLanmuGroupPic;
	}
	/*public void saveLanmuWithPic(CpLanmu cpLanmu) {
		if (cpLanmu.getPid() != null && cpLanmu.getPid() == 0) {
			 List<CpLanmuPicture> cpLanmu1List = cpLanmu.getCpLanmu1().getCpLanmuPictures();
			 for (int i = 0; i < cpLanmu1List.size(); i++) {
				 if (cpLanmu1List.get(i).getId()==null) {
					 cpLanmuPictureMapper.insertSelective(cpLanmu1List.get(i));
				}
				 cpLanmuPictureMapper.updateByPrimaryKey(cpLanmu1List.get(i));
			}
			 List<CpLanmuPicture> cpLanmu2List = cpLanmu.getCpLanmu2().getCpLanmuPictures();
			 for (int i = 0; i < cpLanmu2List.size(); i++) {
				 if (cpLanmu2List.get(i).getId()==null) {
					 cpLanmuPictureMapper.insertSelective(cpLanmu2List.get(i));
				}
				 cpLanmuPictureMapper.updateByPrimaryKey(cpLanmu2List.get(i));
			}
		} 
	}
*/
	
	
	public List<CpLanmuPicture> findCpLanmuGroupPic(List<CpLanmuPicture> lanMuPicDetail,HttpServletRequest request) throws Exception {
		for (int i = 0; i < lanMuPicDetail.size(); i++) {
			CpLanmuGroupPic cpLanmuGroupPic = cpPicGroupMapper
					.findGroupPicByGroupId(lanMuPicDetail.get(i).getGroupId());
			if (cpLanmuGroupPic != null) {
				String allpath = cpLanmuGroupPic.getAllpath();
				if (StringUtils.isNotBlank(allpath)) {
					String allPath = allpath.replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)), ipAdd);
					cpLanmuGroupPic.setAllpath(allPath);
				}
			}
			lanMuPicDetail.get(i).setCpLanmuGroupPic(cpLanmuGroupPic);
		}
		return lanMuPicDetail;
	}
	
	/**
	 * @description 栏目管理中的图片，有原来的小图改为水印中图，主要是增加 type = 4,这个接口可以尝试复用替换上一个
	 * @param lanMuPicDetail
	 * @return
	 * @throws Exception 
	 */
	public List<CpLanmuPicture> findCpLanmuGroupPic_1(List<CpLanmuPicture> lanMuPicDetail,int type,HttpServletRequest request) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("type", type);
		for (int i = 0; i < lanMuPicDetail.size(); i++) {
			map.put("id", lanMuPicDetail.get(i).getGroupId());
			CpLanmuGroupPic cpLanmuGroupPic = cpPicGroupMapper
					.findGroupPicByGroupId_1(map);
			if (cpLanmuGroupPic != null) {
				String allpath = cpLanmuGroupPic.getAllpath();
				if (StringUtils.isNotBlank(allpath)) {
					String allPath = allpath.replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)), ipAdd);
					cpLanmuGroupPic.setAllpath(allPath);
				}
			}
			lanMuPicDetail.get(i).setCpLanmuGroupPic(cpLanmuGroupPic);
		}
		return lanMuPicDetail;
	}

	public CpLanmu lanMuPicPreview(CpLanmu cpLanmu,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		if (cpLanmu.getPid() == null) {
			List<CpLanmuPicture> lanMuPicDetail = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getId());
//			List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(lanMuPicDetail);
			List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(lanMuPicDetail,4,request);
			cpLanmu.setCpLanmuPictures(findCpLanmuGroupPic);
			 return cpLanmu;
		}
		List<CpLanmuPicture> lanMuPicDetail = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getCpLanmu1().getId());
//		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(lanMuPicDetail);
		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(lanMuPicDetail,4,request);
		cpLanmu.getCpLanmu1().setCpLanmuPictures(findCpLanmuGroupPic);
		List<CpLanmuPicture> lanMuPicDetail2 = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getCpLanmu2().getId());
		List<CpLanmuPicture> findCpLanmuGroupPic2 = findCpLanmuGroupPic_1(lanMuPicDetail2,4,request);
		cpLanmu.getCpLanmu2().setCpLanmuPictures(findCpLanmuGroupPic2);
		return cpLanmu;
	}

	public Map<String, Integer> findTopicName(Integer lanmuId) {
		return cpLanmuPictureMapper.findTopicName(lanmuId);
	}

	public String addMoreLanmuPic(String lanmuId, String id, String groupIds) {
		if (StringUtils.isNotBlank(id)) {
			CpLanmuPictureExample example = new CpLanmuPictureExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(Integer.parseInt(id));
			List<CpLanmuPicture> selectByExample = cpLanmuPictureMapper.selectByExample(example);
			if (selectByExample.size()>0) {
				CpLanmuPicture cpLanmuPicture = new CpLanmuPicture();
				cpLanmuPicture.setPotision(100);
				cpLanmuPicture.setGroupId(Integer.parseInt(groupIds));
				cpLanmuPicture.setLanmuId(Integer.parseInt(lanmuId));
				cpLanmuPicture.setId(Integer.parseInt(id));
				cpLanmuPictureMapper.updateByPrimaryKeySelective(cpLanmuPicture);
				return "SUCCESS";
			}
		}
		String[] split = groupIds.split(",");
		CpLanmuPicture cpLanmuPicture = new CpLanmuPicture();
		cpLanmuPicture.setLanmuId(Integer.parseInt(lanmuId));
		cpLanmuPicture.setPotision(100);
		for (String groupId : split) {
			cpLanmuPicture.setGroupId(Integer.parseInt(groupId));
			CpLanmuPictureExample example = new CpLanmuPictureExample();
			Criteria criteria = example.createCriteria();
			criteria.andLanmuIdEqualTo(Integer.parseInt(lanmuId));
			criteria.andGroupIdEqualTo(Integer.parseInt(groupId));
			List<CpLanmuPicture> selectByExample = cpLanmuPictureMapper.selectByExample(example);
			if(selectByExample.size() > 0){//判断稿件是否存在
				cpLanmuPictureMapper.deleteByExample(example);//如果存在就删除
			}
			cpLanmuPictureMapper.insertSelective(cpLanmuPicture);
		}
		return "SUCCESS";
	}
	public void addLanmuPicWithAdv(CpLanmu cpLanmu, CpLanmulist cpLanmulist) {
		if (cpLanmulist.getNoticelist().size()>0) {
			noticeService.saveLanmuAdv(cpLanmulist.getNoticelist());
		}
		 cpLanmulist.getCpLanmu().add(cpLanmu);
		lanmuService.editLanmu(cpLanmulist);
	}

	public CpLanmu  showLanmuWithAdv(CpLanmu cpLanmu, Integer topicId,HttpServletRequest request) throws Exception {
		List<CpNotice> cpNoticeList=new ArrayList<CpNotice>();
		if (topicId!=null) {
			cpNoticeList=noticeService.findNoticeByTopicId(topicId);
		}
		List<CpLanmuPicture> lanMuPicDetail = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getId());
//		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(lanMuPicDetail);
		List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(lanMuPicDetail,2,request);
		if (cpNoticeList.size()>0) {
			cpLanmu.setCpNoticesList(cpNoticeList);
		}
		cpLanmu.setCpLanmuPictures(findCpLanmuGroupPic);
		return cpLanmu;
	}

	public List<CpNotice> showMoreLanmuWithAdv(Integer topicId) {
		return noticeService.showMoreLanmuWithAdv(topicId);
	}
	
	//修改，将栏目中预览的图片由小图改为水印中图（type = 4）
	public CpLanmu lanMuPicWithNoticePreview(CpLanmu cpLanmu,HttpServletRequest request) throws Exception {
			List<CpLanmuPicture> lanMuPicDetail = cpLanmuPictureMapper.lanMuPicDetail(cpLanmu.getId());
//			List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic(lanMuPicDetail);
			List<CpLanmuPicture> findCpLanmuGroupPic = findCpLanmuGroupPic_1(lanMuPicDetail,4,request);
			cpLanmu.setCpLanmuPictures(findCpLanmuGroupPic);
			 List<CpNotice>cpNoticesList = noticeService.findPreview(cpLanmu.getTopicId());
			cpLanmu.setCpNoticesList(cpNoticesList);
			 return cpLanmu;
}
}