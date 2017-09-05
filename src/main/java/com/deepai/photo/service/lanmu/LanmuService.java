package com.deepai.photo.service.lanmu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpLanmuExample;
import com.deepai.photo.bean.CpLanmuPicture;
import com.deepai.photo.bean.CpLanmuExample.Criteria;
import com.deepai.photo.bean.CpLanmulist;
import com.deepai.photo.bean.CpTemplate;
import com.deepai.photo.mapper.CpLanmuMapper;
import com.deepai.photo.mapper.CpTemplateMapper;

/**
 * * @author huqiankai: * @date 创建时间：2017年1月12日 下午2:55:23 * @version 1.0 *
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class LanmuService {
	@Autowired
	private CpLanmuMapper cpLanmuMapper;
   
	// 栏目类型（0是有子栏目，1是一图一文，2是轮播图，4是4盏，5是5盏，6是6盏，8是8盏，9是9盏）
	public String add(CpLanmulist cpLanmulist) {
		if (cpLanmulist.getCpLanmu().size() == 1) {
			CpLanmuExample example = new CpLanmuExample();
			Criteria criteria = example.createCriteria();
			criteria.andTopicIdEqualTo(cpLanmulist.getCpLanmu().get(0).getTopicId());
			criteria.andLanmuNoEqualTo(cpLanmulist.getCpLanmu().get(0).getLanmuNo());
			List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
			if (list!=null&&list.size()>0) {
				cpLanmuMapper.changSomePostion(cpLanmulist.getCpLanmu().get(0));
			}
			Integer typeId = cpLanmulist.getCpLanmu().get(0).getTypeId();
			if (typeId != 0 && typeId != 2) {
				cpLanmulist.getCpLanmu().get(0).setZhanshuCount(typeId);
			}
			if (typeId == 2||typeId == 3) {
				cpLanmulist.getCpLanmu().get(0).setZhanshuCount(5);
			}
			cpLanmuMapper.insertSelective(cpLanmulist.getCpLanmu().get(0));
			return "success";
		}
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(cpLanmulist.getCpLanmu().get(0).getTopicId());
		criteria.andLanmuNoEqualTo(cpLanmulist.getCpLanmu().get(0).getLanmuNo());
		List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
		if (list!=null&&list.size()>0) {
			cpLanmuMapper.changSomePostion(cpLanmulist.getCpLanmu().get(0));
		}
		cpLanmulist.getCpLanmu().get(0).setPid(0);
		 cpLanmuMapper.insertSelective(cpLanmulist.getCpLanmu().get(0));
		Integer muId = cpLanmulist.getCpLanmu().get(0).getId();
		cpLanmulist.getCpLanmu().get(1).setPid(muId);
		Integer typeId1 = cpLanmulist.getCpLanmu().get(1).getTypeId();
		if (typeId1 != 0 && typeId1 != 2) {
			cpLanmulist.getCpLanmu().get(1).setZhanshuCount(typeId1);
		}
		if (typeId1 == 2||typeId1 == 3) {
			cpLanmulist.getCpLanmu().get(1).setZhanshuCount(5);
		}
		cpLanmulist.getCpLanmu().get(2).setPid(muId);
		Integer typeId2 = cpLanmulist.getCpLanmu().get(2).getTypeId();
		if (typeId2 != 0 && typeId2 != 2) {
			cpLanmulist.getCpLanmu().get(2).setZhanshuCount(typeId2);
		}
		if (typeId2 == 2||typeId2 == 3) {
			cpLanmulist.getCpLanmu().get(2).setZhanshuCount(5);
		}
		cpLanmuMapper.insertSelective(cpLanmulist.getCpLanmu().get(1));
		cpLanmuMapper.insertSelective(cpLanmulist.getCpLanmu().get(2));
		return "success";
	}

	public void delete(int id) {
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
		cpLanmuMapper.deleteByPrimaryKey(id);
		if (list.get(0).getPid()!=null) {
			example.clear();
			Criteria criteria1 = example.createCriteria();
			criteria1.andPidEqualTo(id);
			cpLanmuMapper.deleteByExample(example);
		}
	}

	public CpLanmu lanMuDetail(int lanmuid) {
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(lanmuid);
		List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
		CpLanmu cpLanmu = list.get(0);
		if (cpLanmu.getPid()==null) {
			return list.get(0);
		}
		example.clear();
		Criteria criteria1 = example.createCriteria();
		criteria1.andPidEqualTo(cpLanmu.getId());
		List<CpLanmu> list2 = cpLanmuMapper.selectByExample(example);
		cpLanmu.setCpLanmu1(list2.get(0));
		cpLanmu.setCpLanmu2(list2.get(1));
		return cpLanmu;
	}
	public List<CpLanmu> showLanMu(int topic_Id) {
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		criteria.andTopicIdEqualTo(topic_Id);
		List<CpLanmu> cpLanmu = cpLanmuMapper.selectByExample(example);
		for (int i = 0; i < cpLanmu.size(); i++) {
			if (cpLanmu.get(i).getPid()!=null&&cpLanmu.get(i).getPid()==0) {			
				List<CpLanmu> cpZiLanMu = findLanmuByPidId(cpLanmu.get(i).getId());
				CpLanmu cpLanmu1 = cpZiLanMu.get(0);
				cpLanmu.remove(cpLanmu1);
				CpLanmu cpLanmu2 = cpZiLanMu.get(1);
				cpLanmu.remove(cpLanmu2);
				cpLanmu.get(i).setCpLanmu1(cpLanmu1);
				cpLanmu.get(i).setCpLanmu2(cpLanmu2);
			}
			cpLanmu.get(i).setPid(topic_Id);
		}
		return cpLanmu;
	}

	public void editLanmu(CpLanmulist cpLanmulist) {
		Integer pid = cpLanmulist.getCpLanmu().get(0).getPid();
		Map<String, Object>map=new HashMap<>();
		CpLanmu cpLanmu = cpLanmulist.getCpLanmu().get(0);
		if (cpLanmu.getCpLanmu1()==null) {
			/*CpLanmuExample example = new CpLanmuExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(cpLanmulist.getCpLanmu().get(0).getId());
			List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
             if (list!=null&&list.size()>0) {
            	 Integer YlanmuNo = list.get(0).getLanmuNo();
            	 Map<String, Object>map= new  HashMap<String, Object>();
            	 Integer lanmuNo = cpLanmulist.getCpLanmu().get(0).getLanmuNo();
            	 map.put("YlanmuNo", YlanmuNo);
            	 map.put("lanmuNO",lanmuNo);
            	 map.put("topicId",cpLanmulist.getCpLanmu().get(0).getId());
					 if (YlanmuNo!=null&&YlanmuNo<lanmuNo) {
						 cpLanmuMapper.postionToBig(map);
					}
					  if (YlanmuNo>lanmuNo ) {
						  cpLanmuMapper.postionToSmall(map);
					}
			}*/
			Integer typeId = cpLanmu.getTypeId();
			if (typeId != 0 && typeId != 2) {
				cpLanmu.setZhanshuCount(typeId);
			}
			if (typeId == 2||typeId == 3) {
				cpLanmu.setZhanshuCount(5);
			}
			 CpLanmuExample example = new CpLanmuExample();
			 example.createCriteria().andIdEqualTo(cpLanmu.getId());
			 List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
			 Integer YlanmuNo = list.get(0).getLanmuNo();
			 Integer lanmuNo = cpLanmu.getLanmuNo();
			  map.put("YlanmuNo", YlanmuNo);
			  map.put("lanmuNo", lanmuNo);
			  map.put("topicId", cpLanmu.getTopicId());
			  if (YlanmuNo!=null&&lanmuNo!=null&&YlanmuNo<lanmuNo) {//位置由小变大
				  cpLanmuMapper.postionToBig(map);
			}else if(YlanmuNo!=null&&lanmuNo!=null&&YlanmuNo>lanmuNo){//位置由大变小
				cpLanmuMapper.postionToSmall(map);
			}
			  cpLanmuMapper.updateByPrimaryKeySelective(cpLanmu);
		}
		else{
		CpLanmu cpLanmu2 = cpLanmulist.getCpLanmu().get(0);
		 CpLanmuExample example = new CpLanmuExample();
		 example.createCriteria().andIdEqualTo(cpLanmu2.getId());
		 List<CpLanmu> list = cpLanmuMapper.selectByExample(example);
		 Integer YlanmuNo = list.get(0).getLanmuNo();
		 Integer lanmuNo = cpLanmu2.getLanmuNo();
		 map.put("YlanmuNo", YlanmuNo);
		  map.put("lanmuNo", lanmuNo);
		  map.put("topicId", cpLanmu2.getTopicId());
		  if (YlanmuNo!=null&&lanmuNo!=null&&YlanmuNo<lanmuNo) {//位置由小变大
			  cpLanmuMapper.postionToBig(map);
		}else if(YlanmuNo!=null&&lanmuNo!=null&&YlanmuNo>lanmuNo){//位置由大变小
			cpLanmuMapper.postionToSmall(map);
		}
		  cpLanmuMapper.updateByPrimaryKeySelective(cpLanmu2);
		Integer typeId1 = cpLanmu2.getCpLanmu1().getTypeId();
		if (typeId1 != 0 && typeId1 != 2) {
			cpLanmu2.getCpLanmu1().setZhanshuCount(typeId1);
		}
		if (typeId1 == 2||typeId1 == 3) {
			cpLanmu2.getCpLanmu1().setZhanshuCount(5);
		}
		cpLanmuMapper.updateByPrimaryKeySelective(cpLanmu2.getCpLanmu1());
		Integer typeId2 = cpLanmu2.getCpLanmu2().getTypeId();
		if (typeId2 != 0 && typeId2 != 2) {
			cpLanmu2.getCpLanmu2().setZhanshuCount(typeId2);
		}
		if (typeId2 == 2||typeId2 == 3) {
			cpLanmu2.getCpLanmu2().setZhanshuCount(5);
		}
		cpLanmuMapper.updateByPrimaryKeySelective(cpLanmu2.getCpLanmu2());
		}
	}

	public List<CpLanmu> findLanmuByPidId(int topicId) {
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(topicId);
		return cpLanmuMapper.selectByExample(example);
	}

	public List<CpLanmu> serachLanmu(String name, int topicId) {
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		name="%"+name+"%";
		criteria.andNameLike(name);
		criteria.andTopicIdEqualTo(topicId);
		return cpLanmuMapper.selectByExample(example);
	}
	public String  findTopicNameByTopicId(int topicId) {
	 return 	cpLanmuMapper.findTopicNameByTopicId(topicId);
	}

	public List<Integer> findLanmu(Integer lanmuId) {
		CpLanmuExample example = new CpLanmuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(lanmuId);
		List<CpLanmu> CpLanmulist = cpLanmuMapper.selectByExample(example);
		CpLanmu cpLanmu = CpLanmulist.get(0);
		List<Integer> list=  new ArrayList<Integer>();
		if (cpLanmu.getPid()==null) {
			list.add(lanmuId);
			return list;
		}
		example.clear();
		Criteria criteria1 = example.createCriteria();
		criteria1.andPidEqualTo(cpLanmu.getPid());
		List<CpLanmu> list2 = cpLanmuMapper.selectByExample(example);
		list.add(cpLanmu.getPid());
		for (CpLanmu cpLanmu2 : list2) {
			if (cpLanmu2.getId()!=lanmuId) {
				list.add(cpLanmu2.getId());
			}
		}
		return list;
	}

}
