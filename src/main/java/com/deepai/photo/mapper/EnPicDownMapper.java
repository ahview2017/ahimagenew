package com.deepai.photo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deepai.photo.bean.CpPicture;

public interface EnPicDownMapper {

	List<CpPicture> selectByPrimaryKey(@Param("picIds")String picIds);

	List<CpPicture> selectPicByGroupId(@Param("groupId")Integer groupId);

}
