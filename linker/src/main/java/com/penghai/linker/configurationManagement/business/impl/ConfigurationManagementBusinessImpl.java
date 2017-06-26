package com.penghai.linker.configurationManagement.business.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.util.CommonUtils;
import com.penghai.linker.util.EhcacheUtil;
import com.penghai.linker.configurationManagement.business.IConfigurationManagementBusiness;
import com.penghai.linker.configurationManagement.model.LinkerConfiguration;
import com.penghai.linker.util.CommonData.CM_CONFIGURATION_PATH;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;

/**
 * 配置管理业务处理
 * @author 秦超
 * @time 2017年5月18日
 */
@Service("configurationManagementBusiness")
public class ConfigurationManagementBusinessImpl implements IConfigurationManagementBusiness{

	/**
	 * 修改linker配置信息：存储系统路径、数据库信息、rabbitMQ信息
	 * @author 秦超
	 * @param linkerConfiguration
	 * @return 修改结果
	 * @time 2017年5月16日
	 */
	@Override
	public JSONObject saveConfigurations(LinkerConfiguration linkerConfiguration) {
		JSONObject resultJson = new JSONObject();

		Properties pps = new Properties();
		InputStream in;
		OutputStream out;
		try {
			
			in = ConfigurationManagementBusinessImpl.class.getClassLoader().getResourceAsStream(CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH);
			//从输入流中读取属性列表（键和元素对） 
			pps.load(in);
			//调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。  
			//强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
//			out = new FileOutputStream("WEB-INF/classes/"+CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH);
			File file=new File(ConfigurationManagementBusinessImpl.class.getClassLoader().getResource("").getPath(),CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH);
			out = new FileOutputStream(file);
			//存储系统ip
			pps.setProperty("serverIp", linkerConfiguration.getServerIp());
			//SCADA数据库ip
			pps.setProperty("databaseIp", linkerConfiguration.getDatabaseIp());
			//SCADA数据库端口号
			pps.setProperty("databasePort", linkerConfiguration.getDatabasePort());
			//SCADA数据库登录名
			pps.setProperty("databaseUsername", linkerConfiguration.getDatabaseUsername());
			//SCADA数据库登录密码
			pps.setProperty("databasePassword", linkerConfiguration.getDatabasePassword());
			//rabbitMQ的ip
			pps.setProperty("rabbitMqIp", linkerConfiguration.getRabbitMqIp());
			//rabbitMQ的端口号
			pps.setProperty("rabbitMqPort", linkerConfiguration.getRabbitMqPort());
			//rabbitMQ的
			pps.setProperty("rabbitMqUsername", linkerConfiguration.getRabbitMqUsername());
			//rabbitMQ的端口号
			pps.setProperty("rabbitMqPassword", linkerConfiguration.getRabbitMqPassword());
			//以适合使用 load 方法加载到 Properties 表中的格式，  
			//将此 Properties 表中的属性列表（键和元素对）写入输出流  
			pps.store(out, "Update linker configuration");
			in.close();
			out.close();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
		
		return resultJson;
	}

	/**
	 * 获取配置文件中记录的配置信息
	 * @author 秦超
	 * @return 配置信息
	 * @time 2017年5月17日
	 */
	@Override
	public JSONObject getConfigurations() {
		JSONObject resultJson = new JSONObject();
		Properties prop = new Properties();
		//读取properties文件
		try {
			InputStream in = new BufferedInputStream (CommonUtils.class.getClassLoader().getResourceAsStream(CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH));
			prop.load(in);     ///加载属性列表
			Iterator<String> it=prop.stringPropertyNames().iterator();
			while(it.hasNext()){
			    String key=it.next();
			    resultJson.put(key, prop.getProperty(key));
			}
			in.close();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
		return resultJson;
	}

	/**
	 * 获取静态配置信息：监控刷新时间间隔
	 * @author 秦超
	 * @return
	 * @time 2017年5月19日
	 */
	@Override
	public JSONObject getStaticConfigurations() {
		JSONObject resultJson = new JSONObject();
		
		EhcacheUtil ehcache = EhcacheUtil.getInstance();
		//获取缓存中存储的起始时间
		Object startTime = ehcache.get("startTime");
		
		resultJson.put("logRefreshTime", CM_CONFIG_PROPERTIES.LOG_REFRESH_TIME);
		resultJson.put("countRefreshTime", CM_CONFIG_PROPERTIES.COUNT_REFRESH_TIME);
		resultJson.put("dataRefreshTime", CM_CONFIG_PROPERTIES.DATA_REFRESH_TIME);
		resultJson.put("startTime", startTime);
		
		return resultJson;
	}

	/**
	 * 获取linker配置状态
	 * @author 秦超
	 * @return
	 * @time 2017年5月21日
	 */
	@Override
	public JSONObject getLinkerConfigStatus() {
		JSONObject resultJson = new JSONObject();
		Properties prop = new Properties();
		//读取properties文件
		try {
			InputStream in = new BufferedInputStream (CommonUtils.class.getClassLoader().getResourceAsStream(CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH));
			prop.load(in);     ///加载属性列表
			
			resultJson.put("linkerConfigStatus", prop.getProperty("linkerConfigStatus"));
			
			in.close();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
		return resultJson;
	}

	/**
	 * 修改linker配置状态
	 * @author 秦超
	 * @return
	 * @time 2017年5月21日
	 */
	@Override
	public JSONObject updateLinkerConfigStatus(String linkerConfigStatus) {
		JSONObject resultJson = new JSONObject();

		Properties pps = new Properties();
		InputStream in;
		OutputStream out;
		try {
			
			in = ConfigurationManagementBusinessImpl.class.getClassLoader().getResourceAsStream(CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH);
			pps.load(in);
			File file=new File(ConfigurationManagementBusinessImpl.class.getClassLoader().getResource("").getPath(),CM_CONFIGURATION_PATH.PROPERTIES_FILEPATH);
			out = new FileOutputStream(file);
			//存储系统ip
			pps.setProperty("linkerConfigStatus", linkerConfigStatus);
			
			//将此 Properties 表中的属性列表（键和元素对）写入输出流  
			pps.store(out, "Update linker configuration");
			in.close();
			out.close();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
		
		return resultJson;
	}
	
}
