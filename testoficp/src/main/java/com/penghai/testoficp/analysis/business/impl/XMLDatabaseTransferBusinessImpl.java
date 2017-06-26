package com.penghai.testoficp.analysis.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghai.testoficp.analysis.business.XMLDatabaseTransferBusinessI;
import com.penghai.testoficp.analysis.dao.mybatis.XMLDatabaseTransferDaoI;
import com.penghai.testoficp.analysis.model.Schema;
import com.penghai.testoficp.util.xml.XmlUtil;
import com.penghai.testoficp.util.xml.XmlUtil.CollectionWrapper;

/**
 * 数据分析xml文本解析接口实现服务类
 * @author 徐超
 * @Date 2017年5月10日 上午10:00:04
 */
@Service("xmlDatabaseTransferBusinessI")
public class XMLDatabaseTransferBusinessImpl implements XMLDatabaseTransferBusinessI{

	@Autowired
	private XMLDatabaseTransferDaoI xmlDatabaseTransferDaoI;
	
	
	/**
	 * 将XML文本解析为SQL
	 * @author 徐超
	 * @Date 2017年5月8日 上午10:54:00
	 * @param XMLString
	 * @return
	 */
	@Override
	public String analyzeXMLStringToSQL(String XMLString){
		String sql = "";
		Schema schemaObject = new Schema();
		try {
			XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
			schemaObject = resultBinder.fromXml(XMLString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql = schemaObject.conbineAllCreateSQL();
		return sql;
	}
	
	/**
	 * 执行建库sql
	 * @author 徐超
	 * @Date 2017年5月7日 下午4:47:11
	 * @param sql 建库语句
	 * @return
	 */
	@Override
	public List<LinkedHashMap<String, Object>> executeSQL(String sqlString){
		List<LinkedHashMap<String, Object>> resultMap = new ArrayList<LinkedHashMap<String, Object>>();
		try {
			resultMap = xmlDatabaseTransferDaoI.executeSQL(sqlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
