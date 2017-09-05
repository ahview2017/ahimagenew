package com.deepai.photo.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对系统下properties文件进行读写。
 *
 */
public class PropertiesFileUtil {
	
	private static final Logger log = LoggerFactory.getLogger(PropertiesFileUtil.class);
			
	//读取配置文件，通过key获取value.
	/**
	 * ProName property文件名称
	 * key为property文件中的key。
	 */
	public  String QueryValue(String ProName,String key){
		try {
			Properties prop = new Properties();
//			System.out.println(this.getClass().getClassLoader().getResource("").getPath());
			String path=this.getClass().getClassLoader().getResource("/").getPath().toString()+ProName;
			prop.load(new FileInputStream(path));
			//根据key值找到Value
			String value = prop.getProperty(key);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("读取Properties文件失败!文件名:"+ProName+",Key值:"+key+".异常信息:"+e.toString());
			return "";
		}
	}
	
	public static Properties getProperties(String fileName) {
		Properties prop = new Properties();
		InputStream in = PropertiesFileUtil.class.getResourceAsStream("/"
				+ fileName);
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	//写入修改properties信息
	public  boolean writeProperties(String proName, String parameterName, String parameterValue,String path) {
		Properties prop = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(proName);
			/** 从输入流中读取属性列表（键和元素对）*/
			prop.load(inputStream);
			/**关闭流*/
			inputStream.close();
			if(!prop.containsKey(parameterName)){
				log.info("key is not exist!parameterName:"+parameterName+"");
				return false;
			}
			/**将此 Properties 表中的属性列表（键和元素对）写入输出流*/
			OutputStream fos = new FileOutputStream(path);
			prop.setProperty(parameterName, parameterValue);
			prop.store(fos, "Update '" + parameterName + "' value");
			/**关闭流*/
			fos.close();
		} catch (IOException e) {
			log.error(" updating " + parameterName + " value error",e);
			return false;
		}
		log.info(" updating " + parameterName + " value success!");
		return true;
	}
	
    //登录时，获取问候语
	public static String getHello(){
		Properties pros = getProperties("hello.properties");
		int key = new Random().nextInt(pros.size());
		String name = pros.getProperty(key+"");
		if(null == name || "".equals(name)){
			name = "生命在于运动！";
			return name;
		}
		return pros.getProperty(key+"");
	}
}
