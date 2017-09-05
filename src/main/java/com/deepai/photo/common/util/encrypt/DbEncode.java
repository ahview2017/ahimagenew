package com.deepai.photo.common.util.encrypt;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DbEncode {
	
	/**
	 * 将 s 进行 BASE64 编码
	 * 
	 * @param s
	 * @return
	 */
	public  final static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new BASE64Encoder()).encode(s.getBytes());
	}

	/**
	 * 将 s 进行 BASE64 解码
	 * 
	 * @param s
	 * @return
	 */
	public  final static String decodeBase64(String s) {
		if (s == null) {
			return null;
		}
		try {
			return new String(new BASE64Decoder().decodeBuffer(s));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Md5编码
	 * 
	 * @param s
	 * @return
	 */
	private  final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 编码方法，双重编码
	 * 
	 * @param codeString
	 * @return
	 */
	public  static final String Encode(String codeString) {
		return MD5(getBASE64(codeString));
	}

	/**
	 * 产生随机字符串
	 */
	public  static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = ("0123456789").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	public static final String encodeID(String id) {
		return getBASE64(id).replaceAll("=", "@");
	}

	public static final String decodeID(String code) {
		return decodeBase64(code.replaceAll("@", "="));
	}
	
	public static void main(String[] args) {
		System.out.println(getBASE64("202cb962ac59075b964b07152d234b70"));
		System.out.println(decodeBase64("MjAyY2I5NjJhYzU5MDc1Yjk2NGIwNzE1MmQyMzRiNzA="));
		
	}

}
