package com.penghai.css.management.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghai.css.management.business.IQueryIfExistSchema;
import com.penghai.css.management.model.databaseModel.Database;
import com.penghai.css.management.model.databaseModel.Schema;
import com.penghai.css.management.model.databaseModel.Table;
import com.penghai.css.util.MongoUtil;

/**
 * 检查schema中数据库及表名是否重复
 * 
 * @author 李浩
 *
 */
@Service
public class QueryIfExistSchema implements IQueryIfExistSchema {
	@Autowired
	private MongoUtil mongoUtil;
	@Override
	public Map<String, Object> queryIfExistSchema(Schema schema) {
		Map<String, Object> map = new HashMap<>();
		//获取数据库列表
		List<Database> databases = schema.getDatabases();
		//所有数据库列表名
		List<String> newDBNames = new ArrayList<>();
		//所有集合列表名
		List<String> newTableNames = new ArrayList<>();
		for (Database database : databases) {
			newDBNames.add(database.getName());
			// 遍历库中所有的表，得到表名
			for(Table table:database.getTables()){
				newTableNames.add(table.getName());
			}
		}
		String resultMessage = mongoUtil.queryIfExistDatabase(mongoUtil.listToSet(newDBNames),
				mongoUtil.listToSet(newTableNames));
		map.put("code", 2);
		map.put("message", resultMessage);
		return map;
	}



}
