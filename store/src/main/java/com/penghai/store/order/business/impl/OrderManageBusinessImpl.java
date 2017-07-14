package com.penghai.store.order.business.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.store.common.model.HttpSession;
import com.penghai.store.order.business.OrderManageBusiness;
import com.penghai.store.order.dao.mybatis.OrderGoodsFileMapper;
import com.penghai.store.order.dao.mybatis.OrderInfoMapper;
import com.penghai.store.order.dao.mybatis.OrderMapper;
import com.penghai.store.order.model.Order;
import com.penghai.store.order.model.OrderGoodsFile;
import com.penghai.store.order.model.OrderInfo;
import com.penghai.store.user.dao.mybatis.UserMapper;
import com.penghai.store.user.model.User;
import com.penghai.store.util.common.CommonData.CM_ORDER_DATA;
import com.penghai.store.util.common.CommonData.CM_QUERY_INFO;
import com.penghai.store.util.common.CommonData.CM_LOGIN_DATA;
import com.penghai.store.util.common.CommonData.CM_INFO_DATA;

@Service
public class OrderManageBusinessImpl implements OrderManageBusiness{
	//获取日志对象
	Logger log = Logger.getLogger(OrderManageBusinessImpl.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private OrderGoodsFileMapper orderGoodsFileMapper;
	@Autowired
	public HttpSession session;

	/*
	 * 查询某一用户的所有订单详情及订单下的所有商品详情
	 */
	@Override
	public JSONArray getAllOrderAndGoods(Integer userId) {

		Order order = new Order();
		// 查询该用户没删除的所有订单
		order.setUserId(userId);
		//order.setIsDelete(CM_ORDER_DATA.ORRDER_NODELETE);
		List<Order> orders = orderMapper.selectByCondition(order);
		// 设置返回的JSONArray
		JSONArray returnArray = new JSONArray();
		// 解析订单数据
		for (Order o : orders) {
			JSONObject orderObject = new JSONObject();
			Integer id = o.getId();
			String orderCode = o.getOrderCode();
			String buyTime = o.getBuyTimeStr();
			Byte status = o.getStatus();
			String isDelete = o.getIsDelete();
			BigDecimal totalPrice = o.getTotalPrice();
			// 请求该订单下的所有商品

			OrderInfo record = new OrderInfo();
			record.setOrderId(id);
			List<Map<String, Object>> goods = orderInfoMapper.selectByOrderId(record);
			// 商品列表
			JSONArray goodArray = new JSONArray();
			for (Map<String, Object> good : goods) {
				// 商品详情
				JSONObject goodObject = new JSONObject();
				goodObject.put("goodId", good.get("goodsId"));
				goodObject.put("goodName", good.get("goodsName"));
				goodObject.put("goodNumber", good.get("goodsNumber"));
				goodObject.put("goodPrice", good.get("goodsPrice"));
				goodObject.put("goodUrl", good.get("defaultPicture"));
				goodObject.put("status", good.get("status"));
				// 加入商品列表
				goodArray.add(goodObject);
			}
			// 添加订单的详情
			orderObject.put("id", id);
			orderObject.put("orderCode", orderCode);
			orderObject.put("buyTime", buyTime);
			orderObject.put("status", status);
			//0：正常订单 1：已取消订单
			orderObject.put("isDelete", isDelete);
			orderObject.put("totalPrice", totalPrice);
			orderObject.put("userId", userId);
			orderObject.put("goodList", goodArray);
			// 将订单加入订单列表
			returnArray.add(orderObject);
		}

		return returnArray;
	}

	/*
	 * 用户删除某一订单
	 */
	@Override
	public JSONObject deleteOrder(Integer id) {
		JSONObject resultObject = new JSONObject();
		Order record = new Order();
		record.setId(id);
		record.setIsDelete(CM_ORDER_DATA.ORDER_DELETE);
		int result = orderMapper.updateByPrimaryKeySelective(record);
		if (result == 1) {
			// 删除成功
			resultObject.put("success", true);
			return resultObject;
		} else {
			// 删除失败
			resultObject.put("success", false);
		}
		return resultObject;
	}
	
	/*
	 * 购物车提交订单
	 * @author Q.C
	 */
	@Override
	public int submitOrder(Map<String, Object> orderMap){
		
		String orderCode = "";
		long timestamp = System.currentTimeMillis();
		String randomstamp = String.valueOf(new Random().nextInt(89)+10);
		orderCode = timestamp + randomstamp;
		int orderId;
		//提交订单表
		try {
			Order order = new Order();
			order.setOrderCode(orderCode);
			order.setUserId(Integer.valueOf(session.get("userId")));
			order.setBuyTime(new Date());
			order.setTotalPrice(new BigDecimal(orderMap.get("totalPrice").toString()));
			order.setStatus(new Byte("0"));
			order.setIsDelete("0");
			orderMapper.insertSelective(order);
			orderId = order.getId();
		} catch (Exception e) {
			// 提交订单表失败
			return 3;
		}
		
		JSONArray shoppingCartList = JSONArray.parseArray(orderMap.get("shoppingCartList").toString());
		for(Object shoppingCart:shoppingCartList){
			Map<String, Object> cartMap = JSONObject.parseObject(shoppingCart.toString());
			//提交订单详情表
			try {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(orderId);
				orderInfo.setGoodsId(Integer.valueOf(cartMap.get("goodsId").toString()));
				orderInfo.setGoodsNumber(Integer.valueOf(cartMap.get("goodsNumber").toString()));
				orderInfo.setGoodsPrice(new BigDecimal(cartMap.get("goodsPrice").toString()));
				orderInfoMapper.insertSelective(orderInfo);				
			} catch (Exception e) {
				//提交订单详情表
				e.printStackTrace();
				return 4;
			}
			//提交订单商品文件表
			try {
				OrderGoodsFile orderGoodsFile = new OrderGoodsFile();
				orderGoodsFile.setOrderId(orderId);
				orderGoodsFile.setGoodsId(Integer.valueOf(cartMap.get("goodsId").toString()));
				orderGoodsFile.setStatus(0);
				orderGoodsFileMapper.insertSelective(orderGoodsFile);
			} catch (Exception e) {
				// 提交订单商品文件表
				return 5;
			}
		}
		
		return 1;
	}
	
	/*
	 * 管理员获取所有订单信息
	 */
	@Override
	public JSONArray getAllOrderEveryOne() {
		Order order = new Order();
		// 查询该用户没删除的所有订单
		order.setIsDelete(CM_ORDER_DATA.ORRDER_NODELETE);
		List<Order> orders = orderMapper.selectByCondition(order);
		// 设置返回的JSONArray
		JSONArray returnArray = new JSONArray();
		// 解析订单数据
		for (Order o : orders) {
			JSONObject orderObject = new JSONObject();
			Integer id = o.getId();
			Integer userId = o.getUserId();
			String orderCode = o.getOrderCode();
			String buyTime = o.getBuyTimeStr();
			Byte status = o.getStatus();
			String isDelete = o.getIsDelete();
			BigDecimal totalPrice = o.getTotalPrice();
			// 请求该订单下的所有商品
			OrderInfo record = new OrderInfo();
			record.setOrderId(id);
			List<Map<String, Object>> goods = orderInfoMapper.selectByOrderId(record);
			// 商品列表
			JSONArray goodArray = new JSONArray();
			for (Map<String, Object> good : goods) {
				// 商品详情
				JSONObject goodObject = new JSONObject();
				goodObject.put("goodId", good.get("goodsId"));
				goodObject.put("goodName", good.get("goodsName"));
				goodObject.put("goodNumber", good.get("goodsNumber"));
				goodObject.put("goodPrice", good.get("goodsPrice"));
				goodObject.put("goodUrl", good.get("defaultPicture"));
				goodObject.put("goodsModel", good.get("goodsModel"));
				goodObject.put("status", good.get("status"));
				goodObject.put("orderGoodsFileId", good.get("orderGoodsFileId"));
				// 加入商品列表
				goodArray.add(goodObject);
			}
			// 添加订单的详情
			orderObject.put("id", id);
			orderObject.put("orderCode", orderCode);
			orderObject.put("buyTime", buyTime);
			orderObject.put("status", status);
			//0：正常订单 1：已取消订单
			orderObject.put("isDelete", isDelete);
			orderObject.put("totalPrice", totalPrice);
			orderObject.put("userId", userId);
			orderObject.put("goodList", goodArray);
			// 将订单加入订单列表
			returnArray.add(orderObject);
		};
		
		return returnArray;
	}
	/**
	 * 后台编辑用户订单商品xml信息
	 * @author 徐超
	 * @Date 2017年4月28日 下午1:25:14
	 * @param orderId
	 * @param goodsId
	 * @param version
	 * @param xmlContent
	 * @return
	 */
	@Override
	public JSONObject editOrderGoodsXml(
			String orderId,String userId,String goodsId,String version,
			String xmlContent,String goodModel,String isEdit){
		JSONObject resultJson = new JSONObject();
		//如果关键参数为空,返回失败
		if("".equals(orderId)||"".equals(goodsId)||null==orderId||
		   null==goodsId||"null".equals(orderId)||"null".equals(goodsId)){
			
			resultJson.put("result", 0);
			return resultJson;
		}
		OrderGoodsFile record = new OrderGoodsFile();
		String fileName = "";
		int status = 0;
		try {
			//生成文件名
			fileName = userId+"_"+goodModel+"_"+version;
			//赋值
			record.setGoodsId(Integer.valueOf(goodsId));
			record.setOrderId(Integer.valueOf(orderId));
			record.setVersion(Integer.valueOf(version));
			record.setFileName(fileName);
			if("0".equals(isEdit)){
				//新增时更新状态为已生产
				status = 1;
				record.setStatus(status);
			}
			record.setXmlContent(xmlContent);
			record.setUpdateTime(new Date());
			//更新字段
			orderGoodsFileMapper.updateByOrderIdAndGoodsIdSelective(record);
			resultJson.put("result", 1);
			resultJson.put("message", CM_INFO_DATA.EDIT_SUCCESS);
			return resultJson;
		} catch (Exception e) {
			log.error(e.getMessage());
			resultJson.put("result", 0);
			resultJson.put("message", CM_INFO_DATA.SYSTEM_ERROR);
			return resultJson;
		}
	}
	/**
	 * 获取用户所有订单对应的XML_List
	 * @author 徐超、秦超
	 * @Date 2017年4月28日 下午3:00:25
	 * @param email
	 * @param password
	 * @return
	 */
	@Override
	public String getUserOrderXmlList(String email,String password){
		JSONObject resultJson  = new JSONObject();
		try {
			//验证用户名密码是否正确
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			int loginCheck = userMapper.checkAuth(user);
			if(0<loginCheck){
				//正确,查询列表
				JSONArray resultArray = new JSONArray();
				List<Order> orderList = orderMapper.getOrderListByEmail(email);
				
				// 解析订单数据
				for (Order o : orderList) {
					JSONObject orderObject = new JSONObject();
					Integer id = o.getId();
					String orderCode = o.getOrderCode();
					String buyTime = o.getBuyTimeStr();
					Integer userId = o.getUserId();
					Byte status = o.getStatus();
					String isDelete = o.getIsDelete();
					BigDecimal totalPrice = o.getTotalPrice();
					// 请求该订单下的所有商品
					OrderInfo record = new OrderInfo();
					record.setOrderId(id);
					List<Map<String, Object>> goods = orderInfoMapper.selectByOrderId(record);
					// 商品列表
					JSONArray goodArray = new JSONArray();
					for (Map<String, Object> good : goods) {
						// 商品详情
						JSONObject goodObject = new JSONObject();
						goodObject.put("goodId", good.get("goodsId"));
						goodObject.put("goodName", good.get("goodsName"));
						goodObject.put("goodNumber", good.get("goodsNumber"));
						goodObject.put("goodPrice", good.get("goodsPrice"));
						goodObject.put("goodUrl", good.get("defaultPicture"));
						goodObject.put("status", good.get("status"));
						goodObject.put("xmlId", good.get("xmlId"));
						// 加入商品列表
						goodArray.add(goodObject);
					}
					// 添加订单的详情
					orderObject.put("id", id);
					orderObject.put("orderCode", orderCode);
					orderObject.put("buyTime", buyTime);
					orderObject.put("status", status);
					//0：正常订单 1：已取消订单
					orderObject.put("isDelete", isDelete);
					orderObject.put("totalPrice", totalPrice);
					orderObject.put("userId", userId);
					orderObject.put("goodList", goodArray);
					List<OrderGoodsFile> xmlList = orderGoodsFileMapper.getOrderXmlList(o.getId());
					orderObject.put("orderFile", xmlList);
					// 将订单加入订单列表
					resultArray.add(orderObject);
				}
				
//				List<OrderGoodsFile> xmlList = new ArrayList<OrderGoodsFile>();
//				xmlList = orderGoodsFileMapper.getUserOrderXmlList(email);
				
				resultJson.put("result", 1);
				resultJson.put("message", CM_LOGIN_DATA.LOGIN_SUCCESS_INFO);
//				resultJson.put("count", xmlList.size());
//				resultJson.put("xmlList", xmlList);
				resultJson.put("count", resultArray.size());
				resultJson.put("orderList", resultArray);
//				return resultJson.toJSONString();
			}else{
				//用户名或密码错误
				resultJson.put("result", 0);
				resultJson.put("message", CM_LOGIN_DATA.LOGIN_ERROR_INFO);
				resultJson.put("count", 0);
				resultJson.put("orderList", null);
//				return resultJson.toJSONString();
			}
		} catch (Exception e) {
			//系统错误
			log.error(e.getMessage());
			resultJson.put("result", 2);
			resultJson.put("message", CM_INFO_DATA.SYSTEM_ERROR);
			resultJson.put("count", 0);
			resultJson.put("orderList", null);
//			return resultJson.toJSONString();
		}
		
		String resultJsonString;
		try {
			resultJsonString = URLEncoder.encode(resultJson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			resultJsonString = resultJson.toJSONString();
			e.printStackTrace();
		}
		
		return resultJsonString;
	}
	/**
	 * 根据用户ID获取用户订单
	 * @author 徐超
	 * @Date 2017年7月11日 下午5:29:18
	 * @param userId
	 * @return
	 * @see com.penghai.store.order.business.OrderManageBusiness#getUserOrderListByUserId(java.lang.String)
	 */
	@Override
	public String getUserOrderListByUserId(String userId){
		JSONObject resultJson  = new JSONObject();
		try {
			
			JSONArray resultArray = new JSONArray();
			List<Order> orderList = orderMapper.getOrderListByUserId(userId);
			
			// 解析订单数据
			for (Order o : orderList) {
				JSONObject orderObject = new JSONObject();
				Integer id = o.getId();
				String orderCode = o.getOrderCode();
				String buyTime = o.getBuyTimeStr();
				Integer userIdInt = o.getUserId();
				BigDecimal totalPrice = o.getTotalPrice();
				// 请求该订单下的所有商品
				OrderInfo record = new OrderInfo();
				record.setOrderId(id);
				List<Map<String, Object>> goods = orderInfoMapper.selectByOrderId(record);
				// 商品列表
				JSONArray goodArray = new JSONArray();
				for (Map<String, Object> good : goods) {
					// 商品详情
					JSONObject goodObject = new JSONObject();
					goodObject.put("goodId", String.valueOf(good.get("goodsId")));
					goodObject.put("goodName", good.get("goodsName"));
					goodObject.put("goodNumber", String.valueOf(good.get("goodsNumber")));
					goodObject.put("goodPrice", String.valueOf(good.get("goodsPrice")));
					String defaultPicture = String.valueOf(good.get("defaultPicture"));
					defaultPicture = defaultPicture.replace("../..", "@SERVERIP");
					goodObject.put("goodUrl", defaultPicture);
					String status = String.valueOf(good.get("status"));
					String statusName = "";
					if("0".equals(status)){
						statusName = "已下单";
					}else if("1".equals(status)){
						statusName = "已生成";
					}else if("2".equals(status)){
						statusName = "已下载";
					}else{
						statusName = "未知";
					}
					goodObject.put("status", statusName);
					// 加入商品列表
					goodArray.add(goodObject);
				}
				// 添加订单的详情
				orderObject.put("id", String.valueOf(id));
				orderObject.put("orderCode", orderCode);
				orderObject.put("orderDateTime", buyTime);
				orderObject.put("totalPrice", String.valueOf(totalPrice));
				orderObject.put("userId", String.valueOf(userIdInt));
				orderObject.put("goods", goodArray);
				// 将订单加入订单列表
				resultArray.add(orderObject);
			}
			
			
			resultJson.put("code", "0");
			resultJson.put("message", CM_INFO_DATA.QUERY_SUCCESS);
			resultJson.put("count", String.valueOf(resultArray.size()));
			resultJson.put("orderList", resultArray);
			
		} catch (Exception e) {
			//系统错误
			log.error(e.getMessage());
			resultJson.put("code", "2");
			resultJson.put("message", CM_INFO_DATA.SYSTEM_ERROR);
			resultJson.put("count", "0");
			resultJson.put("orderList", null);
//			return resultJson.toJSONString();
		}
		
		String resultJsonString;
		try {
			resultJsonString = URLEncoder.encode(resultJson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			resultJsonString = resultJson.toJSONString();
			e.printStackTrace();
		}
		
		return resultJsonString;
	}
	/**
	 * 获取用户指定xml详细信息
	 * @author 徐超
	 * @Date 2017年4月28日 下午5:17:28
	 * @param email
	 * @param password
	 * @param xmlId
	 * @return
	 */
	@Override
	public String getUserOrderXmlDetail(String email,String password,String xmlId){
		JSONObject resultJson  = new JSONObject();
		OrderGoodsFile xmlDetail = new OrderGoodsFile();
		try {
			//验证用户名密码是否正确
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			int loginCheck = userMapper.checkAuth(user);
			if(0<loginCheck){
				//正确,查询详情
				xmlDetail = orderGoodsFileMapper.getUserOrderXmlDetail(xmlId);
				resultJson.put("result", 1);
				resultJson.put("message", CM_LOGIN_DATA.LOGIN_SUCCESS_INFO);
				resultJson.put("xmlDetail", xmlDetail);
			}else{
				//用户名或密码错误
				resultJson.put("result", 0);
				resultJson.put("message", CM_LOGIN_DATA.LOGIN_ERROR_INFO);
				resultJson.put("xmlDetail", xmlDetail);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			//系统错误
			resultJson.put("result", 0);
			resultJson.put("message", CM_INFO_DATA.SYSTEM_ERROR);
			resultJson.put("xmlDetail", xmlDetail);
		}
		
		String resultStringEncode = "";
		try {
			resultStringEncode = URLEncoder.encode(resultJson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultStringEncode;
	}
	/**
	 * 修改订单xml文件状态
	 * @author 秦超
	 */
	@Override
	public String updateOrderStatus(String email,String password,String xmlId){
		String resultStatus = "0";
		try {
			//验证用户名密码是否正确
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			int loginCheck = userMapper.checkAuth(user);
			if(0<loginCheck){
				//正确,修改订单文件状态
				OrderGoodsFile orderGoodsFile = new OrderGoodsFile();
				orderGoodsFile.setId(Integer.valueOf(xmlId));
				orderGoodsFile.setStatus(2);
				orderGoodsFileMapper.updateOrderGoodsFileStatusByPrimaryKey(orderGoodsFile);
				
				resultStatus = "1";
			}
		} catch (Exception e) {
			return "0";
		}
		
		return resultStatus;
	}
	/**
	 * 根据goodsId和orderId获取用户指定xml详细信息
	 * @author 徐超
	 * @Date 2017年4月29日 下午9:54:36
	 * @param orderId
	 * @param goodsId
	 * @return
	 */
	@Override
	public Map<String, Object> getUserOrderXmlDetailByOrderIdAndGoodsId(String orderId,String goodsId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrderGoodsFile xmlDetailModel = new OrderGoodsFile();
		int version = 0;
		String xmlContent = "";
		OrderGoodsFile record = new OrderGoodsFile();
		record.setOrderId(Integer.valueOf(orderId));
		record.setGoodsId(Integer.valueOf(goodsId));
		try {
			xmlDetailModel = 
					orderGoodsFileMapper.getUserOrderXmlDetailByOrderIdAndGoodsId(record);
			version = xmlDetailModel.getVersion();
			xmlContent = xmlDetailModel.getXmlContent();
			resultMap.put("result", 1);
			resultMap.put("message", CM_QUERY_INFO.QUERY_SUCCESS_INFO);
			resultMap.put("xmlContent", xmlContent);
			resultMap.put("version", version);
			return resultMap;
		} catch (Exception e) {
			log.error(e.getMessage());
			resultMap.put("result", 0);
			resultMap.put("message", CM_INFO_DATA.SYSTEM_ERROR);
			resultMap.put("xmlContent", xmlContent);
			resultMap.put("version", version);
			return resultMap;
		}
	}
}
