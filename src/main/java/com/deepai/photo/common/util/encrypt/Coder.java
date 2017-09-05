package com.deepai.photo.common.util.encrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基础加密组件
 * 
 */
public abstract class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_SHA256 = "SHA-256";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptBASE64(String key) throws Exception {
		return  new String((new BASE64Decoder()).decodeBuffer(key));
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(String key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key.getBytes());
	}

	public static String reverse(String s)throws Exception{
		return new StringBuffer(s).reverse().toString();
	}

	/*** 
	 * MD5加码 生成32位md5码 
	 */  
	public static String string2MD5(String inStr){  
		MessageDigest md5 = null;  
		try{  
			md5 = MessageDigest.getInstance("MD5");  
		}catch (Exception e){  
			System.out.println(e.toString());  
			e.printStackTrace();  
			return "";  
		}  
		char[] charArray = inStr.toCharArray();  
		byte[] byteArray = new byte[charArray.length];  

		for (int i = 0; i < charArray.length; i++)  
			byteArray[i] = (byte) charArray[i];  
		byte[] md5Bytes = md5.digest(byteArray);  
		StringBuffer hexValue = new StringBuffer();  
		for (int i = 0; i < md5Bytes.length; i++){  
			int val = ((int) md5Bytes[i]) & 0xff;  
			if (val < 16)  
				hexValue.append("0");  
			hexValue.append(Integer.toHexString(val));  
		}  
		return hexValue.toString();  

	}  
	public static void main(String[] args) throws Exception {
		String a="111111";
		String md5=new String(string2MD5(a));//MD5密码
		System.out.println(md5);
		System.out.println(encryptBASE64(reverse(md5)));//base64再次加密后
		System.out.println(reverse(decryptBASE64("MzFmMzU3NTYyMDMyOGJiZDUzZDJiNjAxM2IzOTdmNmQ=")));//base64解密后
	}
}
