package com.penghai.css.analysis.business;

import java.util.LinkedHashMap;
import java.util.List;
/**
 * 数据分析xml文本解析接口类
 * @author 徐超
 * @Date 2017年5月10日 上午10:00:35
 */
public interface XMLDatabaseTransferBusinessI {

	/**
	 * 将XML文本解析为SQL
	 * @author 徐超
	 * @Date 2017年5月8日 上午10:54:00
	 * @param XMLString
	 * @return
	 */
	String analyzeXMLStringToSQL(String XMLString);
	
	
	
	
	
	/**
	 * 执行指定的SQL语句
	 * @author 徐超
	 * @Date 2017年5月8日 上午10:52:00
	 * @param sqlString
	 * @return
	 */
	List<LinkedHashMap<String, Object>> executeSQL(String sqlString);
	
	
}
