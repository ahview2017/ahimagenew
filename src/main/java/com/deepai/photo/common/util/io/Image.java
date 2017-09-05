package com.deepai.photo.common.util.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

public class Image {
	public static void main(String[] args) {

		try {
			String filePath="D:\\work4\\image\\3.jpg";
			Image.localImage(filePath);
			System.out.println("==============");
			String urlPath="http://huzhu-pic.oss-cn-beijing.aliyuncs.com/1460342337145.jpg";
			Image.lineImage(urlPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//本地图片
	public static void localImage(String filePath)throws FileNotFoundException, IOException {
		File picture = new File(filePath);
		BufferedImage sourceImg =ImageIO.read(new FileInputStream(picture)); 
//		System.out.println(String.format("%.1f",picture.length()/1024.0));
		System.out.println(sourceImg.getWidth());
		System.out.println(sourceImg.getHeight());
	}

	//网络图片
	public static void lineImage(String urlPath) throws IOException{
		URL url = new URL(urlPath);  
		URLConnection con = url.openConnection();  
		//不超时  
		con.setConnectTimeout(0);  

		//不允许缓存  
		con.setUseCaches(false);  
		con.setDefaultUseCaches(false);  
		InputStream is = con.getInputStream();  

		//先读入内存  
		ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);  
		byte[] b = new byte[1024];  
		int len;  
		while ((len = is.read(b)) != -1) {  
			buf.write(b, 0, len);  
		}  
		//读图像  
		is=new ByteArrayInputStream(buf.toByteArray());  
		BufferedImage image = ImageIO.read(is);  

		System.out.println(image); 
		System.out.println(image.getWidth());
		System.out.println(image.getHeight());
	}
}
