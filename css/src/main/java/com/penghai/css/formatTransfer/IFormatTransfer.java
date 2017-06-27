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
}
