package com.deepai.photo.service.oldPhotoUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpOldpictureExcelInfo;
import com.deepai.photo.bean.CpOldpictureExcelInfoExample;
import com.deepai.photo.bean.CpOldpictureExcelList;
import com.deepai.photo.bean.CpOldpictureExcelListExample;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.io.file.FileUtil;
import com.deepai.photo.common.util.oldUpload.ExcelUploadUtil;
import com.deepai.photo.mapper.CpOldpictureExcelInfoMapper;
import com.deepai.photo.mapper.CpOldpictureExcelListMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.mapper.CpSystemConfigMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.picture.OldFlowService;
import com.deepai.photo.service.picture.OldPictureService;

@Service
@Transactional
public class OldPhotoUploadService {
	
	private static final Logger logger = Logger.getLogger(OldPhotoUploadService.class);
	
	@Autowired
	private CpSystemConfigMapper configMapper;
	@Autowired
	private CpOldpictureExcelInfoMapper cpOldpictureExcelInfoMapper;
	@Autowired
	private CpOldpictureExcelListMapper cpOldpictureExcelListMapper;
	@Autowired
	private SysConfigService configService;
	@Autowired
	private OldPictureService oldPictureService;
	@Autowired
	private OldFlowService oldFlowService;
	@Autowired
	private CpPictureMapper cpPictureMapper;
	
	@Autowired
	private SysConfigService sysConfigService;

	public int uploadZip(MultipartFile zipFile, CpUser cpUser, int siteId)
			throws Exception {
		long begin = System.currentTimeMillis();
		logger.info("上传并解压缩入库---------------------------->");
		String fileName = zipFile.getOriginalFilename();
		// 取到最后一个.。
		int start = fileName.lastIndexOf(".");
		// 截取上传文件的 字符串名字。+1是去掉反斜杠。
		String fileFormat = fileName.substring(start);
		/*if (!".zip".equals(fileFormat)) {
			throw new RuntimeException("请上传zip文件");
		}*/
		synchronized (this) {
			 long startTime = System.currentTimeMillis();    //获取开始时间
			String path = "";
			String path_ = "";
			Integer fileNum = cpOldpictureExcelInfoMapper.getMaxId();
			String defaultPath = configService.getDbSysConfig("OLDPHOTOPATH",
					siteId);
			// 得到上傳存放文件路徑
			path = ExcelUploadUtil.getfilePath(defaultPath, fileNum);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			long temp = System.currentTimeMillis();
			logger.info("准备上传zip文件---------------------------->"+(temp-begin));
			
			// 上傳文件
			zipFile.transferTo(targetFile);
			logger.info("上传zip文件完毕---------------------------->"+(System.currentTimeMillis()-begin));
			long endTime = System.currentTimeMillis();    //获取结束时间

             System.out.println("程序运行时间：" + (endTime - startTime)/1000 + "ms");
			/*try {*/
				ExcelUploadUtil.unzip(targetFile.getPath(), path, false);
				path_ = path.replace(defaultPath, "");
				if (path_.indexOf("/") == 0) {
					path_ = path_.substring(1, path_.length());
				}
				File file = new File(path);
				// 读取解压的文件夹下的文件
				List<String> fileList = ExcelUploadUtil.lisefile(file);
				if (fileList == null || fileList.size() == 0) {
					ExcelUploadUtil.delFolder(path);
					throw new RuntimeException("zip文件为空");
				}
				String excelFilePath = path;
				boolean flag = true;
				int dataNum = 0;
				for (String string : fileList) {
					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
					String excelFileName = string.substring(string
							.lastIndexOf("."));
					// 判断如果不是excel返回0
					if (!ExcelUploadUtil.OFFICE_EXCEL_2003_POSTFIX
							.equals(excelFileName)
							&& !ExcelUploadUtil.OFFICE_EXCEL_2010_POSTFIX
									.equals(excelFileName)) {
						// 统计不是excel文件的文件数量
						dataNum++;
					} else {
						flag = false;
						excelFilePath = excelFilePath + "/" + string;
					}
				}
				if ( dataNum==0) {
					ExcelUploadUtil.delFolder(path);
					throw new RuntimeException("压缩包中无图片文件");
				}
				// 判断是否包含excel文件
				if (flag) {
					ExcelUploadUtil.delFolder(path);
					throw new RuntimeException("zip中没有excel文件");
				}
				File excelFile = new File(excelFilePath);
				CpOldpictureExcelInfo cpOldpictureExcelInfo = new CpOldpictureExcelInfo();
				// 读取excel中总记录数
				int num = ExcelUploadUtil.briefReadExcel(excelFile);
				cpOldpictureExcelInfo.setFileName(fileName);
				cpOldpictureExcelInfo.setFilePath(path_);
				cpOldpictureExcelInfo.setRecordTotal(num);
				cpOldpictureExcelInfo.setUploadDate(new Date());
				cpOldpictureExcelInfo.setUploadUserid(cpUser.getId());
				if (path.endsWith("/")) 
					cpOldpictureExcelInfo.setFileAllpath(path  + fileName);
				else 
					cpOldpictureExcelInfo.setFileAllpath(path + "/" + fileName);
				// 保存文件信息
				int i = cpOldpictureExcelInfoMapper
						.insertSelective(cpOldpictureExcelInfo);
				if (i > 0) {
					// 读取excel
					List<CpOldpictureExcelList> list = ExcelUploadUtil
							.readExcel(excelFilePath,
									cpOldpictureExcelInfo.getId(), path);
					if (list != null && list.size() > 0) {
						// 将excel数据插入数据库
						i = cpOldpictureExcelListMapper.insertList(list);
						if (i > 0) {
							// 执行分析入库
							return analysisStorage(cpOldpictureExcelInfo,
									siteId, fileList, dataNum, path,cpUser);
						}
					} else {
						ExcelUploadUtil.delFolder(path);
						throw new RuntimeException("excel文件为空");
					}
				}

			/*} catch (Exception e) {
				ExcelUploadUtil.delFolder(path);
				throw new RuntimeException("上传文件异常");
			}*/
		}
		return 0;
	}

