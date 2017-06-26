package com.penghai.store.user.dao;

import java.util.Map;

import com.penghai.store.user.model.User;

public interface IUserDao {

	Map<String, Object> regist(User user);
	
	User login(User user);
	/**
	 * @author 刘晓强
	 * 改密码
	 */
	int changePassword(User user);
}
