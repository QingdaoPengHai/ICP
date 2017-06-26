package com.penghai.store.good.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.QueryCatagoryCodeBusiness;
import com.penghai.store.category.business.QueryLevelDataBusiness;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.good.business.GoodsListBusiness;
import com.penghai.store.good.model.Goods;

/**
 * 按照分类展示商品
 * 
 * @author 李浩
 *
 */
@Controller
@RequestMapping(value = "/category")
public class QueryGoodsController extends BaseController {
	@Autowired
	private GoodsListBusiness goodsListBusiness;
	@Autowired
	private QueryCatagoryCodeBusiness queryCatagoryCodeBusiness;
	@Autowired
	private QueryLevelDataBusiness queryLevelDataBusiness;

	/**
	 * 点击分类
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toProducts")
	public ModelAndView toProducts(Model model, String categoryId, String categoryName) {
		// 分类下的商品信息
		String categoryCode = queryCatagoryCodeBusiness.queryCatagoryCode(Integer.valueOf(categoryId));
		JSONObject result = goodsListBusiness.getAllGoodsByCatagoryId(categoryCode);
		// 获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();

		model.addAttribute("categoryName", categoryName);
		model.addAttribute("categoryGoods", result);
		model.addAttribute("doubleLevel", doubleLevel);
		return new ModelAndView("products");
	}
	
	/**
	 * 根据商品名称查询商品列表
	 * @author 徐超
	 * @Date 2017年4月27日 下午5:06:57
	 * @param model
	 * @param goodName 商品名称
	 * @return
	 */
	@RequestMapping(value = "/searchGoodsByGoodName")
	public ModelAndView searchGoodsByGoodName(Model model, String goodName) {
		
		JSONObject result = new JSONObject();
		String goodNameDecode = "";
		try {
			goodNameDecode = URLDecoder.decode(goodName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String goodNameParam = "%"+goodNameDecode+"%";
		List<Goods> goodsList = new ArrayList<Goods>();
		//获取商品列表
		goodsList = goodsListBusiness.getGoodsListByGoodName(goodNameParam);
		// 获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		
		result.put("list", goodsList);
		result.put("totalNum", goodsList.size());
		model.addAttribute("searchResult", result);
		model.addAttribute("doubleLevel", doubleLevel);
		return new ModelAndView("goodsListSearch");
	}
}
