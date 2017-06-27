package com.penghai.css.storage.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.penghai.css.storage.business.StorageBusinessI;
import com.penghai.css.util.MongoUtil;

/**
 * 存储模块业务类
 * 
 * @author 徐超
 * @Date 2017年5月10日 上午9:48:41
 */
@Service("storageBusinessImpl")
public class StorageBusinessImpl implements StorageBusinessI{

	@Autowired
	private MongoUtil mongoUtil;
	
	/**
	 * 将Josn数据转为可执行的sql
	 * 
	 * @author 徐超
	 * @Date 2017年5月9日 下午3:23:20
	 * @param json
	 * @return
	 * @throws Exception
	 *             dsa
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
	 * 将批量的JosnSet数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
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
	public String transferBulkJsonListToSql(List<String> jsonList) throws Exception{
		StringBuffer sql = new StringBuffer();
		//循环List对象,分别转换每一个sql
		for(String jsonString : jsonList){
			sql.append(transferJsonToSql(jsonString));
		}
		return sql.toString();
	}
	/**
	 * 将批量的JosnList数据转为MongoDB支持的List<DBObject>
	 * @author 徐超
	 * @Date 2017年6月20日22:31:10
	 * @param jsonList
	 * @return List<DBObject>
	 * @throws Exception
	 */
	public JSONObject transferListJsonToListDBObject(List<String> jsonList) throws Exception{
		//定义返回的JSON对象
		JSONObject resultJson = new JSONObject();
		//定义存放数据的JSON数组
		JSONArray dataListArray = new JSONArray();
		//数据库名称
		String databaseName = "";
		//遍历每一个JSONString
		for(String jsonString:jsonList){
			//将List中的每一个JSONString转为JSONObject
			JSONObject jsonObject = new JSONObject();
			jsonObject = JSONObject.parseObject(jsonString);
			//获取数据库名称
			databaseName = jsonObject.getString("database");
			//获取数据数组
			JSONArray dataArray = jsonObject.getJSONArray("data");
			//循环获取表数据
			for(int i=0;i<dataArray.size();i++){
				JSONObject collectionJson = new JSONObject();
				List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
				JSONObject tableBody = dataArray.getJSONObject(i);
				//获取将要写入mongo的collection的名字
				String collectionName = tableBody.getString("table");
				
				collectionJson.put("collectionName", collectionName);
				JSONArray tableData = tableBody.getJSONArray("tableData");
				//循环获取字段列表
				for(int j=0;j<tableData.size();j++){
					JSONObject rowData = tableData.getJSONObject(j);
					//组装数据Map
					Map<String,Object> map = new HashMap<String,Object>(rowData);
					mapList.add(map);
				}
				collectionJson.put("dataMapList", mapList);
				dataListArray.add(collectionJson);
			}
		}
		resultJson.put("databaseName", databaseName);
		resultJson.put("dataList", dataListArray);
		
		return resultJson;
	}
	
	/**
	 * 将MongoDB支持的List<DBObject>写入MongoDB
	 * @author 徐超
	 * @Date 2017年6月20日22:32:20
	 * @param dbObjectList
	 * @return WriteResult
	 * @throws Exception
	 */
	public void insertDatasIntoMongoDB(JSONObject dataJSONObject) throws Exception{
		//获取数据库名称
		String databaseName = dataJSONObject.getString("databaseName");
		JSONArray dataListArray = dataJSONObject.getJSONArray("dataList");
		//获取数据库对象
		DB db = mongoUtil.getDataBases(databaseName);
		//遍历数据数组,分别向每一个collection写入数据
		for(int i=0;i<dataListArray.size();i++){
			
			JSONObject collectionObject = dataListArray.getJSONObject(i);
			String collectionName = collectionObject.getString("collectionName");
			List<Map<String,Object>> dataMapList = 
					(List<Map<String,Object>>) collectionObject.get("dataMapList");
			//获取collection对象
			DBCollection collection = mongoUtil.useCollection(db,collectionName);
			
			List<DBObject> dbOblectList = new ArrayList<DBObject>();
			//遍历数据Map
			for(Map<String,Object> map:dataMapList){
				DBObject dbObject = new BasicDBObject(map);
				//组装要写入MongoDB的DBObject
				dbOblectList.add(dbObject);
			}
			//执行写入
			collection.insert(dbOblectList);
		}
	}
}
