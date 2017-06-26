package com.penghai.store.admin.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.common.model.HttpSession;
import com.penghai.store.util.common.CommonData.CM_LOGOUT_DATA;

/**
 * 退出登录服务
 * 
 * @author 李浩
 */
@Service
public class LogoutBusinessImpl {
	@Autowired
	private HttpSession session;

	/**
	 * 退出账号业务逻辑
	 * 
	 * @return
	 */
	public JSONObject logout() {
		String userId = session.get("adminUserId");
		JSONObject returnObject = new JSONObject();
		returnObject.put("user", userId);
		if (userId != null && !userId.equals("null")) {
			int result = session.hdel("adminUserId", "adminEmail", "adminNickname");
			if (result != 0) {
				returnObject.put("success", true);
				returnObject.put("message", CM_LOGOUT_DATA.LOGOUT_SUCCESS);
			} else {
				returnObject.put("success", false);
				returnObject.put("message", CM_LOGOUT_DATA.LOGOUT_ERROR_SYSTEM);
			}
		} else {
			returnObject.put("success", false);
			returnObject.put("message", CM_LOGOUT_DATA.LOGOUT_ERROR_USER);
		}
		return returnObject;
	}
}
