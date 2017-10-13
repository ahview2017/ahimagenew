package com.test.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.deepai.photo.common.util.image.ImgFileUtils;

public class TestCopyFile {
	public static void main(String[] args) {
//		String txtPath = "D:/trsphoto/zh/classification/2017/20170829/20170829001102a.txt";
//		String str = "D:/trsphoto/temp/201709121625540692-5/";
//		try {
//			ImgFileUtils.copyFile(txtPath, str);
//			System.out.println("复制成功");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String inName = "F:\\TRSWCMApp.ini";
		String outName = "F:\\TRSWCMApp2.ini";
		System.out.println("开始复制");
		String charset = "utf-8";
		System.out.println("结束复制");
		try {
			copyFile(inName,outName,charset);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
