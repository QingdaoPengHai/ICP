package com.penghai.testoficp.storage.business;

import java.util.Set;

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
	 * 将批量的Josn数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	String transferBulkJsonToSql(Set<String> jsonSet) throws Exception;
}
