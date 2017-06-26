package com.penghai.store.category.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.QueryLevelDataBusiness;
import com.penghai.store.category.model.IndustryCategory;
import com.penghai.store.common.controller.BaseController;

/**
 * 测试用Controller （后续修改或删除）
 * 
 * @author 李浩
 */
@Controller
@RequestMapping("/level")
public class QueryLevelController extends BaseController {
	@Autowired
	private QueryLevelDataBusiness queryLevelDataBusiness;

	/*
	 * 查询第一层级的分类
	 */
	@RequestMapping("/queryFirstLevel")
	public void queryFirstLevel() {
		List<IndustryCategory> datas = queryLevelDataBusiness.queryFirstLevelData();
		for (IndustryCategory data : datas) {
			System.out.println(data.getId());
		}
	}

	/**
	 * 查询第一和第二层级的分类
	 */
	@RequestMapping("/queryDoubleLevel")
	@ResponseBody
	public Map<String, Object> queryDoubleLevel() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> datas = queryLevelDataBusiness.queryTwoLevelData();
		for (Map<String, Object> data : datas) {
			for (String key : data.keySet()) {
				System.out.println(key + "-->" + data.get(key));
			}
		}
		result.put("result", datas);
		return result;
	}

	/**
	 * 查询所有四层层级的分类
	 * @author 秦超
	 */
	@RequestMapping("/queryFourLevel")
	@ResponseBody
	public JSONObject queryFourLevel(){
		
		JSONObject result = queryLevelDataBusiness.getFourLevelCategory();
		
		return result;
	}

}
