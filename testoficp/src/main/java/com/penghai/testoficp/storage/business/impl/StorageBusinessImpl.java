package com.penghai.testoficp.storage.business.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.testoficp.storage.business.StorageBusinessI;

/**
 * 存储模块业务类
 * @author 徐超
 * @Date 2017年5月10日 上午9:48:41
 */
@Service("storageBusinessImpl")
public class StorageBusinessImpl implements StorageBusinessI{

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
			sql.append(" USE ").append(databaseName).append("; ");
			//获取数据数组
			JSONArray dataArray = jsonObject.getJSONArray("data");
			//循环获取表数据
			for(int i=0;i<dataArray.size();i++){
				JSONObject tableBody = dataArray.getJSONObject(i);
				String tableName = tableBody.getString("table");
				JSONArray tableData = tableBody.getJSONArray("tableData");
				sql.append(" INSERT INTO ").append(tableName).append(" VALUES ");
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
	 * 将批量的Josn数据转为可执行的sql,一个sql
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
	
}
