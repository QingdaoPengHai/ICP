package com.penghai.css.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
/**
 * mongodb操作工具类
 * 
 * @author 李浩
 * 
 */
@Component
public class MongoUtil {
	@Autowired
	private MongoClient mongoClient;

	/**
	 * 查询重复数据库及集合名，返回相应的信息
	 * 
	 * @param newDBnames
	 * @param newTableNames
	 * @return
	 */
	public String queryIfExistDatabase(Set<String> newDBnames, Set<String> newTableNames) {
		Set<String> existDBNames = listToSet(mongoClient.getDatabaseNames());
		// 中间结果集
		Set<String> dbresult = new HashSet<String>();
		Set<String> tableresult = new HashSet<String>();
		// 设置返回消息
		StringBuffer message = new StringBuffer();

		// 数据库不存在
		if (existDBNames.isEmpty() || existDBNames == null)
			message.append("数据已校验：可下载");
		// 数据库存在
		else {
			dbresult.clear();
			dbresult.addAll(existDBNames);
			dbresult.retainAll(newDBnames);
			// 无交集数据库
			if (dbresult.isEmpty()) {
				message.append("数据已校验:无重复数据库");
			}
			// 有交集数据库
			else {
				message.append("数据已校验：重复数据库(表)有  ");
				for (String dbName : dbresult) {
					message.append(dbName + "(");
					DB db = getDataBases(dbName);
					Set<String> existTableNames = db.getCollectionNames();
					tableresult.clear();
					tableresult.addAll(existTableNames);
					tableresult.retainAll(newTableNames);
					// 无重复表
					if (tableresult.isEmpty()) {
						message.append("表无重复");
					}
					// 有重复表
					else {
						for (String tname : tableresult) {
							message.append(tname + " ");
						}
					}
					message.append(") ");
				}
			}
		}

		return message.toString();
	}

	/**
	 * list to set method(String)
	 * 
	 * @param list
	 * @return
	 */
	public Set<String> listToSet(List<String> list) {
		Set<String> set = new HashSet<String>(list);
		return set;
	}
	/**
	 * 选择数据库，若不存在则创建新数据库
	 * 
	 * @param name
	 * @return
	 */
	public DB getDataBases(String name) {
		DB mydb = mongoClient.getDB(name);
		return mydb;
	}

	/**
	 * 判断当前是否为分片集群
	 * 
	 * @return
	 */
	public boolean isShardServer() {
		DB config = mongoClient.getDB("config");
		Set<String> names = config.getCollectionNames();
		if (names.size() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 选择数据集合，若不存在则创建
	 * 
	 * @param name
	 * @return
	 */
	public DBCollection useCollection(DB db, String name) {
		DBCollection mycollection = db.getCollection(name);
		return mycollection;
	}

	/**
	 * 返回所有的数据库名称
	 * 
	 * @return
	 */
	public List<String> getAllDataBases() {
		if (mongoClient != null) {
			return mongoClient.getDatabaseNames();
		} else {
			return null;
		}
	}

	/**
	 * 返回当前数据库下所有的集合名
	 * 
	 * @return
	 */
	public Set<String> getAllCollections(DB db) {
		if (db != null) {
			return db.getCollectionNames();
		} else {
			return null;
		}
	}

	/**
	 * 根据数据库名获取该数据库下所有集合名及文档数目
	 * 
	 * @param databaseName
	 * @return
	 */
	public List<HashMap<String, Object>> getDatabaseTableCount(String databaseName) {
		List<HashMap<String, Object>> result = new ArrayList<>();
		// 根据数据库得到数据库实例
		DB db = mongoClient.getDB(databaseName);
		Set<String> collectionNames = db.getCollectionNames();
		for (String collectionName : collectionNames) {
			HashMap<String, Object> map = new HashMap<>();
			DBCollection collection = db.getCollection(collectionName);

			// 放入集合名及集合文档数
			map.put("tableName", collectionName);
			map.put("count", collection.count());
			result.add(map);
		}
		releaseMongo();
		return result;
	}

	/**
	 * 释放mongodb资源 --测试一下好不好用
	 */
	public void releaseMongo() {
		if (mongoClient != null)
			mongoClient.close();
	}

}
