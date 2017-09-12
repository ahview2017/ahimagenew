package com.deepai.photo.service.picture;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpPicAllpath;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureExample;
import com.deepai.photo.bean.CpPictureExample.Criteria;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImageAnalyseUtil;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.common.validation.CommonValidation;
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
public class PictureService {
	
	private Logger logger=Logger.getLogger(PictureService.class) ;
	
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
	@Autowired
	private HttpServletRequest request;
	

	/**
	 * 上传图片，保存原图和小图
	 * @param picFiles
	 * @param siteId
	 * @throws Exception
	 */
	public List<CpPicture> uploadMorePic(MultipartFile[] picFiles,int siteId) throws Exception{
		List<CpPicture> res=new ArrayList<CpPicture>();
//		List<Map<String,Object>> res=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < picFiles.length; i++) {
			String name = picFiles[i].getOriginalFilename();
			if ((name == null) || (name.trim().equals(""))) {
				continue;
			}
			String filename=null;
			if (name.toLowerCase().lastIndexOf("jpg") >= name.length() - 4) {
				filename = getFileName("jpg");
			}  else if (name.toLowerCase().lastIndexOf("tif") >= name.length() - 4) {
				filename = getFileName("tif");
			}else if (name.toLowerCase().lastIndexOf("png") >= name.length() - 4) {
				filename = getFileName("png");
			}else{
				filename = getFileName("jpg");
			}
			CpPicture pic=uploadOnePic(picFiles[i], filename, siteId);
			pic.setSmallPath(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(filename,request));
			/*Map<String,Object> map=new HashMap<String, Object>();
			map.put("picId",pic.getId());
			map.put("fileTime",pic.getFilmTime());
			map.put("samllPath", CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(filename));
			map.put("height",pic.getPictureHeight());
			map.put("width",pic.getPictureWidth());
			map.put("filename",pic.getFilename());
			map.put("length",pic.getPictureLength());*/
			res.add(pic);
		}
		return res;
	}
	/**
	 * 得到随机的文件名
	 * @param oriName 原始名称
	 * @return
	 * @throws Exception
	 */
	public String getPicFileName(String oriName) throws Exception{
		//图片名称
		String filename = null;
		if (oriName.toLowerCase().lastIndexOf("jpg") >= oriName.length() - 4) {
			filename = getFileName("jpg");
		}  else if (oriName.toLowerCase().lastIndexOf("tif") >= oriName.length() - 4) {
			filename = getFileName("tif");
		}else if (oriName.toLowerCase().lastIndexOf("png") >= oriName.length() - 4) {
			filename = getFileName("png");
		}else{
			filename = getFileName("jpg");
		}
		return filename;
	}
	
	/**
	 * 图片推送
	 * @param cpPicAllpath 路径表信息
	 * @param siteId 站点id
	 * @return
	 * @throws Exception
	 */
	public String uploadPushPic(String fileName, CpPicAllpath cpPicAllpath,Integer siteId) throws Exception{
		//英文版路径
		SessionUtils.setLangType(request, 1);
		
		String oriPath = null;
		
		if(cpPicAllpath.getAllPath().contains("classification")){
			oriPath = sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_CLASSIFICATION_PATH, siteId);
		} else if(cpPicAllpath.getAllPath().contains("watermarkedmedium")){
			oriPath = sysConfigService.getDbSysConfig(SysConfigConstant.WATERMARKEDMEDIUM_PIC_PATH, siteId);
		} else if(cpPicAllpath.getAllPath().contains("big")){
			oriPath = sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_PATH, siteId);
		} else if(cpPicAllpath.getAllPath().contains("medium")){
			oriPath = sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId);
		} else if(cpPicAllpath.getAllPath().contains("small")){
			oriPath = sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId);
		}
		
		FileInputStream fis=new FileInputStream(cpPicAllpath.getAllPath());

		oriPath=initFullPathNoFile(oriPath, fileName);
		
		String name = FileUpload.fileUpNameByInputStream(fis, oriPath, fileName);
		
		return name;
	}
	/**
	 * 上传单张图，保存图片原图和小图
	 * @param picFile
	 * @param oriPath
	 * @param filename
	 * @param siteId
	 * @throws Exception
	 */
	public CpPicture uploadOnePic(MultipartFile picFile,String filename,int siteId) throws Exception{
		//原图存放路径
		String oriPath=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_CLASSIFICATION_PATH, siteId);
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

		// add by xia.yunan@20170906
		identifyCmd.setSearchPath(sysConfigService.getDbSysConfig(
                SysConfigConstant.LOCAL_GM_PATH, 1));
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
			String minWidth=sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINWIDTH, siteId);//最小宽
			if(width<Integer.valueOf(minWidth)){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片宽不能小于【%s】像素", minWidth));
			}
			String minHeight=sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINHEIGHT, siteId);//最小高
			if(height<Integer.valueOf(minHeight)){
				throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片高不能小于【%s】像素", minHeight));
			}
			if(arr.length>2){
				time = arr[2];
			}			
		}
		CpPicture pic = new CpPicture();
		//处理小图，返回小图原路径
		String smallAllPath=opeTmpSmall(oriAllPath, width, height, false,siteId);
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
		String minM=sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINLENGTH, siteId);
		Integer minMI=Integer.valueOf(minM);//图片大小 最小限制 单位KB
		Long minL=minMI * 1024L;//单位字节
		if(saveFile.length()<minL){
			throw new InvalidHttpArgumentException(CommonConstant.FAILURECODE, String.format("图片大小不能小于【%s】KB", minM));
		}
		String maxM=sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMAXLENGTH, siteId);
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
		int mediumSize=Integer.valueOf(sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_SIZE, siteId));
		String mediumPath=sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId);
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
	private String opeTmpSmall(String Path,int width,int height,boolean syn,int siteId)throws Exception{
		int picSize =width>height?width:height;
		String fileName = ImgFileUtils.getFileName(Path);//文件名
		if (fileName.toLowerCase().indexOf("tif") >= fileName.length() - 4) {
			fileName = fileName.substring(0, fileName.length() - 3) + "jpg";
		}
		int smallPicSize=Integer.parseInt(sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_SIZE, siteId));//系统配置小图大小
		String smallPicPath=sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId);//系统配置小图路径
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
	 * 获取文件名
	 */
	private String getFileName(String type) throws Exception {
		String stp = ".jpg";
		if (type.equals("jpg")) {
			stp = ".jpg";
		} else if (type.equals("tif")) {
			stp = ".tif";
		} else if (type.equals("png")) {
			stp = ".png";
		}
		Integer picNum=otherMapper.selectOrderNum("seq_article");
		String picNum_= String.format("%s%06d", DateUtils.getNowShortDate(),picNum);
		StringBuffer sb=new StringBuffer();
//		sb.append(DateUtils.getFormattedTime(DateUtils.sdfShort)).append("_");
		sb.append(picNum_).append("a");
		sb.append(stp);
		return sb.toString();
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
	public CpPicture loadSinglePicInfo(CpPicture cpPicture,boolean isIpTc,int siteId) throws Exception{
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
			opeMediumandSmall(oriAllPath, oldPic.getPictureWidth(),oldPic.getPictureHeight(),synFlag,siteId,cpPicture.getId());
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
	private void opeMediumandSmall(String Path,int width,int height,Boolean synFlag,int siteId,int picId) throws Exception {
		logger.debug("开始处理图片");
		int picSize =width>height?width:height;
		String fileName = ImgFileUtils.getFileName(Path);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("tragetId", picId);
		//中图
		param.put("picType", 1);
		//查询中图是否已存在
		String medium=aboutPictureMapper.selectPicPathByType(param);
		String mediumPath=sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId);
		String mediumAllPath=initFullPathByOrder(mediumPath, fileName);
		if(medium==null){
			int mediumSize=Integer.valueOf(sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_SIZE, siteId));
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
			int bigSize=Integer.valueOf(sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_SIZE, siteId));
			String bigPath=sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_PATH, siteId);
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
		
		//add by xiayunan 2017-09-11
		String waterPicPostion=sysConfigService.getDbSysConfig(SysConfigConstant.WATERMAKER_POSITION, siteId);
		String transparency=sysConfigService.getDbSysConfig(SysConfigConstant.WATERMAKER_TRANSPARENCY, siteId);
		
		if(wmM==null){
			String waterPic=sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);
