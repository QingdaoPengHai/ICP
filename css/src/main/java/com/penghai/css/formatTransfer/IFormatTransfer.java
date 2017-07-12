package com.penghai.css.formatTransfer;

import com.alibaba.fastjson.JSONObject;

/**
 * 格式转换接口类
 * @author 徐超
 * @Date 2017年6月27日 上午11:31:59
 */
public interface IFormatTransfer {

	/**
	 * 用户登录接口的格式化方法
	 * @author 徐超
	 * @Date 2017年6月27日 下午2:34:20
	 * @param json
	 * @return
	 */
	JSONObject transferUserLoginFormat(String jsonString);
	
	/**
	 * 获取用户订单列表的格式化方法
	 * @author 徐超
	 * @Date 2017年7月11日 下午3:24:21
	 * @param jsonString
	 * @return
	 */
	JSONObject transferUserOrdersFormat(String jsonString);
}
