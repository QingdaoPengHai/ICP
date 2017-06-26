package com.penghai.store.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.order.business.OrderManageBusiness;

/**
 * 订单管理控制器
 * 
 * @author 李浩
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderManageController extends BaseController {
	@Autowired
	private OrderManageBusiness orderManageBusiness;

	/*
	 * 查询当前用户所有订单及订单下商品详情
	 */
	@RequestMapping(value = "/queryAllOrdersAndGoods")
	@ResponseBody
	public JSONArray queryAllOrdersAndGoods() {
		// String userId = session.get("userId");
		String userId = request.getParameter("userId");
		return orderManageBusiness.getAllOrderAndGoods(Integer.valueOf(userId));
	}

	/*
	 * 用户删除某一订单,修改订单表的isDelete字段,1代表了删除
	 */
	@RequestMapping(value = "/cancelOrder")
	@ResponseBody
	public JSONObject cancelOrder() {
		String id = request.getParameter("id");
		return orderManageBusiness.deleteOrder(Integer.valueOf(id));
	}
	
	
	/**
	 * 后台编辑用户订单商品xml信息
	 * @author 徐超
	 * @Date 2017年4月28日 下午1:11:44
	 * @return
	 */
	@RequestMapping(value = "/editOrderGoodsXml")
	@ResponseBody
	public JSONObject editOrderGoodsXml(){
		
		String orderId = request.getParameter("orderId");
		String userId = request.getParameter("userId");
		String goodsId = request.getParameter("goodsId");
		String version = request.getParameter("version");
		String xmlContent = request.getParameter("xmlContent");
		String goodModel = request.getParameter("goodsModel");
		String isEdit = request.getParameter("isEdit");
		return orderManageBusiness.editOrderGoodsXml(orderId,userId,goodsId,version,xmlContent,goodModel,isEdit);
		
	}
	/**
	 * 获取用户所有订单对应的XML_List
	 * @author 徐超
	 * @Date 2017年4月28日 下午2:56:30
	 * @return
	 */
	@RequestMapping(value = "/getUserOrderXmlList")
	@ResponseBody
	public String getUserOrderXmlList(){
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		return orderManageBusiness.getUserOrderXmlList(email,password);
	}
	/**
	 * 获取用户指定xml详细信息--登陆
	 * @author 徐超  changeBy 秦超
	 * @Date 2017年4月28日 下午5:15:27
	 * @return
	 */
	@RequestMapping(value = "/getUserOrderXmlDetail")
	@ResponseBody
	public String getUserOrderXmlDetail(HttpServletRequest request){
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String xmlId = request.getParameter("xmlId");
		return orderManageBusiness.getUserOrderXmlDetail(email,password,xmlId);
	}
	/**
	 * 修改xml文件下载状态
	 * @author 秦超
	 * @time 2017年5月11日
	 */
	@RequestMapping(value = "/updateOrderStatus")
	@ResponseBody
	public String updateOrderStatus(HttpServletRequest request){
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String xmlId = request.getParameter("xmlId");
		return orderManageBusiness.updateOrderStatus(email,password,xmlId);
	}
	/**
	 * 根据goodsId和orderId获取用户指定xml详细信息
	 * @author 徐超
	 * @Date 2017年4月29日 下午9:44:39
	 * @return
	 */
	@RequestMapping(value = "/getUserOrderXmlDetailByOrderIdAndGoodsId")
	@ResponseBody
	public Map<String, Object> getUserOrderXmlDetailByOrderIdAndGoodsId(){
		String orderId = request.getParameter("orderId");
		String goodsId = request.getParameter("goodsId");
		return orderManageBusiness.getUserOrderXmlDetailByOrderIdAndGoodsId(orderId,goodsId);
	}

	/**
	 * 后台获取所有订单列表
	 * @author Q.chao
	 * @return
	 */
	@RequestMapping(value = "/getAllOrderByAdmin")
	@ResponseBody
	public JSONArray getAllOrderByAdmin() {
		return orderManageBusiness.getAllOrderEveryOne();
	}
}
