package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpNoticeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpNoticeMapper {
    int countByExample(CpNoticeExample example);

    int deleteByExample(CpNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpNotice record);

    int insertSelective(CpNotice record);

    List<CpNotice> selectByExample(CpNoticeExample example);

    CpNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpNotice record, @Param("example") CpNoticeExample example);

    int updateByExample(@Param("record") CpNotice record, @Param("example") CpNoticeExample example);

    int updateByPrimaryKeySelective(CpNotice record);

    int updateByPrimaryKey(CpNotice record);


	List<CpNotice> showMoreLanmuWithAdv(Integer topicId);

	void postionToBig(CpNotice cpNotice);

	void postionToSmall(CpNotice cpNotice);

	void chang0(CpNotice cpNotice);

	List<CpNotice> findPreview(Integer topicId);

	void changSomePostion(CpNotice cpNotice);

	List<CpNotice> findNoticeByTopicId(Integer topicId);

	List<CpNotice> showToAdmin(Map map);

	List<CpNotice> showToHomePage(Map map);

	List<CpNotice> search(@Param("map") Map<String, Object> map);
}