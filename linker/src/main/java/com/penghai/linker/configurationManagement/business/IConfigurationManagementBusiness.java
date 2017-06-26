package com.penghai.linker.configurationManagement.business;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.model.LinkerConfiguration;
/**
 * 配置管理业务接口
 * @author 秦超
 * @time 2017年5月18日
 */
public interface IConfigurationManagementBusiness {
	/**
	 * 修改linker配置信息：存储系统路径、数据库信息、rabbitMQ信息
	 * @author 秦超
	 * @param linkerConfiguration
	 * @return
	 * @time 2017年5月17日
	 */
	public JSONObject saveConfigurations(LinkerConfiguration linkerConfiguration);
	/**
	 * 获取配置文件中记录的配置信息
	 * @author 秦超
	 * @return
	 * @time 2017年5月17日
	 */
	public JSONObject getConfigurations();
	/**
	 * 获取静态配置信息：监控刷新时间间隔
	 * @author 秦超
	 * @return
	 * @time 2017年5月19日
	 */
	public JSONObject getStaticConfigurations();
	/**
	 * 获取linker配置状态
	 * @author 秦超
	 * @return
	 * @time 2017年5月21日
	 */
	public JSONObject getLinkerConfigStatus();
	/**
	 * 修改linker配置状态
	 * @author 秦超
	 * @return
	 * @time 2017年5月21日
	 */
	public JSONObject updateLinkerConfigStatus(String linkerConfigStatus);
}
