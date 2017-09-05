package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpOldpictureExcelInfo;
import com.deepai.photo.bean.CpOldpictureExcelInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpOldpictureExcelInfoMapper {
    int countByExample(CpOldpictureExcelInfoExample example);

    int deleteByExample(CpOldpictureExcelInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpOldpictureExcelInfo record);

    int insertSelective(CpOldpictureExcelInfo record);

    List<CpOldpictureExcelInfo> selectByExample(CpOldpictureExcelInfoExample example);

    CpOldpictureExcelInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpOldpictureExcelInfo record, @Param("example") CpOldpictureExcelInfoExample example);

    int updateByExample(@Param("record") CpOldpictureExcelInfo record, @Param("example") CpOldpictureExcelInfoExample example);

    int updateByPrimaryKeySelective(CpOldpictureExcelInfo record);

    int updateByPrimaryKey(CpOldpictureExcelInfo record);
    
    int updateStorageNumById(Integer id);
    /**
     * 获取数据表中最大的id+1
     *   */
    Integer getMaxId();
}