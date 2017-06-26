package com.penghai.css.management.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.penghai.css.management.business.ICreateDataBaseAndTableByMongo;
import com.penghai.css.management.model.databaseModel.Schema;
import com.penghai.css.util.MongoUtil;

/**
 * 利用schema信息和mongodb创建数据库及集合，并建立索引
 * 
 * @author 李浩
 *
 */
@Service
public class CreateDataBaseAndTableByMongo implements ICreateDataBaseAndTableByMongo {
	@Autowired
	private MongoUtil mongoUtil;
	@Override
	public void createDataBaseAndTable(Schema schema) {
		HashMap<String, Map<String, List<String>>> indexs = schema.getIndexNames();
		if(indexs!=null)
			executeCreateJob(indexs);
			
	}

	/**
	 * 执行创建索引并进行分片
	 * 
	 * @param indexs
	 */
	public void executeCreateJob(HashMap<String, Map<String, List<String>>> indexs) {
		for (String database : indexs.keySet()) {
			DB db = mongoUtil.getDataBases(database);
			Set<String> collectionNames = db.getCollectionNames();
			// 若数据库已存在则不创建
			if (collectionNames.size() > 0) {
				continue;
			}
			// 数据库不存在 创建集合建立索引
			for (String table : indexs.get(database).keySet()) {
				DBCollection collection = mongoUtil.useCollection(db, table);
				List<String> indexList = indexs.get(database).get(table);
				for (String index : indexList) {
					collection.createIndex(index);
				}
			}
			if (!mongoUtil.isShardServer())
				continue;
			Set<String> cnames = db.getCollectionNames();
			// 开启数据库分片
			db = mongoUtil.getDataBases("admin");
			BasicDBObject object = new BasicDBObject();
			object.append("enablesharding", database);
			db.command(object);
			// 分别对集合进行分片处理
			// 重新查询集合名
			collectionNames = db.getCollectionNames();
			for (String name : cnames) {
				db = mongoUtil.getDataBases(database);
				DBCollection collection = db.getCollection(name);
				// 查询集合中的索引，找到除_id之外的第一索引作为片键
				List<DBObject> indexInfos = collection.getIndexInfo();
				if (indexInfos.size() == 1)
					continue;
				DBObject index = (DBObject) indexInfos.get(1).get("key");
				Set<String> keySet = index.keySet();
				// 得到分片键
				String key = "";
				for (String k : keySet) {
					key = k;
				}
				// 对集合进行分片
				db = mongoUtil.getDataBases("admin");
				BasicDBObject cmd = new BasicDBObject();
				cmd.append("shardcollection", database + "." + name);
				BasicDBObject keyObject = new BasicDBObject(key, 1);
				cmd.append("key", keyObject);
				db.command(cmd);
			}
		}
	}

}
