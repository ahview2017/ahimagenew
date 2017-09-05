package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpOrderDetail;
import com.deepai.photo.bean.CpOrderDetailExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpOrderDetailMapper {
    int countByExample(CpOrderDetailExample example);

    int deleteByExample(CpOrderDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpOrderDetail record);

    int insertSelective(CpOrderDetail record);

    List<CpOrderDetail> selectByExample(CpOrderDetailExample example);

    CpOrderDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpOrderDetail record, @Param("example") CpOrderDetailExample example);

    int updateByExample(@Param("record") CpOrderDetail record, @Param("example") CpOrderDetailExample example);

    int updateByPrimaryKeySelective(CpOrderDetail record);

    int updateByPrimaryKey(CpOrderDetail record);
    
    List<Map<String, Object>> getDownListByOrderId(@Param("orderId")int orderId ,@Param("userId") int userId );
    List<Integer> getPicIdsByDetailId(@Param("detailIds")int[] detailIds ,@Param("userId") int userId);
}