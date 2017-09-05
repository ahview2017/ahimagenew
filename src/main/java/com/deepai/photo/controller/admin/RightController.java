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

import com.deepai.photo.bean.CpRight;
import com.deepai.photo.bean.CpSite;
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
import com.deepai.photo.mapper.CpRightMapper;

/**
 * @author guoyanhong
 * 权限管理相关
 */
@Controller
@RequestMapping("/rightCtro")
public class RightController {
	private Logger log=Logger.getLogger(RightController.class);
	
	@Autowired
	private CpRightMapper cpRightMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	
	
	/**
	 * 查询权限信息
	 * @param request
	 * @param rightName 权限名称，查询条件，可以为空
	 * @param rightId 权限ID，查询条件，可以为空
	 * @param ifPage 是否分页 0不分页，1分页，为空默认不分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRightByQuery")
	public Object getRightByQuery(HttpServletRequest request,String rightName,String rightId,String ifPage){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			if(StringUtil.isNotBlank(rightId)){
				param.put("rightId", rightId);
			}
			if(StringUtil.isNotBlank(rightName)){
				param.put("rightName", rightName);
			}
			param.put("type", 0);//TODO 目前只查出菜单，若以后要按照功能授权，再放开
			List<Map<String,Object>> rightList=null;
			if(StringUtil.isNotBlank(ifPage)&&ifPage.equals("1")){
				PageHelper.startPage(request);
				rightList=basicMapper.selectRightByQuery(param);
				PageHelper.addPagesAndTotal(result, rightList);
				PageHelper.addPages(result, rightList);
				/*List<Map<String, Object>> selectRightByQuery = basicMapper.selectRightByQuery(param);
				result.setOther(selectRightByQuery.size());*/
			}else{
				rightList=basicMapper.selectRightByQuery(param);
				result.setOther(rightList.size());
			}			
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(rightList);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询权限信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 更新权限信息 只能修改权限名称和备注
	 * @param request
	 * @param rightName 权限名称
	 * @param memo 备注
	 * @param rightId 权限ID 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRight")
	@LogInfo(content="更新权限",opeType=2,logTypeCode=CommonConstant.Right)
	public Object updateRight(HttpServletRequest request,String rightName,String memo,String rightId){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(rightId, "权限ID");
			String[] param=new String[2];
			param[0]=rightName;
			param[1]=memo;
			CommonValidation.checkParamAllBlank(param, "权限名称和备注");
			CpUser user=SessionUtils.getUser(request);
			
			CpRight right=new CpRight();
			right.setRightName(rightName);
			right.setMemo(memo);		
			CpRight s=cpRightMapper.selectByPrimaryKey(Integer.valueOf(rightId));
			if(s!=null&&s.getDeleteFlag()==CommonConstant.BYTE0){
				right.setId(Integer.valueOf(rightId));
				right.setUpdateUser(user.getUserName());
				right.setUpdateTime(new Date());
				cpRightMapper.updateByPrimaryKeySelective(right);
				result.setOther(String.format("rightId=%s",right.getId()));
			}else{
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("不存在ID为【%s】的权限！",rightId));
				return result;
			}				
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("更新权限出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
