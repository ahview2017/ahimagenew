package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpLanmuGroupPic;
import com.deepai.photo.bean.CpLanmuPicture;
import com.deepai.photo.bean.CpLanmuPictureExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpLanmuPictureMapper {
    int countByExample(CpLanmuPictureExample example);

    int deleteByExample(CpLanmuPictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpLanmuPicture record);

    int insertSelective(CpLanmuPicture record);

    List<CpLanmuPicture> selectByExample(CpLanmuPictureExample example);

    CpLanmuPicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpLanmuPicture record, @Param("example") CpLanmuPictureExample example);

    int updateByExample(@Param("record") CpLanmuPicture record, @Param("example") CpLanmuPictureExample example);

    int updateByPrimaryKeySelective(CpLanmuPicture record);

    int updateByPrimaryKey(CpLanmuPicture record);

	List<CpLanmuPicture> lanMuPicDetail(Integer id);

	List<CpLanmuPicture> lanMuPicMoreDetail(int lanmuid);

	void changePosition(CpLanmuPicture cpLanmuPicture);

	void upPosition(CpLanmuPicture cpLanmuPicture);
	Map<String, Integer> findTopicName(Integer lanmuId);

	CpLanmuGroupPic getGroupPic(@Param("groupId")Integer groupId);
}