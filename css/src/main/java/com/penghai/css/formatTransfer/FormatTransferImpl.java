package com.penghai.css.formatTransfer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_INFO_DATA;
import com.penghai.css.util.CommonData.CM_LOGIN_DATA;

/**
 * 格式转换实现类
 * @author 徐超
 * @Date 2017年6月27日 上午11:31:33
 */
@Service
public class FormatTransferImpl implements IFormatTransfer {

	Logger log = Logger.getLogger(FormatTransferImpl.class);
	/**
	 * 用户登录接口的格式化方法
	 * @author 徐超
	 * @Date 2017年6月27日 下午2:34:20
	 * @param json
	 * @return
	 */
	public JSONObject transferUserLoginFormat(String jsonString){
		JSONObject resultJson = new JSONObject();
		try {
			if("".equals(jsonString) || null==jsonString){
				resultJson.put("code", "2");
				resultJson.put("message", CM_INFO_DATA.SYSTEM_ERROR);
				resultJson.put("data", null);
				return resultJson;
			}else{
				String jsonStringDecode = URLDecoder.decode(jsonString, "utf-8");
				JSONObject json = new JSONObject();
				json = JSONObject.parseObject(jsonStringDecode);
				boolean loginResult = json.getBooleanValue("result");
				if(loginResult){
					//登陆成功
					JSONObject userInfoJson = json.getJSONObject("userInfo");
					JSONObject resultData = new JSONObject();
					
					resultData.put("userId", userInfoJson.getString("id"));
					resultData.put("nickname", userInfoJson.getString("nickname"));
					resultData.put("email", userInfoJson.getString("email"));
					
					resultJson.put("code", "0");
					resultJson.put("message", CM_LOGIN_DATA.LOGIN_SUCCESS_INFO);
					resultJson.put("data", resultData);
				}else{
					//登录失败
					resultJson.put("code", "1");
					resultJson.put("message", CM_LOGIN_DATA.LOGIN_ERROR_INFO);
					resultJson.put("data", null);
				}
				return resultJson;
			}
		} catch (UnsupportedEncodingException e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR, e);
			resultJson.put("code", "2");
			resultJson.put("message", "系统错误");
			resultJson.put("data", null);
			return resultJson;
		}
	}
	
	/**
	 * 获取用户订单列表的格式化方法
	 * @author 徐超
	 * @Date 2017年7月11日 下午3:24:21
	 * @param jsonString
	 * @return
	 */
	public JSONObject transferUserOrdersFormat(String jsonString){
		JSONObject resultJson = new JSONObject();
		String jsonStringDecode = "";
		try {
			jsonStringDecode = URLDecoder.decode(jsonString, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		jsonStringDecode = jsonStringDecode.replace("@SERVERIP", CM_CONFIG_PROPERTIES.STORE_ROOT_URL);
		
		resultJson = JSONObject.parseObject(jsonStringDecode);
		JSONArray orderListArray = resultJson.getJSONArray("orderList");
		
		JSONObject dataJson = new JSONObject();
		dataJson.put("orders", orderListArray);
		dataJson.put("count", resultJson.get("count"));
		resultJson.remove("orderList");
		resultJson.remove("count");
		resultJson.put("data", dataJson);
	
		return resultJson;
	}
	
}