	public String moveZip(String zipFile, CpUser cpUser, int siteId)
			throws Exception {
		long begin = System.currentTimeMillis();
		logger.info("开始cp压缩文件---------------------------->");
		if (!zipFile.endsWith(".zip")) {
			throw new RuntimeException("请上传zip文件");
		}
		
		String defaultPath = configService.getDbSysConfig("OLDPHOTOPATH",siteId);
		String srcZipPath = configService.getDbSysConfig("OLDPHOTOZIPPATH",siteId);
		File srcZip = new File(srcZipPath+zipFile);
		if (!srcZip.isFile()) {
			logger.info(srcZip+"服务器上该文件不存在，请重新确认！");
			throw new RuntimeException(zipFile+"服务器上该文件不存在，请重新确认！");
		}
		Map<String, String> map = new HashMap<String, String>();
		String fileName = srcZip.getName();
		// 取到最后一个.。
		int start = fileName.lastIndexOf(".");
		// 截取上传文件的 字符串名字。+1是去掉反斜杠。
		String fileFormat = fileName.substring(start);
		
		synchronized (this) {
			 long startTime = System.currentTimeMillis();    //获取开始时间
			String path = "";
			String path_ = "";
			Integer fileNum = cpOldpictureExcelInfoMapper.getMaxId();
//			String defaultPath = "D:/trsphoto/old";
			
			
			// 得到上傳存放文件路徑
			path = ExcelUploadUtil.getfilePath(defaultPath, fileNum);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.getParentFile().mkdirs();
			}
			
/*			
 			// 上傳文件
			zipFile.transferTo(targetFile);
*/
			FileUtil.moveFile(srcZip, targetFile);
			 long endTime = System.currentTimeMillis();    //获取结束时间
			logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
			begin = System.currentTimeMillis();
			logger.info("开始解压缩文件---------------------------->");
             System.out.println("程序运行时间：" + (endTime - startTime)/1000 + "ms");
			/*try {*/
				ExcelUploadUtil.unzip(targetFile.getPath(), path, false);
			logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
			begin = System.currentTimeMillis();
			logger.info("解压缩文件完毕---------------------------->");
				path_ = path.replace(defaultPath, "");
				if (path_.indexOf("/") == 0) {
					path_ = path_.substring(1, path_.length());
				}
				File file = new File(path);
				logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
				begin = System.currentTimeMillis();
				logger.info("开始读取文件列表---------------------------->");
				// 读取解压的文件夹下的文件
				List<String> fileList = ExcelUploadUtil.lisefile(file);
				if (fileList == null || fileList.size() == 0) {
					ExcelUploadUtil.delFolder(path);
					throw new RuntimeException("zip文件为空");
				}
				map.put("picNums", ""+fileList.size());
				String excelFilePath = path;
				boolean flag = true;
				int dataNum = 0;
				for (String string : fileList) {
					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
					String excelFileName = string.substring(string
							.lastIndexOf("."));
					// 判断如果不是excel返回0
					if (!ExcelUploadUtil.OFFICE_EXCEL_2003_POSTFIX
							.equals(excelFileName)
							&& !ExcelUploadUtil.OFFICE_EXCEL_2010_POSTFIX
									.equals(excelFileName)) {
						// 统计不是excel文件的文件数量
						dataNum++;
					} else {
						flag = false;
						excelFilePath = excelFilePath + "/" + string;
					}
				}
				if ( dataNum==0) {
					ExcelUploadUtil.delFolder(path);
					throw new RuntimeException("压缩包中无图片文件");
				}
				// 判断是否包含excel文件
				if (flag) {
					ExcelUploadUtil.delFolder(path);
					throw new RuntimeException("zip中没有excel文件");
				}
				File excelFile = new File(excelFilePath);
				logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
				begin = System.currentTimeMillis();
				logger.info("开始读取文件列表---------------------------->");
				CpOldpictureExcelInfo cpOldpictureExcelInfo = new CpOldpictureExcelInfo();
				// 读取excel中总记录数
				int num = ExcelUploadUtil.briefReadExcel(excelFile);
				cpOldpictureExcelInfo.setFileName(fileName);
				cpOldpictureExcelInfo.setFilePath(path_);
				cpOldpictureExcelInfo.setRecordTotal(num);
				cpOldpictureExcelInfo.setUploadDate(new Date());
				cpOldpictureExcelInfo.setUploadUserid(cpUser.getId());
				if (path.endsWith("/")) 
					cpOldpictureExcelInfo.setFileAllpath(path  + fileName);
				else 
					cpOldpictureExcelInfo.setFileAllpath(path + "/" + fileName);
				logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
				begin = System.currentTimeMillis();
				logger.info("开始保存文件信息---------------------------->");
				// 保存文件信息
				int i = cpOldpictureExcelInfoMapper
						.insertSelective(cpOldpictureExcelInfo);
				if (i > 0) {
					logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
					begin = System.currentTimeMillis();
					logger.info("再次读取Excel信息---------------------------->");
					// 读取excel
					List<CpOldpictureExcelList> list = ExcelUploadUtil
							.readExcel(excelFilePath,
									cpOldpictureExcelInfo.getId(), path);
					map.put("picNums", ""+cpOldpictureExcelInfo.getId());
					if (list != null && list.size() > 0) {
						// 将excel数据插入数据库
						i = cpOldpictureExcelListMapper.insertList(list);
						logger.info("---------------------------->"+(System.currentTimeMillis() - begin));
						begin = System.currentTimeMillis();
						logger.info("Excel信息入库---------------------------->");
						if (i > 0) {
							// 执行分析入库
							analysisStorage(cpOldpictureExcelInfo,siteId, fileList, dataNum, path,cpUser);
							return "{\"excelId\":"+cpOldpictureExcelInfo.getId()+"}";
						}
					} else {
						ExcelUploadUtil.delFolder(path);
						throw new RuntimeException("excel文件为空");
					}
				}else {
					throw new RuntimeException("zip 文件信息入库失败！");
				}

			/*} catch (Exception e) {
				ExcelUploadUtil.delFolder(path);
				throw new RuntimeException("上传文件异常");
			}*/
		}
		
