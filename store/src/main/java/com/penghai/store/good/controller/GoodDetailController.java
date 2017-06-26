package com.penghai.store.good.controller;

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
import com.penghai.store.good.business.GoodDetailBusiness;
import com.penghai.store.good.model.Goods;
import com.penghai.store.good.model.GoodsDetailInfo;

@Controller
@RequestMapping(value = "/goodDetail")
public class GoodDetailController extends BaseController{
	@Autowired
	private GoodDetailBusiness goodDetailBusiness;
	@Autowired
	private QueryLevelDataBusiness queryLevelDataBusiness;
	/**
	 * 商品详情页面
	 * @param goodsId 商品id 
	 * @return model 返回页面数据信息，包含商品详情信息
	 */
	@RequestMapping(value = "/toGoodDetail")
	public ModelAndView toGoodDetail(Model model, int goodsId){
		//获取商品详情
		GoodsDetailInfo goodsDetailInfo = goodDetailBusiness.getGoodInfoById(goodsId);
		
		model.addAttribute("goodDetail", goodsDetailInfo);
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		model.addAttribute("doubleLevel", doubleLevel);
		return new ModelAndView("goodDetail");
	}
	//获取推荐商品列表
		@RequestMapping(value = "/getGoodsDetailInfo")
		@ResponseBody
		public Map<String, Object> getRecommendGoodsList(int goodsId){
			Map<String, Object> resultMap = new HashMap<>();
			
			GoodsDetailInfo goodsDetailInfo = goodDetailBusiness.getGoodInfoById(goodsId);
			resultMap.put("goodDetail", goodsDetailInfo);
			
			return resultMap;
		}
}
