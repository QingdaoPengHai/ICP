package com.penghai.testoficp.init;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.penghai.testoficp.analysis.business.XMLDatabaseTransferBusinessI;
import com.penghai.testoficp.storage.business.StorageBusinessI;
import com.penghai.testoficp.util.JedisUtils;
import com.penghai.testoficp.util.CommonData.CM_CONFIG_PROPERTIES;
import com.rabbitmq.client.*;

import redis.clients.jedis.Jedis;
/**
 * 初始化rabbitMQ消费者初始化
 * @author 徐超
 * @Date 2017年5月9日 上午11:42:09
 */
public class InitRabbitMQ implements ServletContextListener{
	
	//初始化各种service实例
	private XMLDatabaseTransferBusinessI xmlDatabaseTransferBusinessI = null;
	private StorageBusinessI storageBusinessI = null;
	private JedisUtils jedisUtils = null;
	//获取最大缓存条数
	public static final int maxCacheNumber = Integer.parseInt(CM_CONFIG_PROPERTIES.MAX_CACHE_NUMBER);
	//获取redis存储的key名称
	public static final String mqDataKeyName = CM_CONFIG_PROPERTIES.MQ_DATA_KEY_NAME;
	//获取MQ交换机名称
	public static final String MQExcahngeName = CM_CONFIG_PROPERTIES.EXCHANG_NAME;
	//获取MQ队列名称
	public static final String MQqueueName = CM_CONFIG_PROPERTIES.QUEUE_NAME;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		/**
		 * 获取各种对象,因为系统启动时需要加载,无法通过注入的方式引入,因此从上下文中获取该对象
		 */
		WebApplicationContext webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		xmlDatabaseTransferBusinessI=(XMLDatabaseTransferBusinessI) webApplicationContext.getBean("xmlDatabaseTransferBusinessI");
		storageBusinessI=(StorageBusinessI) webApplicationContext.getBean("storageBusinessImpl");
		jedisUtils=(JedisUtils) webApplicationContext.getBean("jedisUtils");
		System.out.println("============init=============");
		try {
			//初始化消费者对象
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(CM_CONFIG_PROPERTIES.RABBIT_HOST);// MQ的IP
		    factory.setPort(Integer.parseInt(CM_CONFIG_PROPERTIES.RABBIT_PORT));// MQ端口
		    factory.setUsername(CM_CONFIG_PROPERTIES.RABBIT_USERNAME);// MQ用户名
		    factory.setPassword(CM_CONFIG_PROPERTIES.RABBIT_PASSWORD);// MQ密码
		    //获取连接
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    //使用fanout交换机
		    channel.exchangeDeclare(MQExcahngeName, "fanout");
		    String queueName = MQqueueName;
		    channel.queueDeclareNoWait(queueName, false, false, false, null);
		    channel.queueBind(queueName, MQExcahngeName, "");

		    //初始化消费者对象
		    Consumer consumer = new DefaultConsumer(channel) {
		        @Override
		        public void handleDelivery(String consumerTag, Envelope envelope,
		                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
		        	Jedis jedis = null;
			        try {
			        	//获取队列中的数据
				          String message = new String(body, "UTF-8");
				          String messageDecode = URLDecoder.decode(message,"utf-8");
				          //将数据存储到redis
			        	  jedis = jedisUtils.getJedis();
			        	  //获取redis中当前存储的数据量
			        	  long dataCountLong = jedis.zcard(CM_CONFIG_PROPERTIES.MQ_DATA_KEY_NAME);
			        	  if(dataCountLong<maxCacheNumber){
			        		  //将该条消息存入redis并且设置该条的score为count+1
			        		  jedis.zadd(mqDataKeyName, dataCountLong+1, messageDecode);
			        		  //再检查一遍数量
			        		  dataCountLong = jedis.zcard(CM_CONFIG_PROPERTIES.MQ_DATA_KEY_NAME);
			        		  if(dataCountLong>=maxCacheNumber){
			        			  //当数量等于允许的最大条数,取出存入数据库,并且清空redis字段
			        			  //大于属于异常情况,但是不能抛弃数据
			        			  Set<String> jsonSet = jedis.zrange(mqDataKeyName, 0, -1);
			        			  //将jsonSet转为可执行的sql
			        			  String sql = storageBusinessI.transferBulkJsonToSql(jsonSet);
			        			  //执行sql
			        			  List<LinkedHashMap<String, Object>> result = xmlDatabaseTransferBusinessI.executeSQL(sql);
			        			  //清空redis
			        			  jedis.del(mqDataKeyName);
			        		  }
			        	  }
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
			  			if (jedis != null)
							jedis.close();
			        }
		        }
		      };
		      //启动消费者
		      channel.basicConsume(queueName, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
