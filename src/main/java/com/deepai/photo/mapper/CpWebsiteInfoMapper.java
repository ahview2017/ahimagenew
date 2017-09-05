package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpWebsiteInfo;
import com.deepai.photo.bean.CpWebsiteInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpWebsiteInfoMapper {
    int countByExample(CpWebsiteInfoExample example);

    int deleteByExample(CpWebsiteInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpWebsiteInfo record);

    int insertSelective(CpWebsiteInfo record);

    List<CpWebsiteInfo> selectByExampleWithBLOBs(CpWebsiteInfoExample example);

    List<CpWebsiteInfo> selectByExample(CpWebsiteInfoExample example);

    CpWebsiteInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpWebsiteInfo record, @Param("example") CpWebsiteInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") CpWebsiteInfo record, @Param("example") CpWebsiteInfoExample example);

    int updateByExample(@Param("record") CpWebsiteInfo record, @Param("example") CpWebsiteInfoExample example);

    int updateByPrimaryKeySelective(CpWebsiteInfo record);

    int updateByPrimaryKeyWithBLOBs(CpWebsiteInfo record);

    int updateByPrimaryKey(CpWebsiteInfo record);

	CpWebsiteInfo showtoedit(Integer id);

	List<CpWebsiteInfo> search(Map map);

	List<CpWebsiteInfo> show(Map map);
}