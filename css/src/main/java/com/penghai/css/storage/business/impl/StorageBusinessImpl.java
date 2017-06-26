package com.penghai.css.storage.business.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.css.storage.business.StorageBusinessI;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_INFO_DATA;
import com.penghai.css.util.CommonData.CM_REDIS_KEY;
import com.penghai.css.util.JedisUtils;

import redis.clients.jedis.Jedis;

/**
 * 存储模块业务类
 * @author 徐超
 * @Date 2017年5月10日 上午9:48:41
 */
@Service("storageBusinessImpl")
public class StorageBusinessImpl implements StorageBusinessI{
	
	//redis
	@Autowired
	private JedisUtils jedisUtils = null;
	
	Logger log = Logger.getLogger(StorageBusinessImpl.class);

	/**
	 * 将Josn数据转为可执行的sql
	 * @author 徐超
	 * @Date 2017年5月9日 下午3:23:20
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferJsonToSql(String json) throws Exception {
		StringBuffer sql = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			//将jsonString转为JSONObject对象
			jsonObject = JSONObject.parseObject(json);
			//获取数据库名称
			String databaseName = jsonObject.getString("database");
			//开始拼接sql
//			sql.append(" USE ").append(databaseName).append("; ");
			//获取数据数组
			JSONArray dataArray = jsonObject.getJSONArray("data");
			//循环获取表数据
			for(int i=0;i<dataArray.size();i++){
				JSONObject tableBody = dataArray.getJSONObject(i);
				String tableName = tableBody.getString("table");
				JSONArray tableData = tableBody.getJSONArray("tableData");
				sql.append(" INSERT INTO ").append(databaseName).append(".").append(tableName).append(" VALUES ");
				//循环获取字段列表
				for(int j=0;j<tableData.size();j++){
					JSONArray rowData = tableData.getJSONArray(j);
					String rowDataString = rowData.toJSONString();
					rowDataString = rowDataString.replace("[", "(").replace("]", ")");
					sql.append(rowDataString).append(",");
				}
				//删除最后一个逗号
				sql.deleteCharAt(sql.length()-1);
				//拼接结束
				sql.append("; ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql.toString();
	}
	
	/**
	 * 将Josn数据转为可执行的sql,for Mycat
	 * @author 徐超
	 * @Date 2017年6月8日22:31:17
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferJsonToSqlForMycat(String json,long sequenceStart) throws Exception {
		StringBuffer sql = new StringBuffer();
		long sequenceStartInside = sequenceStart;
		try {
			JSONObject jsonObject = new JSONObject();
			//将jsonString转为JSONObject对象
			jsonObject = JSONObject.parseObject(json);
			//获取数据库名称
			String databaseName = jsonObject.getString("database");
			//开始拼接sql
//			sql.append(" USE ").append(databaseName).append("; ");
			//获取数据数组
			JSONArray dataArray = jsonObject.getJSONArray("data");
			//循环获取表数据
			for(int i=0;i<dataArray.size();i++){
				JSONObject tableBody = dataArray.getJSONObject(i);
				String tableName = tableBody.getString("table");
				JSONArray tableData = tableBody.getJSONArray("tableData");
				String columus = tableBody.getString("columus");
				String primaryKey = tableBody.getString("primaryKey");
				//获取主键位置
				int primaryKeyIndex = getPrimaryKeyIndex(primaryKey,columus);
				sql.append(" INSERT INTO ")
				   .append(databaseName)
				   .append(".").append(tableName)
				   .append(columus)
				   .append(" VALUES ");
				//循环获取字段列表
				for(int j=0;j<tableData.size();j++){
					JSONArray rowData = tableData.getJSONArray(j);
					String rowDataString = rowData.toJSONString();
//					rowDataString = rowDataString.replace("[", "(").replace("]", ")");
					rowDataString = replacePrimaryKey(rowDataString,primaryKeyIndex,sequenceStartInside);
					sequenceStartInside++;
					sql.append(rowDataString).append(",");
				}
				//删除最后一个逗号
				sql.deleteCharAt(sql.length()-1);
				//拼接结束
				sql.append("; ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql.toString();
	}

	/**
	 * 将批量的JosnSet数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferBulkJsonToSql(Set<String> jsonSet) throws Exception{
		StringBuffer sql = new StringBuffer();
		//循环set对象,分别转换每一个sql
		for(String jsonString : jsonSet){
			sql.append(transferJsonToSql(jsonString));
		}
		return sql.toString();
	}
	
	/**
	 * 将批量的JosnList数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferBulkJsonListToSql(List<String> jsonList) throws Exception{
		StringBuffer sql = new StringBuffer();
		//循环List对象,分别转换每一个sql
		for(String jsonString : jsonList){
			sql.append(transferJsonToSql(jsonString));
		}
		return sql.toString();
	}
	/**
	 * 将批量的JosnList数据转为可执行的sql,一个sql,for Mycat
	 * @author 徐超
	 * @Date 2017年6月8日 下午10:28:32
	 * @param jsonList
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferBulkJsonListToSqlForMycat(List<String> jsonList,long currentSequence) throws Exception{
		StringBuffer sql = new StringBuffer();
		int insertNumber = 0;
		//循环List对象,分别转换每一个sql
		for(String jsonString : jsonList){
			long sequenceStart = currentSequence+insertNumber;
			sql.append(transferJsonToSqlForMycat(jsonString,sequenceStart));
			JSONObject jsonObject = new JSONObject();
			//将jsonString转为JSONObject对象
			jsonObject = JSONObject.parseObject(jsonString);
			//获取数据数组
			JSONArray dataArray = jsonObject.getJSONArray("data");
			for(int i=0;i<dataArray.size();i++){
				JSONObject tableBody = dataArray.getJSONObject(i);
				JSONArray tableData = tableBody.getJSONArray("tableData");
				insertNumber = insertNumber+tableData.size();
			}
		}
		return sql.toString();
	}
	
	/**
	 * 获取当次插入的数据数量
	 * @author 徐超
	 * @Date 2017年6月9日 上午8:34:59
	 * @return
	 */
	@Override
	public int getInsertNumber(List<String> jsonList){
		int totalNum = 0;
		
		for(String jsonString : jsonList){
			
			JSONObject jsonObject = new JSONObject();
			//将jsonString转为JSONObject对象
			jsonObject = JSONObject.parseObject(jsonString);
			//获取数据数组
			JSONArray dataArray = jsonObject.getJSONArray("data");
			for(int i=0;i<dataArray.size();i++){
				JSONObject tableBody = dataArray.getJSONObject(i);
				JSONArray tableData = tableBody.getJSONArray("tableData");
				totalNum = totalNum + tableData.size();
			}
			
		}
		
		return totalNum;
	}
	/**
	 * 获取并修改全局序列信息
	 * @author 徐超
	 * @Date 2017年6月9日 上午8:45:15
	 * @param insertNumber 当次写入的条数
	 * @return long 本次序列的起始值 -1表示有错误
	 */
	@Override
	public long getAndChangeCurrentsequenceInfo(int insertNumber){
		Jedis jedis = null;
		long sequenceStart = 0L;
		String sequenceDatabaseInfo = "";
		String sequenceDatabaseName = "";
		try {
			//获取redis对象
			jedis = jedisUtils.getJedis();
			sequenceDatabaseInfo = jedis.get(CM_REDIS_KEY.SEQUENCE_DATABASE_INFO);
			sequenceDatabaseName = jedis.get(CM_REDIS_KEY.SEQUENCE_DATABASE_NAME);
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			return -1;
		} finally {
			if (jedis != null){
				jedis.close();
			}
		}
		//1.查询当前数值
		if(null==sequenceDatabaseInfo 
				|| "".equals(sequenceDatabaseInfo) 
				|| null==sequenceDatabaseName 
				|| "".equals(sequenceDatabaseName)){
			return -1;
		}
		JSONObject sequenceDatabaseInfoJson = JSONObject.parseObject(sequenceDatabaseInfo);
		String databaseUser = sequenceDatabaseInfoJson.getString("user");
		String databasePassword = sequenceDatabaseInfoJson.getString("password");
		String databaseUrl = sequenceDatabaseInfoJson.getString("url");
		StringBuffer databaseConnectionURL = new StringBuffer();
		databaseConnectionURL.append("jdbc:mysql://")
			.append(databaseUrl).append("/")
			.append(sequenceDatabaseName)
			.append("?characterEncoding=utf8&allowMultiQueries=true");
		StringBuffer selectSQL = new StringBuffer();
		selectSQL.append("SELECT current_value,increment FROM MYCAT_SEQUENCE;");
		String driver = CM_CONFIG_PROPERTIES.DATABASE_DRIVER;
		long current_value = 0L;
		int increment = 0;
		try {
			Class.forName(driver);
	    	//新建连接
	    	Connection connection = DriverManager.getConnection(databaseConnectionURL.toString(), databaseUser,databasePassword);
	    	//新建预加载的查询语句
        	PreparedStatement preparedStatementSelect = connection.prepareStatement(selectSQL.toString());
        	//结果集
        	ResultSet resultSet = preparedStatementSelect.executeQuery();
        	while (resultSet.next()) {
        		current_value = resultSet.getLong(1);
        		increment = resultSet.getInt(2);
        	}
        	long updateCurrentValue = insertNumber+current_value;
        	StringBuffer updateSQL = new StringBuffer();
        	updateSQL.append("UPDATE MYCAT_SEQUENCE SET current_value = ")
        			 .append(updateCurrentValue)
        			 .append(";");
        	
        	//新建预加载的查询语句
        	PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateSQL.toString());
        	//更新
        	preparedStatementUpdate.executeUpdate();
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
		}
		sequenceStart = current_value;
		return sequenceStart;
	}
	/**
	 * 获取主键的位置
	 * @author 徐超
	 * @Date 2017年6月9日 下午1:04:51
	 * @param primaryKey
	 * @param columns
	 * @return
	 */
	public int getPrimaryKeyIndex(String primaryKey,String columns){
		int index = 0;
		
		columns = columns.substring(1, columns.length()-1);
		String[] columnsArray = columns.split(",");
		for(int i=0;i<columnsArray.length;i++){
			if(primaryKey.equals(columnsArray[i])){
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * 替换数据中主键的值为全局序列的值
	 * @author 徐超
	 * @Date 2017年6月9日 下午1:26:23
	 * @param rowDataString
	 * @param primaryKeyIndex
	 * @param sequenceStartInside
	 * @return
	 */
	public String replacePrimaryKey(String rowDataString,int primaryKeyIndex,long sequenceStartInside){
		String rowDataStringTemp = rowDataString;
		StringBuffer rowDataStringReturn = new StringBuffer();
		
		rowDataStringTemp = rowDataStringTemp.substring(1, rowDataStringTemp.length()-1);
		String[] rowDataArray = rowDataStringTemp.split(",");
		rowDataArray[primaryKeyIndex] = "\""+sequenceStartInside+"\"";
		
		rowDataStringReturn.append("(");
		for(int i=0;i<rowDataArray.length;i++){
			
			rowDataStringReturn.append(rowDataArray[i]).append(",");
			
		}
		//删除最后一个逗号
		rowDataStringReturn.deleteCharAt(rowDataStringReturn.length()-1);
		rowDataStringReturn.append(")");
		
		return rowDataStringReturn.toString();
	}
}
