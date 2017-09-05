package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpAdvPosition;
import com.deepai.photo.bean.CpAdvPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpAdvPositionMapper {
    int countByExample(CpAdvPositionExample example);

    int deleteByExample(CpAdvPositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpAdvPosition record);

    int insertSelective(CpAdvPosition record);

    List<CpAdvPosition> selectByExample(CpAdvPositionExample example);

    CpAdvPosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpAdvPosition record, @Param("example") CpAdvPositionExample example);

    int updateByExample(@Param("record") CpAdvPosition record, @Param("example") CpAdvPositionExample example);

    int updateByPrimaryKeySelective(CpAdvPosition record);

    int updateByPrimaryKey(CpAdvPosition record);

	void postionToBig(CpAdvPosition cpAdvPosition);

	void postionToSmall(CpAdvPosition cpAdvPosition);

	void chang0(CpAdvPosition cpAdvPosition);

	List<CpAdvPosition> showToHomePage(int site);

	void changSomePostion(CpAdvPosition cpAdvPosition);

	List<CpAdvPosition> showToEnAdver(@Param("siteId")Integer siteId, @Param("langType")Integer langType);
}