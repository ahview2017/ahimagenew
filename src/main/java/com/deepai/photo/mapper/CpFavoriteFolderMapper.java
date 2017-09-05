package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpFavoriteFolder;
import com.deepai.photo.bean.CpFavoriteFolderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpFavoriteFolderMapper {
    int countByExample(CpFavoriteFolderExample example);

    int deleteByExample(CpFavoriteFolderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpFavoriteFolder record);

    int insertSelective(CpFavoriteFolder record);

    List<CpFavoriteFolder> selectByExample(CpFavoriteFolderExample example);

    CpFavoriteFolder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpFavoriteFolder record, @Param("example") CpFavoriteFolderExample example);

    int updateByExample(@Param("record") CpFavoriteFolder record, @Param("example") CpFavoriteFolderExample example);

    int updateByPrimaryKeySelective(CpFavoriteFolder record);

    int updateByPrimaryKey(CpFavoriteFolder record);
}