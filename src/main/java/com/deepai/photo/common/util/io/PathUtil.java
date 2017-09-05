package com.deepai.photo.common.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class PathUtil {
	/*
	 * 获取classpath1
	 */
	public static String getClasspath(){
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	/*
	 * 获取classpath2
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File(getClassResources() + "template/"+"template_single_line.pdf");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		if((line = bufferedReader.readLine()) != null){
			System.out.println(line);
		}
		fileReader.close();
		
		System.out.println(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")));
		System.out.println(getClasspath());
		System.out.println(getClassResources());
	}
}
