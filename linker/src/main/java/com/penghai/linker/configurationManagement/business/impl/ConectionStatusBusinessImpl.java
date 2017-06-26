package com.penghai.linker.configurationManagement.business.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IConectionStatusBusiness;
import com.penghai.linker.util.EhcacheUtil;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;

/**
 * 获取服务器连接状态业务处理
 * @author 秦超
 * @time 2017年5月26日
 */
@Service
public class ConectionStatusBusinessImpl implements IConectionStatusBusiness{
	/**
	 * 获取服务器连接状态
	 * @author 秦超
	 * @time 2017年5月26日
	 */
	@Override
	public JSONObject getConectionStatus() {
		JSONObject resultJson = new JSONObject();
		
		EhcacheUtil ehcache = EhcacheUtil.getInstance();
		//获取缓存中存储的值
		Object sqlConectionStatus = ehcache.get(CM_CONFIG_PROPERTIES.SCADA_MYSQL_STATUS_CACHE_KEY);
		Object rabbitMqConectionStatus = ehcache.get(CM_CONFIG_PROPERTIES.CSS_MQ_STATUS_CACHE_KEY);
		
		resultJson.put("sqlConectionStatus", sqlConectionStatus);
		resultJson.put("rabbitMqConectionStatus", rabbitMqConectionStatus);
		resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		
		return resultJson;
	}
	
}
