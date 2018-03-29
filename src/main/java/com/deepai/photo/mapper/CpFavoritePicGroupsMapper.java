package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import com.deepai.photo.bean.CpFavoritePicGroups;

public interface CpFavoritePicGroupsMapper {
	
	//查询稿件点赞
	CpFavoritePicGroups getFavoriteCountByUserIdAndGroupId(CpFavoritePicGroups cpFavoritePicGroups);
	
	//插入点赞记录
	int insertSelective(CpFavoritePicGroups cpPicGroupThumbsup); 
	
	//修改稿件点赞状态
    int updateFavoritePicGroupsStatus(CpFavoritePicGroups cpFavoritePicGroups)throws Exception;
    
    //查询我的稿件
  	List<Map<String,Object>> getFavoriteCountByUserId(CpFavoritePicGroups cpFavoritePicGroups);
    
    
}
