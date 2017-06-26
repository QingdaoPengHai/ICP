package com.penghai.css.storage.business.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.penghai.css.management.business.XMLDatabaseTransferBusinessI;
import com.penghai.css.storage.business.IStorageScheduleBusiness;
import com.penghai.css.storage.business.StorageBusinessI;
import com.penghai.css.util.JedisUtils;
import com.penghai.css.util.MongoUtil;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;

import redis.clients.jedis.Jedis;
/**
 * 存储数据模块定时器实现类
 * @author 徐超
 * @Date 2017年5月26日 上午12:11:23
 */
public class StorageScheduleBusinessImpl implements IStorageScheduleBusiness{

	//redis
	@Autowired
	private JedisUtils jedisUtils = null;
	//每次从redis中取数据的条数
	private static final int dataNumber = Integer.parseInt(CM_CONFIG_PROPERTIES.MAX_CACHE_NUMBER);
	//在redis中存储数据的key名称
	private static final String mqToredisListDataKey = CM_CONFIG_PROPERTIES.MQ_DATA_KEY_NAME;
	Logger log = Logger.getLogger(StorageScheduleBusinessImpl.class);
	
	//注入使用的服务接口
	@Autowired
	private StorageBusinessI storageBusinessI;
	@Autowired
	private XMLDatabaseTransferBusinessI xmlDatabaseTransferBusinessI;
	@Autowired
	private MongoUtil mongoUtil;
	/**
	 * 使用Quartz定时器实现的读取redis并存储数据实现方法
	 * @author 徐超
	 * @Date 2017年5月25日 下午11:21:28
	 */
	public void quartzSaveDatasToDatabase(){
		Jedis jedis = null;
		try {
			//获取redis对象
			jedis = jedisUtils.getJedis();
			//从redis中获取指定数量的数据
			List<String> mqJsonDatasStringList = jedis.lrange(mqToredisListDataKey, 0, dataNumber-1);
			//判断若key是否存在
			if(null!=mqJsonDatasStringList && 0!=mqJsonDatasStringList.size()){
				//若有数据
				//1.删除已取出的数据(通过ltrim命令,保存剩下的数据,-1表示最后一条)
				jedis.ltrim(mqToredisListDataKey, mqJsonDatasStringList.size(), -1);
				//2.将List<JSONObject>转为JSONObject,包含库名以及数据列表List<Map>
				
				JSONObject dataJSONObject = storageBusinessI.transferListJsonToListDBObject(mqJsonDatasStringList);
				//3.将List<DBObject>写入MongoDB
				storageBusinessI.insertDatasIntoMongoDB(dataJSONObject);
				/*//2.将List转为sql
				String sql = storageBusinessI.transferBulkJsonListToSql(mqJsonDatasStringList);
				//3.执行sql
				if(!"".equals(sql)){
					List<LinkedHashMap<String, Object>> result = xmlDatabaseTransferBusinessI.executeSQL(sql);
				}else{
					//sql为空,什么都不做
					log.error("======生成的sql为空======");
				}*/
			}else{
				//不存在,什么都不做
				log.error("======redis暂时为空======");
			}
		} catch (Exception e) {
			log.error("======数据转换出错======", e);
		}finally {
			if (jedis != null)
				jedis.close();
		}
	}
}