//			logger.info("默认水印图为："+waterPic);
			String position=sysConfigService.getDbSysConfig(SysConfigConstant.UPLOAD_WATER_POSITION, siteId);
			String watermarkedmedium=sysConfigService.getDbSysConfig(SysConfigConstant.WATERMARKEDMEDIUM_PIC_PATH, siteId);
			String wmAllPath=initFullPathByOrder(watermarkedmedium,fileName);
			
			//add by xiayunan 20170911
			if(StringUtil.notBlank(position)&&!"Custom".equals(position)){
				ImageAnalyseUtil.waterMarkPic(wmAllPath, mediumAllPath, waterPic, position,true);
			}else{
				ImageAnalyseUtil.SpePositionWaterMarkPic(wmAllPath, mediumAllPath, waterPic, waterPicPostion, Integer.valueOf(transparency), true);
			}
			
//			logger.info("wmAllPath："+wmAllPath);
			addPicAllPath(wmAllPath, 4, picId);
		}
		
		// ch add by liu.jinfeng@2017年9月5日 下午9:05:35
        // 中图 1200
        param.put("picType", 8);
        // 查询中图是否已存在
        String medium1200 = aboutPictureMapper.selectPicPathByType(param);
        String mediumPath1200 = sysConfigService
                .getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH1200, siteId);
        String mediumAllPath1200 = initFullPathByOrder(mediumPath1200, fileName);
