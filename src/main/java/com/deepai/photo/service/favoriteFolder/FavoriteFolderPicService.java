package com.deepai.photo.service.favoriteFolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpFavoriteFolderPic;
import com.deepai.photo.bean.CpFavoriteFolderPicExample;
import com.deepai.photo.bean.CpFavoriteFolderPicExample.Criteria;
import com.deepai.photo.mapper.CpFavoriteFolderPicMapper;

/**
 * * @author huqiankai:
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class FavoriteFolderPicService {
	@Autowired
	private CpFavoriteFolderPicMapper cpFavoriteFolderPicMapper;

	public void addFavoriteFolderPic(CpFavoriteFolderPic cpFavoriteFolderPic) {
		cpFavoriteFolderPicMapper.insertSelective(cpFavoriteFolderPic);
	}

	public void delFavoriteFolderPic(String i) {
		cpFavoriteFolderPicMapper.deleteByPrimaryKey(Integer.parseInt(i));
	}

	public List<CpFavoriteFolderPic> showFavoriteFolderPic(Integer Userid, int folderId) {
		CpFavoriteFolderPicExample example = new CpFavoriteFolderPicExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(Userid);
		criteria.andFolderIdEqualTo(folderId);
		return cpFavoriteFolderPicMapper.selectByExample(example);
	}

}
