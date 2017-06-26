package com.penghai.css.storage.business.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.penghai.css.analysis.business.XMLDatabaseTransferBusinessI;
import com.penghai.css.storage.business.IStorageScheduleBusiness;
import com.penghai.css.storage.business.StorageBusinessI;
import com.penghai.css.util.JedisUtils;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_MYCAT_CONFIGURATION;
import com.penghai.css.util.CommonData.CM_REDIS_KEY;

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
	public static final String mycatIp = CM_MYCAT_CONFIGURATION.MYCAT_SERVER_IP;
	public static final String mycatUser = CM_MYCAT_CONFIGURATION.MYCAT_USER;
	public static final String mycatPassword = CM_MYCAT_CONFIGURATION.MYCAT_PASSWORD;
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
			String mycatDatabaseName = jedis.get(CM_REDIS_KEY.SEQUENCE_DATABASE_NAME);
			//从redis中获取指定数量的数据
			List<String> mqJsonDatasStringList = jedis.lrange(mqToredisListDataKey, 0, dataNumber-1);
			//判断若key是否存在
			if(null!=mqJsonDatasStringList && 0!=mqJsonDatasStringList.size()){
				//若有数据
				//1.删除已取出的数据(通过ltrim命令,保存剩下的数据,-1表示最后一条)
				jedis.ltrim(mqToredisListDataKey, mqJsonDatasStringList.size(), -1);
				log.error("获取到了数据");
				
				int insertNumber = storageBusinessI.getInsertNumber(mqJsonDatasStringList);
				
				long currentSequence = storageBusinessI.getAndChangeCurrentsequenceInfo(insertNumber);
				
				//2.将List转为sql
				String sql = storageBusinessI.transferBulkJsonListToSqlForMycat(mqJsonDatasStringList,currentSequence);
				
				//3.执行sql
				if(!"".equals(sql)){
//					List<LinkedHashMap<String, Object>> result = xmlDatabaseTransferBusinessI.executeSQL(sql);
					
					System.out.println("====================sql=================="+sql);
					
					sql = sql.substring(0, sql.length()-1);
					
					String[] sqlArgs = sql.split(";");
					String driver = CM_CONFIG_PROPERTIES.DATABASE_DRIVER;
					//新建连接
			    	Connection connection = DriverManager.getConnection("jdbc:mysql://"+mycatIp+"/"+mycatDatabaseName, mycatUser,mycatPassword);
			    	connection.setAutoCommit(false);
			    	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	
			    	for(int i=0;i<sqlArgs.length;i++){
			    		stmt.addBatch(sqlArgs[i]);
			    	}
			    	stmt.executeBatch();
			    	connection.commit();
			    	
			    	stmt.close();
			    	connection.close();
			    	
//			    	//新建预加载的查询语句
//		        	PreparedStatement preparedStatementSelect = connection.prepareStatement(sql);
//		        	//结果集
//		        	int resultSet = preparedStatementSelect.executeUpdate();
				}else{
					//sql为空,什么都不做
					log.error("======生成的sql为空======");
				}
			}else{
				//不存在,什么都不做
				log.error("======redis暂时为空======");
			}
		} catch (Exception e) {
			log.error("======将List转为sql报错======", e);
		}finally {
			if (jedis != null){
				jedis.close();
			}
		}
	}
}
