package com.deepai.photo.common.util;

import com.deepai.photo.common.util.encrypt.MD5;


public class UUIDGenerator {
	/**
	 * 移动端登录后，生成一个登录ID 
	 * 生成一个UUID， 每天，每个人不同
	 * @param account
	 * @return
	 */
	public static String generate(String account) {
		String result = MD5.getMD5Str(MD5.getMD5Str(String.valueOf(System.currentTimeMillis()))+MD5.getMD5Str(account));
		return result;
	}
	public static void main(String[] args) {
		String md5Str = MD5.getMD5Str(MD5.getMD5Str("1"));
		System.out.println(md5Str);
	}
}
