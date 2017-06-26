package com.penghai.store.good.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penghai.store.category.business.QueryLevelDataBusiness;
import com.penghai.store.category.model.IndustryCategory;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.good.business.GoodsListBusiness;
import com.penghai.store.good.model.Goods;

@Controller
@RequestMapping(value = "/goodsList")
public class GoodsListController extends BaseController{
	@Autowired
	private GoodsListBusiness goodsListBusiness;
	@Autowired
	private QueryLevelDataBusiness queryLevelDataBusiness;
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toHomePage")
	public ModelAndView toHomePage(Model model) {
		//获取热门商品列表
		List<Goods> hotGoodsList = goodsListBusiness.getHotGoodsList();
		//获取推荐商品列表
		List<Goods> recommendGoodsList = goodsListBusiness.getRecommendGoodsList();
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		//获取一级分类列表
		List<IndustryCategory> firstLevel = queryLevelDataBusiness.queryFirstLevelData();
		model.addAttribute("hotGoodsList", hotGoodsList);
		model.addAttribute("recommendGoodsList", recommendGoodsList);
		model.addAttribute("firstLevel", firstLevel);
		model.addAttribute("doubleLevel", doubleLevel);
		return new ModelAndView("homePage");
	}
	
	//获取热门商品列表
	@RequestMapping(value = "/getHotGoodsList")
	@ResponseBody
	public Map<String, Object> getHotGoodsList(){
		Map<String, Object> resultMap = new HashMap<>();
		
		List<Goods> hotGoodsList = goodsListBusiness.getHotGoodsList();
		resultMap.put("hotGoodsList", hotGoodsList);
		
		return resultMap;
	}
	
	//获取推荐商品列表
	@RequestMapping(value = "/getRecommendGoodsList")
	@ResponseBody
	public Map<String, Object> getRecommendGoodsList(){
		Map<String, Object> resultMap = new HashMap<>();
		
		List<Goods> recommendGoodsList = goodsListBusiness.getRecommendGoodsList();
		resultMap.put("recommendGoodsList", recommendGoodsList);
		
		return resultMap;
	}
}