//        logger.info ("mediumAllPath1200>>>>"+mediumAllPath1200);
        if (medium1200 == null) {
            int mediumSize1200 = Integer
                    .valueOf(sysConfigService.getDbSysConfig(
                            SysConfigConstant.MEDIUM_PIC_SIZE1200, siteId));
            if (mediumSize1200 <= picSize) {
                ImageAnalyseUtil.gmAlterImg(mediumSize1200, Path,
                        initFullPathByOrder(mediumPath1200, fileName), width,
                        height, synFlag);
            } else {
                ImgFileUtils.makeDirectory(mediumAllPath1200);
                ImgFileUtils.copyFile(Path, mediumAllPath1200);
            }
            addPicAllPath(mediumAllPath1200, 8, picId);
        }
        
        // 水印中图 1200
        // Thread.sleep(2000);
        param.put("picType", 9);
        // 查询水印中图是否已存在
        String wmM1200 = aboutPictureMapper.selectPicPathByType(param);
//        logger.info ( "wmM1200>>>>"+wmM1200);
        if (wmM1200 == null) {
            String waterPic = sysConfigService.getDbSysConfig(
                    SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId);
            // logger.info ("默认水印图为："+waterPic);
            String position = sysConfigService.getDbSysConfig(SysConfigConstant.UPLOAD_WATER_POSITION, siteId);
            String watermarkedmedium1200 = sysConfigService.getDbSysConfig(
                    SysConfigConstant.WATERMARK_PIC_PATH1200, siteId);
            String wmAllPath1200 = initFullPathByOrder(watermarkedmedium1200, fileName);
            
//            logger.info ("wmAllPath1200>>"+wmAllPath1200);
//            logger.info ("mediumAllPath1200>>"+mediumAllPath1200);
            
            //add by xiayunan 2017-09-11
            if(StringUtil.notBlank(position)&&!"Custom".equals(position)){
            	ImageAnalyseUtil.waterMarkPic(wmAllPath1200, mediumAllPath1200, waterPic,position, true);
            }else{
            	ImageAnalyseUtil.SpePositionWaterMarkPic(wmAllPath1200, mediumAllPath1200, waterPic, waterPicPostion, Integer.valueOf(transparency), true);
            }
			
			
            // logger.info ("wmAllPath："+wmAllPath);
            addPicAllPath(wmAllPath1200, 9, picId);
        }
        
     // 小图 400
        param.put("picType", 10);
        // 查询400小图是否已存在
        String small400 = aboutPictureMapper.selectPicPathByType(param);
        String smallPath400 = sysConfigService
                .getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH400, siteId);
        String smallAllPath400 = initFullPathByOrder(smallPath400, fileName);
//        logger.info (small400+"smallAllPath400>>>>"+smallAllPath400);
        if (small400 == null) {
            int smallSize400 = Integer
                    .valueOf(sysConfigService.getDbSysConfig(
                            SysConfigConstant.SMALL_PIC_SIZE400, siteId));
            if (smallSize400 <= picSize) {
                ImageAnalyseUtil.gmAlterImg(smallSize400, Path,
                        initFullPathByOrder(smallPath400, fileName), width,
                        height, synFlag);
            } else {
                ImgFileUtils.makeDirectory(smallAllPath400);
                ImgFileUtils.copyFile(Path, smallAllPath400);
            }
            addPicAllPath(smallAllPath400, 10, picId);
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
	public static void main(String[] args) {
		 File file1 = new File(".\\test1.txt");
	        File file2 = new File("D:\\workspace\\test\\test1.txt");
	        System.out.println("-----默认相对路径：取得路径不同------");
	        System.out.println(file1.getPath());
	        System.out.println(file1.getAbsolutePath());
	        System.out.println("-----默认绝对路径:取得路径相同------");
	        System.out.println(file2.getPath());
	        System.out.println(file2.getAbsolutePath());
	}
	//TODO（查询）
}
