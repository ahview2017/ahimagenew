package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpFavoriteFolder;
import com.deepai.photo.bean.CpFavoriteFolderPic;
import com.deepai.photo.bean.CpFavoriteFolderPicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpFavoriteFolderPicMapper {
    int countByExample(CpFavoriteFolderPicExample example);

    int deleteByExample(CpFavoriteFolderPicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpFavoriteFolderPic record);

    int insertSelective(CpFavoriteFolderPic record);

    List<CpFavoriteFolderPic> selectByExample(CpFavoriteFolderPicExample example);

    CpFavoriteFolderPic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpFavoriteFolderPic record, @Param("example") CpFavoriteFolderPicExample example);

    int updateByExample(@Param("record") CpFavoriteFolderPic record, @Param("example") CpFavoriteFolderPicExample example);

    int updateByPrimaryKeySelective(CpFavoriteFolderPic record);

    int updateByPrimaryKey(CpFavoriteFolderPic record);

	List<CpFavoriteFolderPic> showAllFavoriteFolder(CpFavoriteFolder cpFavoriteFolder);
}