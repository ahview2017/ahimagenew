package com.deepai.photo.service.admin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicAllpathExample;
import com.deepai.photo.bean.CpSystemConfig;
import com.deepai.photo.bean.CpWaterMarkPicture;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpPicAllpathMapper;
import com.deepai.photo.mapper.CpSystemConfigMapper;
import com.deepai.photo.mapper.CpWaterMarkPictureMapper;


/**
 * @author guoyanhong
 * 水印
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class WaterMarkService {
	private Logger log=Logger.getLogger(WaterMarkService.class);
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CpWaterMarkPictureMapper waterMarkMapper;
	@Autowired
	private CpSystemConfigMapper systemCofigMapper;
	@Autowired
	private CpSystemConfigMapper cpSystemConfigMapper;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private CpPicAllpathMapper allpathMapper;
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;
	
	/**
	 * 新增水印图片
	 * @param wmPic
	 * @param wmFile
	 * @return
	 * @throws Exception
	 */
	public void insertWaterMark(CpWaterMarkPicture wmPic,MultipartFile  wmFile) throws Exception{
		String fileName=getFileName();
		
		//上传
		String allPath=FileUpload.fileUpName(wmFile, sysConfigService.getWaterMarkPicturePathOnServer(fileName, wmPic.getSiteId()), fileName);
		File saveFile = new File(allPath);
		Long minL=20 * 1024L;//单位字节
		if(saveFile.length()>minL){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片大小不能超过【%s】KB", 20));
		}
		if(wmPic.getIsDefaultWmpic()==1){
			//修改其他水印为非默认
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("old", 1);
			map.put("new", 0);
			aboutPictureMapper.updateIsDefault(map);
			//同步到redis
			sysConfigService.setToRedis(SysConfigConstant.DEFAULT_WATERMARK_PIC, wmPic.getSiteId(), allPath);
		}
		wmPic.setFilename(fileName);
		
		waterMarkMapper.insertSelective(wmPic);
		
		//存储文件全路径
		CpPicAllpath allp=new CpPicAllpath();
		allp.setAllPath(allPath);
		allp.setPicType(5);//水印图片
		allp.setTragetId(wmPic.getId());
		allpathMapper.insertSelective(allp);
		//排序和默认为ID
		CpWaterMarkPicture newWm=new CpWaterMarkPicture();
		newWm.setId(wmPic.getId());
		newWm.setSortId(wmPic.getId());
		waterMarkMapper.updateByPrimaryKeySelective(newWm);
	}
	
	/**
	 * 修改水印图片
	 * @param wmPic
	 * @param wmFile
	 * @return
	 * @throws Exception
	 */
	public void updateWaterMark(CpWaterMarkPicture wmPic,MultipartFile  wmFile,int siteId) throws Exception{
		if(wmPic.getIsDefaultWmpic()!=null&&wmPic.getIsDefaultWmpic()==1){
			//修改其他水印为非默认
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("old", 1);
			map.put("new", 0);
			aboutPictureMapper.updateIsDefault(map);
			CpPicAllpathExample e=new CpPicAllpathExample();
			e.createCriteria().andPicTypeEqualTo(5).andTragetIdEqualTo(wmPic.getId());
			List<CpPicAllpath> all=allpathMapper.selectByExample(e);
			if(all!=null&&all.size()>0){
				String allPath=all.get(0).getAllPath();
				log.info("allPath>>>>>>>>>>"+allPath);
				//同步到redis
				sysConfigService.setToRedis(SysConfigConstant.DEFAULT_WATERMARK_PIC,siteId, allPath);
				//修改系统默认水印图路径 2017-08-27 Add by xiayunan
				CpSystemConfig cpSystemConfig= cpSystemConfigMapper.selectByPrimaryKey(2);
				cpSystemConfig.setConfigValue(allPath);
				systemCofigMapper.updateByPrimaryKey(cpSystemConfig);
			}
		}
		if(wmFile==null||wmFile.isEmpty()){//不修改水印图片
			waterMarkMapper.updateByPrimaryKeySelective(wmPic);
		}else{//修改图片
			//上传
			String fileName=getFileName();
			String allPath=FileUpload.fileUpName(wmFile, sysConfigService.getWaterMarkPicturePathOnServer(fileName, siteId), fileName);
			wmPic.setFilename(fileName);
			if(wmPic.getIsDefaultWmpic()!=null&&wmPic.getIsDefaultWmpic()==1){
				//同步到redis
				sysConfigService.setToRedis(SysConfigConstant.DEFAULT_WATERMARK_PIC,siteId, allPath);
				//修改系统默认水印图路径 2017-08-27 Add by xiayunan
				CpSystemConfig cpSystemConfig= cpSystemConfigMapper.selectByPrimaryKey(2);
				cpSystemConfig.setConfigValue(allPath);
				systemCofigMapper.updateByPrimaryKey(cpSystemConfig);
				
			}
			waterMarkMapper.updateByPrimaryKeySelective(wmPic);
			
			//修改存储文件全路径
			CpPicAllpathExample e=new CpPicAllpathExample();
			e.createCriteria().andPicTypeEqualTo(5).andTragetIdEqualTo(wmPic.getId());
			CpPicAllpath allp=new CpPicAllpath();
			allp.setAllPath(allPath);
			allpathMapper.updateByExampleSelective(allp, e);
		}
	}
	               
	public String insertOnePicture(MultipartFile  wmFile,HttpServletRequest request) throws Exception{
		// 上传
		String fileName=getFileName();
//		List<String> list = sysConfigService.findEmail(SysConfigConstant.DEFAULT_CLASSIFICATION_PATH,"");
		String filePath = FileUpload.fileUpName(wmFile, sysConfigService
				.getDbSysConfig(
						SysConfigConstant.DEFAULT_ADVERTISEMENT_PATH,
						SessionUtils.getSiteId(request)),
				 fileName);
		String allPath = ImgFileUtils.getReplacePathByName(filePath,sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_ADVERTISEMENT_PATH,SessionUtils.getSiteId(request))
				, ipAdd, "/advertisement/", request);
		//存储文件全路径
		return allPath;
	}
	
	
	/**
	 * 生成水印图片名
	 * @return 
	 */
	private String getFileName(){
		StringBuffer sb=new StringBuffer();
		sb.append(DateUtils.getFormattedTime(DateUtils.sdfLongTime)).append("_");
		sb.append(System.currentTimeMillis());
		sb.append("_p.png");
		return sb.toString();
		
	}
}
