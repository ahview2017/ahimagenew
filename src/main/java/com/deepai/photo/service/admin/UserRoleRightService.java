package com.deepai.photo.service.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpAuthRightExample;
import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpUserExample;
import com.deepai.photo.bean.CpUserExample.Criteria;
import com.deepai.photo.bean.CpUserRole;
import com.deepai.photo.bean.CpUserRoleExample;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.json.GsonUtil;
import com.deepai.photo.mapper.CpAuthRightMapper;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.NumOrderMapper;
import com.deepai.photo.mapper.CpRightMapper;
import com.deepai.photo.mapper.CpRoleMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.CpUserRoleMapper;


/**
 * @author guoyanhong
 * 用户-角色-权限 相关
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class UserRoleRightService {
	@Autowired
	private CpRightMapper cpRightMapper;
	@Autowired
	private CpAuthRightMapper cpAuthRightMapper;
	@Autowired
	private CpRoleMapper cpRoleMapper;
	@Autowired
	private CpBasicMapper cpBasicMapper;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpUserRoleMapper cpUserRoleMapper;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private NumOrderMapper orderMapper;
	@Autowired
	private NumOrderService orderService;

	/**
	 * 为角色分配权限
	 * @param role 角色信息
	 * @param rightIds 权限ID
	 * @throws Exception
	 */
	public void authRoleRight(int roleId,String rightIds,CpUser admin) throws Exception{
		String rightArr[]=rightIds.split(",");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("rightIds", rightArr);
		param.put("roleId", roleId);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertRoleRightBatch(param);
	}
	
	/**
	 * 为角色分配权限
	 * @param role 角色信息
	 * @param rightIds 权限ID
	 * @throws Exception
	 */
	public void authRoleRight(int roleId,List<Integer> rightIds,CpUser admin) throws Exception{
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("rightIds", rightIds);
		param.put("roleId", roleId);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertRoleRightBatch(param);
	}
	/**
	 * 新增角色，并分配权限
	 * @param role
	 * @param rightIds
	 */
	public void addRoleAndRight(CpRole role,String rightIds,CpUser admin)throws Exception{
//		cpRoleMapper.insertSelective(role);
		orderMapper.insertRoleOrder(role);
		if(StringUtil.isNotBlank(rightIds)){
			authRoleRight(role.getId(), rightIds,admin);
		}
	}
	
	
	/**
	 * 修改角色分配权限
	 * @param role
	 * @param rightIds
	 */
	public void updateRoleRight(int roleId,String rightIds,CpUser admin)throws Exception{
		/*
		//重新授权
		if(StringUtil.isNotBlank(rightIds)){
			CpAuthRightExample example=new CpAuthRightExample();
			example.createCriteria().andTragetIdEqualTo(roleId).andTypeEqualTo(CommonConstant.BYTE0);
			cpAuthRightMapper.deleteByExample(example);//删除角色已有权限
			authRoleRight(roleId, rightIds,admin);
		}*/
		
		//查询已分配权限
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("type", 0);
		param.put("tragetId", roleId);
		//TODO
		List<Integer> olds=cpBasicMapper.selectAuthRightIds(param);
		if(StringUtil.isNotBlank(rightIds)){
			List<Integer> olds_=new ArrayList<Integer>();
			olds_.addAll(olds);
			String rArr[]=rightIds.split(",");
			List<Integer> news=new ArrayList<Integer>();
			for (int i = 0; i < rArr.length; i++) {
				news.add(Integer.valueOf(rArr[i]));
			}
			//差集 = 需删除的
			olds.removeAll(news);
			if(olds.size()>0){
				CpAuthRightExample example=new CpAuthRightExample();
				example.createCriteria().andIdIn(olds);
				cpAuthRightMapper.deleteByExample(example);
			}
			//TODO
			//差集 = 需新增的
			news.removeAll(olds_);
			if(news.size()>0){
				authRoleRight(roleId, news,admin);
			}
		}else if (olds != null && olds.size() !=0){
			//只有 rightIds 为空，且olds 不为空时才进入;当 olds 为空是，会爆出sql异常
			CpAuthRightExample example=new CpAuthRightExample();
			example.createCriteria().andIdIn(olds);
			cpAuthRightMapper.deleteByExample(example);
		}
	}
	/**
	 * 修改角色，修改角色分配权限
	 * @param role
	 * @param rightIds
	 */
	public void updateRoleAndRight(CpRole newRole,CpRole rOld,String rightIds,CpUser admin)throws Exception{
		orderService.updateUnmOrderRole(newRole,rOld);//修改角色信息和排序号
		
		updateRoleRight(newRole.getId(), rightIds,admin);//修改角色权限
	}
	/**
	 * 删除角色，并删除角色权限
	 * @param admin 管理员
	 * @param roleIds 角色IDs
	 */
	public void delRoleAndRight(CpUser admin,String roleIds)throws Exception{
		Map<String,Object> param=new HashMap<String, Object>();
		String rids[]=roleIds.split(",");
		param.put("username", admin.getUserName());
		param.put("updatetime", new Date());
		param.put("ids", rids);
		cpBasicMapper.delRoleByIds(param);//删除角色
		
		List<Integer> vs=new ArrayList<Integer>();
		for (int i = 0; i < rids.length; i++) {
			vs.add(Integer.valueOf(rids[i]));
		}
		CpAuthRightExample example=new CpAuthRightExample();
		example.createCriteria().andTragetIdIn(vs).andTypeEqualTo(CommonConstant.BYTE0);
		cpAuthRightMapper.deleteByExample(example);//删除角色已有权限
	}
	
	/**
	 * @description 检验角色是否拥有用户，可以是角色列表，如果列表中有一个角色包含用户，就返回false，不予批量删除
	 * @param admin
	 * @param rids 
	 */
	public boolean checkRoleHasUser(String rids[]){
		List<Map<String, Object>> list = cpRoleMapper.checkDeleteRoles(rids);
		for (Map<String, Object> map : list) {
    		if ( (Long)map.get("users")>0) {
				return true;
    		}
		}
		return false;
	}
	
	/**
	 * 为用户分配角色
	 * @param userId
	 * @param roleIds
	 * @param admin 操作用户
	 * @throws Exception 
	 */
	public void authUserRole(int userId,String roleIds,CpUser admin) throws Exception{
		String roleArr[]=roleIds.split(",");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("roleIds", roleArr);
		param.put("userId",userId);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertUserRoleBatch(param);
	}
	/**
	 * 为用户分配角色
	 * @param userId
	 * @param roleIds
	 * @param admin 操作管理员
	 * @throws Exception 
	 */
	public void authUserRole(int userId,List<Integer> roleIds,CpUser admin) throws Exception{
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("roleIds", roleIds);
		param.put("userId",userId);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertUserRoleBatch(param);
	}
	
	/**
	 * 添加用户，并为其分配角色
	 * @param user 添加的用户信息
	 * @param roleIds 角色Ids
	 * @param admin 操作用户
	 * @throws Exception 
	 */
	public void addUserAndRole(CpUser user,String roleIds,CpUser admin) throws Exception{
		cpUserMapper.insertSelective(user);
		//为用户分配角色
		if(StringUtil.isNotBlank(roleIds)){
			authUserRole(user.getId(), roleIds, admin);
		}
	}
	
	/**
	 *修改用户分配角色
	 * @param user 添加的用户信息
	 * @param roleIds 角色Ids
	 * @param admin 操作管理员
	 * @throws Exception 
	 */
	public void updateUserRole(int userId,String roleIds,CpUser admin) throws Exception{
		//修改用户 角色
		if(StringUtil.isNotBlank(roleIds)){
			//删除用户已有角色
			CpUserRoleExample example=new CpUserRoleExample();
			example.createCriteria().andUserIdEqualTo(userId);
			cpUserRoleMapper.deleteByExample(example);
			//添加角色
			authUserRole(userId, roleIds, admin);
			/*//查询已分配权限
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("type", 1);
			param.put("tragetId", userId);
			List<Integer> olds=cpBasicMapper.selectAuthRightIds(param);
			List<Integer> olds_=new ArrayList<Integer>();
			olds_.addAll(olds);
			String rArr[]=roleIds.split(",");
			List<Integer> news=new ArrayList<Integer>();
			for (int i = 0; i < rArr.length; i++) {
				news.add(Integer.valueOf(rArr[i]));
			}
			//差集 = 需删除的
			olds.removeAll(news);			
			if(olds.size()>0){
				CpUserRoleExample example=new CpUserRoleExample();
				example.createCriteria().andIdIn(olds);
				cpUserRoleMapper.deleteByExample(example);
			}
			
			//差集 = 需新增的
			news.removeAll(olds_);
			if(news.size()>0){
				authUserRole(userId, news,admin);
			}*/
		}		
	}
	
	/**
	 * 修改用户或修改分配角色
	 * @param user 添加的用户信息
	 * @param roleIds 角色Ids
	 * @param admin 操作管理员
	 * @throws Exception 
	 */
	public int updateUserAndRole(CpUser user,String roleIds,CpUser admin) throws Exception{
		int a=cpUserMapper.updateByPrimaryKeySelective(user);
		updateUserRole(user.getId(), roleIds, admin);
		return a;
	}
		
	/**
	 * 为用户分配权限
	 * @param userId
	 * @param rightIds
	 * @throws Exception 
	 */
	public void authUserRight(int userId,String rightIds,CpUser admin) throws Exception{
		String rightArr[]=rightIds.split(",");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("rightIds", rightArr);
		param.put("userId", userId);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertUserRightBatch(param);
	}
	/**
	 * 为用户分配权限
	 * @param userId
	 * @param rightIds
	 * @throws Exception 
	 */
	public void authUserRight(int userId,List<Integer> rightIds,CpUser admin) throws Exception{
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("rightIds", rightIds);
		param.put("userId", userId);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertUserRightBatch(param);
	}
	/**
	 * 修改用户分配权限
	 * @param userId
	 * @param rightIds
	 * @throws Exception 
	 */
	public void updateUserRight(int userId,String rightIds,CpUser admin) throws Exception{
		if(StringUtil.isNotBlank(rightIds)){
			/*CpAuthRightExample example=new CpAuthRightExample();
			example.createCriteria().andTragetIdEqualTo(Integer.valueOf(userId)).andTypeEqualTo(CommonConstant.BYTE1);
			cpAuthRightMapper.deleteByExample(example);//删除用户已有权限
			authUserRight(userId, rightIds, admin);*/
			//查询已分配权限
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("type", 1);
			param.put("tragetId", userId);
			List<Integer> olds=cpBasicMapper.selectAuthRightIds(param);
			List<Integer> olds_=new ArrayList<Integer>();
			olds_.addAll(olds);
			String rArr[]=rightIds.split(",");
			List<Integer> news=new ArrayList<Integer>();
			for (int i = 0; i < rArr.length; i++) {
				news.add(Integer.valueOf(rArr[i]));
			}
			//差集 = 需删除的
			olds.removeAll(news);
			if(olds.size()>0){
				CpAuthRightExample example=new CpAuthRightExample();
				example.createCriteria().andIdIn(olds);
				cpAuthRightMapper.deleteByExample(example);
			}
			//差集 = 需新增的
			news.removeAll(olds_);
			if(news.size()>0){
				authUserRight(userId, news,admin);
			}
		}
	}
	
	/**
	 * 角色批量分配给用户
	 * @param roleIds
	 * @param userIds
	 */
	public void authRoleToUsers(String roleIds,String userIds,CpUser admin)throws Exception{
		String rids[]=roleIds.split(",");
		String uids[]=userIds.split(",");
		List<Integer> vs=new ArrayList<Integer>();
		for (int i = 0; i < rids.length; i++) {
			vs.add(Integer.valueOf(rids[i]));
		}
		//删除旧的角色用户关系
		CpUserRoleExample example=new CpUserRoleExample();
		example.createCriteria().andRoleIdIn(vs);
		cpUserRoleMapper.deleteByExample(example);
		//添加角色用户关系
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("roleIds", rids);
		param.put("userIds", uids);
		param.put("createUser", admin.getUserName());
		param.put("createTime", new Date());
		cpBasicMapper.insertUsersRolesBatch(param);
	}
	
	/**
	 * 校验用户是否有当前接口权限
	 * @param userId
	 * @param rightUri
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserRight(int userId,String rightUri) throws Exception{
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("rightUri", rightUri);
		Integer c=cpBasicMapper.checkUserRight(param);
		return c!=null&&c>0?true:false;
	}
	
	/**
	 * 校验用户是否登录
	 * @param userToken 用户令牌
	 * @return user
	 */
	public CpUser verify(String userToken){
		try {
			CpUser user=null;
			String userS=redisClientTemplate.get(userToken);
			if(userS!=null){
				user=(CpUser) GsonUtil.fromJson(redisClientTemplate.get(userToken), CpUser.class);
			}
			return user;
		} catch (Exception e) {
			return  null;
		}
		
	}

	//根据uid 获取 手机号，并过滤掉手机号为空或null的
	public List<String> findPhoneByUid(List<Integer> list2) {
		List<String> list = new ArrayList<String>();
		for (int i : list2) {
			String findPhoneByUid = cpUserMapper.findPhoneByUid(i);
			if (findPhoneByUid !=null && findPhoneByUid.length()>10) {
				list.add(findPhoneByUid);
			}
		}
		return list;
	}

	public List<String> findmailByUid(List<Integer> list2) {
		List<String> list = new ArrayList<String>();
		for (int i : list2) {
			 String findPhoneByUid = cpUserMapper.findmailByUid(i);
			 if (findPhoneByUid != null) {
				 list.add(findPhoneByUid);
			}
		}
		return list;
	}
	public String findNameByUid(int id) {
		 return cpUserMapper.findUserNameByUid(id);
	}

	public List<Integer> findTeamID(int i) {
		return cpUserRoleMapper.selectIdByTeamId(i);
	}
	public String findTeamName(int i) {
		return cpUserRoleMapper.findTeamName(i);
	}

	public List<Integer> getRoseId(Integer id) {
		return  cpUserRoleMapper.getRoseId(id);
	}

	public String getRoseName(int id) {
		return  cpUserRoleMapper.getRoseName(id);
	}

	public List<String> getRoseNameBuUid(Integer id) {
		return  cpUserRoleMapper.getRoseNameBuUid(id);
	}

	public CpUser findEmailByUserName(String userName) {
		return cpUserRoleMapper.findEmailByUserName(userName);
	}

	public List<CpUser> findUserByPhone(String phoneNum) {
		return cpUserRoleMapper.findUserByPhone(phoneNum);
	}

	public CpUser findEmailByUserEmail(String email) {
		return cpUserRoleMapper.findEmailByUserEmail(email);
	}
}
