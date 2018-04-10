package com.deepai.photo.common.util.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;

import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.util.SessionUtils;

/**
 * @Title 文件常用操作类
 * @Author guoyanhong 
 */
public class ImgFileUtils {
	/**
	 * 读取绝对路径的文件内容
	 * 
	 * @param filename
	 *            String
	 * @return String
	 */
	public static String read(String filename) {
		String strBuffer = new String();
		try {
			File file = new File(filename);
			if (file.isFile()) {

				Long lens = new Long(file.length());
				byte[] buffer = new byte[lens.intValue()];
				FileInputStream in = new FileInputStream(file);
				in.read(buffer);
				strBuffer = new String(buffer);
				in.close();
			} else {
				// Debuger.print(filename +
				// ": is not a file ,please confirm you file name");
			}
		} catch (IOException e) {
			// log.info(filename+": read IOException..."+e.getMessage());
			e.printStackTrace();
			// Debuger.print(e);
		}
		return strBuffer;
	}

	/**
	 * 读取相对路径的文件串
	 * 
	 * @param filename
	 *            String 文件名，是相对路径
	 * @param flag
	 *            int 标志
	 * @return String
	 */
	public static String read(String filename, int flag) {
		StringBuffer strBuffer = new StringBuffer();
		int len = 0;
		try {
			// 先读入该java文件
			// log.info(filename+": start reading ...");

			ClassLoader loader = ImgFileUtils.class.getClassLoader();
			InputStream in = loader.getResourceAsStream(filename);

			// 每次读取1024字节
			byte[] buffer = new byte[1024];
			if (in != null) {
				while (len != -1) {

					len = in.read(buffer);
					if (len == -1)
						break;
					if (len < 1024) {
						byte[] tmp = new byte[len];

						for (int n = 0; n < len; n++) {
							tmp[n] = buffer[n];
						}
						String temp = new String(tmp);

						strBuffer.append(temp);

					} else {

						strBuffer.append(new String(buffer));
					}
					in.close();
				}
			} else {
				// Debuger.print(filename +
				// ": is not a file ,please confirm you file name");
				// log.info(filename+": is not a file ,please confirm you file
				// name");
			}
		} catch (IOException e) {
			// log.info(filename+": read IOException..."+e.getMessage());
			e.printStackTrace();
			// Debuger.print(e);
		}
		return new String(strBuffer);
	}

	/**
	 * 写文件
	 * 
	 * @param strBuffer
	 *            写入得字符串
	 * @param filename
	 *            文件名（包括路径）
	 */
	public static void write(String strBuffer, String filename) {
		try {
			// log.info(filename+": start writing ... ");
			filename = filename.replaceAll("//", "/");
			// System.out.println(filename);
			File file = new File(filename);
			if (file.isFile()) {
				System.out.println(filename + ": has existed..delete..");
			}

			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(strBuffer.getBytes());
			out.close();
			file.exists();
			// log.info(filename+": end writing ... ");
		} catch (IOException e) {
			e.printStackTrace();
			// Debuger.print(e);
		}

	}

	/**
	 * 利用传入的InputStream写文件到filename
	 * 
	 * @param filename
	 *            String
	 * @param in
	 *            InputStream
	 */
	public static void write(String filename, InputStream in) {
		byte[] b = new byte[2048];
		try {
			// log.info(filename+": start writing ... ");
			filename = filename.replaceAll("//", "/");
			// System.out.println(filename);
			File file = new File(filename);

			if (file.isFile()) {
				// Debuger.print(filename + ": has existed..delete..");
				file.delete();
			}
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			int len = 0;
			do {
				len = in.read(b);

				if (len == -1)
					break;
				out.write(b, 0, len);
			} while (len != -1);

			file.exists();
			out.close();
			// out.flush();
			// log.info(filename+": end writing ... ");
		} catch (IOException e) {
			e.printStackTrace();
			// Debuger.print(e);
		}
	}

