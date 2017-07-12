package com.penghai.css.management.business;
/**
 * 订单接口类
 * @author 徐超
 * @Date 2017年7月11日 下午3:10:25
 */
public interface IOrderBusiness {

	/**
	 * 调用store接口获取订单
	 * @author 徐超
	 * @Date 2017年7月11日 下午3:22:17
	 * @param userEmail
	 * @param password
	 * @return
	 */
	String getUserOrdersFromStore(String userId);
}
