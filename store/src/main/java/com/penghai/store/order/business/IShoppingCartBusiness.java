package com.penghai.store.order.business;

import java.util.Map;

/**
 * 购物车业务逻辑
 * @author 徐超/秦超
 * @Date 2017年4月26日 下午3:12:39
 */
public interface IShoppingCartBusiness {

	//向购物车添加商品
	int addGoodsIntoShoppingCart(String goodsId);
	//从购物车删除商品
	boolean deleteGoodsFromShoppingCart(String goodsId);
	//获取当前购物车中商品
	Map<String, Object> getAllgoodsListFromShoppingCart();
	//清空购物车
	boolean cleanUpUserShoppingCart();
	//获取当前购物车数量
	int getShoppingCartCount();
}
