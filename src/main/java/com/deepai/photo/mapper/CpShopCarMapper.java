package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpShopCar;
import com.deepai.photo.bean.CpShopCarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CpShopCarMapper {
    int countByExample(CpShopCarExample example);

    int deleteByExample(CpShopCarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpShopCar record);

    int insertSelective(CpShopCar record);

    List<CpShopCar> selectByExample(CpShopCarExample example);

    CpShopCar selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpShopCar record, @Param("example") CpShopCarExample example);

    int updateByExample(@Param("record") CpShopCar record, @Param("example") CpShopCarExample example);

    int updateByPrimaryKeySelective(CpShopCar record);

    int updateByPrimaryKey(CpShopCar record);
}