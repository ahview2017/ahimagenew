package com.deepai.photo.service.adver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpAdvPositionExample;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpAdvPositionExample.Criteria;
import com.deepai.photo.mapper.CpAdvPositionMapper;
import com.deepai.photo.mapper.CpMsgMapper;

/**
 * * @author huqiankai: *
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdverService {
	@Autowired
	private CpAdvPositionMapper cpAdvPositionMapper;
   
	public  List<CpAdvPosition> show(String strWhere, Integer langType) {
	CpAdvPositionExample example = new CpAdvPositionExample();
	Criteria criteria = example.createCriteria();
	if (strWhere!=null) {
		criteria.andNameEqualTo(strWhere);
		example.or().andUrlEqualTo(strWhere);
		example.or().andRemarksEqualTo(strWhere);
		example.or().andPicEqualTo(strWhere);
	}
	criteria.andLangTypeTo(langType);
	example.setOrderByClause("position");
	return cpAdvPositionMapper.selectByExample(example);
	}

	public void add(CpAdvPosition cpAdvPosition) {
//		CpAdvPositionExample example = new CpAdvPositionExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andPositionEqualTo(cpAdvPosition.getPosition());
//		List<CpAdvPosition> list = cpAdvPositionMapper.selectByExample(example);
//		if (list!=null&&list.size()>0) {
//			cpAdvPositionMapper.changSomePostion(cpAdvPosition);
//		}
		cpAdvPositionMapper.insertSelective(cpAdvPosition);
	}

	public void edit(CpAdvPosition cpAdvPosition) {
		
		cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
	}

	public void delete(int id) {
		cpAdvPositionMapper.deleteByPrimaryKey(id);
	}

	public List<CpAdvPosition> search(String searchName,Integer langType) {
		 CpAdvPositionExample example = new CpAdvPositionExample();
		  Criteria criteria = example.createCriteria();
		  searchName="%"+searchName+"%";
		 criteria.andNameLike(searchName);
		 criteria.andLangTypeTo(langType);
		 return	 cpAdvPositionMapper.selectByExample(example);
	}

	public CpAdvPosition showtoedit(Integer id) {
		return cpAdvPositionMapper.selectByPrimaryKey(id);
	}
	/*public void edit1(CpAdvPosition cpAdvPosition, Integer yposition, Integer position) {
		cpAdvPosition.setPosition(position);
		cpAdvPosition.setYposition(yposition);
		cpAdvPositionMapper.postionToBig(cpAdvPosition);
		cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
	}
	public void edit2(CpAdvPosition cpAdvPosition, Integer yposition, Integer position) {
		cpAdvPosition.setPosition(position);
		cpAdvPosition.setYposition(yposition);
		cpAdvPositionMapper.postionToSmall(cpAdvPosition);
		cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
		cpAdvPositionMapper.chang0(cpAdvPosition);
	}*/
	
	public void save(CpAdvPosition cpAdvPosition) {
//		 CpAdvPositionExample example = new CpAdvPositionExample();
//		  Criteria criteria = example.createCriteria();
//		  criteria.andIdEqualTo(cpAdvPosition.getId());
//		  List<CpAdvPosition> CpAdvPositionList = cpAdvPositionMapper.selectByExample(example);
//		  Integer yposition = CpAdvPositionList.get(0).getPosition();
		  Integer position = cpAdvPosition.getPosition();
//		  cpAdvPosition.setYposition(yposition);
//		  if (yposition!=null&&position!=null&&yposition<position) {
//			  cpAdvPositionMapper.postionToBig(cpAdvPosition);
//			  cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
//		}
//		  if (yposition!=null&&position!=null&&yposition>position ) {
//			  cpAdvPositionMapper.postionToSmall(cpAdvPosition);
//			  cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
//			  cpAdvPositionMapper.chang0(cpAdvPosition);
//		}
//		  if (position==null||yposition==position) {
//			  cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
//		}
		  if (position!=null) {
			  cpAdvPositionMapper.updateByPrimaryKeySelective(cpAdvPosition);
		}
		  
	}

	public List<CpAdvPosition> showToHomePage(int i) {
		return cpAdvPositionMapper.showToHomePage(i);
	}

	public List<CpAdvPosition> showToEnAdver(Integer siteId, Integer langType) {
		return cpAdvPositionMapper.showToEnAdver(siteId,langType);
	}


}
