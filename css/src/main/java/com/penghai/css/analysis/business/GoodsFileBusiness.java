package com.penghai.css.analysis.business;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * xml文件获取处理
 * @author 秦超
 * @time 2017年5月10日
 */
public interface GoodsFileBusiness {
	/**
	 * 获取单条订单xml详情
	 * @author 秦超
	 * @time 2017年5月10日
	 */
	Map<String, Object> getXmlInfo(String goodsINfoId);
	/**
	 * 获取linker所需xml片段
	 * @author 秦超
	 * @time 2017年5月10日
	 */
	String getDatabaseInfoFromXml(String linkerId);
	
	/**
	 * 获取linker所需xml片段--包含database和targetDatabase
	 * @author 徐超
	 * @Date 2017年6月8日 上午11:39:49
	 * @param linkerId
	 * @return
	 */
	JSONObject getDatabaseAndTargetDatabaseInfoFromXml(String linkerId);

}
