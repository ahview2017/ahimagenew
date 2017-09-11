package com.test.java;

import java.io.IOException;

import org.apache.commons.lang.NumberUtils;
import org.im4java.core.CompositeCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

/**
 * 测试水印图片位置
 * @author xiayunan
 * @date   2017年9月11日
 *
 */
public class TestWaterPicPostion {
	public static void main(String[] args) {
//		String srcPath = "E:\\timg.jpg";
//		String distPath = "E:\\new.jpg";
//		String waterPath = "E:\\water1.png";
//		try {
//			watermarkImg(srcPath,distPath,waterPath,150,150,614,408,100);
//			System.out.println("生成水印图片成功");
//		} catch (IOException | InterruptedException | IM4JavaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println((int)(1*891));

	}
	
	 /** 
     * 添加图片水印 
     * @param srcPath       原图片路径 
     * @param distPath      新图片路径 
     * @param watermarkImg      水印图片路径 
     * @param width     水印宽度（可以于水印图片大小不同） 
     * @param height    水印高度（可以于水印图片大小不同） 
     * @param x     水印开始X坐标 
     * @param y     水印开始Y坐标 
     * @param alpha     透明度[0-100] 
     * @throws IOException 
     * @throws InterruptedException 
     * @throws IM4JavaException 
     */ 
	 private static  void watermarkImg(String srcPath,String distPath,String watermarkImg, int width, int height, int x, int y, int alpha) throws IOException, InterruptedException, IM4JavaException{  
	        CompositeCmd cmd = new CompositeCmd(true);   
	        cmd.setSearchPath("D:\\ProgramFiles\\GraphicsMagick\\GraphicsMagick-1.3.18-Q8");
	        IMOperation op = new IMOperation();  
	        op.dissolve(alpha);  
	        op.geometry(width, height, x, y);
	        op.addImage(watermarkImg);    
	        op.addImage(srcPath);    
	        op.addImage(distPath);   
	        
	        cmd.run(op);  
	    }    
}
