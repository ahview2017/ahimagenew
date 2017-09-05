package com.deepai.photo.controller.enColumn;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpTopicExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.CpTopicExample.Criteria;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.lanmu.LanmuService;
import com.deepai.photo.service.topic.TopicService;

/**
 * 专题栏目
 * @author clong
 *
 */
@Controller
@RequestMapping("/enSpec")
public class EnSpecController {

	
	@Autowired
	private TopicService topicService;
	@Autowired
	private UserRoleRightService userRoleRightService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysConfigService configService; 
	@Autowired
	private LanmuService lanmuService;
	
	private Logger log = Logger.getLogger(EnSpecController.class);
	
	/**
	 * 获取专题栏目
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showTopic")
	public Object show(HttpServletRequest request, HttpServletResponse response,Integer langType) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			List<Integer> RoseId = userRoleRightService.getRoseId(user.getId());
			List<CpTopic> list = topicService.show(RoseId, langType);
			String CLIENTIP = null;
			List<String> clientList= configService.findEmail(SysConfigConstant.CLIENTIP,SysConfigConstant.CLIENTIP);
			if (clientList.size()>0) {
				CLIENTIP = clientList.get(0);
			}else{
				CLIENTIP = sysConfigService.getDbSysConfig(
						SysConfigConstant.CLIENTIP,
						user.getSiteId());
			}
			for (int i = 0; i < list.size(); i++) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime());
				list.get(i).setDatetime(datetime);
				list.get(i).setPid(0);
				if (StringUtils.isNotBlank(CLIENTIP)) {
					list.get(i).setClentIp(CLIENTIP);
				}
				List<CpLanmu> lanmuList = lanmuService.showLanMu(list.get(i).getId());
				list.get(i).setChildren(lanmuList);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("专题获取失败，" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}

		return result;
	}

}
