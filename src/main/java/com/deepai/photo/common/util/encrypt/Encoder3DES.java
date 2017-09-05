package com.deepai.photo.common.util.encrypt;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.MessageDigest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encoder3DES {

    // Please note that 2 below methods are used in #getMD5_Base64 only
    // use them in other methods will make it not thread-safe
    private static MessageDigest digest = null;
    private static boolean isInited = false;

    private static Method encodeMethod1_4 = null;
    private static Method decodeMethod1_4 = null;

    // URLEncoder.encode(String) has been deprecated in J2SE 1.4.
    // Take advantage of the new method URLEncoder.encode(String, enc)
    // if J2SE 1.4 is used.
    static {
        try {
            Class urlEncoderClass = Class.forName("java.net.URLEncoder");
            encodeMethod1_4 = urlEncoderClass.getMethod("encode",
                    new Class[] {String.class, String.class});
        } catch (Exception ex) {} // encodeMethod1_4 will be null if exception

        try {
            Class urlDecoderClass = Class.forName("java.net.URLDecoder");
            decodeMethod1_4 = urlDecoderClass.getMethod("decode",
                    new Class[] {String.class, String.class});
        } catch (Exception ex) {} // decodeMethod1_4 will be null if exception
    }

    private Encoder3DES() {
    }



    /**
     * This method just call URLDecoder.decode() so we can get rid of
     * the deprecation warning from using URLDecoder.encode() directly.
     * @param input String
     * @return String
     */
    public static String decodeURL(String input) {
        if (decodeMethod1_4 != null) {
            Object[] methodArgsName = new Object[2];
            methodArgsName[0] = input;
            methodArgsName[1] = "UTF-8";

            try {
                return (String)decodeMethod1_4.invoke(null, methodArgsName);
            } catch (Exception ex) {
                throw new RuntimeException("System error invoking URLDecoder.decode() by reflection.");
            }
        } else {
            // must use J2SE 1.3 version

            // The following line will cause a warning if compile with jdk1.4
            // However, we cannot use the new method String decode(String s, String enc)
            // in jdk1.4, because it wont be compiled with jdk1.3
            // Currently, there is no way to get rid of this wanring
            return URLDecoder.decode(input);
        }
    }

    /**
	 * 生成3DES密钥.
	 * 
	 * @param key_byte
	 *            seed key
	 * @throws Exception
	 * @return javax.crypto.SecretKey Generated DES key
	 */
	public static javax.crypto.SecretKey genDESKey(byte[] key_byte)
			throws Exception {
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	/**
	 * 3DES加密(byte[]).
	 * 
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] desEncrypt(byte[] src) throws Exception {
		javax.crypto.SecretKey key = genDESKey("123456781234567812345678"
				.getBytes());
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(src);
	}

	/**
	 * 3DES 解密(byte[]).
	 * 
	 * @param crypt
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] desDecrypt(byte[] crypt) throws Exception {
		javax.crypto.SecretKey key = genDESKey("123456781234567812345678"
				.getBytes());
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(crypt);
	}

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static String encrypt(String pwd) {
		String code = null;
		try {
			byte[] enc = desEncrypt(pwd.getBytes("UTF-8")); 
			code = byteArr2HexStr(enc);
		} catch (Exception e) {
		    return null;
		}
		return code;
	}

	public static String decrypt(String code) {
		byte[] des = null;
		String dec = "";
		try {
			des = desDecrypt(hexStr2ByteArr(code));
			dec = new String(des,"UTF-8");
		} catch (Exception e) {
			return null;
		}
		return dec;
	}
	
	
	public static void main(String[] args){
	    System.out.println(encrypt("中文"));
	    System.out.println(decrypt(encrypt("中文")));
	}
}