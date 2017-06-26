package com.penghai.store.user.business.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.common.model.HttpSession;
import com.penghai.store.user.business.IUserService;
import com.penghai.store.user.dao.IUserDao;
import com.penghai.store.user.dao.mybatis.UserMapper;
import com.penghai.store.user.model.User;
import com.penghai.store.util.common.CommonData.CM_LOGIN_DATA;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	public HttpSession session;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IUserDao userDao;

	/**
	 * 查询用户，验证账户名密码
	 */
	@Override
	public JSONObject checkIsUser(Map<String, Object> map) {
		User user = new User(String.valueOf(map.get("email")), String.valueOf(map.get("password")));
		User resultUser = userDao.login(user);
		JSONObject result = new JSONObject();
		if (resultUser != null) {
			// 登录成功
			result.put("message", CM_LOGIN_DATA.LOGIN_SUCCESS_INFO);
			result.put("code", CM_LOGIN_DATA.LOGIN_SUCCESS_CODE);
			// 成功后session设置用户登录信息
			if (session.get("userId") != null && !session.get("userId").equals("null")) {
				session.hdel("userId", "nickname", "email");
			}
			session.set("userId", "" + resultUser.getId());
			session.set("nickname", resultUser.getNickname());
			session.set("email", resultUser.getEmail());

		} else {
			// 登录失败
			result.put("message", CM_LOGIN_DATA.LOGIN_ERROR_INFO);
			result.put("code", CM_LOGIN_DATA.LOGIN_ERROR_CODE);
		}
		return result;
	}

}
