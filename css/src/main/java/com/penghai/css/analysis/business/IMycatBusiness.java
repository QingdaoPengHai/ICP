package com.penghai.css.analysis.business;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.analysis.model.Database;
import com.penghai.css.analysis.model.MycatWriteHost;
import com.penghai.css.analysis.model.Schema;

/**
 * mycat相关业务接口类
 * @author 徐超
 * @Date 2017年6月4日 下午6:38:10
 */
public interface IMycatBusiness {

	/**
	 * 将schema的xml文本转换为schema对象
	 * @author 徐超
	 * @Date 2017年6月4日 下午7:10:30
	 * @param xmlContent
	 * @return
	 */
	Schema analyzedSchemaXmlContent(String xmlContent);
	
	/**
	 * 获取配置文件中配置好的writeHosts
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:39:52
	 * @return
	 */
	List<MycatWriteHost> getWriteHosts();
	
	/**
	 * 分别在writeHost上执行建库建表
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:41:58
	 * @param writeHosts
	 * @param createSQL
	 * @throws Exception
	 */
	boolean createDatabasesAndTablesOnWriteHosts(List<MycatWriteHost> writeHosts ,String createSQL);
	
	/**
	 * 根据用户定义的writeHosts生成mycat配置文件内容
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:43:32
	 * @param writeHosts
	 * @return
	 */
	JSONObject getMycatConfigFilesContent(Schema schema);
	
	/**
	 * 创建全局序列
	 * @author 徐超
	 * @Date 2017年6月6日 上午11:43:08
	 * @param dataNodeName
	 * @return
	 */
	boolean createGlobalSequence(Database database,MycatWriteHost writeHost);
	
	/**
	 * 保存生成的mycat配置文件
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:52:07
	 * @param configFilesContent
	 * @param databases
	 * @throws Exception
	 */
	boolean saveMycatConfigfiles(JSONObject configFilesContent);
	
	/**
	 * 重启mycat使配置生效
	 * @author 徐超
	 * @Date 2017年6月4日 下午6:53:49
	 * @throws Exception
	 */
	boolean restartMycat();
}
