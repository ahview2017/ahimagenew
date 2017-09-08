package com.test.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestTraverseFolder {
	public static void main(String[] args) {
//		String path = "D:\\uniphoto\\199204\\";
//		traverseFolder2(path);
//		
		System.out.println(formatStr("19920410"));
		
		
		
//		String file = "1992040101.jpg";
//		System.out.println(file.substring(0, 8));
//		System.out.println(file.substring(8, 10));
	}
	
	public static void traverseFolder2(String path) {
		File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	try {
							FileInputStream fis = new FileInputStream(file2);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        System.out.println("文件夹:" + file2.getName());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                    	try {
							FileInputStream fis = new FileInputStream(file2);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        System.out.println("文件:" + file2.getName());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
	}
	
	
	public static String formatStr(String dateStr){
		StringBuilder sb = new StringBuilder();
		System.out.println(dateStr.length());
		if(dateStr.length()>=8){
			sb.append(dateStr.substring(0, 4));
			sb.append("-");
			sb.append(dateStr.substring(4,6));
			sb.append("-");
			sb.append(dateStr.substring(6,8));
		}
		return sb.toString();
	}
}