		return "zip文件入库异常";
	}
	
	/**
	 * oldFile Excel数据信息 fileList 文件名列表 dataNum 除去excel外的图片数量
	 * */
	private int analysisStorage(CpOldpictureExcelInfo oldFile, int siteId,
			List<String> fileList, int dataNum, String path,CpUser cpUser) throws Exception {
		
		long begin = System.currentTimeMillis();
		logger.info(" 分析入库---------------------------->");
		CpOldpictureExcelListExample example = new CpOldpictureExcelListExample();
		example.createCriteria().andExcelidEqualTo(oldFile.getId())
				.andPicFileNameIn(fileList);
		for (String string : fileList) {
			logger.info("文件列表--------------->"+string);
		}
		List<CpOldpictureExcelList> list = cpOldpictureExcelListMapper
				.selectByExampleWithBLOBs(example);
		for (CpOldpictureExcelList excel : list) {
			logger.info("对比后的文件列表--------------->"+excel.getPicFileName());
		}
		if (list.size() != dataNum) {
			ExcelUploadUtil.delFolder(path);
			throw new RuntimeException("图片数量与excel中的数据数量不等");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("oriPath", sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_CLASSIFICATION_PATH, siteId));
		map.put("minWidth", sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINWIDTH, siteId));
		map.put("minHeight",sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINHEIGHT, siteId));
		map.put("minM", sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMINLENGTH, siteId));
		map.put("maxM", sysConfigService.getDbSysConfig(SysConfigConstant.PICTUREMAXLENGTH, siteId));
		map.put("mediumSize", sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_SIZE, siteId));
		map.put("mediumPath", sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId));
		map.put("smallPicSize",sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_SIZE, siteId));
		map.put("smallPicPath",sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId));
		map.put("bigSize", sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_SIZE, siteId));
		map.put("bigPath", sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_PATH, siteId));
		map.put("position", sysConfigService.getDbSysConfig(SysConfigConstant.UPLOAD_WATER_POSITION, siteId));
		map.put("watermarkedmedium", sysConfigService.getDbSysConfig(SysConfigConstant.WATERMARKEDMEDIUM_PIC_PATH, siteId));
		map.put("waterPic", sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId));
		logger.info("分析完成---------------------------->"+(System.currentTimeMillis() - begin));
		return moreThread(oldFile.getId(),list, siteId,cpUser,map);
	}

	/**
	 * 采用线程池开启多个子线程，主线程等待所有的子线程执行完毕
	 * 这是原来的方法，没有使用多线程！！！！
	 * @throws Exception
	 */
