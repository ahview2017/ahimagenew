package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpDownloadLevel;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPictureDownloadrecord;

public interface ClientPictureMapper {
    //查询客户端稿件
    public List<Map<String,Object>> selectClientGroup(Map map)throws Exception;
    //查询客户端稿件-更多
    public List<Map<String,Object>> selectMoreGroup(Map map)throws Exception;
    //查询专题图片或新闻图片
    public List<Map<String,Object>> selectPropertiesGroup(Map map)throws Exception;
    //查询稿件详情
    public CpPicGroup selectClientGroupPics(Integer groupId)throws Exception;
    //查询图片详情
    public CpPicGroup selectClientPicture(@Param("groupId")Integer groupId,@Param("pictureId")Integer pictureId)throws Exception;
    //获取用户下载级别
    public CpDownloadLevel selectUserDownLevel(@Param("userId")Integer userId)throws Exception;
    
    
    public List<Map<String,Object>> selectDownPics(@Param("picIdArr")String[] picIdArr,@Param("userId") Integer userId)throws Exception;
    
  
    public Integer insertOrderDetails(Map map)throws Exception;
    
    public Integer insertDownRecords(@Param("recordList")List<CpPictureDownloadrecord> recordList)throws Exception;
    
    public Integer updateLimitDlMun(@Param("type")int type)throws Exception;
    
    public List<Map<String,Object>> selectOrderPics(@Param("orderId")Integer orderId)throws Exception; 
    public List<Map<String,Object>> selectMyDownPics(Map map)throws Exception;
	public List<Map<String, Object>> selectMyDownPicsOneWeek(Map<String, Object> map);
	public List<Map<String, Object>> selectMyDownPicsOneYear(Map<String, Object> map);
	public String findDefWaterPic();
	//下载统计
	public List<CpPictureDownloadrecord> downloadrecordByQuery(CpPictureDownloadrecord cpPictureDownloadrecord);
	//删除
	public void delete(int id); 
}