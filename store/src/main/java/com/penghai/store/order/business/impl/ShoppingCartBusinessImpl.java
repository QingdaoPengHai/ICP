package com.penghai.store.order.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.common.model.HttpSession;
import com.penghai.store.good.dao.mybatis.GoodsMapper;
import com.penghai.store.good.model.Goods;
import com.penghai.store.order.business.IShoppingCartBusiness;
import com.penghai.store.order.model.ShoppingCart;
import com.penghai.store.util.common.CommonData.CM_REDIS_KEY;

/**
 * 购物车业务逻辑
 * @author 徐超/秦超
 * @Date 2017年4月26日 下午3:12:39
 */
@Service
public class ShoppingCartBusinessImpl implements IShoppingCartBusiness,CM_REDIS_KEY{
	
	// 获取会话对象
	@Autowired
	public HttpSession session;
	
	//获取日志对象
	Logger log = Logger.getLogger(ShoppingCartBusinessImpl.class);
	
	@Autowired
	private GoodsMapper goodsMapper;
	/**
	 * 向购物车添加商品
	 * @author 徐超
	 * @Date 2017年4月26日 下午3:39:53
	 * @param goodsId
	 * @return
	 */
	@Override
	public int addGoodsIntoShoppingCart(String goodsId){
		int result = 0;//0--错误 1--成功 2--已存在
		try {
			//构建购物车商品列表Map
			Map<String, ShoppingCart> cartListMap = new HashMap<String, ShoppingCart>();
			//从session获取购物车
			String shoppingCart = session.get(REDIS_SHOPPING_CART);
			if(null != shoppingCart && !"".equals(shoppingCart) && !"null".equals(shoppingCart)){
				//不为空,取出添加
				cartListMap = JSONObject.parseObject(shoppingCart,HashMap.class);
				//判断即将添加的goodsId是否已经存在
				if(cartListMap.containsKey(goodsId)){
					result = 2;
					return result;
				}
			}
			//获取商品详情
			Goods goods = goodsMapper.selectByPrimaryKey(Integer.valueOf(goodsId));
			//生成购物车实体
			ShoppingCart userShoppingCart = new ShoppingCart();
			
			//购物车ListString
			String cartString = "";
			//向购物车实体添加数据
			userShoppingCart.setGoodsName(goods.getGoodName());
			userShoppingCart.setGoodsNumber("1");
			userShoppingCart.setGoodsPrice(new BigDecimal(goods.getPrice().toString()));
			userShoppingCart.setPictureURL(goods.getDefaultPicture());
			userShoppingCart.setGoodsId(goodsId);
			int descriptionLength = goods.getDescription().length();
			String description = goods.getDescription();
			if(descriptionLength>30){
				description = description.substring(0, 30)+"...";
			}
			userShoppingCart.setDescription(description);
			//为空,直接添加
			cartListMap.put(goodsId, userShoppingCart);
			cartString = JSONObject.toJSONString(cartListMap);
			session.set(REDIS_SHOPPING_CART, cartString);
			result = 1;
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result;
		}
	}
	
	/**
	 * 从购物车删除商品
	 * @author 徐超
	 * @Date 2017年4月26日 下午6:23:17
	 * @param goodsId
	 * @return
	 */
	@Override
	public boolean deleteGoodsFromShoppingCart(String goodsId){
		boolean result = false;
		try {
			//构建购物车商品列表Map
			Map<String, Object> cartListMap = new HashMap<String, Object>();
			//从session获取购物车
			String shoppingCart = session.get(REDIS_SHOPPING_CART);
			//转成Map对象
			cartListMap = JSONObject.parseObject(shoppingCart,HashMap.class);
			//删除指定Value
			cartListMap.remove(goodsId);
			//Map转为String
			String cartString = JSONObject.toJSONString(cartListMap);
			//存回到redis
			session.set(REDIS_SHOPPING_CART, cartString);
			result = true;
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result;
		}
	}
	
	/**
	 * 获取当前购物车中商品
	 * @author 徐超
	 * @Date 2017年4月26日 下午5:38:55
	 * @return
	 */
	@Override
	public Map<String, Object> getAllgoodsListFromShoppingCart(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//构建购物车商品列表Map
			Map<String, Object> cartListMap = new HashMap<String, Object>();
			//从session获取购物车
			String shoppingCart = session.get(REDIS_SHOPPING_CART);
			if(null != shoppingCart && !"".equals(shoppingCart) && !"null".equals(shoppingCart)){
				//转为Map对象
				cartListMap = JSONObject.parseObject(shoppingCart,HashMap.class);
				//返回到前台的List
				List shoppingCartList = new ArrayList<>();
				//总价
				BigDecimal totalPrice = new BigDecimal(0);
				Iterator it = cartListMap.keySet().iterator();
				//遍历map添加到List同时计算出总价
				while (it.hasNext()) {
					String key = it.next().toString();
					shoppingCartList.add(cartListMap.get(key));
					JSONObject asd  = JSONObject.parseObject(cartListMap.get(key).toString());
					totalPrice = totalPrice.add(asd.getBigDecimal("goodsPrice"));
				}
				resultMap.put("count", cartListMap.size());
				resultMap.put("totalPrice", totalPrice);
				resultMap.put("shoppingCartList", shoppingCartList);
				return resultMap;
			}else{
				resultMap.put("count", 0);
				resultMap.put("totalPrice", 0);
				resultMap.put("shoppingCartList", "");
				return resultMap;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultMap.put("count", 0);
			resultMap.put("totalPrice", 0);
			resultMap.put("shoppingCartList", "");
			return resultMap;
		}
	}
	
	/**
	 * 清空购物车
	 * @author 徐超
	 * @Date 2017年4月26日 下午7:45:22
	 * @return
	 */
	@Override
	public boolean cleanUpUserShoppingCart(){
		
		session.set(REDIS_SHOPPING_CART, "");
		
		return true;
	}
	/**
	 * 获取当前购物车商品数量
	 * @author 徐超
	 * @Date 2017年4月26日 下午7:46:46
	 * @return
	 */
	@Override
	public int getShoppingCartCount(){
		int count  = 0;
		try {
			//构建购物车商品列表Map
			Map<String, Object> cartListMap = new HashMap<String, Object>();
			//从session获取购物车
			String shoppingCart = session.get(REDIS_SHOPPING_CART);
			//转为Map对象
			cartListMap = JSONObject.parseObject(shoppingCart,HashMap.class);
			count = cartListMap.size();
			return count;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return count;
		}
	}
}
