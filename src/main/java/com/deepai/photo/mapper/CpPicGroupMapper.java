package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpFavoriteFolderPic;
import com.deepai.photo.bean.CpGroupPush;
import com.deepai.photo.bean.CpLanmuGroupPic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupExample;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPicturePrice;

public interface CpPicGroupMapper {
    int countByExample(CpPicGroupExample example);

    int deleteByExample(CpPicGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicGroup record);

    int insertSelective(CpPicGroup record);

    List<CpPicGroup> selectByExample(CpPicGroupExample example);

    CpPicGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPicGroup record, @Param("example") CpPicGroupExample example);

    int updateByExample(@Param("record") CpPicGroup record, @Param("example") CpPicGroupExample example);

    int updateByPrimaryKeySelective(CpPicGroup record);

    int updateByPrimaryKey(CpPicGroup record);
    

    List<String> findPicPathByGourpId(int id);

	List<CpPicGroup> shoutu();

	void setPrice(CpPicturePrice cpPicturePrice);

	/**
	 * @description 栏目管理中的图片，默认类型为 type=2，是静态写死的
	 * @param lanMuPicDetail
	 * @return
	 */
	CpLanmuGroupPic findGroupPicByGroupId(Integer groupId);

	/**
	 * @description 栏目管理中的图片，有原来的小图改为水印中图，主要是将 type 转为动态类型,这个接口可以尝试复用替换上一个
	 * @param param 中主要包含两个参数，id，type
	 * @return
	 */
	CpLanmuGroupPic findGroupPicByGroupId_1(Map<String, Integer> param);
	
	List<CpPicture> showPicByGroupId(int groupId);

	CpFavoriteFolderPic findGroupPicByPicId(int picid);

	void updateGroupStatus(@Param("groupId")Integer groupId, @Param("date")Date date, @Param("user")String user);
	
	CpPicGroup selectByGroupId(Integer groupId);

	CpGroupPush selectPushByGroupId(Integer gId);

	void insertGroupPush(CpGroupPush gPush);

//	ch add by liu.jinfeng@2017年9月4日 下午3:59:07
	void updateByGroupId(Integer gId);

}