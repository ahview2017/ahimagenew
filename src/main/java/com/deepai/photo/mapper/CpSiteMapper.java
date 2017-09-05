package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpSite;
import com.deepai.photo.bean.CpSiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpSiteMapper {
    int countByExample(CpSiteExample example);

    int deleteByExample(CpSiteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpSite record);

    int insertSelective(CpSite record);

    List<CpSite> selectByExample(CpSiteExample example);

    CpSite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpSite record, @Param("example") CpSiteExample example);

    int updateByExample(@Param("record") CpSite record, @Param("example") CpSiteExample example);

    int updateByPrimaryKeySelective(CpSite record);

    int updateByPrimaryKey(CpSite record);
}