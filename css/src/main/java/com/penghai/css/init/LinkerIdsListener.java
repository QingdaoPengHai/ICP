package com.penghai.css.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.penghai.css.util.CommonData.CM_LINKER_REDIS_PROPERTIES;
import com.penghai.css.util.JedisUtils;

import redis.clients.jedis.Jedis;

/**
 * 系统部署时自动将linkerIds信息存储到Redis中去
 * @author 秦超
 *
 */
public class LinkerIdsListener implements ServletContextListener{
	
	@Autowired
	JedisUtils jedisUtils;
	/**
	 * 部署时自动将linkerIds信息存储到Redis中去
	 */
	@Override  
    public void contextInitialized(ServletContextEvent context) {  
        System.out.println("================>[LinkerIdsListener]开始往Redis中存入允许链接访问的linkerId信息...");  
        // 读取Spring容器中的Bean[此时Bean已加载,可以使用]
        String linkerIds = CM_LINKER_REDIS_PROPERTIES.LINKER_IDS;
        String linkerRedisKey = CM_LINKER_REDIS_PROPERTIES.LINKER_KEY;
        String redisHome = CM_LINKER_REDIS_PROPERTIES.REDIS_HOME;
        int redisPort = Integer.valueOf(CM_LINKER_REDIS_PROPERTIES.REDIS_PORT);
//        int linkerRedisExpiration = Integer.valueOf(CM_LINKER_REDIS_PROPERTIES.LINKER_KEY_EXPIRATION);
        
        Jedis jedis = null;
        try {
        	jedis = new Jedis(redisHome,redisPort);
        	
        	jedis.set(linkerRedisKey, linkerIds);
//        	jedis.expire(linkerRedisKey, linkerRedisExpiration);
		} catch (Exception e) {
			e.printStackTrace();  
		}
        
        jedis.close();
        //执行想要的代码  
        System.out.println("================>[LinkerIdsListener]往Redis中存入允许链接访问的linkerId信息结束...");  
    }  
}
