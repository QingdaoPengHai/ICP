package com.penghai.css.management.controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.management.business.DataCountBusiness;

/**
 * 移动端数据统计控制器类
 * @author 徐超
 * @Date 2017年7月12日 下午2:45:03
 */
@Controller
@RequestMapping(
		value="/mobile",
		method=RequestMethod.POST,
		produces = "application/json;charset=UTF-8")
public class MobileDataCountController {


	@Autowired
	private DataCountBusiness dataCountBusiness;
	/**
	 * 获取数据库信息
	 * @author 徐超
	 * @Date 2017年7月12日 下午3:20:34
	 * @return
	 */
	@RequestMapping(value="/queryDatabaseInfo")
	@ResponseBody
	public JSONObject queryDatabaseInfo(){
		JSONObject resultJson = new JSONObject();
		
		resultJson = dataCountBusiness.getDatabaseInfo();
		
		return resultJson;
	}
}
