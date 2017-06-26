package com.penghai.store.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.AdminCategoryManageBusiness;
import com.penghai.store.category.model.IndustryCategory;
import com.penghai.store.common.controller.BaseController;

/**
 * 后台管理分类管理
 * 
 * @author 李浩
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminCategoryManageController extends BaseController {
	@Autowired
	private AdminCategoryManageBusiness business;

	/**
	 * 增加新分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addCategory")
	@ResponseBody
	public JSONObject addCategory(IndustryCategory industryCategory) {
		System.out.println(collectData());
		Byte bstatus = 1;
		industryCategory.setStatus(bstatus);
		JSONObject result = business.addCategory(industryCategory);
		return result;
	}
}
