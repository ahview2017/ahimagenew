package com.deepai.photo.controller.favoriteFolder;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpFavoriteFolderPic;
import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.favoriteFolder.FavoriteFolderPicService;

/**
 * * @author huqiankai:
 */
@Controller
@RequestMapping("favoriteFolderPic")
public class FavoriteFolderPicController {
	private Logger log = Logger.getLogger(FavoriteFolderPicController.class);
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private FavoriteFolderPicService favoriteFolderPicService;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;

	/**
	 * 添加图片到收藏夹
	 * @param request
	 * @param picid
	 * @param folderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addFavoriteFolderPic")
	@SkipAuthCheck
	@LogInfo(content = "添加图片到收藏夹", opeType = 0, logTypeCode = CommonConstant.Favorite)
	public Object addFavoriteFolderPic(HttpServletRequest request,int picid,int folderId) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			CpFavoriteFolderPic cpFavoriteFolderPic=cpPicGroupMapper.findGroupPicByPicId(picid);
			if (cpFavoriteFolderPic!=null) {
				if (StringUtils.isNotBlank(cpFavoriteFolderPic.getAllPath())) {
					String path=cpFavoriteFolderPic.getAllPath().replace(sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)),
							ipAdd);
					cpFavoriteFolderPic.setAllPath(path);
				}
			}
			cpFavoriteFolderPic.setUserId(user.getId());
			cpFavoriteFolderPic.setFolderId(folderId);
			favoriteFolderPicService.addFavoriteFolderPic(cpFavoriteFolderPic);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("收藏夹添加失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除收藏夹内图片
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delFavoriteFolderPic")
	@SkipAuthCheck
	@LogInfo(content = "删除收藏夹内图片", opeType = 1, logTypeCode = CommonConstant.Favorite)
	public Object delFavoriteFolderPic(HttpServletRequest request,String id) {
		ResponseMessage result=new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				favoriteFolderPicService.delFavoriteFolderPic(i);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除收藏夹内图片， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 打开收藏夹所有图片
	 * @param request
	 * @param response
	 * @param folderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("showFavoriteFolderPic")
	@SkipAuthCheck
	public Object showFavoriteFolderPic(HttpServletRequest request, HttpServletResponse response, int folderId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			PageHelper.startPage(request);
			List<CpFavoriteFolderPic>list=favoriteFolderPicService.showFavoriteFolderPic(user.getId(),folderId);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			result.setOther(list.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("打开收藏夹所有图片， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
} 
