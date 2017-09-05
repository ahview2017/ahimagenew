package com.deepai.photo.mapper;

import java.util.Map;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpSensitiveWord;


public interface NumOrderMapper {
	//插入一条敏感词并生成排序号
    public int insertSstwOrder(CpSensitiveWord record)throws Exception;
    
    //部分排序号加1
    public int addOneOrderSstw(Map map)throws Exception;
    
    //部分排序号减1
    public int minusOneOrderSstw(Map map)throws Exception;
    
    //角色
    //插入一条角色并生成排序号
    public int insertRoleOrder(CpRole record)throws Exception;
    
    //部分排序号加1
    public int addOneOrderRole(Map map)throws Exception;
    
    //部分排序号减1
    public int minusOneOrderRole(Map map)throws Exception;
    //查询角色最大排序号
    public int selectRoleMaxUnm()throws Exception;
    //查询敏感词最大排序号
    public int selectSstvMaxUnm()throws Exception;
}