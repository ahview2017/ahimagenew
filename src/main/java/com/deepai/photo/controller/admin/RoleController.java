package com.deepai.photo.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpRole;
import com.deepai.photo.bean.CpRoleExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpRoleMapper;
import com.deepai.photo.service.admin.NumOrderService;
import com.deepai.photo.service.admin.UserRoleRightService;

/**
 * @author guoyanhong
 * 角色管理
 */
@Controller
@RequestMapping("/roleCtro")
public class RoleController {
	private Logger log=Logger.getLogger(RoleController.class);
	
	@Autowired
	private CpRoleMapper cpRoleMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private NumOrderService numOrderService;
	
	/**
	 * 查询角色信息
	 * @param request
	 * @param roleName 角色名称，查询条件，可以为空
	 * @param roleId 角色ID，查询条件，可以为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleByQuery")
	@SkipAuthCheck
	public Object getRoleByQuery(HttpServletRequest request,String roleName,String roleId){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			if(StringUtil.isNotBlank(roleId)){
				param.put("roleId", roleId);
			}
			if(StringUtil.isNotBlank(roleName)){
				param.put("roleName", roleName);
			}
			PageHelper.startPage(request);
			List<Map<String,Object>> roleList=basicMapper.selectRoleByQuery(param);
			for (int i = 0; i < roleList.size(); i++) {
				Map<String,Object> role=roleList.get(i);
				role.put("rights",basicMapper.selectRightByRoleId(Integer.valueOf(role.get("ID").toString())));
			}
			PageHelper.addPages(result, roleList);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(roleList);
			PageHelper.addPagesAndTotal(result, roleList);
		} catch(Exception e1){
			e1.printStackTrace();    
			log.error("查询角色信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 新建角色信息,并分配权限
	 * @param request
	 * @param roleName 角色名称
	 * @param memo 备注
	 * @param rightIds 角色分配权限ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRole")
	@LogInfo(content="新建角色",opeType=0,logTypeCode=CommonConstant.Role)
	public Object addRole(HttpServletRequest request,String roleName,String memo,String rightIds,Integer langType,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(roleName, "角色名称");
			CommonValidation.checkParamBlank(memo, "备注");
			CpUser admin=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			CpRole role=new CpRole();
			role.setRoleName(roleName);
			role.setMemo(memo);	
			role.setLangType(langType);
			CpRoleExample ex=new CpRoleExample();
			ex.createCriteria().andRoleNameEqualTo(roleName).andDeleteFlagEqualTo(CommonConstant.BYTE0);
			List<CpRole> list=cpRoleMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("角色名称为【%s】已存在！",roleName));	
				return result;
			}	
			role.setSiteId(SessionUtils.getSiteId(request));
			role.setDeleteFlag(CommonConstant.BYTE0);
			role.setCreateUser(admin.getUserName());
			role.setCreateTime(new Date());
			userRoleRightService.addRoleAndRight(role, rightIds,admin);
			result.setOther(String.format("roleId=%s;rightIds=%s",role.getId(),rightIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("新建角色出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 更新角色信息(角色名称，备注，权限)
	 * @param request
	 * @param roleName 角色名称
	 * @param memo 备注
	 * @param roleId 角色ID 
	 * @param numOrder 排序号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRole")
	@LogInfo(content="更新角色",opeType=2,logTypeCode=CommonConstant.Role)
	public Object updateRole(HttpServletRequest request,String roleName,String memo,String roleId,String rightIds,Integer numOrder,Integer langType,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(roleId, "角色ID");
			/*String[] param=new String[2];
			param[0]=roleName;
			param[1]=memo;
			CommonValidation.checkParamAllBlank(param, "角色名称和备注");*/
			CpUser admin=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			
			CpRole role=new CpRole();
			if(StringUtil.isNotBlank(roleName)){
				role.setRoleName(roleName);
			}
			if(StringUtil.isNotBlank(memo)){
				role.setMemo(memo);			
			}
			if(numOrder!=null){
				//校验配序号大小
				numOrderService.checkRoleOrderNum(numOrder);
				role.setNumOrder(numOrder);
			}
			CpRoleExample ex=new CpRoleExample();
			ex.createCriteria().andRoleNameEqualTo(roleName).andDeleteFlagEqualTo(CommonConstant.BYTE0).andIdNotEqualTo(Integer.valueOf(roleId));
			List<CpRole> list=cpRoleMapper.selectByExample(ex);
			if(list!=null&&list.size()>0){
				result.setCode(CommonConstant.REPEATCODE);
				result.setMsg(String.format("角色名称为【%s】已存在！",roleName));	
				return result;
			}	
			CpRole rOld=cpRoleMapper.selectByPrimaryKey(Integer.valueOf(roleId));
			if(rOld!=null&&rOld.getDeleteFlag()==CommonConstant.BYTE0){
				role.setId(Integer.valueOf(roleId));
				role.setUpdateUser(admin.getUserName());
				role.setLangType(langType);
				role.setUpdateTime(new Date());
				userRoleRightService.updateRoleAndRight(role,rOld, rightIds,admin);
				result.setOther(String.format("roleId=%s;rightIds=%s",role.getId(),rightIds));
			}else{
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的角色！",roleId));
				return result;
			}				
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			log.error(e);
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("更新角色信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 删除角色信息
	 * @param roleId 角色ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delRoleByIds")
	@LogInfo(content="删除角色",opeType=1,logTypeCode=CommonConstant.Role)
	public Object delRoleByIds(HttpServletRequest request,String roleIds,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(roleIds, "角色id");
			CpUser admin=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			//DOTO
			String rids[]=roleIds.split(",");
			for (int i = 0; i < rids.length; i++) {
				if (rids[i].equals("1")||rids[i].equals("2")||rids[i].equals("3")||rids[i].equals("4")||rids[i].equals("5")) {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("包含不可删除角色，角色删除失败");	
					return result;
				}
			}
			
			boolean bool = userRoleRightService.checkRoleHasUser(rids);
			if (bool) {
				if (rids.length >1) {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("有关联用户，不可批量删除");	
					return result;
				} else {
					result.setCode(CommonConstant.FAILURECODE);
					result.setMsg("该角色下有关联用户，不可删除该角色");	
					return result;
				}
			}
			CpUser user=SessionUtils.getUser(request);
			userRoleRightService.delRoleAndRight(user, roleIds);
			result.setOther(String.format("roleIds=%s",roleIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除角色信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 角色批量分配给用户
	 * @param request
	 * @param roleIds 角色ids
	 * @param userIds 用户ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/authRoleToUsers")
	@LogInfo(content="角色批量分配给用户",opeType=0,logTypeCode=CommonConstant.Role)
	public Object authRoleToUsers(HttpServletRequest request,String roleIds,String userIds,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(roleIds, "角色ID");
			CommonValidation.checkParamBlank(userIds, "用户ID");
			CpUser admin=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)admin.getId()) {
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			userRoleRightService.authRoleToUsers(roleIds, userIds, admin);
			result.setOther(String.format("roleIds=%s;userIds=%s",roleIds,userIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("角色批量分配给用户出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
