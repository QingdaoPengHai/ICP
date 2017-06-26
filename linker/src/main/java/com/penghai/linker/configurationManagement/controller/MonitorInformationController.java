package com.penghai.linker.configurationManagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IConectionStatusBusiness;
import com.penghai.linker.configurationManagement.business.IDataCountBusiness;
import com.penghai.linker.configurationManagement.business.ILogMonitorBusiness;

/**
 * 监控页面请求处理
 * @author 秦超
 * @time 2017年5月18日
 */
@Controller
@RequestMapping("/monitor")
public class MonitorInformationController {
	@Autowired
	private ILogMonitorBusiness logMonitorBusiness;
	@Autowired
	private IDataCountBusiness dataCountBusiness;
	@Autowired
	private IConectionStatusBusiness iConectionStatusBusiness;
	
	/**
	 * 获取最新日志信息
	 * @author 秦超
	 * @return
	 * @time 2017年5月22日
	 */
	@RequestMapping("/getRecentLog")
	@ResponseBody
	public JSONObject getRecentLog(){
		JSONObject resultJson = new JSONObject();
		try {
			resultJson = logMonitorBusiness.getRecentLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultJson;
	}
	
	/**
	 * 数据表信息统计、最新数据显示
	 * @author 秦超
	 * @return
	 * @time 2017年5月22日
	 */
	@RequestMapping("/getTablesAcount")
	@ResponseBody
	public JSONObject getTablesAcount(){
		JSONObject resultJson = new JSONObject();
		resultJson = dataCountBusiness.getTablesAcount();
		return resultJson;
	}
	/**
	 * 获取服务器连接状态
	 * @author 秦超
	 * @return
	 * @time 2017年5月26日
	 */
	@RequestMapping("/getConectionStatus")
	@ResponseBody
	public JSONObject getConectionStatus(){
		JSONObject resultJson = new JSONObject();
		resultJson = iConectionStatusBusiness.getConectionStatus();
		return resultJson;
	}
}
