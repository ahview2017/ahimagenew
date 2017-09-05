package com.deepai.photo.controller.favoriteFolder;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpFavoriteFolder;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.MyStringUtil;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.service.favoriteFolder.FavoriteFolderService;

/**
 * * @author huqiankai: 
 * * 
 */
@Controller
@RequestMapping("favorite")
public class FavoriteFolderController {
	private Logger log = Logger.getLogger(FavoriteFolderController.class);
	@Resource
	private FavoriteFolderService favoriteFolderService ;
	/**
	 * 收藏夹添加
	 * @param request
	 * @param cpFavoriteFolder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addFavoriteFolder")
	@SkipAuthCheck
	@LogInfo(content = "收藏夹添加", opeType = 0, logTypeCode = CommonConstant.Favorite)
	public Object addFavoriteFolder(HttpServletRequest request,CpFavoriteFolder cpFavoriteFolder) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			cpFavoriteFolder.setCreateId(user.getId());
			favoriteFolderService.addFavoriteFolder(cpFavoriteFolder);
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
	 * 展示所有收藏夹名
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("showAllFavoriteFolder")
	@SkipAuthCheck
	public Object showAllFavoriteFolder(HttpServletRequest request) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			PageHelper.startPage(request);
			List<CpFavoriteFolder> list=favoriteFolderService.showAllFavoriteFolder(user.getId());
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			result.setOther(list.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("展示所有收藏夹失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 删除收藏夹
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delAllFavoriteFolder")
	@SkipAuthCheck
	@LogInfo(content = "删除收藏夹", opeType = 1, logTypeCode = CommonConstant.Favorite)
	public Object delAllFavoriteFolder(HttpServletRequest request,String id) {
		ResponseMessage result=new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				favoriteFolderService.delAllFavoriteFolder(i);
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除收藏夹失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 更改收藏夹
	 * @param request
	 * @param cpFavoriteFolder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("upFavoriteFolder")
	@SkipAuthCheck
	@LogInfo(content = "更改收藏夹", opeType = 2, logTypeCode = CommonConstant.Favorite)
	public Object upFavoriteFolder(HttpServletRequest request,CpFavoriteFolder cpFavoriteFolder) {
		ResponseMessage result=new ResponseMessage();
		try {
			favoriteFolderService.upFavoriteFolder(cpFavoriteFolder);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("更改收藏夹失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 展示所有收藏夹名字
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("showAllFavoriteFolderName")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showAllFavoriteFolderName(HttpServletRequest request) {
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			if (user==null) {
				result.setMsg(CommonConstant.NOTLOGINMSG);
				return result;
			}
			PageHelper.startPage(request);
			List<CpFavoriteFolder> list=favoriteFolderService.showAllFavoriteFolderName(user.getId());
			String rows = request.getParameter("rows");
			if (MyStringUtil.isEmpty(rows) || !MyStringUtil.isNumeric(rows)){
				rows ="10";
			}
			int page=list.size()%Integer.parseInt(rows)==0 ? 
			list.size()/Integer.parseInt(rows):list.size()/Integer.parseInt(rows)+1;
			result.setPage(page);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("展示所有收藏夹名字失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
}
