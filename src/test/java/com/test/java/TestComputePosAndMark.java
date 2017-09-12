package com.test.java;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.deepai.photo.common.util.image.ImageAnalyseUtil;

public class TestComputePosAndMark {
	public static void main(String[] args) {
		int isWaterMark = 0;
		int position = 10;
		String wMPath= "D://111.png";
		BufferedImage waterPic = null;
		BufferedImage alterdImage = null;
		BufferedImage finalImage =  null;
		try {
			alterdImage = ImageIO.read(new FileInputStream("D:\\20170829001102a.jpg"));
			alterdImage = ImageAnalyseUtil.resize(alterdImage, 800);
			//水印图
			finalImage = ImageAnalyseUtil.computePosAndMark(isWaterMark, position, wMPath, waterPic, alterdImage);
			//将图片保存为JPG格式
			ImageAnalyseUtil.saveJPG(finalImage, "D:\\111111.jpg");
			System.out.println("保存成功！！");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
