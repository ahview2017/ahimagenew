package com.deepai.photo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpFriendshipLink;

public interface CpFriendshipLinkMapper {
	/**
	 * 查询友情连接
	 * 
	 * @param langType
	 * @return
	 */
	List<CpFriendshipLink> selectFriendshipLinkList(@Param("langType")Integer langType);
	/**
	 * 添加友情连接
	 * @param cpFriendshipLink
	 */
	void addFriendshipLink(CpFriendshipLink cpFriendshipLink);
	/**
	 * 删除
	 * @param id
	 */
	void deleteFriendshipLink(int id);
	void editFriendshipLink(CpFriendshipLink cpFriendshipLink);

}
