package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpDownloadLevel;
import com.deepai.photo.bean.CpDownloadLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpDownloadLevelMapper {
    int countByExample(CpDownloadLevelExample example);

    int deleteByExample(CpDownloadLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpDownloadLevel record);

    int insertSelective(CpDownloadLevel record);

    List<CpDownloadLevel> selectByExample(CpDownloadLevelExample example);

    CpDownloadLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpDownloadLevel record, @Param("example") CpDownloadLevelExample example);

    int updateByExample(@Param("record") CpDownloadLevel record, @Param("example") CpDownloadLevelExample example);

    int updateByPrimaryKeySelective(CpDownloadLevel record);

    int updateByPrimaryKey(CpDownloadLevel record);
}