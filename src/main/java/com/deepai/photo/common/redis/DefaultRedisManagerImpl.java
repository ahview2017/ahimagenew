package com.deepai.photo.common.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.MyStringUtil;
import com.deepai.photo.common.util.UUIDGenerator;

/**
 * redis管理
 * @file DefaultRedisManagerImpl.java
 * @description
 * @author dqqiu
 * @createTime 下午2:01:27
 * @version
 * @since
 */
@Repository
public class DefaultRedisManagerImpl implements DefaultRedisManager {

	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private static String UUID="UUID_";

	@Override
	public String add(String account) {
		// 生成UUID
		String UUID = UUIDGenerator.generate(account);
		// ID 映射
		String oldUUID = redisClientTemplate.get(account);
		// 存入前先判断redis中是否已经存在此键，存在则先删除后存入
		if(redisClientTemplate.exists(account)){
			redisClientTemplate.del(account);
			redisClientTemplate.del(UUID + oldUUID);
		}
		redisClientTemplate.set(account, UUID);
		redisClientTemplate.set(UUID + UUID,account);

		return UUID;

	}

	@Override
	public String getKey(String value) {
		return redisClientTemplate.get(UUID + value);

	}

	@Override
	public boolean remove(String account) {
		// 1.根据key获取UUID
		String UUID = get(account);
		if(MyStringUtil.isEmpty(UUID)){
			return false;
		}
		// 2.移除ID与UUID的映射
		// 3.移除UUID与ID的映射
		redisClientTemplate.del(account);
		redisClientTemplate.del(UUID + UUID);
		return true;
	}

	@Override
	public String get(String account) {
		return redisClientTemplate.get(account);
	}



}
