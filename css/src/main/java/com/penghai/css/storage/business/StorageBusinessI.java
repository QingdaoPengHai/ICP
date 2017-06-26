package com.penghai.css.storage.business;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * 存储模块业务类接口
 * @author 徐超
 * @Date 2017年5月10日 上午9:53:18
 */
public interface StorageBusinessI {

	/**
	 * 将Josn数据转为可执行的sql
	 * @author 徐超
	 * @Date 2017年5月9日 下午3:21:35
	 * @param json
	 * @return
	 */
	String transferJsonToSql(String json) throws Exception;
	
	/**
	 * 将批量的JosnSet数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	String transferBulkJsonToSql(Set<String> jsonSet) throws Exception;
	
	/**
	 * 将批量的JosnList数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	String transferBulkJsonListToSql(List<String> jsonList) throws Exception;
	
	/**
	 * 将批量的JosnList数据转为MongoDB支持的List<DBObject>
	 * @author 徐超
	 * @Date 2017年6月20日22:31:10
	 * @param jsonList
	 * @return List<DBObject>
	 * @throws Exception
	 */
	JSONObject transferListJsonToListDBObject(List<String> jsonList) throws Exception;
	
	/**
	 * 将MongoDB支持的List<DBObject>写入MongoDB
	 * @author 徐超
	 * @Date 2017年6月20日22:32:20
	 * @param dbObjectList
	 * @return WriteResult
	 * @throws Exception
	 */
	void insertDatasIntoMongoDB(JSONObject dataJSONObject) throws Exception;
}
