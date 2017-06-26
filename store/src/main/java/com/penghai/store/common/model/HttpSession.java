package com.penghai.store.common.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.penghai.store.util.common.CommonData.CM_JEDIS_DATA;
import com.penghai.store.util.common.JedisUtils;

import redis.clients.jedis.Jedis;

/**
 * 封装Session会话
 * 
 * @author 李浩
 * @date 2017年4月24日
 */
@Component
public class HttpSession {
	// 获取日志对象
	Logger log = Logger.getLogger(HttpSession.class);
	// 获取jedis操作类
	@Autowired
	private JedisUtils jedisUtils;
	// 有效时间
	private int timeOut = CM_JEDIS_DATA.SESSION_TIMEOUT;
	// sessionId
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	// 构造方法1（默认）
	private HttpSession() {

	}

	// 构造方法2(带参数)
	private HttpSession(String sessionId, int timeOut) {
		this.timeOut = timeOut;
		this.sessionId = sessionId;
	}

	// 注销session
	public void abort(String sessionId) {
		Jedis jedis = null;
		try {
			jedis = jedisUtils.getJedis();
			log.info("注销sessionId前，查看" + sessionId + "状态：" + String.valueOf(jedis.exists("sessionId")));
			jedis.del(sessionId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	// set方法
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisUtils.getJedis();
			jedis.hset(this.sessionId, key, String.valueOf(value));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	// 删除sessionId下多个fields
	@SuppressWarnings("finally")
	public int hdel(String... fields) {
		Jedis jedis = null;
		int result = 0;
		try {
			jedis = jedisUtils.getJedis();
			result = Integer.parseInt(String.valueOf(jedis.hdel(this.sessionId, fields)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
			return result;
		}
	}

	// 简化对jedis的频繁开关操作
	public void setMap(Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = jedisUtils.getJedis();
			for (String key : map.keySet()) {
				String value = map.get(key);
				jedis.hset(this.sessionId, key, String.valueOf(value));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	// 返回所有session信息
	public Map<String, String> getAll() {
		Map<String, String> map = new HashMap<String, String>();
		Jedis jedis = null;
		try {
			jedis = jedisUtils.getJedis();
			map = jedis.hgetAll(this.sessionId);
			return map;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return map;
		} finally {
			if (jedis != null)
				jedis.close();
		}

	}

	// 根据属性name 取session信息
	public String get(String name) {
		Jedis jedis = null;
		String value = "";
		try {
			jedis = jedisUtils.getJedis();
			if (name != null && !name.equals("")) {
				value = String.valueOf(jedis.hget(this.sessionId, name));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			value = "";
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return value;

	}

	// 更新session过期时间
	public boolean upDate(String sessionId) {
		Jedis jedis = null;
		boolean upDateFalg = true;
		String upDateJedis = "0";
		try {
			jedis = jedisUtils.getJedis();
			log.info("更新sessionId前，查看" + sessionId + "状态：" + String.valueOf(jedis.exists("sessionId")));
			this.sessionId = sessionId;
			upDateJedis = String.valueOf(jedis.expire(this.sessionId, this.timeOut));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		log.info("更新sessionId为：" + sessionId + "的返回值是：" + String.valueOf(jedis.exists("sessionId")));
		if (upDateJedis.equals("0")) {
			upDateFalg = false;
		}
		return upDateFalg;
	}

	// 无参数创建session
	public void creatSession() {
		int sessionLen = CM_JEDIS_DATA.SESSION_LEN;
		String sessionId = sessionRandom(sessionLen);
		this.sessionId = sessionId;
		Jedis jedis = null;
		try {
			jedis = jedisUtils.getJedis();
			Long flag = jedis.expire(sessionId, this.timeOut);
			log.info("创建sessid为：" + sessionId + ",并设置过期时间返回值为：" + flag);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}

	}

	// 带参数创建session
	public void creatSession(String sessionId) {
		Jedis jedis = null;
		try {
			jedis = jedisUtils.getJedis();
			this.sessionId = sessionId;
			jedis.expire(sessionId, this.timeOut);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	// 生成随机session
	public static String sessionRandom(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String strfirst = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		int firstNum = random.nextInt(52);
		char firSt = strfirst.charAt(firstNum);
		StringBuffer sb = new StringBuffer();
		sb.append(firSt);
		for (int i = 1; i < length; i++) {

			int number = random.nextInt(62);

			sb.append(str.charAt(number));
		}
		return sb.toString();

	}

}
