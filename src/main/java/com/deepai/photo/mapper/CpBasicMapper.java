package com.deepai.photo.mapper;

import java.util.List;
import java.util.Map;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;


public interface CpBasicMapper {
	//根据ID批量删除子库
    public Integer delSiteByIds(Map map)throws Exception;
    
    //根据条件查询子库
    public List<Map<String,Object>> selectSiteByQuery(Map map)throws Exception;
    
    //根据条件查询权限
    public List<Map<String,Object>> selectRightByQuery(Map map)throws Exception;
   
    //根据条件查询角色
    public List<Map<String,Object>> selectRoleByQuery(Map map)throws Exception;
    
    //根据角色ID查其分配权限
    public List<Map<String,Object>> selectRightByRoleId(int roleId)throws Exception;
    
    //批量插入角色权限（为角色分配多个权限） 
    public Integer insertRoleRightBatch(Map map)throws Exception;
  
    //批量修改角色权限（为角色分配多个权限） 
    public Integer updateRRBatch(Map map)throws Exception;
    
    //根据ID批量删除角色
    public Integer delRoleByIds(Map map)throws Exception;
    
    //根据条件查询敏感词
    public List<Map<String,Object>> selectSstvWordByQuery(Map map)throws Exception;
   
    //根据条件查询检索词
    public List<Map<String,Object>> selectScWordByQuery(Map map)throws Exception;
    
    //根据条件查询用户信息
    public List<Map<String,Object>> selectUserByQuery(Map map)throws Exception;
    //根据条件查询用户信息
    public List<Map<String,Object>> selectUserByQuery1(Map map)throws Exception;
    public Integer selectUserByQuery1Count(Map map)throws Exception;
    
    //批量插入用户权限（为用户分配多个权限） 
    public Integer insertUserRightBatch(Map map)throws Exception;
   
    //批量插入用户角色（为用户分配多个角色） 
    public Integer insertUserRoleBatch(Map map)throws Exception;
    
    //批量插入用户角色（为多个用户分配多个权限） 
    public Integer insertUsersRolesBatch(Map map)throws Exception;
    
    //切换用户查询权限
    public List<Map<String,Object>> selectAllRightByUserId(Map map)throws Exception;
    
    //根据用户ID查询用户所有权限
    public List<Map<String,Object>> selectUserRightById(Map map)throws Exception;
    
    //根据ID批量删除用户
    public Integer delUserByIds(Map map)throws Exception;
   
    //查询分配权限id
    public List<Integer> selectAuthRightIds(Map map)throws Exception;
    
    //校验用户接口权限
    public Integer checkUserRight(Map map)throws Exception;
    
    //根据用户id查询用户角色
    public List<CpRole> selectUserRoleByUId(int userId)throws Exception;
    
    //根据用户名或用户ID获取用户密码
    public CpUser selectPwdByUserName(Map map)throws Exception;
    
    //查询日志
    public List<Map<String,Object>> selectLogByQuery(Map map)throws Exception;
    public List<Map<String,Object>> selectUserLoginLog(Map map)throws Exception;
    public List<Map<String,Object>> selectUserOpeLog(Map map)throws Exception;
    
  //根据ID批量删除配置
    public Integer delConfigsByIds(Map map)throws Exception;

	public void upLoginTime(Map<String, Object> param);

	public void LockUser(Map<String, Object> param);
	public void lockTimeClear(Map<String, Object> param);

	public void removeUser(Map<String, Object> param);

	public void updateDelUser(Map<String, Object> param);

	public void insOther(Map<String, Object> param);
	
	public List<Map<String, Object>> selectUserByLogin(Map map);

	public List<Map<String, Object>> selectUserByCreate(Map map);

	public List<Map<String, Object>> getUserAll(Map map)throws Exception;
}