package com.penghai.css.management.business.impl;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.penghai.css.management.business.ILoginBusiness;
import com.penghai.css.util.HTTPUtil;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_INFO_DATA;
import com.penghai.css.util.CommonData.CM_STORE_SERVER_FUNCTION;
/**
 * 登录业务实现类
 * @author 徐超
 * @Date 2017年6月27日 下午4:55:26
 */
@Service
public class LoginBusiness implements ILoginBusiness {
	
	Logger log = Logger.getLogger(LoginBusiness.class);

	/**
	 * 移动端登录方法
	 * @author 徐超
	 * @Date 2017年6月27日 下午5:36:09
	 * @param email 邮箱
	 * @param password 密码
	 * @return
	 */
	public String mobileLogin(String email,String password){
		
		String resultJsonString = "";
		String requestLoginUrl = CM_CONFIG_PROPERTIES.STORE_SERVER_URL + CM_STORE_SERVER_FUNCTION.LOGIN_CHECKIN;
		try {
			resultJsonString = HTTPUtil.loadURL(requestLoginUrl+"?userEmail="+email+"&password="+password);
		} catch (UnsupportedEncodingException e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR, e);
		}
		return resultJsonString;
	}
}
