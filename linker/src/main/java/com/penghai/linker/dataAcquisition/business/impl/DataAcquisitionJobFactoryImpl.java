package com.penghai.linker.dataAcquisition.business.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IConfigurationManagementBusiness;
import com.penghai.linker.dataAcquisition.business.IDataAcquisitionJobFactory;
import com.penghai.linker.dataAcquisition.model.ScheduleJobModel;
import com.penghai.linker.dataAcquisition.model.TargetDatabaseRoot;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_INFO_DATA;
import com.penghai.linker.util.CommonData.CM_LINKER_RETURN_CODE;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;
import com.penghai.linker.util.EhcacheUtil;
import com.penghai.linker.util.HTTPUtil;
import com.penghai.linker.util.SpringUtil;
import com.penghai.linker.util.xml.XmlUtil;
import com.penghai.linker.util.xml.XmlUtil.CollectionWrapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 定时任务工厂类接口实现,执行定时任务
 * @author 徐超
 * @Date 2017年5月16日 下午4:20:16
 */
@Service
@DisallowConcurrentExecution
public class DataAcquisitionJobFactoryImpl implements IDataAcquisitionJobFactory,Job{
	
	//获取log对象
	Logger log = Logger.getLogger(DataAcquisitionJobFactoryImpl.class);
	
