package com.deepai.photo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpPicGroupThumbsUp;

public interface CpPicGroupThumbsUpMapper {
	//查询稿件点赞总数
	int getGroupThumbsUpCount(@Param("groupId")Integer groupId);
	
	//查询稿件点赞总数
	int getThumbsUpCountByIpAndGroupId(Map<Object,Object> map);
	
	//插入点赞记录
	int insertSelective(CpPicGroupThumbsUp cpPicGroupThumbsup);
	
	//修改稿件点赞状态
    int updateThumbsUpStatus(Map<Object,Object> map)throws Exception;
    
    
}
