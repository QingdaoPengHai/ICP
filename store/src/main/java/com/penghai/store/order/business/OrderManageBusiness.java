package com.penghai.store.order.business;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface OrderManageBusiness {
	public JSONArray getAllOrderAndGoods(Integer userId);

	public JSONObject deleteOrder(Integer id);

	int submitOrder(Map<String, Object> orderMap);

	public JSONArray getAllOrderEveryOne();
	
	JSONObject editOrderGoodsXml(String orderId,String userId,String goodsId,String version,
			 String xmlContent,String goodModel,String isEdit);

	String getUserOrderXmlList(String email,String password);
	
	String getUserOrderXmlDetail(String email,String password,String xmlId);
	
	Map<String, Object> getUserOrderXmlDetailByOrderIdAndGoodsId(String orderId,String goodsId);

	/**
	 * 修改xml文件下载状态
	 * @author 秦超
	 * @time 2017年5月11日
	 */
	public String updateOrderStatus(String email, String password, String xmlId);
}
