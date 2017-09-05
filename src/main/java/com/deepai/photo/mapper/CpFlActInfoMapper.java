package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpFlActInfo;
import com.deepai.photo.bean.CpFlActInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpFlActInfoMapper {
    int countByExample(CpFlActInfoExample example);

    int deleteByExample(CpFlActInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpFlActInfo record);

    int insertSelective(CpFlActInfo record);

    List<CpFlActInfo> selectByExample(CpFlActInfoExample example);

    CpFlActInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpFlActInfo record, @Param("example") CpFlActInfoExample example);

    int updateByExample(@Param("record") CpFlActInfo record, @Param("example") CpFlActInfoExample example);

    int updateByPrimaryKeySelective(CpFlActInfo record);

    int updateByPrimaryKey(CpFlActInfo record);
}