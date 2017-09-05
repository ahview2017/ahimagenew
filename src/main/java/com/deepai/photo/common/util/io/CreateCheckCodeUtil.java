package com.deepai.photo.common.util.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * 生成验证码图片流
 * @author liweijie
 *
 */
public class CreateCheckCodeUtil {

	//输入参数
	private int imageWidth = 102;//图片宽度
	private int imageHeight = 28;//图片高度
	private int checkCodeCount = 4;//生成验证码位数
	private String warpFlag = "0";//扭曲状态，"0"代表不扭曲,"1"代表扭曲
	private String sourceStr = "ABCDEFGHGKLMNPQRSTUVWXY" +
						"abcdefghjkmnpqrstuvwxy" +
						"23456789";//验证码源
	
	private Random random = new Random();
	
	//输出参数
	private BufferedImage bufferImg;//验证码图片流
	private String checkCode = "";//验证码字符串
	 
	public BufferedImage createImage(String codeStr) throws Exception {
		bufferImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bufferImg.createGraphics();
        graphics.setColor(Color.WHITE);
        //填充背景颜色
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        //画边框
//        graphics.setColor(Color.BLACK); 
//        graphics.drawRect(0,0,imageWidth-1,imageHeight-1);
		
		this.setCheckCode(codeStr);
		
		for (int i = 0; i < checkCodeCount; i++) {
			graphics.setColor(createColor());
			graphics.setFont(new Font(null, Font.BOLD, 24));
			String str = checkCode.charAt(i) + "";
			graphics.drawString(str, 18 * (i+1), 22);
		}
		this.setBufferImg(disturb());
		return bufferImg;
	}

    /**
     * 生成颜色
     * @return Color
     */
	private Color createColor() {
		Color color[] = new Color[10];
		for(int i = 0; i<10; i++) {
			color[i] = new Color(random.nextInt(255),
					random.nextInt(255),random.nextInt(255));
		}
		return color[random.nextInt(10)];
	}

	/**
	 * 加入干扰效果,生成最终图片
	 * @return
	 */
	private BufferedImage disturb() {
		drawDisturbLine(bufferImg.createGraphics());
		if("0".equals(warpFlag)) {
			return bufferImg;
		} else {
			return twistImage();
		}
	}

	/**
	 * 干扰点，干扰线
	 */
	private void drawDisturbLine(Graphics2D graphics) {
		graphics.setColor(Color.BLACK);
		//添加干扰线
		for(int i=0; i<2; i++){
			graphics.drawLine(random.nextInt(imageWidth),random.nextInt(imageHeight),
						random.nextInt(imageWidth),random.nextInt(imageHeight));
		}
		//添加干扰点
		for (int i=0;i<4;i++) {
		    int x = random.nextInt(imageWidth);
		    int y = random.nextInt(imageHeight);
		    graphics.drawOval(x,y,1,1);
		}
	}

	/**
	 * 扭曲
	 * @return
	 */
	private BufferedImage twistImage() {
		double dMultValue = random.nextInt(7) + 3;
        double dPhase = random.nextInt(6);
        BufferedImage warpImage = new BufferedImage(bufferImg.getWidth(),
                bufferImg.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < warpImage.getWidth(); i++) {
            for (int j = 0; j < warpImage.getHeight(); j++) {
                int nOldX = getXPosition4Twist(dPhase, dMultValue,
                		warpImage.getHeight(), i, j);
                int nOldY = j;
                if (nOldX >= 0 && nOldX < warpImage.getWidth() && nOldY >= 0
                        && nOldY < warpImage.getHeight()) {
                	warpImage.setRGB(nOldX, nOldY, bufferImg.getRGB(i, j));
                }
            }
        }

        return warpImage;
	}

	private int getXPosition4Twist(double dPhase, double dMultValue,
            int height, int xPosition, int yPosition) {
        double PI = 3.1415926535897932384626433832799;
        double dx = (double) (PI * yPosition) / height + dPhase;
        double dy = Math.sin(dx);
        return xPosition + (int) (dy * dMultValue);
    }

	/**
	 * 生成验证码
	 * @param size
	 * @return
	 */
	public String randomNumber(int size) {
		for(int i = 0; i<size; i++) {
			checkCode += sourceStr.charAt(random.nextInt(sourceStr.length()));
		}
		return checkCode;
	}

	
	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getCheckCodeCount() {
		return checkCodeCount;
	}

	public void setCheckCodeCount(int checkCodeCount) {
		this.checkCodeCount = checkCodeCount;
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		if("".equals(sourceStr)) {
			return;
		}
		this.sourceStr = sourceStr;
	}

	public String getWarpFlag() {
		return warpFlag;
	}

	public void setWarpFlag(String warpFlag) {
		if(!"0".equals(warpFlag)||!"1".equals(warpFlag)) {
			return;
		}
		this.warpFlag = warpFlag;
	}

	public BufferedImage getBufferImg() {
		return bufferImg;
	}

	private void setBufferImg(BufferedImage bufferImg) {
		this.bufferImg = bufferImg;
	}

	public String getCheckCode() {
		return checkCode;
	}

	private void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

}
