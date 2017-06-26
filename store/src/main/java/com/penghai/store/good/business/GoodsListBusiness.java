package com.penghai.store.good.business;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.good.model.Goods;

public interface GoodsListBusiness {

	public List<Goods> getHotGoodsList();

	public List<Goods> getRecommendGoodsList();

	public JSONObject getAllGoodsByCatagoryId(String catagoryCode);
	
	List<Goods> getGoodsListByGoodName(String goodName);

}
