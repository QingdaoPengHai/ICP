package com.penghai.linker.dataAcquisition.business;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据采集服务接口类--与页面交互
 * @author 徐超
 * @Date 2017年5月22日 下午5:49:47
 */
public interface IDataAcquisitionBusiness {

	/**
	 * linker注册服务
	 * @author 徐超
	 * @Date 2017年5月23日 上午9:16:17
	 * @return
	 */
	JSONObject linkerRegist();
	
	/**
	 * 重置参数
	 * @author 徐超
	 * @Date 2017年5月23日 上午10:27:01
	 * @return
	 */
	JSONObject resetParameter();
	
	/**
	 * 判断定时器状态
	 * @author 徐超
	 * @Date 2017年5月23日 下午1:03:40
	 * @return
	 */
	JSONObject getTimerStatus();
	
}