	/**
	 * 获取从classpath根目录开始读取文件注意转化成中文
	 * 
	 * @param path
	 *            String
	 * @return String
	 */
	public static String getCPFile(String path) {
		URL url = ImgFileUtils.class.getClassLoader().getResource(path);
		String filepath = url.getFile();
		File file = new File(filepath);
		byte[] retBuffer = new byte[(int) file.length()];
		try {
			FileInputStream fis = new FileInputStream(filepath);
			fis.read(retBuffer);
			fis.close();
			return new String(retBuffer, "GBK");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 利用java本地拷贝文件及文件夹,实现文件夹对文件夹的拷贝
	 * 
	 * @param objDir
	 *            目标文件夹
	 * @param srcDir
	 *            源的文件夹
	 * @throws IOException
	 */
	public static void copyDirectiory(String objDir, String srcDir)
			throws IOException {
		(new File(objDir)).mkdirs();
		File[] file = (new File(srcDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				FileInputStream input = new FileInputStream(file[i]);
				FileOutputStream output = new FileOutputStream(objDir + "/"
						+ file[i].getName());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (file[i].isDirectory()) {
				copyDirectiory(objDir + "/" + file[i].getName(), srcDir + "/"
						+ file[i].getName());
			}
		}
	}

	/**
	 * 将一个文件inName拷贝到另外一个文件outName中
	 * 
	 * @param inName
	 *            源文件路径
	 * @param outName
	 *            目标文件路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFile(String inName, String outName)throws FileNotFoundException, IOException {
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				inName));
		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(outName));
		copyFile(is, os, true);
	}
	
	
	/**
	 * 将一个文件inName拷贝到另外一个文件outName中
	 * 
	 * @author xiayunan
	 * @date 2017-10-13
	 * @param inName
	 *            源文件路径
	 * @param outName
	 *            目标文件路径
	 * @param outName
	 *            目标文件编码
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFile(String inName, String outName,String charset)throws FileNotFoundException, IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inName),charset));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outName),charset)));
		String str;
		while ( (str = in.readLine())!=null) {
			out.println(str);
			//将数组内容写入；
			out.flush(); //刷新； 
		}
		if(in!=null){
			in.close();
		}
		if(out!=null){
			out.close();
		}
	}
	
	
	
	/**
	 * 拷贝网络文件至本地
	 * @param remoteUrl
	 * @param localDir
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	/*@SuppressWarnings("unused")
	public static void smbGet(String remoteUrl, String localDir)  throws FileNotFoundException, IOException{
		InputStream in = null;
		OutputStream out = null;
		try {
			String smbPath=getSmbPath(remoteUrl);
			//smbPath="smb://Administrator:wlb1029@10.1.90.177/tupian/bjrb_trsphoto/small/2011/20110921/201109211644_1316594668368_p.jpg";
			SmbFile remoteFile = new SmbFile(smbPath);
			// 这一句很重要
			remoteFile.connect();
			if (remoteFile == null) {
				return;
			}
			String fileName = remoteFile.getName();
//			File localFile = new File(localDir + File.separator + fileName);
			in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			out = new BufferedOutputStream(new FileOutputStream(localDir));
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1) {
				out.write(buffer);
				buffer = new byte[1024];
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * 构造网络路径
	 * @param filename
	 * @return
	 */
	/*public static String getSmbPath(String filename){
		String siteId=ConfigConstant.getDefaultSite();
		String url=ConfigConstant.getValue("networkUrl", siteId);
		String userName=ConfigConstant.getValue("networkUserName", siteId);
		String pwd=ConfigConstant.getValue("networkUserPwd", siteId);
		StringBuilder sb=new StringBuilder();
		sb.append("smb://");
		sb.append(userName);
		sb.append(":");
		sb.append(pwd);
		sb.append("@");
		sb.append(url);
		sb.append(filename.substring(2).replaceAll("\\\\", "/").replaceAll("//", "/"));
		return sb.toString();
	}*/

	/**
	 * Copy a file from an opened InputStream to opened OutputStream
	 * 
	 * @param is
	 *            source InputStream
	 * @param os
	 *            target OutputStream
	 * @param close
	 *            写入之后是否需要关闭OutputStream
	 * @throws IOException
	 */
	public static void copyFile(InputStream is, OutputStream os, boolean close)
			throws IOException {
		int b;
		while ((b = is.read()) != -1) {
			os.write(b);
		}
		
		is.close();
		if (close){
			os.flush();
			os.close();
		}
			
	}

	/**
	 * 拷贝文件
	 * 
	 * @param is
	 *            Reader
	 * @param os
	 *            Writer
	 * @param close
	 *            boolean 执行完毕后Writer是否要关闭
	 * @throws IOException
	 */
	public static void copyFile(Reader is, Writer os, boolean close)
			throws IOException {
		int b;
		while ((b = is.read()) != -1) {
			os.write(b);
		}
		is.close();
		if (close)
			os.close();
	}

	/**
	 * 拷贝文件
	 * 
	 * @param inName
	 *            String
	 * @param pw
	 *            PrintWriter
	 * @param close
	 *            boolean 执行完毕后Writer是否要关闭
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFile(String inName, PrintWriter pw, boolean close)
			throws FileNotFoundException, IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		copyFile(is, pw, close);
	}

	/**
	 * 从文件inName中读取第一行的内容
	 * 
	 * @param inName
	 *            源文件路径
	 * @return 第一行的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readLine(String inName) throws FileNotFoundException,
			IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		String line = null;
		line = is.readLine();
		is.close();
		return line;
	}

	/**
	 * default buffer size
	 */
	private static final int BLKSIZ = 8192;

	/**
	 * 拷贝文件
	 * 
	 * @param inName
	 *            String 输入文件名
	 * @param outName
	 *            String 输出文件名
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFileBuffered(String inName, String outName)
			throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(inName);
		OutputStream os = new FileOutputStream(outName);
		int count = 0;
		byte b[] = new byte[BLKSIZ];
		while ((count = is.read(b)) != -1) {
			os.write(b, 0, count);
		}
		is.close();
		os.close();
	}

	/**
	 * 将String变成文本文件
	 * 
	 * @param text
	 *            源String
	 * @param fileName
	 *            目标文件路径
	 * @throws IOException
	 */
	public static void stringToFile(String text, String fileName)
			throws IOException {
		BufferedWriter os = new BufferedWriter(new FileWriter(fileName));
		os.write(text);
		os.flush();
		os.close();
	}

	/**
	 * 打开文件获得BufferedReader
	 * 
	 * @param fileName
	 *            目标文件路径
	 * @return BufferedReader
	 * @throws IOException
	 */
	public static BufferedReader openFile(String fileName) throws IOException {
		return new BufferedReader(new FileReader(fileName));
	}

	/**
	 * 获取文件filePath的字节编码byte[]
	 * 
	 * @param filePath
	 *            文件全路径
	 * @return 文件内容的字节编码
	 * @roseuid 3FBE26DE027D
	 */
	public static byte[] fileToBytes(String filePath) {
		if (filePath == null) {
			return null;
		}
		File tmpFile = new File(filePath);
		if (tmpFile == null) {
			return null;
		}
		byte[] retBuffer = new byte[(int) tmpFile.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			fis.read(retBuffer);
			fis.close();
			return retBuffer;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 将byte[]转化成文件fullFilePath
	 * 
	 * @param fullFilePath
	 *            文件全路径
	 * @param content
	 *            源byte[]
	 */
	public static void bytesToFile(String fullFilePath, byte[] content) {
		if (fullFilePath == null || content == null) {
			return;
		}
		// 创建相应的目录
		File f = new File(getDir(fullFilePath));
		if (f == null || !f.exists()) {
			f.mkdirs();
		}
		try {
			FileOutputStream fos = new FileOutputStream(fullFilePath);
			fos.write(content);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据传入的文件全路径，返回文件所在路径
	 * 
	 * @param fullPath
	 *            文件全路径
	 * @return 文件所在路径
	 */
	public static String getDir(String fullPath) {
		int iPos1 = fullPath.lastIndexOf("/");
		int iPos2 = fullPath.lastIndexOf("\\");
		iPos1 = (iPos1 > iPos2 ? iPos1 : iPos2);
		return fullPath.substring(0, iPos1 + 1);
	}

	/**
	 * 根据传入的文件全路径，返回文件全名（包括后缀名）
	 * 
	 * @param fullPath
	 *            文件全路径
	 * @return 文件全名（包括后缀名）
	 */
	public static String getFileName(String fullPath) {
		int iPos1 = fullPath.lastIndexOf("/");
		int iPos2 = fullPath.lastIndexOf("\\");
		iPos1 = (iPos1 > iPos2 ? iPos1 : iPos2);
		return fullPath.substring(iPos1 + 1);
	}

	/**
	 * 获得文件名fileName中的后缀名
	 * 
	 * @param fileName
	 *            源文件名
	 * @return String 后缀名
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName
				.length());
	}

	/**
	 * 根据传入的文件全名（包括后缀名）或者文件全路径返回文件名（没有后缀名）
	 * 
	 * @param fullPath
	 *            文件全名（包括后缀名）或者文件全路径
	 * @return 文件名（没有后缀名）
	 */
	public static String getPureFileName(String fullPath) {
		String fileFullName = getFileName(fullPath);
		return fileFullName.substring(0, fileFullName.lastIndexOf("."));
	}

	/**
	 * 转换文件路径中的\\为/
	 * 
	 * @param filePath
	 *            要转换的文件路径
	 * @return String
	 */
	public static String wrapFilePath(String filePath) {
		filePath.replace('\\', '/');
		if (filePath.charAt(filePath.length() - 1) != '/') {
			filePath += "/";
		}
		return filePath;
	}

	/**
	 * 删除整个目录path,包括该目录下所有的子目录和文件
	 * 
	 * @param path
	 */
	public static void deleteDirs(String path) {
		File rootFile = new File(path);
		if (rootFile == null) {
			return;
		}
		File[] files = rootFile.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				deleteDirs(file.getPath());
			} else {
				file.delete();
			}
		}
		rootFile.delete();
	}
	
	/**
	 * 删除目录下子目录和文件（按文件最后修改时间）
	 * path：文件路径
	 * sumDay：需要删除几天前的文件（最后修改时间），如1，则表示删除一天前的
	 * @author PuTao
	 * @date 2011-4-11
	 */
	public static void deleteDirsByDate(String path,String sumDay){
		File rootFile = new File(path);
		if (rootFile == null) {
			return;
		}
		File[] files = rootFile.listFiles();
		if (files == null) {
			return;
		}
		long millis = getMillis(new Date()) - (Long.parseLong(sumDay)) * 24 * 3600 * 1000;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			Long fileDate=file.lastModified();
			if(fileDate<millis){
				if (file.isDirectory()) {
					deleteDirs(file.getPath());
				} else {
					file.delete();
				}
			}
		}
//		rootFile.delete();
	}
	
	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 从Reader读取字符串
	 * 
	 * @param is
	 *            Reader
	 * @return String
	 * @throws IOException
	 */
	public static String readerToString(Reader is) throws IOException {
		StringBuffer sb = new StringBuffer();
		char[] b = new char[BLKSIZ];
		int n;
		while ((n = is.read(b)) > 0) {
			sb.append(b, 0, n);
		}
		return sb.toString();
	}

	/**
	 * 从InputStream读取字符串
	 * 
	 * @param is
	 *            InputStream
	 * @return String
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream is) throws IOException {
		return readerToString(new InputStreamReader(is));
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 * 
	 * @param file
	 *            需要修改最后访问时间的文件。
	 * @since 0.1
	 */
	public static void touch(File file) {
		long currentTime = System.currentTimeMillis();
		if (!file.exists()) {
			System.err.println("file not found:" + file.getName());
			System.err.println("Create a new file:" + file.getName());
			try {
				if (file.createNewFile()) {
					System.out.println("Succeeded!");
				} else {
					System.err.println("Create file failed!");
				}
			} catch (IOException e) {
				System.err.println("Create file failed!");
				e.printStackTrace();
			}
		}
		boolean result = file.setLastModified(currentTime);
		if (!result) {
			System.err.println("touch failed: " + file.getName());
		}
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 * 
	 * @param fileName
	 *            需要修改最后访问时间的文件的文件名。
	 * @since 0.1
	 */
	public static void touch(String fileName) {
		File file = new File(fileName);
		touch(file);
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 * 
	 * @param files
	 *            需要修改最后访问时间的文件数组。
	 * @since 0.1
	 */
	public static void touch(File[] files) {
		for (int i = 0; i < files.length; i++) {
			touch(files[i]);
		}
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 * 
	 * @param fileNames
	 *            需要修改最后访问时间的文件名数组。
	 * @since 0.1
	 */
	public static void touch(String[] fileNames) {
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(fileNames[i]);
		}
		touch(files);
	}

	/**
	 * 判断指定的文件是否存在。
	 * 
	 * @param fileName
	 *            要判断的文件的文件名
	 * @return 存在时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean isFileExist(String fileName) {
		return new File(fileName).isFile();
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 * 
	 * @param file
	 *            要创建的目录
	 * @return 完全创建成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 * 
	 * @param fileName
	 *            要创建的目录的目录名
	 * @return 完全创建成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return makeDirectory(file);
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 * @since 0.1
	 */
	public static boolean emptyDirectory(File directory) {
		boolean result = false;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return true;
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directoryName
	 *            要清空的目录的目录名
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean emptyDirectory(String directoryName) {
		File dir = new File(directoryName);
		return emptyDirectory(dir);
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * 
	 * @param dirName
	 *            要删除的目录的目录名
	 * @return 删除成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean deleteDirectory(String dirName) {
		return deleteDirectory(new File(dirName));
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * 
	 * @param dir
	 *            要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}

		File[] entries = dir.listFiles();
		int sz = entries.length;

		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}

		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	/**
	 * 列出目录中的所有内容，包括其子目录中的内容。
	 * 
	 * @param file
	 *            要列出的目录
	 * @param filter
	 *            过滤器
	 * @return 目录内容的文件数组。
	 * @since 0.1
	 */
	public static File[] listAll(File file,
			javax.swing.filechooser.FileFilter filter) {
		ArrayList list = new ArrayList();
		File[] files;
		if (!file.exists() || file.isFile()) {
			return null;
		}
		list(list, file, filter);
		files = new File[list.size()];
		list.toArray(files);
		return files;
	}

	/**
	 * 将目录中的内容添加到列表。
	 * 
	 * @param list
	 *            文件列表
	 * @param filter
	 *            过滤器
	 * @param file
	 *            目录
	 */
	private static void list(ArrayList list, File file,
			javax.swing.filechooser.FileFilter filter) {
		if (filter.accept(file)) {
			list.add(file);
			if (file.isFile()) {
				return;
			}
		}
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				list(list, files[i], filter);
			}
		}
	}

	/**
	 * 返回文件的URL地址。
	 * 
	 * @param file
	 *            文件
	 * @return 文件对应的的URL地址
	 * @throws MalformedURLException
	 * @since 0.4
	 * @deprecated 在实现的时候没有注意到File类本身带一个toURL方法将文件路径转换为URL。 请使用File.toURL方法。
	 */
	public static URL getURL(File file) throws Exception {
		String fileURL = "file:/" + file.getAbsolutePath();
		URL url = new URL(fileURL);
		return url;
	}

	/**
	 * 从文件名得到文件绝对路径。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 对应的文件路径
	 * @since 0.4
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
	 * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
	 * 
	 * @param filePath
	 *            转换前的路径
	 * @return 转换后的路径
	 * @since 0.4
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace('\\', '/');
	}

	/**
	 * 从文件名得到UNIX风格的文件绝对路径。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 对应的UNIX风格的文件路径
	 * @since 0.4
	 * @see #toUNIXpath(String filePath) toUNIXpath
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXpath(file.getAbsolutePath());
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param file
	 *            文件
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的名字部分
	 * @since 0.5
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
	 * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 父路径，不存在或者已经是父目录时返回""
	 * @since 0.5
	 */
	public static String getPathPart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return "";
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				return "";
			} else {
				return fileName.substring(0, secondPoint);
			}
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中首次出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathIndex(String fileName) {
		int point = fileName.indexOf('/');
		if (point == -1) {
			point = fileName.indexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置后首次出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		int point = fileName.indexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.indexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathLsatIndex(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1) {
			point = fileName.lastIndexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathLsatIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 将文件名中的类型部分去掉。
	 * 
	 * @param filename
	 *            文件名
	 * @return 去掉类型部分的结果
	 * @since 0.5
	 */
	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 * 
	 * @param pathName
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 * @since 0.5
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	/**
	 * 删除指定路径的文件
	 * 
	 * @param filename
	 *            要删除的文件路径
	 * @return 删除成功时返回true，否则返回false
	 */
	public static boolean deleteFile(String filename) {
		if(isFileExist(filename)==true){
			return deleteFile(new File(filename));
		}else{
			return false;
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param file
	 *            要删除的文件
	 * @return 删除成功时返回true，否则返回false。
	 */
	public static boolean deleteFile(File file) {
		if ((file == null) || file.isDirectory()) {
			throw new IllegalArgumentException("Argument " + file
					+ " is a directory. ");
		}
		return file.delete();
	}

	/**
	 * 将xml规范化并写为文件
	 * 
	 * @param doc
	 * @param targetFile
	 * @throws IOException
	 */
	public static void writeXML(Document doc, String targetFile)
			throws IOException {
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setIndent(true);
		of.setNewlines(true);
		org.dom4j.io.XMLWriter xw = new org.dom4j.io.XMLWriter(new FileWriter(
				targetFile), of);
		xw.write(doc);
		xw.flush();
	}
	/**
	 * 根据文件名，返回文件小图路径，
	 * 如：20170109151434_1483946074110_p.jpg
	 * 返回\small\2017\20170109\20170109151434_1483946074110_p.jpg
	 * @param fileName
	 * @return
	 */
	public static String getSamllPathByName(String fileName,HttpServletRequest request) {
		
		if(SessionUtils.geLangType(request)==1){
			return  CommonConstant.EN+CommonConstant.doubleSprit+CommonConstant.SMALL+CommonConstant.doubleSprit+
					fileName.substring(0, 4)+CommonConstant.doubleSprit+
					fileName.substring(0, 8)+CommonConstant.doubleSprit+fileName;
		}else{
			return  CommonConstant.ZH+CommonConstant.doubleSprit+CommonConstant.SMALL+CommonConstant.doubleSprit+
					fileName.substring(0, 4)+CommonConstant.doubleSprit+
					fileName.substring(0, 8)+CommonConstant.doubleSprit+fileName;
		}
	}

	/**
	 * 根据文件名，返回文件水印中图路径，
	 * 如：20170109151434_1483946074110_p.jpg
	 * 返回  zh 或  en   \small\2017\20170109\20170109151434_1483946074110_p.jpg
	 * @param fileName
	 * @return
	 */
	public static String getWMPathByName(String fileName,HttpServletRequest request) {
		if(SessionUtils.geLangType(request)==1){
			return  CommonConstant.EN+CommonConstant.doubleSprit+CommonConstant.WATERMEDIUM+CommonConstant.doubleSprit+
					fileName.substring(0, 4)+CommonConstant.doubleSprit+
					fileName.substring(0, 8)+CommonConstant.doubleSprit+fileName;
		}else{
			return  CommonConstant.ZH+CommonConstant.doubleSprit+CommonConstant.WATERMEDIUM+CommonConstant.doubleSprit+
					fileName.substring(0, 4)+CommonConstant.doubleSprit+
					fileName.substring(0, 8)+CommonConstant.doubleSprit+fileName;
		}
	}
	/**
	 * 根据文件名，返回水印图路径，
	 * 如：20170109151434_1483946074110_p.jpg
	 * 返回\watermark\20170109151434_1483946074110_p.jpg
	 * @param fileName
	 * @return
	 */
	public static String getWaterPathByName(String fileName) {
		return CommonConstant.doubleSprit+CommonConstant.WATER+CommonConstant.doubleSprit+fileName;
	}
	
	public static String getReplacePathByName(String fileName,String replaceName,String ipAdd,String typeName,HttpServletRequest request){
		if(SessionUtils.geLangType(request)==1){
			return fileName.replace(replaceName,ipAdd+CommonConstant.oneSprit+CommonConstant.EN+typeName);
		}else{
			return fileName.replace(replaceName,ipAdd+CommonConstant.oneSprit+CommonConstant.ZH+typeName);
		}

	}
	 /**
     * 根据文件名和类型，返回相应文件地址
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月6日 上午10:43:07
     * @param fileName 文件名
     * @param request
     * @param fileSize 文件大小(1:400,2:800,3:1200,4:3000)
     * @return
     */
    public static String getPathByNameAndSize(String fileName,
            HttpServletRequest request, Integer fileSize) {

        // 根据图片大小取位置，默认取最小图
        String sType = CommonConstant.SMALL;
        switch (fileSize) {
        // case 1://小图
        // break;
        case 2:// 中图
            sType = "small400";
            break;
        case 3:// 中图small400
            sType = "medium";
            break;
        case 4:// 1200中图
            sType = "medium1200";
            break;
        case 5:// 大图
            sType = "big";
            break;

        }

        if (SessionUtils.geLangType(request) == 1) {
            return CommonConstant.EN + CommonConstant.doubleSprit + sType
                    + CommonConstant.doubleSprit + fileName.substring(0, 4)
                    + CommonConstant.doubleSprit + fileName.substring(0, 8)
                    + CommonConstant.doubleSprit + fileName;
        } else {
            return CommonConstant.ZH + CommonConstant.doubleSprit + sType
                    + CommonConstant.doubleSprit + fileName.substring(0, 4)
                    + CommonConstant.doubleSprit + fileName.substring(0, 8)
                    + CommonConstant.doubleSprit + fileName;
        }
    }
    /**
     * 根据文件名和大小，返回文件水印中图路径
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月6日 上午11:06:41
     * @param fileName
     * @param request
     * @param fileSize 图片大小（1:800；2:1200）
     * @return
     */
    public static String getWMPathByNameAndSize(String fileName,HttpServletRequest request, Integer fileSize) {
        // 根据图片大小取位置，默认取最小图
        String sType = fileSize==2?"watermarkedmedium1200":CommonConstant.WATERMEDIUM;
        
        if(SessionUtils.geLangType(request)==1){
            return  CommonConstant.EN+CommonConstant.doubleSprit+sType+CommonConstant.doubleSprit+
                    fileName.substring(0, 4)+CommonConstant.doubleSprit+
                    fileName.substring(0, 8)+CommonConstant.doubleSprit+fileName;
        }else{
            return  CommonConstant.ZH+CommonConstant.doubleSprit+sType+CommonConstant.doubleSprit+
                    fileName.substring(0, 4)+CommonConstant.doubleSprit+
                    fileName.substring(0, 8)+CommonConstant.doubleSprit+fileName;
        }
    }
    
    
    /**
     * @author xiayunan
     * @description 模糊查询文件夹中包含字符串的文件
     * @param path
     * @param str
     * @return
     */
    public static boolean delFilesByPath(String path,String str){
    	  boolean b=false;
    	  File file = new File(path);
    	  
    	  File[] tempFile = file.listFiles();
    	  System.out.println("tempFile.length:"+tempFile.length);
    	  for(int i = 0; i < tempFile.length; i++){
    	   if(tempFile[i].getName().startsWith(str)||tempFile[i].getName().endsWith(str)){
    	    boolean del=deleteFile(path+tempFile[i].getName());
    	    if(del){
    	     b=true;
    	    }
    	   }
    	  }
    	  return b;
    	 }
    
    
   
}
