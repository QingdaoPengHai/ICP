package com.penghai.css.management.controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.formatTransfer.IFormatTransfer;
import com.penghai.css.management.business.IOrderBusiness;

/**
 * 移动端订单类
 * @author 徐超
 * @Date 2017年7月11日 下午2:43:39
 */
@Controller
@RequestMapping(
		value="/mobile",
		method=RequestMethod.POST,
		produces = "application/json;charset=UTF-8")
public class MobileOrderController {

	@Autowired
	private IOrderBusiness iOrderBusiness;
	
	@Autowired
	private IFormatTransfer iFormatTransfer;
	/**
	 * 获取用户订单
	 * @author 徐超
	 * @Date 2017年7月11日 下午2:58:07
	 * @return
	 */
	@RequestMapping(value="/queryOrders")
	@ResponseBody
	public JSONObject queryOrders(String userId){
		
		JSONObject resultJson = new JSONObject();
		
		String orderListString = iOrderBusiness.getUserOrdersFromStore(userId);
		
		resultJson = iFormatTransfer.transferUserOrdersFormat(orderListString);
		
		return resultJson;
	}
	
}
