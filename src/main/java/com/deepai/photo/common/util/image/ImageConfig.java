package com.deepai.photo.common.util.image;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.service.admin.SysConfigService;

/**
 * @Title 图片文件常用参数及变量
 * @Author ZhouQiang
 * @CrTime 2010-4-13 下午04:47:02
 * @Version 1.5
 */
public class ImageConfig {
	protected static Logger logger = LoggerFactory.getLogger(ImageConfig.class);

	/**
	 * 接收的图片类型的后缀
	 */
	public final static String[] Accepted_Suffix = { "jpg", "tif", "png" };
	/**
	 * 接收的实际图片类型（MIME）
	 */
	public final static String[] Accepted_Content_Type = { "jpg", "tif", "png" };

	/**
	 * 默认站点类型
	 */
	public final static String DEFAULT_SITE = "1";

	/**
	 * 图片大小的枚举类型
	 */
	public enum ArchivePictureSize {
		/**
		 * 小图
		 */
		SMALL, /**
				 * 中图
				 */
		MEDIUM, /**
				 * 带水印的中图
				 */
		WATERMARKEDMEDIUM, /**
							 * 大图
							 */
		BIG, /**
				 * 原图
				 */
		ORIGINAL;
	}

	/**
	 * 根据图片参数类型和图片文件名得到图片在服务器上的存放路径（没有则生成）
	 * 
	 * @param filename
	 *            规则命名的图片名称
	 * @param size
	 *            图片参数类型
	 * @return 对应图片类型的图片在服务器上的路径
	 * @throws Exception 
	 */
	public static String getFilePath(String filename, String size, int siteId,SysConfigService sysConfigService) throws Exception {
		return getFilePathOnServer(getPictureSizeType(size), filename, siteId,sysConfigService);
	}

	/**
	 * 根据图片大小类型和图片文件名得到图片在服务器上的存放路径（没有则生成）
	 * 
	 * @param aps
	 *            图片大小类型
	 * @param filename
	 *            规则命名的图片名称
	 * @param site
	 *            站点Id
	 * @return 对应图片类型的图片在服务器上的路径
	 * @throws Exception 
	 */
	public static String getFilePathOnServer(ArchivePictureSize aps,String filename, int siteId,SysConfigService sysConfigService) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(filename)) {
			switch (aps) {
			case SMALL:
				sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId));
				break;
			case MEDIUM:
				sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.MEDIUM_PIC_PATH, siteId));
				break;
			case WATERMARKEDMEDIUM:
				sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.WATERMARKEDMEDIUM_PIC_PATH,siteId));
				break;
			case BIG:
				sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.BIG_PIC_PATH, siteId));
				break;
			case ORIGINAL:
				sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.ORIGINAL_PIC_PATH, siteId));
				break;
			default:
				sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId));
				break;
			}
			// 无论配置中的路径后是否有路径分隔符，都在路径后再加一个分隔符
			sb.append(File.separator);
			sb.append(filename.substring(0, 4)).append(File.separator);
			sb.append(filename.substring(0, 8)).append(File.separator);
			sb.append(filename);
		} else {
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.SMALL_PIC_PATH, siteId));
			sb.append(File.separator);
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_PICTURE, siteId));
		}
		ImgFileUtils.makeDirectory(sb.toString());
		return sb.toString();
	}

	/**
	 * 根据图片文件名得到图片在服务器上的存放路径（没有则生成）
	 * 
	 * @param filename
	 *            规则命名的图片名称
	 * @param site
	 *            站点Id
	 * @return 首页图片在服务器上的路径
	 * @throws Exception 
	 */
	public static String getCoverPicturePathOnServer(String filename, int siteId,SysConfigService sysConfigService) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(filename)) {
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.COVER_PIC_PATH, siteId));
			// 无论配置中的路径后是否有路径分隔符，都在路径后再加一个分隔符
			sb.append(File.separator);
			sb.append(filename);
		} else {
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.COVER_PIC_PATH, siteId));
			sb.append(File.separator);
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_COVER_PICTURE, siteId));
		}
		ImgFileUtils.makeDirectory(sb.toString());
		return sb.toString();
	}

	/**
	 * 根据图片名得到图片在服务器上的存放路径（没有则生成）
	 * @param filename
	 * @param site
	 * @return
	 * @throws Exception 
	 */
	public static String getWaterMarkPicturePathOnServer(String filename,int siteId,SysConfigService sysConfigService) throws Exception{
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotBlank(filename)){
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PATH, siteId));
			sb.append(File.separatorChar);
			sb.append(filename);
		}else{
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PATH, siteId));
			sb.append(File.separatorChar);
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId));
		}
		ImgFileUtils.makeDirectory(sb.toString());
		return sb.toString();
	}
	
	/**
	 * weixing 获取应用下临时文件目录
	 * @param filename
	 * @param site
	 * @return
	 * @throws Exception 
	 */
	public static String getTempPathOnServer(String filename,int siteId,SysConfigService sysConfigService) throws Exception{
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotBlank(filename)){
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId));
			sb.append(File.separatorChar);
			sb.append(filename);
		}else{
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId));
			sb.append(File.separatorChar);
		}
		ImgFileUtils.makeDirectory(sb.toString());
		return sb.toString();
	}
	/**
	 * 取到图片的参数
	 * @param filename
	 * @param site
	 * @return
	 * @throws Exception 
	 */
	public static String getPicSizeOnServer(String filename,int siteId,SysConfigService sysConfigService) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(filename)&&"1000".equalsIgnoreCase(filename)) {
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.PICSIZE_1000, siteId));	
		}
		if (StringUtils.isNotBlank(filename)&&"2000".equalsIgnoreCase(filename)) {
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.PICSIZE_2000, siteId));
		}
		if (StringUtils.isNotBlank(filename)&&"3000".equalsIgnoreCase(filename)) {
			sb.append(sysConfigService.getDbSysConfig(SysConfigConstant.PICSIZE_3000, siteId));
		}
		ImgFileUtils.makeDirectory(sb.toString());
		return sb.toString();
	}
	
	/**
	 * 根据图片参数类型获取图片大小类型
	 * 
	 * @param size
	 *            图片参数类型
	 */
	public static ArchivePictureSize getPictureSizeType(String size) {
		ArchivePictureSize aps = null;
		// size.equalsIgnoreCase("s");
		if ("O".equals(size))
			aps = ArchivePictureSize.ORIGINAL;
		else if ("B".equals(size))
			aps = ArchivePictureSize.BIG;
		else if ("M".equals(size))
			aps = ArchivePictureSize.MEDIUM;
		else if ("W".equals(size))
			aps = ArchivePictureSize.WATERMARKEDMEDIUM;
		else
			aps = ArchivePictureSize.SMALL;
		return aps;
	}
	
	
	 /**
     * 根据文件名获取签报存储路径
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月4日 下午2:20:08
     * @param sFileName
     * @return
     * @throws Exception
     */
    public static String getQbPath(int siteId, SysConfigService sysConfigService) throws Exception {
        String sQanBaoPath = sysConfigService
                .getDbSysConfig(SysConfigConstant.QIANBAO_FILE_PATH, siteId);
        if (StringUtils.isEmpty(sQanBaoPath)) {
            return null;
        }

//        String sYear = DateUtils.getNowYear();//filename.substring(0, 4);
//        String sMonth = DateUtils.getNowYear()+DateUtils.getNowMonth()+DateUtils.getNowDay();//filename.substring(0, 8);

//        return sQanBaoPath + File.separator + sYear + File.separator
//                + sMonth+File.separator;
        return sQanBaoPath;
    }
	
	
	
}
