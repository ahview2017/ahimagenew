package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpPictureMapper {
    int countByExample(CpPictureExample example);

    int deleteByExample(CpPictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicture record);

    int insertSelective(CpPicture record);

    List<CpPicture> selectByExample(CpPictureExample example);

    CpPicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPicture record, @Param("example") CpPictureExample example);

    int updateByExample(@Param("record") CpPicture record, @Param("example") CpPictureExample example);

    int updateByPrimaryKeySelective(CpPicture record);

    int updateByPrimaryKey(CpPicture record);

	Map<String, String> FindOrderPicByPid(Integer picId);
}