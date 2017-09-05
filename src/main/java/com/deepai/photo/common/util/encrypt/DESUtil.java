package com.deepai.photo.common.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtil {

	public DESUtil() {
	}

	public String encrypt(String str) {

		byte[] enc = null;
		try {
			enc = desEncrypt(str, "abcdefgh");
		} catch (Exception ex) {
		}

		return new sun.misc.BASE64Encoder().encode(enc);
	}

	public static byte[] desEncrypt(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes("UTF-8"));
	}
	public static void main(String[] args) {
		DESUtil desUtil=new DESUtil();
		String encrypt = desUtil.encrypt("中文");
		System.out.println(encrypt);
	}
}
