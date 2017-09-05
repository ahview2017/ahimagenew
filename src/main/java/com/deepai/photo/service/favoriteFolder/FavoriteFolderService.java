package com.deepai.photo.service.favoriteFolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpFavoriteFolder;
import com.deepai.photo.bean.CpFavoriteFolderExample;
import com.deepai.photo.bean.CpFavoriteFolderExample.Criteria;
import com.deepai.photo.bean.CpFavoriteFolderPic;
import com.deepai.photo.mapper.CpFavoriteFolderMapper;
import com.deepai.photo.mapper.CpFavoriteFolderPicMapper;

/**
 * * @author huqiankai: 
 * * 
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class FavoriteFolderService {
	@Resource
	private CpFavoriteFolderMapper cpFavoriteFolderMapper;
	@Resource
	private CpFavoriteFolderPicMapper cpFavoriteFolderPicMapper;
	public void addFavoriteFolder(CpFavoriteFolder cpFavoriteFolder) {
		cpFavoriteFolderMapper.insertSelective(cpFavoriteFolder);
	}

	public List<CpFavoriteFolder> showAllFavoriteFolder(Integer uid) {
		CpFavoriteFolderExample example = new CpFavoriteFolderExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateIdEqualTo(uid);
		 List<CpFavoriteFolder> CpFavoriteFolderList = cpFavoriteFolderMapper.selectByExample(example);
		 for (int i = 0; i < CpFavoriteFolderList.size(); i++) {
			 CpFavoriteFolder cpFavoriteFolder = CpFavoriteFolderList.get(i);
			 cpFavoriteFolder.setUid(uid);
	 List<CpFavoriteFolderPic>CpFavoriteFolderPicLlist =cpFavoriteFolderPicMapper.showAllFavoriteFolder(cpFavoriteFolder);
	 CpFavoriteFolderList.get(i).setCpFavoriteFolderPics(CpFavoriteFolderPicLlist);
		}
		 return CpFavoriteFolderList;
	}

	public void upFavoriteFolder(CpFavoriteFolder cpFavoriteFolder) {
		cpFavoriteFolderMapper.updateByPrimaryKeySelective(cpFavoriteFolder);
	}

	public void delAllFavoriteFolder(String i) {
		cpFavoriteFolderMapper.deleteByPrimaryKey(Integer.parseInt(i));
	}

	public List<CpFavoriteFolder> showAllFavoriteFolderName(Integer uid) {
		List<String>list=new ArrayList<String>();
		CpFavoriteFolderExample example = new CpFavoriteFolderExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateIdEqualTo(uid);
		 List<CpFavoriteFolder> CpFavoriteFolderList = cpFavoriteFolderMapper.selectByExample(example);
		 return CpFavoriteFolderList;
	}


}
