package com.penghai.store.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penghai.store.category.business.QueryLevelDataBusiness;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.good.model.Goods;
import com.penghai.store.order.business.IShoppingCartBusiness;
import com.penghai.store.order.business.OrderManageBusiness;

@Controller
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController extends BaseController{
	
	@Autowired
	private OrderManageBusiness orderManageBusiness;
	@Autowired
	private IShoppingCartBusiness iShoppingCartBusiness;
	@Autowired
	private QueryLevelDataBusiness queryLevelDataBusiness;
	/**
	 * 购物车页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toShoppingCart")
	public ModelAndView toHomePage(Model model) {
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		model.addAttribute("doubleLevel", doubleLevel);
		//获取购物车列表
		Map<String, Object> shoppingCartMap = iShoppingCartBusiness.getAllgoodsListFromShoppingCart();
		model.addAttribute("shoppingCart", shoppingCartMap);
		
		return new ModelAndView("shoppingCart");
	}
	
	/**
	 * 购物车新增商品
	 * @return
	 */
	@RequestMapping(value = "/addShoppingCart")
	@ResponseBody
	public Map<String, Object> addShoppingCart(String goodsId){
		Map<String, Object> resultMap = new HashMap<>();
//		goodsId = request.getAttribute("goodsId").toString();
		int addResult = iShoppingCartBusiness.addGoodsIntoShoppingCart(goodsId);
		resultMap.put("result", addResult);
		
		return resultMap;
	}
	
	/**
	 * 购物车删除商品
	 * @return
	 */
	@RequestMapping(value = "/deleteShoppingCart")
	@ResponseBody
	public Map<String, Object> deleteShoppingCart(String goodsId){
		Map<String, Object> resultMap = new HashMap<>();
		boolean result =  iShoppingCartBusiness.deleteGoodsFromShoppingCart(goodsId);
		resultMap.put("result", 1);
		
		return resultMap;
	}
	
	/**
	 * 购物车提交订单
	 * @return
	 */
	@RequestMapping(value = "/submitOrder")
	@ResponseBody
	public Map<String, Object> submitOrder(){
		Map<String, Object> resultMap = new HashMap<>();
		//1.获取购物车列表
		Map<String, Object> shoppingCartMap = iShoppingCartBusiness.getAllgoodsListFromShoppingCart();
		if("0".equals(shoppingCartMap.get("count").toString())){
			//购物车为空
			resultMap.put("result", 2);
			return resultMap;
		}
		//2.提交订单
		int submitOrderResult = orderManageBusiness.submitOrder(shoppingCartMap);
		if(submitOrderResult != 1){
			//提交订单失败  3.提交订单表失败 4.提交订单详细表失败 5.提交订单商品文件表失败
			resultMap.put("result", submitOrderResult);
			return resultMap;
		}
		//3.清空购物车
		boolean cleanUpResult = iShoppingCartBusiness.cleanUpUserShoppingCart();
		if(cleanUpResult==false){
			//清空购物车失败
			resultMap.put("result", 6);
			return resultMap;
		}
		//提交成功
		resultMap.put("result", 1);
		return resultMap;
	}
	
	/**
	 * 获取订单条数
	 * @return
	 */
	@RequestMapping(value = "/getShoppingCartCount")
	@ResponseBody
	public Map<String, Object> getShoppingCartCount(){
		Map<String, Object> resultMap = new HashMap<>();
		
		int addResult = iShoppingCartBusiness.getShoppingCartCount();
		resultMap.put("result", addResult);
		
		return resultMap;
	}
	
	/*
	 * 购买成功，跳转成功页面
	 */
	@RequestMapping(value = "/orderSubmitSuccess")
	@ResponseBody
	public ModelAndView orderSubmitSuccess(Model model) {
		//获取购物车列表
		model.addAttribute("userId", session.get("userId"));
		
		return new ModelAndView("orderSubmitSuccess");
	}
}
