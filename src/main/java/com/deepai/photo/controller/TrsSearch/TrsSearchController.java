package com.deepai.photo.controller.TrsSearch;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.pojo.TRSResult;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.TrsSearch.TrsSearchConf;
import com.deepai.photo.controller.admin.SearchWordController;

@Controller
@RequestMapping("/trsSearch")
public class TrsSearchController {
	private Logger log = Logger.getLogger(TrsSearchController.class);
	@Autowired
	private  SearchWordController searchWordController;
	
//	private static Integer langType = 0;
	/**
	 * @param request
	 * @param response
	 * @return list 全文检索
	 */
	@ResponseBody
	@RequestMapping("/trsSearch")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object trsSearch(HttpServletRequest request, HttpServletResponse response,TRSResult trs) {
		ResponseMessage result = new ResponseMessage();
		try {
			String strWhere =request.getParameter("strWhere");
			String page =request.getParameter("page");
			String rows =request.getParameter("rows");
			int langType = SessionUtils.geLangType(request);
			if (StringUtil.isNotBlank(strWhere)) {
				searchWordController.saveWords(request,strWhere,langType);
			}
			Map<String, Object> result_ = TrsSearchConf.TrsSearch(strWhere, page, rows,trs,request);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(result_.get("rows"));
			result.setPage(result_.get("page"));
			result.setOther(result_.get("allCount"));
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("搜索失败==================>",e1);
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
