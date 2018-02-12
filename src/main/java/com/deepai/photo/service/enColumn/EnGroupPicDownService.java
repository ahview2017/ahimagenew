package com.deepai.photo.service.enColumn;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepai.photo.bean.CpCategory;
import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicGroupCategory;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpPictureDownloadrecord;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.NumberUtils;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.ZipCompress;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.controller.util.UserUtils;
import com.deepai.photo.mapper.AboutPictureMapper;
import com.deepai.photo.mapper.ClientPictureMapper;
import com.deepai.photo.mapper.CpCategoryMapper;
import com.deepai.photo.mapper.CpPicGroupCategoryMapper;
import com.deepai.photo.mapper.CpPicGroupMapper;
import com.deepai.photo.mapper.CpPictureMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.EnPicDownMapper;
import com.deepai.photo.service.admin.SysConfigService;
@Service
public class EnGroupPicDownService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private SysConfigService sysConfigService;
	
	@Resource
	private AboutPictureMapper aboutPictureMapper;
	
	@Resource
	private ClientPictureMapper clientPictureMapper;
	
	@Resource
	private CpPictureMapper cpPictureMapper;
	
	@Resource
	private EnPicDownMapper enPicDownMapper;
	
	@Resource
	private CpPicGroupMapper cpPicGroupMapper;
	
	@Autowired
	private CpUserMapper cpUserMapper;
	
	@Autowired
	private CpPicGroupCategoryMapper cpPicGroupCategoryMapper;
	
	@Autowired
	private CpCategoryMapper cpCategoryMapper;
	
	
	
	/**
	 * 下载zip文件
	 * @param filename 文件的绝对路径
	 * @param res 输出流
	 */
	public static void downloadZipFile(String filename,
			HttpServletResponse res) {
		if (StringUtils.isNotBlank(filename)) {
			BufferedInputStream bis = null;
			OutputStream os = null;
			try {
				String zipFile = new File(filename).getName();
				zipFile = new String(zipFile.getBytes("GBK"),"ISO8859_1");

				res.reset();
				res.getCharacterEncoding();
				res.setContentType("application/x-tar");
				//				res.setContentType("application/x-gzip");
				res.setHeader("Content-Disposition", "attachment; filename="
						+ zipFile);
				os = res.getOutputStream();
				// res.setHeader("Content_Length",String.valueOf(new
				// File(filename).length()));
				bis = new BufferedInputStream(new FileInputStream(filename));
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = bis.read(buf)) > 0) {
					os.write(buf, 0, len);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				//logger.error("找不到图片 {}. ", filename, e);
			} catch (IOException e) {
				e.printStackTrace();
				//logger.error("读取文件 { }出错. " + filename, e);
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
					} // 关闭流
				}
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
					} // 关闭输出
				}
			}
		}
	}
	
	/**
	 * 下载单张图片 
	 * @author xiayunan
	 * @param picIds
	 * @param request
	 * @param response
	 * @param type
	 * @param langType
	 * @param groupId
	 * @throws Exception
	 */
	public void downSinglePic(String picIds, HttpServletRequest request ,HttpServletResponse response,Integer type, Integer langType,Integer groupId) throws Exception {
		int siteId =SessionUtils.getSiteId(request);
		CpUser user = SessionUtils.getUser(request);
		//处理图片
		int nRandom = 1000;
		String stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
		String tempPath=sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId);
		// 图片及xml文件的临时存放目录
		String filePath = tempPath + stemp;
		// 确保不存在同名的目录
		while (new File(filePath).exists()) {
			stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
			filePath = tempPath + stemp;
		}
		logger.debug("filepath = "+filePath);
		ImgFileUtils.makeDirectory(filePath + File.separator + "dir");

		String fileName = null;
		String oriPicPath = null;//原图
		String txtPicInfo = "";
		String zipName = filePath + ".zip";
		List<CpPictureDownloadrecord> recordList=new ArrayList<CpPictureDownloadrecord>();
		//获取图片list ,包括原图地址
		
		try {
			List<CpPicture> pictures = enPicDownMapper.selectByPrimaryKey(picIds);
			// 得到对象、对象filename
			try {
				for (CpPicture picture : pictures) {
					
					fileName = picture.getFilename();
					oriPicPath= picture.getOriAllPath();
					if(oriPicPath == null){
						oriPicPath = fileName;
					}
					// 根据对象filename找到原图复制到临时目录
					ImgFileUtils.copyFile(oriPicPath, filePath+ File.separator + fileName);
					//获取稿件及分类   edit by xiayunan@20180206
					CpPicGroup picGroup = cpPicGroupMapper.selectByPrimaryKey(picture.getGroupId());//获取图片对应的稿件
					String id = "";//稿件类别
					String cateName = "";//稿件类别名称
					List<CpPicGroupCategory> categoryList =cpPicGroupCategoryMapper.selectByGroupId(picGroup.getId());
					for (CpPicGroupCategory cpPicGroupCategory : categoryList) {
						Integer categoryId = cpPicGroupCategory.getCategoryId();
						CpCategory cpCategory = cpCategoryMapper.selectByPrimaryKey(categoryId);
						id +=" "+categoryId.toString();
						if(cpCategory!=null){
							cateName +=" "+cpCategory.getCategoryName();
						}
					}
					//稿件文本文件
					if(type==1){
						String substring = fileName.substring(0, fileName.lastIndexOf('.'));
						String txtPath=filePath+ File.separator + substring+".txt";
						BufferedWriter inOut = null;
						try {
							inOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtPath, true)));
							inOut.write("稿件标题："+picGroup.getTitle()+ "\r\n"
									+"稿件说明："+picGroup.getMemo()+ "\r\n"
									+"稿件类别："+cateName+ "\r\n"
									+"关键词："+picture.getKeywords()+ "\r\n"
									+"编辑人员："+picture.getEditor()+ "\r\n"
									+"文件名："+picture.getFilename()+ "\r\n"
									+"作者："+picture.getAuthorName()+ "\r\n"
									+"拍摄时间："+picture.getFilmTime()+ "\r\n"
									+"图片说明："+picture.getMemo()+ "\r\n");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								inOut.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
					//记录图片下载日志
					String userName = cpUserMapper.findUserNameByUid(picGroup.getAuthorId());
					CpPictureDownloadrecord record=new CpPictureDownloadrecord();
					record.setDownloadTime(new Date());
					record.setAuthorId(picGroup.getAuthorId());
					record.setPictureGroupId(picGroup.getId());
					record.setPictureId(picture.getId());
					record.setPictureGroup(id);
					record.setPictureSignTime(picGroup.getSginTime());						
					record.setPictureAuthor(picGroup.getAuthor());
					record.setAuthorLoginName(userName);//作者登录名
					record.setPictureFileName(fileName);
					record.setPictureFilePath(oriPicPath);
					record.setPictureTitle(picture.getTitle());
					record.setSiteId(siteId);
					record.setUserIP(UserUtils.getLocalIp(request));
					record.setUserName(user.getUserName());
//					record.setDownLoadPrice(user.getBalanceBasePerprice());
//					record.setSubscriberType((int)user.getSubscriptionType());
					record.setEditUser(picGroup.getFristPfdUser());
					record.setUserId(user.getId());
					record.setIsInteriorDownLoad(1);
					record.setWatermarker(CommonConstant.BYTE1);
					record.setLangType(langType);
					recordList.add(record);
			}
				clientPictureMapper.insertDownRecords(recordList);
			} catch (FileNotFoundException e) {
				logger.error("找不到图片文件。", e);
			} catch (IOException e) {
				logger.error("读取图片文件异常", e);
			}catch (Exception e) {
				//logger.error("生成水印图片异常",e);
				logger.error("插入下载记录异常",e);
			}
			// 遍历所有列表里面的id之后将目录里面的文件进行压缩
			
			logger.info("<<<filePath:"+filePath);	
			logger.info("<<<zipName:"+zipName);	
			ZipCompress.writeByApacheZipOutputStream(filePath,zipName, "");
			// 将生成的zip文件输出
			downloadZipFile(zipName, response);

			// 删除临时文件
			ImgFileUtils.deleteDirectory(filePath);
			if (ImgFileUtils.isFileExist(zipName)) {
				ImgFileUtils.deleteFile(zipName);
			}
		} catch (FileNotFoundException e) {
			logger.error("找不到压缩文件。", e);
		} catch (IOException e) {
			logger.error("读取压缩文件异常", e);
		}
		
	}
	
	/**
	 * 下载稿件组图
	 * @author xiayunan
	 * @param groupIds
	 * @param request
	 * @param response
	 * @param type
	 * @param langType
	 * @throws Exception
	 */
	public void downGrouplePic(String groupIds, HttpServletRequest request, HttpServletResponse response, Integer type, Integer langType) throws Exception {
		
		int siteId =SessionUtils.getSiteId(request);
		CpUser user = SessionUtils.getUser(request);
		//处理图片
		int nRandom = 1000;
		String stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
		String tempPath=sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId);
		// 图片及xml文件的临时存放目录
		String filePath = tempPath + stemp;
		// 确保不存在同名的目录
		while (new File(filePath).exists()) {
			stemp = DateUtils.format(new Date(),DateUtils.sdfLongTimePlusMill)+ "-" + NumberUtils.randomInteger(nRandom);
			filePath = tempPath + stemp;
		}
		logger.debug("filepath = "+filePath);
		ImgFileUtils.makeDirectory(filePath + File.separator + "dir");
		
		String fileName = null;
		String oriPicPath = null;//原图	
		String zipName = filePath + ".zip";
		String[] gIds = groupIds.split(",");
		CpPicGroup picGroup = null;
		List<CpPictureDownloadrecord> recordList=new ArrayList<CpPictureDownloadrecord>();		
		try {
			for (String groupId : gIds) {
				// 得到对象、对象filename
				try {
					picGroup = cpPicGroupMapper.selectByPrimaryKey(Integer.parseInt(groupId));
					List<CpPicture> pictures = enPicDownMapper.selectPicByGroupId(Integer.parseInt(groupId));
					
					//获取稿件类别
					String id = "";//稿件类别
					String cateName = "";//稿件类别名称
					List<CpPicGroupCategory> categoryList =cpPicGroupCategoryMapper.selectByGroupId(picGroup.getId());
					for (CpPicGroupCategory cpPicGroupCategory : categoryList) {
						Integer categoryId = cpPicGroupCategory.getCategoryId();
						CpCategory cpCategory = cpCategoryMapper.selectByPrimaryKey(categoryId);
						id +=" "+categoryId.toString();
						if(cpCategory!=null){
							cateName +=" "+cpCategory.getCategoryName();
						}
					}
					if(type==1){
						String txtPath=filePath+ File.separator + picGroup.getTitle()+".txt";
						BufferedWriter inOut = null;
						try {
							/*p.FILENAME fileName,p.AUTHOR_NAME authorName,p.CATEGORY_INFO gategoryInfo,p.CREATE_TIME createTime,p.EDITOR editor,p.EX_DATETIME exDatetime,p.FILM_TIME filmTime,p.MEMO memo,p.PEOPLE people,p.PLACE place,p.TITLE title,p.UPLOADER uploader,*/
							inOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtPath, true)));
							inOut.write("稿件标题："+picGroup.getTitle()+ "\r\n"
									+"稿件类别："+cateName+ "\r\n"
									+"关键词："+picGroup.getKeywords()+ "\r\n"
									+"创建人员："+picGroup.getCreator()+ "\r\n"
									+"作者："+picGroup.getAuthor()+ "\r\n"
									+"创建时间："+picGroup.getCreateTime()+ "\r\n"
									+"稿件说明："+picGroup.getMemo()+ "\r\n");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								inOut.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					if(!pictures.isEmpty()){
						for (CpPicture picture : pictures) {
							fileName = picture.getFilename();
							oriPicPath= picture.getOriAllPath();
							if(oriPicPath == null){
								oriPicPath = fileName;
							}
							// 根据对象filename找到原图复制到临时目录
							ImgFileUtils.copyFile(oriPicPath, filePath+ File.separator + fileName);
							
							
							
							
							//稿件文本文件
							if(type==1){
								String substring = fileName.substring(0, fileName.lastIndexOf('.'));
								String txtPath=filePath+ File.separator + substring+".txt";
								BufferedWriter inOut = null;
								try {
									
									/*p.FILENAME fileName,p.AUTHOR_NAME authorName,p.CATEGORY_INFO gategoryInfo,p.CREATE_TIME createTime,p.EDITOR editor,p.EX_DATETIME exDatetime,p.FILM_TIME filmTime,p.MEMO memo,p.PEOPLE people,p.PLACE place,p.TITLE title,p.UPLOADER uploader,*/
									inOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtPath, true)));
									inOut.write("稿件标题："+picGroup.getTitle()+ "\r\n"
											+"稿件说明："+picGroup.getMemo()+ "\r\n"
											+"稿件类别："+cateName+ "\r\n"
											+"图片说明："+picture.getMemo()+ "\r\n"
											+"关键词："+picture.getKeywords()+ "\r\n"
											+"编辑人员："+picture.getEditor()+ "\r\n"
											+"文件名："+picture.getFilename()+ "\r\n"
											+"作者："+picture.getAuthorName()+ "\r\n"
											+"拍摄时间："+picture.getFilmTime()+ "\r\n");
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									try {
										inOut.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							//记录图片下载日志
//							String id = "";//稿件类别
//							List<CpPicGroupCategory> categoryList =cpPicGroupCategoryMapper.selectByGroupId(picGroup.getId());
//							for (CpPicGroupCategory cpPicGroupCategory : categoryList) {
//								Integer categoryId = cpPicGroupCategory.getCategoryId();
//								id +=" "+categoryId.toString();
//							}
							
							String userName = cpUserMapper.findUserNameByUid(picGroup.getAuthorId());
							CpPictureDownloadrecord record=new CpPictureDownloadrecord();
							record.setDownloadTime(new Date());
							record.setAuthorId(picGroup.getAuthorId());
							record.setPictureGroupId(picGroup.getId());
							record.setPictureId(picture.getId());
							record.setPictureGroup(id);
							record.setPictureSignTime(picGroup.getSginTime());						
							record.setPictureAuthor(picGroup.getAuthor());
							record.setAuthorLoginName(userName);//作者登录名
							record.setPictureFileName(fileName);
							record.setPictureFilePath(oriPicPath);
							record.setPictureTitle(picture.getTitle());
							record.setSiteId(siteId);
							record.setUserIP(UserUtils.getLocalIp(request));
							record.setUserName(user.getUserName());
//							record.setDownLoadPrice(user.getBalanceBasePerprice());
							record.setSubscriberType(Integer.parseInt(user.getSubscriptionType()==null?"0":user.getSubscriptionType()+"")); 
							if (user.getSubscriptionType() == null) {
								record.setSubscriberType(null);
							}else{
								record.setSubscriberType((int)user.getSubscriptionType());
							}
							record.setEditUser(picGroup.getFristPfdUser());
							record.setUserId(user.getId());
							record.setIsInteriorDownLoad(1);
							record.setWatermarker(CommonConstant.BYTE1);
							record.setLangType(langType);
							recordList.add(record);
						}
					}
				} catch (FileNotFoundException e) {
					logger.error("找不到图片文件。", e);
				} catch (IOException e) {
					logger.error("读取图片文件异常", e);
				}catch (Exception e) {
					logger.error("插入图片下载记录异常",e);
				}
				
			}		
			clientPictureMapper.insertDownRecords(recordList);
		
		
			
			// 遍历所有列表里面的id之后将目录里面的文件进行压缩
			logger.info("<<<filePath:"+filePath);	
			logger.info("<<<zipName:"+zipName);	
			ZipCompress.writeByApacheZipOutputStream(filePath,zipName, "");
			// 将生成的zip文件输出
			downloadZipFile(zipName, response);

			// 删除临时文件
			ImgFileUtils.deleteDirectory(filePath);
			if (ImgFileUtils.isFileExist(zipName)) {
				ImgFileUtils.deleteFile(zipName);
			}
			
		} catch (FileNotFoundException e) {
			logger.error("找不到压缩文件。", e);
		} catch (IOException e) {
			logger.error("读取压缩文件异常", e);
		}
	}

}
