package com.ue.ems.common.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ue.ems.common.util.HexUtil;


/**
 * md5加密
 * 
 * @author leo
 * */
public final class MD5 {

	private static final Log log = LogFactory.getLog(MD5.class);

	/** 数据编码方式 */
	private static final String ENCODE = "UTF-8";

	/** 加密算法 */
	private static final String ALGORITHM = "MD5";

	/**
	 * 返回明文的md5摘要
	 * 
	 * @param text
	 *            明文
	 * @return 返回text的md5摘要
	 */
	private static final String md5(String text) {
		String value = null;
		try {
			byte[] strTemp = text.getBytes(ENCODE);
			MessageDigest mdTemp = MessageDigest.getInstance(ALGORITHM);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			value = HexUtil.toHexString(md);
		} catch (NoSuchAlgorithmException e) {
			log.error("执行加密算[" + ALGORITHM + "]法异常!", e);
		} catch (Exception e) {
			log.error(e);
		}
		
		return value;
	}

	/**
	 * 32位的md5
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String md5for32(String text) {
		String ptext = md5(text);
		if(log.isInfoEnabled()){
			log.info("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}

	/**
	 * 有的系统使用的是，该方法返回16位的md5。
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String md5for16(String text) {
		String ptext = md5(text).substring(8, 24);
		if(log.isInfoEnabled()){
			log.info("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
}