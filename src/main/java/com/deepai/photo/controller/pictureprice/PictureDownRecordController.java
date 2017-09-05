package com.deepai.photo.controller.pictureprice;

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

import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateTimeUtil;
import com.deepai.photo.mapper.CpPictureDownloadrecordMapper;

/**
 * @author zhangShuo:
 * 
 */
@Controller
@RequestMapping("/pictureDownRecord")
public class PictureDownRecordController {
	private Logger log = Logger.getLogger(PictureDownRecordController.class);

	@Autowired
	private CpPictureDownloadrecordMapper pictureDownloadrecordMapper;

	/**
	 * 查询下载图片列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selPictureDowns")
	@SkipAuthCheck
	public Object selPictureDowns(HttpServletRequest request, String uploader,
			String fileName, String title, String starttime, String endtime,Integer flag,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser cpUser = SessionUtils.getUser(request);
			int siteId = SessionUtils.getSiteId(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("userId", cpUser.getId());
			map.put("langType", langType);
			if (flag!=null) {
				map.put("flag", flag);
				map.put("userName", cpUser.getUserName());
			}
			if (StringUtil.isNotEmpty(title)) {
				map.put("title", title);
			}
			if (StringUtil.isNotEmpty(fileName)) {
				map.put("fileName", fileName);
			}
			if (StringUtil.isNotEmpty(uploader)) {
				map.put("uploader", uploader);
			}
			if (StringUtil.isNotEmpty(starttime)) {
				Date createStartTimeTemp = DateTimeUtil.convertAsDateString(starttime);
				map.put("starttime", createStartTimeTemp);
			}
			if (StringUtil.isNotEmpty(endtime)) {
				Date createEndTimeTemp = DateTimeUtil.convertAsDateString(endtime);
				map.put("endtime", createEndTimeTemp);
			}
			PageHelper.startPage(request);
			List<Map<String, Object>> maps = pictureDownloadrecordMapper
					.getDownloadPictures(map);
			PageHelper.addPagesAndTotal(result, maps);
			if (maps.isEmpty()) {
				result.setCode(CommonConstant.NULLCODE);
				result.setMsg(String.format("【%s】用户,未下载过图片 ！",
						cpUser.getUserName()));
			} else {
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setData(maps);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询用户基本信息出错，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
