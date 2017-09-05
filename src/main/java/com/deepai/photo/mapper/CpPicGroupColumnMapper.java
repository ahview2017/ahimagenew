package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpLanmuGroupPic;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupColumn;
import com.deepai.photo.bean.CpPicture;

public interface CpPicGroupColumnMapper {
	
	/**
	 * 获取衍生栏目组图和首页轮训图
	 * @param columnId
	 * @param maxSignNum
	 * @return
	 */
	List<Map<String, Object>> getGroupCarouselPicList(@Param("columnId")String columnId, @Param("maxSignNum")Integer maxSignNum);
	/**
	 * 查询最新栏目组图
	 * @param columnId
	 * @return
	 */
	List<Map<String ,Object>> selectNewestPicGroup(String columnId);
	/**
	 * 获取单个栏目下所有组图（签发时间）
	 * @param columnId
	 * @return
	 */
	List<Map<String, Object>> getGroupPicList(String columnId);
	
	List<CpPicGroupColumn> selectGroupByColumn();
	/**
	 * 
	 * @param id 组图id
	 * @param picType 图片类型  0  大图  1 中图, 2 小图, 3 原始图   4 水印图
	 * @return
	 */
	CpLanmuGroupPic findGroupPicByGroupId(@Param("id")Integer id, @Param("picType")Integer picType);
	
	List<CpPicGroupColumn> selectDeriGroupByColumnId(String columnId);
	
	List<CpPicGroupColumn> selectOrderGroupByColumnId(@Param("columnId")Integer columnId,
			@Param("showNum")Integer showNum);
	
	List<CpPicGroupColumn> selectGroupByColumnIdAndTime(String columnId);
	
	List<CpPicture> selectPicPictureByGroupId(Integer groupId);
	
	List<Integer> selectOrderGroupIdByColumnId(@Param("columnId")Integer columnId,
			@Param("startPage")Integer startPage, @Param("maxPage")Integer maxPage);
	
	CpPicGroup selectGroupByGroupId(Integer groupId);
	
	Integer getGroupCountByColumn(@Param("columnId")Integer columnId);
	
	List<CpPicGroupColumn> selectByGroupId(@Param("columnId")Integer columnId, @Param("signPosition")Integer signPosition);
	
	void updateCpPicGroupColumn(@Param("signId") Integer signId, @Param("columnId") Integer columnId, @Param("groupId") Integer groupId);
	
	CpPicGroupColumn selectByColumnIdAndSgin(@Param("columnId") Integer columnId, @Param("signId") Integer signId);
	
	int getContByColumnIdAndSignId(@Param("columnId")Integer columnId, @Param("signId")Integer signId, @Param("langType")Integer langType);
	//根据稿件Id和栏目id修改status
	void updateByGroupIdAndColumnId(@Param("groupId")int groupId, @Param("columnId")Integer columnId);
	

	
	
	
	
	
	
	
	

}
