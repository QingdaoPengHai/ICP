package com.penghai.css.management.controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.formatTransfer.IFormatTransfer;
import com.penghai.css.management.business.ILoginBusiness;

/**
 * 移动端登录类
 * @author 徐超
 * @Date 2017年6月27日 下午4:53:57
 */
@Controller
@RequestMapping(
		value="/mobile",
		method=RequestMethod.POST,
		produces = "application/json;charset=UTF-8")
public class MobileLoginController {
	
	@Autowired
	private IFormatTransfer iFormatTransfer;
	
	@Autowired
	private ILoginBusiness iLoginBusiness;

	/**
	 * 移动端验证登录接口
	 * @author 徐超
	 * @Date 2017年6月27日 下午5:34:27
	 * @param userEmail
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public JSONObject login(String userEmail,String password){
		
		JSONObject resultJson = new JSONObject();
		
		String resultJsonString = iLoginBusiness.mobileLogin(userEmail, password);
		
		resultJson = iFormatTransfer.transferUserLoginFormat(resultJsonString);
		
		return resultJson;
	}
}
