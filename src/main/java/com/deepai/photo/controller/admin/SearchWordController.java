package com.deepai.photo.controller.admin;

import java.util.ArrayList;
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

import com.deepai.photo.bean.CpSearchWord;
import com.deepai.photo.bean.CpSearchWordExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpSearchWordExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpSearchWordMapper;
import com.deepai.photo.service.admin.UserRoleRightService;

/**
 * @author guoyanhong
 * 检索词词管理
 */
@Controller
@RequestMapping("/scWordCtro")
public class SearchWordController {
	private Logger log=Logger.getLogger(SearchWordController.class);
	
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private CpSearchWordMapper cpScWordMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	
	/**
	 * 查询检索词信息
	 * @param request
	 * @param scWord 检索词内容，查询条件，可以为空
	 * @param scWordId 检索词ID，查询条件，可以为空
	 * @param count 检索次数，查询条件，可以为空
	 * @param count 检索次数，查询条件，可以为空
	 * @param beginTime 最后检索时间开始时间，查询条件，可以为空
	 * @param endTime 最后检索时间结束时间，查询条件，可以为空
	 * @paramter 基本检索条件
	 * @langType 中英文标记
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getScWordByQuery")
	public Object getScWordByQuery(HttpServletRequest request,String scWord,String scWordId,
			String count,String beginTime,String endTime,String paramter,String langType){
		ResponseMessage result=new ResponseMessage();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("siteId", SessionUtils.getSiteId(request));
			if(StringUtil.isNotBlank(scWordId)){
				param.put("scWordId", scWordId);
			}
			if(StringUtil.isNotBlank(scWord)){
				param.put("scWord", scWord);
			}
			if(StringUtil.isNotBlank(langType)){
				param.put("langType",langType);
			}
			if(StringUtil.isNotBlank(count)){
				param.put("count", count);
			}
			if(StringUtil.isNotBlank(paramter)){
				param.put("paramter", paramter);
			}
			if(!StringUtil.isEmpty(beginTime)){
				param.put("beginTime", DateUtils.fromStringToDate("yyyy-MM-dd",beginTime));
			}
			if(!StringUtil.isEmpty(endTime)){
				param.put("endTime", DateUtils.fromStringToDate("yyyy-MM-dd",endTime));
			}	
			PageHelper.startPage(request);
			List<Map<String,Object>> scWordList=basicMapper.selectScWordByQuery(param);
			PageHelper.addPages(result, scWordList);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
			result.setData(scWordList);
			PageHelper.addPagesAndTotal(result, scWordList);
		} catch(Exception e1){
			e1.printStackTrace();
			log.error("查询检索词信息出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	//TODO(修改 、 批量停用检索词？？？？)
	
	/**
	 * 删除检索词信息
	 * @param scWordId 检索词ID，多个用英文逗号隔开
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delScWordByIds")
	@LogInfo(content="删除检索词",opeType=1,logTypeCode=CommonConstant.Other)
	public Object delScWordByIds(HttpServletRequest request,String scWordIds,String userToken){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(scWordIds, "检索词id");
			CpUser user=SessionUtils.getUser(request);
			CpUser cpUser = userRoleRightService.verify(userToken);
			if (cpUser ==null || (int)cpUser.getId() != (int)user.getId()){
				result.setCode(CommonConstant.FAILURECODE);
				result.setMsg("操作失败，请登录！");
				return result;
			}
			String ids[]=scWordIds.split(",");
			List<Integer> values=new ArrayList<Integer>();
			for (int i = 0; i < ids.length; i++) {
				values.add(Integer.valueOf(ids[i]));
			}
			CpSearchWordExample example=new CpSearchWordExample();
			example.createCriteria().andIdIn(values);
			cpScWordMapper.deleteByExample(example);
			result.setOther(String.format("scWordIds=%s",scWordIds));
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);			
		} catch (InvalidHttpArgumentException e) {
			e.printStackTrace();
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("删除检索词出错，"+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	public void saveWords(HttpServletRequest request,String strWhere,Integer langType) {
		CpSearchWordExample example=new CpSearchWordExample();
		Criteria criteria = example.createCriteria();
		criteria.andWordContentEqualTo(strWhere);
		criteria.andLangTypeEqualTo(langType);
		List<CpSearchWord> cpSearchWordsList = cpScWordMapper.selectByExample(example);
		if (cpSearchWordsList!=null&&cpSearchWordsList.size()>0) {
			cpSearchWordsList.get(0).setWordCount(cpSearchWordsList.get(0).getWordCount()+1);
			cpScWordMapper.updateByPrimaryKeySelective(cpSearchWordsList.get(0));
		}else{
		    CpUser cpUser = SessionUtils.getUser(request);
		    CpSearchWord cpSearchWord = new CpSearchWord();
		    if (cpUser!=null) {
		    	cpSearchWord.setCreateUser(cpUser.getUserName());
			}
		  cpSearchWord.setSiteId(SessionUtils.getSiteId(request));
		  cpSearchWord.setWordContent(strWhere);
		  cpSearchWord.setWordCount(1);
		  cpSearchWord.setLangType(langType);
		  cpSearchWord.setStatus(new Byte("0"));
		  cpSearchWord.setLastTime(new Date());
		  cpScWordMapper.insertSelective(cpSearchWord);}
	}
	
}
