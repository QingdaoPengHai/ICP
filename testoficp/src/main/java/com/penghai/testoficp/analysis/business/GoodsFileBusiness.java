package com.penghai.testoficp.analysis.business;

import java.util.Map;

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

}
