package com.deepai.photo.common.validation;

import org.springframework.cache.annotation.CachePut;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;


/**
 * @author gyh
 * 常用校验
 */
public class CommonValidation {
	
	private final static String NOTNULL="不可为空！";
	private final static String NOTALLNULL="不可同时为空！";
	/**
	 * 校验不可为空参数是否为空
	 * @param param 参数
	 * @param errorMessage 错误提示
	 */
	public static void checkParamBlank(String param, String errorMessage) {
		if (StringUtil.isBlank(param)||"null".equals(param)) {
			throw new InvalidHttpArgumentException(CommonConstant.PARAMERROR,errorMessage+NOTNULL);
		}
	}
	
	/**
	 * 校验参数不可同时都为空
	 * @param param 参数
	 * @param errorMessage 错误提示
	 */
	public static void checkParamAllBlank(String[] params, String errorMessage) {
		boolean flag=true;
		for (int i = 0; i < params.length; i++) {
			if(StringUtil.isNotBlank(params[i])){
				flag=false;
			}
		}
		if (flag) {
			throw new InvalidHttpArgumentException(CommonConstant.PARAMERROR,errorMessage+NOTALLNULL);
		}
	}
	/**
	 * 校验稿件是否正在编辑
	 * @param oldGroup 稿件状态
	 * @param groupId 稿件id
	 */
	public static void checkGroupBeginEdit(CpPicGroup oldGroup,int groupId) {
		if(oldGroup==null){
			throw new InvalidHttpArgumentException(CommonConstant.NULLCODE,String.format("不存在id为%s的稿件",groupId));
		}
		if(oldGroup.getIsLocked()!=null&&oldGroup.getIsLocked()==1){
			throw new InvalidHttpArgumentException(CommonConstant.ISEDITCODE,String.format(CommonConstant.ISEDITMSG, oldGroup.getLockerName()));
		}
	}
	
	/**
	 * 校验稿件是否被锁定
	 * @param oldGroup 稿件状态
	 * @param groupId 稿件id
	 * @param user 操作人
	 */
	public static void checkGroupSaveEdit(CpPicGroup oldGroup,int groupId,CpUser user) {
		if(oldGroup==null){
			throw new InvalidHttpArgumentException(CommonConstant.NULLCODE,String.format("不存在id为%s的稿件",groupId));
		}
		if(oldGroup.getIsLocked()!=null&&oldGroup.getIsLocked()==0){
			throw new InvalidHttpArgumentException(CommonConstant.UNISEDITCODE,String.format(CommonConstant.UNISEDITMSG, oldGroup.getUnlockName()));
		}
		if(oldGroup.getIsLocked()!=null&&oldGroup.getIsLocked()==1&&!oldGroup.getLockerName().equals(user.getUserName())){
			throw new InvalidHttpArgumentException(CommonConstant.ISEDITCODE,String.format(CommonConstant.ISEDITMSG, oldGroup.getLockerName()));
		}
	}

}
