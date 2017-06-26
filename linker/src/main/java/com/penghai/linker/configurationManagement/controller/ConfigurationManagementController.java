package com.penghai.linker.configurationManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IConfigurationManagementBusiness;
import com.penghai.linker.configurationManagement.model.LinkerConfiguration;

/**
 * 配置管理页面相关请求处理
 * @author 秦超
 * @time 2017年5月18日
 */
@Controller
@RequestMapping("/management")
public class ConfigurationManagementController{
	@Autowired
	private IConfigurationManagementBusiness configurationManagementBusiness;
	/**
	 * 跳转监控页面
	 * @author 秦超
	 * @param model
	 * @return
	 * @time 2017年5月18日
	 */
	@RequestMapping("/showManagePage")
	public ModelAndView showManagePage(Model model){
		return new ModelAndView("linkerAnalysis");
	}
	
	/**
	 * 获取配置信息
	 * @author 秦超
	 * @return
	 * @time 2017年5月18日
	 */
	@RequestMapping("/getConfigurations")
	@ResponseBody
	public JSONObject getConfigurations(){
		return configurationManagementBusiness.getConfigurations();
	}
	
	/**
	 * 保存配置信息
	 * @author 秦超
	 * @param linkerConfiguration 配置信息实体
	 * @return
	 * @time 2017年5月18日
	 */
	@RequestMapping("/saveConfigurations")
	@ResponseBody
	public JSONObject saveConfigurations(LinkerConfiguration linkerConfiguration){
		return configurationManagementBusiness.saveConfigurations(linkerConfiguration);
	}
	
	/**
	 * 获取静态配置信息：监控刷新时间间隔
	 * @author 秦超
	 * @return
	 * @time 2017年5月18日
	 */
	@RequestMapping("/getStaticConfigurations")
	@ResponseBody
	public JSONObject getStaticConfigurations(){
		return configurationManagementBusiness.getStaticConfigurations();
	}
}
