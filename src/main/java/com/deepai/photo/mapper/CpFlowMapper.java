package com.deepai.photo.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpProofread;


public interface CpFlowMapper {

    //根据条件查询校审配置 
    public List<CpProofread> selectProofreadByQuery(Map map)throws Exception;
//    public List<CpProofread> selectProofreadByQuery1(Map map)throws Exception;
    
    //根据ID批量删除校审配置
    public Integer delProofreadByIds(Map map)throws Exception;
    
    //查询最大的时间
    public Date selectMaxDate()throws Exception;
    
    //批量插入值班人员
    public Integer insertDutyUserBatch(Map map)throws Exception;
    
    //将过期校审配置设置为不启用
    public Integer changeUnEnabled()throws Exception;
    
    //获取当前启用的校审配置 
    public List<CpProofread> selectNowProofread(@Param("siteId")Integer siteId)throws Exception;
    
    //删除稿件已有分类
    public Integer delGroupCategory(int groupId)throws Exception;
    
    //为稿件添加稿件分类
    public void insertGroupCategroy(Map map)throws Exception;
    //为稿件添加签发分类
    public void insertGroupCategroy1(Map map)throws Exception;
  
    //为稿件添加签发分类
    public void insertGroupTopic(Map map)throws Exception;
    
    //批量添加稿件日志
    public void insertFlowLogBatch(Map map)throws Exception;
    
    //修改签发类型位置为空
    public void updateCateBleak(Map map)throws Exception;

	public List<CpProofread> proofreadSearch(Map map)throws Exception;
}