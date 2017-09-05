package com.deepai.photo.controller.picture;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.util.image.ImageAnalyseUtil;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

public class PicTest {
	public static void main(String[] args) throws Exception {
		/*File jpegFile = new File("D:/work4/image/222/IMG_0569.JPG");
		Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
		Directory exif = metadata.getDirectory(ExifDirectory.class);
		Iterator tags = exif.getTagIterator();
		while (tags.hasNext()) {
			Tag tag = (Tag)tags.next();
			System.out.println(tag);
		}
		IMOperation op = new IMOperation();
		op.format("%w#%h#%[EXIF:DateTimeOriginal]");
		op.addImage("D:/work4/image/222/IMG_0486.JPG");
		IdentifyCmd identifyCmd = new IdentifyCmd(true);
		ArrayListOutputConsumer output = new ArrayListOutputConsumer();
		identifyCmd.setOutputConsumer(output);
		identifyCmd.run(op);
		ArrayList<String> out = output.getOutput();
		String wNh = null;
		if (out.size() > 0) {
			wNh = out.get(0);
//			int width = Integer.parseInt(wNh.split("#")[0]);
//			int height = Integer.parseInt(wNh.split("#")[1]);
//			System.out.println(width);
//			System.out.println(height);
			System.out.println(wNh);
		}*/
 
		String waterPic="D:/trsphoto/a-gyh/waterMark.png";//水印图
		String pic="D:/trsphoto/medium/2017/20170218/20170218145807_1487401087600_p.jpg";//原图
		String position="southeast";//水印位置
		String wmAllPath="D:/trsphoto/watermarkedmedium/2017/20170218/20170218145807_1487401087600_p.jpg";//输出水印图
		
		ImageAnalyseUtil.waterMarkPic(wmAllPath,pic, waterPic, position,true);
		System.out.println("------...");
		
		/*String root="D:/trsphoto/watermark\\";
		String fileName="20170218120523_1487390723190_p.jpg";
		String a=initFullPathByOrderStatic(root, fileName);
		System.out.println(a);*/
	}
	
	public static String initFullPathByOrderStatic(String root, String fileName) {
		String tempp = root;
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			if (tempp.lastIndexOf(CommonConstant.oneSprit) != tempp.length() - 1) {
				tempp += CommonConstant.oneSprit;
			}
			if (tempp.lastIndexOf(CommonConstant.doubleSprit) == tempp.length() - 1) {
				tempp=tempp.substring(0, tempp.length()-1);
				tempp += CommonConstant.oneSprit;
			}
			tempp=tempp + fileName.substring(0, 4) + CommonConstant.oneSprit
					+ fileName.substring(0, 8) + CommonConstant.oneSprit + fileName;
		}else{
			if (tempp.lastIndexOf(CommonConstant.oneSprit) != tempp.length() - 1) {
				tempp += CommonConstant.oneSprit;
			}
			tempp=tempp + fileName.substring(0, 4) + CommonConstant.oneSprit
					+ fileName.substring(0, 8) + CommonConstant.oneSprit + fileName;
		}
		return tempp;
	}
}
