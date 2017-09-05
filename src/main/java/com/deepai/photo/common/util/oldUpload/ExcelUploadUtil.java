package com.deepai.photo.common.util.oldUpload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import cn.jpush.api.utils.StringUtils;

import com.deepai.photo.bean.CpOldpictureExcelList;
import com.deepai.photo.common.StringUtil;

public class ExcelUploadUtil {
	public static final String OFFICE_EXCEL_2003_POSTFIX = ".xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = ".xlsx";
	public static final String EMPTY = "";
	//获取文件夹下所有文件名
	public static List<String> lisefile(File file) {
		List<String> listStr=new ArrayList<String>();
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files != null) {
				if (files.length == 0) {
					return null;
				} else {
					for (File file2 : files) {
						if (file2.isDirectory()) {
							List<String> list = lisefile(file2);
							if(list!=null){
								listStr.addAll(list);
							}
						} else {
							if(file2.getName().indexOf(".zip")==-1){
								listStr.add(file2.getName());
							}
						}
					}
				}
			}
		} else {
			return null;
		}
		return listStr;
	}
	//删除文件及文件夹
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 解压缩zip包
	 * 
	 * @param zipFilePath
	 *            zip文件的全路径
	 * @param unzipFilePath
	 *            解压后的文件保存的路径
	 * @param includeZipFileName
	 *            解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
	 */
	@SuppressWarnings("unchecked")
	public static void unzip(String zipFilePath, String unzipFilePath,
			boolean includeZipFileName) throws Exception {
		if (StringUtils.isEmpty(zipFilePath)
				|| StringUtils.isEmpty(unzipFilePath)) {
			return;
		}
		File zipFile = new File(zipFilePath);
		// 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			if (StringUtils.isNotEmpty(fileName)) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}
		// 创建解压缩文件保存的路径
		File unzipFileDir = new File(unzipFilePath);
		if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
			unzipFileDir.mkdirs();
		}

		// 开始解压
		final int bufferSize = 1024;
		final byte[] buffer = new byte[bufferSize];
		Charset charset = Charset.forName("gbk");//window下压缩采用了gbk，而zip解压缩是默认utf-8
		final ZipFile zip = new ZipFile(zipFile,charset);
		if (zip.size() == 0) {
			delFolder(unzipFilePath);
			throw new RuntimeException("zip文件为空");
		}
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
		// 循环对压缩包里的每一个文件进行解压
		ZipEntry entry = null;
		ExecutorService exe = Executors.newFixedThreadPool(1);
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			final String unzipFilePath_=unzipFilePath;
			final ZipEntry entry_=entry;
			exe.execute(new Runnable() {
				@Override
				public void run() {
					try {
						File entryDir = null;
						String entryDirPath = null;
						// 构建压缩包中一个文件解压后保存的文件全路径
						String entryFilePath = unzipFilePath_
								+ File.separator;
						if(entry_.getName().indexOf("/")!=-1){
							entryFilePath=entryFilePath+entry_.getName().substring(entry_.getName().indexOf("/"),
									entry_.getName().length());
						}else{
							entryFilePath=entryFilePath+entry_.getName();
						}
						// 构建解压后保存的文件夹路径
						int index = entryFilePath.lastIndexOf(File.separator);
						if (index != -1) {
							entryDirPath = entryFilePath.substring(0, index);
						} else {
							entryDirPath = "";
						}
						entryDir = new File(entryDirPath);
						// 如果文件夹路径不存在，则创建文件夹
						if (!entryDir.exists() || !entryDir.isDirectory()) {
							entryDir.mkdirs();
						}
						// 创建解压文件
						File entryFile = new File(entryFilePath);
						if (entryFile.exists()) {
							// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
							SecurityManager securityManager = new SecurityManager();
							//securityManager.checkDelete(entryFilePath);
							// 删除已存在的目标文件
							entryFile.delete();
						}
						// 写入文件
						BufferedInputStream bis = null;
						BufferedOutputStream bos = null;
						int  count = 0;
						bos = new BufferedOutputStream(new FileOutputStream(entryFile));
						bis = new BufferedInputStream(zip.getInputStream(entry_));
						while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
							bos.write(buffer, 0, count);
						}
						bos.flush();
						bos.close();
					} catch (Exception e) {
						System.out.println("文件写入失败");
						e.printStackTrace();
					} 
				}
			});
		}
		exe.shutdown();
		while (true) {
			if (exe.isTerminated()) {
				break;
			}
			Thread.sleep(100);
		}
	}

	public static void main(String[] args) {
		String zipFilePath = "C:\\Users\\Admin\\Desktop\\中新社\\A05-老照片\\A05\\20130826\\E第四本政界人物WXH.zip";
		String unzipFilePath = "D:\\ziptest\\zipPath";
		try {
			unzip(zipFilePath, unzipFilePath, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 创建文件目录
	public static String getfilePath(String path, Integer fileNum) {
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		if (path.lastIndexOf("/") + 1 != path.length()) {
			path = path + "/";
		}
		Calendar dayc = new GregorianCalendar();
		dayc.setTime(new Date()); // 设置calendar的日期
		path = path + dayc.get(Calendar.YEAR);
		filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		path = path + "/" + (Integer.valueOf(dayc.get(Calendar.MONTH)) + 1);
		filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		path = path + "/" + dayc.get(Calendar.DATE);
		filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		if (fileNum == null) {
			fileNum = 1;
		}
		path = path + "/" + generatePath(fileNum);
		filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		return path;
	}

	private static String generatePath(int fileNum) {
		String path_ = "";
		String path = String.valueOf(fileNum);
		if (path.length() < 5) {
			for (int i = 0; i < 4 - path.length(); i++) {
				path_ = path_ + "0";
			}
			path_ = path_ + path;
			return path_;
		}
		return path;
	}

	/**
	 * 第一次上传excel简略读一下相关信息
	 * 
	 * */
	public static int briefReadExcel(File file) throws IOException {
		// 獲取文件名
		String value = file.getName();
		// 取到最后一个.。
		int start = value.lastIndexOf(".");
		// 截取上传文件的 字符串名字。+1是去掉反斜杠。
		String fileFormat = value.substring(start);
		if (!EMPTY.equals(fileFormat)) {
			InputStream is = new FileInputStream(file);
			// 判断上传的文件类型
			if (OFFICE_EXCEL_2003_POSTFIX.equals(fileFormat)) {
				return briefReadXls(is);
			} else if (OFFICE_EXCEL_2010_POSTFIX.equals(fileFormat)) {
				return briefReadXlsx(is);
			}
		}
		return 0;
	}

	private static int briefReadXls(InputStream is) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		int rowNum = 0;
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hSSFSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hSSFSheet == null) {
				continue;
			}
			for (int rowNum_ = 1; rowNum_ <= hSSFSheet.getLastRowNum(); rowNum_++) {
				HSSFRow hSSFRow = hSSFSheet.getRow(rowNum_);
				if (hSSFRow != null) {
					HSSFCell rowone = hSSFRow.getCell(0);
					if (StringUtil.isNotEmpty(getValue(rowone))) {
						rowNum++;
					}
				}
			}
		}
		return rowNum;
	}

	private static int briefReadXlsx(InputStream is) throws IOException {
		int rowNum = 0;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			for (int rowNum_ = 1; rowNum_ <= xssfSheet.getLastRowNum(); rowNum_++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum_);
				if (xssfRow != null) {
					XSSFCell rowone = xssfRow.getCell(0);
					if (StringUtil.isNotEmpty(getValue(rowone))) {
						rowNum++;
					}
				}
			}
		}
		return rowNum;
	}

	public static List<CpOldpictureExcelList> readExcel(String filePath,
			int excelid,String path) throws IOException {
		File file = new File(filePath);
		// 获取路径名
		String value = file.getName();
		// 取到最后一个反斜杠。
		int start = value.lastIndexOf(".");
		// 截取上传文件的 字符串名字。+1是去掉反斜杠。
		String fileFormat = value.substring(start);
		if (!EMPTY.equals(fileFormat)) {
			@SuppressWarnings("resource")
			InputStream is = new FileInputStream(file);
			if (OFFICE_EXCEL_2003_POSTFIX.equals(fileFormat)) {
				return readXls(is, excelid,path);
			} else if (OFFICE_EXCEL_2010_POSTFIX.equals(fileFormat)) {
				return readXlsx(is, excelid,path);
			}
		}
		return null;
	}

	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	private static List<CpOldpictureExcelList> readXlsx(InputStream is,
			int excelid,String path) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<CpOldpictureExcelList> list = new ArrayList<CpOldpictureExcelList>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					CpOldpictureExcelList cpel = new CpOldpictureExcelList();
					XSSFCell title = xssfRow.getCell(0);
					XSSFCell oldDate = xssfRow.getCell(1);
					XSSFCell years = xssfRow.getCell(2);
					XSSFCell category = xssfRow.getCell(3);
					XSSFCell figure = xssfRow.getCell(4);
					XSSFCell place = xssfRow.getCell(5);
					XSSFCell author = xssfRow.getCell(6);
					XSSFCell editor = xssfRow.getCell(7);
					XSSFCell number = xssfRow.getCell(8);
					XSSFCell picFileName = xssfRow.getCell(9);
					XSSFCell keywords = xssfRow.getCell(10);
					XSSFCell memo = xssfRow.getCell(11);
					XSSFCell handin = xssfRow.getCell(12);
					cpel.setTitle(getValue(title));
					cpel.setOldDate(getValue(oldDate));
					cpel.setYears(getValue(years));
					cpel.setCategory(getValue(category));
					cpel.setFigure(getValue(figure));
					cpel.setPlace(getValue(place));
					cpel.setAuthor(getValue(author));
					cpel.setEditor(getValue(editor));
					cpel.setNumber(getValue(number));
					cpel.setPicFileName(getValue(picFileName));
					cpel.setKeywords(getValue(keywords));
					cpel.setHandin(getValue(handin));
					cpel.setMemo(getValue(memo));
					cpel.setExcelid(excelid);
					cpel.setFileAllpath(path+"/"+getValue(picFileName));
					list.add(cpel);
				}
			}
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	private static List<CpOldpictureExcelList> readXls(InputStream is,
			int excelid,String path) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<CpOldpictureExcelList> list = new ArrayList<CpOldpictureExcelList>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hSSFSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hSSFSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
				HSSFRow hSSFRow = hSSFSheet.getRow(rowNum);
				if (hSSFRow != null) {
					CpOldpictureExcelList cpel = new CpOldpictureExcelList();
					HSSFCell title = hSSFRow.getCell(0);
					HSSFCell oldDate = hSSFRow.getCell(1);
					HSSFCell years = hSSFRow.getCell(2);
					HSSFCell category = hSSFRow.getCell(3);
					HSSFCell figure = hSSFRow.getCell(4);
					HSSFCell place = hSSFRow.getCell(5);
					HSSFCell author = hSSFRow.getCell(6);
					HSSFCell editor = hSSFRow.getCell(7);
					HSSFCell number = hSSFRow.getCell(8);
					HSSFCell picFileName = hSSFRow.getCell(9);
					HSSFCell keywords = hSSFRow.getCell(10);
					HSSFCell memo = hSSFRow.getCell(11);
					HSSFCell handin = hSSFRow.getCell(12);
					cpel.setTitle(getValue(title));
					cpel.setOldDate(getValue(oldDate));
					cpel.setYears(getValue(years));
					cpel.setCategory(getValue(category));
					cpel.setFigure(getValue(figure));
					cpel.setPlace(getValue(place));
					cpel.setAuthor(getValue(author));
					cpel.setEditor(getValue(editor));
					cpel.setNumber(getValue(number));
					cpel.setPicFileName(getValue(picFileName));
					cpel.setKeywords(getValue(keywords));
					cpel.setHandin(getValue(handin));
					cpel.setMemo(getValue(memo));
					cpel.setExcelid(excelid);
					cpel.setFileAllpath(path+"/"+getValue(picFileName));
					list.add(cpel);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow != null) {
			if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfRow.getBooleanCellValue());
			} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
				return String.valueOf(xssfRow.getNumericCellValue());
			} else {
				return String.valueOf(xssfRow.getStringCellValue());
			}
		}
		return null;
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell != null) {
			if (hssfCell == null || "null".equals(hssfCell)) {
				return null;
			}
			if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(hssfCell.getBooleanCellValue());
			} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(hssfCell.getNumericCellValue());
			} else {
				return String.valueOf(hssfCell.getStringCellValue());
			}
		}
		return null;
	}
}
