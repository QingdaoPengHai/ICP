package com.penghai.css.analysis.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.analysis.business.IMycatBusiness;
import com.penghai.css.analysis.model.Database;
import com.penghai.css.analysis.model.MycatWriteHost;
import com.penghai.css.analysis.model.MycatWriteHostList;
import com.penghai.css.analysis.model.Schema;
import com.penghai.css.analysis.model.Table;
import com.penghai.css.util.JedisUtils;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_INFO_DATA;
import com.penghai.css.util.CommonData.CM_MYCAT_CONFIGURATION;
import com.penghai.css.util.CommonData.CM_REDIS_KEY;
import com.penghai.css.util.xml.XmlUtil;
import com.penghai.css.util.xml.XmlUtil.CollectionWrapper;

import redis.clients.jedis.Jedis;

/**
 * mycat相关业务接口实现类
 * @author 徐超
 * @Date 2017年6月4日 下午6:38:10
 */
@Service
public class MycatBusinessImpl implements IMycatBusiness{
	
	//获取日志对象
	Logger log = Logger.getLogger(MycatBusinessImpl.class);

	//redis
	@Autowired
	private JedisUtils jedisUtils = null;
	/**
	 * 将schema的xml文本转换为schema对象
	 * @author 徐超
	 * @Date 2017年6月4日 下午7:10:30
	 * @param xmlContent
	 * @return
	 */
	public Schema analyzedSchemaXmlContent(String xmlContent){
		Schema schema = new Schema();
		try {
			XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
			schema = resultBinder.fromXml(xmlContent);
			return schema;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR, e);
			return null;
		}
	}
	
	/**
	 * 获取配置文件中配置好的writeHosts
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:39:52
	 * @return
	 */
	@Override
	public List<MycatWriteHost> getWriteHosts() {
		List<MycatWriteHost> writeHosts = new ArrayList<MycatWriteHost>();
		MycatWriteHostList mycatWriteHostList = new MycatWriteHostList();
		//获取配置文件中配置的writeHosts
		String writeHostsXmlContent = CM_MYCAT_CONFIGURATION.WRITE_HOSTLIST;
		try {
			//将配置的writeHosts转为对象
			XmlUtil resultBinder = new XmlUtil(MycatWriteHostList.class,CollectionWrapper.class);
			mycatWriteHostList = resultBinder.fromXml(writeHostsXmlContent);
			writeHosts = mycatWriteHostList.getWriteHosts();
			return writeHosts;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR, e);
			return null;
		}
	}

	/**
	 * 分别在writeHost上执行建库建表
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:41:58
	 * @param writeHosts
	 * @param createSQL
	 * @throws Exception
	 */
	@Override
	public boolean createDatabasesAndTablesOnWriteHosts(List<MycatWriteHost> writeHosts, String createSQL){
		boolean result = false;
		String driver = CM_CONFIG_PROPERTIES.DATABASE_DRIVER;
		try {
			for(MycatWriteHost host:writeHosts){
				Class.forName(driver);
				StringBuffer databaseURL = new StringBuffer();
				databaseURL.append("jdbc:mysql://")
					.append(host.getUrl()).append("/")
					.append("mysql?characterEncoding=utf8&allowMultiQueries=true");
				//新建连接
	        	Connection connection = DriverManager.getConnection(databaseURL.toString(), host.getUser(),host.getPassword());
	        	//新建预加载的查询语句
	        	PreparedStatement preparedStatement = connection.prepareStatement(createSQL);
	        	preparedStatement.executeUpdate();
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
			}
			result = true;
			return result;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return result;
		}
	}

	/**
	 * 根据用户定义的writeHosts生成mycat配置文件内容
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:43:32
	 * @param writeHosts
	 * @return
	 */
	@Override
	public JSONObject getMycatConfigFilesContent(Schema schema) {
		JSONObject resultObject = new JSONObject();
		//中间变量
		StringBuffer schemaDataHosts = new StringBuffer();
		StringBuffer schemaDataNodes = new StringBuffer();
		StringBuffer schemaSchemas = new StringBuffer();
		StringBuffer schemaNames = new StringBuffer();
		StringBuffer tableRules = new StringBuffer();
		MycatWriteHost returnMycatWriteHost = new MycatWriteHost();
		Database returnDatabase = new Database();
		//获取mycat配置模板
		String totalSchemaTemplate = CM_MYCAT_CONFIGURATION.TOTAL_SCHEMA_TEMPLATE;
		String totalServerTemplate = CM_MYCAT_CONFIGURATION.TOTAL_SERVER_TEMPLATE;
		String schemaTemplate = CM_MYCAT_CONFIGURATION.SCHEMA_TEMPLATE;
		String schemaTableTemplate = CM_MYCAT_CONFIGURATION.SCHEMA_TABLE_TEMPLATE;
		String schemaDataNodeTemplate = CM_MYCAT_CONFIGURATION.SCHEMA_DATANODE_TEMPLATE;
		String schemaDataHostTemplate = CM_MYCAT_CONFIGURATION.SCHEMA_DATAHOST_TEMPLATE;
		String schemaWriteHostTemplate = CM_MYCAT_CONFIGURATION.SCHEMA_WRITEHOST_TEMPLATE;
		String sequence_db_conf = CM_MYCAT_CONFIGURATION.SEQUENCE_DB_CONF_TEMPLATE;
		String totalRuleTemplate = CM_MYCAT_CONFIGURATION.TOTAL_RULE_TEMPLATE;
		String tableRuleTemplate = CM_MYCAT_CONFIGURATION.TABLE_RULE_TEMPLATE;
		String functionTemplate = CM_MYCAT_CONFIGURATION.FUNCTION_TEMPLATE;
		//获取定义的writeHost列表对象
		List<MycatWriteHost> writeHosts = this.getWriteHosts();
		//获取待连接的数据库列表对象
		List<Database> databases = schema.getDatabases();
		
		//1.根据定义的writeHost列表生成dataHost列表
		for(MycatWriteHost host:writeHosts){
			String schemaDataHostTemplateTemporary = schemaDataHostTemplate.replace("@NAME", host.getHost());
			String schemaWriteHostTemplateTemporary = schemaWriteHostTemplate
					.replace("@HOST", host.getHost())
					.replace("@URL", host.getUrl())
					.replace("@USER", host.getUser())
					.replace("@PASSWORD", host.getPassword());
			schemaDataHostTemplateTemporary = schemaDataHostTemplateTemporary
					.replace("@WRITEHOST", schemaWriteHostTemplateTemporary);
			schemaDataHosts.append(schemaDataHostTemplateTemporary);
		}
		
		
		//2.根据数据库列表生成dataNode列表和schema列表
		for(Database database:databases){
			//中间变量
			StringBuffer schemaTables = new StringBuffer();
			//记录dataNodeName变量
			StringBuffer dataNodesName = new StringBuffer();
			//生成dataNode
			for(MycatWriteHost host:writeHosts){
				String schemaDataNodeTemplateTemporary = schemaDataNodeTemplate
						.replace("@DATABASE", database.getName())
						.replace("@DATAHOST", host.getHost())
						.replace("@NAME", database.getName()+host.getHost());
				schemaDataNodes.append(schemaDataNodeTemplateTemporary);
				dataNodesName.append(database.getName()).append(host.getHost()).append(",");
			}
			//删除最后一个逗号
			dataNodesName.deleteCharAt(dataNodesName.length()-1);
			//生成tableList和rule中tableRuleList
			for(Table table:database.getTables()){
				String tableRuleName = database.getName()+table.getName();
				String schemaTableTemplateTemporary = schemaTableTemplate
						.replace("@NAME", table.getName())
						.replace("@DATANODE", dataNodesName.toString())
						.replace("@PRIMARYKEY", table.getPrimaryKey())
						.replace("@RULE", tableRuleName);
				schemaTables.append(schemaTableTemplateTemporary);
				String tableRuleTemplateTemporary = tableRuleTemplate
						.replace("@TABLERULENAME", tableRuleName)
						.replace("@PRIMARYKEY", table.getPrimaryKey());
				tableRules.append(tableRuleTemplateTemporary);
			}
			String schemaTemplateTemporary = schemaTemplate
					.replace("@NAME", database.getName())
					.replace("@TABLE", schemaTables.toString());
			schemaSchemas.append(schemaTemplateTemporary);
			schemaNames.append(database.getName()).append(",");
		}
		//添加dataHost
		totalSchemaTemplate = totalSchemaTemplate.replace("@DATAHOST", schemaDataHosts);
		//添加schema
		totalSchemaTemplate = totalSchemaTemplate.replace("@SCHEMA", schemaSchemas.toString());
		//添加dataNode
		totalSchemaTemplate = totalSchemaTemplate.replace("@DATANODE", schemaDataNodes.toString());
		
		//删除最后一个逗号
		schemaNames.deleteCharAt(schemaNames.length()-1);
		totalServerTemplate = totalServerTemplate.replace("@SCHEMAS", schemaNames.toString());
		
		//取第一个,用于存放全局序列号
		returnMycatWriteHost = writeHosts.get(0);
		returnDatabase = databases.get(0);
		String sequenceDataNodeName = returnDatabase.getName()+returnMycatWriteHost.getHost();
		sequence_db_conf = sequence_db_conf.replace("@DATANODE", sequenceDataNodeName);
		
		//3.生成rule.xml文本
		functionTemplate = functionTemplate.replace("@HOSTNUMBER", String.valueOf(writeHosts.size()));
		totalRuleTemplate = totalRuleTemplate
				.replace("@TABLERULE", tableRules)
				.replace("@FUNCTION", functionTemplate);
		
		resultObject.put("sequenceDataNodeDatabase", returnDatabase);
		resultObject.put("sequenceDataNodeWriteHost", returnMycatWriteHost);
		resultObject.put("schemaXmlContent", totalSchemaTemplate);
		resultObject.put("serverXmlContent", totalServerTemplate);
		resultObject.put("sequence_db_conf", sequence_db_conf);
		resultObject.put("ruleXmlContent", totalRuleTemplate);
		return resultObject;
	}
	
	/**
	 * 创建全局序列
	 * @author 徐超
	 * @Date 2017年6月6日 上午11:43:08
	 * @param dataNodeName
	 * @return
	 */
	public boolean createGlobalSequence(Database database,MycatWriteHost writeHost){
		boolean result = false;
		Jedis jedis = null;
		try {
			//创建
			StringBuffer createSQL = new StringBuffer();
			String driver = CM_CONFIG_PROPERTIES.DATABASE_DRIVER;
			String databaseName = database.getName();
			StringBuffer databaseURL = new StringBuffer();
			databaseURL.append("jdbc:mysql://")
				.append(writeHost.getUrl()).append("/")
				.append(databaseName)
				.append("?characterEncoding=utf8&allowMultiQueries=true");
			//拼接建表语句
			createSQL.append("DROP TABLE IF EXISTS MYCAT_SEQUENCE;").append("\n")
					 .append("CREATE TABLE MYCAT_SEQUENCE (").append("\n")
					 .append("	`name` VARCHAR (50) NOT NULL,").append("\n")
					 .append("	`current_value` INT NOT NULL,").append("\n")
					 .append("	`increment` INT NOT NULL DEFAULT 100,").append("\n")
					 .append("	PRIMARY KEY (`name`)").append("\n")
					 .append(") ENGINE = INNODB;").append("\n")
					 .append("INSERT INTO MYCAT_SEQUENCE (").append("\n")
					 .append("	`name`,").append("\n")
					 .append("	`current_value`,").append("\n")
					 .append("	`increment`").append("\n")
					 .append(")").append("\n")
					 .append("VALUES").append("\n")
					 .append("	('GLOBAL', 1, 100);").append("\n")
					 .append("DROP FUNCTION IF EXISTS `mycat_seq_currval`;").append("\n")
					 .append("CREATE FUNCTION `mycat_seq_currval` (seq_name VARCHAR(50)) RETURNS VARCHAR (64) CHARSET latin1 DETERMINISTIC").append("\n")
					 .append("BEGIN").append("\n")
					 .append("	DECLARE").append("\n")
					 .append("		retval VARCHAR (64);").append("\n")
					 .append("SET retval = \"-999999999,null\";").append("\n")
					 .append("SELECT").append("\n")
					 .append("	concat(").append("\n")
					 .append("		CAST(current_value AS CHAR),").append("\n")
					 .append("		\",\",").append("\n")
					 .append("		CAST(increment AS CHAR)").append("\n")
					 .append("	) INTO retval").append("\n")
					 .append("FROM").append("\n")
					 .append("	MYCAT_SEQUENCE").append("\n")
					 .append("WHERE").append("\n")
					 .append("	NAME = seq_name;").append("\n")
					 .append("RETURN retval;").append("\n")
					 .append("END ;").append("\n")
					 .append("DROP FUNCTION IF EXISTS `mycat_seq_nextval`;").append("\n")
					 .append("CREATE FUNCTION `mycat_seq_nextval` (seq_name VARCHAR(50)) RETURNS VARCHAR (64) CHARSET latin1 DETERMINISTIC").append("\n")
					 .append("BEGIN").append("\n")
					 .append("	UPDATE MYCAT_SEQUENCE").append("\n")
					 .append("SET current_value = current_value + increment").append("\n")
					 .append("WHERE").append("\n")
					 .append("	NAME = seq_name;").append("\n")
					 .append("RETURN mycat_seq_currval (seq_name);").append("\n")
					 .append("END ;").append("\n")
					 .append("DROP FUNCTION IF EXISTS `mycat_seq_setval`;").append("\n")
					 .append("CREATE FUNCTION `mycat_seq_setval` (").append("\n")
					 .append("seq_name VARCHAR (50),").append("\n")
					 .append("VALUE").append("\n")
					 .append("	INTEGER").append("\n")
					 .append(") RETURNS VARCHAR (64) CHARSET latin1 DETERMINISTIC").append("\n")
					 .append("BEGIN").append("\n")
					 .append("	UPDATE MYCAT_SEQUENCE").append("\n")
					 .append("SET current_value =").append("\n")
					 .append("VALUE").append("\n")
					 .append("WHERE").append("\n")
					 .append("	NAME = seq_name;").append("\n")
					 .append("RETURN mycat_seq_currval (seq_name);").append("\n")
					 .append("END ;").append("\n")
					 .append("");
			//加载mysql驱动
			Class.forName(driver);
			//新建连接
        	Connection connection = DriverManager.getConnection(databaseURL.toString(), writeHost.getUser(),writeHost.getPassword());
        	//新建预加载的查询语句
        	PreparedStatement preparedStatement = connection.prepareStatement(createSQL.toString());
        	preparedStatement.executeUpdate();
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
            //将存储全局序列的表名存储到redis中
            //获取redis对象
			jedis = jedisUtils.getJedis();
			String writeHostJson = JSONObject.toJSONString(writeHost);
			jedis.set(CM_REDIS_KEY.SEQUENCE_DATABASE_NAME, databaseName);
			jedis.set(CM_REDIS_KEY.SEQUENCE_DATABASE_INFO, writeHostJson);
			result = true;
			return result;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return result;
		}finally {
			if (jedis != null){
				jedis.close();
			}
		}
	}
	
	/**
	 * 保存生成的mycat配置文件
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:52:07
	 * @param configFilesContent
	 * @param databases
	 * @throws Exception
	 */
	@Override
	public boolean saveMycatConfigfiles(JSONObject configFilesContent) {
		boolean result = false;
		//schema.xml内容
		String schemaXmlContent = configFilesContent.getString("schemaXmlContent");
		String schemaFileName = "/schema.xml";
		//server.xml内容
		String serverXmlContent = configFilesContent.getString("serverXmlContent");
		String serverFileName = "/server.xml";
		//sequence_db_conf.properties内容
		String sequence_db_conf = configFilesContent.getString("sequence_db_conf");
		String sequenceFileName = "/sequence_db_conf.properties";
		//rule.xml内容
		String ruleXmlContent = configFilesContent.getString("ruleXmlContent");
		String ruleXmlFileName = "/rule.xml";
		
		String configFilePath = CM_MYCAT_CONFIGURATION.MYCAT_CONFIG_FILEPATH;
		
		try {
			File schemaXml = new File(configFilePath+schemaFileName);
			File serverXml = new File(configFilePath+serverFileName);
			File sequenceProperties = new File(configFilePath+sequenceFileName);
			File ruleXml = new File(configFilePath+ruleXmlFileName);
			
			if(!schemaXml.exists()){
				schemaXml.createNewFile();
			}
			if(!serverXml.exists()){
				serverXml.createNewFile();
			}
			if(!sequenceProperties.exists()){
				sequenceProperties.createNewFile();
			}
			if(!ruleXml.exists()){
				ruleXml.createNewFile();
			}
			
			FileWriter schemaXmlFileWriter = new FileWriter(schemaXml);
			FileWriter serverXmlFileWriter = new FileWriter(serverXml);
			FileWriter sequencePropertiesFileWriter = new FileWriter(sequenceProperties);
			FileWriter ruleXmlFileWriter = new FileWriter(ruleXml);
			
			PrintWriter schemaXmlPrintWriter = new PrintWriter(schemaXmlFileWriter);
			PrintWriter serverXmlPrintWriter = new PrintWriter(serverXmlFileWriter);
			PrintWriter sequencePropertiesPrintWriter = new PrintWriter(sequencePropertiesFileWriter);
			PrintWriter ruleXmlPrintWriter = new PrintWriter(ruleXmlFileWriter);
			
			schemaXmlPrintWriter.println(schemaXmlContent);
			serverXmlPrintWriter.println(serverXmlContent);
			sequencePropertiesPrintWriter.println(sequence_db_conf);
			ruleXmlPrintWriter.println(ruleXmlContent);
			
			schemaXmlFileWriter.close();
			serverXmlFileWriter.close();
			sequencePropertiesFileWriter.close();
			ruleXmlFileWriter.close();
			
			schemaXmlPrintWriter.close();
			serverXmlPrintWriter.close();
			sequencePropertiesPrintWriter.close();
			ruleXmlPrintWriter.close();
			
			result = true;
			return result;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return result;
		}
	}

	/**
	 * 重启mycat使配置生效
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:53:49
	 * @throws Exception
	 */
	@Override
	public boolean restartMycat() {
		boolean result = false;
		InputStream in = null;
		try {
			String mycatBinPath = CM_MYCAT_CONFIGURATION.MYCAT_BIN_PATH;
			StringBuffer command = new StringBuffer();
			command.append("sh ").append(mycatBinPath).append("/mycat restart");
			Process pro = Runtime.getRuntime().exec(command.toString());
			pro.waitFor();
			in = pro.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String executeResult = read.readLine();
			log.info(executeResult);
			result = true;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	
}
