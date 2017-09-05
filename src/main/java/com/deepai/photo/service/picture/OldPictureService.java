package com.deepai.photo.service.picture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureExample;
import com.deepai.photo.bean.CpPictureExample.Criteria;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImageAnalyseUtil;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.CpPicAllpathMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.mapper.OtherMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.iptc.IptcDirectory;

/**
 * @author guoyanhong
 * 图片相关
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class OldPictureService {
	
	private Logger logger=Logger.getLogger(OldPictureService.class) ;
	
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CpPicAllpathMapper allpathMapper;
	@Autowired
	private CpPictureMapper cpPictureMapper;
	@Autowired
	private AboutPictureMapper aboutPictureMapper;
	@Autowired
	private CpPicGroupMapper cpPicGroupMapper;
	@Autowired
	private FlowService flowService;
	@Autowired
	private OtherMapper otherMapper;
	
	

	/**
	 * 上传单张图，保存图片原图和小图
	 * @param picFile
	 * @param oriPath
	 * @param filename
	 * @param siteId
	 * @throws Exception
	 */
	public CpPicture uploadOnePicT(MultipartFile picFile,String filename,int siteId,Map<String, String> map) throws Exception{
		//原图存放路径
		String oriPath= map.get("oriPath");
		oriPath=initFullPathNoFile(oriPath, filename);
		//上传原图并返回全途径
		String oriAllPath=FileUpload.fileUpName(picFile, oriPath, filename);
		int height = 0;
		int width = 0;
		String time=null;
		IMOperation op = new IMOperation();
		op.format("%w#%h#%[EXIF:DateTimeOriginal]");
		op.addImage(oriAllPath);
		IdentifyCmd identifyCmd = new IdentifyCmd(true);
		ArrayListOutputConsumer output = new ArrayListOutputConsumer();
		identifyCmd.setOutputConsumer(output);
		identifyCmd.run(op);
		ArrayList<String> out = output.getOutput();
		String wNh = null;
		if (out.size() > 0) {
			wNh = out.get(0);
			String arr[]=wNh.split("#");
			width = Integer.parseInt(arr[0]);
			height = Integer.parseInt(arr[1]);
			//上传图片宽高限制
			String minWidth= map.get("minWidth");
//					sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINWIDTH, siteId);//最小宽
			if(width<Integer.valueOf(minWidth)){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片宽不能小于【%s】像素", minWidth));
			}
			String minHeight= map.get("minHeight");
//					sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINHEIGHT, siteId);//最小高
			if(height<Integer.valueOf(minHeight)){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片高不能小于【%s】像素", minHeight));
			}
			if(arr.length>2){
				time = arr[2];
			}			
		}
		CpPicture pic = new CpPicture();
		int smallPicSize=Integer.parseInt( map.get("smallPicSize"));
//				sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_SIZE, siteId));//系统配置小图大小
		String smallPicPath=map.get("smallPicPath");
//				sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId);//系统配置小图路径
		
		//处理小图，返回小图原路径
		String smallAllPath=opeTmpSmallT(oriAllPath, width, height, false,smallPicSize,smallPicPath);
		if (width > height) {
			pic.setOrientation(CommonConstant.BYTE0);//横
		} else if (width == height) {
			pic.setOrientation(CommonConstant.BYTE2);//方
		} else {
			pic.setOrientation(CommonConstant.BYTE1);//竖
		}
		if (filename.toLowerCase().lastIndexOf("tif") >= filename.length() - 4) {
			filename = filename.substring(0, filename.length() - 3) + "jpg";
		}
		File saveFile = new File(oriAllPath);
		//上传图片大小限制
		String minM= map.get("minM");
//				sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINLENGTH, siteId);
		Integer minMI=Integer.valueOf(minM);//图片大小 最小限制 单位KB
		Long minL=minMI * 1024L;//单位字节
		if(saveFile.length()<minL){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片大小不能小于【%s】KB", minM));
		}
		String maxM=map.get("maxM");
//				sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMAXLENGTH, siteId);
		Integer maxMI=Integer.valueOf(maxM);//图片大小 最大限制 单位KB
		Long maxL=maxMI * 1024L;//单位字节
		if(saveFile.length()>maxL){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片大小不能超过【%s】KB", maxM));
		}
		
		pic.setPictureHeight(height);
		pic.setPictureWidth(width);
		pic.setFilename(filename);
		pic.setPictureLength(saveFile.length());
		pic.setSourcePictureName(picFile.getOriginalFilename());
		if(time!=null&&!time.equals("unknown")){
			time=time.replaceFirst(":", "-").replaceFirst(":", "-");
			pic.setFilmTime(DateUtils.sdfLongTimePlus.parse(time));
		}else{
			pic.setFilmTime(new Date());
		}
		pic.setCreateTime(new Date());
		pic.setSiteId(siteId);
		// 标识缩略图图片状态，组件上传成功后需改过来
		pic.setPictureState(-1);
		/*if(groupId!=null){
			pic.setGroupId(groupId);
		}*/
		pic.setDeleteFlag(CommonConstant.BYTE0);
		cpPictureMapper.insertSelective(pic);
		//记录图片全路径
		addPicAllPath(oriAllPath, 3, pic.getId());//原图
		addPicAllPath(smallAllPath, 2, pic.getId());//小图
		//生成中图
		int mediumSize=Integer.valueOf(map.get("mediumSize"));
//				Integer.valueOf(sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_SIZE, siteId));
		String mediumPath= map.get("mediumPath");
//				sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId);
		String mediumAllPath=initFullPathByOrder(mediumPath, filename);
		int picSize =width>height?width:height;
		if (mediumSize <= picSize) {
			ImageAnalyseUtil.gmAlterImg(mediumSize, oriAllPath, 
					initFullPathByOrder(mediumPath,filename), width,height,false);
		} else {
			ImgFileUtils.makeDirectory(mediumAllPath);
			ImgFileUtils.copyFile(oriAllPath, mediumAllPath);
		}
		addPicAllPath(mediumAllPath, 1, pic.getId());
		try{
			pic.setIsIptc(CommonConstant.BYTE1);
			pic.setCreateTime(new Date());
			File oriFile=new File(oriAllPath);
			pic=loadInfoByIPTC(pic, oriFile);
		}catch(Exception iptce){
			
		}
		return pic;
	}
	
	/**
	 * 处理小图，若图大则生成小图
	 * @param Path 原图地址
	 * @param width 原图宽
	 * @param height 原图高
	 * @param syn
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	private String opeTmpSmallT(String Path,int width,int height,boolean syn,int smallPicSize,String smallPicPath)throws Exception{
		int picSize =width>height?width:height;
		String fileName = ImgFileUtils.getFileName(Path);//文件名
		if (fileName.toLowerCase().indexOf("tif") >= fileName.length() - 4) {
			fileName = fileName.substring(0, fileName.length() - 3) + "jpg";
		}
		/*int smallPicSize=Integer.parseInt(sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_SIZE, siteId));//系统配置小图大小
		String smallPicPath=sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId);//系统配置小图路径
		*/
		String allPath=initFullPathByOrder(smallPicPath,fileName);
		if (smallPicSize<= picSize) {
			ImageAnalyseUtil.gmAlterImg(smallPicSize, Path, allPath, width,height,syn);
		} else {
			ImgFileUtils.makeDirectory(allPath);
			ImgFileUtils.copyFile(Path, allPath);
		}
		return allPath;
	}

	/**
	 * @param root 文件夹路径
	 * @param fileName 文件名
	 * @return 文件全路径
	 */
	public String initFullPathByOrder(String root, String fileName) {
		String tempp = root;
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			if (tempp.lastIndexOf(CommonConstant.oneSprit) != tempp.length() - 1) {
				tempp += CommonConstant.oneSprit;
			}
			if (tempp.lastIndexOf(CommonConstant.doubleSprit) == tempp.length() - 1) {
				tempp=tempp.substring(0, tempp.length()-1);
				tempp += CommonConstant.oneSprit;
			}
			tempp=tempp + fileName.substring(0, 4) + CommonConstant.oneSprit
					+ fileName.substring(0, 8) + CommonConstant.oneSprit + fileName;
		}else{
			if (tempp.lastIndexOf(CommonConstant.oneSprit) != tempp.length() - 1) {
				tempp += CommonConstant.oneSprit;
			}
			tempp=tempp + fileName.substring(0, 4) + CommonConstant.oneSprit
					+ fileName.substring(0, 8) + CommonConstant.oneSprit + fileName;
		}
		return tempp;
	}
	/**
	 * @param root 文件夹路径
	 * @return 文件路径
	 */
	public String initFullPathNoFile(String root,String fileName) {
		String tempp = root;
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			if (tempp.lastIndexOf(CommonConstant.doubleSprit) != tempp.length() - 1) {
				tempp += CommonConstant.doubleSprit;
			}
			tempp=tempp + fileName.substring(0, 4) + CommonConstant.doubleSprit
					+ fileName.substring(0, 8) + CommonConstant.doubleSprit ;
		}else{
			if (tempp.lastIndexOf(CommonConstant.oneSprit) != tempp.length() - 1) {
				tempp += CommonConstant.oneSprit;
			}
			tempp=tempp + fileName.substring(0, 4) + CommonConstant.oneSprit
					+ fileName.substring(0, 8) + CommonConstant.oneSprit;
		}
		return tempp;
	}
	
	/**
	 * 记录图片全路径
	 * @param allPath 图片全路径
	 * @param type 图片类型
	 * @param picId 图片记录id
	 */
	public void addPicAllPath(String allPath,int type,int picId){
		CpPicAllpath allp=new CpPicAllpath();
		allp.setAllPath(allPath);
		allp.setPicType(type);//图片类型
		allp.setTragetId(picId);
		allpathMapper.insertSelective(allp);
	}
	
	/**
	 * 处理单张图片信息，大、中、水印图
	 * @param cpPicture
	 * @param isIpTc
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public CpPicture loadSinglePicInfo(CpPicture cpPicture,boolean isIpTc,int siteId,Map<String, String> map) throws Exception{
		long s1=System.currentTimeMillis();
		try{			
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("picType", 3);
			param.put("tragetId", cpPicture.getId());
			//原图全路径
			String oriAllPath=aboutPictureMapper.selectPicPathByType(param);
			CpPicture oldPic=cpPictureMapper.selectByPrimaryKey(cpPicture.getId());
			boolean synFlag=false;//默认不是异步处理，否则前端会发生不返回数据的情况
			long st=System.currentTimeMillis();
			cpPicture.setIsSourcePicture(CommonConstant.BYTE1);
			//生成大、中、水印图
			opeMediumandSmall(oriAllPath, oldPic.getPictureWidth(),oldPic.getPictureHeight(),synFlag,siteId,cpPicture.getId(),map);
			long end=System.currentTimeMillis();
			logger.debug("生成大中图耗时："+(end-st));
			
			long startE=System.currentTimeMillis();
			File oriFile=new File(oriAllPath);
			cpPicture=loadInfoByEXIF(cpPicture, oriFile);
			long endE=System.currentTimeMillis();
			logger.debug("加载EXIF耗时："+(endE-startE));
			
		}catch(Exception e){
			logger.error("处理该上传图片信息异常:"+e.getMessage());
			throw e;
		}
		cpPicture.setUploadProgress(1);
		long s2=System.currentTimeMillis();
		logger.debug("load pic 耗时:"+(s2-s1));
		return cpPicture;
		
	}
	
	/**
	 * 加载EXIF信息
	 * 
	 * @param CpPicture
	 * @param File
	 * @throws Exception
	 */
	private CpPicture loadInfoByEXIF(CpPicture ap, File temp){
		try {
			Directory EXIFInfo = ImageAnalyseUtil.extratEXIFFromFile(temp
					.getAbsolutePath());
			// 型号
			ap.setExModel(EXIFInfo.getString(ExifDirectory.TAG_MODEL));
			//快门
			ap.setExExposureTime(EXIFInfo.getString(ExifDirectory.TAG_EXPOSURE_TIME));
			try {
				ap.setExDatetime(new SimpleDateFormat("yyyy:MM:dd HH:mm:ss",
						Locale.US).parse(EXIFInfo
						.getString(ExifDirectory.TAG_DATETIME_ORIGINAL)));
			} catch (Exception e) {
				logger.debug("加载EXIF拍摄时间出错,使用当前时间");
				ap.setExDatetime(new Date());
			}
			// 感光度
			ap.setExIso(EXIFInfo.getString(ExifDirectory.TAG_ISO_EQUIVALENT));
			// 光圈
			ap.setExFnumber(EXIFInfo.getString(ExifDirectory.TAG_FNUMBER));
			
		} catch (Exception e) {
			//TODO 如果没有exif信息，是否弹出提示？
		}
		return ap;
	}
	
	/**
	 * 加载图片IPTC信息
	 * 
	 * @param ap
	 * @param temp
	 * @throws Exception
	 */
	private CpPicture loadInfoByIPTC(CpPicture ap, File temp) throws Exception {
		try {
			Directory IPTCInfo = ImageAnalyseUtil.extratIPTCFromFile(temp
					.getAbsolutePath());
			Boolean isnull = true;
			System.out.println(IPTCInfo.getString(IptcDirectory.TAG_CAPTION));
			isnull = shiftCase(isnull, IPTCInfo
					.getString(IptcDirectory.TAG_CAPTION));
			isnull = shiftCase(isnull, IPTCInfo
					.getString(IptcDirectory.TAG_KEYWORDS));
			isnull = shiftCase(isnull, IPTCInfo
					.getString(IptcDirectory.TAG_CITY));
			isnull = shiftCase(isnull, IPTCInfo
					.getString(IptcDirectory.TAG_HEADLINE));
			isnull = shiftCase(isnull, IPTCInfo
					.getString(IptcDirectory.TAG_CATEGORY));
			isnull = shiftCase(isnull, IPTCInfo
					.getString(IptcDirectory.TAG_WRITER));
			if (isnull) {
				logger.info("图片没有可用的IPTC信息");
//				throw new Exception("图片没有可用的IPTC信息");
			}
			// 标题
			String strTitle=IPTCInfo.getString(IptcDirectory.TAG_HEADLINE);
			if(strTitle.length()>250){
				strTitle=strTitle.substring(1, 250);
			}
			ap.setTitle(strTitle);
			// 关键词
			ap.setKeywords(IPTCInfo.getString(IptcDirectory.TAG_KEYWORDS));
			// 拍摄时间
			try {
				ap.setFilmTime(new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US)
						.parse(IPTCInfo.getString(IptcDirectory.TAG_DATE_CREATED)));
			} catch (Exception e) {
				// 日期格式可能多种,暂时不抛异常
				logger.info("加载IPTC拍摄时间转换异常，使用当前时间");
				ap.setFilmTime(new Date());
			}
			// 地点
			ap.setPlace(IPTCInfo.getString(IptcDirectory.TAG_CITY));
			// 说明
			String strMemo=IPTCInfo.getString(IptcDirectory.TAG_CAPTION);
			if(strMemo.length()>1100){
				strMemo=strMemo.substring(0, 1100);
			}
			ap.setMemo(strMemo);
			// 文件名
			String fileName = temp.getName();

			if (fileName.toLowerCase().indexOf("tif") >= fileName.length() - 4) {
				fileName = fileName.substring(0, fileName.length() - 3) + "jpg";
			}
			ap.setFilename(fileName);
			// 图片体积
			ap.setPictureLength(temp.length());
			// 分类
			ap.setCategoryInfo(IPTCInfo.getString(IptcDirectory.TAG_CATEGORY));
			// 这里还要注入当前编辑信息
			// 摄影师
			ap.setAuthorName(IPTCInfo.getString(IptcDirectory.TAG_WRITER));
		} catch (Exception e) {
			logger.info("上传图片包含非IPTC图片");
//			throw new Exception("上传图片包含非IPTC图片，请重新选择图片上传");
		}
		return ap;
	}
	private Boolean shiftCase(Boolean isnull, String string) {
		if (isnull && string != null) {
			return false;
		}
		return isnull;
	}
	/**
	 * 生成大、中、水印图
	 * @param Path 原图全地址
	 * @param width
	 * @param height
	 * @param synFlag
	 * @param siteId
	 * @throws Exception
	 */
	private void opeMediumandSmall(String Path,int width,int height,Boolean synFlag,int siteId,int picId,Map<String, String> map) throws Exception {
		logger.debug("开始处理图片");
		int picSize =width>height?width:height;
		String fileName = ImgFileUtils.getFileName(Path);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("tragetId", picId);
		//中图
		param.put("picType", 1);
		//查询中图是否已存在
		String medium=aboutPictureMapper.selectPicPathByType(param);
//		String mediumPath=sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId);
		String mediumPath= map.get("mediumPath");
		String mediumAllPath=initFullPathByOrder(mediumPath, fileName);
		if(medium==null){
//			int mediumSize=Integer.valueOf(sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_SIZE, siteId));
			int mediumSize =Integer.valueOf( map.get("mediumSize"));
			if (mediumSize <= picSize) {
				ImageAnalyseUtil.gmAlterImg(mediumSize, Path, 
						initFullPathByOrder(mediumPath,fileName), width,height,synFlag);
			} else {
				ImgFileUtils.makeDirectory(mediumAllPath);
				ImgFileUtils.copyFile(Path, mediumAllPath);
			}
			addPicAllPath(mediumAllPath, 1, picId);
		}
		
		//大图
		param.put("picType", 0);
		//查询大图是否已存在
		String big=aboutPictureMapper.selectPicPathByType(param);
		if(big==null){
//			int bigSize=(sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_SIZE, siteId));
			int bigSize= Integer.valueOf(map.get("bigSize"));
			String bigPath= map.get("bigPath");
//			String bigPath=sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_PATH, siteId);
			String bigAllPath=initFullPathByOrder(bigPath, fileName);
			if (bigSize<= picSize) {			
				ImageAnalyseUtil.gmAlterImg(bigSize, Path,  initFullPathByOrder(bigPath,fileName), width,height,synFlag);
			} else {
				ImgFileUtils.makeDirectory(bigAllPath);
				ImgFileUtils.copyFile(Path, bigAllPath);
			}
			addPicAllPath(bigAllPath, 0, picId);
		}
		
		//水印中图
//		Thread.sleep(2000);
		param.put("picType", 4);
		//查询水印中图是否已存在
		String wmM=aboutPictureMapper.selectPicPathByType(param);
//		logger.info("wmM>>>>>>>>>>>>>>>>>"+wmM+"<---->"+picId);
//		logger.info("mediumAllPath>>>>>>>>>>>>>>>>>"+mediumAllPath+"<---->"+picId);
		if(wmM==null){
//			String waterPic=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);
			String waterPic= map.get("waterPic");
//			logger.info("默认水印图为："+waterPic);
//			String position=sysConfigService.getDbSysConfig(SysConfigConstant.UPLOAD_WATER_POSITION, siteId);
//			String watermarkedmedium=sysConfigService.getDbSysConfig(SysConfigConstant.WATERMARKEDMEDIUM_PIC_PATH, siteId);
			String watermarkedmedium= map.get("watermarkedmedium");
			String position= map.get("position");
			String wmAllPath=initFullPathByOrder(watermarkedmedium,fileName);
			ImageAnalyseUtil.waterMarkPic(wmAllPath, mediumAllPath, waterPic, position,true);
//			logger.info("wmAllPath："+wmAllPath);
			addPicAllPath(wmAllPath, 4, picId);
		}
	}
	public CpPicture findById(Integer pid) {
		CpPictureExample example = new  CpPictureExample();
		Criteria criteria = example.createCriteria();
		Criteria andIdEqualTo = criteria.andIdEqualTo(pid);
		return cpPictureMapper.selectByExample(example).get(0);
	}
	public List<String> findPicPathByGourpId(int id) {
		return cpPicGroupMapper.findPicPathByGourpId(id);
	}
	public List<CpPicture> showPicByGroupId(int groupId) {
		return cpPicGroupMapper.showPicByGroupId(groupId);
	}
}
