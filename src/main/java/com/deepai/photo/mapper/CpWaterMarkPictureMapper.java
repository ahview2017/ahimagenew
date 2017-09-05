package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpWaterMarkPicture;
import com.deepai.photo.bean.CpWaterMarkPictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpWaterMarkPictureMapper {
    int countByExample(CpWaterMarkPictureExample example);

    int deleteByExample(CpWaterMarkPictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpWaterMarkPicture record);

    int insertSelective(CpWaterMarkPicture record);

    List<CpWaterMarkPicture> selectByExample(CpWaterMarkPictureExample example);

    CpWaterMarkPicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpWaterMarkPicture record, @Param("example") CpWaterMarkPictureExample example);

    int updateByExample(@Param("record") CpWaterMarkPicture record, @Param("example") CpWaterMarkPictureExample example);

    int updateByPrimaryKeySelective(CpWaterMarkPicture record);

    int updateByPrimaryKey(CpWaterMarkPicture record);
}