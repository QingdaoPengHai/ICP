package com.penghai.linker.configurationManagement.business;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取服务器连接状态接口
 * @author 秦超
 * @time 2017年5月26日
 */
public interface IConectionStatusBusiness {
	/**
	 * 获取服务器连接状态
	 * @author 秦超
	 * @time 2017年5月26日
	 */
	JSONObject getConectionStatus();

}
