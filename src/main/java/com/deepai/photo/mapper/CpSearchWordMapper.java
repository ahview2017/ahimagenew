package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpSearchWord;
import com.deepai.photo.bean.CpSearchWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpSearchWordMapper {
    int countByExample(CpSearchWordExample example);

    int deleteByExample(CpSearchWordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpSearchWord record);

    int insertSelective(CpSearchWord record);

    List<CpSearchWord> selectByExample(CpSearchWordExample example);

    CpSearchWord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpSearchWord record, @Param("example") CpSearchWordExample example);

    int updateByExample(@Param("record") CpSearchWord record, @Param("example") CpSearchWordExample example);

    int updateByPrimaryKeySelective(CpSearchWord record);

    int updateByPrimaryKey(CpSearchWord record);
}