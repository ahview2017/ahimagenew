package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpOldpictureExcelList;
import com.deepai.photo.bean.CpOldpictureExcelListExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpOldpictureExcelListMapper {
    int countByExample(CpOldpictureExcelListExample example);

    int deleteByExample(CpOldpictureExcelListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpOldpictureExcelList record);

    int insertSelective(CpOldpictureExcelList record);

    List<CpOldpictureExcelList> selectByExampleWithBLOBs(CpOldpictureExcelListExample example);

    List<CpOldpictureExcelList> selectByExample(CpOldpictureExcelListExample example);

    CpOldpictureExcelList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpOldpictureExcelList record, @Param("example") CpOldpictureExcelListExample example);

    int updateByExampleWithBLOBs(@Param("record") CpOldpictureExcelList record, @Param("example") CpOldpictureExcelListExample example);

    int updateByExample(@Param("record") CpOldpictureExcelList record, @Param("example") CpOldpictureExcelListExample example);

    int updateByPrimaryKeySelective(CpOldpictureExcelList record);

    int updateByPrimaryKeyWithBLOBs(CpOldpictureExcelList record);

    int updateByPrimaryKey(CpOldpictureExcelList record);
    
    /**
     * 批量插入
     *   */
    int insertList(@Param("list")List<CpOldpictureExcelList> list);
    
    /**
	 * 查询zip中excel中的图片数据
	 */
    List<Map<String, Object>> getExcelPhotoExcelList(@Param("map")Map<String, Object> map);
}