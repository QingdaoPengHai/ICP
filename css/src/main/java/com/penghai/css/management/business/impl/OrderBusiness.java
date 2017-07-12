package com.penghai.css.management.business.impl;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.penghai.css.management.business.IOrderBusiness;
import com.penghai.css.util.HTTPUtil;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_INFO_DATA;
import com.penghai.css.util.CommonData.CM_STORE_SERVER_FUNCTION;

/**
 * 订单接口实现类
 * @author 徐超
 * @Date 2017年7月11日 下午3:11:04
 */
@Service
public class OrderBusiness implements IOrderBusiness{

	Logger log = Logger.getLogger(LoginBusiness.class);
	/**
	 * 调用store接口获取订单
	 * @author 徐超
	 * @Date 2017年7月11日 下午3:22:17
	 * @param userEmail
	 * @param password
	 * @return
	 */
	public String getUserOrdersFromStore(String userId){
		String resultJsonString = "";
		String requestLoginUrl = CM_CONFIG_PROPERTIES.STORE_SERVER_URL + CM_STORE_SERVER_FUNCTION.ORDER_LIST_BY_USERID;
		try {
			resultJsonString = HTTPUtil.loadURL(requestLoginUrl+"?userId="+userId);
		} catch (UnsupportedEncodingException e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR, e);
		}
		return resultJsonString;
	}
}