/*	private int moreThread1(List<CpOldpictureExcelList> list, int siteId, CpUser cpUser)
			throws Exception {
		
		long begin = System.currentTimeMillis();
		logger.info(" 老照片稿件入库---------------------------->");
		ExecutorService exe = Executors.newFixedThreadPool(10);
		int total = list.size();
		// 将数据分块
		
		 * PageUtil pu = new PageUtil(Integer.valueOf(20), total,
		 * Integer.valueOf(1)); // 可以分成多少块 int pageCount = pu.getPageCount();
		 * for (int i = 1; i <= pageCount; i++) { pu = new
		 * PageUtil(Integer.valueOf(20), total, Integer.valueOf(i)); int start =
		 * pu.getFromIndex(); int stop = pu.getToIndex(); PictureService ps
		 * =pictureService; exe.execute(new Runnable() {
		 * 
		 * @Override public void run() { try { //处理文件 for (int i_ = start; i_ <
		 * stop; i_++) {
		 
		long temp = 0;
		for (int i_ = 0; i_ < list.size(); i_++) {
			temp = System.currentTimeMillis();
			logger.info(" 第"+i_+"张照片开始入库---------------------------->"+temp);
			File file = new File(list.get(i_).getFileAllpath());
			String name = file.getName();
			String filename = null;
			if (name.toLowerCase().lastIndexOf("jpg") >= name.length() - 4) {
				filename = getFileName("jpg");
			} else if (name.toLowerCase().lastIndexOf("tif") >= name.length() - 4) {
				filename = getFileName("tif");
			} else if (name.toLowerCase().lastIndexOf("png") >= name.length() - 4) {
				filename = getFileName("png");
			} else {
				filename = getFileName("jpg");
			}
			FileInputStream in = new FileInputStream(file);
			MultipartFile files = new MockMultipartFile(filename, in);
			CpPicture pic = oldPictureService.uploadOnePic(files, filename, siteId);
			CpPicGroup group = new CpPicGroup();
			group.setTitle(list.get(i_).getTitle());// 稿件标题
			group.setPeople(list.get(i_).getFigure());// 稿件人物
			group.setAuthor(list.get(i_).getAuthor());// 稿件作者名
			group.setKeywords(list.get(i_).getKeywords());// "稿件关键字"
			group.setPlace(list.get(i_).getPlace());// 地点
			group.setMemo(list.get(i_).getMemo());// 稿件说明
			group.setLangType(0);// 是 0:中文；1:英文
			// group.setProperties((byte)2);//是 0：新闻图片，1：专题图片
			group.setGoodsType(2);
			if (StringUtil.isNotEmpty(list.get(i_).getOldDate())) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				    Date date = sdf.parse(list.get(i_).getOldDate().replace(".", "-"));
					group.setForumsTime(date);// 是 拍摄时间（日期格式如2017-01-09）
				} catch (Exception e) {
					group.setForumsTime(new Date());// 是 拍摄时间（日期格式如2017-01-09）
				}
			} else {
				group.setForumsTime(new Date());// 是 拍摄时间（日期格式如2017-01-09）
			}
			group.setCategoryInfo(list.get(i_).getCategory());
			CpUser user = new CpUser();
			user.setUserName(list.get(i_).getAuthor());
			List<CpPicture> pics = new ArrayList<CpPicture>();
			pics.add(pic);
//			oldFlowService.makePicGroup(pics, group, false, user, siteId, 1,-1);
			list.get(i_).setPicGroupId(group.getId());
			cpOldpictureExcelListMapper.updateByPrimaryKeyWithBLOBs(list.get(i_));
			logger.info(" 第"+i_+"张老照片入库结束---------------------------->"+(System.currentTimeMillis()-temp));
		}
		
		 * } } catch (Exception e) { e.printStackTrace(); } } }); }
		 * exe.shutdown(); while (true) { if (exe.isTerminated()) {
		 * System.out.println("所有的子线程都结束了！"); break; } Thread.sleep(100); }
		 
		logger.info(" 所有老照片稿件入库结束---------------------------->"+(System.currentTimeMillis()-temp));
		return 0;
	}

	/**
	 * 获取文件名
	 */
	private static String getFileName(String type) throws Exception {
		String stp = "_p.jpg";
		if (type.equals("jpg")) {
			stp = "_p.jpg";
		} else if (type.equals("tif")) {
			stp = "_t.tif";
		} else if (type.equals("png")) {
			stp = "_p.png";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtils.getFormattedTime(DateUtils.sdfLongTime))
				.append("_");
		sb.append(System.currentTimeMillis());
		sb.append(stp);
		return sb.toString();
	}
	/**
	 * 查询已上传的zip包列表
	 * 
	 * @param request
	 * @return
	 */
	public List<CpOldpictureExcelInfo> getPhotoExcelList(Map<String, Object> map){
		CpOldpictureExcelInfoExample example =new CpOldpictureExcelInfoExample();
		example.or().andUploadUseridEqualTo(Integer.valueOf(map.get("userId").toString()));
		return cpOldpictureExcelInfoMapper.selectByExample(example);
	}
	
	/**
	 * 查询zip包列表中图片上传失败的数目
	 * @param request
	 * @return
	 */
	public CpOldpictureExcelInfo getPhotoExcelInfo(int zipId){
		return cpOldpictureExcelInfoMapper.selectByPrimaryKey(zipId);
	}
	
	public List<Map<String, Object>> getExcelPhotoExcelList(Map<String, Object> map){
		return cpOldpictureExcelListMapper.getExcelPhotoExcelList(map);
	}
	
	private int moreThread(int zipFileId ,List<CpOldpictureExcelList> list, int siteId, CpUser cpUser,Map<String, String> map){
		long begin = System.currentTimeMillis();
		logger.info("线程池开始 ---------------->");
		BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>(1000);
		ThreadPoolExecutor excutor = new ThreadPoolExecutor(5, 15, 60, TimeUnit.SECONDS, queue);
		OldPicThread oldPicThread;
		for (int j = 0; j < list.size(); j++) {
			oldPicThread = new OldPicThread(zipFileId,j+1, siteId, list.get(j),map);
			excutor.execute(oldPicThread);
			logger.info("线程池中的线程数目： "+excutor.getPoolSize()+"  ，队列中等待执行的任务数目： "+
			excutor.getQueue().size()+"  ,已经执行完的任务数目： "+ excutor.getCompletedTaskCount());
			
		}
		excutor.shutdown();
		logger.info("线程池结束 ---------------->"+(System.currentTimeMillis()-begin));
		return 0;
	}
	
	class OldPicThread implements Runnable{
		
		private int id;
		private int taskNum;
		private int siteId;
		private CpOldpictureExcelList cpOldpictureExcelList;
		private Map<String, String > map;
		
		public OldPicThread(int id,int num,int siteId,CpOldpictureExcelList cpOldpicture,Map<String, String> config) {
			this.id= id;
			this.taskNum = num;
			this.siteId = siteId;
			this.cpOldpictureExcelList = cpOldpicture;
			this.map = config;
		}
		
		@Override
		public void run() {
			long begin = System.currentTimeMillis();
			logger.info("正在执行 老照片入库 ---------------->"+taskNum);
			try {
				File file = new File(cpOldpictureExcelList.getFileAllpath());
				String name = file.getName();
				String filename = null;
				if (name.toLowerCase().lastIndexOf("jpg") >= name.length() - 4) {
					filename = getFileName("jpg");
				} else if (name.toLowerCase().lastIndexOf("tif") >= name.length() - 4) {
					filename = getFileName("tif");
				} else if (name.toLowerCase().lastIndexOf("png") >= name.length() - 4) {
					filename = getFileName("png");
				} else {
					filename = getFileName("jpg");
				}
				FileInputStream in = new FileInputStream(file);
				MultipartFile files = new MockMultipartFile(filename, in);
				CpPicture pic = oldPictureService.uploadOnePicT(files, filename, siteId,map);
				CpPicGroup group = new CpPicGroup();
				group.setTitle(cpOldpictureExcelList.getTitle());// 稿件标题
				group.setPeople(cpOldpictureExcelList.getFigure());// 稿件人物
				group.setAuthor(cpOldpictureExcelList.getAuthor());// 稿件作者名
				group.setKeywords(cpOldpictureExcelList.getKeywords());// "稿件关键字"
				group.setPlace(cpOldpictureExcelList.getPlace());// 地点
				group.setMemo(cpOldpictureExcelList.getMemo());// 稿件说明
				group.setLangType(0);// 是 0:中文；1:英文
				// group.setProperties((byte)2);//是 0：新闻图片，1：专题图片
				group.setGoodsType(2);
				if (StringUtil.isNotEmpty(cpOldpictureExcelList.getOldDate())) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
					    Date date = sdf.parse(cpOldpictureExcelList.getOldDate().replace(".", "-"));
						group.setForumsTime(date);// 是 拍摄时间（日期格式如2017-01-09）
					} catch (Exception e) {
						group.setForumsTime(new Date());// 是 拍摄时间（日期格式如2017-01-09）
					}
				} else {
					group.setForumsTime(new Date());// 是 拍摄时间（日期格式如2017-01-09）
				}
				group.setCategoryInfo(cpOldpictureExcelList.getCategory());
				CpUser user = new CpUser();
				user.setUserName(cpOldpictureExcelList.getAuthor());
				List<CpPicture> pics = new ArrayList<CpPicture>();
				pics.add(pic);
				oldFlowService.makePicGroup(pics, group, false, user, siteId, 1,-1,map);
				cpOldpictureExcelList.setPicGroupId(group.getId());
				cpOldpictureExcelInfoMapper.updateStorageNumById(cpOldpictureExcelList.getExcelid());
				cpOldpictureExcelListMapper.updateByPrimaryKeyWithBLOBs(cpOldpictureExcelList);
				
			} catch (Exception e) {
				CpOldpictureExcelInfo zipFileInfo = cpOldpictureExcelInfoMapper.selectByPrimaryKey(id);
				CpOldpictureExcelInfo zipFile = new CpOldpictureExcelInfo();
				zipFile.setId(id);
				zipFile.setPicUploadNum(zipFileInfo.getPicUploadNum()+1);
				cpOldpictureExcelInfoMapper.updateByPrimaryKeySelective(zipFile);
				logger.error("单张老照片入库异常：", e);
				logger.error(cpOldpictureExcelList.getPicFileName()+"入库失败"+cpOldpictureExcelList.getId());
				e.printStackTrace();
			}
			logger.info(" 老照片入库 执行完毕---------------->"+taskNum);
			logger.info(taskNum+"任务完成 ---------------->"+(System.currentTimeMillis()-begin));
		}
		
	}
	
	public List<String> getOldPicFailList(List<Map<String, Object>> successList,String filePath){
		
		List<String> successLists = new ArrayList<String>(successList.size());
		for (Map<String, Object> map : successList) {
			successLists.add((String) map.get("PIC_FILE_NAME"));
		}
		List<String> failList = new ArrayList<String>();
		List<String> list = ExcelUploadUtil.lisefile(new File(filePath));
		for (String string : list) {
			String excelFileName = string.substring(string
					.lastIndexOf("."));
			// 判断如果不是excel返回0
			if (!ExcelUploadUtil.OFFICE_EXCEL_2003_POSTFIX
					.equals(excelFileName)
					&& !ExcelUploadUtil.OFFICE_EXCEL_2010_POSTFIX
							.equals(excelFileName)) {
				
				if (!successLists.contains(string)) {
					failList.add(string);
					logger.info("还未入库的老照片----------------------->"+string);
				}
			} 
		}
		return failList;
	}
	
	public int getPicNums(String path){
		List<String> list = ExcelUploadUtil.lisefile(new File(path));
		int number = 0;
		if (list == null || list.size()==0) {
			throw new RuntimeException("该压缩文件分析入库失败");
		}
		//这是用来测试的，正式环境可以删掉 JSONARRAY
		JSONArray array = new JSONArray(); 
		for (String string : list) {
			String excelFileName = string.substring(string
					.lastIndexOf("."));
			// 判断如果不是excel返回0
			if (!ExcelUploadUtil.OFFICE_EXCEL_2003_POSTFIX
					.equals(excelFileName)
					&& !ExcelUploadUtil.OFFICE_EXCEL_2010_POSTFIX
							.equals(excelFileName)) {
				number ++;
				array.add(string);
			}
		}

		logger.info("已经入库的老照片----------------------->"+array.toString());
		return number;
	}
	
}
