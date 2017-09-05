package com.deepai.photo.common.redis;

public interface DefaultRedisManager {
	/**
	 * 添加
	 * @param account 用户ID
	 */
	String add(String account);
	
	/**
	 * 据值取键，取出的键为身份标识+用户ID
	 * @param uuid
	 * @return
	 */
	String getKey(String value);
	/**
	 * 获取值(UUID_UUID字符串)
	 * @param account 用户ID
	 * @return
	 */
	String get(String account);
	
	/**
	 * 移除
	 * @param account 用户ID
	 * @return
	 */
	boolean remove(String account);
	
	
}
