package com.penghai.store.category.business;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.model.IndustryCategory;

public interface AdminCategoryManageBusiness {
	JSONObject addCategory(IndustryCategory industryCategory);
}
