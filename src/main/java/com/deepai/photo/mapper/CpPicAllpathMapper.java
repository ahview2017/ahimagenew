package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicAllpathExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpPicAllpathMapper {
    int countByExample(CpPicAllpathExample example);

    int deleteByExample(CpPicAllpathExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpPicAllpath record);

    int insertSelective(CpPicAllpath record);

    List<CpPicAllpath> selectByExample(CpPicAllpathExample example);

    CpPicAllpath selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpPicAllpath record, @Param("example") CpPicAllpathExample example);

    int updateByExample(@Param("record") CpPicAllpath record, @Param("example") CpPicAllpathExample example);

    int updateByPrimaryKeySelective(CpPicAllpath record);

    int updateByPrimaryKey(CpPicAllpath record);

	List<CpPicAllpath> selectByPictureId(Integer picId);
}