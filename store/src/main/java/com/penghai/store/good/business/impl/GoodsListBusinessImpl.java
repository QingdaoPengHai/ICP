package com.penghai.store.good.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.store.good.business.GoodsListBusiness;
import com.penghai.store.good.dao.mybatis.GoodsMapper;
import com.penghai.store.good.model.Goods;
import com.penghai.store.util.common.CommonData.CM_QUERY_INFO;

/**
 * 商品列表Service，获取不同条件商品列表
 * 
 * @author Q.chao 2017-04-25 15:35:04
 */
@Service
public class GoodsListBusinessImpl implements GoodsListBusiness {
	
	//获取日志对象
	Logger log = Logger.getLogger(GoodsListBusinessImpl.class);

	@Autowired
	private GoodsMapper goodsMapper;

	/**
	 * 获取热门商品列表
	 */
	@Override
	public List<Goods> getHotGoodsList() {

		Goods goodsParam = new Goods();
		goodsParam.setPageIndex(0);
		goodsParam.setPageSize(8);
		return goodsMapper.getGoodsListByCondition(goodsParam);
	}

	/**
	 * 获取推荐商品列表
	 */
	@Override
	public List<Goods> getRecommendGoodsList() {
		Goods goodsParam = new Goods();
		goodsParam.setPageIndex(0);
		goodsParam.setPageSize(8);
		return goodsMapper.getGoodsListByCondition(goodsParam);
	}

	@Override
	public JSONObject getAllGoodsByCatagoryId(String catagoryCode) {
		JSONObject result = new JSONObject();
		List<Goods> goods = goodsMapper.selectByGoodCateCode(catagoryCode);
		if (goods != null) {
			int length = goods.size();
			result.put("success", true);
			result.put("totalNum", length);
			if (length > 0) {
				result.put("message", CM_QUERY_INFO.QUERY_SUCCESS_INFO);
				JSONArray goodArray = new JSONArray();
				for (Goods good : goods) {
					JSONObject goodObject = new JSONObject();
					goodObject.put("id", good.getId() + "");
					goodObject.put("goodCate", good.getGoodCate() + "");
					goodObject.put("goodName", good.getGoodName());
					goodObject.put("price", good.getPrice());
					goodObject.put("description", good.getDescription());
					goodObject.put("pictureUrl", good.getDefaultPicture());
					goodArray.add(goodObject);
				}
				result.put("list", goodArray);
			} else {
				result.put("message", CM_QUERY_INFO.QUERY_NODATA_INFO);
			}
		} else {
			result.put("success", false);
			result.put("message", CM_QUERY_INFO.QUERY_ERROR_INFO);
		}
		return result;

	}
	
	/**
	 * 根据商品名称搜索商品列表
	 * @author 徐超
	 * @Date 2017年4月27日 下午4:00:43
	 * @param goodName
	 * @return
	 */
	@Override
	public List<Goods> getGoodsListByGoodName(String goodName){
		List<Goods> goodsList = new ArrayList<Goods>();
		try {
			Goods good = new Goods();
			//将goodName拼接为sql的like参数
			String goodsNameParam = "%"+goodName+"%";
			good.setGoodName(goodsNameParam);
			//查询商品列表
			goodsList = goodsMapper.getGoodsListByCondition(good);
			return goodsList;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return goodsList;
		}
	}

}
