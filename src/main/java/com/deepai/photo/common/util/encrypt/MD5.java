package com.deepai.photo.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	 public static String JM(String inStr) {   
		  char[] a = inStr.toCharArray();   
		  for (int i = 0; i < a.length; i++) {   
		   a[i] = (char) (a[i] ^ 't');   
		  }   
		  String k = new String(a);   
		  return k.toString();   
		 }   
	
	public static void main(String[] args) {
		String a="1234";
		System.out.println(getMD5Str(a));
		System.out.println(new String(JM("f379eaf3c831b04de153469d1bec345e")));
	}
}
