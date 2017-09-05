package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpPictureDownloadrecord;
import com.deepai.photo.bean.CpPictureDownloadrecordExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpPictureDownloadrecordMapper {
	int countByExample(CpPictureDownloadrecordExample example);

	int deleteByExample(CpPictureDownloadrecordExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(CpPictureDownloadrecord record);

	int insertSelective(CpPictureDownloadrecord record);

	List<CpPictureDownloadrecord> selectByExample(
			CpPictureDownloadrecordExample example);

	CpPictureDownloadrecord selectByPrimaryKey(Integer id);

	int updateByExampleSelective(
			@Param("record") CpPictureDownloadrecord record,
			@Param("example") CpPictureDownloadrecordExample example);

	int updateByExample(@Param("record") CpPictureDownloadrecord record,
			@Param("example") CpPictureDownloadrecordExample example);

	int updateByPrimaryKeySelective(CpPictureDownloadrecord record);

	int updateByPrimaryKey(CpPictureDownloadrecord record);

	// 获取下载图片列表
	List<Map<String, Object>> getDownloadPictures(Map<String, Object> map);

}