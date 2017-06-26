package com.penghai.store.user.business;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.user.model.User;

public interface IUserService {
 
	public JSONObject checkIsUser(Map<String, Object> map);
	
}
