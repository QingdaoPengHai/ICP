package com.penghai.css.management.business;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author 秦超
 * @time 2017年5月10日
 */
public interface DataCountBusiness {
	/**
	 * 获取数据库列表
	 * @author 秦超
	 */
	String getDatabaseList();
	/**
	 * 获取数据库下表列表及统计信息
	 * @author 秦超
	 */
	String getTableDataCount(String databaseName);
	/**
	 * 获取数据库统计信息
	 * @author 秦超
	 */
	String getDatabaseDataCount();

	/**
	 * 基于mongoDB的获取数据库信息
	 * @author 徐超
	 * @Date 2017年7月12日 下午4:00:56
	 * @return
	 */
	JSONObject getDatabaseInfo();
}
