package com.penghai.linker.util;

import java.net.URL;

import com.penghai.linker.util.CommonData.CM_EHCACHE_CONFIG;

import net.sf.ehcache.Cache;  
import net.sf.ehcache.CacheManager;  
import net.sf.ehcache.Element;  

/**
 * ehcache工具类
 * @author 秦超
 * @time 2017年5月20日
 */
public class EhcacheUtil {  
	//ehcache配置xml文件
    private static final String path = CM_EHCACHE_CONFIG.EHCACHE_XML_PATH;  
    //linker系统用cache名称
    private static final String cacheName = CM_EHCACHE_CONFIG.LINKER_EHCACHE_NAME;  
  
    private URL url;  
    private CacheManager manager;  
    private static EhcacheUtil ehCache;  
    
    private EhcacheUtil(String path) {  
        url = EhcacheUtil.class.getClassLoader().getResource(path);  
        manager = CacheManager.create(url);  
    }  
    /**
     * 获取缓存实例
     * @author 秦超
     * @return
     * @time 2017年5月22日
     */
    public static EhcacheUtil getInstance() {  
        if (ehCache== null) {  
            ehCache= new EhcacheUtil(path);  
        }  
        return ehCache;  
    }  
    /**
     * 往缓存中存放值
     * @author 秦超
     * @param key
     * @param value
     * @time 2017年5月22日
     */
    public void put(String key, Object value) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = new Element(key, value);  
        cache.put(element);  
    }  
    /**
     * 获取缓存中某个值
     * @author 秦超
     * @param key
     * @return
     * @time 2017年5月22日
     */
    public Object get(String key) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = cache.get(key);  
        return element == null ? null : element.getObjectValue();  
    }  
    /**
     * 获取缓存对象
     * @author 秦超
     * @return
     * @time 2017年5月22日
     */
    public Cache getCache() {  
        return manager.getCache(cacheName);  
    }  
    /**
     * 移除缓存中某个值
     * @author 秦超
     * @param key
     * @time 2017年5月22日
     */
    public void remove(String key) {  
        Cache cache = manager.getCache(cacheName);  
        cache.remove(key);  
    }  
    /**
     * 移除缓存中所有值
     * @author 秦超
     * @param key
     * @time 2017年5月22日
     */
    public void removeAll() { 
        Cache cache = manager.getCache(cacheName);  
        cache.removeAll();  
    }  
}