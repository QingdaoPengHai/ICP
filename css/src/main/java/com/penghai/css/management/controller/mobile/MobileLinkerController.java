package com.penghai.css.management.controller.mobile;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.management.dao.impl.IUserDaoImpl;
import com.penghai.css.management.model.Linker;

/**
 * 移动端Linker控制器类
 * @author 徐超
 * @Date 2017年7月12日 下午5:32:56
 */
@Controller
@RequestMapping(
		value="/mobile",
		method=RequestMethod.POST,
		produces = "application/json;charset=UTF-8")
public class MobileLinkerController {

	@Autowired
	private IUserDaoImpl iUserDaoImpl;
	/**
	 * 获取Linker信息接口
	 * @author 徐超
	 * @Date 2017年7月12日 下午5:36:22
	 * @return
	 */
	@RequestMapping(value="/queryLinkerInfo")
	@ResponseBody
	public JSONObject queryLinkerInfo(){
		JSONObject resultJson = new JSONObject();
		
		//查询所有linker信息
		List<LinkedHashMap<String,Object>> linkers = iUserDaoImpl.getLinkerInfoMobile();
		
		resultJson.put("code", "0");
		resultJson.put("message", "获取成功");
		resultJson.put("data", linkers);
		return resultJson;
	}
}
