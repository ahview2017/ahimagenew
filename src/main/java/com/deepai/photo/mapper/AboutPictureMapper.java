package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;


public interface AboutPictureMapper {
	//根据稿件id修改一审值班人员
	public Integer updateDutyUser(@Param("groupId")Integer groupId,@Param("editorName")String editorName)throws Exception;
	public Integer updateProcessUser(@Param("groupId")Integer groupId,@Param("editorId")Integer editorId,
			@Param("editorName")String editorName)throws Exception;
	//根据ID批量删除下载级别
    public Integer delDownLevelByIds(Map map)throws Exception;
    
    //根据条件查询下载级别
    public List<Map<String,Object>> selectDownLevelByQuery(Map map)throws Exception;
    
    //修改水印默认
    public Integer updateIsDefault(Map map)throws Exception;
    
    //修改水印默认
    public String selectPicPathByType(Map map)throws Exception;
    
    //修改水印默认
    public String selectDeafautWaterPic(int siteId)throws Exception;
    
   //根据ID批量删除水印图片
    public Integer delWaterMarkByIds(Map map)throws Exception;
    
    //根据ID批量删除稿件图片
    public Integer delPictureByIds(Map map)throws Exception;
    
    //根据ID批量删除稿件
    public Integer delGroupByIds(Map map)throws Exception;
    
    //根据权限查询拥有此权限的用户
    public List<Map<String,Object>> selectUserByRight(int rightId,@Param("langType")Integer langType)throws Exception;
    
    //根据权限查询拥有此权限的用户
    public Integer isUserRight(Map map)throws Exception;
    
    //查询旧稿件的所有图片 
    public List<Integer> selectPicIdsByGroup(int sourceGId)throws Exception;
    
    /**
     * 复制原稿的图片，并指向复制出的稿件
     * @param sourceGId 原稿id
     * @param copyGId 复制稿id（存做历史版本）
     * @throws Exception
     */
    public void copyPicsByGroupId(int sourceGId,int copyGId)throws Exception;
    
    //将稿件图片deleteFlag置为1
    public void delPicsByGroupId(int sourceGId)throws Exception;
    
    //查询稿件
    public List<Map<String,Object>> selectGroupsByQuery(Map map)throws Exception;
    //查询稿件历史版本
    public List<Map<String,Object>> selectGroupHistoryVersion(Map map)throws Exception;
    
    //合并-查询稿件基本信息和包含图片
    public List<Map<String,Object>> selectMergeGroup(@Param("gIds")String[] gIds)throws Exception;
    //批量修改稿件狀態
    public void changeStatusBatch(@Param("groups")List<CpPicGroup> groups)throws Exception;
    
    public CpPicGroup selectGroupPics(@Param("groupId")Integer groupId)throws Exception;
    public List<CpPicGroup> selectPicInfo(@Param("picIdArr")String[] picIdArr)throws Exception;
    public List<CpPicGroup> selectPicInfoByOrder(@Param("orderId")Integer orderId)throws Exception;
    public List<CpPicGroup> selectPicInfoByOrderDetail(@Param("picIds")List<Integer> picIds)throws Exception;
    public CpPicGroup selectPicInfoByGroupId(@Param("groupId")Integer groupId)throws Exception;

	public int showNoReadPicGroupNum(int integer);

	public int selectGroupsCountByQuery(Map<String, Object> param);

	public List<Map<String, Object>> selectGroups(Map map)throws Exception;
	public List<Map<String, Object>> selectGroupsOnlySeach(Map map)throws Exception;
	public List<Map<String, Object>> selectSubGroups(Map map)throws Exception;

	public int selectCountGroups(Map param);
	public int selectCountGroupsOnlySeach(Map param);
	public int selectCountSubGroups(Map param);
    
}