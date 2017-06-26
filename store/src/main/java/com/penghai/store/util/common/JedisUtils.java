package com.penghai.store.util.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis 工具类
 * 
 * @author 李浩
 * @date 2017年4月24日
 */
@Component
public class JedisUtils {
	// 获取日志对象
	private static final Logger log = Logger.getLogger(JedisUtils.class);
	// 自动注入JedisPool
	@Autowired
	private JedisPool jedisPool;

	/*
	 * 获得JedisPool
	 */
	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	/*
	 * 释放Jedis资源
	 */
	public void releaseJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}

	}
}
