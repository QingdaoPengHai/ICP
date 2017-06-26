package com.penghai.linker.configurationManagement.business;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据统计业务接口
 * @author 秦超
 * @time 2017年5月22日
 */
public interface IDataCountBusiness {
	/**
	 * 数据表信息统计、最新数据显示
	 * @author 秦超
	 * @return
	 * @time 2017年5月22日
	 */
	JSONObject getTablesAcount();
}
