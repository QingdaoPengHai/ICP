package com.penghai.linker.configurationManagement.business;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
/**
 * 日志监控业务接口
 * @author 秦超
 * @time 2017年5月22日
 */
public interface ILogMonitorBusiness {
	/**
	 * 获取最新的日志信息
	 * @author 秦超
	 * @return
	 * @throws IOException
	 * @time 2017年5月19日
	 */
	JSONObject getRecentLog() throws IOException;
}