	/**
	 * 定时任务执行的具体操作
	 * @author 徐超
	 * @Date 2017年5月19日 下午4:55:59
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取Ehcache对象
		EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
		//获取配置信息对象
		IConfigurationManagementBusiness configurationManagementBusiness = 
				(IConfigurationManagementBusiness)SpringUtil.getInstance().getBeanById("configurationManagementBusiness");
		//获取任务对象
		ScheduleJobModel scheduleJob = (ScheduleJobModel)context.getMergedJobDataMap().get("scheduleJob");
		//缓存中单表已读取条数--主键的值
		String tableSelectCountKeyName = scheduleJob.getTargetTable()+"SelectCount";
		//缓存中单表已上传条数--主键的值
		String tableStorageCountKeyName = scheduleJob.getTargetTable()+"SaveCount";
		//缓存中单表已读取条数--条数
		String tableSelectCountNumberKeyName = scheduleJob.getTargetTable()+"SelectCountNumber";
		//缓存中单表已上传条数--条数
		String tableStorageCountNumberKeyName = scheduleJob.getTargetTable()+"SaveCountNumber";
		//主键的名称
		String primaryKeyName = scheduleJob.getPrimaryKey();
		//要存储的表的名称
		String targetTableName = scheduleJob.getStorageTable();
		//执行的sql
		StringBuffer selectSQL = new StringBuffer(scheduleJob.getSelectSQL());
		selectSQL.append("WHERE `").append(primaryKeyName).append("` > ")
			.append(scheduleJob.getCurrentSelectColumn()).append(" LIMIT ")
			.append(CM_CONFIG_PROPERTIES.READ_DATA_COUNT).append(" ; ");
		
		
		//获取动态配置的参数
		JSONObject configParams = new JSONObject();
		configParams = configurationManagementBusiness.getConfigurations();
		
		//数据库参数
		String driver = CM_CONFIG_PROPERTIES.DATABASE_DRIVER;
		String databaseName = (String)ehcacheUtil.get("selectDatabaseName");
		String password = configParams.getString("databasePassword");
		String userName = configParams.getString("databaseUsername");
		String databaseIP = configParams.getString("databaseIp");
		String databasePort = configParams.getString("databasePort");
		StringBuffer databaseURL = new StringBuffer();
		databaseURL.append("jdbc:mysql://").append(databaseIP)
				   .append(":").append(databasePort).append("/")
				   .append(databaseName).append("?characterEncoding=utf8");
		
		//rabbitMQ参数
		String mqIP = configParams.getString("rabbitMqIp");
		String mqPort = configParams.getString("rabbitMqPort");
		String mqUserName = configParams.getString("rabbitMqUsername");
		String mqPassword = configParams.getString("rabbitMqPassword");
		
		//执行查询sql获取数据
		JSONObject json = getDatasFromDatabase(
				driver,databaseName,password,userName,databaseURL.toString(),
				selectSQL.toString(),targetTableName,primaryKeyName,
				scheduleJob.getRefColumns(),scheduleJob.getRefPrimaryKey());
		if(null!=json){
			//当次查询的结果集条数
			int currentCount = Integer.parseInt(json.getString("dataNumber"));
			//将获取的值转换为JSON格式
			JSONObject dataJsonMQ = json.getJSONObject("dataJsonMQ");
			
			//本次取出的最后一条记录
			String currentSelectCount = json.getString("currentSelectCount");
			scheduleJob.setCurrentSelectColumn(Integer.valueOf(currentSelectCount));
		
			//将当前查到的条数记录到缓存中
			int currentSelectColumnNumber = scheduleJob.getCurrentSelectColumnNumber()+currentCount;
			scheduleJob.setCurrentSelectColumnNumber(currentSelectColumnNumber);
			ehcacheUtil.put(tableSelectCountNumberKeyName, currentSelectColumnNumber);
			//将JSON存储到MQ当中
			boolean result = publishJsonToMQ(dataJsonMQ.toJSONString(),mqIP,mqPort,mqUserName,mqPassword);
			if(result){
				//成功则将缓存中数据条数更新
				scheduleJob.setCurrentStorageColumn(Integer.valueOf(currentSelectCount));
				int currentSaveColumnNumber = scheduleJob.getCurrentStorageColumnNumber()+currentCount;
				scheduleJob.setCurrentStorageColumnNumber(currentSelectColumnNumber);
				ehcacheUtil.put(tableStorageCountNumberKeyName, currentSaveColumnNumber);
			}
		}else{
			log.info("已取到最后一条！");
		}
		
	}

	/**
	 * linker注册服务业务逻辑
	 * @author 徐超
	 * @Date 2017年5月17日 上午9:42:38
	 * @param linkerId
	 * @throws Exception
	 */
	@Override
	public JSONObject linkerRegister(String linkerId){
		JSONObject returnJson = new JSONObject();
		try {
			IConfigurationManagementBusiness configurationManagementBusiness = 
					(IConfigurationManagementBusiness)SpringUtil.getInstance().getBeanById("configurationManagementBusiness");
			//获取动态配置的参数
			JSONObject configParams = new JSONObject();
			configParams = configurationManagementBusiness.getConfigurations();
			//存储系统IP
			String cssIP = configParams.getString("serverIp");
			String cssPort = "";
			StringBuffer registerURL = new StringBuffer();
			registerURL.append("http://").append(cssIP)
				.append("/css/linkerManager/linkerRegister")
				.append("?linkerId=").append(linkerId);
			String resultString = HTTPUtil.loadURL(registerURL.toString());
			String resultStringDecode = URLDecoder.decode(resultString, "utf-8");
			JSONObject resultJson = JSONObject.parseObject(resultStringDecode);
			String resultCode = resultJson.getString("code");
			if(null!=resultCode && !"".equals(resultCode) && 
			   resultCode.equals(CM_LINKER_RETURN_CODE.LINKER_RETURN_OK)){
				//注册成功,提取xml文本
				String databaseXml = resultJson.getString("targetDatabaseXml");
				String refDatabaseInfoJson = resultJson.getString("databaseInfoJson");
				if(null==databaseXml || "".equals(databaseXml) || null==refDatabaseInfoJson || "".equals(refDatabaseInfoJson)){
					log.warn(CM_INFO_DATA.XML_TEXT_NULL);
					return null;
				}else{
					returnJson.put("refDatabaseInfoJson", refDatabaseInfoJson);
					returnJson.put("databaseXml", databaseXml);
					return returnJson;
				}
			}else{
				//注册失败
				log.error(CM_INFO_DATA.LINKER_REGISTER_FAILED);
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return null;
		}
	}

	/**
	 * 将xml文本转为TargetDatabase对象
	 * @author 徐超
	 * @Date 2017年5月17日 下午2:43:41
	 * @param xmlContent
	 * @return
	 */
	@Override
	public TargetDatabaseRoot transferXML2Model(String xmlContent){
		TargetDatabaseRoot database = new TargetDatabaseRoot();
		try {
			//解析XML文本
			XmlUtil resultBinder = new XmlUtil(TargetDatabaseRoot.class, CollectionWrapper.class);
			database = (TargetDatabaseRoot)resultBinder.fromXml(xmlContent);
			return database;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return database;
		}
	}
	
	/**
	 * 执行查询语句,去目标数据库查询数据
	 * @author 徐超
	 * @Date 2017年5月19日 下午1:02:15
	 * @param driver
	 * @param databaseName
	 * @param password
	 * @param userName
	 * @param databaseURL
	 * @param sql
	 * @return
	 */
	public JSONObject getDatasFromDatabase(
			String driver,
			String databaseName,
			String password,
			String userName,
			String databaseURL,
			String sql,
			String targetTableName,
			String primaryKeyName,
			String refColumns,
			String refPrimaryKey){
		
        JSONObject json = new JSONObject();
        EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
        try {
        	//获取Ehcache对象
        	Class.forName(driver);
        	//新建连接
        	Connection connection = DriverManager.getConnection(databaseURL, userName,password);
        	//新建预加载的查询语句
        	PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	//结果集
        	ResultSet resultSet = preparedStatement.executeQuery();
        	json = transferDataToJson(resultSet,targetTableName,primaryKeyName,refColumns,refPrimaryKey);
        	// 关闭记录集
            if (resultSet != null) {
                try {
                	resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 关闭声明
            if (preparedStatement != null) {
                try {
                	preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 关闭链接对象
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //将正确状态保存至缓存
            ehcacheUtil.put(CM_CONFIG_PROPERTIES.SCADA_MYSQL_STATUS_CACHE_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
        }catch (Exception e) {
        	log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
        	//将错误状态保存至缓存
        	ehcacheUtil.put(CM_CONFIG_PROPERTIES.SCADA_MYSQL_STATUS_CACHE_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
		return json;
	}
	
	/**
	 * 将查询出的数据转换为JSON格式
	 * @author 徐超
	 * @Date 2017年5月19日 下午1:13:46
	 * @param resultSet
	 * @return
	 */
	public JSONObject transferDataToJson(
			ResultSet resultSet ,
			String targetTableName ,
			String primaryKeyName,
			String refColumns,
			String refPrimaryKey){
		//初始化Ehcache对象
		EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
		//获取linkerId和需要存储的数据库的名字
		String linkerId = (String)ehcacheUtil.get("linkerId");
		String targetDatabaseName = (String)ehcacheUtil.get("targetDatabaseName");
		//构造JSON结构
		JSONObject json = new JSONObject();//总JSON对象
    	JSONArray dataArray = new JSONArray();//数据列表data
    	JSONObject dataJson = new JSONObject();//数据列表dataArray
    	JSONArray tableDataArray = new JSONArray();//表列表信息tableData
    	JSONObject dataJson4Return = new JSONObject();
    	String currentSelectCount = "";
		json.put("linker", linkerId);
		json.put("database", targetDatabaseName);
		int totalNumber = 0;
		try {
			if(resultSet.next()){
				resultSet.first();
				resultSet.previous();
				//遍历返回数据结果集
				while (resultSet.next()) {
					JSONArray columnDataArray = new JSONArray();//表列表信息columnData
		    		int columnCount = resultSet.getMetaData().getColumnCount();
		    		//遍历每一列
		    		for(int i=1;i<columnCount+1;i++){
		    			columnDataArray.add(resultSet.getString(i));
		    		}
		    		tableDataArray.add(columnDataArray);
		    	}
				resultSet.last();
				totalNumber = resultSet.getRow();
				currentSelectCount = resultSet.getString(primaryKeyName);
			}else{
				return null;
			}
			//组装JSON结构
			dataJson.put("columus", refColumns);
			dataJson.put("primaryKey", refPrimaryKey);
			dataJson.put("table", targetTableName);
			dataJson.put("tableData", tableDataArray);
			dataArray.add(dataJson);
			json.put("data", dataArray);
			dataJson4Return.put("dataJsonMQ",json);
			dataJson4Return.put("currentSelectCount", currentSelectCount);
			dataJson4Return.put("dataNumber", totalNumber);
			return dataJson4Return;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return json;
		}
	}
	
	
	
	/**
	 * 将JSON数据发布到RabbitMQ
	 * @author 徐超
	 * @param jsonString
	 * @Date 2017年5月19日 下午1:01:52
	 */
	public boolean publishJsonToMQ(String jsonString,String mqIP,String mqPort,String mqUserName,String mqPassword){
		boolean result = false;
		EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
		try {
			//将数据发布到MQ
			ConnectionFactory factory = new ConnectionFactory();
			// MQ的IP
			factory.setHost(mqIP);
			// MQ端口
		    factory.setPort(Integer.parseInt(mqPort));
		    // MQ用户名
		    factory.setUsername(mqUserName);
		    // MQ密码
		    factory.setPassword(mqPassword);
		    //新建MQ链接
		    com.rabbitmq.client.Connection connection = factory.newConnection();
		    //新建管道
	        Channel channel = connection.createChannel();
	        //将要发布的字符串进行UTF-8编码
	        String jsonEncode = URLEncoder.encode(jsonString,"utf-8");
	        //进行发布
	        channel.basicPublish(CM_CONFIG_PROPERTIES.EXCHANG_NAME, "", null, jsonEncode.getBytes());
	        channel.close();
	        connection.close();
			result = true;
			//将正确状态保存至缓存
            ehcacheUtil.put(CM_CONFIG_PROPERTIES.CSS_MQ_STATUS_CACHE_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
			return result;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			result = false;
			//将错误状态保存至缓存
        	ehcacheUtil.put(CM_CONFIG_PROPERTIES.CSS_MQ_STATUS_CACHE_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
			return result;
		}
	}
	
	
	
}
