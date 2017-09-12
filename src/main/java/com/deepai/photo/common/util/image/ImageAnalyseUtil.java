package com.deepai.photo.common.util.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpWaterMarkPicture;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.listener.SpringContextUtil;
import com.deepai.photo.common.util.NumberUtils;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.service.admin.SysConfigService;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.PNGDecodeParam;
import com.sun.media.jai.codec.PNGEncodeParam;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codecimpl.PNGCodec;

/**
 * @title 图片信息解析工具
 * @author LiangJC
 * @crTime 2010-4-15
 */
public class ImageAnalyseUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageAnalyseUtil.class);

	public static enum Color {
		red(255 * 256 * 256 + 0 * 256 + 0, 0), orange(255 * 256 * 256 + 128 * 256 + 0, 1), yellow(
				255 * 256 * 256 + 255 * 256 + 0,
				2), green(0 * 256 * 256 + 255 * 256 + 0, 3), cyan(0 * 256 * 256 + 255 * 256 + 255,
						4), blue(0 * 256 * 256 + 0 * 256 + 255, 5), purple(128 * 256 * 256 + 0 * 256 + 255,
								6), white(255 * 256 * 256 + 255 * 256 + 255, 7), black(0, 8);
		private int colorValue;
		private int serialNumber;

		private Color(int value, int no) {
			this.colorValue = value;
			this.serialNumber = no;
		}

		public int value() {
			return this.colorValue;
		}

		public int number() {
			return this.serialNumber;
		}

		public String string() {
			switch (this.serialNumber) {
			case 0:
				return "red";
			case 1:
				return "orange";
			case 2:
				return "yellow";
			case 3:
				return "green";
			case 4:
				return "cyan";
			case 5:
				return "blue";
			case 6:
				return "purple";
			case 7:
				return "white";
			case 8:
				return "black";
			default:
				return "unknown";
			}
		}
	}
	/*
	 * private static int red = 255*256*256+0*256+0; private static int orange =
	 * 255*256*256+128*256+0; private static int yellow = 255*256*256+255*256+0;
	 * private static int green = 0*256*256+255*256+0; private static int cyan =
	 * 0*256*256+255*256+255; private static int blue = 0*256*256+0*256+255;
	 * private static int purple = 128*256*256+0*256+255;
	 */

	/**
	 * 抽取Metadata
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	private static Metadata readMetadataFromFile(String filePath) throws Exception {

		File imgFile = new File(filePath);
		if (filePath.toLowerCase().lastIndexOf("tif") >= filePath.length() - 4) {
			return TiffMetadataReader.readMetadata(imgFile);
		}
		return JpegMetadataReader.readMetadata(imgFile);
	}

	/**
	 * 提取IPTC信息
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Directory extratIPTCFromFile(String filePath) throws Exception {
		return readMetadataFromFile(filePath).getDirectory(IptcDirectory.class);
	}

	/**
	 * 提取EXIF信息
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Directory extratEXIFFromFile(String filePath) throws Exception {
		return readMetadataFromFile(filePath).getDirectory(ExifDirectory.class);
	}

	/**
	 * 大图变小图,暂时只支持RGB的jpg
	 * 
	 * @param size
	 *            新图片大小
	 * @param file
	 *            大图
	 * @param Path_new
	 *            目标图
	 * @throws Exception
	 */
	public static void alterPic(int size, File file, String Path_new, BufferedImage img) throws Exception {

		if (!file.exists()) {
			throw new Exception("big pic not exists");
		}

		if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("jpg") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") == -1) {
			throw new Exception("Pic is not the right type");
		}

		BufferedImage src = null;
		try {
			File temp = file;

			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				temp = new File("c:\\" + new Date().getTime() + ".jpg");
				SeekableStream s = new FileSeekableStream(file);

				TIFFDecodeParam tifParam = new TIFFDecodeParam();

				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, tifParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				ImageIO.write(rImage, "jpeg", temp);
				src = javax.imageio.ImageIO.read(temp);
			} else {
				src = img;
			}

			int old_w = src.getWidth(null);
			int old_h = src.getHeight(null);
			int new_w = 0;
			int new_h = 0;
			if (old_w > old_h) {
				new_w = size;
				new_h = old_h * new_w / old_w;
			} else {
				new_h = size;
				new_w = old_w * new_h / old_h;
			}
			ImgFileUtils.makeDirectory(Path_new);
			BufferedImage altered_pic = alterSize(src, new_w, new_h);
			saveJPG(altered_pic, Path_new);
			altered_pic = null;
			// alterSize(new_w, new_h, temp, Path_new);
			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				temp.delete();
			}

			// src=null;

		} catch (Exception e) {
			src = null;
			throw e;
			// e.printStackTrace();
		}
	}

	/**
	 * 使用gmgraphicemagic 进行图片变换大小调用
	 * 
	 * @param size
	 * @param srcPath
	 * @param destPath
	 * @param img
	 * @param synFlag
	 *            是否异步进行图片处理,为空也是异步
	 * @throws Exception
	 */
	public static void gmAlterImg(int size, String srcPath, String destPath, int width, int height, Boolean synFlag)
			throws Exception {
		try {
			//
			ImgFileUtils.makeDirectory(destPath);
			int old_w = width;
			int old_h = height;
			int new_w = 0;
			int new_h = 0;
			if (old_w > old_h) {
				new_w = size;
				new_h = old_h * new_w / old_w;
			} else {
				new_h = size;
				new_w = old_w * new_h / old_h;
			}

			IMOperation op = new IMOperation();
			op.addImage(srcPath);
			op.resize(new_w, new_h);
			op.addImage(destPath);
			ConvertCmd convert = new ConvertCmd(true);
			if (synFlag == null || synFlag) {
				convert.setAsyncMode(true);
			}
			// add by xia.yunan@20170906
			SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
			convert.setSearchPath(sysConfigService.getDbSysConfig(
	                SysConfigConstant.LOCAL_GM_PATH,1));
			
			convert.run(op);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static int getSize(File file, BufferedImage src) throws Exception {

		if (!file.exists()) {
			throw new Exception("big pic not exists");
		}
		if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("jpg") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") == -1) {
			throw new Exception("Pic is not the right type");
		}

		// Image src = null;
		// BufferedImage src=null;
		try {
			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				SeekableStream s = new FileSeekableStream(file);

				TIFFDecodeParam tifParam = new TIFFDecodeParam();

				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, tifParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				return rImage.getWidth() > rImage.getHeight() ? rImage.getWidth() : rImage.getHeight();
			}

			/*
			 * InputStream is=new BufferedInputStream(new
			 * FileInputStream(file)); ImageIO.setUseCache(false);
			 */
			// src = javax.imageio.ImageIO.read(file);
			int size = src.getWidth(null) > src.getHeight(null) ? src.getWidth(null) : src.getHeight(null);
			// return
			// src.getWidth(null)>src.getHeight(null)?src.getWidth(null):src.getHeight(null);
			// src=null;
			return size;
		} catch (Exception e) {
			throw e;
			// e.printStackTrace();
		}
	}

	public static int getHeight(File file) throws Exception {

		if (!file.exists()) {
			throw new Exception("big pic not exists");
		}
		if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("jpg") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") == -1) {
			throw new Exception("Pic is not the right type");
		}

		Image src = null;
		try {
			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				SeekableStream s = new FileSeekableStream(file);

				TIFFDecodeParam tifParam = new TIFFDecodeParam();

				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, tifParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				return rImage.getHeight();
			}
			src = javax.imageio.ImageIO.read(file);
			return src.getHeight(null);
		} catch (Exception e) {
			throw e;
			// e.printStackTrace();
		}
	}

	public static int getWidth(File file) throws Exception {

		if (!file.exists()) {
			throw new Exception("big pic not exists");
		}
		if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("jpg") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") == -1) {
			throw new Exception("Pic is not the right type");
		}

		Image src = null;
		try {
			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				SeekableStream s = new FileSeekableStream(file);

				TIFFDecodeParam tifParam = new TIFFDecodeParam();

				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, tifParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				return rImage.getWidth();
			}
			src = javax.imageio.ImageIO.read(file);
			return src.getWidth(null);
		} catch (Exception e) {
			throw e;
			// e.printStackTrace();
		}
	}

	/** */
	/**
	 * 把图片印刷到图片上,只支持jpg
	 * 
	 * @param pressImg
	 *            -- 水印文件
	 * @param targetImg
	 *            -- 目标文件
	 * @param x
	 *            --x坐标
	 * @param y
	 *            --y坐标
	 */
	public final static void pressImage(String pressImg, String targetImg, int x, int y) {
		try {
			// 目标文件
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao,
					null);
			// 水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据大图生成小图,暂时只支持RGB
	 * 
	 * @param new_w
	 *            宽度
	 * @param new_h
	 *            高度
	 * @param file
	 *            大图
	 * @param Path_new
	 *            输出路径
	 * @throws Exception
	 */
	private static void alterSize(int new_w, int new_h, File file, String Path_new) throws Exception {
		if (!file.exists()) {
			throw new Exception("big pic not exists");
		}
		if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("jpg") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") == -1) {
			throw new Exception("Pic is not the right type");
		}

		Image src = null;
		try {
			ImgFileUtils.makeDirectory(Path_new);
			java.awt.image.BufferedImage tag = new java.awt.image.BufferedImage(new_w, new_h,
					java.awt.image.BufferedImage.TYPE_INT_RGB);

			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				SeekableStream s = new FileSeekableStream(file);

				TIFFDecodeParam tifParam = new TIFFDecodeParam();

				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, tifParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				src = (Image) rImage;
			} else {
				src = javax.imageio.ImageIO.read(file);
			}
			tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null);
			FileOutputStream newimage = new FileOutputStream(Path_new);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			// 近JPEG编码
			encoder.encode(tag);
			newimage.close();
		} catch (IOException ee) {
			throw ee;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取图片对象
	 * 
	 * @param file_path
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage getGraphics(String file_path, int siteId, SysConfigService sysConfigService)
			throws Exception {
		File file = new File(file_path);
		if (!file.exists()) {
			throw new Exception("big pic not exists:" + file_path);
		}
		if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("jpg") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("png") == -1
				&& file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") == -1) {
			throw new Exception("Pic is not the right type");
		}

		BufferedImage src = null;
		try {
			if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("tif") != -1) {
				SeekableStream s = new FileSeekableStream(file);

				TIFFDecodeParam tifParam = new TIFFDecodeParam();

				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, tifParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				File tempFile = new File(sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId)
						+ File.separator + new Date().getTime() + ".jpg");
				ImageIO.write(rImage, "jpg", tempFile);
				src = javax.imageio.ImageIO.read(tempFile);
				tempFile.deleteOnExit();
			} else if (file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase().indexOf("png") != -1) {
				SeekableStream s = new FileSeekableStream(file);

				PNGDecodeParam pngParam = new PNGDecodeParam();

				ImageDecoder dec = PNGCodec.createImageDecoder("png", s, pngParam);
				RenderedImage rImage = dec.decodeAsRenderedImage();

				File tempFile = new File(sysConfigService.getDbSysConfig(SysConfigConstant.TEMP_PIC_PATH, siteId)
						+ File.separator + new Date().getTime() + ".jpg");
				ImageIO.write(rImage, "png", tempFile);
				src = javax.imageio.ImageIO.read(tempFile);
				tempFile.deleteOnExit();
			} else {
				src = javax.imageio.ImageIO.read(file);
			}
			return src;
		} catch (IOException ee) {
			throw ee;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取图片类型
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 *             add by dulei 20120717
	 */
	public static String getImageType(String filePath) {
		File file = new File(filePath);
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext())
				return null;
			ImageReader reader = iter.next();
			return reader.getFormatName();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取水印图片坐标
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 *             add by dulei 20120717
	 */
	public static int[] getWatermarkPosition(int b_width, int b_height, int w_width, int w_height, int position) {
		int[] xy = new int[2];
		switch (position) {
		case 1:
			xy[0] = 0;
			xy[1] = 0;
			break;
		case 2:
			xy[0] = b_width / 2 - w_width / 2;
			xy[1] = 0;
			break;
		case 3:
			xy[0] = b_width - w_width;
			xy[1] = 0;
			break;
		case 4:
			xy[0] = 0;
			xy[1] = b_height / 2 - w_height / 2;
			break;
		case 5:
			xy[0] = b_width / 2 - w_width / 2;
			xy[1] = b_height / 2 - w_height / 2;
			break;
		case 6:
			xy[0] = b_width - w_width;
			xy[1] = b_height / 2 - w_height / 2;
			break;
		case 7:
			xy[0] = 0;
			xy[1] = b_height - w_height;
			break;
		case 8:
			xy[0] = b_width / 2 - w_width / 2;
			xy[1] = b_height - w_height;
			break;
		case 9:
			xy[0] = b_width - w_width;
			xy[1] = b_height - w_height;
			break;
		}
		return xy;
	}

	/**
	 * 处理下载的图片，包括改变大小，计算水印的位置坐标
	 * 
	 * @throws Exception
	 */
	public static BufferedImage handlePic(BufferedImage basepicture, BufferedImage waterMark, HttpServletRequest req,
			HttpServletResponse res, SysConfigService sysConfigService) throws Exception {
		int siteId = SessionUtils.getSiteId(req);
		String waterPosition = sysConfigService.getDbSysConfig("ORIGINAL_PIC_PATH", siteId);
		String picSize = req.getParameter("picSize");
		int picRealSize = Integer.parseInt(ImageConfig.getPicSizeOnServer(picSize, siteId, sysConfigService));
		int posW = 0;
		int posH = 0;
		int bigPicWidth = basepicture.getWidth();
		int bigPicHeight = basepicture.getHeight();
		int waterPicWidth = waterMark.getWidth();
		int waterPicHeight = waterMark.getHeight();
		// 根据原先图片的长宽比计算出新图片的长宽比
		float rate;
		if (bigPicWidth >= bigPicHeight) {
			rate = (float) bigPicHeight / (float) bigPicWidth;
			bigPicWidth = picRealSize;
			bigPicHeight = (int) (bigPicWidth * rate);
		} else {
			rate = (float) bigPicWidth / (float) bigPicHeight;
			bigPicHeight = picRealSize;
			bigPicWidth = (int) (bigPicHeight * rate);
		}

		// 三种不同类型的大小格式
		if ("1000".equalsIgnoreCase(picSize)) {
			basepicture = ImageAnalyseUtil.alterSize(basepicture, bigPicWidth, bigPicHeight);
		}
		if ("2000".equalsIgnoreCase(picSize)) {
			basepicture = ImageAnalyseUtil.alterSize(basepicture, bigPicWidth, bigPicHeight);
		}
		if ("3000".equalsIgnoreCase(picSize)) {
			basepicture = ImageAnalyseUtil.alterSize(basepicture, bigPicWidth, bigPicHeight);
		}
		// 根据水印配置计算坐标 9个位置
		if ("1".equalsIgnoreCase(waterPosition)) {
			posW = 0;
			posH = 0;
		}
		if ("2".equalsIgnoreCase(waterPosition)) {
			posW = basepicture.getWidth() / 2 - waterPicWidth / 2;
			posH = 0;
		}
		if ("3".equalsIgnoreCase(waterPosition)) {
			posW = basepicture.getWidth() - waterPicWidth;
			posH = 0;
		}
		if ("4".equalsIgnoreCase(waterPosition)) {
			posW = 0;
			posH = basepicture.getHeight() / 2 - waterPicHeight / 2;
		}
		if ("5".equalsIgnoreCase(waterPosition)) {
			posW = basepicture.getWidth() / 2 - waterPicWidth / 2;
			posH = basepicture.getHeight() / 2 - waterPicHeight / 2;
		}
		if ("6".equalsIgnoreCase(waterPosition)) {
			posW = basepicture.getWidth() - waterPicWidth;
			posH = basepicture.getHeight() / 2 - waterPicHeight / 2;
		}
		if ("7".equalsIgnoreCase(waterPosition)) {
			posW = 0;
			posH = basepicture.getHeight() - waterPicHeight;
		}
		if ("8".equalsIgnoreCase(waterPosition)) {
			posW = basepicture.getWidth() / 2 - waterPicWidth / 2;
			posH = basepicture.getHeight() - waterPicHeight;
		}
		if ("9".equalsIgnoreCase(waterPosition)) {
			posW = basepicture.getWidth() - waterPicWidth;
			posH = basepicture.getHeight() - waterPicHeight;
		}
		return basepicture;

	}

	/**
	 * 为图片添加水印
	 * 
	 * @param basePicture
	 *            图片
	 * @param waterMark
	 *            水印标记图
	 * @param position_x
	 *            x坐标
	 * @param position_y
	 *            y坐标
	 * @param DarkPercent
	 *            水印深度百分比值 0-100
	 * @param waterPicType
	 *            水印图片类型(暂时支持jpg和png)其他类型需求时扩展
	 * @return 打好水印的图片 modify by dulei 20120717
	 */
	public static BufferedImage markPicture(BufferedImage basePicture, BufferedImage waterMark, int position_x,
			int position_y, int DarkPercent, boolean ignoreWhite, String waterPicType) {
		int w_width = waterMark.getWidth();
		int w_height = waterMark.getHeight();
		int[] b_pixels = basePicture.getRGB(position_x, position_y, w_width, w_height, null, 0, w_width);
		int[] w_pixels = waterMark.getRGB(0, 0, w_width, w_height, null, 0, w_width);
		if (DarkPercent > 100 || DarkPercent < 0) {
			DarkPercent = 50;
		}

		try {
			for (int i = 0; i < w_height; i++) {
				try {
					for (int j = 0; j < w_width; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int w_clip = getPixel(w_pixels, w_width, j, i);
						int red = cm.getRed(w_clip);
						int green = cm.getGreen(w_clip);
						int blue = cm.getBlue(w_clip);
						int b_clip = getPixel(b_pixels, w_width, j, i);
						int b_red = cm.getRed(b_clip);
						int b_green = cm.getGreen(b_clip);
						int b_blue = cm.getBlue(b_clip);

						if (waterPicType.equalsIgnoreCase("png")) {
							if (!ignoreWhite || !((w_clip == 16777215 && red == 255 && green == 255 && blue == 255)
									|| (red == 0 && green == 0 && blue == 0))) {
								b_pixels[i * w_width + j] = (red * DarkPercent + b_red * (100 - DarkPercent)) / 100
										* 256 * 256;
								b_pixels[i * w_width + j] += (green * DarkPercent + b_green * (100 - DarkPercent)) / 100
										* 256;
								b_pixels[i * w_width + j] += (blue * DarkPercent + b_blue * (100 - DarkPercent)) / 100;
							}
						} else if (waterPicType.equalsIgnoreCase("jpg")) {
							if (!ignoreWhite || w_clip != -1) {
								b_pixels[i * w_width + j] = (red * DarkPercent + b_red * (100 - DarkPercent)) / 100
										* 256 * 256;
								b_pixels[i * w_width + j] += (green * DarkPercent + b_green * (100 - DarkPercent)) / 100
										* 256;
								b_pixels[i * w_width + j] += (blue * DarkPercent + b_blue * (100 - DarkPercent)) / 100;
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			basePicture.setRGB(position_x, position_y, w_width, w_height, b_pixels, 0, w_width);
			return basePicture;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		return null;
	}

	/**
	 * 为图片添加隐藏水印,保存图片时尽量使用无损格式或无损压缩，否则滤波后的效果不够理想，选择水印位置时，尽量选择黑/白颜色以外的位置，否则补偿点会溢出
	 * ，隐藏效果不够理想
	 * 
	 * @param basePicture
	 *            图片
	 * @param waterMark
	 *            水印标记图
	 * @param position_x
	 *            x坐标
	 * @param position_y
	 *            y坐标
	 * @param DarkPercent
	 *            水印深度百分比值 0-100
	 * @return 打好水印的图片
	 */
	public static BufferedImage markDarkPicture(BufferedImage basePicture, BufferedImage waterMark, int position_x,
			int position_y, int DarkPercent, boolean ignoreWhite) {
		int b_width = basePicture.getWidth();
		int b_height = basePicture.getHeight();
		int w_width = waterMark.getWidth();
		int w_height = waterMark.getHeight();
		int[] b_pixels = basePicture.getRGB(position_x, position_y, w_width, w_height, null, 0, w_width);
		int[] w_pixels = waterMark.getRGB(0, 0, w_width, w_height, null, 0, w_width);
		if (DarkPercent > 100 || DarkPercent < 0) {
			DarkPercent = 15;
		}

		try {
			for (int i = 0; i < w_height; i++) {
				try {
					int last_pixel = getPixel(b_pixels, w_width, 0, i);
					for (int j = 0; j < w_width; j++) {
						if ((i + j + position_x + position_y) % 2 != 0 && j != 0) {
							/*
							 * if(j==0) { last_pixel = getPixel(b_pixels,
							 * w_width, j-1, i); continue; }
							 */
							// 补偿
							ColorModel cm = ColorModel.getRGBdefault();
							int red = cm.getRed(last_pixel);
							int green = cm.getGreen(last_pixel);
							int blue = cm.getBlue(last_pixel);
							int l_clip = getPixel(b_pixels, w_width, j - 1, i);
							int l_red = cm.getRed(l_clip);
							int l_green = cm.getGreen(l_clip);
							int l_blue = cm.getBlue(l_clip);
							int b_clip = getPixel(b_pixels, w_width, j, i);
							int b_red = cm.getRed(b_clip);
							int b_green = cm.getGreen(b_clip);
							int b_blue = cm.getBlue(b_clip);
							last_pixel = b_clip;
							b_pixels[i * w_width + j] = (b_red - l_red + red) > 255 ? 255 * 256 * 256
									: (b_red - l_red + red) * 256 * 256;
							if ((b_red - l_red + red) < 0) {
								b_pixels[i * w_width + j] = 0;
							}
							if ((b_green - l_green + green) >= 0) {
								b_pixels[i * w_width + j] += (b_green - l_green + green) > 255 ? 255 * 256
										: (b_green - l_green + green) * 256;
							}
							if ((b_blue - l_blue + blue) >= 0) {
								b_pixels[i * w_width + j] += (b_blue - l_blue + blue) > 255 ? 255
										: (b_blue - l_blue + blue);
							}
						} else if (j != 0) {
							// 水印
							ColorModel cm = ColorModel.getRGBdefault();
							int w_clip = getPixel(w_pixels, w_width, j, i);
							int red = cm.getRed(w_clip);
							int green = cm.getGreen(w_clip);
							int blue = cm.getBlue(w_clip);
							int b_clip = getPixel(b_pixels, w_width, j, i);
							int b_red = cm.getRed(b_clip);
							int b_green = cm.getGreen(b_clip);
							int b_blue = cm.getBlue(b_clip);
							last_pixel = b_clip;

							if (!ignoreWhite || w_clip != -1) {
								b_pixels[i * w_width + j] = (red * DarkPercent + b_red * (100 - DarkPercent)) / 100
										* 256 * 256;
								b_pixels[i * w_width + j] += (green * DarkPercent + b_green * (100 - DarkPercent)) / 100
										* 256;
								b_pixels[i * w_width + j] += (blue * DarkPercent + b_blue * (100 - DarkPercent)) / 100;
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("ex");
					// 忽略行溢出的点
				}
			}
			basePicture.setRGB(position_x, position_y, w_width, w_height, b_pixels, 0, w_width);
			return basePicture;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		return null;
	}

	private static int getPixel(int[] pixels, int scanLine, int x, int y) {
		if (scanLine < x) {
			throw new IndexOutOfBoundsException("横坐标溢出");
		}
		return pixels[y * scanLine + x];
	}

	/**
	 * 高斯模糊,支持在制定区域打码
	 * 
	 * @param basePicture
	 *            图片
	 * @param position_x
	 *            模糊位置x
	 * @param position_y
	 *            模糊位置y
	 * @param size_x
	 *            模糊区域x
	 * @param size_y
	 *            模糊区域y
	 * @param readSquare
	 *            参考域
	 * @param readCentralization
	 *            参考强度 1-100的数
	 * @return
	 */
	public static BufferedImage gaussBlear(BufferedImage basePicture, int position_x, int position_y, int size_x,
			int size_y, int readSquare, int readCentralization) {
		int b_width = size_x;// basePicture.getWidth();
		int b_height = size_y;// basePicture.getHeight();
		int[] b_pixels = basePicture.getRGB(position_x, position_y, size_x, size_y, null, 0, size_x);
		if (readSquare < 0) {
			readSquare = 1;
		}
		if (readCentralization < 0 || readCentralization > 100) {
			readCentralization = 75;
		}

		for (int i = 0; i < b_height; i++) {
			int a = 1;
			try {
				for (int j = 0; j < b_width; j++) {
					ColorModel cm = ColorModel.getRGBdefault();
					int b_clip = getPixel(b_pixels, b_width, j, i);
					List<Integer> readPixels = new ArrayList<Integer>();
					try {
						// 一圈一圈参考
						for (int k = 1; k <= readSquare; k++) {
							// 每圈参考8个点,有星芒效果,暂时不用
							/*
							 * readPixels.add((int)getPixel(b_pixels, b_width,
							 * j-k, i-k));
							 * readPixels.add((int)getPixel(b_pixels, b_width,
							 * j-k, i)); readPixels.add((int)getPixel(b_pixels,
							 * b_width, j-k, i+k));
							 * readPixels.add((int)getPixel(b_pixels, b_width,
							 * j, i-k)); readPixels.add((int)getPixel(b_pixels,
							 * b_width, j, i+k));
							 * readPixels.add((int)getPixel(b_pixels, b_width,
							 * j+k, i-k));
							 * readPixels.add((int)getPixel(b_pixels, b_width,
							 * j+k, i)); readPixels.add((int)getPixel(b_pixels,
							 * b_width, j+k, i+k));
							 */
							// 每圈参考所有点
							for (int l = -k; l < k; l++) {
								// 参考点在圆内加入参考列表
								if (l * l + k * k <= readSquare * readSquare) {
									// 与高斯模糊的正态权重不同,此函数使用线性权重,可以在一定程度下保留边缘的细节
									int readTimes = readSquare - (int) Math.sqrt((double) (l * l + k * k));
									for (int m = 0; m < readTimes; m++) {
										readPixels.add((int) getPixel(b_pixels, b_width, j + l, i - k));
										readPixels.add((int) getPixel(b_pixels, b_width, j - l, i + k));
										readPixels.add((int) getPixel(b_pixels, b_width, j - k, i + l));
										readPixels.add((int) getPixel(b_pixels, b_width, j + k, i - l));
									}
								}
							}
						}
					} catch (Exception e) {
					}

					// 元颜色
					int b_red = cm.getRed(b_clip);
					int b_green = cm.getGreen(b_clip);
					int b_blue = cm.getBlue(b_clip);

					// 保存参考颜色
					int read_red = 0;
					int read_green = 0;
					int read_blue = 0;

					// 算参考颜色
					for (Integer k : readPixels) {
						read_red += cm.getRed((int) k) > 255 ? 255 : (cm.getRed((int) k) < 0 ? 0 : cm.getRed((int) k));
						read_green += cm.getGreen((int) k) > 255 ? 255
								: (cm.getGreen((int) k) < 0 ? 0 : cm.getGreen((int) k));
						read_blue += cm.getBlue((int) k) > 255 ? 255
								: (cm.getBlue((int) k) < 0 ? 0 : cm.getBlue((int) k));
					}
					if (read_red != 0 && read_green != 0 && read_blue != 0) {
						read_red /= readPixels.size();
						read_green /= readPixels.size();
						read_blue /= readPixels.size();
					}

					// 按照参考强度算值
					b_red = (b_red * readCentralization + read_red * (100 - readCentralization)) / 100;
					if (b_red > 255) {
						b_red = 255;
					}
					if (b_red < 0) {
						b_red = 0;
					}
					b_green = (b_green * readCentralization + read_green * (100 - readCentralization)) / 100;
					if (b_green > 255) {
						b_green = 255;
					}
					if (b_green < 0) {
						b_green = 0;
					}
					b_blue = (b_blue * readCentralization + read_blue * (100 - readCentralization)) / 100;
					if (b_blue > 255) {
						b_blue = 255;
					}
					if (b_blue < 0) {
						b_blue = 0;
					}

					// 合成RGB
					b_pixels[i * b_width + j] = b_red * 256 * 256;
					b_pixels[i * b_width + j] += b_green * 256;
					b_pixels[i * b_width + j] += b_blue;

				}
			} catch (Exception e) {
				// 忽略行溢出的点
			}
		}
		basePicture.setRGB(position_x, position_y, size_x, size_y, b_pixels, 0, size_x);
		return basePicture;

	}

	/**
	 * 亮度
	 * 
	 * @param basePicture
	 * @param position_x
	 * @param position_y
	 * @param size_x
	 * @param size_y
	 * @param range
	 *            调整范围 -200 至 200
	 * @return
	 */
	public static BufferedImage modifySight(BufferedImage basePicture, int position_x, int position_y, int size_x,
			int size_y, int range) {
		int b_width = basePicture.getWidth();
		int b_height = basePicture.getHeight();
		int[] b_pixels = basePicture.getRGB(position_x, position_y, size_x, size_y, null, 0, size_x);
		if (range > 200 || range < -200) {
			range = 50;
		}

		try {
			for (int i = 0; i < size_y; i++) {
				try {
					for (int j = 0; j < size_x; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int w_clip = getPixel(b_pixels, size_x, j, i);
						int red = cm.getRed(w_clip);
						int green = cm.getGreen(w_clip);
						int blue = cm.getBlue(w_clip);

						int new_red = (red + 256 * range / 200) > 255 ? 255
								: ((red + 256 * range / 200) < 0 ? 0 : (red + 256 * range / 200));
						int new_green = (green + 256 * range / 200) > 255 ? 255
								: ((green + 256 * range / 200) < 0 ? 0 : (green + 256 * range / 200));
						int new_blue = (blue + 256 * range / 200) > 255 ? 255
								: ((blue + 256 * range / 200) < 0 ? 0 : (blue + 256 * range / 200));

						b_pixels[i * size_x + j] = new_red * 256 * 256;
						b_pixels[i * size_x + j] += new_green * 256;
						b_pixels[i * size_x + j] += new_blue;

					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			basePicture.setRGB(position_x, position_y, size_x, size_y, b_pixels, 0, size_x);
			return basePicture;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		return basePicture;
	}

	/**
	 * 对比度
	 * 
	 * @param basePicture
	 * @param position_x
	 * @param position_y
	 * @param size_x
	 * @param size_y
	 * @return
	 */
	public static BufferedImage modifyComparison(BufferedImage basePicture, int position_x, int position_y, int size_x,
			int size_y, int range) {
		int b_width = basePicture.getWidth();
		int b_height = basePicture.getHeight();
		int[] b_pixels = basePicture.getRGB(position_x, position_y, size_x, size_y, null, 0, size_x);
		if (range > 200 || range < -200) {
			range = 50;
		}

		try {
			for (int i = 0; i < size_y; i++) {
				try {
					for (int j = 0; j < size_x; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int w_clip = getPixel(b_pixels, size_x, j, i);
						int red = cm.getRed(w_clip);
						int green = cm.getGreen(w_clip);
						int blue = cm.getBlue(w_clip);

						int new_red = (red + 256 * range / 200) > 255 ? 255
								: ((red + 256 * range / 200) < 127 ? 127 : (red + 256 * range / 200));
						if (red < 128) {
							new_red = (red - 256 * range / 200) < 0 ? 0
									: ((red - 256 * range / 200) > 128 ? 128 : (red - 256 * range / 200));
						}
						int new_green = (green + 256 * range / 200) > 255 ? 255
								: ((green + 256 * range / 200) < 127 ? 127 : (green + 256 * range / 200));
						if (green < 128) {
							new_green = (green - 256 * range / 200) < 0 ? 0
									: ((green - 256 * range / 200) > 128 ? 128 : (green - 256 * range / 200));
						}
						int new_blue = (blue + 256 * range / 200) > 255 ? 255
								: ((blue + 256 * range / 200) < 127 ? 127 : (blue + 256 * range / 200));
						if (blue < 128) {
							new_blue = (blue - 256 * range / 200) < 0 ? 0
									: ((blue - 256 * range / 200) > 128 ? 128 : (blue - 256 * range / 200));
						}

						b_pixels[i * size_x + j] = new_red * 256 * 256;
						b_pixels[i * size_x + j] += new_green * 256;
						b_pixels[i * size_x + j] += new_blue;

					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			basePicture.setRGB(position_x, position_y, size_x, size_y, b_pixels, 0, size_x);
			return basePicture;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		return basePicture;
	}

	/**
	 * 饱和度
	 * 
	 * @param basePicture
	 * @param position_x
	 * @param position_y
	 * @param size_x
	 * @param size_y
	 * @return
	 */
	public static BufferedImage modifySaturation(BufferedImage basePicture, int position_x, int position_y, int size_x,
			int size_y, int range) {
		int b_width = basePicture.getWidth();
		int b_height = basePicture.getHeight();
		int[] b_pixels = basePicture.getRGB(position_x, position_y, size_x, size_y, null, 0, size_x);
		if (range > 200 || range < -200) {
			range = 50;
		}

		try {
			for (int i = 0; i < size_y; i++) {
				try {
					for (int j = 0; j < size_x; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int w_clip = getPixel(b_pixels, size_x, j, i);
						int red = cm.getRed(w_clip);
						int green = cm.getGreen(w_clip);
						int blue = cm.getBlue(w_clip);

						int new_red = red;
						int new_green = green;
						int new_blue = blue;

						if (amITheBiggest(red, green, blue)) {
							new_red = (red + 256 * range / 200) > 255 ? 255
									: ((red + 256 * range / 200) < (green > blue ? green : blue)
											? (green > blue ? green : blue) : (red + 256 * range / 200));
							if (green < blue) {
								new_green = (green - 256 * range / 200) > blue ? blue
										: ((green - 256 * range / 200) < 0 ? 0 : (green - 256 * range / 200));
							} else {
								new_blue = (blue - 256 * range / 200) > green ? green
										: ((blue - 256 * range / 200) < 0 ? 0 : (blue - 256 * range / 200));
							}
						}
						if (amITheBiggest(green, red, blue)) {
							new_green = (green + 256 * range / 200) > 255 ? 255
									: ((green + 256 * range / 200) < (red > blue ? red : blue)
											? (red > blue ? red : blue) : (green + 256 * range / 200));
							if (red < blue) {
								new_red = (red - 256 * range / 200) > blue ? blue
										: ((red - 256 * range / 200) < 0 ? 0 : (red - 256 * range / 200));
							} else {
								new_blue = (blue - 256 * range / 200) > red ? red
										: ((blue - 256 * range / 200) < 0 ? 0 : (blue - 256 * range / 200));
							}
						}
						if (amITheBiggest(blue, red, green)) {
							new_blue = (blue + 256 * range / 200) > 255 ? 255
									: ((blue + 256 * range / 200) < (red > green ? red : green)
											? (red > green ? red : green) : (blue + 256 * range / 200));
							if (red < green) {
								new_red = (red - 256 * range / 200) > green ? green
										: ((red - 256 * range / 200) < 0 ? 0 : (red - 256 * range / 200));
							} else {
								new_green = (green - 256 * range / 200) > red ? red
										: ((green - 256 * range / 200) < 0 ? 0 : (green - 256 * range / 200));
							}
						}

						b_pixels[i * size_x + j] = new_red * 256 * 256;
						b_pixels[i * size_x + j] += new_green * 256;
						b_pixels[i * size_x + j] += new_blue;

					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			basePicture.setRGB(position_x, position_y, size_x, size_y, b_pixels, 0, size_x);
			return basePicture;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		return basePicture;
	}

	private static boolean amITheBiggest(int I, int NPC1, int NPC2) {
		return I >= NPC1 && I >= NPC2;
	}

	/**
	 * 改变图片大小
	 * 
	 * @param source
	 * @param new_w
	 * @param new_h
	 * @return
	 */
	public static BufferedImage alterSize(BufferedImage source, int new_w, int new_h) {
		BufferedImage new_pic = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		// int new_source_w=new_w;
		// int new_source_h=new_h;
		if (new_w > source_w || new_h > source_h) {
			return source;
		}
		int[] newPicPixels = new_pic.getRGB(0, 0, new_w, new_h, null, 0, new_w);
		// int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h,
		// null, 0, source_w);
		// 获得起始扫描的宽度索引
		/*
		 * int start_source_w_index=0; //获得起始扫描的高度索引 int start_source_h_index=0;
		 * int min_source_h;//最小的原始图片高度 int min_source_w;//最小的原始图片宽度
		 * //根据已知的图片高宽，确定有多少 int ws=source_w/100; int hs=source_h/100; int
		 * n=ws>hs?hs:ws; //如果图片高宽之一小于100，则直接处理 min_source_h= n==0?source_h:100;
		 * min_source_w= n==0?source_w:100; //new_w=new_w>100?100:new_w;
		 * //new_h=new_h>100?100:new_h; int m=0; //构建临时的temp的二维RGB图片数组 int[]
		 * tempSourcePicPixelds=source.getRGB(0, 0, min_source_w, min_source_h,
		 * null, 0, min_source_w);
		 * 
		 */
		try {
			/*
			 * for(int i = 0;i<new_h;i++) { try { for (int j = 0; j < new_w;
			 * j++) { ColorModel cm = ColorModel.getRGBdefault();
			 * 
			 * int new_red=0,new_green=0,new_blue=0,pixelCount=0;
			 * 
			 * for(int k=source_w*j/new_w;k<source_w*(j+1)/new_w;k++) { for(int
			 * l=source_h*i/new_h;l<source_h*(i+1)/new_h;l++) { int temp_Pixel =
			 * getPixel(sourcePicPixelds, source_w, k, l);
			 * new_red+=cm.getRed(temp_Pixel);
			 * new_green+=cm.getGreen(temp_Pixel);
			 * new_blue+=cm.getBlue(temp_Pixel); pixelCount++; } }
			 * 
			 * new_red /= pixelCount; new_green /= pixelCount; new_blue /=
			 * pixelCount;
			 * 
			 * 
			 * newPicPixels[i*new_w+j] = new_red*256*256;
			 * newPicPixels[i*new_w+j] += new_green*256; newPicPixels[i*new_w+j]
			 * += new_blue;
			 * 
			 * } } catch (IndexOutOfBoundsException e) { // 忽略行溢出的点 } }
			 */
			for (int i = 0; i < new_h; i++) {
				try {
					for (int j = 0; j < new_w; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int new_red = 0, new_green = 0, new_blue = 0, pixelCount = 0;
						for (int k = source_w * j / new_w; k < source_w * (j + 1) / new_w; k++) {
							for (int l = source_h * i / new_h; l < source_h * (i + 1) / new_h; l++) {
								// int temp_Pixel = getPixel(sourcePicPixelds,
								// source_w, k, l);
								int temp_Pixel = source.getRGB(k, l);
								/*
								 * if(temp_Pixel!=temp_Pixel_1){
								 * System.out.println("source:"+temp_Pixel+
								 * " 	new:"+temp_Pixel_1); }
								 */
								new_red += cm.getRed(temp_Pixel);
								new_green += cm.getGreen(temp_Pixel);
								new_blue += cm.getBlue(temp_Pixel);
								pixelCount++;
							}
						}

						new_red /= pixelCount;
						new_green /= pixelCount;
						new_blue /= pixelCount;

						newPicPixels[i * new_w + j] = new_red * 256 * 256;
						newPicPixels[i * new_w + j] += new_green * 256;
						newPicPixels[i * new_w + j] += new_blue;

					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			/*
			 * for(int i = 0;i<new_h;i++) { try { for (int j = 0; j < new_w;
			 * j++) { ColorModel cm = ColorModel.getRGBdefault(); int
			 * new_red=0,new_green=0,new_blue=0,pixelCount=0;
			 * 
			 * do{ if(m==n-1){ min_source_h=source_h-start_source_h_index;
			 * min_source_w=source_w-start_source_w_index;
			 * tempSourcePicPixelds=source.getRGB(start_source_w_index,
			 * start_source_h_index, source_w-start_source_w_index,
			 * source_h-start_source_h_index, null, 0,
			 * source_w-start_source_w_index); }else{ start_source_w_index+=100;
			 * start_source_h_index+=100; }
			 * 
			 * for(int k=min_source_w*j/new_w;k<min_source_w*(j+1)/new_w;k++) {
			 * for(int l=min_source_h*i/new_h;l<min_source_h*(i+1)/new_h;l++) {
			 * int temp_Pixel = getPixel(tempSourcePicPixelds, min_source_w, k,
			 * l); new_red+=cm.getRed(temp_Pixel);
			 * new_green+=cm.getGreen(temp_Pixel);
			 * new_blue+=cm.getBlue(temp_Pixel); pixelCount++; } }
			 * if(pixelCount!=0){ new_red /= pixelCount; new_green /=
			 * pixelCount; new_blue /= pixelCount; newPicPixels[i*new_w+j] =
			 * new_red*256*256; newPicPixels[i*new_w+j] += new_green*256;
			 * newPicPixels[i*new_w+j] += new_blue; }
			 * 
			 * 
			 * 
			 * m++; //更改临时RGB里的数据
			 * tempSourcePicPixelds=source.getRGB(start_source_w_index,
			 * start_source_h_index, min_source_w, min_source_h, null, 0,
			 * min_source_w); }while(m<=n-1); m=0; }
			 * 
			 * } catch (IndexOutOfBoundsException e) { // 忽略行溢出的点 }
			 * 
			 * 
			 * }
			 */
			new_pic.setRGB(0, 0, new_w, new_h, newPicPixels, 0, new_w);
			return new_pic;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		return null;
	}

	/**
	 * 获取图片的主色调
	 * 
	 * @param source
	 * @return
	 */
	public static Color getImageMainColor(BufferedImage source) {
		int source_w = source.getWidth();// 获得像素宽
		int source_h = source.getHeight();// 获得像素高
		int min_source_w = 100;// 创建最小的source_w
		int min_source_h = 100;// 创建最小的source_h
		// min_source_w=min_source_w<source_w?min_source_w:source_w;
		// min_source_h=min_source_h<source_h?min_source_h:source_h;
		// 构建整个图片的像素的二维数组，往往会出现内存溢出，比如一个八百万像素的图片3264*2488就会堆栈异常。
		// int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h,
		// null, 0, source_w);
		// 7 color types,red,orange,yellow,green,cyan,blue,purple;
		int[] colorlist = new int[Color.values().length];
		// 获得起始扫描的宽度索引
		int start_source_w_index = 0;
		// 获得起始扫描的高度索引
		int start_source_h_index = 0;
		// 根据已知的图片高宽，确定有多少
		int ws = source_w / 100;
		int hs = source_h / 100;
		int n = ws > hs ? hs : ws;
		// 如果图片高宽之一小于100，则直接处理
		min_source_h = n == 0 ? source_h : 100;
		min_source_w = n == 0 ? source_w : 100;
		int k = 0;
		// 构建临时的temp的二维RGB图片数组
		int[] tempSourcePicPixelds = source.getRGB(0, 0, min_source_w, min_source_h, null, 0, min_source_w);
		try {
			do {
				// 当运行到最后一次时，将剩余的像素点全部放入临时数组中
				if (k == n - 1) {
					min_source_h = source_h - start_source_h_index;
					min_source_w = source_w - start_source_w_index;
					tempSourcePicPixelds = source.getRGB(start_source_w_index, start_source_h_index,
							source_w - start_source_w_index, source_h - start_source_h_index, null, 0,
							source_w - start_source_w_index);
				} else {
					start_source_w_index += 100;
					start_source_h_index += 100;
				}
				for (int i = 0; i < min_source_h; i++) {
					try {
						for (int j = 0; j < min_source_w; j++) {
							Color clr = getColorType(getPixel(tempSourcePicPixelds, min_source_w, j, i));
							if (clr == Color.red) {
								colorlist[0]++;
							} else if (clr == Color.orange) {
								colorlist[1]++;
							} else if (clr == Color.yellow) {
								colorlist[2]++;
							} else if (clr == Color.green) {
								colorlist[3]++;
							} else if (clr == Color.cyan) {
								colorlist[4]++;
							} else if (clr == Color.blue) {
								colorlist[5]++;
							} else if (clr == Color.purple) {
								colorlist[6]++;
							} else if (clr == Color.white) {
								colorlist[7]++;
							} else if (clr == Color.black) {
								colorlist[8]++;
							}
						}
					} catch (IndexOutOfBoundsException e) {
						// 忽略行溢出的点
					}
				}

				k++;
				// 更改临时RGB里的数据
				tempSourcePicPixelds = source.getRGB(start_source_w_index, start_source_h_index, min_source_w,
						min_source_h, null, 0, min_source_w);
			} while (k <= n - 1);
			/*
			 * for(int i = 0;i<source_h;i++) { try { for (int j = 0; j <
			 * source_w; j++) { Color clr =
			 * getColorType(getPixel(sourcePicPixelds, source_w, j, i)); if(clr
			 * == Color.red) { colorlist[0]++; } else if(clr == Color.orange) {
			 * colorlist[1]++; } else if(clr == Color.yellow) { colorlist[2]++;
			 * } else if(clr == Color.green) { colorlist[3]++; } else if(clr ==
			 * Color.cyan) { colorlist[4]++; } else if(clr == Color.blue) {
			 * colorlist[5]++; } else if(clr == Color.purple) { colorlist[6]++;
			 * } else if(clr == Color.white) { colorlist[7]++; } else if(clr ==
			 * Color.black) { colorlist[8]++; } } } catch
			 * (IndexOutOfBoundsException e) { // 忽略行溢出的点 } }
			 */
			tempSourcePicPixelds = null;
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		int max = 0;
		int index = 0;
		Color colour = Color.red;
		for (int i = 0; i < colorlist.length; i++) {
			if (colorlist[i] > max) {
				max = colorlist[i];
				index = i;
				if (Color.red.number() == i) {
					colour = Color.red;
				} else if (Color.orange.number() == i) {
					colour = Color.orange;
				} else if (Color.yellow.number() == i) {
					colour = Color.yellow;
				} else if (Color.green.number() == i) {
					colour = Color.green;
				} else if (Color.cyan.number() == i) {
					colour = Color.cyan;
				} else if (Color.blue.number() == i) {
					colour = Color.blue;
				} else if (Color.purple.number() == i) {
					colour = Color.purple;
				} else if (Color.white.number() == i) {
					colour = Color.white;
				} else if (Color.black.number() == i) {
					colour = Color.black;
				}
			}
		}
		return colour;
	}

	/**
	 * 反转颜色,底片效果
	 * 
	 * @param source
	 * @return
	 */
	public static BufferedImage reverseImageColor(BufferedImage source) {
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h, null, 0, source_w);
		try {
			for (int i = 0; i < source_h; i++) {
				try {
					for (int j = 0; j < source_w; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int pixel = getPixel(sourcePicPixelds, source_w, j, i);
						int red = cm.getRed(pixel);
						int green = cm.getGreen(pixel);
						int blue = cm.getBlue(pixel);

						red = 255 - red;
						green = 255 - green;
						blue = 255 - blue;

						sourcePicPixelds[i * source_w + j] = red * 256 * 256;
						sourcePicPixelds[i * source_w + j] += green * 256;
						sourcePicPixelds[i * source_w + j] += blue;

					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		source.setRGB(0, 0, source_w, source_h, sourcePicPixelds, 0, source_w);
		return source;
	}

	/**
	 * 翻转图片
	 * 
	 * @param source
	 * @return
	 */
	public static BufferedImage reverseImage(BufferedImage source, boolean x, boolean y) {
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h, null, 0, source_w);
		try {
			if (x) {
				for (int i = 0; i < source_h; i++) {
					try {
						for (int j = 0; j < source_w / 2; j++) {
							ColorModel cm = ColorModel.getRGBdefault();
							int pixel = getPixel(sourcePicPixelds, source_w, j, i);
							sourcePicPixelds[i * source_w + j] = sourcePicPixelds[i * source_w + source_w - j];
							sourcePicPixelds[i * source_w + source_w - j] = pixel;
						}
					} catch (IndexOutOfBoundsException e) {
						// 忽略行溢出的点
					}
				}
			}
			if (y) {
				for (int i = 0; i < source_h / 2; i++) {
					try {
						for (int j = 0; j < source_w; j++) {
							ColorModel cm = ColorModel.getRGBdefault();
							int pixel = getPixel(sourcePicPixelds, source_w, j, i);
							sourcePicPixelds[i * source_w + j] = sourcePicPixelds[(source_h - i) * source_w + j];
							sourcePicPixelds[(source_h - i) * source_w + j] = pixel;
						}
					} catch (IndexOutOfBoundsException e) {
						// 忽略行溢出的点
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		source.setRGB(0, 0, source_w, source_h, sourcePicPixelds, 0, source_w);
		return source;
	}

	/**
	 * 浮雕效果
	 * 
	 * @param source
	 * @param readpixel
	 *            参考像素数,可以理解为刻刀的粗细,值越大,刻刀越粗
	 * @param flagarea
	 *            浮雕阈值,可以理解为雕刻的深浅,一般在1-60之间,值越大雕刻的越写意
	 * @param isNegative
	 *            如果此值为真,浮雕为阴雕,否则为阳雕
	 * @return
	 */
	public static BufferedImage carveImage(BufferedImage source, int readpixel, int flagarea, boolean isNegative) {
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		// 参考垂直点数
		int readpixels = readpixel;
		// 浮雕阈值
		int flagArea = flagarea;
		int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h, null, 0, source_w);
		int[] new_sourcePicPixelds = new int[sourcePicPixelds.length];
		try {
			for (int i = 0; i < source_h; i++) {
				try {
					for (int j = 0; j < source_w; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int readUp_red = 0;
						int readUp_green = 0;
						int readUp_blue = 0;
						int readDown_red = 0;
						int readDown_green = 0;
						int readDown_blue = 0;
						int pixel = getPixel(sourcePicPixelds, source_w, j, i);

						for (int k = 0; k < readpixels; k++) {
							int readpixelup = getPixel(sourcePicPixelds, source_w, j, i - k);
							int readpixeldown = getPixel(sourcePicPixelds, source_w, j, i + k);
							readUp_red += cm.getRed(readpixelup);
							readUp_green += cm.getGreen(readpixelup);
							readUp_blue += cm.getBlue(readpixelup);
							readDown_red += cm.getRed(readpixeldown);
							readDown_green += cm.getGreen(readpixeldown);
							readDown_blue += cm.getBlue(readpixeldown);
						}
						readUp_red /= readpixels;
						readUp_green /= readpixels;
						readUp_blue /= readpixels;
						readDown_red /= readpixels;
						readDown_green /= readpixels;
						readDown_blue /= readpixels;

						int readUp = readUp_red * 256 * 256 + readUp_green * 256 + readUp_blue;
						int readDown = readDown_red * 256 * 256 + readDown_green * 256 + readDown_blue;

						double distance = getLinearDistance(readUp, readDown);
						int light = readUp_red + readUp_green + readUp_blue
								- (readDown_red + readDown_green + readDown_blue);
						if (light > flagArea) {
							if (!isNegative) {
								new_sourcePicPixelds[i * source_w + j] = 20 * 256 * 256;
								new_sourcePicPixelds[i * source_w + j] += 20 * 256;
								new_sourcePicPixelds[i * source_w + j] += 20;
							} else {
								new_sourcePicPixelds[i * source_w + j] = 255 * 256 * 256;
								new_sourcePicPixelds[i * source_w + j] += 255 * 256;
								new_sourcePicPixelds[i * source_w + j] += 255;
							}
						} else if (light < -flagArea) {
							if (isNegative) {
								new_sourcePicPixelds[i * source_w + j] = 20 * 256 * 256;
								new_sourcePicPixelds[i * source_w + j] += 20 * 256;
								new_sourcePicPixelds[i * source_w + j] += 20;
							} else {
								new_sourcePicPixelds[i * source_w + j] = 255 * 256 * 256;
								new_sourcePicPixelds[i * source_w + j] += 255 * 256;
								new_sourcePicPixelds[i * source_w + j] += 255;
							}
						} else {
							new_sourcePicPixelds[i * source_w + j] = 128 * 256 * 256;
							new_sourcePicPixelds[i * source_w + j] += 128 * 256;
							new_sourcePicPixelds[i * source_w + j] += 128;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		source = new BufferedImage(source_w, source_h, BufferedImage.TYPE_INT_RGB);
		source.setRGB(0, 0, source_w, source_h, new_sourcePicPixelds, 0, source_w);
		return source;
	}

	/**
	 * 水墨画效果
	 * 
	 * @param source
	 * @param readpixel
	 * @param flagarea
	 * @param isNegative
	 * @return
	 */
	public static BufferedImage cartoonImage(BufferedImage source, int readpixel, int flagarea, int borderPixel) {
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		// 参考垂直点数
		int readpixels = readpixel;
		// 阈值
		int flagArea = flagarea;
		int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h, null, 0, source_w);
		int[] new_sourcePicPixelds = new int[sourcePicPixelds.length];
		try {
			for (int i = 0; i < source_h; i++) {
				try {
					for (int j = 0; j < source_w; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						int readUp_red = 0;
						int readUp_green = 0;
						int readUp_blue = 0;
						int readDown_red = 0;
						int readDown_green = 0;
						int readDown_blue = 0;
						int readLeft_red = 0;
						int readLeft_green = 0;
						int readLeft_blue = 0;
						int readRight_red = 0;
						int readRight_green = 0;
						int readRight_blue = 0;
						int pixel = getPixel(sourcePicPixelds, source_w, j, i);

						for (int k = 0; k < readpixels; k++) {
							int readpixelup = getPixel(sourcePicPixelds, source_w, j, i - k);
							int readpixeldown = getPixel(sourcePicPixelds, source_w, j, i + k);
							readUp_red += cm.getRed(readpixelup);
							readUp_green += cm.getGreen(readpixelup);
							readUp_blue += cm.getBlue(readpixelup);
							readDown_red += cm.getRed(readpixeldown);
							readDown_green += cm.getGreen(readpixeldown);
							readDown_blue += cm.getBlue(readpixeldown);
						}
						readUp_red /= readpixels;
						readUp_green /= readpixels;
						readUp_blue /= readpixels;
						readDown_red /= readpixels;
						readDown_green /= readpixels;
						readDown_blue /= readpixels;

						int readUp = readUp_red * 256 * 256 + readUp_green * 256 + readUp_blue;
						int readDown = readDown_red * 256 * 256 + readDown_green * 256 + readDown_blue;

						double distance = getLinearDistance(readUp, readDown);
						int light = readUp_red + readUp_green + readUp_blue
								- (readDown_red + readDown_green + readDown_blue);

						for (int k = 0; k < readpixels; k++) {
							int readpixelup = getPixel(sourcePicPixelds, source_w, j, i - k);
							int readpixeldown = getPixel(sourcePicPixelds, source_w, j, i + k);
							readLeft_red += cm.getRed(readpixelup);
							readLeft_green += cm.getGreen(readpixelup);
							readLeft_blue += cm.getBlue(readpixelup);
							readRight_red += cm.getRed(readpixeldown);
							readRight_green += cm.getGreen(readpixeldown);
							readRight_blue += cm.getBlue(readpixeldown);
						}
						readLeft_red /= readpixels;
						readLeft_green /= readpixels;
						readLeft_blue /= readpixels;
						readRight_red /= readpixels;
						readRight_green /= readpixels;
						readRight_blue /= readpixels;

						int readLeft = readLeft_red * 256 * 256 + readLeft_green * 256 + readLeft_blue;
						int readRight = readRight_red * 256 * 256 + readRight_green * 256 + readRight_blue;

						distance = getLinearDistance(readLeft, readRight);
						int light2 = readLeft_red + readLeft_green + readLeft_blue
								- (readRight_red + readRight_green + readRight_blue);
						// 判断边缘
						if ((light > flagArea || light < -flagArea) || (light2 > flagArea || light2 < -flagArea)) {
							for (int l = 0; l < borderPixel; l++) {
								if (light > flagArea || light < -flagArea) {
									new_sourcePicPixelds[(i - l) * source_w + j] = 0 * 256 * 256;
									new_sourcePicPixelds[(i - l) * source_w + j] += 0 * 256;
									new_sourcePicPixelds[(i - l) * source_w + j] += 0;
								}
								if (light2 > flagArea || light2 < -flagArea) {
									new_sourcePicPixelds[i * source_w + j + l] = 0 * 256 * 256;
									new_sourcePicPixelds[i * source_w + j + l] += 0 * 256;
									new_sourcePicPixelds[i * source_w + j + l] += 0;
								}
							}
						} else {
							Color color = getColorType(pixel);
							new_sourcePicPixelds[i * source_w + j] = color.colorValue;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		source = new BufferedImage(source_w, source_h, BufferedImage.TYPE_INT_RGB);
		source.setRGB(0, 0, source_w, source_h, new_sourcePicPixelds, 0, source_w);
		return source;
	}

	/**
	 * 保存图片
	 * 
	 * @param source
	 * @param destPath
	 * @throws Exception
	 */
	public static void saveJPG(BufferedImage source, String destPath) throws Exception {
		if (source != null) {
			ImgFileUtils.makeDirectory(destPath);
			FileOutputStream fout = new FileOutputStream(destPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fout);
			encoder.encode(source);
			fout.flush();
			fout.close();
			source = null;
		}
	}

	/**
	 * 保存图片
	 * 
	 * @param source
	 * @param destPath
	 * @throws Exception
	 */
	public static void savePNG(BufferedImage source, String destPath) throws Exception {
		if (source != null) {
			ImgFileUtils.makeDirectory(destPath);
			FileOutputStream fout = new FileOutputStream(destPath);
			ImageEncoder encoder = PNGCodec.createImageEncoder("PNG", fout,
					PNGEncodeParam.getDefaultEncodeParam(source));
			encoder.encode(source);
			fout.close();
		}
	}

	/**
	 * 读取两个颜色的线性距离
	 * 
	 * @param color1
	 * @param color2
	 * @return
	 */
	public static double getLinearDistance(int color1, int color2) {
		ColorModel cm = ColorModel.getRGBdefault();
		int color1_red = cm.getRed(color1);
		int color1_green = cm.getGreen(color1);
		int color1_blue = cm.getBlue(color1);
		int color2_red = cm.getRed(color2);
		int color2_green = cm.getGreen(color2);
		int color2_blue = cm.getBlue(color2);
		return Math.sqrt((color2_red - color1_red) * (color2_red - color1_red)
				+ (color2_green - color1_green) * (color2_green - color1_green)
				+ (color2_blue - color1_blue) * (color2_blue - color1_blue));
	}

	/**
	 * 获取一个颜色的向性
	 * 
	 * @param color
	 * @return
	 */
	public static Color getColorType(int color) {
		double toRed = getLinearDistance(color, Color.red.value());
		double toOrange = getLinearDistance(color, Color.orange.value());
		double toYellow = getLinearDistance(color, Color.yellow.value());
		double toGreen = getLinearDistance(color, Color.green.value());
		double toCyan = getLinearDistance(color, Color.cyan.value());
		double toBlue = getLinearDistance(color, Color.blue.value());
		double toPurple = getLinearDistance(color, Color.purple.value());
		double toWhite = getLinearDistance(color, Color.white.value());
		double toBlack = getLinearDistance(color, Color.black.value());

		double minValue = Math.min(toRed, Math.min(toOrange, Math.min(toYellow, Math.min(toGreen,
				Math.min(toCyan, Math.min(toBlue, Math.min(toPurple, Math.min(toWhite, toBlack))))))));

		if (toRed == minValue) {
			return Color.red;
		}
		if (toOrange == minValue) {
			return Color.orange;
		}
		if (toYellow == minValue) {
			return Color.yellow;
		}
		if (toGreen == minValue) {
			return Color.green;
		}
		if (toCyan == minValue) {
			return Color.cyan;
		}
		if (toBlue == minValue) {
			return Color.blue;
		}
		if (toPurple == minValue) {
			return Color.purple;
		}
		if (toWhite == minValue) {
			return Color.white;
		}
		if (toBlack == minValue) {
			return Color.black;
		}
		return Color.black;
	}

	/**
	 * 图片裁剪
	 * 
	 * @param image
	 * @param index_x
	 * @param index_y
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage cutImage(BufferedImage image, int index_x, int index_y, int width, int height)
			throws Exception {
		// BufferedImage img=null;
		// img=image.getSubimage(index_x, index_y, width, height);
		try {
			int maxWidth = image.getData().getWidth();
			int maxHeight = image.getData().getHeight();
			if ((index_x + width) > maxWidth) {
				width = maxWidth - index_x;
			}
			if ((index_y + height) > maxHeight) {
				height = maxHeight - index_y;
			}
			return image.getSubimage(index_x, index_y, width, height);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		/*
		 * BufferedImage newImg=new
		 * BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB)
		 * ; try{
		 * 
		 * for(int i=0;i<width;i++){ for(int j=0;j<height;j++){ newImg.setRGB(i,
		 * j, image.getRGB(index_x+i, index_y+j)); } } image=null;
		 * }catch(Exception e){ e.printStackTrace(); throw e; }
		 * 
		 * return newImg;
		 */
	}

	/**
	 * 按照比例缩图
	 * 
	 * @param image
	 * @param zoomInPercent
	 *            1-100
	 * @return
	 */
	public static BufferedImage alterSize(BufferedImage image, int zoomInPercent) {
		return alterSize(image, image.getWidth() * zoomInPercent / 100, image.getHeight() * zoomInPercent / 100);
	}

	/**
	 * 分形
	 * 
	 * @param image
	 * @param x_percent
	 * @param y_percent
	 * @param percent
	 * @return
	 */
	public static BufferedImage splitCell(BufferedImage image, int x_percent, int y_percent, int percent) {
		BufferedImage child = alterSize(image, percent);
		if (child.getWidth() > 10) {
			child = splitCell(child, x_percent, y_percent, percent);
		}
		int s_width = image.getWidth();
		int s_height = image.getHeight();
		image.setRGB(s_width * x_percent / 100, s_height * y_percent / 100, s_width * percent / 100,
				s_height * percent / 100,
				child.getRGB(0, 0, child.getWidth(), child.getHeight(), null, 0, child.getWidth()), 0,
				child.getWidth());
		return image;
	}

	public static BufferedImage rotateImage(BufferedImage bufferedimage, int degree) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		double rotateRound = Math.sqrt((double) (w * w + h * h));
		int dest_w = ((int) rotateRound + 1) * 2;
		int dest_h = ((int) rotateRound + 1) * 2;
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(dest_w, dest_h, type)).createGraphics())
				.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(Math.toRadians(degree), dest_w / 2, dest_h / 2);
		graphics2d.drawImage(bufferedimage, dest_w / 2, dest_h / 2, null);
		graphics2d.dispose();
		img = trimBorder(img);
		return img;
	}

	/**
	 * 旋转图片
	 * 
	 * @param source
	 * @param angle
	 * @return
	 */
	public static BufferedImage turnImage(BufferedImage source, double angle) {
		// if(angle%90!=0)
		// {
		// return null;
		// }
		while (angle < 0) {
			angle += 360;
		}
		angle = angle / 180 * Math.PI;
		angle %= 360;
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		double rotateRound = Math.sqrt((double) (source_w * source_w + source_h * source_h));
		// 旋转矩阵
		double[][] rotateMatrix = new double[3][3];
		rotateMatrix[0][0] = Math.cos(angle);
		rotateMatrix[0][1] = -Math.sin(angle);
		rotateMatrix[0][2] = 0;
		rotateMatrix[1][0] = Math.sin(angle);
		rotateMatrix[1][1] = Math.cos(angle);
		rotateMatrix[1][2] = 0;
		rotateMatrix[2][0] = 0;
		rotateMatrix[2][1] = 0;
		rotateMatrix[2][2] = 1;
		int[][] location = new int[3][1];
		double[][] newLocation = new double[3][1];
		// int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h,
		// null, 0, source_w);
		int dest_w = ((int) rotateRound + 1) * 2;
		int dest_h = ((int) rotateRound + 1) * 2;
		int[] destPicPixelds = new int[dest_w * dest_h];
		try {
			for (int i = 0; i < source_h; i++) {
				try {
					for (int j = 0; j < source_w; j++) {
						ColorModel cm = ColorModel.getRGBdefault();
						// int pixel = getPixel(sourcePicPixelds, source_w, j,
						// i);
						int pixel = source.getRGB(j, i);

						int red = cm.getRed(pixel);
						int green = cm.getGreen(pixel);
						int blue = cm.getBlue(pixel);

						// destPicPixelds[j*source_h+(source_h-i-1)] = pixel;
						location[0][0] = j;
						location[1][0] = i;
						location[2][0] = 1;
						newLocation[0][0] = rotateMatrix[0][0] * location[0][0] + rotateMatrix[0][1] * location[1][0]
								+ rotateMatrix[0][2] * location[2][0];
						newLocation[1][0] = rotateMatrix[1][0] * location[0][0] + rotateMatrix[1][1] * location[1][0]
								+ rotateMatrix[1][2] * location[2][0];
						newLocation[2][0] = rotateMatrix[2][0] * location[0][0] + rotateMatrix[2][1] * location[1][0]
								+ rotateMatrix[2][2] * location[2][0];
						int integerX = newLocation[0][0] > 0 ? (int) newLocation[0][0] : ((int) newLocation[0][0] - 1);
						int integerY = newLocation[1][0] > 0 ? (int) newLocation[1][0] : ((int) newLocation[1][0] - 1);
						double[][] colorPercent = new double[2][2];
						colorPercent[0][0] = 1
								- Math.sqrt((newLocation[0][0] - integerX) * (newLocation[0][0] - integerX)
										+ (newLocation[1][0] - integerY) * (newLocation[1][0] - integerY));
						if (colorPercent[0][0] < 0) {
							colorPercent[0][0] = 0;
						}
						colorPercent[0][1] = 1
								- Math.sqrt((integerX + 1 - newLocation[0][0]) * (integerX + 1 - newLocation[0][0])
										+ (newLocation[1][0] - integerY) * (newLocation[1][0] - integerY));
						if (colorPercent[0][1] < 0) {
							colorPercent[0][1] = 0;
						}
						colorPercent[1][0] = 1
								- Math.sqrt((newLocation[0][0] - integerX) * (newLocation[0][0] - integerX)
										+ (integerY + 1 - newLocation[1][0]) * (integerY + 1 - newLocation[1][0]));
						if (colorPercent[1][0] < 0) {
							colorPercent[1][0] = 0;
						}
						colorPercent[1][1] = 1
								- Math.sqrt((integerX + 1 - newLocation[0][0]) * (integerX + 1 - newLocation[0][0])
										+ (integerY + 1 - newLocation[1][0]) * (integerY + 1 - newLocation[1][0]));
						if (colorPercent[1][1] < 0) {
							colorPercent[1][1] = 0;
						}
						int destRed = 0, destGreen = 0, destBlue = 0, destPixel;

						destPixel = destPicPixelds[(integerY + dest_h / 2) * dest_w + integerX + dest_w / 2];
						destRed = cm.getRed(destPixel);
						destGreen = cm.getGreen(destPixel);
						destBlue = cm.getBlue(destPixel);
						destRed += (int) red * colorPercent[0][0];
						destGreen += (int) green * colorPercent[0][0];
						destBlue += (int) blue * colorPercent[0][0];
						destPicPixelds[(integerY + dest_h / 2) * dest_w + integerX + dest_w / 2] = mixColor(destRed,
								destGreen, destBlue);

						destPixel = destPicPixelds[(integerY + dest_h / 2) * dest_w + integerX + 1 + dest_w / 2];
						destRed = cm.getRed(destPixel);
						destGreen = cm.getGreen(destPixel);
						destBlue = cm.getBlue(destPixel);
						destRed += (int) red * colorPercent[0][1];
						destGreen += (int) green * colorPercent[0][1];
						destBlue += (int) blue * colorPercent[0][1];
						destPicPixelds[(integerY + dest_h / 2) * dest_w + integerX + 1 + dest_w / 2] = mixColor(destRed,
								destGreen, destBlue);

						destPixel = destPicPixelds[(integerY + 1 + dest_h / 2) * dest_w + integerX + dest_w / 2];
						destRed = cm.getRed(destPixel);
						destGreen = cm.getGreen(destPixel);
						destBlue = cm.getBlue(destPixel);
						destRed += (int) red * colorPercent[1][0];
						destGreen += (int) green * colorPercent[1][0];
						destBlue += (int) blue * colorPercent[1][0];
						destPicPixelds[(integerY + 1 + dest_h / 2) * dest_w + integerX + dest_w / 2] = mixColor(destRed,
								destGreen, destBlue);

						destPixel = destPicPixelds[(integerY + 1 + dest_h / 2) * dest_w + integerX + 1 + dest_w / 2];
						destRed = cm.getRed(destPixel);
						destGreen = cm.getGreen(destPixel);
						destBlue = cm.getBlue(destPixel);
						destRed += (int) red * colorPercent[1][1];
						destGreen += (int) green * colorPercent[1][1];
						destBlue += (int) blue * colorPercent[1][1];
						destPicPixelds[(integerY + 1 + dest_h / 2) * dest_w + integerX + 1 + dest_w / 2] = mixColor(
								destRed, destGreen, destBlue);
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		int temp = source_w;
		source_w = source_h;
		source_h = temp;
		System.gc();
		// for(int i:sourcePicPixelds)
		// {
		// System.out.print(i);
		// }
		source = new BufferedImage(dest_w, dest_h, BufferedImage.TYPE_INT_RGB);
		source.setRGB(0, 0, dest_w, dest_h, destPicPixelds, 0, dest_w);
		return trimBorder(source);
	}

	private static BufferedImage trimBorder(BufferedImage source) {
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		// int[] sourcePicPixelds = source.getRGB(0, 0, source_w, source_h,
		// null, 0, source_w);
		int x_start = 0, x_end = source_w, y_start = 0, y_end = source_h;
		try {
			for (int i = 0; i < source_h; i++) {
				try {
					int flag_y = 0;
					for (int j = 0; j < source_w; j++) {
						// int pixel = getPixel(sourcePicPixelds, source_w, j,
						// i);
						int pixel = source.getRGB(j, i);
						if (pixel != -16777216) {
							flag_y++;
							break;
						}
					}
					if (flag_y == 0) {
						y_start = i;
					} else {
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			for (int i = 0; i < source_w; i++) {
				try {
					int flag_x = 0;
					for (int j = 0; j < source_h; j++) {
						// int pixel = getPixel(sourcePicPixelds, source_w, i,
						// j);
						int pixel = source.getRGB(i, j);
						if (pixel != -16777216) {
							flag_x++;
							break;
						}
					}
					if (flag_x == 0) {
						x_start = i;
					} else {
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			for (int i = source_h; i > 0; i--) {
				try {
					int flag_y = 0;
					for (int j = 0; j < source_w; j++) {
						// int pixel = getPixel(sourcePicPixelds, source_w, j,
						// i);
						int pixel = source.getRGB(j, i);
						if (pixel != -16777216) {
							flag_y++;
							break;
						}
					}
					if (flag_y == 0) {
						y_end = i;
					} else {
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
			for (int i = source_w; i > 0; i--) {
				try {
					int flag_x = 0;
					for (int j = 0; j < source_h; j++) {
						// int pixel = getPixel(sourcePicPixelds, source_w, i,
						// j);
						int pixel = source.getRGB(i, j);
						if (pixel != -16777216) {
							flag_x++;
							break;
						}
					}
					if (flag_x == 0) {
						x_end = i;
					} else {
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					// 忽略行溢出的点
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// 忽略列溢出的点
		}
		BufferedImage newImg = null;
		newImg = source.getSubimage(x_start, y_start, x_end - x_start, y_end - y_start);
		source = null;
		return newImg;

	}

	private static int mixColor(int red, int green, int blue) {
		return red * 256 * 256 + green * 256 + blue;
	}

	/**
	 * 封装好的对图片进行简单旋转的处理
	 * 
	 * @param srcFilename
	 *            原始图片路径
	 * @param destPath
	 *            目标图片路径
	 * @param degree
	 *            旋转角度，90的倍数
	 */
	public static void rotateImage(String srcFilename, String destPath, int degree, int siteId,
			SysConfigService sysConfigService) throws Exception {
		try {
			BufferedImage bi = getGraphics(srcFilename, siteId, sysConfigService);
			BufferedImage newBi = null;
			newBi = turnImage(bi, degree);
			// bi = rotateImage(bi, degree); //图可能变大
			// bi = rotateImageIgnoreSize(bi, degree);//旋转保持原来宽/高
			bi = null;
			saveJPG(newBi, destPath);
			newBi = null;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 通过im4java翻转大图，im4java是调用的console，因此不会占用JVM的内存而导致内存溢出
	 * 
	 * @param srcFilename
	 * @param destPath
	 * @param degree
	 * @update liuguangqiang
	 * @throws Exception
	 */
	public static void gmRotateImage(String srcFilename, String destPath, int degree) throws Exception {
		try {
			ConvertCmd cmd = new ConvertCmd(true);
			IMOperation op = new IMOperation();
			// im4java.useGM=true;
			double di = degree;
			op.rotate(di);
			op.addImage(srcFilename);
			op.addImage(destPath);
			
			SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
			cmd.setSearchPath(sysConfigService.getDbSysConfig(
	                SysConfigConstant.LOCAL_GM_PATH,1));
			cmd.run(op);

		} catch (Exception e) {
			throw e;

		}

	}

	/**
	 * 封装好的对图片进行裁剪的处理
	 * 
	 * @param srcFilename
	 *            原始图片路径
	 * @param destPath
	 *            目标图片路径
	 * @param x
	 *            左上角位置
	 * @param y
	 *            右上角位置
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 */
	public static void cropImage(String srcFilename, String destPath, int x, int y, int w, int h, int siteId,
			SysConfigService sysConfigService) throws Exception {
		try {
			BufferedImage bi = getGraphics(srcFilename, siteId, sysConfigService);
			BufferedImage newBi = null;
			newBi = cutImage(bi, x, y, w, h);
			bi = null;
			saveJPG(newBi, destPath);
			newBi = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 旋转图片并自动缩图
	 * 
	 * @param source
	 * @param theta
	 * @throws Exception
	 */
	public static BufferedImage rotateImageIgnoreSize(BufferedImage source, int theta) throws Exception {
		int source_w = source.getWidth();
		int source_h = source.getHeight();
		int size = source_w > source_h ? source_w : source_h;
		try {
			source = rotateImage(source, theta);
			System.gc();
			int new_size = source.getWidth() > source.getHeight() ? source.getWidth() : source.getHeight();
			source = alterSize(source, size * 100 / new_size);
			return source;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 读取图片的宽高比，返回图片实体
	 * 
	 * @param ap
	 * @param currentFile
	 * @param trsLoggerManager
	 * @return
	 */
	public static CpPicture setPictureSize(CpPicture ap, File currentFile) {
		int height = 0;
		int width = 0;
		try {
			Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = (ImageReader) readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(currentFile);
			reader.setInput(iis, true);
			height = reader.getHeight(0);
			width = reader.getWidth(0);
			// 宽
			ap.setPictureWidth(width);
			// 高
			ap.setPictureHeight(height);
		} catch (IIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				IMOperation op = new IMOperation();
				op.format("%w#%h");
				op.addImage(currentFile.getAbsolutePath());
				IdentifyCmd identifyCmd = new IdentifyCmd(true);
				ArrayListOutputConsumer output = new ArrayListOutputConsumer();
				identifyCmd.setOutputConsumer(output);
				SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
				identifyCmd.setSearchPath(sysConfigService.getDbSysConfig(
		                SysConfigConstant.LOCAL_GM_PATH,1));
				identifyCmd.run(op);
				ArrayList<String> out = output.getOutput();
				String wNh = null;
				if (out.size() > 0) {
					wNh = out.get(0);
					width = Integer.parseInt(wNh.split("#")[0]);
					height = Integer.parseInt(wNh.split("#")[1]);
					// 宽
					ap.setPictureWidth(width);
					// 高
					ap.setPictureHeight(height);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return ap;
	}

	/**
	 * 读取图片的宽高比，返回map
	 * 
	 * @param currentFile
	 * @param trsLoggerManager
	 * @return
	 */
	public static Map<String, Integer> getPictureSize(String filePath) {
		int height = 0;
		int width = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		File currentFile = new File(filePath);
		if (!currentFile.exists()) {
			return map;
		}
		try {
			Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = (ImageReader) readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(currentFile);
			reader.setInput(iis, true);
			height = reader.getHeight(0);
			width = reader.getWidth(0);
			// 宽
			map.put("width", width);
			// 高
			map.put("height", height);
		} catch (IIOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			try {
				IMOperation op = new IMOperation();
				op.format("%w#%h");
				op.addImage(currentFile.getAbsolutePath());
				IdentifyCmd identifyCmd = new IdentifyCmd(true);
				ArrayListOutputConsumer output = new ArrayListOutputConsumer();
				identifyCmd.setOutputConsumer(output);
				//add by xiayunan 20170912
				SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
				identifyCmd.setSearchPath(sysConfigService.getDbSysConfig(
		                SysConfigConstant.LOCAL_GM_PATH,1));
				identifyCmd.run(op);
				ArrayList<String> out = output.getOutput();
				String wNh = null;
				if (out.size() > 0) {
					wNh = out.get(0);
					width = Integer.parseInt(wNh.split("#")[0]);
					height = Integer.parseInt(wNh.split("#")[1]);
					// 宽
					map.put("width", width);
					// 高
					map.put("height", height);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 输出的水印图
	 * 
	 * @param waterPic
	 *            生成的带水印的图
	 * @param srcPic
	 *            原图
	 * @param waterMarkerPic
	 *            水印图
	 * @param place
	 *            水印位置:center 居中；southwest 左下；southeast 右下
	 * @throws IM4JavaException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void waterMarkPic(String waterPic, String srcPic, String waterMarkerPic, String place,
			Boolean synFlag) throws IOException, InterruptedException, IM4JavaException {
		try {
			ImgFileUtils.makeDirectory(waterPic);
			IMOperation op = new IMOperation();
			op.gravity(place);
			op.addImage(waterMarkerPic);
			op.addImage(srcPic);
			op.addImage(waterPic);
			CompositeCmd convert = new CompositeCmd(true);
			if (synFlag == null || synFlag) {
				convert.setAsyncMode(true);
			}
			// add by xia.yunan@20170906
			SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
			convert.setSearchPath(sysConfigService.getDbSysConfig(
	                SysConfigConstant.LOCAL_GM_PATH,1));

			
			convert.run(op);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	/**
	 * 输出的水印图
	 * 
	 * @param waterPic 
	 * 			生成水印在特定位置的的水印图
	 * @param srcPic 
	 * 			原图
	 * @param waterMarkerPic 
	 * 			水印图
	 * @param postionCoefficient 
	 * 			位置系数
	 * @param alpha 
	 * 			水印透明度
	 * @param alpha 
	 * 			水印透明度
	 * @param synFlag 
	 * 			是否异步
	 * @throws IM4JavaException
	 * @throws InterruptedException
	 * @throws IOException
	 * @author xiayunan
	 * @date 2017-09-11
	 */
	public static void SpePositionWaterMarkPic(String waterPic, String srcPic, String waterMarkerPic,String postionCoefficient,int alpha,
			Boolean synFlag) throws IOException, InterruptedException, IM4JavaException {
		try {
			ImgFileUtils.makeDirectory(waterPic);
			IMOperation op = new IMOperation();
			int width = 0;//水印宽度（可以于水印图片大小不同） 
			int height = 0;//水印高度（可以于水印图片大小不同）
			int x = 0;//水印开始X坐标 
			int y = 0;//水印开始y坐标 
			double posCoefficient = Double.valueOf(postionCoefficient);
			Map<String,Integer> srcPicMap = getPictureSize(srcPic);
			Map<String,Integer> waterPicMap = getPictureSize(waterMarkerPic);
			width = waterPicMap.get("width");
			height = waterPicMap.get("height");
			if(posCoefficient>=0){//水印沿做左对角线从左往右
				x = (int)(posCoefficient*(srcPicMap.get("width")-width));
				y = (int)(posCoefficient*(srcPicMap.get("height")-height));
			}else{//水印沿右对角线从右往左
				posCoefficient = Math.abs(posCoefficient);
				x = (int)((1-posCoefficient)*(srcPicMap.get("width")-width));
				y = (int)(posCoefficient*(srcPicMap.get("height")-height));
			}
			op.dissolve(alpha); 
			op.geometry(width, height, x, y);
			
			op.addImage(waterMarkerPic);
			op.addImage(srcPic);
			op.addImage(waterPic);
			CompositeCmd convert = new CompositeCmd(true);
			if (synFlag == null || synFlag) {
				convert.setAsyncMode(true);
			}
			// add by xia.yunan@20170906
			SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
			convert.setSearchPath(sysConfigService.getDbSysConfig(
	                SysConfigConstant.LOCAL_GM_PATH,1));
			
			convert.run(op);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	

	public static int[] computeSize(BufferedImage alterdImage, int picRealSize) {
		int[] size = new int[2];
		int bigPicHeight = alterdImage.getHeight();
		int bigPicWidth = alterdImage.getWidth();
		float rate;
		if (bigPicWidth >= bigPicHeight) {
			rate = (float) bigPicHeight / (float) bigPicWidth;
			bigPicWidth = picRealSize;
			bigPicHeight = (int) (bigPicWidth * rate);
		} else {
			rate = (float) bigPicWidth / (float) bigPicHeight;
			bigPicHeight = picRealSize;
			bigPicWidth = (int) (bigPicHeight * rate);
		}
		size[0] = bigPicWidth;
		size[1] = bigPicHeight;
		return size;
	}

	/*
	 * public static BufferedImage computePosAndMark(String[] waterIds,String[]
	 * waterPoss,int waterMark,BufferedImage waterPic,BufferedImage
	 * alterdImage,CpWaterMarkPicture waterMarkPicture ,int
	 * siteId,SysConfigService sysConfigService) { BufferedImage finalImage =
	 * alterdImage; String waterPicPath = "";//水印图 // String site =
	 * ConfigConstant.getDefaultSite(); int waterPicHeight = 0; int
	 * waterPicWidth = 0; int posW = 0; int posH = 0; try { if(waterMark==1){
	 * for (int j = 0; j < waterIds.length; j++) { // WaterMarkPicture
	 * waterMarkPicture = wmpd.getWaterMarkPicture(Long.parseLong(waterIds[j]));
	 * waterPicPath =
	 * ImageConfig.getWaterMarkPicturePathOnServer(waterMarkPicture.getFilename(
	 * ), siteId,sysConfigService); waterPic = ImageIO.read(new
	 * FileInputStream(waterPicPath)); waterPicWidth = waterPic.getWidth();
	 * waterPicHeight = waterPic.getHeight(); //根据水印配置计算坐标 9个位置 if
	 * ("1".equalsIgnoreCase(waterPoss[j])) { posW = 0; posH = 0; } if
	 * ("2".equalsIgnoreCase(waterPoss[j])) { posW =
	 * alterdImage.getWidth()/2-waterPicWidth/2; posH = 0; } if
	 * ("3".equalsIgnoreCase(waterPoss[j])) { posW =
	 * alterdImage.getWidth()-waterPicWidth; posH = 0; } if
	 * ("4".equalsIgnoreCase(waterPoss[j])) { posW = 0; posH =
	 * alterdImage.getHeight()/2-waterPicHeight/2; } if
	 * ("5".equalsIgnoreCase(waterPoss[j])) { posW =
	 * alterdImage.getWidth()/2-waterPicWidth/2; posH =
	 * alterdImage.getHeight()/2-waterPicHeight/2; } if
	 * ("6".equalsIgnoreCase(waterPoss[j])) { posW =
	 * alterdImage.getWidth()-waterPicWidth; posH =
	 * alterdImage.getHeight()/2-waterPicHeight/2; } if
	 * ("7".equalsIgnoreCase(waterPoss[j])) { posW = 0; posH =
	 * alterdImage.getHeight()-waterPicHeight; } if
	 * ("8".equalsIgnoreCase(waterPoss[j])) { posW =
	 * alterdImage.getWidth()/2-waterPicWidth/2; posH =
	 * alterdImage.getHeight()-waterPicHeight; } if
	 * ("9".equalsIgnoreCase(waterPoss[j])) { posW =
	 * alterdImage.getWidth()-waterPicWidth; posH =
	 * alterdImage.getHeight()-waterPicHeight; } alterdImage =
	 * ImageAnalyseUtil.markPicture(alterdImage, waterPic, posW, posH, 50,
	 * true,"png"); } finalImage = alterdImage; }else { finalImage =
	 * alterdImage; }
	 * 
	 * } catch (Exception e) { // TODO: handle exception } return finalImage; }
	 */
	/**
	 * 生成水印图片
	 * 
	 * @param isWaterMark
	 *            是否水印：0是，1否
	 * @param postion
	 *            水印位置
	 * @param wMPath
	 *            水印图片地址
	 * @param waterPic
	 * @param alterdImage
	 * @return
	 */
	public static BufferedImage computePosAndMark(int isWaterMark, int postion, String wMPath, BufferedImage waterPic,
			BufferedImage alterdImage) {
		BufferedImage finalImage = alterdImage;
		int waterPicHeight = 0;
		int waterPicWidth = 0;
		int posW = 0;
		int posH = 0;
		try {
			if (isWaterMark == 0) {
				waterPic = ImageIO.read(new FileInputStream(wMPath));
				waterPicWidth = waterPic.getWidth();
				waterPicHeight = waterPic.getHeight();
				// 根据水印配置计算坐标 9个位置
				switch (postion) {
				case 1:
					posW = 0;
					posH = 0;
					break;
				case 2:
					posW = alterdImage.getWidth() / 2 - waterPicWidth / 2;
					posH = 0;
					break;
				case 3:
					posW = alterdImage.getWidth() - waterPicWidth;
					posH = 0;
					break;
				case 4:
					posW = 0;
					posH = alterdImage.getHeight() / 2 - waterPicHeight / 2;
					break;
				case 5:
					posW = alterdImage.getWidth() / 2 - waterPicWidth / 2;
					posH = alterdImage.getHeight() / 2 - waterPicHeight / 2;
					break;
				case 6:
					posW = alterdImage.getWidth() - waterPicWidth;
					posH = alterdImage.getHeight() / 2 - waterPicHeight / 2;
					break;
				case 7:
					posW = 0;
					posH = alterdImage.getHeight() - waterPicHeight;
					break;
				case 8:
					posW = alterdImage.getWidth() / 2 - waterPicWidth / 2;
					posH = alterdImage.getHeight() - waterPicHeight;
					break;
				case 9:
					posW = alterdImage.getWidth() - waterPicWidth;
					posH = alterdImage.getHeight() - waterPicHeight;
					break;
				case 10://自定义水印位置，根据 位置系数确定在左右对角线上的位置
					// add by xia.yunan@20170906
					SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
					System.out.println("<<<<<<<<<sysConfigService:"+sysConfigService);
					String waterPosCoefficient = sysConfigService.getDbSysConfig(
			                SysConfigConstant.WATERMAKER_POSITION,1);
					double posCoefficient = Double.valueOf(waterPosCoefficient);
					System.out.println("posCoefficient:"+posCoefficient);
					if(posCoefficient>=0){
						posW = (int)(posCoefficient*(alterdImage.getWidth() - waterPicWidth));
						posH = (int)(posCoefficient*(alterdImage.getHeight() - waterPicHeight));
					}else{
						posCoefficient = Math.abs(posCoefficient);
						posW = (int)((1-posCoefficient)*(alterdImage.getWidth() - waterPicWidth));
						posH = (int)(posCoefficient*(alterdImage.getHeight() - waterPicHeight));
					}
					break;
				default:
					posW = alterdImage.getWidth() - waterPicWidth;
					posH = alterdImage.getHeight() - waterPicHeight;
					break;
				}
				alterdImage = ImageAnalyseUtil.markPicture(alterdImage, waterPic, posW, posH, 50, true, "png");
				finalImage = alterdImage;
			} else {
				finalImage = alterdImage;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载生成水印出错:" + e.getMessage());
		}
		return finalImage;
	}

	/**
	 * 改变图片的大小
	 * 
	 * @param alterdImage
	 * @param pxW
	 * @param pxH
	 * @return
	 */
	public static BufferedImage resize(BufferedImage alterdImage, int pxH) {
		int height = alterdImage.getHeight();
		int width = alterdImage.getWidth();
		BufferedImage image = null;
		if (pxH > height && pxH > width) {
			return alterdImage;
		} else {
			if (height > width) {
				double percent = height / pxH;
				double newWidth = width / percent;
				image = new BufferedImage((int) newWidth, pxH, BufferedImage.TYPE_INT_BGR);
				Graphics graphics = image.createGraphics();
				graphics.drawImage(alterdImage, 0, 0, (int) newWidth, pxH, null);
			}
			if (height < width) {
				double percent = width / pxH;
				double newHeight = height / percent;
				image = new BufferedImage(pxH, (int) newHeight, BufferedImage.TYPE_INT_BGR);
				Graphics graphics = image.createGraphics();
				graphics.drawImage(alterdImage, 0, 0, pxH, (int) newHeight, null);
			}
			if (height == width) {
				image = new BufferedImage(pxH, pxH, BufferedImage.TYPE_INT_BGR);
				Graphics graphics = image.createGraphics();
				graphics.drawImage(alterdImage, 0, 0, pxH, pxH, null);
			}
		}

		return image;
	}
}
