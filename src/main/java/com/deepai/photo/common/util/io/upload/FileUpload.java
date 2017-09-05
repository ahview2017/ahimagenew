package com.deepai.photo.common.util.io.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.common.util.io.PathUtil;

/**
 * 上传文件
 * dqqiu
 * @version
 */
public class FileUpload {

	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名
	 * @return  文件名文件名路径
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName)throws Exception{
		String extName = ""; // 扩展名格式：
		if (file.getOriginalFilename().lastIndexOf(".") >= 0){
			extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		}
		copyFile(file.getInputStream(), filePath, fileName+extName);
//		copyFile(file.getInputStream(), PathUtil.getClasspath()+filePath, fileName+extName).replaceAll("-", "");
		return filePath  + fileName + extName;
	}
	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名：带扩展名格式
	 * @return  文件名路径
	 */
	public static String fileUpName(MultipartFile file, String filePath, String fileName)throws Exception{
		copyFile(file.getInputStream(), filePath, fileName);
		return filePath  + fileName;
	}
	
	public static String fileUpNameByInputStream(InputStream in,String filePath, String fileName) throws IOException{
		copyFile(in, filePath, fileName);
		return filePath  + fileName;
	}
	
	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String dir, String realName)
			throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		} else {
			file.delete();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}
}
