package com.penghai.store.category.business;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.model.IndustryCategory;

public interface QueryLevelDataBusiness {
	List<IndustryCategory> queryFirstLevelData();

	List<Map<String, Object>> queryTwoLevelData();

	JSONObject getFourLevelCategory();
}
