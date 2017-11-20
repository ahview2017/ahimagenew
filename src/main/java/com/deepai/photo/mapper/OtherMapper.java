package com.deepai.photo.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpProofread;
import com.deepai.photo.bean.CpUser;


public interface OtherMapper {
    //查询所有敏感词
    public List<String> selectAllSstvWord()throws Exception;
    //查询最后一个自动分配的人
    public String selectAssignUser()throws Exception;
    
    public void updateGroupPicNotDel(@Param("groupId")Integer groupId)throws Exception;
    public void updatePicGroupId(@Param("pics")List<CpPicture> pics)throws Exception;
    
    public List<Map<String,Object>> selectPhotoUser(@Param("uName")String uName)throws Exception;
    
    public List<Map<String,Object>> selectPhotographer()throws Exception;
    
    public List<Map<String,Object>> selectArtist()throws Exception;
    
    public Map<String,Object> selectPhotographerByUserId(@Param("userId")Integer userId)throws Exception;
    
    public List<Map<String,Object>> selectGroupComplex(Map map)throws Exception;
    public Integer selectGoodsPrice(Integer goodsType)throws Exception;   
    public Integer checkIdCard(@Param("idCard")String idCard,@Param("id")Integer id)throws Exception; 
    public Integer selectOrderNum(@Param("sequence")String sequence)throws Exception;
    public void resetSequence()throws Exception;   
    
    public CpProofread checkPfdDate(@Param("beginDate")Date beginDate,@Param("endDate")Date endDate, @Param("langType")Integer langType)throws Exception;   
    
}