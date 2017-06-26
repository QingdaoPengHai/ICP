package com.penghai.css.analysis.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.analysis.business.XMLDatabaseTransferBusinessI;
import com.penghai.css.analysis.dao.mybatis.XMLDatabaseTransferDaoI;
import com.penghai.css.storage.business.StorageBusinessI;
import com.penghai.css.util.HTTPUtil;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * xml文本转换类
 * @author 徐超
 * @Date 2017年5月10日 上午9:47:52
 */
@Controller
@RequestMapping(value = "/xml")
public class XMLDatabaseTransferController {

	@Autowired
	private XMLDatabaseTransferBusinessI xmlDatabaseTransferBusinessI;
	
	@Autowired
	private XMLDatabaseTransferDaoI xmlDatabaseTransferDaoI;
	
	@Autowired
	private StorageBusinessI storageBusinessI;
	
	Logger log = Logger.getLogger(XMLDatabaseTransferController.class);
	
//	private final static String EXCHANGE_NAME = CM_CONFIG_PROPERTIES.QUEUE_NAME;
	/**
	 * 测试执行建库service--sql--正式上线需删除
	 * @author 徐超
	 * @Date 2017年5月7日 下午4:51:53
	 * @param sql
	 * @return
	 */
	@RequestMapping(value = "/execute")
	@ResponseBody
	public Map<String, Object> executeSQL(String sqlString){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<LinkedHashMap<String, Object>> resultLinkedMap = new ArrayList<LinkedHashMap<String, Object>>();
		sqlString = "create table execute (id int(10) )";
		resultLinkedMap = xmlDatabaseTransferBusinessI.executeSQL(sqlString);
		resultMap.put("result", resultLinkedMap);
		return resultMap;
	}
	
	
	/**
	 * 用于测试的发布信息到MQ方法--正式上线需删除
	 * @author 徐超
	 * @Date 2017年5月9日 下午3:48:11
	 * @param timeInterval 向MQ发送消息的时间间隔
	 * @param runTime 运行的总时间 单位秒
	 * @param dataNumbers 每次向表中写几条数据
	 * @param varcharLength 可变字段的长度
	 * @return
	 */
	@RequestMapping(value = "publish")
	public Map<String, Object> publishMQ(int timeInterval,int runTime,int dataNumbers,int varcharLength){
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		int timeInterval = 0;//向MQ发送消息的时间间隔
//		int runTime = 0;//运行的总时间
//		int dataNumbers = 0;//每次向表中写几条数据
//		int varcharLength = 0;//可变字段的长度
		String randomString = getRandomString(varcharLength);
		long startTime = System.currentTimeMillis();
		long endTime = startTime+runTime*1000;
		try {
			final String jsonStatic = xmlDatabaseTransferDaoI.getJson();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(CM_CONFIG_PROPERTIES.RABBIT_HOST);// MQ的IP
		    factory.setPort(Integer.parseInt(CM_CONFIG_PROPERTIES.RABBIT_PORT));// MQ端口
		    factory.setUsername(CM_CONFIG_PROPERTIES.RABBIT_USERNAME);// MQ用户名
		    factory.setPassword(CM_CONFIG_PROPERTIES.RABBIT_PASSWORD);// MQ密码
			Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	        Runnable runnable = new Runnable() {
				int j = 0;
				@Override
				public void run() {
					try {
						String json = jsonStatic;
						StringBuffer str = new StringBuffer();
						j++;
						for(int i=0;i<dataNumbers;i++){
							long time = System.currentTimeMillis();
							str.append("[\"第").append(j).append("次\",")
							   .append("\"第").append(i).append("条\",")
							   .append("\"").append(time).append("\",")
							   .append("\"").append(randomString).append("\",")
							   .append("\"dataString5\"],");
						}
						str.deleteCharAt(str.length()-1);
						json = json.replace("table1String", str).replace("table2String", str);
						String jsonEncode = URLEncoder.encode(json,"utf-8");
						System.out.println("====第"+j+"条字段的长度为"+jsonEncode.length()+"====");
						channel.basicPublish(CM_CONFIG_PROPERTIES.EXCHANG_NAME, "", null, jsonEncode.getBytes());
						if(System.currentTimeMillis()>endTime){
							service.shutdownNow();
					        channel.close();
					        connection.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			service.scheduleAtFixedRate(runnable, 0, timeInterval, TimeUnit.MILLISECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	/**
	 * 生成随机字符串
	 * @author 徐超
	 * @Date 2017年5月12日 上午11:59:44
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }
	/**
	 * 全流程的测试方法--正式上线需删除
	 * @author 徐超
	 * @Date 2017年5月9日 下午3:48:54
	 */
	@RequestMapping(value = "/test")
	public void test(){
		try {
			String url = "http://localhost:8080/store/order/getUserOrderXmlDetail?email=qc@qq.com&password=123123&xmlId=16";
			String result = HTTPUtil.loadURL(url);
			JSONObject json = new JSONObject();
//			json = JSONObject.parseObject(result);
//			String xmlString = json.getJSONObject("xmlDetail").getString("xmlContent");
//			String sql = xmlDatabaseTransferBusinessI.analyzeXMLStringToSQL(xmlString);
			String sql = "INSERT INTO test.`name` (`name`) VALUES ('aaaaa');";
			List<LinkedHashMap<String, Object>> mapList = xmlDatabaseTransferBusinessI.executeSQL(sql);
			System.out.println(json);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对json转sql的测试方法--正式上线需删除
	 * @author 徐超
	 * @Date 2017年5月9日 下午4:18:08
	 */
	@RequestMapping(value = "/testTransfer")
	public void testOfTransferJsonToSql(){
		//模拟获取jsonString
		String json = xmlDatabaseTransferDaoI.getJson();
		
		try {
			String sql = storageBusinessI.transferJsonToSql(json);
			System.out.println("=============SUCCESS===testOfTransferJsonToSql=============");
		} catch (Exception e) {
			System.out.println("=============ERROR===testOfTransferJsonToSql=============");
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 循环测试--正式上线需删除
	 * @author 徐超
	 * @Date 2017年5月10日 上午8:38:06
	 */
	@RequestMapping("/loop")
	public void loop(){
		final String jsonStatic = xmlDatabaseTransferDaoI.getJson();
		Runnable runnable = new Runnable() {
			int j = 0;
//			String json = jsonStatic;
			@Override
			public void run() {
//				try {
					String json = jsonStatic;
					StringBuffer str = new StringBuffer();
					j++;
					log.info("run这是第"+j+"次100循环~");
					for(int i=1;i<101;i++){
//						log.info("100这是第"+i+"次循环~");
						str.append("[\"第").append(j).append("次\",")
						   .append("\"第").append(i).append("条\",")
						   .append("\"dataString3\",").append("\"dataString4\"],");
					}
					str.deleteCharAt(str.length()-1);
					json = json.replace("table1String", str).replace("table2String", str);
					log.info(json);
//					System.out.println(" [x] Sent '" + json + "'");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		
		service.scheduleAtFixedRate(runnable, 5, 1, TimeUnit.SECONDS);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
