package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpPicturePriceExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpPicturePriceMapper {
    int countByExample(CpPicturePriceExample example);

    int deleteByExample(CpPicturePriceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicturePrice record);

    int insertSelective(CpPicturePrice record);

    List<CpPicturePrice> selectByExampleWithBLOBs(CpPicturePriceExample example);

    List<CpPicturePrice> selectByExample(CpPicturePriceExample example);

    CpPicturePrice selectByPrimaryKey(Integer id);
    
    List<Map<String, Object>> selectByIds(@Param("ids") String [] ids);

    int updateByExampleSelective(@Param("record") CpPicturePrice record, @Param("example") CpPicturePriceExample example);

    int updateByExampleWithBLOBs(@Param("record") CpPicturePrice record, @Param("example") CpPicturePriceExample example);

    int updateByExample(@Param("record") CpPicturePrice record, @Param("example") CpPicturePriceExample example);

    int updateByPrimaryKeySelective(CpPicturePrice record);

    int updateByPrimaryKeyWithBLOBs(CpPicturePrice record);

    int updateByPrimaryKey(CpPicturePrice record);
}