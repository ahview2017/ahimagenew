package com.deepai.photo.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.redis.DefaultRedisManagerImpl;
import com.deepai.photo.common.redis.RedisClientTemplate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
 
import javax.imageio.ImageIO;
@Controller
public class VilidateUtil {
	/**
	 * 验证码生成
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("yanzheng")
	@SkipLoginCheck
	@SkipAuthCheck
	public void execute(
			HttpServletResponse response,
			HttpSession session) throws Exception{
			//0.创建空白图片
			BufferedImage image = new BufferedImage(60,26,BufferedImage.TYPE_INT_RGB);
			//1.获取图片画笔
			Graphics g = image.getGraphics();
			Random r = new Random();
			//2.设置画笔颜色
			g.setColor(Color.WHITE);
			//3.绘制矩形的背景
			g.fillRect(0, 0, 60, 26);
			//4.调用自定义的方法，获取长度为5的字母数字组合的字符串
			String number = getNumber(4);	
			//将图片字符存入session,用于验证码检查使用
			session.setAttribute("scode", number);
			g.setColor(new Color(0,0,0));
			g.setFont(new Font(null,Font.BOLD,18));
			//5.设置颜色字体后，绘制字符串
			g.drawString(number, 7, 19);
			//6.绘制8条干扰线
			for(int i=0;i<10;i++){
				g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255),r.nextInt(255)));
				g.drawLine(r.nextInt(100), r.nextInt(30), r.nextInt(100), r.nextInt(30));
			}
			response.setContentType("image/jpeg");
			OutputStream ops = response.getOutputStream();
			ImageIO.write(image, "jpeg", ops);
			ops.close();
		}
		
		private String getNumber(int size){
			String str = "qwertyuipkjhgfdsazxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			String number = "";
			Random r = new Random();
			for(int i=0;i<size;i++){
				number+=str.charAt(r.nextInt(str.length()));
			}
			return number;
		}
}
