package com.penghai.store.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.admin.business.AdminBusiness;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.good.model.Goods;

/**
 * 后台商品管理
 * 
 * @author 李浩
 *
 */
@Controller
@RequestMapping("/admin/manage")
public class AdminManageController extends BaseController {
	@Autowired
	private AdminBusiness adminBusiness;

	/*
	 * 添加商品
	 */
	@RequestMapping(value = "/addGood")
	@ResponseBody
	/*public JSONObject addGood(@RequestParam("photo") MultipartFile file) {
		Map<String, Object> map = collectData();*/
	public JSONObject editGood(String goodCate, String goodName, @RequestParam(required=false)String description, String price, String goodModel,@RequestParam(required=false)MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodCate",goodCate);
		map.put("goodName",goodName);
		map.put("description",StringEscapeUtils.escapeJava(description));
		map.put("price",price);
		map.put("goodModel",goodModel);
		JSONObject result = adminBusiness.addGood(map, file, request, response);
		return result;
	}

	/**
	 * 按条件查询商品
	 */
	@RequestMapping(value = "/queryGoods")
	@ResponseBody
	public Map<String, Object> queryGoods() {
		Map<String, Object> map = collectData();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Goods> goods = adminBusiness.queryAllGoods(map);
		for (Goods good : goods) {
			System.out.println(good.getGoodName());
		}
		result.put("result", goods);
		result.put("length", goods.size());
		return result;
	}

	/**
	 * 删除商品
	 */
	@RequestMapping(value = "/deleteGood")
	@ResponseBody
	public JSONObject deleteGood() {
		Map<String, Object> map = collectData();
		JSONObject result = adminBusiness.deleteGoodById(Integer.valueOf((String) map.get("id")));
		return result;
	}

	/**
	 * 修改商品
	 */
	@RequestMapping(value = "/editGood")
	@ResponseBody
	/*public JSONObject editGood(@RequestParam("photo") MultipartFile file) {
		Map<String, Object> map = collectData();*/
	public JSONObject editGood(String id, String goodCate, String goodName, @RequestParam(required=false)String description, String price,String oldPicture,String goodModel,@RequestParam(required=false)MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("goodCate",goodCate);
		map.put("goodName",goodName);
		map.put("description",StringEscapeUtils.escapeJava(description));
		map.put("price",price);
		map.put("oldPicture",oldPicture);
		map.put("goodModel",goodModel);
		JSONObject result = adminBusiness.updateGood(map, file, request, response);
		return result;
	}
}
