package com.deepai.photo.mapper;

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpUserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CpUserMapper {
    int countByExample(CpUserExample example);

    int deleteByExample(CpUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpUser record);

    int insertSelective(CpUser record);

    List<CpUser> selectByExample(CpUserExample example);

    CpUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpUser record, @Param("example") CpUserExample example);

    int updateByExample(@Param("record") CpUser record, @Param("example") CpUserExample example);
    
    int updateByUserNameSelective(CpUser record);
    
    int updateByPrimaryKeySelective(CpUser record);
    int updateByPrimaryKeySelectiveSelf(CpUser record);

    int updateByPrimaryKey(CpUser record);

	 String  findPhoneByUid(int i);
  
	 String findmailByUid(int i);

	 String findUserNameByUid(int i);
     int findUidByUname(String uname);
	CpUser selectDownloadDetail(int parseInt);

	CpUser findUserByUserName(String userName);
	
	List<CpUser> findUserByBindEmail(String emailBind);
	
	List<CpUser> findUserByBindTel(String telBind);

	List<Integer> findUidByTeamId(String i);

	List<CpUser> findUserByQAndA(Map<String, Object> map);

}