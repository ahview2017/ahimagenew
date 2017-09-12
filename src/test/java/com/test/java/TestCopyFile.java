package com.test.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.deepai.photo.common.util.image.ImgFileUtils;

public class TestCopyFile {
	public static void main(String[] args) {
		String txtPath = "D:/trsphoto/zh/classification/2017/20170829/20170829001102a.txt";
		String str = "D:/trsphoto/temp/201709121625540692-5/";
		try {
			ImgFileUtils.copyFile(txtPath, str);
			System.out.println("复制成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
