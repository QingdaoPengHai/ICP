package com.penghai.testoficp.analysis.dao.mybatis;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 数据分析Mybatis接口类
 * @author 徐超
 * @Date 2017年5月10日 上午9:49:41
 */
@Repository
public interface XMLDatabaseTransferDaoI {

	/**
	 * 执行指定的SQL
	 * @author 徐超
	 * @Date 2017年5月9日 下午4:14:07
	 * @param sqlString
	 * @return
	 * @throws Exception
	 */
	List<LinkedHashMap<String, Object>> executeSQL(String sqlString) throws Exception;
	
	/**
	 * 获取一个测试JSON字符串--正式发布需删除
	 * @author 徐超
	 * @Date 2017年5月9日 下午4:29:17
	 * @return
	 */
	String getJson();
}
