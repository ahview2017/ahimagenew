package com.deepai.photo.controller.favoriteFolder;

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

import com.deepai.photo.bean.CpFavoritePicGroups;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpFavoritePicGroupsMapper;

/**
 * * @author huqiankai: 
 * * 
 */
@Controller
@RequestMapping("favoriteGroupPics")
public class FavoriteGroupPicsController {
	private Logger log = Logger.getLogger(FavoriteGroupPicsController.class);
	@Autowired
	private CpFavoritePicGroupsMapper cpFavoritePicGroupsMapper;
	/**
	 * 添加收藏图片
	 * @param request
	 * @param cpFavoriteFolder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addFavoriteGroupPics")
	@SkipAuthCheck
	public Object addFavoriteFolder(HttpServletRequest request,Integer groupId) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CpUser user = SessionUtils.getUser(request);

			CpFavoritePicGroups cpFavoritePicGroups = new CpFavoritePicGroups();
			cpFavoritePicGroups.setGroupId(groupId);
			cpFavoritePicGroups.setUserId(user.getId());
			
			log.info("<<<groupId:"+groupId);
			log.info("<<<userId:"+user.getId());
			CpFavoritePicGroups qCpFavoritePicGroups = cpFavoritePicGroupsMapper.getFavoriteCountByUserIdAndGroupId(cpFavoritePicGroups);
			Map<Object,Object> resultMap = new HashMap<Object,Object>();
			if(qCpFavoritePicGroups!=null){//如果已经有收藏记录，则更新收藏状态
				int status = qCpFavoritePicGroups.getStatus();
				if(status==0){
					qCpFavoritePicGroups.setStatus(1);
					cpFavoritePicGroupsMapper.updateFavoritePicGroupsStatus(qCpFavoritePicGroups);
					result.setCode(528);
					result.setMsg("取消收藏成功");
					result.setData(resultMap);
					resultMap.put("flag", 0);//1表示当前稿件已经收藏过，0表示未收藏
				}else{
					qCpFavoritePicGroups.setStatus(0);
					cpFavoritePicGroupsMapper.updateFavoritePicGroupsStatus(qCpFavoritePicGroups);
					result.setCode(529);
					result.setMsg("收藏成功");
					result.setData(resultMap);
					resultMap.put("flag", 1);//1表示当前稿件已经收藏过，0表示未收藏
				}
				return result;
			}
			CpFavoritePicGroups iFavoritePicGroups = new CpFavoritePicGroups();
			iFavoritePicGroups.setUserId(user.getId());
			iFavoritePicGroups.setGroupId(groupId);
			iFavoritePicGroups.setCrtime(new Date());
			iFavoritePicGroups.setStatus(0);//todo 0表示收藏,1表示取消收藏
			cpFavoritePicGroupsMapper.insertSelective(iFavoritePicGroups);
			resultMap.put("flag", 0);//1表示当前稿件已经收藏过，0表示未收藏
			result.setCode(529);  
			result.setMsg("收藏成功");
			result.setData(resultMap);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("收藏稿件失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	/**
	 * 查询稿件收藏状态
	 * @param request
	 * @param cpFavoriteFolder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectFavoriteCollectStatus")
	@SkipLoginCheck
	public Object selectFavoriteCollectStatus(HttpServletRequest request,Integer groupId) {
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CpUser user = SessionUtils.getUser(request);
			CpFavoritePicGroups cpFavoritePicGroups = new CpFavoritePicGroups();
			cpFavoritePicGroups.setGroupId(groupId);
			cpFavoritePicGroups.setUserId(user.getId());
			CpFavoritePicGroups qCpFavoritePicGroups = cpFavoritePicGroupsMapper.getFavoriteCountByUserIdAndGroupId(cpFavoritePicGroups);
			Map<Object,Object> resultMap = new HashMap<Object,Object>();
			if(qCpFavoritePicGroups!=null){//如果已经有收藏记录，则更新收藏状态
				int status = qCpFavoritePicGroups.getStatus();
				if(status==0){
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
					resultMap.put("flag", 0);
				}else{
					result.setCode(CommonConstant.SUCCESSCODE);
					result.setMsg(CommonConstant.SUCCESSSTRING);
					resultMap.put("flag", 1);
				}
				result.setData(resultMap);
				return result;
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			resultMap.put("flag", 1);//1表示当前稿件已经收藏过，0表示未收藏
			result.setData(resultMap);	
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查询收藏稿件失败， "+e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 更新稿件点赞状态
	 * @author xiayunan
	 * @date 2017-10-26
	 * @param request
	 * @param groupId 稿件id
	 * @param ip 点赞者ip
	 * @return
	 */
	//todo
	@ResponseBody
	@RequestMapping("/updateFavoritePicGroupsStatus")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object updateFavoritePicGroupsStatus(HttpServletRequest request,Integer groupId,Integer status){
		ResponseMessage result=new ResponseMessage();
		try {
			CommonValidation.checkParamBlank(groupId+"", "稿件id");
			CpUser user = SessionUtils.getUser(request);
			if(user==null){
				result.setCode(CommonConstant.NOTLOGINCODE);
				result.setMsg(CommonConstant.NOTLOGINMSG);
				return result;
			}
			CpFavoritePicGroups cpFavoritePicGroups = new CpFavoritePicGroups();
			cpFavoritePicGroups.setGroupId(groupId);
			cpFavoritePicGroups.setUserId(user.getId());
			cpFavoritePicGroups.setStatus(status);
			cpFavoritePicGroupsMapper.updateFavoritePicGroupsStatus(cpFavoritePicGroups);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("更新稿件点赞记录失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	/**
	 * 获取我的收藏稿件列表
	 * @author xiayunan
	 * @date 2017-10-26
	 * @param request
	 * @param groupId 稿件id
	 * @param ip 点赞者ip
	 * @return
	 */
	//todo
	@ResponseBody
	@RequestMapping("/getFavoritePicGroups")
	@SkipAuthCheck
	@SkipLoginCheck
	public Object getFavoritePicGroups(HttpServletRequest request){
		ResponseMessage result=new ResponseMessage();
		try {
			CpUser user = SessionUtils.getUser(request);
			if(user==null){
				result.setCode(CommonConstant.NOTLOGINCODE);
				result.setMsg(CommonConstant.NOTLOGINMSG);
				return result;
			}
			CpFavoritePicGroups cpFavoritePicGroups = new CpFavoritePicGroups();
			cpFavoritePicGroups.setUserId(user.getId());
			
			PageHelper.startPage(request);
			List<Map<String,Object>> list = cpFavoritePicGroupsMapper.getFavoriteCountByUserId(cpFavoritePicGroups);
			for (Map<String,Object> map:list) {
				if(map.containsKey("FILENAME")){
                    map.put("filePath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getPathByNameAndSize(map.get("FILENAME").toString(),request,2));
				}
			}
			PageHelper.addPagesAndTotal(result, list);
			result.setData(list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (InvalidHttpArgumentException e) {
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}catch(Exception e1){
			e1.printStackTrace();
			log.error("获取用户稿件收藏列表，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
}
