package com.deepai.photo.controller.topic;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpLanmu;
import com.deepai.photo.bean.CpLanmuPicture;
import com.deepai.photo.bean.CpLanmulist;
import com.deepai.photo.bean.CpNotice;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupExample;
import com.deepai.photo.bean.CpPicGroupExample.Criteria;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPicturePrice;
import com.deepai.photo.bean.CpTopic;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.json.JSONUtils;
import com.deepai.photo.controller.admin.RightController;
import com.deepai.photo.mapper.CpLanmuMapper;
import com.deepai.photo.mapper.CpLanmuPictureMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.service.admin.WaterMarkService;
import com.deepai.photo.service.lanmu.LanmuPicService;
import com.deepai.photo.service.lanmu.LanmuService;
import com.deepai.photo.service.notice.NoticeService;
import com.deepai.photo.service.picture.PictureService;
import com.deepai.photo.service.topic.TopicService;
import com.sun.org.apache.bcel.internal.generic.LCMP;

/**
 * * @author huqiankai: 
 */
@Controller
@RequestMapping("/lanmu")
public class LanmuController {
	private Logger log = Logger.getLogger(LanmuController.class);
	@Autowired
	private LanmuService lanmuService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private LanmuPicService lanmuPicService;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired 
	private TopicService topicService;
	@Autowired
	private WaterMarkService waterMarkService;
	@Autowired
	private CpLanmuMapper cpLanmuMapper;
	@Autowired
	private NoticeService noticeService;
	/**
	 * 栏目添加
	 * 如果有是子栏目则集合第一个是大栏目 下面1.2是小的子栏目
	 * @param request
	 * @param response
	 * @param cpLanmu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addLanmu")
	@LogInfo(content = "栏目添加", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object addLanmu(HttpServletRequest request, HttpServletResponse response, CpLanmulist cpLanmulist) {
		ResponseMessage result = new ResponseMessage();
		try {
			lanmuService.add(cpLanmulist);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("栏目添加失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 栏目显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showLanMu")
	public Object show(HttpServletRequest request, HttpServletResponse response, int topicId) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpLanmu> list = lanmuService.showLanMu(topicId);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("栏目展示失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 删除栏目
	 * 
	 * @param request
	 * @param response
	 * @param cpLanmu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteLanmu")
	@LogInfo(content = "删除栏目", opeType = 1, logTypeCode = CommonConstant.Other)
	public Object deleteLanmu(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String string : split) {
				lanmuService.delete(Integer.parseInt(string));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除栏目失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 保存编辑的栏目
	 * @param request
	 * @param response
	 * @param cpLanmulist
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editLanmu")
	@LogInfo(content = "编辑栏目", opeType = 2, logTypeCode = CommonConstant.Other)
	public Object editLanmu(HttpServletRequest request, HttpServletResponse response, CpLanmulist cpLanmulist) {
		ResponseMessage result = new ResponseMessage();
		try {
			
			lanmuService.editLanmu(cpLanmulist);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("保存编辑的栏目失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**
	 * 按栏目名字查找栏目
	 * @param request
	 * @param response
	 * @param name
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/serachLanmu")
	public Object serachLanmu(HttpServletRequest request, HttpServletResponse response, String name ,int topicId) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpLanmu> list = lanmuService.serachLanmu(name,topicId);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("栏目搜索失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**根据栏目中图片的栏目ID删除
	 * @param request
	 * @param response
	 * @param lanmuPicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deletePIc")
	@LogInfo(content = "删除栏目内的稿件图", opeType = 1, logTypeCode = CommonConstant.Other)
	public Object getPic(HttpServletRequest request, HttpServletResponse response, String id) {
		ResponseMessage result = new ResponseMessage();
		try {
			String[] split = id.split(",");
			for (String i : split) {
				lanmuPicService.delete(Integer.parseInt(i));
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("删除展示图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**查看栏目内在专题页展示的
	 * @param request
	 * @param response
	 * @param lanmuid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lanMuPicDetail")
	public Object lanMuPicDetail(HttpServletRequest request, HttpServletResponse response, int lanmuid) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpLanmu cpLanmu = lanmuService.lanMuDetail(lanmuid);
			String topicName = lanmuService.findTopicNameByTopicId(cpLanmu.getTopicId());
			cpLanmu.setTopicName(topicName);
			CpLanmu cpLanmu1 = lanmuPicService.lanMuPicDetail(cpLanmu,request);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpLanmu1);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看栏目内展示的图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**查看更多图片
	 * @param request
	 * @param response
	 * @param lanmuid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lanMuPicMoreDetail")
	public Object lanMuPicMoreDetail(HttpServletRequest request, HttpServletResponse response, int lanmuid) {
		ResponseMessage result = new ResponseMessage();
		try {
			//TODO
			PageHelper.startPage(request);
			List<CpLanmuPicture> list = lanmuPicService.lanMuPicMoreDetail(lanmuid,request);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看更多图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**查看更多图片
	 * @param request
	 * @param response
	 * @param lanmuid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lanMuPicMoreDetailToClient")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object lanMuPicMoreDetailToClient(HttpServletRequest request, HttpServletResponse response, int lanmuid) {
		ResponseMessage result = new ResponseMessage();
		try {
			//TODO
			PageHelper.startPage(request);
			List<CpLanmuPicture> list = lanmuPicService.lanMuPicMoreDetail(lanmuid,request);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
			PageHelper.addPagesAndTotal(result, list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看更多图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/AllLanmuPic")
	public Object AllLanmuPic(HttpServletRequest request, HttpServletResponse response, int lanmuid) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpLanmuPicture> list = lanmuPicService.AllLanmuPic(lanmuid,request);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看更多图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}

	/**单张添加图片 需要上传图片位置
	 * @param request
	 * @param response
	 * @param cpLanmuPicture
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addLanmuPic")
	public Object addLanmuPic(HttpServletRequest request, HttpServletResponse response, CpLanmuPicture cpLanmuPicture) {
		ResponseMessage result = new ResponseMessage();
		try {
			//TODO
			lanmuPicService.add(cpLanmuPicture);
			Map<String, Integer> somaBack= lanmuPicService.findTopicName(cpLanmuPicture.getLanmuId());
			List<Integer> lanmuids  =lanmuService.findLanmu(cpLanmuPicture.getLanmuId());
			String  lanmuid="lanmuId";
			int a =0;
			if (lanmuids.size()==1) {
				somaBack.put(lanmuid, lanmuids.get(0));
			}
			if (lanmuids.size()>1) {
				for (Integer integer : lanmuids) {
					somaBack.put(lanmuid, integer);
					a++;
					lanmuid=lanmuid+a+"";
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(somaBack);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("单张添加图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
//子栏目保存修改~~~~~~~~~~~~~~~~~~~~~~~~~！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	@ResponseBody                                 
	@RequestMapping("/saveLanmuWithPic")  
	public Object saveLanmuWithPic(HttpServletRequest request, HttpServletResponse response,CpLanmulist cpLanmulist, @RequestParam(required = false, value = "wmFile")MultipartFile  wmFile) {
		ResponseMessage result = new ResponseMessage();
		try {
			if (wmFile!=null) {
				String pic_path = waterMarkService.insertOnePicture(wmFile,request);
				cpLanmulist.getCpLanmu().get(0).setSubAdds(pic_path);
			}
			lanmuService.editLanmu(cpLanmulist);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("单张添加图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/** 添加更多稿件照片
	 * @param request
	 * @param response
	 * @param cpLanmuPicture
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addMoreLanmuPic")
	@LogInfo(content = "添加栏目内的稿件图", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object addMoreLanmuPic(HttpServletRequest request, HttpServletResponse response,
			String lanmuId,String id ,String groupId) {
		ResponseMessage result = new ResponseMessage();
		try {
			lanmuPicService.addMoreLanmuPic(lanmuId,id,groupId);
			Map<String, Integer> somaBack= lanmuPicService.findTopicName(Integer.parseInt(lanmuId));
			List<Integer> lanmuids  =lanmuService.findLanmu(Integer.parseInt(lanmuId));
			String  lanmuid="lanmuId";
			int a =0;
			if (lanmuids.size()==1) {
				somaBack.put(lanmuid, lanmuids.get(0));
			}
			if (lanmuids.size()>1) {
				for (Integer integer : lanmuids) {
					somaBack.put(lanmuid, integer);
					a++;
					lanmuid=lanmuid+a+"";
				}
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(somaBack);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("添加更多稿件照片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	/**
	 * 保存带有富文本的轮播图
	 * @param request
	 * @param response
	 * @param cpLanmu
	 * @param cpLanmulist
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveLanmuWithAdv")
	@LogInfo(content = "添加栏目内的公告", opeType = 0, logTypeCode = CommonConstant.Other)
	public Object addLanmuPicWithAdv(HttpServletRequest request, HttpServletResponse response,
			 CpLanmu cpLanmu,CpLanmulist cpLanmulist) {
		ResponseMessage result = new ResponseMessage();
		try {
			lanmuPicService.addLanmuPicWithAdv(cpLanmu,cpLanmulist);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("保存栏目失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 展示栏目+栏目公告
	 * @param request
	 * @param response
	 * @param lanmuid
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showLanmuWithAdv")
	public Object showLanmuWithAdv(HttpServletRequest request, HttpServletResponse response, int lanmuid ,Integer topicId) {
		ResponseMessage result = new ResponseMessage();
		try {
			//TODO
			CpLanmu cpLanmu = lanmuService.lanMuDetail(lanmuid);
			String topicName = lanmuService.findTopicNameByTopicId(cpLanmu.getId());
			cpLanmu.setTopicName(topicName);
			cpLanmu = lanmuPicService.showLanmuWithAdv(cpLanmu,topicId,request);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpLanmu);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看更多图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 栏目内更多栏目公告
	 * @param request
	 * @param response
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showMoreLanmuWithAdv")
	public Object showMoreLanmuWithAdv(HttpServletRequest request, HttpServletResponse response,Integer topicId) {
		ResponseMessage result = new ResponseMessage();
		try {
			//TODO
			PageHelper.startPage(request);
			List<CpNotice>list= lanmuPicService.showMoreLanmuWithAdv(topicId);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看更多图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 展示专题内容
	 * 0位置为banna图位置
	 * @param request
	 * @param response
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showPicAndLanmuByTopicId")
	@SkipLoginCheck
	@SkipAuthCheck
	public Object showPicAndLanmuByTopicId(HttpServletRequest request, HttpServletResponse response, int topicId) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpTopic findTopicTopicId = topicService.findTopicTopicId(topicId);
			List<CpLanmu> cpLanmuList = lanmuService.showLanMu(topicId);
			List<CpLanmu> list = lanmuPicService.findAllPicWithLanmuId(cpLanmuList,request);
			List<CpNotice> cpnoticeList = noticeService.findNoticeByTopicId(topicId);
			for (CpLanmu cpLanmu : list) {
				if (cpLanmu.getTypeId()==2) {
					cpLanmu.setCpNoticesList(cpnoticeList);
				}
			}
			  String lanmu = JSONUtils.beanListToJson(list);
			  Map<String, String>map = new HashMap<>();
			  map.put("lanmu", lanmu);
			  if (findTopicTopicId.getEmage()!=null) {
				  map.put("banna", findTopicTopicId.getEmage());
				  map.put("desc", findTopicTopicId.getRemarks());
			}
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(map);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("展示专题内容失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	/**
	 * 根据稿件id展示稿件所有图片
	 * @param request
	 * @param response
	 * @param groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showPicByGroupId")
	public Object showPicByGroupId(HttpServletRequest request, HttpServletResponse response, int groupId) {
		ResponseMessage result = new ResponseMessage();
		try {
			PageHelper.startPage(request);
			List<CpPicture> list = pictureService.showPicByGroupId(groupId);
			PageHelper.addPages(result, list);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(list);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("展示稿件所有图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	/**
	 * 栏目预览
	 * @param request
	 * @param response
	 * @param lanmuid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lanMuPicPreview")
	public Object lanMuPicPreview(HttpServletRequest request, HttpServletResponse response, int lanmuid) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpLanmu cpLanmu = lanmuService.lanMuDetail(lanmuid);
			CpLanmu cpLanmu1 = lanmuPicService.lanMuPicPreview(cpLanmu,request);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpLanmu1);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看栏目内展示的图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/lanMuPicWithNoticePreview")
	public Object lanMuPicWithNoticePreview(HttpServletRequest request, HttpServletResponse response, int lanmuid) {
		ResponseMessage result = new ResponseMessage();
		try {
			CpLanmu cpLanmu = lanmuService.lanMuDetail(lanmuid);
			CpLanmu cpLanmu1 = lanmuPicService.lanMuPicWithNoticePreview(cpLanmu,request);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(cpLanmu1);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("查看栏目内展示的图片失败， " + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
	}
	
	
	
	
}

